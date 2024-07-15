package controllertest;

import controller.filewriter.IFileWriter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import model.OngoingTransactionModel;
import model.IRepeatingStockTransaction;
import model.IStock;

/**
 * The Mock Model class that logs all the messages that are passed to it.
 */
public class MockModel implements OngoingTransactionModel {

  private final List<String> log;
  private final List<String> existingPortfolios;
  private final List<String> mutablePortfolio;
  private final List<String> validStocks;
  private final HashMap<LocalDate, SortedMap<LocalDate, Double>> tickerStockData;

  private final List<String> validFilePath;

  private boolean setWeightedAddStockFlag = false;

  /**
   * Constructs a MockModel object.
   */
  public MockModel() {
    this.log = new ArrayList<>();
    this.existingPortfolios = new ArrayList<>();
    this.mutablePortfolio = new ArrayList<>();
    this.validStocks = new ArrayList<>();
    this.tickerStockData = new HashMap<>();
    this.validFilePath = new ArrayList<>();
  }

  /**
   * Sets the valid file Path for testing.
   *
   * @param path The path of the file.
   */

  public void setValidFilePath(String path) {
    this.validFilePath.add(path);
  }


  /**
   * Gets the Log of the messages.
   *
   * @return the log of the messages.
   */

  public List<String> getLog() {
    return log;
  }

  /**
   * Adds an existing portfolio to the list of existing portfolios.
   *
   * @param portfolio the portfolio to be added.
   */
  public void addExistingPortfolio(String portfolio) {
    this.existingPortfolios.add(portfolio.toLowerCase());
  }

  /**
   * Adds the valid stock to mimic mocking.
   *
   * @param stock The valid stocks.
   */

  public void addSValidStocks(String stock) {
    this.validStocks.add(stock.toLowerCase());
  }

  /**
   * Adds the mutable portfolio for mocking.
   *
   * @param portfolio The name of the mutable portfolio.
   */

  public void addMutableExisting(String portfolio) {
    this.mutablePortfolio.add(portfolio.toLowerCase());
  }


  /**
   * logs the Sets the valid data Method call.
   *
   * @param date The date for which the data exists.
   * @param days The number of days for which the data exists.
   */

  public void setValidDataExists(LocalDate date, int days) {
    SortedMap<LocalDate, Double> portfolioValues = new TreeMap<>();
    LocalDate currentDate = date;
    double currentValue = 100;

    for (int i = 0; i < days; i++) {
      portfolioValues.put(currentDate, currentValue);
      currentDate = currentDate.plusDays(1);
      currentValue += 1; // Incrementing the dummy value
    }
    this.tickerStockData.put(date, portfolioValues);
  }

  /**
   * Logs the action of Invoking getPortfolio value.
   *
   * @param portfolioName the name of the portfolio.
   * @param date          the date the value should be calculated with.
   * @return A dummy value.
   * @throws IllegalArgumentException if the portfolio does not exist.
   */
  @Override
  public double getPortfolioValue(String portfolioName, LocalDate date)
          throws IllegalArgumentException {
    log.add("get portfolio value called with portfolioName:" + portfolioName + " date:" + date);
    return 0;
  }

  /**
   * Logs the action of Invoking getStockPrice.
   *
   * @param tickerSymbol the NYSE ticker symbol of the stock.
   * @param date         the date of the stock.
   * @param atClosing    whether the opening (false) or closing (true) price is retrieved.
   * @return A dummy value.
   * @throws IllegalArgumentException if the stock does not exist.
   */

  @Override
  public double getStockPrice(String tickerSymbol, LocalDate date, boolean atClosing)
          throws IllegalArgumentException {
    if (!this.validStocks.isEmpty()) {
      if (!this.validStocks.contains(tickerSymbol.toLowerCase())) {
        throw new IllegalArgumentException("Stock not found");
      } else {
        log.add("get stock price called with tickerSymbol:" + tickerSymbol + " " +
                "date:" + date + " atClosing:" + atClosing);
      }
    } else {
      log.add("get stock price called with tickerSymbol:" + tickerSymbol + " " +
              "date:" + date + " atClosing:" + atClosing);
    }
    if (this.setWeightedAddStockFlag) {
      return 50;
    } else {
      return 0;
    }
  }

