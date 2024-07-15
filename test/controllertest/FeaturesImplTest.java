package controllertest;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import controller.FeaturesImpl;
import controller.IFeatures;
import controller.filewriter.FileWriterFactory;

import java.io.File;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the FeaturesImpl class (Controller for the GUI).
 */

public class FeaturesImplTest {

  private IFeatures features;
  private MockModel model;
  private MockView view;

  /**
   * Sets up the test by creating a mock model and a mock view.
   */

  @Before
  public void setUp() {
    this.model = new MockModel();
    this.view = new MockView();
    features = new FeaturesImpl(this.model, this.view, new FileWriterFactory()
    );
  }

  private String getModelLog() {
    return String.join(" ", this.model.getLog());
  }

  private String getViewLog() {
    return String.join(" ", this.view.getLog());
  }

  @Test
  public void testSetView() {
    features.setView(view);
    assertEquals("", this.getModelLog());
    assertEquals("", this.getViewLog());
  }

  @Test
  public void validAddPortfolioTest() {
    String expectedModelLog = "create portfolio called with portfolioName:portfolioName";
    String expectedViewLog = "Portfolio created successfully";
    features.addPortfolio("portfolioName");
    assertEquals(expectedModelLog, this.getModelLog());
    assertEquals(expectedViewLog, this.getViewLog());
  }

  @Test
  public void validBuyStockTest() {
    String expectedModelLog = "add stock called with ticker:GOOGL quantity:10.0 date:2018-11-11" +
            " portfolioName:portfolioName";
    String expectedViewLog = "Stock bought successfully!";
    features.buyStock("GOOGL", "10", "2018-11-11",
            "portfolioName");
    assertEquals(expectedModelLog, this.getModelLog());
    assertEquals(expectedViewLog, this.getViewLog());
  }

  @Test
  public void validBuyStockInvalidTickerQuantityTest() {
    String expectedModelLog = "";
    String expectedViewLog = "Please Enter a Valid positive quantity.";
    features.buyStock("GOOGL", "X", "2018-11-11", "portfolioName");
    assertEquals(expectedModelLog, this.getModelLog());
    assertEquals(expectedViewLog, this.getViewLog());
  }

  @Test
  public void validBuyStockIntegerQuantityTest() {
    String expectedModelLog = "add stock called with ticker:GOOGL quantity:10.0" +
            " date:2018-11-11 portfolioName:portfolioName";
    String expectedViewLog = "Stock bought successfully!";
    features.buyStock("GOOGL", "10", "2018-11-11",
            "portfolioName");
    assertEquals(expectedModelLog, this.getModelLog());
    assertEquals(expectedViewLog, this.getViewLog());
  }

  @Test
  public void validBuyStockDoubleQuantityTest() {
    String expectedModelLog = "add stock called with ticker:GOOGL quantity:2.34" +
            " date:2018-11-11 portfolioName:portfolioName";
    String expectedViewLog = "Stock bought successfully!";
    features.buyStock("GOOGL", "2.34", "2018-11-11",
            "portfolioName");
    assertEquals(expectedModelLog, this.getModelLog());
    assertEquals(expectedViewLog, this.getViewLog());
  }

  @Test
  public void validBuyStockInvalidTickerTest() {
    String expectedModelLog = "";
    String expectedViewLog = "Stock not found";
    this.model.addSValidStocks("ValidStock");
    features.buyStock("InvalidStock", "2.34", "2018-11-11",
            "portfolioName");
    assertEquals(expectedModelLog, this.getModelLog());
    assertEquals(expectedViewLog, this.getViewLog());
  }

  @Test
  public void validBuyStockInvalidDateTestTest() {
    String expectedModelLog = "";
    String expectedViewLog = "Invalid date format. Please enter a valid date in the" +
            " format yyyy-MM-dd";
    features.buyStock("GOOGL", "10", "2014-30-20",
            "portfolioName");
    assertEquals(expectedModelLog, this.getModelLog());
    assertEquals(expectedViewLog, this.getViewLog());
  }

  @Test
  public void validBuyStockInvalidStockQuantity() {
    String expectedModelLog = "";
    String expectedViewLog = "Please Enter a Valid positive quantity.";
    features.buyStock("GOOGL", "m", "2014-30-20", "portfolioName");
    assertEquals(expectedModelLog, this.getModelLog());
    assertEquals(expectedViewLog, this.getViewLog());
  }

