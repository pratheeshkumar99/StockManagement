package modeltest.historiantest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.function.Function;
import model.FlexibleHistorianImpl;
import model.IFlexibleHistorian;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the methods of the FlexibleHistorian class.
 */
public class FlexibleHistorianTest {

  private IFlexibleHistorian historian;
  private Entry<LocalDate, Double> actual;
  private static final Function<String, Map<LocalDate, List<Double>>> testGetter = s -> {
    if (s.equalsIgnoreCase("IllegalArgumentException")) {
      throw new IllegalArgumentException("Ticker Invalid");
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
        List.of(1452.4, 13543.2, 14768.5, 15345.6, 1836.7));
    result.put(LocalDate.of(2024, 3, 14),
        List.of(891.65, 768.54, 353.32, 549.43, 87.43));
    result.put(LocalDate.of(2024, 3, 13),
        List.of(17892.4, 54654.2, 4534.5, 15.6, 16.7));
    result.put(LocalDate.of(2024, 3, 12),
        List.of(3761.65, 3672.54, 345.32, 3454.5, 455.08));
    return result;
  };

  @Before
  public void setUp() {
    historian = new FlexibleHistorianImpl(testGetter);
  }

  @Test
  public void testGetEarliestOnDate() {
    LocalDate d = LocalDate.of(2024, 3, 15);
    actual = historian.getEarliest(" ", d, true);
    assertEquals(d, actual.getKey());
    assertEquals(15345.6, actual.getValue(), 0.0001);
  }

  @Test
  public void testGetEarliestLaterDate() {
    LocalDate d = LocalDate.of(2024, 3, 16);
    actual = historian.getEarliest(" ", d, true);
    assertEquals(d.plusDays(2), actual.getKey());
    assertEquals(4345.5, actual.getValue(), 0.0001);
    actual = historian.getEarliest(" ", d, false);
    assertEquals(146.65, actual.getValue(), 0.0001);
  }

  @Test
  public void testGetEarliestInvalidTicker() {
    LocalDate d = LocalDate.of(2024, 3, 16);
    Exception e = assertThrows(IllegalArgumentException.class,
        () -> historian.getEarliest("IllegalArgumentException", d, true));
    assertEquals("Invalid Ticker Symbol", e.getMessage());
  }

  @Test
  public void testGetEarliestNoLaterDays() {
    LocalDate d = LocalDate.of(2024, 3, 23);
    Exception e = assertThrows(IllegalArgumentException.class,
        () -> historian.getEarliest(" ", d, true));
    assertEquals("No later dates available.", e.getMessage());
  }
}
