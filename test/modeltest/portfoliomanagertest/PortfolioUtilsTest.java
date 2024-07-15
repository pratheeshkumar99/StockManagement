package modeltest.portfoliomanagertest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import model.PortfolioUtils;
import model.IStock;
import model.Stock;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the methods of the PortfolioUtils class.
 */
public class PortfolioUtilsTest {
  private double actualShares;
  private double expectedShares;
  private final static Set<IStock> stockSet = new HashSet<>();

  @Before
  public void setUp() {
    stockSet.add(new Stock("NFLX", 233.0, LocalDate.of(2024, 3, 1)));
    stockSet.add(new Stock("NFLX", 2343, LocalDate.of(2024, 3, 1)));
    stockSet.add(new Stock("NFLX", -10, LocalDate.of(2024, 3, 12)));
    stockSet.add(new Stock("TSLA", 233.0, LocalDate.of(2024, 3, 1)));
  }

  @Test
  public void testGetUniqueTickers() {
    Set<String> expectedTickers = new HashSet<>();
    expectedTickers.add("NFLX");
    expectedTickers.add("TSLA");
    Set<String> actualTickers = PortfolioUtils.getUniqueTickers(
        stockSet,
        LocalDate.of(2023, 1, 1),
        LocalDate.of(2024, 4, 3));
    assertEquals(expectedTickers, actualTickers);
    actualTickers = PortfolioUtils.getUniqueTickers(
        stockSet,
        LocalDate.of(2024, 5,1),
        LocalDate.of(2024, 9, 3));
    assertTrue(actualTickers.isEmpty());
  }

  @Test
  public void testNumSharesOnDate() {
    expectedShares = 233 + 2343;
    actualShares = PortfolioUtils.numSharesOnDate("NFLX",
      LocalDate.of(2024, 3, 1), stockSet);
    assertEquals(expectedShares, actualShares, 0.001);
  }

  @Test
  public void testNumSharesOnDateBeforeBought() {
    expectedShares = 0;
    actualShares = PortfolioUtils.numSharesOnDate("NFLX",
      LocalDate.of(2023, 3, 1), stockSet);
    assertEquals(expectedShares, actualShares, 0.001);
  }

  @Test
  public void testNumSharesAfterSell() {
    expectedShares = 233 + 2343 - 10;
    actualShares = PortfolioUtils.numSharesOnDate("NFLX",
      LocalDate.of(2024, 5, 1), stockSet);
    assertEquals(expectedShares, actualShares, 0.001);
  }

  @Test
  public void testNumSharesThroughout() {
    expectedShares = 233 + 2343 - 10;
    actualShares = PortfolioUtils.numSharesThroughout("NFLX", stockSet);
    assertEquals(expectedShares, actualShares, 0.0001);
  }




}
