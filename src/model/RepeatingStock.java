package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Implementation of a Repeating stock.
 */
public class RepeatingStock implements IRepeatingStockTransaction {
  private final int length;
  private final ChronoUnit unit;
  private final int repetitions;
  private final String ticker;
  private final double quantity;
  private final LocalDate startDate;


  /**
   * Public constructor for a Repeating Stock.
   * @param name the stock's ticker.
   * @param quantity the quantity of stock to be bought/sold at each transaction.
   * @param date the starting date.
   * @param length the length of time between transactions.
   * @param unit the unit of time to measure the length between transactions.
   * @param repetitions the number of occurances. If infinite, this is -1.
   */
  public RepeatingStock(String name, double quantity, LocalDate date, int length, ChronoUnit unit,
      int repetitions) {

    if (!unit.isDateBased()) {
      throw new IllegalArgumentException("Unit must be date based. Provided: " + unit);
    } else if (repetitions < 1 && repetitions != -1) {
      throw new IllegalArgumentException("Repetitions must be positive or -1. Provided: "
                                           + repetitions);
    }
    this.length = length;
    this.unit = unit;
    this.repetitions = repetitions;
    this.ticker = name;
    this.quantity = quantity;
    this.startDate = date;
  }

  @Override
  public Set<IStock> getStocksBetween(LocalDate start, LocalDate end, IFlexibleHistorian historian)
      throws IllegalArgumentException {
    if (end.isBefore(start)) {
      throw new IllegalArgumentException("End date cannot be before the start date");
    }
    Set<IStock> result = new HashSet<>();
    LocalDate date = this.startDate;
    Predicate<LocalDate> inRange = (d -> (d.isEqual(start) || d.isAfter(start))
                                           && (d.isEqual(end) || d.isBefore(end)));
    Predicate<Integer> withinCount = (i -> repetitions == -1 || i < repetitions);
    while (inRange.test(date) && withinCount.test(result.size())) {
      try {
        Entry<LocalDate, Double> entry = historian.getEarliest(ticker, date, true);
        result.add(new Stock(ticker, quantity / entry.getValue(), entry.getKey()));
      } catch (IllegalArgumentException ignored) {       }
      date = date.plus(length, unit);
    }
    return result;
  }

  @Override
  public LocalDate getDate() {
    return this.startDate;
  }
}
