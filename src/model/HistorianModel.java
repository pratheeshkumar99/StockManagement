package model;

import utils.Utils;
import controller.filewriter.IFileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Simple Implementation of the IHistorianPortfolioManager that contains an internal historian for
 * retrieving historical values.
 */
public class HistorianModel implements IHistorianPortfolioManager {

  protected final IFlexibleHistorian h;
  private final Map<String, Set<IStock>> portfolios;
  private final Set<String> immutablePortfolios;


  /**
   * Public Constructor of a Historian model, used by the controller.
   * @param getValue a Call back function to retrieve online data.
   */
  public HistorianModel(Function<String, Map<LocalDate, List<Double>>> getValue) {
    this.h = new FlexibleHistorianImpl(getValue);
    this.portfolios = new HashMap<>();
    this.immutablePortfolios = new HashSet<>();
  }

  @Override
  public double getPortfolioValue(String portfolioName, LocalDate date)
      throws IllegalArgumentException {
    checkValidPortfolioName(portfolioName);
    return portfolios.get(portfolioName).stream()
      .filter(s -> s.getDate().isEqual(date) || s.getDate().isBefore(date))
      .mapToDouble(s -> s.getQuantity() * (getStockPrice(s.getName(), date, true)))
      .sum();
  }

  @Override
  public double getStockPrice(String tickerSymbol, LocalDate date, boolean atClosing)
      throws IllegalArgumentException {
    return h.getValue(tickerSymbol, date, atClosing);
  }

  @Override
  public double getCostBasis(String portfolioName, LocalDate date) throws IllegalArgumentException {
    checkValidPortfolioName(portfolioName);
    return portfolios.get(portfolioName).stream()
      .filter(s -> s.getDate().isEqual(date) || s.getDate().isBefore(date) && s.getQuantity() > 0)
      .mapToDouble(s -> s.getQuantity() * (getStockPrice(s.getName(), s.getDate(), true)))
      .sum();
  }

  @Override
  public void savePortfolio(IFileWriter writer, String portfolioName, String path)
      throws IOException {
    checkValidPortfolioName(portfolioName);
    try {
      writer.write(path, portfolioName, portfolios.get(portfolioName));
    } catch (IllegalArgumentException e) {
      throw new IOException(e.getMessage());
    }

  }


  @Override
  public void addStock(String ticker, double quantity, LocalDate date, String portfolioName)
      throws IllegalArgumentException {
    addStockHelper(ticker, quantity, date, portfolioName);
  }

  protected void addStockHelper(String ticker, double quantity, LocalDate date,
      String portfolioName)
      throws IllegalArgumentException {
    checkValidPortfolioName(portfolioName);
    if (immutablePortfolios.contains(portfolioName)) {
      String msg = String.format( "Portfolio: %s is immutable. "
          + "Please make it mutable before attempting to buy/sell stocks.", portfolioName);
      throw new IllegalArgumentException(msg);
    }

    IStock stock = new Stock(ticker, quantity, date);
    h.checkStock(stock);
    double availableByDate = PortfolioUtils.numSharesOnDate(ticker, date,
        getPortfolioComposition(portfolioName));
    double availableEver = PortfolioUtils.numSharesThroughout(ticker,
        getPortfolioComposition(portfolioName));
    if (availableByDate >= - 1 * quantity && availableEver >= - 1 * quantity) {
      safePut(ticker, date, quantity, portfolioName);
    } else if (availableByDate < - 1 * quantity) {
      String msg = String.format(
          "Portfolio %s does not own enough shares of stock: %s by %s for this transaction."
          + " You must first purchase at least %.2f shares on this date or earlier",
          portfolioName, ticker,
          date.format(DateTimeFormatter.ofPattern("yyyy MMM dd")), availableByDate - quantity);
      throw new IllegalArgumentException(msg);
    } else if (availableEver < - 1 * quantity) {
      String msg = String.format("Portfolio %s is already set to sell stock: %s in the future.",
          portfolioName, ticker);
      throw new IllegalArgumentException(msg);
    }
  }

  @Override
  public void createPortfolio(String portfolioName) throws IllegalArgumentException {
    if (this.portfolios.containsKey(portfolioName)) {
      throw new IllegalArgumentException(
        String.format("Portfolio name: %s is already used.", portfolioName));
    }
    this.portfolios.put(portfolioName, new HashSet<>());
  }

  @Override
  public void flipMutability(String portfolioName) {
    checkValidPortfolioName(portfolioName);
    if (immutablePortfolios.contains(portfolioName)) {
      immutablePortfolios.remove(portfolioName);
    } else {
      immutablePortfolios.add(portfolioName);
    }
  }

  @Override
  public boolean isPortfolioMutable(String portfolioName) {
    checkValidPortfolioName(portfolioName);
    return ! immutablePortfolios.contains(portfolioName);
  }

