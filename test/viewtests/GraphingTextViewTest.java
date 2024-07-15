package viewtests;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import model.IStock;
import model.Stock;
import org.junit.Before;
import org.junit.Test;
import view.text.GraphingTextView;
import view.text.IGraphingView;

/**
 * Test the methods of the GraphingTextView class.
 */
public class GraphingTextViewTest {

  private IGraphingView graphingView;
  private String expected;
  private SortedMap<LocalDate, Double> performanceMap;
  private SortedMap<LocalDate, Boolean> buySellDates;

  private OutputStream out;

  @Before
  public void setUp() {
    out = new ByteArrayOutputStream();
    graphingView = new GraphingTextView(out);
    performanceMap = new TreeMap<>();
    buySellDates = new TreeMap<>();
  }

  @Test
  public void testDisplayValueOfStock() {
    expected = "Value of stock Apple Inc. (AAPL) on 04 May 2021 is: $1,234.56"
      + System.lineSeparator();
    LocalDate d = LocalDate.of(2021, 5, 4);
    graphingView.displayValueOfAsset("stock Apple Inc. (AAPL)", 1234.56, d);
    assertEquals(expected, out.toString());
  }



  @Test
  public void testPlotPerformanceAbsoluteScale() {
    expected = String.format("Performance of portfolio XXX from 31 Jan 2010 to 31 Dec 2010%1$s"
      + "2010 Jan 31 : *****%1$s"
      + "2010 Feb 28 : ***%1$s"
      + "2010 Mar 31 : ********%1$s"
      + "2010 Apr 30 : ******%1$s"
      + "2010 May 31 : *******%1$s"
      + "2010 Jun 30 : ********%1$s"
      + "2010 Jul 30 : %1$s"
      + "2010 Aug 31 : *********%1$s"
      + "2010 Sep 30 : ***********%1$s"
      + "2010 Oct 31 : ************%1$s"
      + "2010 Nov 30 : **********%1$s"
      + "2010 Dec 31 : **************%1$s"
      + "Scale: * = $1,000.00%1$s", System.lineSeparator());

    performanceMap.put(LocalDate.of(2010, 1, 31), 5430.34);
    performanceMap.put(LocalDate.of(2010, 2, 28), 3499.99); //Check rounding
    performanceMap.put(LocalDate.of(2010, 3, 31), 8302.54);
    performanceMap.put(LocalDate.of(2010, 4, 30), 6032.76);
    performanceMap.put(LocalDate.of(2010, 5, 31), 7302.54);
    performanceMap.put(LocalDate.of(2010, 6, 30), 8407.54);
    performanceMap.put(LocalDate.of(2010, 7, 30), 365.00); //Check 0
    performanceMap.put(LocalDate.of(2010, 8, 31), 9302.54);
    performanceMap.put(LocalDate.of(2010, 9, 30), 11302.54);
    performanceMap.put(LocalDate.of(2010, 10, 31), 12002.54);
    performanceMap.put(LocalDate.of(2010, 11, 30), 10267.54);
    performanceMap.put(LocalDate.of(2010, 12, 31), 13902.843);
    graphingView.plotPerformance(performanceMap, "portfolio XXX");
    assertEquals(expected, out.toString());
  }

  @Test
  public void testPlotPerformanceScaleDays() {
    //Test that a map of dates that share a month will render timestamps with the month shown.
    expected = String.format("Performance of portfolio XXX from 01 Jan 2010 to 30 Jan 2010%1$s"
      + "2010 Jan 01 : *****%1$s"
      + "2010 Jan 05 : ***%1$s"
      + "2010 Jan 10 : ********%1$s"
      + "2010 Jan 15 : ******%1$s"
      + "2010 Jan 20 : *******%1$s"
      + "2010 Jan 25 : ********%1$s"
      + "2010 Jan 30 : %1$s"
      + "Scale: * = $1,000.00%1$s", System.lineSeparator());
    performanceMap.put(LocalDate.of(2010, 1, 1), 5430.34);
    performanceMap.put(LocalDate.of(2010, 1, 5), 3499.99);
    performanceMap.put(LocalDate.of(2010, 1, 10), 8302.54);
    performanceMap.put(LocalDate.of(2010, 1, 15), 6032.76);
    performanceMap.put(LocalDate.of(2010, 1, 20), 7302.54);
    performanceMap.put(LocalDate.of(2010, 1, 25), 8407.54);
    performanceMap.put(LocalDate.of(2010, 1, 30), 365.00);
    graphingView.plotPerformance(performanceMap, "portfolio XXX");
    assertEquals(expected, out.toString());
  }

