package model;

import controller.filewriter.IFileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.SortedMap;

/**
 * A Portfolio Manager which provides the basic functionality of managing multiple portfolios and
 * there composition but additionally stores historical stock prices for the stocks in its
 * portfolios. Supports methods for retrieving stock prices from CallBack function.
 */
public interface IHistorianPortfolioManager {

  /**
   * Get the value of this portfolio at a given point in time. This disregards stocks bought in the
   * future.
   *
   * @param portfolioName the name of the portfolio.
   * @param date          the date the value should be calculated with.
   * @return The portfolio value for all stocks bought and sold by this date.
   * @throws IllegalArgumentException if the name of the portfolio is invalid or if the date
   *                                  provided is on a day the stock exchange does not operate);
   */
  double getPortfolioValue(String portfolioName, LocalDate date) throws IllegalArgumentException;

  /**
   * Get the opening or closing price of the stock on a given date.
   *
   * @param tickerSymbol the NYSE ticker symbol of the stock.
   * @param date         the date of the stock.
   * @param atClosing    whether the opening (false) or closing (true) price is retrieved.
   * @return the price of the stock.
   * @throws IllegalArgumentException if the name or date are not valid, or if the stock was not yet
   *                                  publicly traded at the given date.
   */
  double getStockPrice(String tickerSymbol, LocalDate date, boolean atClosing)
      throws IllegalArgumentException;

  /**
   * Get the cost basis of the portfolio at a given date.
   *
   * @param portfolioName the name of the portfolio.
   * @param date          the date the cost basis is calculated with.
   * @return the dollar value iof all stocks purchased on a given date.
   * @throws IllegalArgumentException if the portfolio name is invalid.
   */
  double getCostBasis(String portfolioName, LocalDate date) throws IllegalArgumentException;

  /**
   * Determine if a given portfolio is mutable.
   *
   * @param portfolioName the name of the portfolio.
   * @return true if the portfolio is mutable, false if immutable.
   * @throws IllegalArgumentException if the name of the portfolio is invalid.
   */
  boolean isPortfolioMutable(String portfolioName) throws IllegalArgumentException;

  /**
   * List out the name of all portfolios in the portfolio manager.
   *
   * @return an array of portfolio names
   */
  String[] listPortfolioNames();

  /**
   * Get a set of stocks that make up a portfolio by name.
   *
   * @param portfolioName the name of the portfolio.
   * @return the stocks that make up the portfolio.
   * @throws IllegalArgumentException if the model does not have a portfolio of the provided name.
   */
  Set<IStock> getPortfolioComposition(String portfolioName) throws IllegalArgumentException;

  /**
   * Return a SortedMap of stock prices according to a unit of time specified.
   *
   * @param unit      A date-based unit of time.
   * @param ticker    A NYSE ticker symbol.
   * @param lastEntry The date of the last entry. If not available, the next earliest will be used.
   * @param maxSize   The maximum size of the map to be returned. If there are not enough values
   *                  available, a smaller map will be returned.
   * @param atClosing if the prices should be closing prices (true) or opening prices (false).
   * @param length    The length of each unit, such as 1 month vs 3 weeks.
   * @return A map of the stock prices. Each item will be on the last day of the respective unit.
   *         Units with no values contained are skipped
   *         (example weekends are skipped when the unit is days).
   * @throws IllegalArgumentException if the ticker symbol or the time unit is invalid.
   */
  SortedMap<LocalDate, Double> getStockPrices(ChronoUnit unit, String ticker,
      LocalDate lastEntry, int maxSize, boolean atClosing, int length)
      throws IllegalArgumentException;

  /**
   * Return a SortedMap of portfolio Values according to a unit of time specified.
   *
   * @param unit          A date-based unit of time.
   * @param portfolioName The portfolio name to be used.
   * @param lastEntry     The date of the last entry. if not available, the latest earlier date
   *                      still within the same unit will be used.
   * @param size          the size of the map to be returned. Weekends and holidays are skipped.
   * @param length        the length of each unit of time, such as 1 month or 3 month increments.
   * @return portfolio values. Each item will be on the last day of its respective unit.
   *         Units are not skipped, and instead default to a value of 0.
   * @throws IllegalArgumentException if the portfolio name the unit of time provided is invalid.
   */
  SortedMap<LocalDate, Double> getPortfolioValues(ChronoUnit unit, String portfolioName,
      LocalDate lastEntry, int size, int length) throws IllegalArgumentException;


  /**
   * Verifies whether a given portfolio is present.
   *
   * @param portfolioName the name of the specified portfolio
   * @return true if the portfolio is present, false if not
   */
  boolean isPortfolioPresent(String portfolioName);

  /**
   * Save the portfolio using the given writer.
   *
   * @param writer some writer object that is capable of writing the state of the model.
   * @throws IOException if the writer is unable to write to its destination successfully.
   * @throws IllegalArgumentException if the portfolioName is invalid.
   */
  void savePortfolio(IFileWriter writer, String portfolioName, String path)
      throws IOException, IllegalArgumentException;

  /**
   * Add the stock to the given portfolio.
   *
   * @param ticker        the stock to be added.
   * @param quantity      the quantity of stocks to be added. A negative quantity indicates stocks
   *                      sold.
   * @param date          the date of the transaction.
   * @param portfolioName the portfolio the stock should be added to.
   * @throws IllegalArgumentException for the following cases with a unique message:
   *                                  <ul>
   *                                    <li>the portfolio name is invalid</li>
   *                                    <li>stock ticker symbol is not registered in the NYSE</li>
   *                                    <li>the stock was not publicly traded on that day</li>
   *                                    <li>there are insufficient stocks available to be sold
   *                                    (stock set to be sold in the future is considered)</li>
   *                                    <li>the portfolio is set to be immutable</li>
   *                                  </ul>
   * @throws IOException              if the validity of the stock by ticker symbol cannot be
   *                                  determined.
   */
  void addStock(String ticker, double quantity, LocalDate date, String portfolioName)
      throws IllegalArgumentException, IOException;

  /**
   * Make a mutable portfolio.
   *
   * @param portfolioName the portfolio name.
   * @throws IllegalArgumentException if the name is already taken.
   */
  void createPortfolio(String portfolioName) throws IllegalArgumentException;

  /**
   * If the portfolio was mutable, make it immutable. If it was immutable, make it mutable.
   *
   * @param portfolioName the name of the portfolio.
   * @throws IllegalArgumentException if the name is invalid.
   */
  void flipMutability(String portfolioName) throws IllegalArgumentException;

}