  @Override
  public String[] listPortfolioNames() {
    return portfolios.keySet().toArray(new String[0]);
  }

  @Override
  public Set<IStock> getPortfolioComposition(String portfolioName) throws IllegalArgumentException {
    checkValidPortfolioName(portfolioName);
    return Collections.unmodifiableSet(portfolios.get(portfolioName));
  }

  @Override
  public SortedMap<LocalDate, Double> getStockPrices(ChronoUnit unit, String ticker,
      LocalDate lastEntry, int maxSize, boolean atClosing, int length)
      throws IllegalArgumentException {

    SortedMap<LocalDate, Double> result = new TreeMap<>();
    LocalDate dateToQuery = lastEntry;
    while (result.size() < maxSize) {

      //if the current date is already prior to an IPO, quit the loop
      if (dateToQuery.isBefore(h.getIPO(ticker))) {
        return result;
      }
      try {
        Entry<LocalDate, Double> val = h.getLatestValueWithinRange(ticker,
            Utils.lastWeekdayOfPreviousUnit(dateToQuery, unit, length), dateToQuery, atClosing);
        result.put(val.getKey(), val.getValue());
      } catch (IllegalArgumentException ignored) {
        //do nothing and try the next one
      }
      dateToQuery = Utils.lastWeekdayOfPreviousUnit(dateToQuery, unit, length);
    }
    return result;
  }

  @Override
  public SortedMap<LocalDate, Double> getPortfolioValues(ChronoUnit unit, String portfolioName,
      LocalDate lastEntry, int size, int length)
      throws IllegalArgumentException, IllegalStateException {

    checkValidPortfolioName(portfolioName);

    SortedMap<LocalDate, Double> finalResult = new TreeMap<>();
    Set<String> tickers = PortfolioUtils.getUniqueTickers(
        portfolios.get(portfolioName), LocalDate.MIN, lastEntry);
    LocalDate maxBound = lastEntry;
    LocalDate minBound = Utils.lastWeekdayOfPreviousUnit(maxBound, unit, length);
    LocalDate resultingDay = lastEntry;
    boolean hasAtLeastOneValidEntry = false;
    boolean priorToIPODateForAll = true;

    while (finalResult.size() < size) {
      double sum = 0.0;

      for (String ticker : tickers) {
        try {
          Entry<LocalDate, Double> queryResult
              = h.getLatestValueWithinRange(ticker, minBound, maxBound, true);
          sum += PortfolioUtils.numSharesOnDate(
              ticker, queryResult.getKey(), portfolios.get(portfolioName)) * queryResult.getValue();
          resultingDay = queryResult.getKey();
          hasAtLeastOneValidEntry = true;

        } catch (IllegalArgumentException ignored) {
        }
        priorToIPODateForAll = priorToIPODateForAll && h.getIPO(ticker).isAfter(maxBound);
      }
      if (priorToIPODateForAll) {
        return finalResult; //return early without this data if there is no more useful information
      }
      if (hasAtLeastOneValidEntry) {
        finalResult.put(resultingDay, sum);
      }

      //reset for the next date loop
      hasAtLeastOneValidEntry = false;
      priorToIPODateForAll = true;
      maxBound = Utils.lastWeekdayOfPreviousUnit(maxBound, unit, length);
      minBound = Utils.lastWeekdayOfPreviousUnit(maxBound, unit, length);
    }
    return finalResult;
  }

  private void checkValidPortfolioName(String portfolioName) throws IllegalArgumentException {
    if (! portfolios.containsKey(portfolioName)) {
      String msg = String.format("Portfolio: %s is not recognized.", portfolioName);
      throw new IllegalArgumentException(msg);
    }
  }

  @Override
  public boolean isPortfolioPresent(String portfolioName) {
    return this.portfolios.containsKey(portfolioName);
  }

  private void safePut(String ticker, LocalDate date, double quantity, String portfolioName) {
    if (! portfolios.containsKey(portfolioName)) {
      throw new IllegalStateException("called safePut with invalid portfolio name");
    }
    Set<IStock> sameDateAndTicker = portfolios.get(portfolioName)
        .stream()
        .filter(s -> s.getName().equals(ticker) && s.getDate().equals(date))
        .collect(Collectors.toSet());
    if (sameDateAndTicker.isEmpty()) {
      portfolios.get(portfolioName).add(new Stock(ticker, quantity, date));
      return;
    }
    double aggQuantity = quantity;
    for (IStock toDelete : sameDateAndTicker) {
      portfolios.get(portfolioName).remove(toDelete);
      aggQuantity += toDelete.getQuantity();
    }
    //if the final aggQuantity is 0, don't add to the portfolio, just return
    if (aggQuantity == 0) {
      return;
    }
    portfolios.get(portfolioName).add(new Stock(ticker, aggQuantity, date));
  }
}