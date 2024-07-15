package model;

import java.time.LocalDate;
import java.util.Map.Entry;

/**
 * Historian that provides additional searching functionality.
 */
public interface IFlexibleHistorian extends IHistorian {

  /**
   * Get the earliest date and price for a specified date for which a given ticker was traded.
   * @param ticker the ticker symbol
   * @param date the date to start searching for.
   * @param atClosing whether the closing (true) or opening (false) price should be returned.
   * @return a combination of the earliest day and its appropriate price.
   * @throws IllegalArgumentException if the ticker symbol is invalid or there are no days on
   *                                  that day or beyond for which the ticker was traded.
   */
  Entry<LocalDate, Double> getEarliest(String ticker, LocalDate date, boolean atClosing)
      throws IllegalArgumentException;

}
