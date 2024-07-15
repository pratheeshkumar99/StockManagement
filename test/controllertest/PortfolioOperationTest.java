package controllertest;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import controller.IMainController;
import controller.OldMainMenu;

import static org.junit.Assert.assertEquals;

/**
 * Tests class that tests the GetPortfolioComposition Command, BuyStock.
 * command, SellStock command, GetPortfolioValue command, GetPortfolioCostBasis command.
 */

public class PortfolioOperationTest {

  @Test
  public void testBuyStockInvalidNegativeDayInDate() {
    MockModel model = new MockModel();
    model.addMutableExisting("p11");
    model.addSValidStocks("Valid ticker");
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("8\np11\nvalid ticker"
            + "\n4\n \n2023-30--34/2023\n2023-01-21\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p11 "
            + "add stock called with ticker:valid ticker quantity:4.0 "
            + "date:2023-01-21 portfolioName:p11";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio Enter "
            + "the ticker symbol of the stock Enter the quantity of stocks "
            + "you want to buy Enter "
            + "the date in the format yyyy-MM-dd Invalid Date. Please enter a valid date in the "
            + "format yyyy-MM-dd Enter the date in the format "
            + "yyyy-MM-dd Invalid Date. Please enter "
            + "a valid date in the format yyyy-MM-dd Enter"
            + " the date in the format yyyy-MM-dd Stock "
            + "bought successfully! Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void testBuyStockInvalidDayInDate() {
    MockModel model = new MockModel();
    model.addMutableExisting("p11");
    model.addSValidStocks("Valid ticker");
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("8\np11\n"
            + "valid ticker\n4\n \n2023-30-34/2023\n2023-01-21\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p11"
            + " add stock called with ticker:valid ticker quantity:4.0 date:2023-01-21"
            + " portfolioName:p11";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of"
            + " the portfolio Enter " +
            "the ticker symbol of the stock Enter the quantity of stocks you "
            + "want to buy Enter the date in the format yyyy-MM-dd Invalid Date. Please enter a "
            + "valid date in the "
            + "format yyyy-MM-dd Enter the date in the format yyyy-MM-dd "
            + "Invalid Date. Please enter "
            + "a valid date in the format yyyy-MM-dd Enter the date in the"
            + " format yyyy-MM-dd Stock "
            + "bought successfully! Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testBuyStockInvalidMonthInDate() {
    MockModel model = new MockModel();
    model.addMutableExisting("p11");
    model.addSValidStocks("Valid ticker");
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("8\np11\nvalid ticker\n4\n \n"
            + "2023-30-02/2023\n2023-01-21\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p11 add stock "
            + "called with ticker:valid ticker quantity:4.0 date:2023-01-21 portfolioName:p11";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio Enter "
            + "the ticker symbol of the stock Enter the quantity of stocks you want to buy Enter "
            + "the date in the format yyyy-MM-dd Invalid Date. Please enter a valid date in the "
            + "format yyyy-MM-dd Enter the date in the format yyyy-MM-dd Invalid Date. "
            + "Please enter "
            + "a valid date in the format yyyy-MM-dd Enter the date in the format yyyy-MM-dd Stock "
            + "bought successfully! Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testBuyStockInvalidDateFormat() {
    MockModel model = new MockModel();
    model.addMutableExisting("p11");
    model.addSValidStocks("Valid ticker");
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("8\np11\nvalid ticker"
            + "\n4\n \n21/02/2023\n2023-01-21\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with "
            + "portfolioName:p11 add stock called with ticker:valid "
            + "ticker quantity:4.0 date:2023-01-21 portfolioName:p11";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio "
            + "Enter "
            + "the ticker symbol of the stock Enter the quantity of stocks you want to buy Enter "
            + "the date in the format yyyy-MM-dd Invalid Date. Please enter a valid date in the "
            + "format yyyy-MM-dd Enter the date in the format yyyy-MM-dd Invalid Date. "
            + "Please enter "
            + "a valid date in the format yyyy-MM-dd Enter the date in the format yyyy-MM-dd Stock "
            + "bought successfully! Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testBuyStockEmptyDate() {
    MockModel model = new MockModel();
    model.addMutableExisting("p11");
    model.addSValidStocks("Valid ticker");
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("8\np11\n"
            + "valid ticker\n4\n \n2012-01-21\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with "
            + "portfolioName:p11 add stock called with ticker:valid "
            + "ticker quantity:4.0 date:2012-01-21 portfolioName:p11";
    String expectedViewResult = "Main Menu Choose an option: "
            + "Enter the name of the portfolio"
            + " Enter the ticker symbol of the stock Enter the "
            + "quantity of stocks you want to buy "
            + "Enter the date in the format yyyy-MM-dd Invalid Date. "
            + "Please enter a valid date in "
            + "the format yyyy-MM-dd Enter the date in the format "
            + "yyyy-MM-dd Stock bought "
            + "successfully! Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testBuyStockNullDate() {
    MockModel model = new MockModel();
    model.addMutableExisting("p11");
    model.addSValidStocks("Valid ticker");
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("8\np11\n"
            + "valid ticker\n4\n\n2012-01-21\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:"
            + "p11 add stock called with ticker:valid ticker quantity:4.0 date:2012-01-21"
            + " portfolioName:p11";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio"
            + " Enter the ticker symbol of the stock Enter the quantity of stocks you want to buy "
            + "Enter the date in the format yyyy-MM-dd Invalid Date. Please enter a valid date in "
            + "the format yyyy-MM-dd Enter the date in the format yyyy-MM-dd Stock bought "
            + "successfully! Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testBuyStockNullStockTicker() {
    MockModel model = new MockModel();
    model.addMutableExisting("p11");
    model.addSValidStocks("Valid ticker");
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("8\np11\n\n" +
            "valid ticker\n4\n2012-01-21\n" +
            "q").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called "
            + "with portfolioName:p11 add stock called with ticker:"
            + "valid ticker quantity:4.0 date:2012-01-21 portfolioName:p11";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio "
            + "Enter the ticker symbol of the stock Ticker symbol cannot be empty! "
            + "Enter the ticker symbol of the stock Enter the quantity of stocks you want "
            + "to buy Enter the date in the format yyyy-MM-dd Stock bought successfully! "
            + "Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void testBuyStockEmptyStockTicker() {
    MockModel model = new MockModel();
    model.addMutableExisting("p11");
    model.addSValidStocks("Valid ticker");
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream((("8\np11\n \n"
            + "valid ticker\n4\n2012-01-21\nq").getBytes()));
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:" +
            "p11 add stock called with ticker:valid ticker quantity:4.0 date:2012-01-21 " +
            "portfolioName:p11";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio "
            + "Enter the ticker symbol of the stock Ticker symbol cannot be empty! "
            + "Enter the ticker symbol of the stock Enter the quantity of stocks you want "
            + "to buy Enter the date in the format yyyy-MM-dd Stock bought successfully! "
            + "Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testBuyStockInvalidStockTicker() {
    MockModel model = new MockModel();
    model.addMutableExisting("p11");
    model.addSValidStocks("Valid ticker");
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("8\np11\nInvalidTicker\n5\n2012-01-21"
            + "\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p11";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio" +
            " Enter the ticker symbol of the stock Enter the quantity of stocks you want" +
            " to buy Enter the date in the format yyyy-MM-dd Stock not found Main Menu " +
            "Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testBuyStockInvalidEmptyPortfolioName() {
    MockModel model = new MockModel();
    model.addExistingPortfolio("p2");
    model.addMutableExisting("p1");
    model.addSValidStocks("APPL INC");
    model.addSValidStocks("GOOGLE INC");
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("8\n \n"
            + "p1\nappl inc\n5\n2012-01-21\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:"
            + "  is portfolio present called with portfolioName:p1 add stock called with"
            + " ticker:appl inc quantity:5.0 date:2012-01-21 portfolioName:p1";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio "
            + "Invalid Portfolio Name. Please enter a valid Portfolio Name(Mutable and Non-empty)"
            + " Enter the name of the portfolio Enter the ticker symbol of the stock Enter the "
            + "quantity of stocks you want to buy Enter the date in the format yyyy-MM-dd Stock "
            + "bought successfully! Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void testBuyStockInvalidNullPortfolioName() {
    MockModel model = new MockModel();
    model.addExistingPortfolio("p2");
    model.addMutableExisting("p1");
    model.addSValidStocks("APPL INC");
    model.addSValidStocks("GOOGLE INC");
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("8\n\np1\nappl inc\n"
            + "5\n2012-01-21\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);

    String expectedModelResult = "is portfolio present called with portfolioName:p1 add stock "
            +
            "called with ticker:appl inc quantity:5.0 date:2012-01-21 portfolioName:p1";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio "
            +
            "Invalid Portfolio Name. Please enter a valid Portfolio Name(Mutable and Non-empty)"
            +
            " Enter the name of the portfolio Enter the ticker symbol of the stock Enter the "
            +
            "quantity of stocks you want to buy Enter the date in the format yyyy-MM-dd Stock "
            +
            "bought successfully! Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testBuyStockInvalidMutablePortfolioName() {
    MockModel model = new MockModel();
    model.addExistingPortfolio("p2");
    model.addMutableExisting("p1");
    model.addSValidStocks("APPL INC");
    model.addSValidStocks("GOOGLE INC");
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("8\np2\np1\nappl "
            + "inc\n5\n2012-01-21\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);

    String expectedModelResult = "is portfolio present called with portfolioName:p2 is "
            + "portfolio present called with portfolioName:p1 add stock called with ticker:"
            + "appl inc quantity:5.0 date:2012-01-21 portfolioName:p1";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio "
            + "Invalid Portfolio Name. Please enter a valid Portfolio Name(Mutable and Non-empty)"
            + " Enter the name of the portfolio Enter the ticker symbol of the stock Enter the "
            + "quantity of stocks you want to buy Enter the date in the format yyyy-MM-dd Stock "
            + "bought successfully! Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void testBuyStockValidInputs() {
    MockModel model = new MockModel();
    model.addMutableExisting("p1");
    model.addSValidStocks("appl inc");
    model.addSValidStocks("GOOGLE INC");
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("8\np1\nappl inc\n"
            + "5\n2012-01-21\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p1 add stock " +
            "called with ticker:appl inc quantity:5.0 date:2012-01-21 portfolioName:p1";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio Enter "
            + "the ticker symbol of the stock Enter the quantity of stocks you want to buy "
            + "Enter the date in the format yyyy-MM-dd Stock bought successfully!"
            + " Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void testSellStockInvalidNegativeDayInDate() {
    MockModel model = new MockModel();
    model.addMutableExisting("p11");
    model.addSValidStocks("Valid ticker");
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("9\np11\nvalid ticker\n5\n \n"
            + "2023-30--34/2023\n2023-01-21\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p11 add stock" +
            " called with ticker:valid ticker quantity:5.0 date:2023-01-21 portfolioName:p11";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of" +
            " the portfolio Enter the ticker symbol of the stock Enter" +
            " the quantity of stocks you want to sell Enter the date in the" +
            " format yyyy-MM-dd Invalid Date. Please enter a valid date in the" +
            " format yyyy-MM-dd Enter the date in the format yyyy-MM-dd Invalid Date." +
            " Please enter a valid date in the format yyyy-MM-dd Enter the date in the" +
            " format yyyy-MM-dd Stock bought successfully! Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void testSellStockInvalidDayInDate() {
    MockModel model = new MockModel();
    model.addMutableExisting("p11");
    model.addSValidStocks("Valid ticker");
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("9\np11\nvalid ticker\n5\n" +
            " \n2023-30-34\n2023-01-21\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p11 add stock" +
            " called with ticker:valid ticker quantity:5.0 date:2023-01-21 portfolioName:p11";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio" +
            " Enter the ticker symbol of the stock Enter the quantity of stocks you want" +
            " to sell Enter the date in the format yyyy-MM-dd Invalid Date. Please enter" +
            " a valid date in the format yyyy-MM-dd Enter the date in the format yyyy-MM-dd" +
            " Invalid Date. Please enter a valid date in the format yyyy-MM-dd Enter the date" +
            " in the format yyyy-MM-dd Stock bought successfully! Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testSellStockInvalidMonthInDate() {
    MockModel model = new MockModel();
    model.addMutableExisting("p11");
    model.addSValidStocks("Valid ticker");
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("9\np11\nvalid ticker" +
            "\n5\n2023-30-02\n2023-01-21\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p11 add stock" +
            " called with ticker:valid ticker quantity:5.0 date:2023-01-21 portfolioName:p11";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio" +
            " Enter the ticker symbol of the stock Enter the quantity of stocks you want to" +
            " sell Enter the date in the format yyyy-MM-dd Invalid Date. Please enter a valid" +
            " date in the format yyyy-MM-dd Enter the date in the format yyyy-MM-dd Stock bought" +
            " successfully! Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testSellStockEmptyDate() {
    MockModel model = new MockModel();
    model.addMutableExisting("p11");
    model.addSValidStocks("Valid ticker");
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("9\np11\nvalid ticker\n4" +
            "\n \n2023-01-21\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:" +
            "p11 add stock called with ticker:valid ticker quantity:4.0 date:2023-01-21 " +
            "portfolioName:p11";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio" +
            " Enter the ticker symbol of the stock Enter the quantity of stocks you" +
            " want to sell Enter the date in the format yyyy-MM-dd Invalid Date. Please" +
            " enter a valid date in the format yyyy-MM-dd Enter the date in the format" +
            " yyyy-MM-dd Stock bought successfully! Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testSellStockNullDate() {
    MockModel model = new MockModel();
    model.addMutableExisting("p11");
    model.addSValidStocks("Valid ticker");
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("9\np11\nvalid ticker\n" +
            "4\n\n2012-01-21\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p11 " +
            "add stock called with ticker:valid ticker quantity:4.0 date:2012-01-21 " +
            "portfolioName:p11";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio" +
            " Enter the ticker symbol of the stock Enter the quantity of stocks you" +
            " want to sell Enter the date in the format yyyy-MM-dd Invalid Date. Please" +
            " enter a valid date in the format yyyy-MM-dd Enter the date in the format" +
            " yyyy-MM-dd Stock bought successfully! Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testSellStockNullStockTicker() {
    MockModel model = new MockModel();
    model.addMutableExisting("p11");
    model.addSValidStocks("Valid ticker");
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("9\np11\n\nvalid ticker\n4\n" +
            "\n2012-01-21\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p11 " +
            "add stock called with ticker:valid ticker quantity:4.0 date:2012-01-21 " +
            "portfolioName:p11";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the " +
            "portfolio Enter the ticker symbol of the stock Ticker symbol cannot be empty! " +
            "Enter the ticker symbol of the stock Enter the quantity of stocks you want to " +
            "sell Enter the date in the format yyyy-MM-dd Invalid Date. Please enter a " +
            "valid date in the format yyyy-MM-dd Enter the date in the format " +
            "yyyy-MM-dd Stock bought successfully! Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void testSellStockEmptyStockTicker() {
    MockModel model = new MockModel();
    model.addMutableExisting("p11");
    model.addSValidStocks("Valid ticker");
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("9\np11\n \nvalid" +
            " ticker\n4\n\n2012-01-21\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p11 " +
            "add stock called with ticker:valid ticker quantity:4.0 date:2012-01-21 " +
            "portfolioName:p11";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the " +
            "portfolio Enter the ticker symbol of the stock Ticker symbol cannot be empty! " +
            "Enter the ticker symbol of the stock Enter the quantity of stocks you want to " +
            "sell Enter the date in the format yyyy-MM-dd Invalid Date. Please enter a " +
            "valid date in the format yyyy-MM-dd Enter the date in the format " +
            "yyyy-MM-dd Stock bought successfully! Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testSellStockInvalidEmptyPortfolioName() {
    MockModel model = new MockModel();
    model.addExistingPortfolio("p2");
    model.addMutableExisting("p1");
    model.addSValidStocks("APPL INC");
    model.addSValidStocks("GOOGLE INC");
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("9\n \np1\nappl inc" +
            "\n5\n2012-01-21\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:  " +
            "is portfolio present called with portfolioName:p1 add stock called " +
            "with ticker:appl inc quantity:5.0 date:2012-01-21 portfolioName:p1";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio " +
            "Invalid Portfolio Name. Please enter a valid Portfolio Name" +
            "(Mutable and Non-empty) Enter the name of the portfolio Enter the ticker symbol " +
            "of the stock Enter the quantity of stocks you want to sell Enter the date in the " +
            "format yyyy-MM-dd Stock bought successfully! Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void testSellStockInvalidNullPortfolioName() {
    MockModel model = new MockModel();
    model.addExistingPortfolio("p2");
    model.addMutableExisting("p1");
    model.addSValidStocks("APPL INC");
    model.addSValidStocks("GOOGLE INC");
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("9\n\np1\nappl inc\n5\n2021-01-21\nq").
            getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p1 add stock " +
            "called with ticker:appl inc quantity:5.0 date:2021-01-21 portfolioName:p1";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the" +
            " portfolio Invalid Portfolio Name. Please enter a valid Portfolio" +
            " Name(Mutable and Non-empty) Enter the name of the portfolio Enter" +
            " the ticker symbol of the stock Enter the quantity of stocks you want" +
            " to sell Enter the date in the format yyyy-MM-dd Stock bought successfully!" +
            " Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testSellStockInvalidNonMutablePortfolioName() {
    MockModel model = new MockModel();
    model.addExistingPortfolio("p2"); //Adding mutable portfolio
    model.addMutableExisting("p1");
    model.addSValidStocks("APPL INC");
    model.addSValidStocks("GOOGLE INC");
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("9\np2\np1\nappl inc" +
            "\n4\n2012-01-21\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p2" +
            " is portfolio present called with portfolioName:p1 add stock called" +
            " with ticker:appl inc quantity:4.0 date:2012-01-21 portfolioName:p1";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the" +
            " portfolio Invalid Portfolio Name. Please enter a valid Portfolio" +
            " Name(Mutable and Non-empty) Enter the name of the portfolio Enter" +
            " the ticker symbol of the stock Enter the quantity of stocks you want" +
            " to sell Enter the date in the format yyyy-MM-dd Stock bought" +
            " successfully! Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void testSellStockValidInputs() {
    MockModel model = new MockModel();
    model.addMutableExisting("p1");
    model.addSValidStocks("APPL INC");
    model.addSValidStocks("GOOGLE INC");
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("9\np1\nappl inc\n5\n2012-01-21\nq").
            getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p1 " +
            "add stock called with ticker:appl inc quantity:5.0 date:2012-01-21 portfolioName:p1";

    String expectedViewResult = "Main Menu Choose an option: Enter the name of the" +
            " portfolio Enter the ticker symbol of the stock Enter the quantity of stocks" +
            " you want to sell Enter the date in the format yyyy-MM-dd Stock bought" +
            " successfully! Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  //GetPortfolioValue Test


  @Test
  public void testGetPortfolioValueMutableEmptyPortfolioNameTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    model.addMutableExisting("p1");
    InputStream simulatedInput = new ByteArrayInputStream(("3\n \np1\n2024-03-19\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:  is portfolio" +
            " present called with portfolioName:p1 get portfolio value called with" +
            " portfolioName:p1 date:2024-03-19";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio" +
            " Invalid Portfolio Name. Please enter a valid Portfolio Name Enter the name" +
            " of the portfolio Enter the date in the format yyyy-MM-dd Asset: p1 Value:" +
            " 0.0 Date: 2024-03-19 Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testGetPortfolioValueMutableNullPortfolioNameTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    model.addMutableExisting("p1");
    InputStream simulatedInput = new ByteArrayInputStream(("3\n\np1\n2024-03-19\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p1 " +
            "get portfolio value called with portfolioName:p1 date:2024-03-19";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio" +
            " Invalid Portfolio Name. Please enter a valid Portfolio Name Enter the name of" +
            " the portfolio Enter the date in the format yyyy-MM-dd Asset: p1 Value: 0.0" +
            " Date: 2024-03-19 Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testGetPortfolioValueImmutableNullPortfolioNameTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    model.addExistingPortfolio("p1");
    InputStream simulatedInput = new ByteArrayInputStream(("3\n\np1\n2024-03-19\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p1 " +
            "get portfolio value called with portfolioName:p1 date:2024-03-19";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio" +
            " Invalid Portfolio Name. Please enter a valid Portfolio Name Enter the name of" +
            " the portfolio Enter the date in the format yyyy-MM-dd Asset: p1 Value: 0.0" +
            " Date: 2024-03-19 Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testGetPortfolioValueMutableNonPresentPortfolioNameTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    model.addMutableExisting("p1");
    InputStream simulatedInput = new ByteArrayInputStream(("3\np2\np1\n2024-03-19\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p2 i" +
            "s portfolio present called with portfolioName:p1 get portfolio value called" +
            " with portfolioName:p1 date:2024-03-19";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio" +
            " Invalid Portfolio Name. Please enter a valid Portfolio Name Enter the name of" +
            " the portfolio Enter the date in the format yyyy-MM-dd Asset: p1 Value: 0.0" +
            " Date: 2024-03-19 Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testGetPortfolioValueImmutableNonPresentPortfolioNameTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    model.addExistingPortfolio("p1");
    InputStream simulatedInput = new ByteArrayInputStream(("3\np2\np1\n2024-03-19\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p2 is " +
            "portfolio present called with portfolioName:p1 get portfolio value called " +
            "with portfolioName:p1 date:2024-03-19";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio" +
            " Invalid Portfolio Name. Please enter a valid Portfolio Name Enter the name of" +
            " the portfolio Enter the date in the format yyyy-MM-dd Asset: p1 Value: 0.0" +
            " Date: 2024-03-19 Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void testGetPortfolioValueImmutableValidPortfolioNameTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    model.addExistingPortfolio("p1");
    InputStream simulatedInput = new ByteArrayInputStream(("3\np1\n2024-03-19\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p1 get" +
            " portfolio value called with portfolioName:p1 date:2024-03-19";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the " +
            "portfolio Enter" +
            " the date in the format yyyy-MM-dd Asset: p1 Value: 0.0 Date: 2024-03-19 " +
            "Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testGetPortfolioValueMutableValidPortfolioNameTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    model.addMutableExisting("p1");
    InputStream simulatedInput = new ByteArrayInputStream(("3\np1\n2024-03-19\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p1 " +
            "get portfolio value called with portfolioName:p1 date:2024-03-19";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the" +
            " portfolio Enter the date in the format yyyy-MM-dd Asset:" +
            " p1 Value: 0.0 Date: 2024-03-19 Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testGetPortfolioValueValidPortfolioNameInvalidDateFormatTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    model.addExistingPortfolio("p1");
    InputStream simulatedInput = new ByteArrayInputStream(("3\np1\n2024/03/19\n2024-03-19\nq").
            getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p1 get " +
            "portfolio value called with portfolioName:p1 date:2024-03-19";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio" +
            " Enter the date in the format yyyy-MM-dd Invalid Date." +
            " Please enter a valid date in the format yyyy-MM-dd Enter the date in" +
            " the format yyyy-MM-dd Asset: p1 Value: 0.0 Date: 2024-03-19 Main Menu" +
            " Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void testGetPortfolioValueValidPortfolioNameInvalidMonthTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    model.addExistingPortfolio("p1");
    InputStream simulatedInput =
            new ByteArrayInputStream(("3\np1\n2024-123-19\n2024-03-19\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p1 " +
            "get portfolio value called with portfolioName:p1 date:2024-03-19";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio" +
            " Enter the date in the format yyyy-MM-dd Invalid Date." +
            " Please enter a valid date in the format yyyy-MM-dd Enter the date in" +
            " the format yyyy-MM-dd Asset: p1 Value: 0.0 Date: 2024-03-19 Main Menu" +
            " Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void testGetPortfolioValueValidPortfolioNameInvalidDateTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    model.addExistingPortfolio("p1");
    InputStream simulatedInput = new ByteArrayInputStream(("3\np1\n2024-01-119\n2024-03-19\nq").
            getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p1 " +
            "get portfolio value called with portfolioName:p1 date:2024-03-19";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio" +
            " Enter the date in the format yyyy-MM-dd Invalid Date." +
            " Please enter a valid date in the format yyyy-MM-dd Enter the date in" +
            " the format yyyy-MM-dd Asset: p1 Value: 0.0 Date: 2024-03-19 Main Menu" +
            " Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void testGetPortfolioValueValidPortfolioNameNullDateTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    model.addExistingPortfolio("p1");
    InputStream simulatedInput = new ByteArrayInputStream(("3\np1\n\n2024-03-19\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p1 " +
            "get portfolio value called with portfolioName:p1 date:2024-03-19";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio" +
            " Enter the date in the format yyyy-MM-dd Invalid Date." +
            " Please enter a valid date in the format yyyy-MM-dd Enter the date in" +
            " the format yyyy-MM-dd Asset: p1 Value: 0.0 Date: 2024-03-19 Main Menu" +
            " Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void testGetPortfolioValueValidPortfolioNameEmptyDateTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    model.addExistingPortfolio("p1");
    InputStream simulatedInput = new ByteArrayInputStream(("3\np1\n \n2024-03-19\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p1 " +
            "get portfolio value called with portfolioName:p1 date:2024-03-19";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio" +
            " Enter the date in the format yyyy-MM-dd Invalid Date. Please enter" +
            " a valid date in the format yyyy-MM-dd Enter the date in the format " +
            "yyyy-MM-dd Asset: p1 Value: 0.0 Date: 2024-03-19 Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  // GetCostBasis test

  @Test
  public void testGetCostBasisMutableEmptyPortfolioNameTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    model.addMutableExisting("p1");
    InputStream simulatedInput = new ByteArrayInputStream(("5\n \np1\n2024-03-19\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:" +
            "p1 get cost basis called with portfolioName:p1 date:2024-03-19";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio " +
            "Portfolio name cannot be empty! Enter the name of the portfolio Enter the date in " +
            "the format yyyy-MM-dd Asset: p1 Value: 0.0 Date: 2024-03-19 Main Menu " +
            "Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testGetCostBasisIMMutableEmptyPortfolioNameTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    model.addExistingPortfolio("p1");
    InputStream simulatedInput = new ByteArrayInputStream(("5\n \np1\n2024-03-19\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:" +
            "p1 get cost basis called with portfolioName:p1 date:2024-03-19";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio " +
            "Portfolio name cannot be empty! Enter the name of the portfolio Enter the date in " +
            "the format yyyy-MM-dd Asset: p1 Value: 0.0 Date: 2024-03-19 Main Menu " +
            "Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testGetCostBasisMutableNullPortfolioNameTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    model.addMutableExisting("p1");
    InputStream simulatedInput = new ByteArrayInputStream(("5\n\np1\n2024-03-19\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:" +
            "p1 get cost basis called with portfolioName:p1 date:2024-03-19";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio " +
            "Portfolio name cannot be empty! Enter the name of the portfolio Enter the date in " +
            "the format yyyy-MM-dd Asset: p1 Value: 0.0 Date: 2024-03-19 Main Menu " +
            "Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testHetCostBasisImmutableNullPortfolioNameTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    model.addExistingPortfolio("p1");
    InputStream simulatedInput = new ByteArrayInputStream(("5\n\np1\n2024-03-19\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:" +
            "p1 get cost basis called with portfolioName:p1 date:2024-03-19";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio " +
            "Portfolio name cannot be empty! Enter the name of the portfolio Enter the date in " +
            "the format yyyy-MM-dd Asset: p1 Value: 0.0 Date: 2024-03-19 Main Menu " +
            "Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testGetCostBasisValueMutableNonPresentPortfolioNameTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    model.addMutableExisting("p1");
    InputStream simulatedInput = new ByteArrayInputStream(("5\np2\np1\n2024-03-19\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:" +
            "p2 is portfolio present called with portfolioName:p1 get cost basis " +
            "called with portfolioName:p1 date:2024-03-19";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio " +
            "Portfolio name cannot be empty! Enter the name of the portfolio Enter the date in " +
            "the format yyyy-MM-dd Asset: p1 Value: 0.0 Date: 2024-03-19 Main Menu " +
            "Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testGetCostBasisImmutableValidPortfolioNameTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    model.addExistingPortfolio("p1");
    InputStream simulatedInput = new ByteArrayInputStream(("5\np1\n2024-03-19\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p1 " +
            "get cost basis called with portfolioName:p1 date:2024-03-19";
    String expectedViewResult = "Main Menu Choose an option: " +
            "Enter the name of the portfolio Enter the date in the format " +
            "yyyy-MM-dd Asset: p1 Value: 0.0 Date: 2024-03-19 Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testGetCostBasisMutableValidPortfolioNameTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    model.addMutableExisting("p1");
    InputStream simulatedInput = new ByteArrayInputStream(("5\np1\n2024-03-19\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p1 get " +
            "cost basis called with portfolioName:p1 date:2024-03-19";
    String expectedViewResult = "Main Menu Choose an option: " +
            "Enter the name of the portfolio Enter the date in the format " +
            "yyyy-MM-dd Asset: p1 Value: 0.0 Date: 2024-03-19 Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testGetCostBasisPortfolioNameInvalidDateFormatTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    model.addExistingPortfolio("p1");
    InputStream simulatedInput = new ByteArrayInputStream(("5\np1\n2024/03/19\n2024-03-19\nq").
            getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with " +
            "portfolioName:p1 get cost basis called with portfolioName:p1 date:2024-03-19";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio " +
            "Enter the date in the format yyyy-MM-dd Invalid Date. Please enter a valid date in " +
            "the format yyyy-MM-dd Enter the date in the format yyyy-MM-dd Asset: p1 Value: 0.0 " +
            "Date: 2024-03-19 Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void testGetCostBasisValidPortfolioNameInvalidMonthTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    model.addExistingPortfolio("p1");
    InputStream simulatedInput = new ByteArrayInputStream(("5\np1\n2024-123-19\n2024-03-19\nq").
            getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p1 " +
            "get cost basis called with portfolioName:p1 date:2024-03-19";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio" +
            " Enter the date in the format yyyy-MM-dd Invalid Date. Please enter a valid date in" +
            " the format yyyy-MM-dd Enter the date in the format yyyy-MM-dd Asset: p1 Value: 0.0" +
            " Date: 2024-03-19 Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void testGetCostBasisValidPortfolioNameInvalidDateTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    model.addExistingPortfolio("p1");
    InputStream simulatedInput = new ByteArrayInputStream(("5\np1\n2024-01-119\n2024-03-19\nq").
            getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:" +
            "p1 get cost basis called with portfolioName:p1 date:2024-03-19";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio " +
            "Enter the date in the format yyyy-MM-dd Invalid Date. Please enter a " +
            "valid date in the format yyyy-MM-dd Enter the date in the format yyyy-MM-dd " +
            "Asset: p1 Value: 0.0 Date: 2024-03-19 Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void testGetCostBasisValidPortfolioNameNullDateTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    model.addExistingPortfolio("p1");
    InputStream simulatedInput = new ByteArrayInputStream(("5\np1\n\n2024-03-19\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with " +
            "portfolioName:p1 get cost basis called with portfolioName:p1 date:2024-03-19";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio" +
            " Enter the date in the format yyyy-MM-dd Invalid Date. Please enter a valid date" +
            " in the format yyyy-MM-dd Enter the date in the format yyyy-MM-dd Asset: p1 Value:" +
            " 0.0 Date: 2024-03-19 Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void testGetPCostBasisValidPortfolioNameEmptyDateTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    model.addExistingPortfolio("p1");
    InputStream simulatedInput = new ByteArrayInputStream(("5\np1\n \n2024-03-19\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with " +
            "portfolioName:p1 get cost basis called with portfolioName:p1 date:2024-03-19";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio" +
            " Enter the date in the format yyyy-MM-dd Invalid Date. Please enter a valid date" +
            " in the format yyyy-MM-dd Enter the date in the format yyyy-MM-dd Asset: p1 Value:" +
            " 0.0 Date: 2024-03-19 Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  //GetPortfolioComposition test

  @Test
  public void testGetPortfolioMutableEmptyPortfolioNameTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    model.addMutableExisting("p1");
    InputStream simulatedInput = new ByteArrayInputStream(("4\n \np1\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:  " +
            "is portfolio present called with portfolioName:p1 get portfolio composition " +
            "called with portfolioName:p1";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio" +
            " Invalid Portfolio Name. Please enter a valid Portfolio Name Enter the" +
            " name of the portfolio Portfolio: p1 null Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testGetPortfolioImmutableEmptyPortfolioNameTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    model.addExistingPortfolio("p1");
    InputStream simulatedInput = new ByteArrayInputStream(("4\n \np1\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:  " +
            "is portfolio present called with portfolioName:p1 get portfolio " +
            "composition called with portfolioName:p1";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio" +
            " Invalid Portfolio Name. Please enter a valid Portfolio Name Enter the" +
            " name of the portfolio Portfolio: p1 null Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
    assertEquals(expectedModelResult, actualModelResult);
  }


  @Test
  public void testGetPortfolioMutableNullPortfolioNameTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    model.addMutableExisting("p1");
    InputStream simulatedInput = new ByteArrayInputStream(("4\n\np1\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p1 get " +
            "portfolio composition called with portfolioName:p1";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio" +
            " Invalid Portfolio Name. Please enter a valid Portfolio Name Enter the name of" +
            " the portfolio Portfolio: p1 null Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testGetPortfolioImmutableNullPortfolioNameTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    model.addExistingPortfolio("p1");
    InputStream simulatedInput = new ByteArrayInputStream(("4\n\np1\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p1 " +
            "get portfolio composition called with portfolioName:p1";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the" +
            " portfolio Invalid Portfolio Name. Please enter a valid Portfolio Name Enter" +
            " the name of the portfolio Portfolio: p1 null Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testGetPortfolioMutableNonPresentPortfolioNameTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    model.addMutableExisting("p1");
    InputStream simulatedInput = new ByteArrayInputStream(("4\np3\np1\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p3 " +
            "is portfolio present called with portfolioName:p1 get portfolio composition " +
            "called with portfolioName:p1";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio" +
            " Invalid Portfolio Name. Please enter a valid Portfolio Name Enter the name" +
            " of the portfolio Portfolio: p1 null Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testGetPortfolioImmutableNonPresentPortfolioNameTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    model.addExistingPortfolio("p1");
    InputStream simulatedInput = new ByteArrayInputStream(("4\np3\np1\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p3 is " +
            "portfolio present called with portfolioName:p1 get portfolio composition " +
            "called with portfolioName:p1";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio" +
            " Invalid Portfolio Name. Please enter a valid Portfolio Name Enter the name" +
            " of the portfolio Portfolio: p1 null Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void testGetPortfolioImmutableValidPortfolioNameTest() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    model.addExistingPortfolio("p1");
    InputStream simulatedInput = new ByteArrayInputStream(("4\np1\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p1 get " +
            "portfolio composition called with portfolioName:p1";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio" +
            " Portfolio: p1 null Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

}
