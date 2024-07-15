package controllertest;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.IMainController;
import controller.OldMainMenu;
import controller.commands.DollarCostAveraging;
import controller.commands.ICommand;

import static org.junit.Assert.assertEquals;

/**
 * This class tests the creation portfolio functionality of the application inclusive of
 * CreateMutable portfolio, Create Immutable portfolio and Create Portfolio with DCA.
 */

public class CreatePortfolioTest {

  private ICommand c;
  private MockModel m;
  private MockView v;
  private static final LocalDate start = LocalDate.of(2024, 3, 15);
  private static final LocalDate end = LocalDate.of(2024, 4, 9);
  private Map<String, Integer> split;

  @Test
  public void testCreateEmptyMutablePortfolioValidInputs() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("1\np1\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p1 create "
            + "portfolio called with portfolioName:p1";
    String expectedViewResult = "Main Menu Choose an option: " +
            "Enter the name of the portfolio Portfolio created successfully " +
            "Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testCreateEmptyMutablePortfolioWithEmptyPortfolioName() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("1\n \nm1\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:m1 "
            + "create portfolio called with portfolioName:m1";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the " +
            "portfolio Invalid portfolio name! Please make sure to enter " +
            "a unique and non-empty portfolio name. Enter the name of the " +
            "portfolio Portfolio created successfully Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testCreateEmptyMutablePortfolioWithEmptySequencePortfolioName() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("1\n      \nm1\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:m1 create "
            + "portfolio called with portfolioName:m1";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the " +
            "portfolio Invalid portfolio name! Please make sure to enter " +
            "a unique and non-empty portfolio name. Enter the name of the " +
            "portfolio Portfolio created successfully Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testCreateImmutablePortfolioWithNullPortfolioNameTestInputs() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("2\n\np1\n2\nappl inc\n10\n"
            + "google\n20\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p1 "
            + "create portfolio called with portfolioName:p1 add stock "
            + "called with ticker:appl inc quantity:10.0 date:"
            + LocalDate.now().minusDays(1).toString() + " portfolioName:p1 "
            + "add stock called with ticker:google quantity:20.0 date:"
            + LocalDate.now().minusDays(1).toString() + " "
            + "portfolioName:p1 flip mutability called with portfolioName:p1";
    assertEquals(expectedModelResult, actualModelResult);

  }

