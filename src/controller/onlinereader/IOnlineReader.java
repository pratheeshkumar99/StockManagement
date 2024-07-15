package controller.onlinereader;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Represents an online reader which can get the data of a stock on a certain date.
 */

public interface IOnlineReader {

  /**
   * This method fetches the stock data from the alphavantage API.
   *
   * @param ticker the ticker symbol of the stock.
   * @return a map of the stock data.
   * @throws IllegalArgumentException if the API call is invalid or the data is not found.
   */
  Map<LocalDate, List<Double>> getData(String ticker) throws IllegalArgumentException;


}
