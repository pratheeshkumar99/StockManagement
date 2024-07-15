package modeltest.stocktest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import model.FlexibleHistorianImpl;
import model.IFlexibleHistorian;
import model.IRepeatingStockTransaction;
import model.IStock;
import model.RepeatingStock;
import model.Stock;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the methods of the repeating stock class.
 */
public class RepeatingStockTest {

  private final static String ticker = "TSLA";
  private final static double dollarValue = 25;
  private final static LocalDate date = LocalDate.of(2024, 3, 1);
  private final static int length = 1;
  private final static int reps = 3;

  private static final Function<String, Map<LocalDate, List<Double>>> testGetter = s -> {
    if (s.equalsIgnoreCase("IllegalArgumentException")) {
      throw new IllegalArgumentException("InvalidTicker");
    }
    Map<LocalDate, List<Double>> result = new TreeMap<>();
    result.put(LocalDate.of(2024, 3, 21),
        List.of(12.4, 13.2, 14.5, 15.6, 16.7));
    result.put(LocalDate.of(2024, 3, 20),
        List.of(1.65, 2.54, 3.32, 4.5, 5.08));
    result.put(LocalDate.of(2024, 3, 19),
        List.of(43.45, 4324.23, 654.23, 324.52, 432432.43));
    result.put(LocalDate.of(2024, 3, 18),
        List.of(146.65, 2567.54, 334.32, 4345.5, 567.08));
    result.put(LocalDate.of(2024, 3, 15),
        List.of(1452.4, 13543.2, 14768.5, 100.0, 1836.7));
    result.put(LocalDate.of(2024, 3, 14),
        List.of(891.65, 768.54, 353.32, 549.43, 87.43));
    result.put(LocalDate.of(2024, 3, 13),
        List.of(17892.4, 54654.2, 4534.5, 15.6, 16.7));
    result.put(LocalDate.of(2024, 3, 12),
        List.of(3761.65, 3672.54, 345.32, 50.0, 455.08));
    result.put(LocalDate.of(2024, 3, 1),
        List.of(1.2, 2.3, 3.4, 5.0, 5.6));
    result.put(LocalDate.of(2024, 2, 29),
        List.of(1.1, 2.2, 3.5,4.3,5.0));
    result.put(LocalDate.of(2024, 1, 29),
        List.of(1.1, 2.2, 3.5,4.3,5.0));
    result.put(LocalDate.of(2023, 12, 31),
        List.of(1.1, 2.2, 3.5,4.3,5.0));
    return result;
  };

  private ChronoUnit unit;
  private IRepeatingStockTransaction r;
  private Set<IStock> set;
  private IFlexibleHistorian historian;

  @Before
  public void setUp() {
    unit = ChronoUnit.WEEKS;
    r = new RepeatingStock(ticker, dollarValue, date, length, unit, reps);
    historian = new FlexibleHistorianImpl(testGetter);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConstructorWrongUnit() {
    r = new RepeatingStock(ticker, dollarValue, date, length, ChronoUnit.HOURS, reps);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConstructorWrongRepetitions() {
    r = new RepeatingStock(ticker, dollarValue, date, length, unit, 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConstructorNegRepetitions() {
    r = new RepeatingStock(ticker, dollarValue, date, length, unit, -3);
  }

  @Test
  public void testGetStocksBetweenFinite() {
    set = r.getStocksBetween(date, date.plusDays(1), historian);
    assertEquals(1, set.size());
    IStock first = new Stock(ticker, 5.0, date);
    assertTrue(set.contains(first));
    IStock second = new Stock(ticker, .5, LocalDate.of(2024, 3, 12));
    IStock third = new Stock(ticker, .25, LocalDate.of(2024, 3, 15));
    IStock notFound = new Stock(ticker, 5.0, LocalDate.of(2024, 5, 3));
    set = r.getStocksBetween(date, date.plusMonths(1), historian);
    assertEquals(reps, set.size());
    assertTrue(set.contains(first));
    assertTrue(set.contains(second));
    assertTrue(set.contains(third));
    assertFalse(set.contains(notFound));
  }

  @Test
  public void testGetStocksBetweenNoEnd() {
    r = new RepeatingStock(ticker, dollarValue, date, length, unit, -1);
    set = r.getStocksBetween(date, date.plusWeeks(2), historian);
    IStock first = new Stock(ticker, 5.0, date);
    IStock second = new Stock(ticker, .5, LocalDate.of(2024, 3, 12));
    IStock third = new Stock(ticker, .25, LocalDate.of(2024, 3, 15));
    assertEquals(3, set.size());
    assertTrue(set.contains(first));
    assertTrue(set.contains(second));
    assertTrue(set.contains(third));
  }

  @Test
  public void testGetStockBetweenOutsideRange() {
    set = r.getStocksBetween(date.minusYears(1), date.minusMonths(6), historian);
    assertTrue(set.isEmpty());
    set = r.getStocksBetween(date.plusYears(1), date.plusYears(2), historian);
    assertTrue(set.isEmpty());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetStocksBetweenStartAfterEnd() {
    r.getStocksBetween(date.plusDays(1), date, historian);
  }
}