  /**
   * Logs the action of Invoking getCostBasis.
   *
   * @param portfolioName the name of the portfolio.
   * @param date          the date the cost basis is calculated with.
   * @return A dummy value.
   * @throws IllegalArgumentException if the portfolio does not exist.
   */

  @Override
  public double getCostBasis(String portfolioName, LocalDate date) throws
          IllegalArgumentException {
    log.add("get cost basis called with portfolioName:" + portfolioName + " date:" + date);
    return 0;
  }


  private boolean isValidPath(String path) {
    return validFilePath.contains(path);
  }

  /**
   * Sets the flag to return a weighted value for the stock.
   */
  public void setWeightedAddStock() {
    this.setWeightedAddStockFlag = true;
  }

  /**
   * Logs the action of Invoking savePortfolio.
   *
   * @param writer        some writer object that is capable of writing the state of the model.
   * @param portfolioName the name of the portfolio.
   * @param path          the path to save the portfolio to.
   * @throws IllegalArgumentException if the path is invalid.
   */
  @Override
  public void savePortfolio(IFileWriter writer, String portfolioName, String path)
          throws IllegalArgumentException {
    if (!this.validFilePath.isEmpty()) {
      if (this.isValidPath(path)) {
        log.add("save portfolio called with portfolioName:" + portfolioName + " path:" + path);
      } else {
        throw new IllegalArgumentException("Invalid file Path. Please enter a valid file path.");
      }
    } else {
      log.add("save portfolio called with portfolioName:" + portfolioName + " path:" + path);
    }
  }

  /**
   * Logs the action of Invoking addStock.
   *
   * @param ticker        the stock to be added.
   * @param quantity      the quantity of stocks to be added. A negative quantity indicates stocks
   *                      sold.
   * @param date          the date of the transaction.
   * @param portfolioName the portfolio the stock should be added to.
   * @throws IllegalArgumentException if the stock does not exist.
   */

  @Override
  public void addStock(String ticker, double quantity, LocalDate date, String portfolioName)
          throws IllegalArgumentException {
    if (!this.validStocks.isEmpty()) {
      if (!this.validStocks.contains(ticker.toLowerCase())) {
        throw new IllegalArgumentException("Stock not found");
      } else {
        log.add("add stock called with ticker:" + ticker + " quantity:" + quantity +
                " date:" + date + " portfolioName:" + portfolioName);
      }
    } else {
      log.add("add stock called with ticker:" + ticker + " quantity:" + quantity +
              " date:" + date + " portfolioName:" + portfolioName);
    }
  }


  /**
   * Logs the action of Invoking createPortfolio.
   *
   * @param portfolioName the portfolio name.
   * @throws IllegalArgumentException if the portfolio already exists.
   */

  @Override
  public void createPortfolio(String portfolioName) throws IllegalArgumentException {
    log.add("create portfolio called with portfolioName:" + portfolioName);
  }

  /**
   * Logs the action of Invoking addPortfolio.
   *
   * @param portfolioName the name of the portfolio.
   * @throws IllegalArgumentException if the portfolio does not exist.
   */
  @Override
  public void flipMutability(String portfolioName) throws IllegalArgumentException {
    log.add("flip mutability called with portfolioName:" + portfolioName);
  }

  /**
   * Logs the action of Invoking addPortfolio.
   *
   * @param portfolioName the name of the portfolio.
   * @return true if the portfolio is mutable, false otherwise.
   * @throws IllegalArgumentException if the portfolio does not exist.
   */

  @Override
  public boolean isPortfolioMutable(String portfolioName) throws IllegalArgumentException {
    return mutablePortfolio.contains(portfolioName);
  }

  /**
   * Logs the action of Invoking addPortfolio.
   *
   * @return the list of portfolio names.
   */

  @Override
  public String[] listPortfolioNames() {
    return new String[] {"Example"};
  }

  /**
   * Logs the action of Invoking addPortfolio.
   *
   * @param portfolioName the name of the portfolio.
   * @return the list of stock tickers in the portfolio.
   * @throws IllegalArgumentException if the portfolio does not exist.
   */

  @Override
  public Set<IStock> getPortfolioComposition(String portfolioName) throws IllegalArgumentException {
    log.add("get portfolio composition called with portfolioName:" + portfolioName);
    return null;
  }

