package model;

import utils.Utils;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Provides a set of common static methods for calculations involving Sets of IStock.
 */
public class PortfolioUtils {

  /**
   * Return a set of ticker symbols found in a given portfolio's composition.
   *
   * @param  portfolio the portfolio to process.
   * @param start         A start date to filter by.
   * @param end           An end date to filter by.
   * @return a set of unique ticker symbols that make up the portfolio.
   */
  public static Set<String> getUniqueTickers(
      Set<IStock> portfolio, LocalDate start, LocalDate end) {
    return portfolio.stream()
             .filter(s -> Utils.inRange(s.getDate(), start, end))
             .map(IStock::getName)
             .collect(Collectors.toSet());
  }

  /**
   * Get the number of shares owned on a particular date.
   * @param ticker the ticker of the share to be analyzed.
   * @param date the date to be analyzed on.
   * @param portfolio the portfolio to be analyzed with.
   * @return the quantity of shares owned on that date.
   */
  public static double numSharesOnDate(String ticker, LocalDate date, Set<IStock> portfolio) {
    return portfolio
             .stream()
             .filter(s -> Utils.inRange(s.getDate(), LocalDate.MIN, date)
                            && s.getName().equals(ticker))
             .mapToDouble(IStock::getQuantity)
             .sum();
  }

  /**
   * Get the number of shares owned after all transactions are complete.
   * @param ticker the ticker to be analyzed.
   * @param portfolio the portfolio containing all transactions.
   * @return the quantity of shares owned after all transactions,
   *         and therefore are eligible to be sold.
   */
  public static double numSharesThroughout(String ticker, Set<IStock> portfolio) {
    return portfolio.stream()
             .filter(s -> s.getName().equals(ticker))
             .mapToDouble(IStock::getQuantity)
             .sum();
  }

}
