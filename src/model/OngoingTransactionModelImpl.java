package model;

import utils.Utils;
import controller.filewriter.IFileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Function;

/**
 * Portfolio manager model that supports repeat stock transactions.
 */
public class OngoingTransactionModelImpl extends HistorianModel implements OngoingTransactionModel {
  private final Map<String, Set<IRepeatingStockTransaction>> portfolioRepeatingStocks;

  /**
   * Public Constructor of a Historian model, used by the controller.
   *
   * @param getValue a Call back function to retrieve online data.
   */
  public OngoingTransactionModelImpl(Function<String, Map<LocalDate, List<Double>>> getValue) {
    super(getValue);
    this.portfolioRepeatingStocks = new HashMap<>();
  }

  @Override
  public double getPortfolioValue(String portfolioName, LocalDate date)
      throws IllegalArgumentException {

    double finalPrice = super.getPortfolioValue(portfolioName, date);
    //add repeating stocks between LocalDate.min and the given date or yesterday, whichever earlier
    LocalDate max = (date.isBefore(LocalDate.now())) ? date : LocalDate.now().minusDays(1);
    Set<IStock> repeatStocks = renderStocks(LocalDate.MIN, max, portfolioName);
    Set<String> uniqueTickers = PortfolioUtils.getUniqueTickers(repeatStocks, LocalDate.MIN, max);
    double numShares;
    double stockPrice;
    for (String ticker : uniqueTickers) {
      stockPrice = getStockPrice(ticker, date, true);
      numShares = PortfolioUtils.numSharesOnDate(ticker, date, repeatStocks);
      finalPrice += stockPrice * numShares;
    }
    return finalPrice;
  }

  @Override
  public double getCostBasis(String portfolioName, LocalDate date) throws IllegalArgumentException {
    double initial = super.getCostBasis(portfolioName, date);
    initial += renderStocks(LocalDate.MIN, date, portfolioName).stream()
                 .filter(s -> s.getQuantity() > 0
                                && Utils.inRange(s.getDate(), LocalDate.MIN, date))
                 .mapToDouble(s -> s.getQuantity()
                                     * getStockPrice(s.getName(), s.getDate(), true))
                 .sum();
    return initial;
  }

  @Override
  public void savePortfolio(IFileWriter writer, String portfolioName, String path)
      throws IOException {
    writer.write(path, portfolioName, getPortfolioComposition(portfolioName));
  }

  @Override
  public void createPortfolio(String portfolioName) throws IllegalArgumentException {
    super.createPortfolio(portfolioName);
    this.portfolioRepeatingStocks.put(portfolioName, new HashSet<>());
  }

  @Override
  public Set<IStock> getPortfolioComposition(String portfolioName) throws IllegalArgumentException {
    Set<IStock> initial = new HashSet<>();

    if (!isPortfolioPresent(portfolioName)) {
      throw new IllegalArgumentException("Portfolio Name not recognized");
    }
    try {
      LocalDate earliest = this.portfolioRepeatingStocks.get(portfolioName).stream()
                             .map(IRepeatingStockTransaction::getDate)
                             .sorted().findFirst().orElseThrow();
      LocalDate latest = LocalDate.now().minusDays(1);
      initial.addAll(renderStocks(earliest, latest, portfolioName));
    } catch (NoSuchElementException ignored) { }
    initial.addAll(super.getPortfolioComposition(portfolioName));
    return Collections.unmodifiableSet(initial);
  }

  @Override
  public void addDollarCostAveragingPortfolio(String portfolioName,
      Map<String, Double> investmentAmounts, LocalDate startDate, ChronoUnit unit, int length,
      int reps) throws IllegalArgumentException {

    createPortfolio(portfolioName);
    for (String ticker : investmentAmounts.keySet()) {
      if (investmentAmounts.get(ticker) <= 0) {
        throw new IllegalArgumentException("All investment amounts must be positive");
      }
      LocalDate earliest = h.getEarliest(ticker, startDate, true).getKey();
      portfolioRepeatingStocks.get(portfolioName).add(
          new RepeatingStock(ticker, investmentAmounts.get(ticker), earliest, length, unit, reps));
    }
  }

  @Override
  public String[] getDCAPortfolios() {
    return this.portfolioRepeatingStocks.keySet().toArray(new String[0]);
  }

  @Override
  public Set<IRepeatingStockTransaction> getDCAComposition(String portfolioName)
      throws IllegalArgumentException {
    if (!this.portfolioRepeatingStocks.containsKey(portfolioName)) {
      throw new IllegalArgumentException("Portfolio name provided does not exist "
                                           + "or does not use Dollar Cost Averaging.");
    }
    return Collections.unmodifiableSet(this.portfolioRepeatingStocks.get(portfolioName));
  }


  /**
   * Helper function to render out repeat stocks into a set of IStocks between two dates.
   * @param min the earliest date (inclusive).
   * @param max the latest date (inclusive).
   * @param portfolioName  the portfolio name that the stocks should be pulled from.
   * @return all repeat stock transactions that are set to take place between the two dates.
   *         If a stock would ideally be bought or sold on a holiday,
   *         it instead uses the earliest available market day following,
   *         which could fall outside the range.
   */
  private Set<IStock> renderStocks(LocalDate min, LocalDate max, String portfolioName) {
    Set<IStock> finalResult = new HashSet<>();
    for (IRepeatingStockTransaction r : portfolioRepeatingStocks.get(portfolioName)) {
      finalResult.addAll(r.getStocksBetween(min, max, h));
    }
    return finalResult;
  }
}