  /**
   * Logs the action of gettingStock prices.
   *
   * @param unit      A date-based unit of time.
   * @param ticker    A NYSE ticker symbol.
   * @param lastEntry The date of the last entry. If not available, the next earliest will be used.
   * @param maxSize   The maximum size of the map to be returned. If there are not enough values
   *                  available, a smaller map will be returned.
   * @param atClosing if the prices should be closing prices (true) or opening prices (false).
   * @param length    The length of each unit, such as 1 month vs 3 weeks.
   * @return A map of dates to stock prices.
   * @throws IllegalArgumentException if the stock does not exist.
   */

  @Override
  public SortedMap<LocalDate, Double> getStockPrices(ChronoUnit unit, String ticker,
                                                     LocalDate lastEntry, int maxSize,
                                                     boolean atClosing, int length) throws
          IllegalArgumentException {
    log.add("get stock prices called with ticker:" + ticker + "date:" + lastEntry
            + "atClosing:" + atClosing);
    return this.tickerStockData.get(lastEntry);
  }

  /**
   * Logs the action of gettingPortfolio values.
   *
   * @param unit          A date-based unit of time.
   * @param portfolioName The portfolio name to be used.
   * @param lastEntry     The date of the last entry. if not available, the latest earlier date
   *                      still within the same unit will be used.
   * @param size          the size of the map to be returned. Weekends and holidays are skipped.
   * @param length        the length of each unit of time, such as 1 month or 3 month increments.
   * @return A map of dates to portfolio values.
   * @throws IllegalArgumentException if the portfolio does not exist.
   */

  @Override
  public SortedMap<LocalDate, Double> getPortfolioValues(ChronoUnit unit, String portfolioName,
                                                         LocalDate lastEntry, int size, int length)
          throws IllegalArgumentException {
    log.add("get portfolio values called with portfolioName:" + portfolioName);
    return null;
  }

  /**
   * Logs the action of isPortfolioPresent method call.
   *
   * @param portfolioName the name of the specified portfolio
   * @return
   */
  @Override
  public boolean isPortfolioPresent(String portfolioName) {
    log.add("is portfolio present called with portfolioName:" + portfolioName);
    return (existingPortfolios.contains(portfolioName.toLowerCase()) ||
            mutablePortfolio.contains(portfolioName.toLowerCase()));
  }

  /**
   * Logs the action of addDollarCostAveragingPortfolio method call.
   *
   * @param portfolioName     the name of the portfolio.
   * @param investmentAmounts The amount in dollars that each stock transaction should be
   * @param startDate         the date of the first transaction.
   * @param unit              the unit of time between transactions.
   * @param length            the length of time between transactions.
   * @param reps              the number of times the stocks are bought.
   *                          Must be a positive number of -1 to signify an infinite number
   *                          of repetitions.
   * @throws IllegalArgumentException if the portfolio already exists.
   */

  @Override
  public void addDollarCostAveragingPortfolio(String portfolioName,
                                              Map<String, Double> investmentAmounts,
                                              LocalDate startDate, ChronoUnit unit, int length,
                                              int reps) throws IllegalArgumentException {
    this.addExistingPortfolio(portfolioName);
    log.add(String.format("added DCA with: "
                    + "name: %s, split: %s, startDate: %s,"
                    + " unit: %s, length: %d, reps: %d",
            portfolioName, investmentAmounts, startDate, unit, length, reps));
    if (portfolioName.equalsIgnoreCase("exception")) {
      log.add("throw exception");
      throw new IllegalArgumentException("set to throw exception");

    }
  }

  /**
   * Logs the action of getDCAPortfolio method call.
   *
   * @return the list of portfolios that have DCA operations.
   */

  @Override
  public String[] getDCAPortfolios() {
    return new String[0];
  }

  /**
   * Logs the action of getDCAComposition method call.
   *
   * @param portfolioName the portfolio name.
   * @return the set of DCA transactions.
   * @throws IllegalArgumentException if the portfolio does not exist.
   */

  @Override
  public Set<IRepeatingStockTransaction> getDCAComposition(String portfolioName)
          throws IllegalArgumentException {
    return null;
  }
}