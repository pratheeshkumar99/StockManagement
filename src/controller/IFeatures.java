package controller;

import java.io.File;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import view.text.IGraphingView;

/**
 * Represents a collection of features that the view can use.
 * Each feature is a callback function to the controller.
 */
public interface IFeatures {

  //Ben to test
  void setView(IGraphingView view);

  /**
   * Feature for a user to buy or sell stock in a mutable portfolio.
   *
   * @param ticker        the stock Ticker.
   * @param quantity      the quantity of the given stock. Must be a non-0 integer.
   * @param date          the date of the transaction.
   * @param portfolioName the name of the portfolio the transaction occurs in.
   */

  void buyStock(String ticker, String quantity, String date, String portfolioName);

  /**
   * Feature for a user to sell stock in a mutable portfolio.
   *
   * @param ticker        the stock Ticker.
   * @param quantity      the quantity of the given stock. Must be a non-0 integer.
   * @param date          the date of the transaction.
   * @param portfolioName the name of the portfolio the transaction occurs in.
   */

  void sellStock(String ticker, String quantity, String date, String portfolioName);

  /**
   * Allow the user to add a new portfolio to the simulation.
   *
   * @param portfolioName the portfolio name.
   * @return true if the operation was successful. False otherwise
   */

  boolean addPortfolio(String portfolioName);

  /**
   * Allow the user to calculate the crossover days for a given stock.
   * by its ticker using a 30-day moving average.
   *
   * @param ticker the ticker symbol.
   * @param start  the start date.
   * @param end    the end date.
   */

  void crossoverDays(String ticker, LocalDate start, LocalDate end);

  /**
   * Load a portfolio from some file.
   *
   * @param file the file to be added.
   * @return true if the operation was successful, false otherwise.
   */

  boolean loadPortfolio(File file);

  /**
   * Calculate a custom moving average for stock.
   *
   * @param ticker the stock's ticker.
   * @param xDay   the smaller bound of moving averages to calculate with.
   * @param yDay   the larger bound of moving averages to calculate with.
   * @param start  the starting date.
   * @param end    the ending date.
   */

  void movingCrossOverDays(String ticker, int xDay, int yDay, LocalDate start, LocalDate end);

  /**
   * Save a portfolio by name.
   *
   * @param portfolioName the portfolio's name
   * @param path          the path to save the portfolio to.
   */
  void savePortfolio(String portfolioName, String path);

  /**
   * Calculate an X-day moving average for a stock.
   *
   * @param ticker     the stock's ticker symbol.
   * @param xDayLength the length of the moving average, in number of days.
   * @param date       the date to calculate from.
   */

  void xDayMovingAverage(String ticker, int xDayLength, LocalDate date);

  /**
   * Calculate the cost basis of a portfolio on a given date.
   *
   * @param portfolioName the name of the portfolio.
   * @param date          a past date.
   */

  void costBasis(String portfolioName, String date);

  /**
   * Calculate the value of a portfolio on a given date.
   *
   * @param portfolioName the name of the portfolio.
   * @param date          a past date.
   */

  void portfolioValue(String portfolioName, String date);

  /**
   * Get the performance of a stock over a given time period.
   *
   * @param ticker the stock ticker symbol.
   * @param start  the start date.
   * @param end    the end date.
   */

  void stockPerformanceNet(String ticker, LocalDate start, LocalDate end);

  /**
   * Invest a fixed contribution amount in a given portfolio and split it into a number of stocks.
   *
   * @param portfolioName the portfolio name.
   * @param contribution  the total contribution amount.
   * @param date          the date the contribution was made on.
   * @param split         The percent of the contribution amount each stock gets by its ticker.
   *                      The sum must be 100. If a ticker is not present,
   *                      it is bought on the appropriate days.
   */

  void investFixedAmount(String portfolioName, double contribution, String date,
                         Map<String, Integer> split);

  /**
   * Create a new portfolio with a repeating dollar cost averaging operation.
   * If one of the regular transaction days is on a holiday,
   * the next available day is used to invest.
   *
   * @param portfolioName      the name of the portfolio.
   * @param dates              an Array of Dates, containing 1 or 2 values.
   * @param contributionAmount the repeating contribution amount.
   * @param split              how much each stock should be split by. The total must be 100.
   * @param unit               the unit of measurement between contribution events.
   * @param length             the length of time between each contribution event.
   * @return true if the operation is successful. False otherwise.
   */

  boolean dollarCostAverage(String portfolioName, LocalDate[] dates,
                            double contributionAmount, Map<String, Integer> split, ChronoUnit unit,
                            int length);

  /**
   * List the active portfolios.
   *
   * @return an Array of Strings, containing the names of portfolios managed by the model.
   */

  String[] listPortfolios();
}
