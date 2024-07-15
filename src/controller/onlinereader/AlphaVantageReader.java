package controller.onlinereader;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class implements the IOnlineReader interface and provides the functionality to read stock.
 */

public class AlphaVantageReader implements IOnlineReader {


  /**
   * This method fetches the stock data from the alphavantage API.
   *
   * @param ticker the ticker symbol of the stock.
   * @return a map of the stock data.
   */

  public Map<LocalDate, List<Double>> getData(String ticker) {

    String apiKey = "W0M1JOKC82EZEQA8";
    URL url;
    try {
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + ticker + "&apikey=" + apiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new IllegalArgumentException("the alphavantage API has either changed or "
              + "no longer works");
    }
    InputStream in;
    StringBuilder output = new StringBuilder();
    try {
      in = url.openStream();
      int b;
      while ((b = in.read()) != -1) {
        output.append((char) b);
        if (output.toString().equals("{"
                + "    \"Error Message\": \"Invalid API call. Please retry or visit the "
                + "documentation"
                + " (https://www.alphavantage.co/documentation/) for TIME_SERIES_DAILY.\"\n"
                + "}")) {

          throw new IllegalArgumentException("Unable to fetch data. Please retry again!");
        }
      }
    } catch (IOException e) {
      System.out.println("No data");
      throw new IllegalArgumentException("No price data found for " + ticker);
    }
    if (output.toString().equals("{"
            +
            "    \"Error Message\": \"Invalid API call. Please retry or visit the"
            +
            " documentation (https://www.alphavantage.co/documentation/) for"
            +
            " TIME_SERIES_DAILY.\"\n" +
            "}")) {
      throw new IllegalArgumentException("Invalid Stock Data! Please enter a valid Data!.");
    }
    Map<LocalDate, List<Double>> historicalData = new HashMap<>();
    for (String line : output.toString().split("\n")) {
      String[] values = line.split(",");
      if (values[0].equals("timestamp")) {
        continue;
      }
      List<Double> dayData = new ArrayList<>();
      for (int i = 1; i < values.length; i++) {
        dayData.add(Double.parseDouble(values[i]));
      }

      historicalData.put(LocalDate.parse(values[0]), dayData);
    }
    return historicalData;
  }


}