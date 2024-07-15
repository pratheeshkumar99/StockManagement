package model;

import java.time.LocalDate;
import java.util.Map.Entry;

/**
 * Represents a historian which tracks in memory the historical prices of different stocks.
 */
public interface IHistorian {

  /**
   * Get the value of a stock.
   *
   * @param ticker    the ticker symbol of the stock.
   * @param date      the date of the stock.
   * @param atClosing if the price should be the closing price (true) or opening price (false).
   * @return the price of the stock.
   * @throws IllegalArgumentException if the ticker is invalid or the date is out of range of
   *                                  publicly available stock data.
   */
  double getValue(String ticker, LocalDate date, boolean atClosing) throws IllegalArgumentException;

  /**
   * Determine if a given stock is valid.
   *
   * @param stock some stock.
   * @throws IllegalArgumentException if the ticker symbol is invalid, or if the stock was not
   *                                  offered publicly at the given purchase date or if the validity
   *                                  of the stock could not be determined.
   */
  void checkStock(IStock stock) throws IllegalArgumentException;

  /**
   * Get the latest value of the ticker within the given date range.
   *
   * @param ticker    The ticker symbol to check.
   * @param start     the minimum boundary (exclusive).
   * @param end       the maximum boundary (inclusive).
   * @param atClosing whether the value should be the closing price (true) or opening price
   *                  (false).
   * @return the price of the stock at the latest date available.
   * @throws IllegalArgumentException If there are no values available in the window.
   */
  Entry<LocalDate, Double> getLatestValueWithinRange(String ticker, LocalDate start, LocalDate end,
      boolean atClosing) throws IllegalArgumentException;

  /**
   * Get the date of Initial Public Offering (IPO) for a given stock ticker.
   *
   * @param ticker the NYSE ticker Symbol.
   * @return The date the ticker was first publicly traded.
   * @throws IllegalArgumentException if the ticker symbol is invalid.
   */
  LocalDate getIPO(String ticker) throws IllegalArgumentException;
}