  @Test
  public void validBuyStockNegativeInvalidStockQuantity() {
    String expectedModelLog = "";
    String expectedViewLog = "Quantity must be greater than 0.";
    features.buyStock("GOOGL", "-10", "2014-30-20",
            "portfolioName");
    assertEquals(expectedModelLog, this.getModelLog());
    assertEquals(expectedViewLog, this.getViewLog());
  }

  @Test
  public void validBuyStockInvalidDateFormat() {
    String expectedModelLog = "";
    String expectedViewLog = "Invalid date format. Please enter a valid date in the format" +
            " yyyy-MM-dd";
    features.buyStock("GOOGL", "10", "20-01-2014",
            "portfolioName");
    assertEquals(expectedModelLog, this.getModelLog());
    assertEquals(expectedViewLog, this.getViewLog());
  }

  @Test
  public void validBuyStockZeroInvalidStockQuantity() {
    String expectedModelLog = "";
    String expectedViewLog = "Quantity must be greater than 0.";
    features.buyStock("GOOGL", "0", "2014-30-20", "portfolioName");
    assertEquals(expectedModelLog, this.getModelLog());
    assertEquals(expectedViewLog, this.getViewLog());
  }


  @Test
  public void validSellStockTest() {
    String expectedModelLog = "add stock called with ticker:GOOGL quantity:-10.0 date:2018-11-11" +
            " portfolioName:portfolioName";
    String expectedViewLog = "Stock sold successfully!";
    features.sellStock("GOOGL", "10", "2018-11-11",
            "portfolioName");
    assertEquals(expectedModelLog, this.getModelLog());
    assertEquals(expectedViewLog, this.getViewLog());
  }

  @Test
  public void validSellStockInvalidTickerQuantityTest() {
    String expectedModelLog = "";
    String expectedViewLog = "Please Enter a Valid positive quantity.";
    features.sellStock("GOOGL", "X", "2018-11-11",
            "portfolioName");
    assertEquals(expectedModelLog, this.getModelLog());
    assertEquals(expectedViewLog, this.getViewLog());
  }

  @Test
  public void validSellStockIntegerQuantityTest() {
    String expectedModelLog = "add stock called with ticker:GOOGL quantity:-10.0" +
            " date:2018-11-11 portfolioName:portfolioName";
    String expectedViewLog = "Stock sold successfully!";
    features.sellStock("GOOGL", "10", "2018-11-11",
            "portfolioName");
    assertEquals(expectedModelLog, this.getModelLog());
    assertEquals(expectedViewLog, this.getViewLog());
  }

  @Test
  public void validSellStockDoubleQuantityTest() {
    String expectedModelLog = "add stock called with ticker:GOOGL quantity:-2.34 " +
            "date:2018-11-11 portfolioName:portfolioName";
    String expectedViewLog = "Stock sold successfully!";
    features.sellStock("GOOGL", "2.34", "2018-11-11",
            "portfolioName");
    assertEquals(expectedModelLog, this.getModelLog());
    assertEquals(expectedViewLog, this.getViewLog());
  }

  @Test
  public void validSellStockInvalidTickerTest() {
    String expectedModelLog = "";
    String expectedViewLog = "Stock not found";
    this.model.addSValidStocks("ValidStock");
    features.sellStock("InvalidStock", "2.34",
            "2018-11-11", "portfolioName");
    assertEquals(expectedModelLog, this.getModelLog());
    assertEquals(expectedViewLog, this.getViewLog());
  }

  @Test
  public void validSellStockInvalidDateTestTest() {
    String expectedModelLog = "";
    String expectedViewLog = "Invalid date format. Please enter a valid date in" +
            " the format yyyy-MM-dd";
    features.sellStock("GOOGL", "10", "2014-30-20",
            "portfolioName");
    assertEquals(expectedModelLog, this.getModelLog());
    assertEquals(expectedViewLog, this.getViewLog());
  }

  @Test
  public void validSellStockInvalidStockQuantity() {
    String expectedModelLog = "";
    String expectedViewLog = "Please Enter a Valid positive quantity.";
    features.sellStock("GOOGL", "m", "2014-30-20",
            "portfolioName");
    assertEquals(expectedModelLog, this.getModelLog());
    assertEquals(expectedViewLog, this.getViewLog());
  }