  @Test
  public void testPlotPerformanceScaleYears() {
    //Even if dates are not at the end of the year, as long as there are no dates sharing a year,
    // only the year will be shown in the timestamp
    expected = String.format("Performance of portfolio XXX from 31 Jan 2010 to 31 Dec 2021%1$s"
      + "2010 Jan 31 : *****%1$s"
      + "2011 Feb 28 : ***%1$s"
      + "2012 Mar 31 : ********%1$s"
      + "2013 Apr 30 : ******%1$s"
      + "2014 May 31 : *******%1$s"
      + "2015 Jun 30 : ********%1$s"
      + "2016 Jul 30 : %1$s"
      + "2017 Aug 31 : *********%1$s"
      + "2018 Sep 30 : ***********%1$s"
      + "2019 Oct 31 : ************%1$s"
      + "2020 Nov 30 : **********%1$s"
      + "2021 Dec 31 : **************%1$s"
      + "Scale: * = $1,000.00%1$s", System.lineSeparator());
    performanceMap.put(LocalDate.of(2010, 1, 31), 5430.34);
    performanceMap.put(LocalDate.of(2011, 2, 28), 3499.99); //Check rounding
    performanceMap.put(LocalDate.of(2012, 3, 31), 8302.54);
    performanceMap.put(LocalDate.of(2013, 4, 30), 6032.76);
    performanceMap.put(LocalDate.of(2014, 5, 31), 7302.54);
    performanceMap.put(LocalDate.of(2015, 6, 30), 8407.54);
    performanceMap.put(LocalDate.of(2016, 7, 30), 365.00); //Check 0
    performanceMap.put(LocalDate.of(2017, 8, 31), 9302.54);
    performanceMap.put(LocalDate.of(2018, 9, 30), 11302.54);
    performanceMap.put(LocalDate.of(2019, 10, 31), 12002.54);
    performanceMap.put(LocalDate.of(2020, 11, 30), 10267.54);
    performanceMap.put(LocalDate.of(2021, 12, 31), 13902.843);
    graphingView.plotPerformance(performanceMap, "portfolio XXX");
    assertEquals(expected, out.toString());
  }

  @Test (expected = IllegalStateException.class)
  public void testPlotPerformanceNegativeValue() {
    performanceMap.put(LocalDate.of(2010, 1, 31), 5430.34);
    performanceMap.put(LocalDate.of(2010, 2, 28), 3499.99);
    performanceMap.put(LocalDate.of(2010, 3, 31), 8302.54);
    performanceMap.put(LocalDate.of(2010, 4, 30), -6032.76);
    performanceMap.put(LocalDate.of(2010, 5, 31), 7302.54);
    performanceMap.put(LocalDate.of(2010, 6, 30), 8407.54);
    performanceMap.put(LocalDate.of(2010, 7, 30), 365.00);
    performanceMap.put(LocalDate.of(2010, 8, 31), 9302.54);
    performanceMap.put(LocalDate.of(2010, 9, 30), 11302.54);
    performanceMap.put(LocalDate.of(2010, 10, 31), 12002.54);
    performanceMap.put(LocalDate.of(2010, 11, 30), 10267.54);
    performanceMap.put(LocalDate.of(2010, 12, 31), 13902.843);
    graphingView.plotPerformance(performanceMap, "portfolio XXX");
  }

