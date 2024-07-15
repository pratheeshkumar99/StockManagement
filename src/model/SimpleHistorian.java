package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Function;

/**
 * Implementation of a historian that stores the
 * opening, closing, highs, lows, volume of stocks over time.
 * Only supports retrieving opening and closing prices.
 */
public class SimpleHistorian implements IHistorian {

  //field available to the historian to get values if it doesn't already have them.
  private final Function<String, Map<LocalDate, List<Double>>> getter;

  //private repository of open, high, low, close, volume for each date
  protected final Map<String, SortedMap<LocalDate, double[]>> stockArchive;

  //set of tickers that the historian doesn't need to re-search.
  private final Set<String> searchedTickers;

  /**
   * Public constructor for a simple historian.
   * @param getter a Call Back function to retrieve data if the historian does not have it.
   */
  public SimpleHistorian(Function<String, Map<LocalDate, List<Double>>> getter) {
    this.getter = getter;
    this.stockArchive = new TreeMap<>();
    this.searchedTickers = new HashSet<>();
  }

  @Override
  public double getValue(String ticker, LocalDate date, boolean atClosing)
      throws IllegalArgumentException {
    try {
      return Objects.requireNonNull(getValueWithUpdate(ticker, date, atClosing));
    } catch (NullPointerException e) {
      throw getException(ticker, date);
    }
  }

  @Override
  public void checkStock(IStock stock) throws IllegalArgumentException {
    if (getValueWithUpdate(stock.getName(), stock.getDate(), true) == null) {
      throw getException(stock.getName(), stock.getDate());
    }
  }

  @Override
  public Entry<LocalDate, Double> getLatestValueWithinRange(String ticker, LocalDate start,
      LocalDate end, boolean atClosing) throws IllegalArgumentException {

    updateHistorian(ticker);
    LocalDate dateIfError = LocalDate.MIN;
    LocalDate latestDate = stockArchive.get(ticker)
        .keySet()
        .stream()
        .filter(d -> (d.isBefore(end) || d.isEqual(end)) && d.isAfter(start))
        .max(LocalDate::compareTo).orElse(dateIfError);
    if (latestDate.isEqual(dateIfError)) {
      throw new IllegalArgumentException("Could not find value");
    } else {
      return new SimpleEntry<>(latestDate, retrieveLocalPrice(ticker, latestDate, atClosing));
    }
  }

  @Override
  public LocalDate getIPO(String ticker) throws IllegalArgumentException {
    if (stockArchive.containsKey(ticker)) {
      return stockArchive.get(ticker).firstKey();
    }
    updateHistorian(ticker);
    if (stockArchive.containsKey(ticker)) {
      return stockArchive.get(ticker).firstKey();
    } else {
      throw new IllegalArgumentException("Invalid ticker symbol");
    }
  }


  protected Double getValueWithUpdate(String ticker, LocalDate date, boolean atClosing) {
    if (hasPrice(ticker, date)) {
      return retrieveLocalPrice(ticker, date, atClosing);
    }
    updateHistorian(ticker);
    return hasPrice(ticker, date) ? retrieveLocalPrice(ticker, date, atClosing) : null;
  }

  private boolean hasPrice(String ticker, LocalDate date) {
    return stockArchive.containsKey(ticker) && stockArchive.get(ticker).containsKey(date);
  }

  private IllegalArgumentException getException(String ticker, LocalDate date) {
    if (stockArchive.containsKey(ticker) && stockArchive.get(ticker).containsKey(date)) {
      throw new IllegalStateException("getException called at wrong time");
    } else if (stockArchive.get(ticker).isEmpty()) {
      throw new IllegalStateException("Stock ticker is valid but has no stock prices available");
    } else if (stockArchive.get(ticker).firstKey().isAfter(date)) {
      String formattedDate = stockArchive.get(ticker).firstKey()
          .format(DateTimeFormatter.ofPattern("yyyy MMM dd"));
      return new IllegalArgumentException("Stock not yet listed. IPO date is " + formattedDate);
    } else if (stockArchive.get(ticker).lastKey().isBefore(date)
        && date.isBefore(LocalDate.now())) {
      String formattedDate = stockArchive.get(ticker).lastKey()
          .format(DateTimeFormatter.ofPattern("yyyy MMM dd"));
      return new IllegalArgumentException(
        String.format("Stock: %s was de-listed from the NYSE on %s", ticker, formattedDate));
    } else if (stockArchive.get(ticker).lastKey().isBefore(date)) {
      String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy MMM dd"));
      return new IllegalArgumentException("Stock price not yet listed for date: " + formattedDate);
    } else {
      return new IllegalArgumentException("Requested date on a market holiday or weekend");
    }
  }

  /**
   * Private helper method to return values known to be stored in the historian.
   *
   * @param ticker    the ticker symbol
   * @param date      the date of the value
   * @param atClosing if the value returned should be at atClosing (true), or closing (false)
   * @return the price of the stock
   */
  private double retrieveLocalPrice(String ticker, LocalDate date, boolean atClosing) {
    return getPrice(stockArchive.get(ticker).get(date), atClosing);
  }

  /**
   * Private method update the historian with information on a specific stock.
   *
   * @param ticker the ticker symbol the data should be added to.
   * @throws IllegalArgumentException if the ticker symbol is invalid.
   */
  private void updateHistorian(String ticker) throws IllegalArgumentException {
    Map<LocalDate, List<Double>> rawResult;
    if (searchedTickers.contains(ticker)) {
      searchedTickers.add(ticker);
      return; //skip processing for tickers already searched.
    }
    searchedTickers.add(ticker);
    try {
      rawResult = getter.apply(ticker);
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid Ticker Symbol");
    }
    if (rawResult == null) {
      throw new IllegalArgumentException("unable to retrieve online data for stock: " + ticker);
    }

    SortedMap<LocalDate, double[]> arrResult = new TreeMap<>();
    for (Entry<LocalDate, List<Double>> entry : rawResult.entrySet()) {
      List<Double> listValues = entry.getValue();
      if (listValues.size() != 5) {
        throw new IllegalStateException("provided null or malformed value(s)");
      }
      //TODO: this converts Map<LocalDate, List<Double>> to SortedMap<LocalDate, double[]>.
      // In the future we should get data natively in an immutable array of primitives.
      double[] arrValues = entry.getValue().stream().mapToDouble(d -> d).toArray();
      arrResult.put(entry.getKey(), arrValues);
    }
    if (! this.stockArchive.containsKey(ticker)) {
      stockArchive.put(ticker, arrResult);
    } else {
      stockArchive.get(ticker).putAll(arrResult);
    }
  }

  protected static double getPrice(double[] arr, boolean atClosing) {
    return (atClosing) ? arr[3] : arr[0];
  }
}