  @Test
  public void validSellStockNegativeInvalidStockQuantity() {
    String expectedModelLog = "";
    String expectedViewLog = "Quantity must be greater than 0";
    features.sellStock("GOOGL", "-10", "2014-30-20",
            "portfolioName");
    assertEquals(expectedModelLog, this.getModelLog());
    assertEquals(expectedViewLog, this.getViewLog());
  }

  @Test
  public void validSellStockInvalidDateFormat() {
    String expectedModelLog = "";
    String expectedViewLog = "Invalid date format. Please enter a valid date in the format" +
            " yyyy-MM-dd";
    features.sellStock("GOOGL", "10", "20-01-2014",
            "portfolioName");
    assertEquals(expectedModelLog, this.getModelLog());
    assertEquals(expectedViewLog, this.getViewLog());
  }

  @Test
  public void validSellStockZeroInvalidStockQuantity() {
    String expectedModelLog = "";
    String expectedViewLog = "Quantity must be greater than 0";
    features.sellStock("GOOGL", "0", "2014-30-20",
            "portfolioName");
    assertEquals(expectedModelLog, this.getModelLog());
    assertEquals(expectedViewLog, this.getViewLog());
  }

  @Test
  public void validSavePortfolioTest() {
    String expectedModelLog = "save portfolio called with portfolioName:PortfolioName " +
            "path:ValidPath";
    String expectedViewLog = "Portfolio saved successfully!";
    features.savePortfolio("PortfolioName", "ValidPath");
    assertEquals(expectedModelLog, this.getModelLog());
    assertEquals(expectedViewLog, this.getViewLog());
  }

  @Test
  public void validInvalidFilePathSavePortfolioTest() {
    String expectedModelLog = "";
    String expectedViewLog = "Invalid file Path. Please enter a valid file path.";
    this.model.setValidFilePath("validFilePath");
    features.savePortfolio("PortfolioName", "InValidFilePath");
    assertEquals(expectedModelLog, this.getModelLog());
    assertEquals(expectedViewLog, this.getViewLog());
  }

  @Test
  public void validCostBasisTest() {
    String expectedModelLog = "get cost basis called with portfolioName:PortfolioName" +
            " date:2018-11-11";
    String expectedViewLog = "Asset: PortfolioName Value: 0.0 Date: 2018-11-11";
    features.costBasis("PortfolioName", "2018-11-11");
    assertEquals(expectedModelLog, this.getModelLog());
    assertEquals(expectedViewLog, this.getViewLog());
  }

  @Test
  public void inValidDateCostBasisTest() {
    String expectedModelLog = "";
    String expectedViewLog = "Invalid date format. Please enter a valid date in the" +
            " format yyyy-MM-dd";
    features.costBasis("PortfolioName", "bjdhbfbsjfb");
    assertEquals(expectedModelLog, this.getModelLog());
    assertEquals(expectedViewLog, this.getViewLog());
  }


  @Test
  public void inValidDateFormatCostBasisTest() {
    String expectedModelLog = "";
    String expectedViewLog = "Invalid date format. Please enter a valid date in the" +
            " format yyyy-MM-dd";
    features.costBasis("PortfolioName", "20-02-2004");
    assertEquals(expectedModelLog, this.getModelLog());
    assertEquals(expectedViewLog, this.getViewLog());
  }

  @Test
  public void inValidDateSeparatorCostBasisTest() {
    String expectedModelLog = "";
    String expectedViewLog = "Invalid date format. Please enter a valid date in the" +
            " format yyyy-MM-dd";
    features.costBasis("PortfolioName", "20/02/2004");
    assertEquals(expectedModelLog, this.getModelLog());
    assertEquals(expectedViewLog, this.getViewLog());
  }

  @Test
  public void inValidDayInDateCostBasisTest() {
    String expectedModelLog = "";
    String expectedViewLog = "Invalid date format. Please enter a valid date in the" +
            " format yyyy-MM-dd";
    features.costBasis("PortfolioName", "2004-03-32");
    assertEquals(expectedModelLog, this.getModelLog());
    assertEquals(expectedViewLog, this.getViewLog());
  }

  @Test
  public void inValidMonthInDateCostBasisTest() {
    String expectedModelLog = "";
    String expectedViewLog = "Invalid date format. Please enter a valid date in the format " +
            "yyyy-MM-dd";
    features.costBasis("PortfolioName", "2004-14-32");
    assertEquals(expectedModelLog, this.getModelLog());
    assertEquals(expectedViewLog, this.getViewLog());
  }


