package utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * General purpose class with functionality for operations across all MVC components.
 */
public class Utils {

  /**
   * Determine the most appropriate unit of time to request information for, given a date range.
   *
   * @param start the start date.
   * @param end   the end date.
   * @param min   a minimum number of units between the start and end.
   * @param max   a maximum number of units between the start and end.
   * @return A unit of time such that the difference between two dates in that unit is between the
   *         min and max.
   * @throws IllegalStateException if the start date is equal to or after the end date, or there are
   *                               no applicable units.
   */

  public static Entry<ChronoUnit, Integer> getAppropriateUnit(LocalDate start, LocalDate end,
                                                              int min, int max)
          throws IllegalStateException {

    if (start.isEqual(end) || start.isAfter(end)) {
      throw new IllegalStateException("Start date cannot the same or after the end date");
    } else if (min >= max || min < 1) {
      throw new IllegalStateException("Min must be less than max. Both must be positive.");
    }
    List<ChronoUnit> units = List.of(ChronoUnit.DAYS, ChronoUnit.WEEKS,
            ChronoUnit.MONTHS, ChronoUnit.YEARS);
    Predicate<ChronoUnit> fits = (u -> u.between(start, end) <= max
            && u.between(start, end) >= min);

    ChronoUnit unit = null;
    int length = 1; //default to 1
    try {
      unit = units.stream().filter(fits).findFirst().orElseThrow();
    } catch (NoSuchElementException e) {
      //If there is no good single unit, then we need to calculate again in multiple units
      BiPredicate<ChronoUnit, Integer> fitsMultiple = ((u, i) ->
              u.between(start, end) <= (long) max * i && u.between(start, end) >= (long) min * i);
      for (int l = 0; l < 300; l++) {
        int finalL = l;
        Predicate<ChronoUnit> fitsWithUnit = (u -> fitsMultiple.test(u, finalL));
        //find the smallest unit that fits
        unit = units.stream().filter(fitsWithUnit).findFirst().orElse(null);
        if (unit != null) {
          break;
        }
      }
    }
    return new SimpleEntry<>(unit, length);
  }

  /**
   * Get the latest weekday that is at least the length (with unit) prior to a given start date.
   *
   * @param start the given date. Can be any date of the week.
   * @param unit  a date-based unit of time.
   * @param length the number of units from the start date.
   * @return the latest day that is still one unit of time prior. For example, the output of
   *         26March, Days, 1 is 25March. The output of 26March, Weeks, 2 is 16March.
   */
  public static LocalDate lastWeekdayOfPreviousUnit(LocalDate start, ChronoUnit unit, int length) {
    if (!unit.isDateBased()) {
      throw new IllegalStateException("Only date based units are supported. Provided: " + unit);
    } else if (length <= 0) {
      throw new IllegalStateException("Length must be positive. Provided length: " + length);
    }
    LocalDate end;
    end = start.minus(length, unit);

    switch (unit) {
      case MONTHS:
        end = end.withDayOfMonth(end.lengthOfMonth());
        break;
      //If the unit is a multiple of a year, set the end to the last day of that year
      case YEARS:
      case DECADES:
      case CENTURIES:
        end = end.withDayOfYear(end.lengthOfYear());
        break;
      //if the unit is a week, set the end to that friday
      case WEEKS:
        end = end.plusDays(6 - end.getDayOfWeek().getValue());
        break;
      default:
        //just continue processing without further modification
    }
    //if the end result is a saturday or sunday, set it to the previous friday
    if (end.getDayOfWeek() == DayOfWeek.SATURDAY) {
      end = end.minusDays(1);
    } else if (end.getDayOfWeek() == DayOfWeek.SUNDAY) {
      end = end.minusDays(2);
    }
    return end;
  }

  /**
   * Determine if a date is in a given range.
   * @param date the date being tested.
   * @param start the start date (inclusive).
   * @param end the end date (inclusive).
   * @return true if the date is within the range. False otherwise.
   */
  public static boolean inRange(LocalDate date, LocalDate start, LocalDate end) {
    return (start.isBefore(date) || start.isEqual(date))
             && (end.isAfter(date) || end.isEqual(date));
  }

  /**
   * Format a given double as a dollar about.
   * @param value the value to be formatted.
   * @return the dollar-formatted value.
   */
  public static String formatDollarValue(double value) {
    DecimalFormat valueFormatter = new DecimalFormat("$#,##0.00");
    valueFormatter.setRoundingMode(RoundingMode.HALF_UP);
    return valueFormatter.format(value);
  }
}
