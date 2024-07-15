package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Set;

/**
 * Represents an investment simulation model that supports repeat transactions.
 */
public interface OngoingTransactionModel extends IHistorianPortfolioManager {
  /**
   * Create a portfolio with repeating stock transactions.
   * @param portfolioName the name of the portfolio.
   * @param investmentAmounts The amount in dollars that each stock transaction should be
   * @param startDate the date of the first transaction.
   * @param unit the unit of time between transactions.
   * @param length the length of time between transactions.
   * @param reps the number of times the stocks are bought.
   *             Must be a positive number of -1 to signify an infinite number of repetitions.
   * @throws IllegalArgumentException if any of the following are met:
   *                                  <ul>
   *                                    <li>the portfolio name is already taken</li>
   *                                    <li>Any of the stock tickers are invalid</li>
   *                                    <li>any of the investment amounts are not positive.</li>
   *                                    <li>the unit is not date-based</li>
   *                                    <li>the number of repetitions is not positive or 01</li>
   *                                  </ul>
   */
  void addDollarCostAveragingPortfolio(String portfolioName, Map<String, Double> investmentAmounts,
      LocalDate startDate, ChronoUnit unit, int length, int reps)
      throws IllegalArgumentException;


  /**
   * Get an array of PortfolioNames which utilize Dollar Cost Averaging.
   * @return a String array of all portfolios that contain at least one repeat stock transaction.
   */
  String[] getDCAPortfolios();

  /**
   * Get a representation of the contents in a Dollar Cost Averaging portfolio.
   * @param portfolioName the portfolio name.
   * @return the set of repeat stock purchases it is set to have.
   * @throws IllegalArgumentException if the stock name is not present in the model.
   */
  Set<IRepeatingStockTransaction> getDCAComposition(String portfolioName)
      throws IllegalArgumentException;
}