  @Test
  public void validPortfolioValueTest() {
    String expectedModelLog = "get portfolio value called with portfolioName:PortfolioName " +
            "date:2018-11-11";
    String expectedViewLog = "Asset: PortfolioName Value: 0.0 Date: 2018-11-11";
    features.portfolioValue("PortfolioName", "2018-11-11");
    assertEquals(expectedModelLog, this.getModelLog());
    assertEquals(expectedViewLog, this.getViewLog());
  }

  @Test
  public void inValidDatePortfolioValueTest() {
    String expectedModelLog = "";
    String expectedViewLog = "Invalid date format. Please enter a valid date in the format " +
            "yyyy-MM-dd";
    features.costBasis("PortfolioName", "bjdhbfbsjfb");
    assertEquals(expectedModelLog, this.getModelLog());
    assertEquals(expectedViewLog, this.getViewLog());
  }


  @Test
  public void inValidDateFormatPortfolioValueTest() {
    String expectedModelLog = "";
    String expectedViewLog = "Invalid date format. Please enter a valid date in the format" +
            " yyyy-MM-dd";
    features.costBasis("PortfolioName", "20-02-2004");
    assertEquals(expectedModelLog, this.getModelLog());
    assertEquals(expectedViewLog, this.getViewLog());
  }

  @Test
  public void inValidDateSeparatorPortfolioValueTest() {
    String expectedModelLog = "";
    String expectedViewLog = "Invalid date format. Please enter a valid date in the format" +
            " yyyy-MM-dd";
    features.costBasis("PortfolioName", "20/02/2004");
    assertEquals(expectedModelLog, this.getModelLog());
    assertEquals(expectedViewLog, this.getViewLog());
  }

  @Test
  public void inValidDayInDatevTest() {
    String expectedModelLog = "";
    String expectedViewLog = "Invalid date format. Please enter a valid date in the format" +
            " yyyy-MM-dd";
    features.costBasis("PortfolioName", "2004-03-32");
    assertEquals(expectedModelLog, this.getModelLog());
    assertEquals(expectedViewLog, this.getViewLog());
  }

  @Test
  public void inValidMonthInDatePortfolioValueTest() {
    String expectedModelLog = "";
    String expectedViewLog = "Invalid date format. Please enter a valid date in the format " +
            "yyyy-MM-dd";
    features.costBasis("PortfolioName", "2004-14-32");
    assertEquals(expectedModelLog, this.getModelLog());
    assertEquals(expectedViewLog, this.getViewLog());
  }


  @Test
  public void testInValidInvestFixedAmountInValidWeightSumTest() {
    String expectedModelLog = "";
    String expectedViewLog = "Weights do not add up to 100";
    HashMap<String, Integer> data = new HashMap<>();
    data.put("GOOGL", 20);
    data.put("AAPL", 40);
    data.put("TSLA", 60);
    features.investFixedAmount("PortfolioName", 100, "2018-11-11", data);
    assertEquals(expectedModelLog, this.getModelLog());
    assertEquals(expectedViewLog, this.getViewLog());
  }

  @Test
  public void testValidInvestFixedAmountValidWeightSumTest() {
    String expectedModelLog = "get stock price called with tickerSymbol:GOOGL date:2018-11-11 " +
            "atClosing:true add stock called with ticker:GOOGL quantity:0.4 date:2018-11-11" +
            " portfolioName:PortfolioName get stock price called with tickerSymbol:AAPL " +
            "date:2018-11-11 atClosing:true add stock called with ticker:AAPL quantity:0.8 " +
            "date:2018-11-11 portfolioName:PortfolioName get stock price called with " +
            "tickerSymbol:TSLA date:2018-11-11 atClosing:true add stock called with " +
            "ticker:TSLA quantity:0.8 date:2018-11-11 portfolioName:PortfolioName";
    String expectedViewLog = "Investment Successful";
    HashMap<String, Integer> data = new HashMap<>();
    data.put("GOOGL", 20);
    data.put("AAPL", 40);
    data.put("TSLA", 40);
    this.model.setWeightedAddStock();
    features.investFixedAmount("PortfolioName", 100, "2018-11-11", data);
    assertEquals(expectedModelLog, this.getModelLog());
    assertEquals(expectedViewLog, this.getViewLog());
  }


