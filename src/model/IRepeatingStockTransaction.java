package model;

import java.time.LocalDate;
import java.util.Set;

/**
 * Represents a repeating stock transaction. This doesn't extend the IStock interface as there is no
 * equivalency between the two.
 */
public interface IRepeatingStockTransaction {

  /**
   * Get the set of stock transactions of this that are set to occur between the specified dates.
   *
   * @param start the starting date (inclusive).
   * @param end   the ending date (inclusive).
   * @return all stock transactions that without adjustment occur between the two dates. The final
   *         result may include results outside the original range,
   *         in cases where a stock is set to occur on a Market Holiday,
   *         but the earliest Market Day afterward is outside the range.
   * @throws IllegalArgumentException if the start date is after the end date.
   */
  Set<IStock> getStocksBetween(LocalDate start, LocalDate end, IFlexibleHistorian historian)
      throws IllegalArgumentException;

  /**
   * Get the starting date of this stock transaction.
   *
   * @return the starting date.
   */
  LocalDate getDate();
}
