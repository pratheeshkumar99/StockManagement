package modeltest.portfoliomanagertest;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import model.IStock;
import model.OngoingTransactionModel;
import model.OngoingTransactionModelImpl;
import model.Stock;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the new methods of the OngoingTransactionModelImpl class.
 */
public class OngoingTransactionModelImplTest {

  private static final LocalDate date = LocalDate.of(2024, 3, 15);
  private static final double DELTA = 0.0001;

  private OngoingTransactionModel m;

  private static final Function<String, Map<LocalDate, List<Double>>> testGetter = s -> {
    if (s.equalsIgnoreCase("IllegalArgumentException")) {
      throw new IllegalArgumentException("InvalidTicker");
    }
    Map<LocalDate, List<Double>> result = new TreeMap<>();
    result.put(LocalDate.of(2024, 3, 21),
        List.of(1.5, 13.2, 14.5, 5.5, 16.7));
    result.put(LocalDate.of(2024, 3, 20),
        List.of(1.65, 2.54, 3.32, 4.5, 5.08));
    result.put(LocalDate.of(2024, 3, 19),
        List.of(43.45, 4324.23, 654.23, 3.5, 432432.43));
    result.put(LocalDate.of(2024, 3, 18),
        List.of(146.65, 2567.54, 334.32, 2.5, 567.08));
    result.put(LocalDate.of(2024, 3, 15),
        List.of(1452.4, 13543.2, 14768.5, 1.5, 1836.7));
    result.put(LocalDate.of(2024, 3, 14),
        List.of(891.65, 768.54, 353.32, 0.5, 87.43));
    return result;
  };

  @Before
  public void setUp() {
    m = new OngoingTransactionModelImpl(testGetter);
    Map<String, Double> investmentAmounts = new HashMap<>();
    investmentAmounts.put("IRTC", 100.0);
    investmentAmounts.put("GUTS", 200.0);
    investmentAmounts.put("NFLX", 50.0);
    investmentAmounts.put("F", 25.0);
    m.addDollarCostAveragingPortfolio(
        "DCA", investmentAmounts, date, ChronoUnit.DAYS, 1, -1);
    m.createPortfolio("port1");
    try {
      m.addStock("NFLX", 12.0, date, "port1");
      m.addStock("NFLX", 12.0, date, "DCA");
    } catch (IOException e) {
      fail();
    }
  }

  @Test
  public void testGetPortfolioValue() {
    //Confirm that it still works for the normal portfolios
    assertEquals(18, m.getPortfolioValue("port1", date), DELTA);
    //Confirm for DCA Style portfolios on the date they start.
    // Should be equal to the investment amounts, including the normal contribution
    assertEquals(375 + 18, m.getPortfolioValue("DCA", date), DELTA);
    //Confirm for DCA portfolios that they are 0 before the DCA start
    assertEquals(0, m.getPortfolioValue("DCA", date.minusDays(1)), DELTA);
    //Confirm for DCA that additional investments are reflected
    /*
    Expected Stocks owned by 18March
    15March - 12 Shares NFLX
    15March - $50 NFLX - 33.33 shares
    15March - $100 IRTC - 66.67 shares
    15March - $200 GUTS - 133.33 shares
    15March - $25 F - 16.67 shares
    18March - $50 NFLX - 20 shares
    18March - $100 IRTC - 40 shares
    18March - $200 GUTS - 80 shares
    18March - $25 F     - 10 shares
    Total expected value = 412 shares times 2.5 = $1030
     */
    double actualVal = m.getPortfolioValue("DCA", date.plusDays(3));
    assertEquals(1030, actualVal, DELTA);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetPortfolioValueNotFound() {
    m.getPortfolioValue("DCA not found", date);
  }

  @Test
  public void testGetCostBasis() {
    //assert that it works as expected for normal portfolios
    assertEquals(18, m.getCostBasis("port1", date), DELTA);

    //Confirm for DCA portfolios that 0 is returned prior to start date
    assertEquals(0, m.getCostBasis("DCA", date.minusDays(1)), DELTA);
    assertEquals(375 + 18, m.getCostBasis("DCA", date), DELTA);
    assertEquals(375 + 18 + 375, m.getCostBasis("DCA", date.plusDays(3)), DELTA);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetCostBasisNotFound() {
    m.getCostBasis("DCA not found", date);
  }

  @Test
  public void testListPortfolioNames() {
    String[] expected = new String[] {"DCA", "port1"};
    assertArrayEquals(expected, m.listPortfolioNames());
  }

  @Test
  public void testGetPortfolioComp() {
    Map<String, Double> investmentAmounts = new HashMap<>();
    investmentAmounts.put("IRTC", 3.0);
    investmentAmounts.put("GUTS", 3.0);
    investmentAmounts.put("NFLX", 3.0);
    investmentAmounts.put("F", 3.0);

    m.addDollarCostAveragingPortfolio("c", investmentAmounts, date, ChronoUnit.DAYS, 2, 1);
    Set<IStock> actual = m.getPortfolioComposition("c");
    Set<IStock> expected = new HashSet<>();
    expected.add(new Stock("F", 2, date));
    expected.add(new Stock("IRTC", 2, date));
    expected.add(new Stock("GUTS", 2, date));
    expected.add(new Stock("NFLX", 2, date));
    assertEquals(expected.size(), actual.size());
    for (IStock s: actual) {
      assertTrue(expected.contains(s));
    }
  }
}