  @Test
  public void testInValidInvestFixedAmountInvalidDateTest() {
    String expectedModelLog = "";
    String expectedViewLog = "Invalid date format. Please enter a valid date in the" +
            " format yyyy-MM-dd";
    HashMap<String, Integer> data = new HashMap<>();
    data.put("GOOGL", 20);
    data.put("AAPL", 40);
    data.put("TSLA", 40);
    this.model.setWeightedAddStock();
    features.investFixedAmount("PortfolioName", 100, "20-01-2018", data);
    assertEquals(expectedModelLog, this.getModelLog());
    assertEquals(expectedViewLog, this.getViewLog());
  }

  @Test
  public void testInValidInvestInvalidTickerInvalidDateTest() {
    String expectedModelLog = "get stock price called with tickerSymbol:GOOGL date:" +
            "2024-04-04 atClosing:true add stock called with ticker:GOOGL quantity:0.4" +
            " date:2024-04-04 portfolioName:PortfolioName get stock price called with" +
            " tickerSymbol:AAPL date:2024-04-04 atClosing:true add stock called with" +
            " ticker:AAPL quantity:0.8 date:2024-04-04 portfolioName:PortfolioName";
    String expectedViewLog = "Stock not found";
    HashMap<String, Integer> data = new HashMap<>();
    this.model.addSValidStocks("GOOGL");
    this.model.addSValidStocks("AAPL");
    data.put("GOOGL", 20);
    data.put("AAPL", 40);
    data.put("InvalidTicker", 40);
    this.model.setWeightedAddStock();
    features.investFixedAmount("PortfolioName", 100, "2024-04-04", data);
    assertEquals(expectedModelLog, this.getModelLog());
    assertEquals(expectedViewLog, this.getViewLog());
  }

  @Test
  public void testCrossOverDays() {
    LocalDate start = LocalDate.of(2024, 3, 15);
    LocalDate end = LocalDate.of(2024, 3, 18);
    features.crossoverDays("Test", start, end);
    String expectedModelLog = "get stock price called with tickerSymbol:Test date:2024-03-15"
            + " atClosing:true "
            + "get stock prices called with ticker:Testdate:2024-03-15"
            + "atClosing:true "
            + "get stock price called with tickerSymbol:Test date:2024-03-16"
            + " atClosing:true "
            + "get stock prices called with ticker:Testdate:2024-03-16"
            + "atClosing:true "
            + "get stock price called with tickerSymbol:Test date:2024-03-17"
            + " atClosing:true "
            + "get stock prices called with ticker:Testdate:2024-03-17"
            + "atClosing:true "
            + "get stock price called with tickerSymbol:Test date:2024-03-18 "
            + "atClosing:true "
            + "get stock prices called with ticker:Testdate:2024-03-18"
            + "atClosing:true";
    String expectedViewLog = "dates and values:{} title: Crossover signals description:"
            + "Dates arecalculated using 30 day moving average.";
    assertEquals(expectedModelLog, getModelLog());
    assertEquals(expectedViewLog, getViewLog());
  }

  @Test
  public void testLoadPortfolioInvalidPath() {
    features.loadPortfolio(new File("test/path/filename.csv"));
    String expectedModelLog = "is portfolio present called with portfolioName:filename";
    String expectedViewLog = "Invalid file path. Please enter a valid file path.";
    assertEquals(expectedModelLog, getModelLog());
    assertEquals(expectedViewLog, getViewLog());
  }

  @Test
  public void testLoadPortfolioValidPath() {
    features.loadPortfolio(new File("res/example_files/f1.csv"));
    String expectedModelLog = "create portfolio called with portfolioName:f1 "
            + "add stock called with ticker:NFLX quantity:2.0 date:2024-03-26"
            + " portfolioName:f1"
            + " is portfolio present called with portfolioName:f1";
    assertEquals(expectedModelLog, getModelLog());
    String expectedViewLog = "Portfolio Loaded Successfully";
    assertEquals(expectedViewLog, getViewLog());
  }

