package modeltest.portfoliomanagertest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Function;
import model.HistorianModel;
import model.IHistorianPortfolioManager;
import model.IStock;
import model.Stock;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the methods fo the HistorianModel class.
 */
public class HistorianModelTest {
  private final static String VALID_PORTFOLIO = "port1";

  private IHistorianPortfolioManager manager;
  private final LocalDate checkDate = LocalDate.of(2024, 3, 21);
  private final LocalDate buyDate = LocalDate.of(2024, 3, 12);
  private double actualValue;
  private double expectedValue;
  private static final double DELTA = 0.00001;

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
        List.of(1452.4, 13543.2, 14768.5, 15345.6, 1836.7));
    result.put(LocalDate.of(2024, 3, 14),
        List.of(891.65, 768.54, 353.32, 549.43, 87.43));
    result.put(LocalDate.of(2024, 3, 13),
        List.of(17892.4, 54654.2, 4534.5, 15.6, 16.7));
    result.put(LocalDate.of(2024, 3, 12),
        List.of(3761.65, 3672.54, 345.32, 3454.5, 455.08));
    result.put(LocalDate.of(2024, 2, 29),
        List.of(1.1, 2.2, 3.5,4.3,5.0));
    result.put(LocalDate.of(2024, 1, 29),
        List.of(1.1, 2.2, 3.5,4.3,5.0));
    result.put(LocalDate.of(2023, 12, 29),
        List.of(1.1, 2.2, 3.5,4.3,5.0));
    result.put(LocalDate.of(2023, 12, 28),
        List.of(1.1, 2.2, 3.5,4.3,5.0));
    return result;
  };

  @Before
  public void setUp() {
    manager = new HistorianModel(testGetter);
    manager.createPortfolio(VALID_PORTFOLIO);
    try {
      manager.addStock("NFLX", 30, buyDate, VALID_PORTFOLIO);
    } catch (IOException e) {
      fail(); //mock historian unexpectedly threw an error.
    }
  }

  @Test
  public void testGetPortfolioValueValid() {
    actualValue = manager.getPortfolioValue(VALID_PORTFOLIO, checkDate);
    expectedValue = 30 * 15.6;
    assertEquals(expectedValue, actualValue, DELTA);
    //confirm that stocks not yet bought are not included
    try {
      manager.addStock("AMZN", 30, buyDate.plusDays(1), VALID_PORTFOLIO);
    } catch (IOException | IllegalArgumentException e) {
      fail();
    }
    actualValue = manager.getPortfolioValue(VALID_PORTFOLIO, checkDate);
    expectedValue = 60 * 15.6;
    assertEquals(expectedValue, actualValue, DELTA);


    actualValue = manager.getPortfolioValue(VALID_PORTFOLIO, buyDate);
    expectedValue = 3454.5 * 30;
    assertEquals(expectedValue, actualValue, DELTA);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetPortfolioValueInvalidName() {
    manager.getPortfolioValue("InvalidPortfolio", checkDate);
  }

  @Test
  public void testGetStockPriceValid() {
    actualValue = manager.getStockPrice("valid", checkDate, true);
    expectedValue = 15.6;
    assertEquals(expectedValue, actualValue, DELTA);
    actualValue = manager.getStockPrice("valid", checkDate, false);
    expectedValue = 12.4;
    assertEquals(expectedValue, actualValue, DELTA);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetStockPriceInvalidTicker() {
    manager.getStockPrice("IllegalArgumentException", checkDate, true);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetStockPriceInFuture() {
    manager.getStockPrice("valid", LocalDate.now().plusDays(1), true);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetStockPriceInPast() {
    LocalDate wayInThePast = checkDate.minusYears(300);
    manager.getStockPrice("valid", wayInThePast, true);
  }

  @Test
  public void testGetCostBasisValid() {
    //test before and after the buyDate which was when the NFLX stock was dded
    actualValue = manager.getCostBasis(VALID_PORTFOLIO, buyDate.minusDays(1));
    expectedValue = 0;
    assertEquals(expectedValue, actualValue, DELTA);
    actualValue = manager.getCostBasis(VALID_PORTFOLIO, checkDate.plusDays(1));
    expectedValue = 3454.5 * 30;
    assertEquals(expectedValue, actualValue, DELTA);
  }

  @Test
  public void testGetCostBasisMultipleDays() {
    try {
      manager.addStock("valid", 30, buyDate.plusDays(2), VALID_PORTFOLIO);
    } catch (IOException e) {
      fail();
    }
    expectedValue = 549.43 * 30 + 3454.5 * 30;
    actualValue = manager.getCostBasis(VALID_PORTFOLIO, checkDate);
    assertEquals(expectedValue, actualValue, DELTA);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetCostBasisInvalidPortfolioName() {
    manager.getCostBasis("invalidName", checkDate);
  }


  @Test
  public void addStockValid() {
    try {
      IStock stock = new Stock("NFLX", 40, checkDate);
      manager.addStock("NFLX", 40, checkDate, VALID_PORTFOLIO);
      assertTrue(manager.getPortfolioComposition(VALID_PORTFOLIO).contains(stock));
      //check that the model stores stock sold as a separate entity
      stock = new Stock("NFLX", -30, checkDate.minusDays(1));
      manager.addStock("NFLX", -30, checkDate.minusDays(1), VALID_PORTFOLIO);
      assertTrue(manager.getPortfolioComposition(VALID_PORTFOLIO).contains(stock));
      //verify that multiple transactions on the same date for the same stock combine
      stock = new Stock("NFLX", 10, checkDate.minusDays(1));
      manager.addStock("NFLX", 40, checkDate.minusDays(1), VALID_PORTFOLIO);
      assertTrue(manager.getPortfolioComposition(VALID_PORTFOLIO).contains(stock));
    } catch (IOException e) {
      fail();
    }
  }

  @Test
  public void addStockCancelOut() {
    try {
      manager.addStock("NFLX", -30, buyDate, VALID_PORTFOLIO);
    } catch (IOException e) {
      fail();
    }
    //if the same stock is bought and sold on a given date, the portfolio cancels it out.
    assertTrue(manager.getPortfolioComposition(VALID_PORTFOLIO).isEmpty());
  }

  @Test
  public void addStockInvalidPortfolioName() {
    Exception exception = assertThrows(IllegalArgumentException.class,
        () -> manager.addStock("NFLX", 30, checkDate, "Invalid"));
    String expectedMessage = "Portfolio: Invalid is not recognized.";
    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  public void addStockInvalidStockName() {
    Exception exception = assertThrows(IllegalArgumentException.class, () ->
        manager.addStock("IllegalArgumentException", 30, checkDate, VALID_PORTFOLIO));
    String expectedMessage = "Invalid Ticker Symbol";
    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  public void addStockNotYetListed() {
    Exception exception = assertThrows(IllegalArgumentException.class, () ->
        manager.addStock("NFLX", 30, checkDate.minusYears(300),
          VALID_PORTFOLIO));
    String expectedMessage = "Stock not yet listed. IPO date is 2023 Dec 28";
    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  public void addStockMarketHoliday() {
    Exception exception = assertThrows(IllegalArgumentException.class, () ->
        manager.addStock("NFLX", 30, checkDate.minusDays(4),
          VALID_PORTFOLIO));
    String expectedMessage = "Requested date on a market holiday or weekend";
    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  public void addStockFuture() {
    LocalDate futureDate = LocalDate.now().plusDays(1);
    String dateFormatted = futureDate.format(DateTimeFormatter.ofPattern("yyyy MMM dd"));
    Exception exception = assertThrows(IllegalArgumentException.class, () ->
        manager.addStock("NFLX", 30, futureDate, VALID_PORTFOLIO));
    String expectedMessage = "Stock price not yet listed for date: " + dateFormatted;
    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  public void addStockDeListed() {
    Exception exception = assertThrows(IllegalArgumentException.class,
        () -> manager.addStock("De-listed", 30, checkDate.plusDays(1),
          VALID_PORTFOLIO));
    String expectedMessage = "Stock: De-listed was de-listed from the NYSE on 2024 Mar 21";
    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  public void addStockInsufficientStockToSell() {
    Exception exception = assertThrows(IllegalArgumentException.class,
        () -> manager.addStock("NFLX", -300, buyDate, VALID_PORTFOLIO));
    String expectedMessage = "Portfolio port1 does not own enough shares of stock: "
        + "NFLX by 2024 Mar 12 for this transaction. "
        + "You must first purchase at least 330.00 shares on this date or earlier";
    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  public void addStockStockAlreadySoldInTheFuture() {
    try {
      //successfully sell the stock in the future
      manager.addStock("NFLX", -30, buyDate.plusDays(1), VALID_PORTFOLIO);
    } catch (IOException e) {
      fail();
    }
    IStock expected = new Stock("NFLX", -30, buyDate.plusDays(1));
    assertTrue(manager.getPortfolioComposition(VALID_PORTFOLIO).contains(expected));
    //try to sell stock already set to be sold
    Exception exception = assertThrows(IllegalArgumentException.class,
        () -> manager.addStock("NFLX", -3, buyDate, VALID_PORTFOLIO));
    String expectedMessage = "Portfolio port1 is already set to sell stock: NFLX in the future.";
    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  public void testAddStockImmutablePortfolio() {
    manager.flipMutability(VALID_PORTFOLIO);
    Exception exception = assertThrows(IllegalArgumentException.class,
        () -> manager.addStock("NFLX", -300, checkDate.plusDays(1), VALID_PORTFOLIO));
    String expectedMessage = String.format("Portfolio: %s is immutable."
        + " Please make it mutable before attempting to buy/sell stocks.", VALID_PORTFOLIO);
    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  public void testCreatePortfolioValid() {
    manager.createPortfolio("Portfolio2");
    assertTrue(Arrays.asList(manager.listPortfolioNames()).contains("Portfolio2"));
  }

  @Test
  public void testCreatePortfolioNameAlreadyTaken() {
    Exception exception = assertThrows(IllegalArgumentException.class,
        () -> manager.createPortfolio(VALID_PORTFOLIO));
    String expectedMessage = "Portfolio name: port1 is already used.";
    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  public void testFlipMutability() {
    assertTrue(manager.isPortfolioMutable(VALID_PORTFOLIO));
    manager.flipMutability(VALID_PORTFOLIO);
    assertFalse(manager.isPortfolioMutable(VALID_PORTFOLIO));
    manager.flipMutability(VALID_PORTFOLIO);
    assertTrue(manager.isPortfolioMutable(VALID_PORTFOLIO));

    Exception exception = assertThrows(IllegalArgumentException.class,
        () -> manager.flipMutability("invalidName"));
    String expectedMessage = "Portfolio: invalidName is not recognized.";
    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  public void testIsPortfolioMutable() {
    //expect no error when adding a stock to a mutable portfolio
    assertTrue(manager.isPortfolioMutable(VALID_PORTFOLIO));
    try {
      manager.addStock("NFLX", 30, checkDate, VALID_PORTFOLIO);
      manager.flipMutability(VALID_PORTFOLIO);
      assertFalse(manager.isPortfolioMutable(VALID_PORTFOLIO));
    } catch (IOException e) {
      fail();
    }
    Exception exception = assertThrows(IllegalArgumentException.class,
        () -> manager.addStock("NFLX", 30, checkDate, VALID_PORTFOLIO));
    String expectedMessage = "Portfolio: port1 is immutable."
        + " Please make it mutable before attempting to buy/sell stocks.";
    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  public void testListPortfolioNames() {
    assertEquals(1, manager.listPortfolioNames().length);
    assertTrue(Arrays.asList(manager.listPortfolioNames()).contains(VALID_PORTFOLIO));
    manager.createPortfolio("port2");
    assertEquals(2, manager.listPortfolioNames().length);
    assertTrue(Arrays.asList(manager.listPortfolioNames()).contains(VALID_PORTFOLIO));
    assertTrue(Arrays.asList(manager.listPortfolioNames()).contains("port2"));
  }

  @Test
  public void testGetStockCompositionValid() {
    manager.createPortfolio("port2");
    assertTrue(manager.getPortfolioComposition("port2").isEmpty());
    Set<IStock> actual = new HashSet<>();
    try {
      manager.addStock("F", 30, buyDate.plusDays(1), VALID_PORTFOLIO);
      manager.addStock("TSLA", 30, buyDate.plusDays(2), VALID_PORTFOLIO);
      manager.addStock("NFLX", -5, buyDate.plusDays(1), VALID_PORTFOLIO);
      actual = manager.getPortfolioComposition(VALID_PORTFOLIO);
    } catch (IOException e) {
      fail();
    }
    assertEquals(4, actual.size());
    assertTrue(actual.contains(new Stock("NFLX", 30, buyDate)));
    assertTrue(actual.contains(new Stock("F", 30, buyDate.plusDays(1))));
    assertTrue(actual.contains(new Stock("TSLA", 30, buyDate.plusDays(2))));
    assertTrue(actual.contains(new Stock("NFLX", -5, buyDate.plusDays(1))));
  }

  @Test
  public void testGetStockPricesDays() {
    //Test that weekends are skipped, and that the earliest date prior to the one specified is used
    //Test that if there are not enough values available, the map is of size less than max size.
    SortedMap<LocalDate, Double> expected = new TreeMap<>();
    expected.put(LocalDate.of(2024, 3, 21), 15.6);
    expected.put(LocalDate.of(2024, 3, 20), 4.5);
    expected.put(LocalDate.of(2024, 3, 19), 324.52);
    expected.put(LocalDate.of(2024, 3, 18), 4345.5);
    expected.put(LocalDate.of(2024, 3, 15), 15345.6);

    LocalDate startDate = LocalDate.of(2024, 3, 21);
    SortedMap<LocalDate, Double> actual = manager.getStockPrices(
        ChronoUnit.DAYS, "SomeTicker", startDate, 5, true, 1);
    assertEquals(expected.size(), actual.size());
    System.out.println(actual);
    System.out.println(expected);
    for (Entry<LocalDate, Double> entry : actual.entrySet()) {
      assertTrue(expected.containsKey(entry.getKey()));
      assertEquals(expected.get(entry.getKey()), entry.getValue(), DELTA);
    }
    //Test that even well beyond the original
    expected.put(LocalDate.of(2024, 3, 14), 549.43);
    expected.put(LocalDate.of(2024, 3, 13), 15.6);
    expected.put(LocalDate.of(2024, 3, 12), 3454.5);
    expected.put(LocalDate.of(2024, 2, 29), 4.3);
    expected.put(LocalDate.of(2024, 1, 29), 4.3);
    expected.put(LocalDate.of(2023, 12, 29), 4.3);
    expected.put(LocalDate.of(2023, 12, 28), 4.3);
    actual = manager.getStockPrices(ChronoUnit.DAYS, "S", startDate, 13, true, 1);
    assertEquals(expected.size(), actual.size());
    for (Entry<LocalDate, Double> entry : actual.entrySet()) {
      assertTrue(expected.containsKey(entry.getKey()));
      assertEquals(expected.get(entry.getKey()), entry.getValue(), DELTA);
    }
  }

  @Test
  public void testGetStockPricesWeeks() {
    SortedMap<LocalDate, Double> expected = new TreeMap<>();
    expected.put(LocalDate.of(2024, 3, 21), 15.6);
    expected.put(LocalDate.of(2024, 3, 15), 15345.6);
    expected.put(LocalDate.of(2024, 2, 29), 4.3);

    LocalDate startDate = LocalDate.of(2024, 3, 21);
    SortedMap<LocalDate, Double> actual = manager.getStockPrices(
        ChronoUnit.WEEKS, "SomeTicker", startDate, 3, true, 1);
    System.out.println(actual);
    assertEquals(expected.size(), actual.size());
    for (Entry<LocalDate, Double> entry : actual.entrySet()) {
      assertTrue(expected.containsKey(entry.getKey()));
      assertEquals(expected.get(entry.getKey()), entry.getValue(), DELTA);
    }
  }

  @Test
  public void testGetStockPricesMonths() {
    SortedMap<LocalDate, Double> expected = new TreeMap<>();
    expected.put(LocalDate.of(2024, 3, 21), 15.6);
    expected.put(LocalDate.of(2024, 2, 29), 4.3);
    expected.put(LocalDate.of(2024, 1, 29), 4.3);
    expected.put(LocalDate.of(2023, 12, 29), 4.3);

    LocalDate startDate = LocalDate.of(2024, 3, 21);
    SortedMap<LocalDate, Double> actual = manager.getStockPrices(
        ChronoUnit.MONTHS, "SomeTicker", startDate, 5, true, 1);
    System.out.println(actual);
    assertEquals(expected.size(), actual.size());
    for (Entry<LocalDate, Double> entry : actual.entrySet()) {
      assertTrue(expected.containsKey(entry.getKey()));
      assertEquals(expected.get(entry.getKey()), entry.getValue(), DELTA);
    }
  }

  @Test
  public void testGetStockPricesYears() {
    SortedMap<LocalDate, Double> expected = new TreeMap<>();
    expected.put(LocalDate.of(2024, 3, 21), 15.6);
    expected.put(LocalDate.of(2023, 12, 29), 4.3);

    LocalDate startDate = LocalDate.of(2024, 3, 21);
    SortedMap<LocalDate, Double> actual = manager.getStockPrices(
        ChronoUnit.YEARS, "SomeTicker", startDate, 5, true, 1);
    System.out.println(actual);
    assertEquals(expected.size(), actual.size());
    for (Entry<LocalDate, Double> entry : actual.entrySet()) {
      assertTrue(expected.containsKey(entry.getKey()));
      assertEquals(expected.get(entry.getKey()), entry.getValue(), DELTA);
    }
  }

  @Test
  public void testGetStockPricesWrongUnit() {
    Exception exception = assertThrows(IllegalStateException.class, () ->
        manager.getStockPrices(ChronoUnit.HOURS, "",
          checkDate, 5, true, 1));
    String expectedMessage = "Only date based units are supported. Provided: Hours";
    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  public void testGetStockPricesInvalidTicker() {
    Exception exception = assertThrows(IllegalArgumentException.class, () ->
        manager.getStockPrices(ChronoUnit.DAYS, "IllegalArgumentException",
        checkDate, 5, true, 1));
    String expectedMessage = "Invalid Ticker Symbol";
    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  public void testGetPortfolioValuesDays() {
    //Test that weekends are skipped, and that the earliest date prior to the one specified is used
    //Test that if there are not enough values available, the map is of size less than max size.
    SortedMap<LocalDate, Double> expected = new TreeMap<>();
    expected.put(LocalDate.of(2024, 3, 21), 468.0);
    expected.put(LocalDate.of(2024, 3, 20), 135.);
    expected.put(LocalDate.of(2024, 3, 19), 324.52 * 30);
    expected.put(LocalDate.of(2024, 3, 18), 4345.5 * 30);
    expected.put(LocalDate.of(2024, 3, 15), 15345.6 * 30);

    LocalDate startDate = LocalDate.of(2024, 3, 22);
    SortedMap<LocalDate, Double> actual = manager.getPortfolioValues(
        ChronoUnit.DAYS, VALID_PORTFOLIO, startDate, 5, 1);
    assertEquals(expected.size(), actual.size());
    for (Entry<LocalDate, Double> entry : actual.entrySet()) {
      assertTrue(expected.containsKey(entry.getKey()));
      assertEquals(expected.get(entry.getKey()), entry.getValue(), DELTA);
    }

    expected.put(LocalDate.of(2024, 3, 14), 549.43 * 30);
    expected.put(LocalDate.of(2024, 3, 13), 15.6 * 30);
    expected.put(LocalDate.of(2024, 3, 12), 3454.5 * 30);
    //entries below should be zero because this is before the buy date
    expected.put(LocalDate.of(2024, 2, 29), 0.0);
    expected.put(LocalDate.of(2024, 1, 29), 0.0);
    expected.put(LocalDate.of(2023, 12, 29), 0.0);
    expected.put(LocalDate.of(2023, 12, 28), 0.0);
    actual = manager.getPortfolioValues( ChronoUnit.DAYS, VALID_PORTFOLIO, startDate, 12, 1);

    assertEquals(expected.size(), actual.size());
    for (Entry<LocalDate, Double> entry : actual.entrySet()) {
      assertTrue(expected.containsKey(entry.getKey()));
      assertEquals(expected.get(entry.getKey()), entry.getValue(), DELTA);
    }
  }

  @Test
  public void testGetPortfolioValuesWeeks() {
    SortedMap<LocalDate, Double> expected = new TreeMap<>();
    expected.put(LocalDate.of(2024, 3, 21), 15.6 * 30);
    expected.put(LocalDate.of(2024, 3, 15), 15345.6 * 30);
    expected.put(LocalDate.of(2024, 2, 29), 0.0);

    LocalDate startDate = LocalDate.of(2024, 3, 21);
    SortedMap<LocalDate, Double> actual = manager.getPortfolioValues(
        ChronoUnit.WEEKS, VALID_PORTFOLIO, startDate, 3, 1);
    assertEquals(expected.size(), actual.size());

    for (Entry<LocalDate, Double> entry : actual.entrySet()) {
      assertTrue(expected.containsKey(entry.getKey()));
      assertEquals(expected.get(entry.getKey()), entry.getValue(), DELTA);
    }
  }

  @Test
  public void testGetPortfolioValuesMonths() {
    SortedMap<LocalDate, Double> expected = new TreeMap<>();
    expected.put(LocalDate.of(2024, 3, 21), 15.6 * 30);
    expected.put(LocalDate.of(2024, 2, 29), 0.0);
    expected.put(LocalDate.of(2024, 1, 29), 0.0);
    expected.put(LocalDate.of(2023, 12, 29), 0.0);

    LocalDate startDate = LocalDate.of(2024, 3, 21);
    SortedMap<LocalDate, Double> actual = manager.getPortfolioValues(
        ChronoUnit.MONTHS, VALID_PORTFOLIO, startDate, 5, 1);
    assertEquals(expected.size(), actual.size());

    for (Entry<LocalDate, Double> entry : actual.entrySet()) {
      assertTrue(expected.containsKey(entry.getKey()));
      assertEquals(expected.get(entry.getKey()), entry.getValue(), DELTA);
    }
  }

  @Test
  public void testGetPortfolioValuesYears() {
    SortedMap<LocalDate, Double> expected = new TreeMap<>();
    expected.put(LocalDate.of(2024, 3, 21), 15.6  * 30);
    expected.put(LocalDate.of(2023, 12, 29), 0.0);

    LocalDate startDate = LocalDate.of(2024, 3, 21);
    SortedMap<LocalDate, Double> actual = manager.getPortfolioValues(
        ChronoUnit.YEARS, VALID_PORTFOLIO, startDate, 5, 1);
    assertEquals(expected.size(), actual.size());
    for (Entry<LocalDate, Double> entry : actual.entrySet()) {
      assertTrue(expected.containsKey(entry.getKey()));
      assertEquals(expected.get(entry.getKey()), entry.getValue(), DELTA);
    }

  }

  @Test
  public void testGetPortfolioValuesWrongUnit() {
    Exception exception = assertThrows(IllegalStateException.class, () ->
        manager.getPortfolioValues(ChronoUnit.HOURS, VALID_PORTFOLIO, checkDate, 5, 1));
    String expectedMessage = "Only date based units are supported. Provided: Hours";
    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);
  }

  @Test
  public void testGetPortfolioValuesInvalidPortfolioName() {
    Exception exception = assertThrows(IllegalArgumentException.class, () ->
        manager.getPortfolioValues(ChronoUnit.DAYS, "Invalid", checkDate, 5, 1));
    String expectedMessage = "Portfolio: Invalid is not recognized.";
    String actualMessage = exception.getMessage();
    assertEquals(expectedMessage, actualMessage);
  }

}