  @Test
  public void testCreateImmutablePortfolioWithEmptyNoSharesTestInputs() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("2\np1\n \n2\nappl inc\n10\n"
            + "google\n20\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p1 "
            + "create portfolio called with portfolioName:p1 add stock called with "
            + "ticker:appl inc quantity:10.0 date:"
            + LocalDate.now().minusDays(1).toString() + " portfolioName:p1 add stock "
            + "called with ticker:google quantity:20.0 date:"
            + LocalDate.now().minusDays(1).toString() + " portfolioName:p1 "
            + "flip mutability called with portfolioName:p1";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of " +
            "the portfolio Enter the number of stocks you want to buy Invalid input. " +
            "Please enter a valid positive Integer. Enter the number of stocks you want " +
            "to buy Please enter details for the Stock 1 Enter the ticker symbol of the" +
            " stock Enter the quantity of stocks you want to buy Please enter details for" +
            " the Stock 2 Enter the ticker symbol of the stock Enter the quantity of stocks" +
            " you want to buy Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void testCreateImmutablePortfolioWithInvalidStringForNoSharesTestInputs() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("2\np1\nhi\n2\nappl inc\n10\n"
            + "google\n20\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p1 create "
            + "portfolio called with portfolioName:p1 add stock called with ticker:appl inc "
            + "quantity:10.0 date:" + LocalDate.now().minusDays(1) + " "
            + "portfolioName:p1 add stock called with ticker:google quantity:20.0 "
            + "date:" + LocalDate.now().minusDays(1) + " "
            + "portfolioName:p1 flip mutability called with portfolioName:p1";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio " +
            "Enter the number of stocks you want to buy Invalid input. Please enter a " +
            "valid positive Integer. Enter the number of stocks you want to buy Please " +
            "enter details for the Stock 1 Enter the ticker symbol of the stock Enter the " +
            "quantity of stocks you want to buy Please enter details for the Stock 2 Enter " +
            "the ticker symbol of the stock Enter the quantity of stocks you want to buy " +
            "Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void testCreateImmutablePortfolioWithNegativeInputForNoSharesTestInputs() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("2\np1\n-1\n2\nappl inc\n10\n" +
            "google\n20\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p1 create " +
            "portfolio called with portfolioName:p1 add stock called with ticker:appl inc " +
            "quantity:10.0 date:" + LocalDate.now().minusDays(1) + " " +
            "portfolioName:p1 add stock called with ticker:google quantity:20.0 date:"
            + LocalDate.now().minusDays(1).toString() + " portfolioName:p1 " +
            "flip mutability called with portfolioName:p1";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio" +
            " Enter the number of stocks you want to buy Invalid input. Please enter a" +
            " valid positive Integer. Enter the number of stocks you want to buy Please" +
            " enter details for the Stock 1 Enter the ticker symbol of the stock Enter" +
            " the quantity of stocks you want to buy Please enter details for the Stock" +
            " 2 Enter the ticker symbol of the stock Enter the quantity of stocks you" +
            " want to buy Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void testCreateImmutablePortfolioWithNonIntegerInputForNoSharesTestInputs() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("2\np1\n1.452\n2\nappl inc\n10\n" +
            "google\n20\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p1 create " +
            "portfolio called with portfolioName:p1 add stock called with ticker:appl " +
            "inc quantity:10.0 date:" + LocalDate.now().minusDays(1).toString() +
            " portfolioName:p1 add stock called with ticker:google quantity:20.0 date:"
            + LocalDate.now().minusDays(1).toString() + " portfolioName:p1 " +
            "flip mutability called with portfolioName:p1";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio" +
            " Enter the number of stocks you want to buy Invalid input. Please enter" +
            " a valid positive Integer. Enter the number of stocks you want to buy" +
            " Please enter details for the Stock 1 Enter the ticker symbol of the" +
            " stock Enter the quantity of stocks you want to buy Please enter details" +
            " for the Stock 2 Enter the ticker symbol of the stock Enter the quantity" +
            " of stocks you want to buy Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testCreateImmutablePortfolioWithInvalidNotIntegerValuesForNoSharesTestInputs() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("2\np1\n3.5\n2\nappl inc\n10\n" +
            "google\n20\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p1 " +
            "create portfolio called with portfolioName:p1 add stock called with ticker:" +
            "appl inc quantity:10.0 date:" + LocalDate.now().minusDays(1).toString()
            + " portfolioName:p1 add stock called with ticker:google quantity:20.0 date:" +
            LocalDate.now().minusDays(1).toString() + " portfolioName:p1 flip " +
            "mutability called with portfolioName:p1";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio " +
            "Enter the number of stocks you want to buy Invalid input. Please enter a " +
            "valid positive Integer. Enter the number of stocks you want to buy Please " +
            "enter details for the Stock 1 Enter the ticker symbol of the stock Enter " +
            "the quantity of stocks you want to buy Please enter details for the Stock " +
            "2 Enter the ticker symbol of the stock Enter the quantity of stocks you want " +
            "to buy Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void testCreateImmutablePortfolioWithNullForNoSharesTestInputs() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("2\np1\n\n2\nappl inc\n10\n" +
            "google\n20\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p1 " +
            "create portfolio called with portfolioName:p1 add stock called with ticker:" +
            "appl inc quantity:10.0 date:"
            + LocalDate.now().minusDays(1).toString() + " portfolioName:p1 add" +
            " stock called with ticker:google quantity:20.0" +
            " date:" + LocalDate.now().minusDays(1).toString() + " " +
            "portfolioName:p1 flip mutability called with portfolioName:p1";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio" +
            " Enter the number of stocks you want to buy Invalid input. Please enter" +
            " a valid positive Integer. Enter the number of stocks you want to buy Please" +
            " enter details for the Stock 1 Enter the ticker symbol of the stock Enter" +
            " the quantity of stocks you want to buy Please enter details for the Stock 2" +
            " Enter the ticker symbol of the stock Enter the quantity of stocks you want" +
            " to buy Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void testCreateImmutablePortfolioWithEmptyInputForTickerTestInputs() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("2\np1\n2\n \nntfx\n10\n" +
            "google\n20\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p1 " +
            "create portfolio called with portfolioName:p1 add stock called with " +
            "ticker:ntfx " +
            "quantity:10.0 date:" + LocalDate.now().minusDays(1).toString() + " " +
            "portfolioName:p1 add stock called with ticker:google quantity:20.0 " +
            "date:" + LocalDate.now().minusDays(1).toString() + " " +
            "portfolioName:p1 flip mutability called with portfolioName:p1";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio " +
            "Enter the number of stocks you want to buy Please enter details for the " +
            "Stock 1 Enter the ticker symbol of the stock Ticker symbol cannot be empty! " +
            "Enter the ticker symbol of the stock Enter the quantity of stocks you want to " +
            "buy Please enter details for the Stock 2 Enter the ticker symbol of the stock " +
            "Enter the quantity of stocks you want to buy Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testCreateImmutablePortfolioWithNullInputForTickerTestInputs() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("2\np1\n2\n\nntfx\n10\n" +
            "google\n20\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p1 create " +
            "portfolio called with portfolioName:p1 add stock called with ticker:ntfx " +
            "quantity:10.0 date:" + LocalDate.now().minusDays(1).toString() + " " +
            "portfolioName:p1 add stock called with ticker:google quantity:" +
            "20.0 date:" + LocalDate.now().minusDays(1).toString() + " " +
            "portfolioName:p1 flip mutability called with portfolioName:p1";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio" +
            " Enter the number of stocks you want to buy Please enter details for the Stock" +
            " 1 Enter the ticker symbol of the stock Ticker symbol cannot be empty! Enter the" +
            " ticker symbol of the stock Enter the quantity of stocks you want to buy Please" +
            " enter details for the Stock 2 Enter the ticker symbol of the stock Enter the" +
            " quantity of stocks you want to buy Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void testCreateImmutablePortfolioWithNullInputForStockQuantityTestInputs() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("2\np1\n2\nntfx\n\n10\n" +
            "google\n20\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p1 " +
            "create portfolio called with portfolioName:p1 add stock called with " +
            "ticker:ntfx quantity:10.0 date:" + LocalDate.now().minusDays(1).
            toString() + " portfolioName:p1 add stock called with ticker:google quantity:20.0 " +
            "date:" + LocalDate.now().minusDays(1).toString() + " portfolioName:" +
            "p1 flip mutability called with portfolioName:p1";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the " +
            "portfolio Enter the number of stocks you want to buy Please enter " +
            "details for the Stock 1 Enter the ticker symbol of the stock Enter " +
            "the quantity of stocks you want to buy Invalid input. Please enter a " +
            "valid positive Integer. Enter the quantity of stocks you want to buy " +
            "Please enter details for the Stock 2 Enter the ticker symbol of the stock " +
            "Enter the quantity of stocks you want to buy Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void testCreateImmutablePortfolioWithNonIntegerInputForStockQuantityTestInputs() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("2\np1\n2\nntfx\n3.214\n10\n" +
            "google\n20\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p1 create " +
            "portfolio called with portfolioName:p1 add stock called with ticker:ntfx " +
            "quantity:10.0 date:" + LocalDate.now().minusDays(1).toString() + " " +
            "portfolioName:p1 add stock called with ticker:google quantity:20.0 " +
            "date:" + LocalDate.now().minusDays(1).toString() + " " +
            "portfolioName:p1 flip mutability called with portfolioName:p1";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio" +
            " Enter the number of stocks you want to buy Please enter details for the" +
            " Stock 1 Enter the ticker symbol of the stock Enter the quantity of stocks" +
            " you want to buy Invalid input. Please enter a valid positive Integer." +
            " Enter the quantity of stocks you want to buy Please enter details for" +
            " the Stock 2 Enter the ticker symbol of the stock Enter the quantity of" +
            " stocks you want to buy Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void testCreateImmutablePortfolioWithNegativeInputForStockQuantityTestInputs() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("2\np1\n2\nntfx\n-1\n10\n" +
            "google\n20\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p1 create " +
            "portfolio called with portfolioName:p1 add stock called with ticker:ntfx quantity:" +
            "10.0 date:" + LocalDate.now().minusDays(1).toString()
            + " portfolioName:p1 " +
            "add stock called with ticker:google quantity:20.0 date:"
            + LocalDate.now().minusDays(1).toString()
            + " portfolioName:p1 flip mutability called with portfolioName:p1";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio" +
            " Enter the number of stocks you want to buy Please enter details for the" +
            " Stock 1 Enter the ticker symbol of the stock Enter the quantity of stocks" +
            " you want to buy Invalid input. Please enter a valid positive Integer." +
            " Enter the quantity of stocks you want to buy Please enter details for" +
            " the Stock 2 Enter the ticker symbol of the stock Enter the quantity of" +
            " stocks you want to buy Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  @Test
  public void testCreateImmutablePortfolioValidInputs() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    InputStream simulatedInput = new ByteArrayInputStream(("2\np1\n2\nappl inc\n10\n" +
            "google\n20\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:p1 create " +
            "portfolio called with portfolioName:p1 add stock called with ticker:appl inc " +
            "quantity:10.0 date:" + LocalDate.now().minusDays(1).toString() + " " +
            "portfolioName:p1 add stock called with ticker:google " +
            "quantity:20.0 date:" + LocalDate.now().minusDays(1).toString() + " " +
            "portfolioName:p1 flip mutability called with portfolioName:p1";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio" +
            " Enter the number of stocks you want to buy Please enter details for the Stock 1" +
            " Enter the ticker symbol of the stock Enter the quantity of stocks you want to" +
            " buy Please enter details for the Stock 2 Enter the ticker symbol of the stock" +
            " Enter the quantity of stocks you want to buy Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testCreateImmutablePortfolioWithExistingPortfolioNameInput() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    model.addExistingPortfolio("P1");
    InputStream simulatedInput = new ByteArrayInputStream(("2\nP1\np2\n2\nAPPL INC\n10\n" +
            "GOOGLE\n20\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:P1 " +
            "is portfolio present called with portfolioName:p2 create portfolio called " +
            "with portfolioName:p2 add stock called with ticker:APPL INC " +
            "quantity:10.0 date:" + LocalDate.now().minusDays(1).toString() + " " +
            "portfolioName:p2 add stock called with ticker:GOOGLE " +
            "quantity:20.0 date:" + LocalDate.now().minusDays(1).toString() + " " +
            "portfolioName:p2 flip mutability called with portfolioName:p2";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio" +
            " Invalid portfolio name! Please make sure to enter a unique and non-empty portfolio" +
            " name. Enter the name of the portfolio Enter the number of stocks you want to buy" +
            " Please enter details for the Stock 1 Enter the ticker symbol of the stock Enter" +
            " the quantity of stocks you want to buy Please enter details for the Stock 2" +
            " Enter the ticker symbol of the stock Enter the quantity of stocks you want" +
            " to buy Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }


  @Test
  public void testCreateImmutablePortfolioWithInvalidStockTickerInput() {
    MockModel model = new MockModel();
    MockView view = new MockView();
    model.addSValidStocks("APPL INC");
    model.addSValidStocks("GOOGLE");
    InputStream simulatedInput = new ByteArrayInputStream(("2\nP1\n2\nAPPLE\n10\n" +
            "APPL INC\n10\n" +
            "GOOGLE\n20\nq").getBytes());
    IMainController controller = new OldMainMenu(model, view, simulatedInput);
    controller.execute();
    List<String> modelLog = model.getLog();
    List<String> viewLog = view.getLog();
    String actualModelResult = String.join(" ", modelLog);
    String actualViewResult = String.join(" ", viewLog);
    String expectedModelResult = "is portfolio present called with portfolioName:P1 create" +
            " portfolio called with portfolioName:P1 add stock called with " +
            "ticker:APPL INC quantity:" +
            "10.0 date:" + LocalDate.now().minusDays(1).toString() + " " +
            "portfolioName:P1 add stock called with ticker:GOOGLE " +
            "quantity:20.0 date:" + LocalDate.now().minusDays(1).toString() + " " +
            "portfolioName:P1 flip mutability called with portfolioName:P1";
    String expectedViewResult = "Main Menu Choose an option: Enter the name of the portfolio" +
            " Enter the number of stocks you want to buy Please enter details" +
            " for the Stock 1 Enter the ticker symbol of the stock Enter the" +
            " quantity of stocks you want to buy Stock not found Please enter" +
            " details for the Stock 1 Enter the ticker symbol of the stock Enter" +
            " the quantity of stocks you want to buy Please enter details for the" +
            " Stock 2 Enter the ticker symbol of the stock Enter the quantity of" +
            " stocks you want to buy Main Menu Choose an option:";
    assertEquals(expectedModelResult, actualModelResult);
    assertEquals(expectedViewResult, actualViewResult);
  }

  private void dcaPrivateHelper() {
    m = new MockModel();
    v = new MockView();
    split = new HashMap<>();
    split.put("AAPL", 40);
    split.put("TSLA", 30);
    split.put("IRTC", 30);
    LocalDate[] dates = new LocalDate[]{start, end};
    c = new DollarCostAveraging(m, v, "Test", dates, split, 1000,
            1, ChronoUnit.WEEKS);
  }

  @Test
  public void testExecuteWithEndDate() {
    this.dcaPrivateHelper();
    c.execute();
    String actual = m.getLog().get(0);
    String expected = "added DCA with: name: Test, split: {AAPL=400.0, TSLA=300.0, IRTC=300.0},"
            + " startDate: 2024-03-15, unit: Weeks, length: 1, reps: 3";
    assertEquals(expected, actual);
    actual = v.getLog().get(0);
    expected = "Dollar Cost Averaging strategy for 'Test' initiated successfully.";
    assertEquals(expected, actual);
  }

  @Test
  public void testExecuteWithNoEnd() {
    this.dcaPrivateHelper();
    c = new DollarCostAveraging(m, v, "Test", new LocalDate[]{start}, split,
            1000, 1, ChronoUnit.WEEKS);
    c.execute();
    String actual = m.getLog().get(0);
    String expected = "added DCA with: name: Test, split: {AAPL=400.0, TSLA=300.0, IRTC=300.0},"
            + " startDate: 2024-03-15, unit: Weeks, length: 1, reps: -1";
    assertEquals(expected, actual);
    actual = v.getLog().get(0);
    expected = "Dollar Cost Averaging strategy for 'Test' initiated successfully.";
    assertEquals(expected, actual);
  }

  @Test
  public void testExceptionFromModel() {
    this.dcaPrivateHelper();
    c = new DollarCostAveraging(m, v, "exception", new LocalDate[]{start}, split,
            1000, 1, ChronoUnit.WEEKS);
    c.execute();
    String expectedMessage = "added DCA with: name: exception,"
            + " split: {AAPL=400.0, TSLA=300.0, IRTC=300.0},"
            + " startDate: 2024-03-15, unit: Weeks, length: 1, reps: -1";
    String actualModelMessage = m.getLog().get(0);
    assertEquals(expectedMessage, actualModelMessage);
    actualModelMessage = m.getLog().get(1);
    assertEquals("throw exception", actualModelMessage);
    String actualViewResponse = v.getLog().get(0);
    String expectedViewResponse = "set to throw exception";
    assertEquals(expectedViewResponse, actualViewResponse);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptySplit() {
    this.dcaPrivateHelper();
    new DollarCostAveraging(m, v, "test", new LocalDate[]{start},
            new HashMap<>(), 100, 1, ChronoUnit.WEEKS);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyDates() {
    this.dcaPrivateHelper();
    new DollarCostAveraging(m, v, "test", new LocalDate[]{},
            split, 100, 1, ChronoUnit.WEEKS);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoreThanTwoDates() {
    this.dcaPrivateHelper();
    c = new DollarCostAveraging(m, v, "Test", new LocalDate[]{start, start, start},
            split, 1000, 1, ChronoUnit.WEEKS);
  }


}
