package modeltest.historiantest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import model.IHistorian;
import model.SimpleHistorian;
import model.IStock;
import model.Stock;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the methods of the simple historian class.
 */
public class SimpleHistorianTest {

  private IHistorian h;
  private final LocalDate validDate = LocalDate.of(2024, 3, 21);
  private final LocalDate earlyDate = LocalDate.of(2023, 12, 31);
  private final LocalDate futureDate = LocalDate.now().plusDays(3);
  private final LocalDate deListedDate = LocalDate.of(2024, 3, 23);
  private final LocalDate holiday = LocalDate.of(2024, 3, 17);
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
    h = new SimpleHistorian(testGetter);
  }

  @Test
  public void testGetValueValidClosing() {
    double expected = 15.6;
    double actual = h.getValue("NFLX", validDate, true);
    assertEquals(expected, actual, 0.001);
  }

  @Test
  public void testGetValueValidOpening() {
    double expected = 12.4;
    double actual = h.getValue("NFLX", validDate, false);
    assertEquals(expected, actual, 0.001);
  }

  @Test
  public void testGetValueTickerInvalid() {
    Exception e = assertThrows(IllegalArgumentException.class,
        () -> h.getValue("IllegalArgumentException", validDate, true));
    String expected = "Invalid Ticker Symbol";
    String actual = e.getMessage();
    assertEquals(expected, actual);
  }

  @Test
  public void testGetValueTooEarly() {
    Exception e = assertThrows(IllegalArgumentException.class,
        () -> h.getValue("NFLX", earlyDate, true));
    String expected = "Stock not yet listed. IPO date is 2024 Mar 12";
    String actual = e.getMessage();
    assertEquals(expected, actual);
  }

  @Test
  public void testGetValueFuture() {
    Exception e = assertThrows(IllegalArgumentException.class,
        () -> h.getValue("NFLX", futureDate, true));
    String formattedDate = futureDate.format(DateTimeFormatter.ofPattern("yyyy MMM dd"));
    String expected = "Stock price not yet listed for date: " + formattedDate;
    String actual = e.getMessage();
    assertEquals(expected, actual);
  }

  @Test
  public void testGetValueHoliday() {
    Exception e = assertThrows(IllegalArgumentException.class,
        () -> h.getValue("NFLX", holiday, true));
    String expected = "Requested date on a market holiday or weekend";
    String actual = e.getMessage();
    assertEquals(expected, actual);
  }

  @Test
  public void testGetValueDeListed() {
    Exception e = assertThrows(IllegalArgumentException.class,
        () -> h.getValue("NFLX", deListedDate, true));
    String expected = "Stock: NFLX was de-listed from the NYSE on 2024 Mar 21";
    String actual = e.getMessage();
    assertEquals(expected, actual);
  }

  @Test
  public void testCheckStockValid() {
    IStock validStock = new Stock("NFLX", 30, validDate);
    try {
      h.checkStock(validStock);
    } catch (IllegalArgumentException e) {
      fail();
    }
  }

  @Test
  public void testCheckStockTickerInvalid() {
    IStock invalidTicker = new Stock("IllegalArgumentException", 30, validDate);
    Exception e = assertThrows(IllegalArgumentException.class, () -> h.checkStock(invalidTicker));
    String expected = "Invalid Ticker Symbol";
    String actual = e.getMessage();
    assertEquals(expected, actual);
  }

  @Test
  public void testCheckStockBeforeIPO() {
    IStock invalidTicker = new Stock("NFLX", 30, earlyDate);
    Exception e = assertThrows(IllegalArgumentException.class, () -> h.checkStock(invalidTicker));
    String expected = "Stock not yet listed. IPO date is 2024 Mar 12";
    String actual = e.getMessage();
    assertEquals(expected, actual);
  }

  @Test
  public void testCheckStockFuture() {
    IStock invalidTicker = new Stock("NFLX", 30, futureDate);
    Exception e = assertThrows(IllegalArgumentException.class, () -> h.checkStock(invalidTicker));
    String formattedDate = futureDate.format(DateTimeFormatter.ofPattern("yyyy MMM dd"));
    String expected = "Stock price not yet listed for date: " + formattedDate;
    String actual = e.getMessage();
    assertEquals(expected, actual);
  }

  @Test
  public void testCheckStockHoliday() {
    IStock invalidTicker = new Stock("NFLX", 30, holiday);
    Exception e = assertThrows(IllegalArgumentException.class, () -> h.checkStock(invalidTicker));
    String expected = "Requested date on a market holiday or weekend";
    String actual = e.getMessage();
    assertEquals(expected, actual);
  }

  @Test
  public void testCheckValueDeListed() {
    IStock deListedStock = new Stock("NFLX", 30, deListedDate);
    Exception e = assertThrows(IllegalArgumentException.class,
        () -> h.checkStock(deListedStock));
    String expected = "Stock: NFLX was de-listed from the NYSE on 2024 Mar 21";
    String actual = e.getMessage();
    assertEquals(expected, actual);
  }

  @Test
  public void testGetValueWithAlt() {
    //if a value is there on the given date return it
    double actual = h.getLatestValueWithinRange("", validDate.minusDays(1),
        validDate, true).getValue();
    double expected = 15.6;
    assertEquals(expected, actual, 0.001);

    //if a value is on the second-closest date, return it
    actual = h.getLatestValueWithinRange("", validDate.minusDays(6),
        validDate.plusDays(1),true).getValue();
    assertEquals(expected, actual, 0.001);

    //if a there are no values in the given window, throw an error
    Exception e = assertThrows(IllegalArgumentException.class, () ->
        h.getLatestValueWithinRange("", validDate.minusYears(1000),
        validDate.minusYears(20), true));
    String expectedErrorMessage = "Could not find value";
    String actualErrorMessage = e.getMessage();
    assertEquals(expectedErrorMessage, actualErrorMessage);
  }

  @Test
  public void testGetValueWithAltException() {
    Exception e = assertThrows(IllegalArgumentException.class,
        () -> h.getLatestValueWithinRange(
          "IllegalArgumentException", validDate, validDate, true));
    String expected = "Invalid Ticker Symbol";
    String actual = e.getMessage();
    assertEquals(expected, actual);
  }

  @Test
  public void testGetIPOValid() {
    LocalDate actual = h.getIPO("");
    LocalDate expected = LocalDate.of(2024, 3, 12);
    assertEquals(actual, expected);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetIPOInvalidTicker() {
    h.getIPO("IllegalArgumentException");
  }
}