  @Test (expected = IllegalStateException.class)
  public void testPlotPerformanceEmptyMap() {
    graphingView.plotPerformance(new TreeMap<>(), "test");
  }

  @Test (expected = IllegalStateException.class)
  public void testPlotPerformanceMapHasOneValue() {
    performanceMap.put(LocalDate.of(2010, 12, 31), 13902.843);
    graphingView.plotPerformance(performanceMap, "portfolio XXX");
  }

  @Test
  public void testShowBuySellDates() {
    expected = String.format("Report: Moving Crossover Days for Stock X%1$s"
      + "05 Nov 2023 : SELL%1$s"
      + "03 Dec 2023 : BUY%1$s"
      + "24 Dec 2023 : SELL%1$s"
      + "31 Jan 2024 : SELL%1$s"
      + "04 Feb 2024 : BUY%1$s"
      + "Dates above are calculated using 30 and 100 day moving averages.%1$s",
      System.lineSeparator());
    buySellDates.put(LocalDate.of(2023, 11, 5), false);
    buySellDates.put(LocalDate.of(2023, 12, 3), true);
    buySellDates.put(LocalDate.of(2023, 12, 24), false);
    buySellDates.put(LocalDate.of(2024, 1, 31), false);
    buySellDates.put(LocalDate.of(2024, 2, 4), true);
    graphingView.showBuySellDates(buySellDates,
        "Moving Crossover Days for Stock X",
        "Dates above are calculated using 30 and 100 day moving averages.");
    assertEquals(expected, out.toString());
  }

  @Test
  public void testShowBuySellDatesEmpty() {
    expected = String.format("Report: Moving Crossover Days for Stock X%1$s"
        + "There are no buy sell dates in this report%1$s"
        + "Dates above are calculated using 30 and 100 day moving averages.%1$s",
      System.lineSeparator());
    graphingView.showBuySellDates(buySellDates,
        "Moving Crossover Days for Stock X",
        "Dates above are calculated using 30 and 100 day moving averages.");
    assertEquals(expected, out.toString());
  }

  @Test
  public void testShowNetIncrease() {
    expected = "Portfolio1 increased by $99.46 between 04 Jan 2021 and 06 Feb 2021"
      + System.lineSeparator();
    LocalDate start = LocalDate.of(2021, 1, 4);
    LocalDate end = LocalDate.of(2021, 2, 6);
    graphingView.showNet(start, end, "Portfolio1", 20.54, 120);
    assertEquals(expected, out.toString());
  }

  @Test
  public void testShowNetSameDay() {
    expected = "Portfolio1 increased by $99.46 on 04 Jan 2021" + System.lineSeparator();
    LocalDate start = LocalDate.of(2021, 1, 4);
    graphingView.showNet(start, start, "Portfolio1", 20.54, 120);
    assertEquals(expected, out.toString());
  }

  @Test
  public void testShowNetNoChange() {
    expected = "Portfolio1 did not change in value between 04 Jan 2021 and 06 Feb 2021"
      + System.lineSeparator();
    LocalDate start = LocalDate.of(2021, 1, 4);
    LocalDate end = LocalDate.of(2021, 2, 6);
    graphingView.showNet(start, end, "Portfolio1", 120, 120.001);
    assertEquals(expected, out.toString());
  }