  @Test
  public void testMovingCrossoverDays() {
    LocalDate start = LocalDate.of(2024, 3, 15);
    LocalDate end = LocalDate.of(2024, 3, 18);
    model.addSValidStocks("test");
    LocalDate x;
    for (x = start; x.isBefore(end.plusDays(1));
         x = x.plusDays(1)) {
      model.setValidDataExists(x, 60);
    }
    features.movingCrossOverDays("test", 1, 2, start, end);
    String expectedModelLog = "get stock prices called with ticker:testdate:2024-03-15at" +
            "Closing:true get stock prices called with ticker:testdate:2024-03-15atClosing:" +
            "true get stock prices called with ticker:testdate:2024-03-16atClosing:true get " +
            "stock prices called with ticker:testdate:2024-03-16atClosing:true get stock prices " +
            "called with ticker:testdate:2024-03-17atClosing:true get stock prices called with" +
            " ticker:testdate:2024-03-17atClosing:true get stock prices called with ticker:" +
            "testdate:2024-03-18atClosing:true get stock prices called with ticker:testdate:" +
            "2024-03-18atClosing:true";
    String expectedViewLog = "dates and values:{} title: Moving crossover signals. description:" +
            "Dates are calculated using 1 and 2 moving averages.";
    assertEquals(expectedModelLog, this.getModelLog());
    assertEquals(expectedViewLog, this.getViewLog());
  }

  @Test
  public void testXDayMovingAverage() {
    model.addSValidStocks("test");
    LocalDate start = LocalDate.of(2024, 3, 15);
    model.setValidDataExists(start, 15);
    features.xDayMovingAverage("test", 10, start);
    String expectedModelLog = "get stock prices called with "
            + "ticker:testdate:2024-03-15atClosing:true";
    String expectedViewLog = "The moving average of the stock test on 2024-03-15 is $107.00";
    assertEquals(expectedModelLog, getModelLog());
    assertEquals(expectedViewLog, getViewLog());
  }

  @Test
  public void testStockPerformanceNet() {
    LocalDate start = LocalDate.of(2024, 3, 15);
    LocalDate end = LocalDate.of(2024, 3, 18);
    features.stockPerformanceNet("test", start, end);
    String expectedModelLog = "get stock price called with tickerSymbol:test date:2024-03-15"
            + " atClosing:true"
            + " get stock price called with tickerSymbol:test date:2024-03-18 "
            + "atClosing:true";
    String expectedViewLog = "Start: 2024-03-15 End: 2024-03-18 Asset: test "
            + "Start Value: 0.0 End Value: 0.0";
    assertEquals(expectedModelLog, getModelLog());
    assertEquals(expectedViewLog, getViewLog());
    //Test that if the same day is given, the other method is called
    features.stockPerformanceNet("test", start, start);
    expectedModelLog = "get stock price called with tickerSymbol:test date:2024-03-15"
            + " atClosing:true"
            + " get stock price called with tickerSymbol:test date:2024-03-18"
            + " atClosing:true"
            + " get stock price called with tickerSymbol:test date:2024-03-15"
            + " atClosing:false "
            + "get stock price called with tickerSymbol:test date:2024-03-15"
            + " atClosing:true";
    expectedViewLog = "Start: 2024-03-15 End: 2024-03-18 Asset: test Start Value: 0.0"
            + " End Value: 0.0 Start: 2024-03-15 End: 2024-03-15 "
            + "Asset: test Start Value: 0.0 End Value: 0.0";
    assertEquals(expectedModelLog, getModelLog());
    assertEquals(expectedViewLog, getViewLog());
  }

  @Test
  public void testDollarCostAverage() {
    LocalDate start = LocalDate.of(2024, 3, 15);
    LocalDate end = LocalDate.of(2024, 3, 18);
    Map<String, Integer> testSplit = new HashMap<>();
    testSplit.put("TSLA", 50);
    testSplit.put("IRTC", 50);
    boolean b = features.dollarCostAverage("test", new LocalDate[]{start, end},
            100.00, testSplit, ChronoUnit.WEEKS, 1);
    String expectedModelLog = "added DCA with: name: test, split: {TSLA=50.0, IRTC=50.0},"
            + " startDate: 2024-03-15, unit: Weeks, length: 1, "
            + "reps: 0 is portfolio present called with portfolioName:test";
    String expectedViewLog = "Dollar Cost Averaging strategy for 'test' initiated successfully.";
    assertEquals(expectedModelLog, getModelLog());
    assertEquals(expectedViewLog, getViewLog());
    assertTrue(b);
  }

  @Test
  public void testListPortfolios() {
    String[] actualArr = features.listPortfolios();
    String[] expectedArr = new String[]{"Example"};
    assertArrayEquals(expectedArr, actualArr);
  }


}
