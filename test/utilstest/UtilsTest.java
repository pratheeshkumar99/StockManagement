package utilstest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import utils.Utils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

/**
 * Test the methods of DateUtils class.
 */
public class UtilsTest {

  ChronoUnit expected;
  ChronoUnit actual;
  LocalDate end;
  Entry<ChronoUnit, Integer> actualEntry;

  @Before
  public void setUp() {
    end = LocalDate.of(2024, 3, 27);
  }

  @Test
  public void testGetAppropriateUnit() {
    //Test that the smallest unit that exists between the given bounds and dates is returned.
    expected = ChronoUnit.DAYS;
    actualEntry = Utils.getAppropriateUnit(end.minusDays(4), end, 3, 50);
    assertEquals(expected, actualEntry.getKey());
    assertEquals(1, actualEntry.getValue().intValue());

    actualEntry = Utils.getAppropriateUnit(end.minusDays(50), end, 3, 51);
    assertEquals(expected, actualEntry.getKey());
    assertEquals(1, actualEntry.getValue().intValue());

    expected = ChronoUnit.WEEKS;
    actualEntry = Utils.getAppropriateUnit(end.minusDays(50), end, 2, 30);
    assertEquals(expected, actualEntry.getKey());
    assertEquals(1, actualEntry.getValue().intValue());

    actualEntry = Utils.getAppropriateUnit(end.minusMonths(12), end, 3, 20);
    expected = ChronoUnit.MONTHS;
    assertEquals(expected, actualEntry.getKey());
    assertEquals(1, actualEntry.getValue().intValue());

    actualEntry = Utils.getAppropriateUnit(end.minusMonths(100), end, 3, 20);
    expected = ChronoUnit.YEARS;
    assertEquals(expected, actualEntry.getKey());
    assertEquals(1, actualEntry.getValue().intValue());
  }

  @Test
  public void testGetUnit() {
    LocalDate l1 = LocalDate.of(2021, 3, 14);
    LocalDate l2 = LocalDate.of(2024, 3, 12);
    actualEntry = Utils.getAppropriateUnit(l1, l2, 5, 30);
    expected = ChronoUnit.MONTHS;
    assertEquals(expected, actualEntry.getKey());
    assertEquals(1, actualEntry.getValue().intValue());

  }

  @Test(expected = IllegalStateException.class)
  public void testGetAppropriateUnitStartEqualToEnd() {
    Utils.getAppropriateUnit(end, end, 3, 12);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetAppropriateUnitStartGreaterThanEnd() {
    Utils.getAppropriateUnit(end.plusDays(1), end, 3, 12);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetAppropriateUnitMinEqualToMax() {
    Utils.getAppropriateUnit(end.minusMonths(3), end, 12, 12);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetAppropriateUnitMinGreaterThanMax() {
    Utils.getAppropriateUnit(end.minusMonths(3), end, 13, 12);
  }

  @Test
  public void testGetLastDateOfPreviousUnit() {
    //test day
    LocalDate d = LocalDate.of(2024, 3, 4);
    LocalDate expected = LocalDate.of(2024, 3, 1);
    LocalDate actual = Utils.lastWeekdayOfPreviousUnit(d, ChronoUnit.DAYS, 2);
    assertEquals(expected, actual);
    //test weeks
    expected = LocalDate.of(2024, 2, 23);
    actual = Utils.lastWeekdayOfPreviousUnit(d, ChronoUnit.WEEKS, 2);
    assertEquals(expected, actual);
    //test months
    expected = LocalDate.of(2024, 1, 31);
    actual = Utils.lastWeekdayOfPreviousUnit(d, ChronoUnit.MONTHS, 2);
    assertEquals(expected, actual);
    //test years
    expected = LocalDate.of(2022, 12, 30);
    actual = Utils.lastWeekdayOfPreviousUnit(d, ChronoUnit.YEARS, 2);
    assertEquals(expected, actual);
    //test decades
    expected = LocalDate.of(2004, 12, 31);
    actual = Utils.lastWeekdayOfPreviousUnit(d, ChronoUnit.DECADES, 2);
    assertEquals(expected, actual);
    //test centuries
    expected = LocalDate.of(1824, 12, 31);
    actual = Utils.lastWeekdayOfPreviousUnit(d, ChronoUnit.CENTURIES, 2);
    assertEquals(expected, actual);
  }

  @Test
  public void testLastWeekdayOfPreviousUnit1Week() {
    LocalDate d = LocalDate.of(2024, 3, 21);
    LocalDate actual = Utils.lastWeekdayOfPreviousUnit(d, ChronoUnit.WEEKS, 1);
    LocalDate expected = LocalDate.of(2024, 3, 15);
    assertEquals(expected, actual);
  }

  @Test (expected = IllegalStateException.class)
  public void testLastWeekDayWrongLength() {
    LocalDate d = LocalDate.of(2024, 3, 1);
    Utils.lastWeekdayOfPreviousUnit(d, ChronoUnit.WEEKS, -2);
  }

  @Test (expected = IllegalStateException.class)
  public void testLastWeekDayWrongUnit() {
    LocalDate d = LocalDate.of(2024, 3, 1);
    Utils.lastWeekdayOfPreviousUnit(d, ChronoUnit.HOURS, -2);
  }

  @Test
  public void testInRange() {
    assertTrue(Utils.inRange(LocalDate.now(), LocalDate.MIN, LocalDate.MAX));
    assertTrue(Utils.inRange(LocalDate.now(), LocalDate.now(), LocalDate.now()));
    assertTrue(Utils.inRange(LocalDate.now(), LocalDate.now(), LocalDate.now().plusDays(1)));
    assertTrue(Utils.inRange(LocalDate.now(), LocalDate.now().minusDays(1), LocalDate.now()));
    assertFalse(Utils.inRange(LocalDate.now(),
        LocalDate.now().plusDays(1),
        LocalDate.now().plusDays(2)));
    assertFalse(Utils.inRange(LocalDate.now(),
        LocalDate.now().plusDays(10),
        LocalDate.now().minusDays(2)));
  }
}
