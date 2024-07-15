package controllertest;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import controller.IMainController;
import controller.OldMainMenu;
import controller.commands.AnalyzePortfolioPerformance;
import controller.commands.AnalyzeStockPerformance;
import controller.commands.ICommand;
import view.text.GraphingTextView;
import view.text.IGraphingView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for testing all the statistical Stock operations and its respective commands .
 * which including DayStockPerformance,
 * PeriodStockPerformance, XMovingAverage, CrossOverDays, MovingAverageCrossOver,
 * AnalyzeStockPerformance, AnalyzePortfolioPerformance.
 */

public class StockStatsTest {

  private ICommand c;
  private MockModel m;
  private MockView v;
  private static final LocalDate start = LocalDate.of(2024, 3, 15);
  private static final LocalDate end = LocalDate.of(2024, 4, 9);

  @Test
  public void dayStockPerformanceValidInputsTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("10\nAAPL\n2024-03-19\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "get stock price called with tickerSymbol:AAPL date:2024-03-19 " +
            "atClosing:false get stock price called with tickerSymbol:AAPL " +
            "date:2024-03-19 atClosing:true";
    String expectedViewResult = "Main Menu Choose an option: Enter the ticker symbol " +
            "of the stock Enter the date in the format yyyy-MM-dd Start: 2024-03-19 End: " +
            "2024-03-19 Asset: AAPL Start Value: 0.0 End Value: 0.0 Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void dayStockPerformanceInvalidTickerNullTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("10\n\nAAPL\n2024-03-19\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "get stock price called with tickerSymbol:AAPL date:" +
            "2024-03-19 atClosing:false get stock price called with tickerSymbol:AAPL " +
            "date:2024-03-19 atClosing:true";
    String expectedViewResult = "Main Menu Choose an option: Enter the ticker symbol of " +
            "the stock Ticker symbol cannot be empty! Enter the ticker symbol of the stock " +
            "Enter the date in the format yyyy-MM-dd Start: 2024-03-19 End: 2024-03-19 " +
            "Asset: AAPL Start Value: 0.0 End Value: 0.0 Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void dayStockPerformanceInvalidTickerMultipleInvalidTickerTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("10\n\n    \nAAPL\n2024-03-19\nq").
            getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "get stock price called with tickerSymbol:AAPL date:2024-03-19 " +
            "atClosing:false get stock price called with tickerSymbol:AAPL " +
            "date:2024-03-19 atClosing:true";
    String expectedViewResult = "Main Menu Choose an option: Enter the ticker " +
            "symbol of the stock Ticker symbol cannot be empty! Enter the ticker " +
            "symbol of the stock Ticker symbol cannot be empty! Enter the ticker symbol " +
            "of the stock Enter the date in the format yyyy-MM-dd Start: 2024-03-19 End: " +
            "2024-03-19 Asset: AAPL Start Value: 0.0 End Value: 0.0 Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void dayStockPerformanceInvalidTickerNonValidTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    model.addSValidStocks("AAPL");
    InputStream simulatedInput = new ByteArrayInputStream(
            ("10\n\n \nNLFX\n2024-03-19\n10\n\n \nAAPL\n2024-03-19\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "get stock price called with tickerSymbol:AAPL date:2024-03-19 " +
            "atClosing:false get stock price called with tickerSymbol:AAPL " +
            "date:2024-03-19 atClosing:true";
    String expectedViewResult = "Main Menu Choose an option: Enter the ticker symbol " +
            "of the stock Ticker symbol cannot be empty! Enter the ticker symbol of the " +
            "stock Ticker symbol cannot be empty! Enter the ticker symbol of the stock " +
            "Enter the date in the format yyyy-MM-dd Stock not found Main Menu Choose an " +
            "option: Enter the ticker symbol of the stock Ticker symbol cannot be empty! " +
            "Enter the ticker symbol of the stock Ticker symbol cannot be empty! Enter the " +
            "ticker symbol of the stock Enter the date in the format yyyy-MM-dd Start: " +
            "2024-03-19 End: 2024-03-19 Asset: AAPL Start Value: 0.0 End Value: 0.0 " +
            "Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void dayStockPerformanceInvalidDateFormat() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("10\nAAPL\n2024/03/19\n2024-03-19\nq").
            getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "get stock price called with tickerSymbol:AAPL d" +
            "ate:2024-03-19 atClosing:false get stock price called with tickerSymbol:" +
            "AAPL date:2024-03-19 atClosing:true";
    String expectedViewResult = "Main Menu Choose an option: Enter the ticker symbol " +
            "of the stock Enter the date in the format yyyy-MM-dd Invalid Date. " +
            "Please enter a valid date in the format yyyy-MM-dd Enter the date " +
            "in the format yyyy-MM-dd Start: 2024-03-19 End: 2024-03-19 Asset: " +
            "AAPL Start Value: 0.0 End Value: 0.0 Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void dayStockPerformanceInvalidMonthTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("10\nAAPL\n2024-123-19\n2024-03-19\nq").
            getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "get stock price called with tickerSymbol:AAPL " +
            "date:2024-03-19 atClosing:false get stock price called with tickerSymbol:" +
            "AAPL date:2024-03-19 atClosing:true";
    String expectedViewResult = "Main Menu Choose an option: Enter the ticker symbol " +
            "of the stock Enter the date in the format yyyy-MM-dd Invalid Date. " +
            "Please enter a valid date in the format yyyy-MM-dd Enter the date in the " +
            "format yyyy-MM-dd Start: 2024-03-19 End: 2024-03-19 Asset: AAPL Start " +
            "Value: 0.0 End Value: 0.0 Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void dayStockPerformanceInvalidDateTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("10\nAAPL\n2024-01-119\n2024-03-19\nq").
            getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "get stock price called with tickerSymbol:" +
            "AAPL date:2024-03-19 atClosing:false get stock price called with " +
            "tickerSymbol:AAPL date:2024-03-19 atClosing:true";
    String expectedViewResult = "Main Menu Choose an option: Enter the ticker " +
            "symbol of the stock Enter the date in the format yyyy-MM-dd Invalid Date. " +
            "Please enter a valid date in the format yyyy-MM-dd Enter the date in the " +
            "format yyyy-MM-dd Start: 2024-03-19 End: 2024-03-19 Asset: AAPL Start " +
            "Value: 0.0 End Value: 0.0 Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void dayStockPerformanceInvalidEmptyTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("10\nAAPL" +
            "\n \n2024-03-19\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "get stock price called with tickerSymbol:AAPL " +
            "date:2024-03-19 atClosing:false get stock price called with tickerSymbol:" +
            "AAPL date:2024-03-19 atClosing:true";
    String expectedViewResult = "Main Menu Choose an option: Enter the ticker symbol of t" +
            "he stock Enter the date in the format yyyy-MM-dd Invalid Date. Please enter a " +
            "valid date in the format yyyy-MM-dd Enter the date in the format yyyy-MM-dd " +
            "Start: 2024-03-19 End: 2024-03-19 Asset: AAPL Start Value: 0.0 End Value: 0.0 " +
            "Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void dayStockPerformanceInvalidNullTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("10\nAAPL\n\n2024-03-19\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "get stock price called with tickerSymbol:AAPL date:" +
            "2024-03-19 atClosing:false get stock price called with tickerSymbol:AAPL " +
            "date:2024-03-19 atClosing:true";
    String expectedViewResult = "Main Menu Choose an option: Enter the ticker symbol " +
            "of the stock Enter the date in the format yyyy-MM-dd Invalid Date. " +
            "Please enter a valid date in the format yyyy-MM-dd Enter the date in " +
            "the format yyyy-MM-dd Start: 2024-03-19 End: 2024-03-19 Asset: AAPL " +
            "Start Value: 0.0 End Value: 0.0 Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void daysStockPerformanceTestIntegrationTesting() {
    ByteArrayOutputStream viewOutput = new ByteArrayOutputStream();
    IGraphingView view = new GraphingTextView(viewOutput);
    InputStream simulatedInput = new ByteArrayInputStream(("10\nAAPL\n2024-03-19\nq").getBytes());
    IMainController controller = new OldMainMenu(null, view, simulatedInput);
    controller.execute();
    assertEquals("Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the ticker symbol of the stock\n" +
            "Enter the date in the format yyyy-MM-dd\n" +
            "AAPL increased by $1.74 on 19 Mar 2024\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n", viewOutput.toString());
  }

  @Test
  public void TestValidInputTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    LocalDate date = LocalDate.of(2024, 1, 19);
    model.setValidDataExists(date, 20);
    InputStream simulatedInput = new ByteArrayInputStream(("12\nAAPL\n20\n" +
            "2024-01-19\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "get stock prices called with ticker:AAPLdate:2024-01-19" +
            "atClosing:true get stock prices called with ticker:AAPLdate:2024-01-19atClosing:true";
    String expectedViewResult = "Main Menu Choose an option: Enter the ticker symbol of the " +
            "stock Enter the the no of days for moving average Enter the date in the format " +
            "yyyy-MM-dd The moving average of the stock AAPL on 2024-01-19 is $109.50 Main " +
            "Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void TestInvalidDateInputTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    LocalDate date1 = LocalDate.of(2024, 3, 19);
    model.setValidDataExists(date1, 20);
    LocalDate date2 = LocalDate.of(2024, 3, 9);
    model.setValidDataExists(date2, 30);
    InputStream simulatedInput =
            new ByteArrayInputStream(("12\nAAPL\n30\n2024-03-01\n2024-03-09\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "get stock prices called with ticker:" +
            "AAPLdate:2024-03-01atClosing:true get stock prices called " +
            "with ticker:AAPLdate:2024-03-09atClosing:true get stock prices " +
            "called with ticker:AAPLdate:2024-03-09atClosing:true";
    String expectedViewResult = "Main Menu Choose an option: Enter the ticker symbol of the " +
            "stock Enter the the no of days for moving average Enter the date in the format " +
            "yyyy-MM-dd Invalid Date. The dates entered should be valid and the data should " +
            "be available for the given days. Enter the date in the format yyyy-MM-dd The " +
            "moving average of the stock AAPL on 2024-03-09 is $114.50 Main Menu Choose an " +
            "option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void TestInvalidInputEmptyDateTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    LocalDate date = LocalDate.of(2024, 1, 19);
    model.setValidDataExists(date, 20);
    InputStream simulatedInput = new ByteArrayInputStream(("12\nAAPL\n20\n \n" +
            "2024-01-19\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "get stock prices called with ticker:" +
            "AAPLdate:2024-01-19atClosing:true get stock prices called with ticker:" +
            "AAPLdate:2024-01-19atClosing:true";
    String expectedViewResult = "Main Menu Choose an option: Enter the ticker symbol of the " +
            "stock Enter the the no of days for moving average Enter the date " +
            "in the format yyyy-MM-dd Invalid Date. The dates entered should be " +
            "valid and the data should be available for the given days. Enter the " +
            "date in the format yyyy-MM-dd The moving average of the stock AAPL on " +
            "2024-01-19 is $109.50 Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void TestInvalidInputNullDateTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    LocalDate date = LocalDate.of(2024, 1, 19);
    model.setValidDataExists(date, 20);
    InputStream simulatedInput = new ByteArrayInputStream(("12\nAAPL\n20\n\n" +
            "2024-01-19\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "get stock prices called with ticker:AAPLdate:2024-01-19at" +
            "Closing:true get stock prices called with ticker:AAPLdate:2024-01-19atClosing:true";
    String expectedViewResult = "Main Menu Choose an option: Enter the ticker symbol of the " +
            "stock Enter the the no of days for moving average Enter the date in the " +
            "format yyyy-MM-dd Invalid Date. The dates entered should be valid and the " +
            "data should be available for the given days. Enter the date in the format " +
            "yyyy-MM-dd The moving average of the stock AAPL on 2024-01-19 is $109.50 " +
            "Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void TestInvalidInputInvalidDateFormatTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    LocalDate date = LocalDate.of(2024, 1, 19);
    model.setValidDataExists(date, 20);
    InputStream simulatedInput = new ByteArrayInputStream(("12\nAAPL\n20\n" +
            "2012/12/11\n2024-01-19\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "get stock prices called with ticker:AAPLdate:2024-01-19at" +
            "Closing:true get stock prices called with ticker:AAPLdate:2024-01-19atClosing:true";
    String expectedViewResult = "Main Menu Choose an option: Enter the ticker symbol of the " +
            "stock Enter the the no of days for moving average Enter the date in the " +
            "format yyyy-MM-dd Invalid Date. The dates entered should be valid and the " +
            "data should be available for the given days. Enter the date in the format " +
            "yyyy-MM-dd The moving average of the stock AAPL on 2024-01-19 is $109.50 " +
            "Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void TestInvalidInputInvalidDateTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    LocalDate date = LocalDate.of(2024, 1, 19);
    model.setValidDataExists(date, 20);
    InputStream simulatedInput = new ByteArrayInputStream(("12\nAAPL\n20\n2012-12-111\n" +
            "2024-01-19\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "get stock prices called with ticker:AAPLdate:2024-01-19at" +
            "Closing:true get stock prices called with ticker:AAPLdate:2024-01-19atClosing:true";
    String expectedViewResult = "Main Menu Choose an option: Enter the ticker symbol of the " +
            "stock Enter the the no of days for moving average Enter the date in the " +
            "format yyyy-MM-dd Invalid Date. The dates entered should be valid and the " +
            "data should be available for the given days. Enter the date in the format " +
            "yyyy-MM-dd The moving average of the stock AAPL on 2024-01-19 is $109.50 " +
            "Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void TestInvalidInputInvalidMonthTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    LocalDate date = LocalDate.of(2024, 1, 19);
    model.setValidDataExists(date, 20);
    InputStream simulatedInput = new ByteArrayInputStream(("12\nAAPL\n20\n2012-112-111\n" +
            "2024-01-19\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "get stock prices called with ticker:AAPLdate:2024-01-19at" +
            "Closing:true get stock prices called with ticker:AAPLdate:2024-01-19atClosing:true";
    String expectedViewResult = "Main Menu Choose an option: Enter the ticker symbol of the " +
            "stock Enter the the no of days for moving average Enter the date in the " +
            "format yyyy-MM-dd Invalid Date. The dates entered should be valid and the " +
            "data should be available for the given days. Enter the date in the format " +
            "yyyy-MM-dd The moving average of the stock AAPL on 2024-01-19 is $109.50 " +
            "Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void TestInValidInputEmptyTickerTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    LocalDate date = LocalDate.of(2024, 1, 19);
    model.setValidDataExists(date, 20);
    InputStream simulatedInput = new ByteArrayInputStream(("12\n \n" +
            "AAPL\n20\n2024-01-19\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "get stock prices called with ticker:AAPLdate:" +
            "2024-01-19atClosing:true get stock prices called with ticker:AAPLdate:" +
            "2024-01-19atClosing:true";
    String expectedViewResult = "Main Menu Choose an option: Enter the ticker symbol of" +
            " the stock Ticker symbol cannot be empty! Enter the ticker symbol of the" +
            " stock Enter the the no of days for moving average Enter the date in the" +
            " format yyyy-MM-dd The moving average of the stock AAPL on 2024-01-19 is" +
            " $109.50 Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void TestInValidInputNullTickerTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    LocalDate date = LocalDate.of(2024, 1, 19);
    model.setValidDataExists(date, 20);
    InputStream simulatedInput = new ByteArrayInputStream(("12\n \nAAPL\n20\n" +
            "2024-01-19\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "get stock prices called with ticker:AAPLdate:" +
            "2024-01-19atClosing:true get stock prices called with ticker:AAPLdate:" +
            "2024-01-19atClosing:true";
    String expectedViewResult = "Main Menu Choose an option: Enter the ticker symbol of" +
            " the stock Ticker symbol cannot be empty! Enter the ticker symbol of the" +
            " stock Enter the the no of days for moving average Enter the date in the" +
            " format yyyy-MM-dd The moving average of the stock AAPL on 2024-01-19 is" +
            " $109.50 Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void XDaysMovingAverageTestIntegrationTesting() {
    ByteArrayOutputStream viewOutput = new ByteArrayOutputStream();
    IGraphingView view = new GraphingTextView(viewOutput);
    InputStream simulatedInput = new ByteArrayInputStream(("12\nAAPL\n20\n" +
            "2024-03-19\nq").getBytes());
    IMainController controller = new OldMainMenu(null, view, simulatedInput);
    controller.execute();
    assertEquals("Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the ticker symbol of the stock\n" +
            "Enter the the no of days for moving average\n" +
            "Enter the date in the format yyyy-MM-dd\n" +
            "The moving average of the stock AAPL on 2024-03-19 is $176.07\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n", viewOutput.toString());
  }

  @Test
  public void testGetCrossDaysValidInputs() {
    MockModel model = new MockModel();
    model.addSValidStocks("AAPL");
    MockView view = new MockView();
    LocalDate date = LocalDate.of(2024, 1, 19);
    model.setValidDataExists(date, 30);
    InputStream simulatedInput = new ByteArrayInputStream(("13\nAAPL\n2024-01-19\n" +
            "2024-02-15\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "get stock prices called with ticker:" +
            "AAPLdate:2024-01-19atClosing:true get stock price called " +
            "with tickerSymbol:AAPL date:2024-01-19 atClosing:true " +
            "get stock prices called with ticker:AAPLdate:2024-01-19at" +
            "Closing:true get stock price called with tickerSymbol:AAPL " +
            "date:2024-01-20 atClosing:true get stock prices called with " +
            "ticker:AAPLdate:2024-01-20atClosing:true get stock price called " +
            "with tickerSymbol:AAPL date:2024-01-21 atClosing:true get stock " +
            "prices called with ticker:AAPLdate:2024-01-21atClosing:true get " +
            "stock price called with tickerSymbol:AAPL date:2024-01-22 atClosing:" +
            "true get stock prices called with ticker:AAPLdate:2024-01-22atClosing:true get " +
            "stock price called with tickerSymbol:AAPL date:2024-01-23 atClosing:true get " +
            "stock prices called with ticker:AAPLdate:2024-01-23atClosing:true get stock " +
            "price called with tickerSymbol:AAPL date:2024-01-24 atClosing:true get stock " +
            "prices called with ticker:AAPLdate:2024-01-24atClosing:true get stock price " +
            "called with tickerSymbol:AAPL date:2024-01-25 atClosing:true get stock prices" +
            " called with ticker:AAPLdate:2024-01-25atClosing:true get stock price called" +
            " with tickerSymbol:AAPL date:2024-01-26 atClosing:true get stock prices called" +
            " with ticker:AAPLdate:2024-01-26atClosing:true get stock price called with" +
            " tickerSymbol:AAPL date:2024-01-27 atClosing:true get stock prices called" +
            " with ticker:AAPLdate:2024-01-27atClosing:true get stock price called with" +
            " tickerSymbol:AAPL date:2024-01-28 atClosing:true get stock prices called" +
            " with ticker:AAPLdate:2024-01-28atClosing:true get stock price called with" +
            " tickerSymbol:AAPL date:2024-01-29 atClosing:true get stock prices called" +
            " with ticker:AAPLdate:2024-01-29atClosing:true get stock price called with" +
            " tickerSymbol:AAPL date:2024-01-30 atClosing:true get stock prices called" +
            " with ticker:AAPLdate:2024-01-30atClosing:true get stock price called with" +
            " tickerSymbol:AAPL date:2024-01-31 atClosing:true get stock prices called" +
            " with ticker:AAPLdate:2024-01-31atClosing:true get stock price called with" +
            " tickerSymbol:AAPL date:2024-02-01 atClosing:true get stock prices called" +
            " with ticker:AAPLdate:2024-02-01atClosing:true get stock price called with" +
            " tickerSymbol:AAPL date:2024-02-02 atClosing:true get stock prices called" +
            " with ticker:AAPLdate:2024-02-02atClosing:true get stock price called with" +
            " tickerSymbol:AAPL date:2024-02-03 atClosing:true get stock prices called" +
            " with ticker:AAPLdate:2024-02-03atClosing:true get stock price called with" +
            " tickerSymbol:AAPL date:2024-02-04 atClosing:true get stock prices called" +
            " with ticker:AAPLdate:2024-02-04atClosing:true get stock price called with" +
            " tickerSymbol:AAPL date:2024-02-05 atClosing:true get stock prices called" +
            " with ticker:AAPLdate:2024-02-05atClosing:true get stock price called with" +
            " tickerSymbol:AAPL date:2024-02-06 atClosing:true get stock prices called" +
            " with ticker:AAPLdate:2024-02-06atClosing:true get stock price called with" +
            " tickerSymbol:AAPL date:2024-02-07 atClosing:true get stock prices called" +
            " with ticker:AAPLdate:2024-02-07atClosing:true get stock price called with" +
            " tickerSymbol:AAPL date:2024-02-08 atClosing:true get stock prices called" +
            " with ticker:AAPLdate:2024-02-08atClosing:true get stock price called with" +
            " tickerSymbol:AAPL date:2024-02-09 atClosing:true get stock prices called" +
            " with ticker:AAPLdate:2024-02-09atClosing:true get stock price called with" +
            " tickerSymbol:AAPL date:2024-02-10 atClosing:true get stock prices called" +
            " with ticker:AAPLdate:2024-02-10atClosing:true get stock price called with" +
            " tickerSymbol:AAPL date:2024-02-11 atClosing:true get stock prices called" +
            " with ticker:AAPLdate:2024-02-11atClosing:true get stock price called with" +
            " tickerSymbol:AAPL date:2024-02-12 atClosing:true get stock prices called" +
            " with ticker:AAPLdate:2024-02-12atClosing:true get stock price called with" +
            " tickerSymbol:AAPL date:2024-02-13 atClosing:true get stock prices called" +
            " with ticker:AAPLdate:2024-02-13atClosing:true get stock price called with" +
            " tickerSymbol:AAPL date:2024-02-14 atClosing:true get stock prices called" +
            " with ticker:AAPLdate:2024-02-14atClosing:true get stock price called with" +
            " tickerSymbol:AAPL date:2024-02-15 atClosing:true get stock prices called" +
            " with ticker:AAPLdate:2024-02-15atClosing:true";

    String expectedViewResult = "Main Menu Choose an option: Enter the ticker symbol" +
            " of the stock Enter the start Date in the format yyyy-MM-dd Enter the date" +
            " in the format yyyy-MM-dd dates and values:{} title: Crossover signals description:" +
            "Dates arecalculated using 30 day moving average. Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testGetCrossDaysIntegrationTest() {
    ByteArrayOutputStream viewOutput = new ByteArrayOutputStream();
    IGraphingView view = new GraphingTextView(viewOutput);
    InputStream simulatedInput = new ByteArrayInputStream(("13\nAAPL\n2024-01-19\n2024-02-15\nq").
            getBytes());
    IMainController controller = new OldMainMenu(null, view, simulatedInput);
    controller.execute();
    String expectedViewOutput = "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the ticker symbol of the stock\n" +
            "Enter the start Date in the format yyyy-MM-dd\n" +
            "Enter the date in the format yyyy-MM-dd\n" +
            "Report: Crossover signals\n" +
            "30 Jan 2024 : SELL\n" +
            "06 Feb 2024 : BUY\n" +
            "12 Feb 2024 : SELL\n" +
            "Dates arecalculated using 30 day moving average.\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n";
    assertEquals(expectedViewOutput, viewOutput.toString());
  }


  @Test
  public void testGetCrossDaysInvalidTickerIntegrationTest() {
    ByteArrayOutputStream viewOutput = new ByteArrayOutputStream();
    IGraphingView view = new GraphingTextView(viewOutput);
    InputStream simulatedInput = new ByteArrayInputStream(("13\nAPPLE\nAAPL\n2024-01-19\n" +
            "2024-02-15\nq").getBytes());
    IMainController controller = new OldMainMenu(null, view, simulatedInput);
    controller.execute();
    String expectedViewOutput = "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the ticker symbol of the stock\n" +
            "Invalid Ticker Symbol!Please Provide a valid ticker Symbol!\n" +
            "Enter the ticker symbol of the stock\n" +
            "Enter the start Date in the format yyyy-MM-dd\n" +
            "Enter the date in the format yyyy-MM-dd\n" +
            "Report: Crossover signals\n" +
            "30 Jan 2024 : SELL\n" +
            "06 Feb 2024 : BUY\n" +
            "12 Feb 2024 : SELL\n" +
            "Dates arecalculated using 30 day moving average.\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n";
    assertEquals(expectedViewOutput, viewOutput.toString());
  }


  @Test
  public void testGetCrossDaysNullTicker() {
    MockModel model = new MockModel();
    model.addSValidStocks("AAPL");
    MockView view = new MockView();
    LocalDate date = LocalDate.of(2024, 1, 19);
    model.setValidDataExists(date, 30);
    InputStream simulatedInput = new ByteArrayInputStream(("13\n\nAAPL\n2024-01-19\n" +
            "2024-02-15\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "get stock prices called with " +
            "ticker:AAPLdate:2024-01-19atClosing:true get stock " +
            "price called with tickerSymbol:AAPL date:2024-01-19 " +
            "atClosing:true get stock prices called with ticker:" +
            "AAPLdate:2024-01-19atClosing:true get stock price " +
            "called with tickerSymbol:AAPL date:2024-01-20 " +
            "atClosing:true get stock prices called with " +
            "ticker:AAPLdate:2024-01-20atClosing:true get " +
            "stock price called with tickerSymbol:AAPL " +
            "date:2024-01-21 atClosing:true get stock prices " +
            "called with ticker:AAPLdate:2024-01-21atClosing:true get stock price called " +
            "with tickerSymbol:AAPL date:2024-01-22 atClosing:true get stock prices called" +
            " with ticker:AAPLdate:2024-01-22atClosing:true get stock price called with" +
            " tickerSymbol:AAPL date:2024-01-23 atClosing:true get stock prices called" +
            " with ticker:AAPLdate:2024-01-23atClosing:true get stock price called with" +
            " tickerSymbol:AAPL date:2024-01-24 atClosing:true get stock prices called" +
            " with ticker:AAPLdate:2024-01-24atClosing:true get stock price called with" +
            " tickerSymbol:AAPL date:2024-01-25 atClosing:true get stock prices called" +
            " with ticker:AAPLdate:2024-01-25atClosing:true get stock price called " +
            "with tickerSymbol:AAPL date:2024-01-26 atClosing:true get stock prices " +
            "called with ticker:AAPLdate:2024-01-26atClosing:true get stock price " +
            "called with tickerSymbol:AAPL date:2024-01-27 atClosing:true get stock " +
            "prices called with ticker:AAPLdate:2024-01-27atClosing:true get stock " +
            "price called with tickerSymbol:AAPL date:2024-01-28 atClosing:true get " +
            "stock prices called with ticker:AAPLdate:2024-01-28atClosing:true get " +
            "stock price called with tickerSymbol:AAPL date:2024-01-29 atClosing:true " +
            "get stock prices called with ticker:AAPLdate:2024-01-29atClosing:true get " +
            "stock price called with tickerSymbol:AAPL date:2024-01-30 atClosing:true " +
            "get stock prices called with ticker:AAPLdate:2024-01-30atClosing:true " +
            "get stock price called with tickerSymbol:AAPL date:2024-01-31 atClosing:" +
            "true get stock prices called with ticker:AAPLdate:2024-01-31atClosing:true" +
            " get stock price called with tickerSymbol:AAPL date:2024-02-01 atClosing:true" +
            " get stock prices called with ticker:AAPLdate:2024-02-01atClosing:true" +
            " get stock price called with tickerSymbol:AAPL date:2024-02-02 atClosing:true" +
            " get stock prices called with ticker:AAPLdate:2024-02-02atClosing:true get" +
            " stock price called with tickerSymbol:AAPL date:2024-02-03 atClosing:true" +
            " get stock prices called with ticker:AAPLdate:2024-02-03atClosing:true get" +
            " stock price called with tickerSymbol:AAPL date:2024-02-04 atClosing:true" +
            " get stock prices called with ticker:AAPLdate:2024-02-04atClosing:true get" +
            " stock price called with tickerSymbol:AAPL date:2024-02-05 atClosing:true" +
            " get stock prices called with ticker:AAPLdate:2024-02-05atClosing:true get" +
            " stock price called with tickerSymbol:AAPL date:2024-02-06 atClosing:true" +
            " get stock prices called with ticker:AAPLdate:2024-02-06atClosing:true get" +
            " stock price called with tickerSymbol:AAPL date:2024-02-07 atClosing:true" +
            " get stock prices called with ticker:AAPLdate:2024-02-07atClosing:true get" +
            " stock price called with tickerSymbol:AAPL date:2024-02-08 atClosing:true" +
            " get stock prices called with ticker:AAPLdate:2024-02-08atClosing:true get" +
            " stock price called with tickerSymbol:AAPL date:2024-02-09 atClosing:true" +
            " get stock prices called with ticker:AAPLdate:2024-02-09atClosing:true get" +
            " stock price called with tickerSymbol:AAPL date:2024-02-10 atClosing:true" +
            " get stock prices called with ticker:AAPLdate:2024-02-10atClosing:true get" +
            " stock price called with tickerSymbol:AAPL date:2024-02-11 atClosing:true" +
            " get stock prices called with ticker:AAPLdate:2024-02-11atClosing:true get" +
            " stock price called with tickerSymbol:AAPL date:2024-02-12 atClosing:true" +
            " get stock prices called with ticker:AAPLdate:2024-02-12atClosing:true get" +
            " stock price called with tickerSymbol:AAPL date:2024-02-13 atClosing:true get" +
            " stock prices called with ticker:AAPLdate:2024-02-13atClosing:true get stock" +
            " price called with tickerSymbol:AAPL date:2024-02-14 atClosing:true get stock" +
            " prices called with ticker:AAPLdate:2024-02-14atClosing:true get stock price" +
            " called with tickerSymbol:AAPL date:2024-02-15 atClosing:true get stock prices" +
            " called with ticker:AAPLdate:2024-02-15atClosing:true";
    String expectedViewResult = "Main Menu Choose an option: Enter the ticker symbol of the" +
            " stock" +
            " Invalid Ticker Symbol!Please Provide a valid ticker Symbol! Enter the ticker" +
            " symbol of the stock Enter the start Date in the format yyyy-MM-dd Enter the" +
            " date in the format yyyy-MM-dd dates and values:{} title: Crossover signals" +
            " description:Dates arecalculated using 30 day moving average." +
            " Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testGetCrossDaysEmptyTicker() {
    MockModel model = new MockModel();
    model.addSValidStocks("AAPL");
    MockView view = new MockView();
    LocalDate date = LocalDate.of(2024, 1, 19);
    model.setValidDataExists(date, 30);
    InputStream simulatedInput = new ByteArrayInputStream(("13\n\nAAPL\n2024-01-19\n" +
            "2024-02-15\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "get stock prices called with " +
            "ticker:AAPLdate:2024-01-19atClosing:true get stock " +
            "price called with tickerSymbol:AAPL date:2024-01-19 " +
            "atClosing:true get stock prices called with ticker:" +
            "AAPLdate:2024-01-19atClosing:true get stock price " +
            "called with tickerSymbol:AAPL date:2024-01-20 " +
            "atClosing:true get stock prices called with " +
            "ticker:AAPLdate:2024-01-20atClosing:true get " +
            "stock price called with tickerSymbol:AAPL " +
            "date:2024-01-21 atClosing:true get stock prices " +
            "called with ticker:AAPLdate:2024-01-21atClosing:true get stock price called " +
            "with tickerSymbol:AAPL date:2024-01-22 atClosing:true get stock prices called" +
            " with ticker:AAPLdate:2024-01-22atClosing:true get stock price called with" +
            " tickerSymbol:AAPL date:2024-01-23 atClosing:true get stock prices called" +
            " with ticker:AAPLdate:2024-01-23atClosing:true get stock price called with" +
            " tickerSymbol:AAPL date:2024-01-24 atClosing:true get stock prices called" +
            " with ticker:AAPLdate:2024-01-24atClosing:true get stock price called with" +
            " tickerSymbol:AAPL date:2024-01-25 atClosing:true get stock prices called" +
            " with ticker:AAPLdate:2024-01-25atClosing:true get stock price called " +
            "with tickerSymbol:AAPL date:2024-01-26 atClosing:true get stock prices " +
            "called with ticker:AAPLdate:2024-01-26atClosing:true get stock price " +
            "called with tickerSymbol:AAPL date:2024-01-27 atClosing:true get stock " +
            "prices called with ticker:AAPLdate:2024-01-27atClosing:true get stock " +
            "price called with tickerSymbol:AAPL date:2024-01-28 atClosing:true get " +
            "stock prices called with ticker:AAPLdate:2024-01-28atClosing:true get " +
            "stock price called with tickerSymbol:AAPL date:2024-01-29 atClosing:true " +
            "get stock prices called with ticker:AAPLdate:2024-01-29atClosing:true get " +
            "stock price called with tickerSymbol:AAPL date:2024-01-30 atClosing:true " +
            "get stock prices called with ticker:AAPLdate:2024-01-30atClosing:true " +
            "get stock price called with tickerSymbol:AAPL date:2024-01-31 atClosing:" +
            "true get stock prices called with ticker:AAPLdate:2024-01-31atClosing:true" +
            " get stock price called with tickerSymbol:AAPL date:2024-02-01 atClosing:true" +
            " get stock prices called with ticker:AAPLdate:2024-02-01atClosing:true" +
            " get stock price called with tickerSymbol:AAPL date:2024-02-02 atClosing:true" +
            " get stock prices called with ticker:AAPLdate:2024-02-02atClosing:true get" +
            " stock price called with tickerSymbol:AAPL date:2024-02-03 atClosing:true" +
            " get stock prices called with ticker:AAPLdate:2024-02-03atClosing:true get" +
            " stock price called with tickerSymbol:AAPL date:2024-02-04 atClosing:true" +
            " get stock prices called with ticker:AAPLdate:2024-02-04atClosing:true get" +
            " stock price called with tickerSymbol:AAPL date:2024-02-05 atClosing:true" +
            " get stock prices called with ticker:AAPLdate:2024-02-05atClosing:true get" +
            " stock price called with tickerSymbol:AAPL date:2024-02-06 atClosing:true" +
            " get stock prices called with ticker:AAPLdate:2024-02-06atClosing:true get" +
            " stock price called with tickerSymbol:AAPL date:2024-02-07 atClosing:true" +
            " get stock prices called with ticker:AAPLdate:2024-02-07atClosing:true get" +
            " stock price called with tickerSymbol:AAPL date:2024-02-08 atClosing:true" +
            " get stock prices called with ticker:AAPLdate:2024-02-08atClosing:true get" +
            " stock price called with tickerSymbol:AAPL date:2024-02-09 atClosing:true" +
            " get stock prices called with ticker:AAPLdate:2024-02-09atClosing:true get" +
            " stock price called with tickerSymbol:AAPL date:2024-02-10 atClosing:true" +
            " get stock prices called with ticker:AAPLdate:2024-02-10atClosing:true get" +
            " stock price called with tickerSymbol:AAPL date:2024-02-11 atClosing:true" +
            " get stock prices called with ticker:AAPLdate:2024-02-11atClosing:true get" +
            " stock price called with tickerSymbol:AAPL date:2024-02-12 atClosing:true" +
            " get stock prices called with ticker:AAPLdate:2024-02-12atClosing:true get" +
            " stock price called with tickerSymbol:AAPL date:2024-02-13 atClosing:true get" +
            " stock prices called with ticker:AAPLdate:2024-02-13atClosing:true get stock" +
            " price called with tickerSymbol:AAPL date:2024-02-14 atClosing:true get stock" +
            " prices called with ticker:AAPLdate:2024-02-14atClosing:true get stock price" +
            " called with tickerSymbol:AAPL date:2024-02-15 atClosing:true get stock prices" +
            " called with ticker:AAPLdate:2024-02-15atClosing:true";
    String expectedViewResult = "Main Menu Choose an option: Enter the ticker symbol of the " +
            "stock Invalid Ticker Symbol!Please Provide a valid ticker Symbol! Enter the" +
            " ticker symbol of the stock Enter the start Date in the format yyyy-MM-dd Enter" +
            " the date in the format yyyy-MM-dd dates and values:{} title: Crossover signals" +
            " description:Dates arecalculated using 30 day moving average. Main Menu Choose" +
            " an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testGetCrossDaysInvalidTickerStartHasNo30ValidStockDataBeforeIt() {
    ByteArrayOutputStream viewOutput = new ByteArrayOutputStream();
    IGraphingView view = new GraphingTextView(viewOutput);
    InputStream simulatedInput = new ByteArrayInputStream(("13\nAPPLE\nAAPL\n1999-11-08\n" +
            "2000-01-01\n2000-01-15\nq").getBytes());
    IMainController controller = new OldMainMenu(null, view, simulatedInput);
    controller.execute();
    String expectedViewOutput = "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the ticker symbol of the stock\n" +
            "Invalid Ticker Symbol!Please Provide a valid ticker Symbol!\n" +
            "Enter the ticker symbol of the stock\n" +
            "Enter the start Date in the format yyyy-MM-dd\n" +
            "Invalid Date. Please enter a valid date in the format yyyy-MM-dd\n" +
            "Enter the start Date in the format yyyy-MM-dd\n" +
            "Enter the date in the format yyyy-MM-dd\n" +
            "Report: Crossover signals\n" +
            "06 Jan 2000 : SELL\n" +
            "Dates arecalculated using 30 day moving average.\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n";
    assertEquals(expectedViewOutput, viewOutput.toString());
  }

  @Test
  public void testGetCrossDaysInvalidEndDateBeforeStartDate() {
    MockModel model = new MockModel();
    model.addSValidStocks("AAPL");
    MockView view = new MockView();
    LocalDate date = LocalDate.of(2024, 1, 19);
    model.setValidDataExists(date, 30);
    InputStream simulatedInput = new ByteArrayInputStream(("13\n\nAAPL\n2024-01-19" +
            "\n2024-01-01\n2024-01-15\n2024-01-28\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "get stock prices called with ticker:AAPLdate:" +
            "2024-01-19atClosing:true get stock price called with tickerSymbol:" +
            "AAPL date:2024-01-19 atClosing:true get stock prices called with " +
            "ticker:AAPLdate:2024-01-19atClosing:true get stock price called " +
            "with tickerSymbol:AAPL date:2024-01-20 atClosing:true get stock " +
            "prices called with ticker:AAPLdate:2024-01-20atClosing:true get " +
            "stock price called with tickerSymbol:AAPL date:2024-01-21 atClosing:" +
            "true get stock prices called with ticker:AAPLdate:2024-01-21atClosing:" +
            "true get stock price called with tickerSymbol:AAPL date:2024-01-22 " +
            "atClosing:true get stock prices called with ticker:" +
            "AAPLdate:2024-01-22atClosing:true get stock price called " +
            "with tickerSymbol:AAPL date:2024-01-23 atClosing:true get " +
            "stock prices called with ticker:AAPLdate:2024-01-23atClosing:" +
            "true get stock price called with tickerSymbol:AAPL date:2024-01-24 " +
            "atClosing:true get stock prices called with ticker:AAPLdate:" +
            "2024-01-24atClosing:true get stock price called with tickerSymbol:" +
            "AAPL date:2024-01-25 atClosing:true get stock prices called with " +
            "ticker:AAPLdate:2024-01-25atClosing:true get stock price called " +
            "with tickerSymbol:AAPL date:2024-01-26 atClosing:true get stock " +
            "prices called with ticker:AAPLdate:2024-01-26atClosing:true get " +
            "stock price called with tickerSymbol:AAPL date:2024-01-27 " +
            "atClosing:true get stock prices called with ticker:AAPLdate:2024-01-27atClosing:" +
            "true " +
            "get stock price called with tickerSymbol:AAPL date:2024-01-28 atClosing:true get " +
            "stock prices called with ticker:AAPLdate:2024-01-28atClosing:true";
    String expectedViewResult = "Main Menu Choose an option: Enter the ticker symbol of the " +
            "stock Invalid Ticker Symbol!Please Provide a valid ticker Symbol! Enter the " +
            "ticker symbol of the stock Enter the start Date in the format yyyy-MM-dd Enter " +
            "the date in the format yyyy-MM-dd Invalid Date. Please enter a valid date " +
            "(format yyyy-MM-dd) end date must be greater than start date. Enter the date " +
            "in the format yyyy-MM-dd Invalid Date. Please enter a valid date " +
            "(format yyyy-MM-dd) " +
            "end date must be greater than start date. Enter the date in the format " +
            "yyyy-MM-dd dates and values:{} title: Crossover signals description:Dates " +
            "arecalculated using 30 day moving average. Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void testGetCrossDaysInvalidStartDateFormat() {
    MockModel model = new MockModel();
    model.addSValidStocks("AAPL");
    MockView view = new MockView();
    LocalDate date = LocalDate.of(2024, 1, 19);
    model.setValidDataExists(date, 30);
    InputStream simulatedInput = new ByteArrayInputStream(("13\n\nAAPL\n2024/01/19" +
            "\n2024-01-19\n2024-01-28\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "get stock prices called with ticker:AAPLdate:" +
            "2024-01-19atClosing:true get stock price called with tickerSymbol:AAPL " +
            "date:2024-01-19 atClosing:true get stock prices called with " +
            "ticker:AAPLdate:2024-01-19atClosing:true get stock price called " +
            "with tickerSymbol:AAPL date:2024-01-20 atClosing:true get stock " +
            "prices called with ticker:AAPLdate:2024-01-20atClosing:true get " +
            "stock price called with tickerSymbol:AAPL date:2024-01-21 atClosing:true " +
            "get stock prices called with ticker:AAPLdate:2024-01-21atClosing:true get " +
            "stock price called with tickerSymbol:AAPL date:2024-01-22 atClosing:true " +
            "get stock prices called with ticker:AAPLdate:2024-01-22atClosing:true get " +
            "stock price called with tickerSymbol:AAPL date:2024-01-23 atClosing:true " +
            "get stock prices called with ticker:AAPLdate:2024-01-23atClosing:true " +
            "get stock price called with tickerSymbol:AAPL date:2024-01-24 atClosing:" +
            "true get stock prices called with ticker:AAPLdate:2024-01-24atClosing:true " +
            "get stock price called with tickerSymbol:AAPL date:2024-01-25 atClosing:true " +
            "get stock prices called with ticker:AAPLdate:2024-01-25atClosing:true get " +
            "stock price called with tickerSymbol:AAPL date:2024-01-26 atClosing:true " +
            "get stock prices called with ticker:AAPLdate:2024-01-26atClosing:true get " +
            "stock price called with tickerSymbol:AAPL date:2024-01-27 atClosing:true get " +
            "stock prices called with ticker:AAPLdate:2024-01-27atClosing:true get stock " +
            "price called with tickerSymbol:AAPL date:2024-01-28 atClosing:true get stock " +
            "prices called with ticker:AAPLdate:2024-01-28atClosing:true";
    String expectedViewResult = "Main Menu Choose an option: Enter the ticker symbol of " +
            "the stock Invalid Ticker Symbol!Please Provide a valid ticker Symbol! " +
            "Enter the ticker symbol of the stock Enter the start Date in the format " +
            "yyyy-MM-dd Invalid Date. Please enter a valid date in the format yyyy-MM-dd " +
            "Enter the start Date in the format yyyy-MM-dd Enter the date in the format " +
            "yyyy-MM-dd dates and values:{} title: Crossover signals description:Dates " +
            "arecalculated using 30 day moving average. Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testGetCrossDaysEmptyStartDateFormat() {
    MockModel model = new MockModel();
    model.addSValidStocks("AAPL");
    MockView view = new MockView();
    LocalDate date = LocalDate.of(2024, 1, 19);
    model.setValidDataExists(date, 30);
    InputStream simulatedInput = new ByteArrayInputStream(("13\n\nAAPL\n  \n" +
            "2024-01-19\n2024-01-28" +
            "\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "get stock prices called with ticker:AAPLdate:" +
            "2024-01-19atClosing:true get stock price called with tickerSymbol:AAPL " +
            "date:2024-01-19 atClosing:true get stock prices called with " +
            "ticker:AAPLdate:2024-01-19atClosing:true get stock price called " +
            "with tickerSymbol:AAPL date:2024-01-20 atClosing:true get stock " +
            "prices called with ticker:AAPLdate:2024-01-20atClosing:true get " +
            "stock price called with tickerSymbol:AAPL date:2024-01-21 atClosing:true " +
            "get stock prices called with ticker:AAPLdate:2024-01-21atClosing:true get " +
            "stock price called with tickerSymbol:AAPL date:2024-01-22 atClosing:true " +
            "get stock prices called with ticker:AAPLdate:2024-01-22atClosing:true get " +
            "stock price called with tickerSymbol:AAPL date:2024-01-23 atClosing:true " +
            "get stock prices called with ticker:AAPLdate:2024-01-23atClosing:true " +
            "get stock price called with tickerSymbol:AAPL date:2024-01-24 atClosing:" +
            "true get stock prices called with ticker:AAPLdate:2024-01-24atClosing:true " +
            "get stock price called with tickerSymbol:AAPL date:2024-01-25 atClosing:true " +
            "get stock prices called with ticker:AAPLdate:2024-01-25atClosing:true get " +
            "stock price called with tickerSymbol:AAPL date:2024-01-26 atClosing:true " +
            "get stock prices called with ticker:AAPLdate:2024-01-26atClosing:true get " +
            "stock price called with tickerSymbol:AAPL date:2024-01-27 atClosing:true get " +
            "stock prices called with ticker:AAPLdate:2024-01-27atClosing:true get stock " +
            "price called with tickerSymbol:AAPL date:2024-01-28 atClosing:true get stock " +
            "prices called with ticker:AAPLdate:2024-01-28atClosing:true";
    String expectedViewResult = "Main Menu Choose an option: Enter the ticker symbol of " +
            "the stock Invalid Ticker Symbol!Please Provide a valid ticker Symbol! " +
            "Enter the ticker symbol of the stock Enter the start Date in the format " +
            "yyyy-MM-dd Invalid Date. Please enter a valid date in the format yyyy-MM-dd " +
            "Enter the start Date in the format yyyy-MM-dd Enter the date in the format " +
            "yyyy-MM-dd dates and values:{} title: Crossover signals description:Dates " +
            "arecalculated using 30 day moving average. Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void testGetCrossDaysNullStartDateFormat() {
    MockModel model = new MockModel();
    model.addSValidStocks("AAPL");
    MockView view = new MockView();
    LocalDate date = LocalDate.of(2024, 1, 19);
    model.setValidDataExists(date, 30);
    InputStream simulatedInput = new ByteArrayInputStream(("13\n\nAAPL\n\n2024-01-19" +
            "\n2024-01-28\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "get stock prices called with ticker:AAPLdate:" +
            "2024-01-19atClosing:true get stock price called with tickerSymbol:AAPL " +
            "date:2024-01-19 atClosing:true get stock prices called with " +
            "ticker:AAPLdate:2024-01-19atClosing:true get stock price called " +
            "with tickerSymbol:AAPL date:2024-01-20 atClosing:true get stock " +
            "prices called with ticker:AAPLdate:2024-01-20atClosing:true get " +
            "stock price called with tickerSymbol:AAPL date:2024-01-21 atClosing:true " +
            "get stock prices called with ticker:AAPLdate:2024-01-21atClosing:true get " +
            "stock price called with tickerSymbol:AAPL date:2024-01-22 atClosing:true " +
            "get stock prices called with ticker:AAPLdate:2024-01-22atClosing:true get " +
            "stock price called with tickerSymbol:AAPL date:2024-01-23 atClosing:true " +
            "get stock prices called with ticker:AAPLdate:2024-01-23atClosing:true " +
            "get stock price called with tickerSymbol:AAPL date:2024-01-24 atClosing:" +
            "true get stock prices called with ticker:AAPLdate:2024-01-24atClosing:true " +
            "get stock price called with tickerSymbol:AAPL date:2024-01-25 atClosing:true " +
            "get stock prices called with ticker:AAPLdate:2024-01-25atClosing:true get " +
            "stock price called with tickerSymbol:AAPL date:2024-01-26 atClosing:true " +
            "get stock prices called with ticker:AAPLdate:2024-01-26atClosing:true get " +
            "stock price called with tickerSymbol:AAPL date:2024-01-27 atClosing:true get " +
            "stock prices called with ticker:AAPLdate:2024-01-27atClosing:true get stock " +
            "price called with tickerSymbol:AAPL date:2024-01-28 atClosing:true get stock " +
            "prices called with ticker:AAPLdate:2024-01-28atClosing:true";
    String expectedViewResult = "Main Menu Choose an option: Enter the ticker symbol of the stock" +
            " Invalid Ticker Symbol!Please Provide a valid ticker Symbol! Enter" +
            " the ticker symbol of the stock Enter the start Date in the format" +
            " yyyy-MM-dd Invalid Date. Please enter a valid date in the format" +
            " yyyy-MM-dd Enter the start Date in the format yyyy-MM-dd Enter the" +
            " date in the format yyyy-MM-dd dates and values:{} title:" +
            " Crossover signals description:Dates arecalculated using 30 day" +
            " moving average. Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void getCrossOverDaysIntegrationTesting() {
    ByteArrayOutputStream viewOutput = new ByteArrayOutputStream();
    IGraphingView view = new GraphingTextView(viewOutput);
    InputStream simulatedInput = new ByteArrayInputStream(("13\nAAPL\n2024-01-15\n2024-01-23\nq").
            getBytes());
    IMainController controller = new OldMainMenu(null, view, simulatedInput);
    controller.execute();
    assertEquals("Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the ticker symbol of the stock\n" +
            "Enter the start Date in the format yyyy-MM-dd\n" +
            "Enter the date in the format yyyy-MM-dd\n" +
            "Report: Crossover signals\n" +
            "19 Jan 2024 : BUY\n" +
            "Dates arecalculated using 30 day moving average.\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n", viewOutput.toString());
  }


  @Test
  public void getMovingAverageCrossOverDaysIntegrationTesting() {
    ByteArrayOutputStream viewOutput = new ByteArrayOutputStream();
    IGraphingView view = new GraphingTextView(viewOutput);
    InputStream simulatedInput = new ByteArrayInputStream(("14\nAAPL\n6\n12\n2024-01-15\n" +
            "2024-02-21\nq").getBytes());
    IMainController controller = new OldMainMenu(null, view, simulatedInput);
    controller.execute();
    assertEquals("Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the ticker symbol of the stock\n" +
            "Enter the number of days for shorter moving average\n" +
            "Enter the number of days for longer moving average\n" +
            "Enter the start Date in the format yyyy-MM-dd\n" +
            "Enter the end date in the format yyyy-MM-dd\n" +
            "Report: Moving crossover signals.\n" +
            "18 Jan 2024 : BUY\n" +
            "01 Feb 2024 : SELL\n" +
            "12 Feb 2024 : BUY\n" +
            "15 Feb 2024 : SELL\n" +
            "Dates are calculated using 6 and 12 moving averages.\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n", viewOutput.toString());
  }


  @Test
  public void testGetMovingAverageCrossOverDaysEmptyTickerTest() {
    ByteArrayOutputStream viewOutput = new ByteArrayOutputStream();
    IGraphingView view = new GraphingTextView(viewOutput);
    InputStream simulatedInput = new ByteArrayInputStream(("13\n \nAAPL\n6\n12\n2024-01-19\n2024" +
            "-02-12\nq").getBytes());
    IMainController controller = new OldMainMenu(null, view, simulatedInput);
    controller.execute();
    assertEquals("Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the ticker symbol of the stock\n" +
            "Invalid Ticker Symbol!Please Provide a valid ticker Symbol!\n" +
            "Enter the ticker symbol of the stock\n" +
            "Enter the start Date in the format yyyy-MM-dd\n" +
            "Invalid Date. Please enter a valid date in the format yyyy-MM-dd\n" +
            "Enter the start Date in the format yyyy-MM-dd\n" +
            "Invalid Date. Please enter a valid date in the format yyyy-MM-dd\n" +
            "Enter the start Date in the format yyyy-MM-dd\n" +
            "Enter the date in the format yyyy-MM-dd\n" +
            "Report: Crossover signals\n" +
            "30 Jan 2024 : SELL\n" +
            "06 Feb 2024 : BUY\n" +
            "12 Feb 2024 : SELL\n" +
            "Dates arecalculated using 30 day moving average.\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n", viewOutput.toString());
  }


  @Test
  public void testGetMovingAverageCrossOverDaysNullTickerTest() {
    ByteArrayOutputStream viewOutput = new ByteArrayOutputStream();
    IGraphingView view = new GraphingTextView(viewOutput);
    InputStream simulatedInput = new ByteArrayInputStream(("14\n\nAAPL\n6\n12\n2024-01-19\n20" +
            "24-02-12\nq").getBytes());
    IMainController controller = new OldMainMenu(null, view, simulatedInput);
    controller.execute();
    assertEquals("Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the ticker symbol of the stock\n" +
            "Invalid Ticker symbol\n" +
            "Enter the ticker symbol of the stock\n" +
            "Enter the number of days for shorter moving average\n" +
            "Enter the number of days for longer moving average\n" +
            "Enter the start Date in the format yyyy-MM-dd\n" +
            "Enter the end date in the format yyyy-MM-dd\n" +
            "Report: Moving crossover signals.\n" +
            "01 Feb 2024 : SELL\n" +
            "12 Feb 2024 : BUY\n" +
            "Dates are calculated using 6 and 12 moving averages.\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n", viewOutput.toString());
  }


  @Test
  public void testGetMovingAverageCrossOverDayInvalidTickerTest() {
    ByteArrayOutputStream viewOutput = new ByteArrayOutputStream();
    IGraphingView view = new GraphingTextView(viewOutput);
    InputStream simulatedInput = new ByteArrayInputStream(("14\nbbfbfbh\nAAPL\n6\n12\n2024-" +
            "01-19\n2024-02-12\nq").getBytes());
    IMainController controller = new OldMainMenu(null, view, simulatedInput);
    controller.execute();
    assertEquals("Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the ticker symbol of the stock\n" +
            "Invalid Ticker symbol\n" +
            "Enter the ticker symbol of the stock\n" +
            "Enter the number of days for shorter moving average\n" +
            "Enter the number of days for longer moving average\n" +
            "Enter the start Date in the format yyyy-MM-dd\n" +
            "Enter the end date in the format yyyy-MM-dd\n" +
            "Report: Moving crossover signals.\n" +
            "01 Feb 2024 : SELL\n" +
            "12 Feb 2024 : BUY\n" +
            "Dates are calculated using 6 and 12 moving averages.\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n", viewOutput.toString());
  }


  @Test
  public void testGetMovingAverageCrossOverDayInvalidXDaysTest() {
    ByteArrayOutputStream viewOutput = new ByteArrayOutputStream();
    IGraphingView view = new GraphingTextView(viewOutput);
    InputStream simulatedInput = new ByteArrayInputStream(("13\nbbfbfbh\nAAPL\n-6\n6\n12\n" +
            "2024-01-19\n2024-02-12\nq").getBytes());
    IMainController controller = new OldMainMenu(null, view, simulatedInput);
    controller.execute();
    assertEquals("Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the ticker symbol of the stock\n" +
            "Invalid Ticker Symbol!Please Provide a valid ticker Symbol!\n" +
            "Enter the ticker symbol of the stock\n" +
            "Enter the start Date in the format yyyy-MM-dd\n" +
            "Invalid Date. Please enter a valid date in the format yyyy-MM-dd\n" +
            "Enter the start Date in the format yyyy-MM-dd\n" +
            "Invalid Date. Please enter a valid date in the format yyyy-MM-dd\n" +
            "Enter the start Date in the format yyyy-MM-dd\n" +
            "Invalid Date. Please enter a valid date in the format yyyy-MM-dd\n" +
            "Enter the start Date in the format yyyy-MM-dd\n" +
            "Enter the date in the format yyyy-MM-dd\n" +
            "Report: Crossover signals\n" +
            "30 Jan 2024 : SELL\n" +
            "06 Feb 2024 : BUY\n" +
            "12 Feb 2024 : SELL\n" +
            "Dates arecalculated using 30 day moving average.\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n", viewOutput.toString());
  }

  @Test
  public void testGetMovingAverageCrossOverDayFloatXDaysTest() {
    ByteArrayOutputStream viewOutput = new ByteArrayOutputStream();
    IGraphingView view = new GraphingTextView(viewOutput);
    InputStream simulatedInput = new ByteArrayInputStream(("13\nbbfbfbh\nAAPL\n6.3\n6\n12\n" +
            "2024-01-19\n2024-02-12\nq").getBytes());
    IMainController controller = new OldMainMenu(null, view, simulatedInput);
    controller.execute();
    assertEquals("Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the ticker symbol of the stock\n" +
            "Invalid Ticker Symbol!Please Provide a valid ticker Symbol!\n" +
            "Enter the ticker symbol of the stock\n" +
            "Enter the start Date in the format yyyy-MM-dd\n" +
            "Invalid Date. Please enter a valid date in the format yyyy-MM-dd\n" +
            "Enter the start Date in the format yyyy-MM-dd\n" +
            "Invalid Date. Please enter a valid date in the format yyyy-MM-dd\n" +
            "Enter the start Date in the format yyyy-MM-dd\n" +
            "Invalid Date. Please enter a valid date in the format yyyy-MM-dd\n" +
            "Enter the start Date in the format yyyy-MM-dd\n" +
            "Enter the date in the format yyyy-MM-dd\n" +
            "Report: Crossover signals\n" +
            "30 Jan 2024 : SELL\n" +
            "06 Feb 2024 : BUY\n" +
            "12 Feb 2024 : SELL\n" +
            "Dates arecalculated using 30 day moving average.\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n", viewOutput.toString());
  }

  @Test
  public void testGetMovingAverageCrossOverDayInvalidYDaysTestLessThanXDays() {
    ByteArrayOutputStream viewOutput = new ByteArrayOutputStream();
    IGraphingView view = new GraphingTextView(viewOutput);
    InputStream simulatedInput = new ByteArrayInputStream(("13\nbbfbfbh\nAAPL\n6\n3\n12\n" +
            "2024-01-19\n2024-02-12\nq").getBytes());
    IMainController controller = new OldMainMenu(null, view, simulatedInput);
    controller.execute();
    assertEquals("Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the ticker symbol of the stock\n" +
            "Invalid Ticker Symbol!Please Provide a valid ticker Symbol!\n" +
            "Enter the ticker symbol of the stock\n" +
            "Enter the start Date in the format yyyy-MM-dd\n" +
            "Invalid Date. Please enter a valid date in the format yyyy-MM-dd\n" +
            "Enter the start Date in the format yyyy-MM-dd\n" +
            "Invalid Date. Please enter a valid date in the format yyyy-MM-dd\n" +
            "Enter the start Date in the format yyyy-MM-dd\n" +
            "Invalid Date. Please enter a valid date in the format yyyy-MM-dd\n" +
            "Enter the start Date in the format yyyy-MM-dd\n" +
            "Enter the date in the format yyyy-MM-dd\n" +
            "Report: Crossover signals\n" +
            "30 Jan 2024 : SELL\n" +
            "06 Feb 2024 : BUY\n" +
            "12 Feb 2024 : SELL\n" +
            "Dates arecalculated using 30 day moving average.\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n", viewOutput.toString());
  }


  @Test
  public void testGetMovingAverageCrossOverDayEndDate() {
    ByteArrayOutputStream viewOutput = new ByteArrayOutputStream();
    IGraphingView view = new GraphingTextView(viewOutput);
    InputStream simulatedInput = new ByteArrayInputStream(("13\nAAPL\n6\n12\n2024-01-19\n" +
            "2024-01-18\n2024-01-21\nq").getBytes());
    IMainController controller = new OldMainMenu(null, view, simulatedInput);
    controller.execute();
    assertEquals("Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the ticker symbol of the stock\n" +
            "Enter the start Date in the format yyyy-MM-dd\n" +
            "Invalid Date. Please enter a valid date in the format yyyy-MM-dd\n" +
            "Enter the start Date in the format yyyy-MM-dd\n" +
            "Invalid Date. Please enter a valid date in the format yyyy-MM-dd\n" +
            "Enter the start Date in the format yyyy-MM-dd\n" +
            "Enter the date in the format yyyy-MM-dd\n" +
            "Invalid Date. Please enter a valid date (format yyyy-MM-dd) end date must be " +
            "greater than start date.\n" +
            "Enter the date in the format yyyy-MM-dd\n" +
            "Report: Crossover signals\n" +
            "There are no buy sell dates in this report\n" +
            "Dates arecalculated using 30 day moving average.\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n", viewOutput.toString());
  }

  @Test
  public void testGetMovingAverageCrossOverDayNotEnoughDataInvalidStartDate() {
    ByteArrayOutputStream viewOutput = new ByteArrayOutputStream();
    IGraphingView view = new GraphingTextView(viewOutput);
    InputStream simulatedInput = new ByteArrayInputStream(("13\nAAPL\n6\n12\n1999-11-02" +
            "\n2024-01-19\n" +
            "2024-01-18\n2024-01-21\nq").getBytes());
    IMainController controller = new OldMainMenu(null, view, simulatedInput);
    controller.execute();
    assertEquals("Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the ticker symbol of the stock\n" +
            "Enter the start Date in the format yyyy-MM-dd\n" +
            "Invalid Date. Please enter a valid date in the format yyyy-MM-dd\n" +
            "Enter the start Date in the format yyyy-MM-dd\n" +
            "Invalid Date. Please enter a valid date in the format yyyy-MM-dd\n" +
            "Enter the start Date in the format yyyy-MM-dd\n" +
            "Invalid Date. Please enter a valid date in the format yyyy-MM-dd\n" +
            "Enter the start Date in the format yyyy-MM-dd\n" +
            "Enter the date in the format yyyy-MM-dd\n" +
            "Invalid Date. Please enter a valid date (format yyyy-MM-dd) end date must be greater" +
            " than start date.\n" +
            "Enter the date in the format yyyy-MM-dd\n" +
            "Report: Crossover signals\n" +
            "There are no buy sell dates in this report\n" +
            "Dates arecalculated using 30 day moving average.\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n", viewOutput.toString());
  }


  @Test
  public void periodStockPerformanceValidInputsTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("11\n2024-01-01\n2024-03-19\nAAPL\nq").
            getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "get stock price called with tickerSymbol:AAPL date:2024-01-01 " +
            "atClosing:true get stock price called with tickerSymbol:AAPL date:2024-03-19 " +
            "atClosing:true";
    String expectedViewResult = "Main Menu Choose an option: Enter the start date in the format " +
            "yyyy-MM-dd Enter the end date in the format yyyy-MM-dd Enter the ticker symbol " +
            "of the stock Start: 2024-01-01 End: 2024-03-19 Asset: AAPL Start Value: 0.0 " +
            "End Value: 0.0 Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void periodStockPerformanceInvalidTickerNullTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("11\n2024-01-01\n" +
            "2024-03-19\n\nAPL\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "get stock price called with tickerSymbol:APL date:" +
            "2024-01-01 atClosing:true get stock price called with tickerSymbol:" +
            "APL date:2024-03-19 atClosing:true";
    String expectedViewResult = "Main Menu Choose an option: Enter the start " +
            "date in the format yyyy-MM-dd Enter the end date in the format " +
            "yyyy-MM-dd Enter the ticker symbol of the stock Ticker symbol " +
            "cannot be empty! Enter the ticker symbol of the stock Start: " +
            "2024-01-01 End: 2024-03-19 Asset: APL Start Value: 0.0 End " +
            "Value: 0.0 Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void periodStockPerformanceInvalidTickerMultipleInvalidTickerTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("11\n2024-01-01\n" +
            "2024-03-19\n  \nAPL\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "get stock price called with tickerSymbol:APL date:" +
            "2024-01-01 atClosing:true get stock price called with tickerSymbol:" +
            "APL date:2024-03-19 atClosing:true";
    String expectedViewResult = "Main Menu Choose an option: Enter the start " +
            "date in the format yyyy-MM-dd Enter the end date in the format " +
            "yyyy-MM-dd Enter the ticker symbol of the stock Ticker symbol " +
            "cannot be empty! Enter the ticker symbol of the stock Start: " +
            "2024-01-01 End: 2024-03-19 Asset: APL Start Value: 0.0 End " +
            "Value: 0.0 Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void periodStockPerformanceInvalidEndDateBeforeStartDate() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("11\n2024-03-01\n" +
            "2024-02-5\n2024-03-19\nAPL\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "get stock price called with tickerSymbol:APL date:" +
            "2024-03-01 atClosing:true get stock price called with tickerSymbol:APL " +
            "date:2024-03-19 atClosing:true";
    String expectedViewResult = "Main Menu Choose an option: Enter the start " +
            "date in the format yyyy-MM-dd Enter the end date in the " +
            "format yyyy-MM-dd Invalid Date. Please enter a valid date " +
            "in the format yyyy-MM-dd Enter the end date in the format " +
            "yyyy-MM-dd Enter the ticker symbol of the stock Start: " +
            "2024-03-01 End: 2024-03-19 Asset: APL Start Value: 0.0 " +
            "End Value: 0.0 Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void periodStockPerformanceNullStartDate() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("11\n\n2024-01-01\n" +
            "2024-03-19\nAAPL\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "get stock price called with tickerSymbol:AAPL" +
            " date:2024-01-01 atClosing:true get stock price called with tickerSymbol" +
            ":AAPL date:2024-03-19 atClosing:true";
    String expectedViewResult = "Main Menu Choose an option: Enter the start date in" +
            " the format yyyy-MM-dd Invalid Date. Please enter a valid date in the" +
            " format yyyy-MM-dd Enter the start date in the format yyyy-MM-dd Enter" +
            " the end date in the format yyyy-MM-dd Enter the ticker symbol of the" +
            " stock Start: 2024-01-01 End: 2024-03-19 Asset: AAPL Start Value: 0.0" +
            " End Value: 0.0 Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void periodStockPerformanceEmptyStartDate() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("11\n \n2024-01-01\n" +
            "2024-03-19\nAAPL\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "get stock price called with tickerSymbol:AAPL date" +
            ":2024-01-01 atClosing:true get stock price called with tickerSymbol:AAPL" +
            " date:2024-03-19 atClosing:true";
    String expectedViewResult = "Main Menu Choose an option: Enter the start date in" +
            " the format yyyy-MM-dd Invalid Date. Please enter a valid date in the" +
            " format yyyy-MM-dd Enter the start date in the format yyyy-MM-dd Enter" +
            " the end date in the format yyyy-MM-dd Enter the ticker symbol of the" +
            " stock Start: 2024-01-01 End: 2024-03-19 Asset: AAPL Start Value: 0.0" +
            " End Value: 0.0 Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void periodStockPerformanceEmptyEndDate() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("11\n2024-01-01\n \n" +
            "2024-03-19\nAAPL\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "get stock price called with tickerSymbol:AAPL" +
            " date:2024-01-01 atClosing:true get stock price called with tickerSymbol" +
            ":AAPL date:2024-03-19 atClosing:true";
    String expectedViewResult = "Main Menu Choose an option: Enter the start date in" +
            " the format yyyy-MM-dd Enter the end date in the format yyyy-MM-dd Invalid Date." +
            " Please enter a valid date in the format yyyy-MM-dd Enter the end date in the" +
            " format yyyy-MM-dd Enter the ticker symbol of the stock Start: 2024-01-01 End:" +
            " 2024-03-19 Asset: AAPL Start Value: 0.0 End Value: 0.0 Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void periodStockPerformanceNullEndDate() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("11\n2024-01-01\n\n" +
            "2024-03-19\nAAPL\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "get stock price called with tickerSymbol:AAPL date" +
            ":2024-01-01 atClosing:true get stock price called with tickerSymbol:AAPL" +
            " date:2024-03-19 atClosing:true";
    String expectedViewResult = "Main Menu Choose an option: Enter the start date in" +
            " the format yyyy-MM-dd Enter the end date in the format yyyy-MM-dd Invalid Date." +
            " Please enter a valid date in the format yyyy-MM-dd Enter the end date in the" +
            " format yyyy-MM-dd Enter the ticker symbol of the stock Start: 2024-01-01 End:" +
            " 2024-03-19 Asset: AAPL Start Value: 0.0 End Value: 0.0 Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void periodStockPerformanceTestIntegrationTesting() {
    ByteArrayOutputStream viewOutput = new ByteArrayOutputStream();
    IGraphingView view = new GraphingTextView(viewOutput);
    InputStream simulatedInput = new ByteArrayInputStream(("11\n2024-01-03\n" +
            "2024-03-19\nAAPL\nq").getBytes());
    IMainController controller = new OldMainMenu(null, view, simulatedInput);
    controller.execute();
    assertEquals("Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n" +
            "Enter the start date in the format yyyy-MM-dd\n" +
            "Enter the end date in the format yyyy-MM-dd\n" +
            "Enter the ticker symbol of the stock\n" +
            "AAPL decreased by $8.17 between 03 Jan 2024 and 19 Mar 2024\n" +
            "Welcome to the Investment Portfolio Manager!\n" +
            "Please select an option from the following menu:\n" +
            "\n" +
            "1.Create Mutable Portfolio\n" +
            "2.Create Immutable Portfolio\n" +
            "3.Get Portfolio Value\n" +
            "4.Get Portfolio Composition\n" +
            "5.Get Cost Basis\n" +
            "6.Load Portfolio\n" +
            "7.Save Portfolio\n" +
            "8.Buy Stocks\n" +
            "9.Sell Stocks\n" +
            "10.Get Daily Stock Performance\n" +
            "11.Get Period Stock Performance\n" +
            "12.Get X-Day Moving Average\n" +
            "13.Get Cross Over Days\n" +
            "14.Get Moving Average Cross Over\n" +
            "15.Analyze Stock Performance\n" +
            "16.Analyze Portfolio Performance\n" +
            "17. Invest with Weighted Strategy\n" +
            "18. Create Dollar Cost Averaging Portfolios\n" +
            "q.Quit\n" +
            "Choose an option:\n", viewOutput.toString());
  }


  private void analyzePortfolioPerformanceSetUp() {
    m = new MockModel();
    v = new MockView();
    c = new AnalyzePortfolioPerformance(m, v, "Port", start, end);
  }

  private void analyzeStockPerformanceSetUp() {
    m = new MockModel();
    v = new MockView();
    c = new AnalyzeStockPerformance(m, v, "IRTC", start, end);
  }

  @Test
  public void analyzePortFolioPerformanceTestHappyPath() {
    this.analyzePortfolioPerformanceSetUp();
    m.addSValidStocks("IRTC");
    c.execute();
    //test that an appropriate unit and length is chosen
    String actual = m.getLog().get(0);
    String expected = "getStockPrices with unit: Days, ticker: IRTC, lastEntry: 2024-04-09, "
            + "maxSize 25, atClosing: true, length: 1";
    assertEquals(expected, actual);
    assertEquals(25, ChronoUnit.DAYS.between(start, end));
    actual = v.getLog().get(0);
    expected = "values: {2024-04-09=100.0}";
    assertEquals(expected, actual);
  }

  @Test
  public void analyzePortFolioPerformanceTestDatesTooClose() {
    this.analyzePortfolioPerformanceSetUp();
    c = new AnalyzePortfolioPerformance(m, v, "IRTC", start, start);
    c.execute();
    assertTrue(m.getLog().isEmpty());
    String expected = "End date must be at least 2 market trading days "
            + "and less than 30 years from the start date.";
    assertEquals(expected, v.getLog().get(0));
  }


  @Test
  public void analyzeStockPerformanceTestHappyPath() {
    this.analyzeStockPerformanceSetUp();
    m.addSValidStocks("IRTC");
    c.execute();
    //test that an appropriate unit and length is chosen
    String actual = m.getLog().get(0);
    String expected = "getStockPrices with unit: Days, ticker: IRTC, lastEntry: 2024-04-09, "
            + "maxSize 25, atClosing: true, length: 1";
    assertEquals(expected, actual);
    assertEquals(25, ChronoUnit.DAYS.between(start, end));
    actual = v.getLog().get(0);
    expected = "values: {2024-04-09=100.0}";
    assertEquals(expected, actual);
  }

  @Test
  public void analyzeStockPerformanceTestDatesTooClose() {
    this.analyzeStockPerformanceSetUp();
    c = new AnalyzeStockPerformance(m, v, "IRTC", start, start);
    c.execute();
    assertTrue(m.getLog().isEmpty());
    String expected = "End date must be at least 2 market trading days "
            + "and less than 30 years from the start date.";
    assertEquals(expected, v.getLog().get(0));
  }

}
