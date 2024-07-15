package view.text;

import java.time.LocalDate;
import java.util.SortedMap;

/**
 * An extension of the original InvestmentSimulationView to support showing buy/sell dates for
 * stocks, stock values, and plotting the performance of a stock/portfolio.
 */
public interface IGraphingView extends IView {

  /**
   * Plot the performance of an asset over a number of dates in a text-based way. Utilize the "*"
   * character to display value and scale appropriately. Specify a relative or absolute scale at the
   * bottom Note the timestamps on the left of each value at a consistent width The max number of
   * asterisks in any one line is 50 Specify the time range and name at the top.
   *
   * @param values the values to be displayed.
   * @param name   the name of the plot.
   * @throws IllegalArgumentException if any values are negative.
   */
  void plotPerformance(SortedMap<LocalDate, Double> values, String name)
      throws IllegalArgumentException;

  /**
   * Display the buy/sell dates for a particular asset over a specified length of time.
   *
   * @param dates       A sorted map of dates and whether one should buy (true) or sell (false).
   * @param title       the name of the asset.
   * @param description an accompanying description.
   */
  void showBuySellDates(SortedMap<LocalDate, Boolean> dates, String title, String description);

  /**
   * show the net profits or losses of a particular asset (either Portfolio or Stock) over a given
   * period of time.
   *
   * @param start      the starting date
   * @param end        the ending date
   * @param asset      the name of the asset. A portfolio can be displayed as is, but a stock should
   *                   use both the publicly traded name and ticker symbol, such as "Apple Inc.
   *                   (AAPL)".
   * @param startValue the starting value
   * @param endValue   the ending value
   * @throws IllegalArgumentException if the start date is after the end date
   */
  void showNet(LocalDate start, LocalDate end, String asset, double startValue, double endValue)
      throws IllegalArgumentException;


  /**
   * Show the cost basis of an asset (either a portfolio or stock) on a given date.
   *
   * @param date  the date to be displayed.
   * @param asset the name of the asset. A portfolio can be displayed as is, but a stock should use
   *              both the publicly traded name and ticker symbol, such as "Apple Inc. (AAPL)".
   * @param value the cost basis of the stock.
   */
  void showCostBasis(LocalDate date, String asset, double value);


  /**
   * Display the main menu for the user to interact with.
   */
  void displayMainMenu();
}
