package model;

import java.time.LocalDate;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

/**
 * An upgraded version of the Historian that supports an additional method stock data.
 */
public class FlexibleHistorianImpl extends SimpleHistorian implements IFlexibleHistorian {

  /**
   * Public constructor for a simple historian.
   *
   * @param getter a Call Back function to retrieve data if the historian does not have it.
   */
  public FlexibleHistorianImpl(
      Function<String, Map<LocalDate, List<Double>>> getter) {
    super(getter);
  }

  @Override
  public Entry<LocalDate, Double> getEarliest(String ticker, LocalDate date, boolean atClosing)
      throws IllegalArgumentException {


    //try to get the price with update
    Double val = getValueWithUpdate(ticker, date, atClosing);
    if (val != null) {
      return new SimpleEntry<>(date, val);
    } else if (!this.stockArchive.containsKey(ticker)) {
      throw new IllegalArgumentException("Ticker invalid");
    } else {
      try {
        LocalDate d = this.stockArchive.get(ticker).tailMap(date).firstKey();
        return new SimpleEntry<>(d, getPrice(this.stockArchive.get(ticker).get(d), atClosing));
      } catch (Exception e) {
        throw new IllegalArgumentException("No later dates available.");
      }
    }

  }
}