  @Test
  public void testShowNetDecrease() {
    expected = "Portfolio1 decreased by $99.46 between 04 Jan 2021 and 06 Feb 2021"
      + System.lineSeparator();
    LocalDate start = LocalDate.of(2021, 1, 4);
    LocalDate end = LocalDate.of(2021, 2, 6);
    graphingView.showNet(start, end, "Portfolio1", 120, 20.54);
    assertEquals(expected, out.toString());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testShowNetStartAfterEnd() {
    graphingView.showNet(
        LocalDate.of(2021, 3,1),
        LocalDate.of(2020, 3, 1),
        "Portfolio1", 0, 0);
  }

  @Test
  public void testDisplayMainMenu() {
    expected = String.format("Welcome to the Investment Portfolio Manager!%1$s"
                               + "Please select an option from the following menu:%1$s"
                               + "%1$s"
                               + "1.Create Mutable Portfolio%1$s"
                               + "2.Create Immutable Portfolio%1$s"
                               + "3.Get Portfolio Value%1$s"
                               + "4.Get Portfolio Composition%1$s"
                               + "5.Get Cost Basis%1$s"
                               + "6.Load Portfolio%1$s"
                               + "7.Save Portfolio%1$s"
                               + "8.Buy Stocks%1$s"
                               + "9.Sell Stocks%1$s"
                               + "10.Get Daily Stock Performance%1$s"
                               + "11.Get Period Stock Performance%1$s"
                               + "12.Get X-Day Moving Average%1$s"
                               + "13.Get Cross Over Days%1$s"
                               + "14.Get Moving Average Cross Over%1$s"
                               + "15.Analyze Stock Performance%1$s"
                               + "16.Analyze Portfolio Performance%1$s"
                               + "17. Invest with Weighted Strategy%1$s"
                               + "18. Create Dollar Cost Averaging Portfolios%1$s"
                               + "q.Quit%1$s", System.lineSeparator());
    graphingView.displayMainMenu();
    assertEquals(expected, out.toString());
  }

  @Test
  public void testShowCostBasis() {
    expected = "CostBasis of Portfolio1 on 05 Mar 2024 is: $13,003.34" + System.lineSeparator();
    graphingView.showCostBasis(
        LocalDate.of(2024, 3, 5), "Portfolio1", 13003.34);
    assertEquals(expected, out.toString());
  }

  @Test
  public void testDisplayPortfolioComposition() {
    Set<IStock> stockSet = new HashSet<>();
    stockSet.add(new Stock("NFLX", 30,
        LocalDate.of(2024, 3, 26)));
    stockSet.add(new Stock("NFLX", 30,
        LocalDate.of(2024, 3, 26).plusDays(1)));
    stockSet.add(new Stock("NFLX", 30,
        LocalDate.of(2024, 3, 26).minusDays(2)));
    stockSet.add(new Stock("TSLA", 30,
        LocalDate.of(2024, 3, 26)));
    stockSet.add(new Stock("NFLX", -30,
        LocalDate.of(2024, 3, 26)));
    stockSet.add(new Stock("SPLK", 30,
        LocalDate.of(2024, 3, 26)));
    stockSet.add(new Stock("IRTC", 30,
        LocalDate.of(2024, 3, 26)));
    stockSet.add(new Stock("GUTS", 30,
        LocalDate.of(2024, 3, 26)));

    expected = String.format("Port1 composition:%1$s"
      + "2024 Mar 24: 30.00 shares bought of NFLX%1$s"
      + "2024 Mar 26: 30.00 shares bought of GUTS%1$s"
      + "2024 Mar 26: 30.00 shares bought of IRTC%1$s"
      + "2024 Mar 26: 30.00 shares bought of NFLX%1$s"
      + "2024 Mar 26: 30.00 shares sold of NFLX%1$s"
      + "2024 Mar 26: 30.00 shares bought of SPLK%1$s"
      + "2024 Mar 26: 30.00 shares bought of TSLA%1$s"
      + "2024 Mar 27: 30.00 shares bought of NFLX%1$s%1$s", System.lineSeparator());
    graphingView.displayPortfolioComposition(stockSet, "Port1");
    assertEquals(expected, out.toString());

  }

  @Test
  public void testDisplayPortfolioCompositionEmpty() {
    Set<IStock> stockSet = new HashSet<>();
    graphingView.displayPortfolioComposition(stockSet, "Port1");
    expected = "Port1 composition:" + System.lineSeparator()
      + "This portfolio does not contain any stocks" + System.lineSeparator();
    assertEquals(expected, out.toString());
  }
}
