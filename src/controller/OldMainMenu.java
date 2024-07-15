package controller;

import controller.commands.AnalyzePortfolioPerformance;
import controller.commands.AnalyzeStockPerformance;
import controller.commands.BuyStock;
import controller.commands.CreateImmutablePortfolio;
import controller.commands.CreateMutablePortfolio;
import controller.commands.DayStockPerformance;
import controller.commands.DollarCostAveraging;
import controller.commands.GetCostBasis;
import controller.commands.GetCrossOverDays;
import controller.commands.GetPortfolioComposition;
import controller.commands.GetPortfolioValue;
import controller.commands.ICommand;
import controller.commands.LoadPortfolio;
import controller.commands.MovingAverageCrossOver;
import controller.commands.PeriodStockPerformance;
import controller.commands.SavePortfolio;
import controller.commands.WeightedAddStock;
import controller.commands.XDayMovingAverage;
import controller.filereader.FileReaderFactory;
import controller.filereader.IFileReaderFactory;
import controller.filewriter.FileWriterFactory;
import controller.filewriter.IFileWriterFactory;
import controller.inputhandler.IInputHandler;
import controller.inputhandler.InputHandler;
import controller.onlinereader.AlphaVantageReader;
import controller.onlinereader.IOnlineReader;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Supplier;
import model.OngoingTransactionModel;
import model.OngoingTransactionModelImpl;
import view.text.IGraphingView;

/**
 * The main menu class is the controller class that controls the main menu of the.
 * portfolio program.
 */


public class OldMainMenu implements IMainController {

  private OngoingTransactionModel model;
  private IGraphingView view;

  private final IInputHandler inputHandler;

  private final IFileReaderFactory factoryReader;

  private final IFileWriterFactory factoryWriter;
  private final Map<String, Supplier<ICommand>> commands = new HashMap<>();

  private final IOnlineReader onlineReader;

  private IFeatures features;

  /**
   * Constructs the Main Menu Controller with the model and view needed.
   *
   * @param model the model of the portfolio program.
   * @param view  the view of the portfolio program.
   */


  public OldMainMenu(OngoingTransactionModel model, IGraphingView view,
                     InputStream in) {
    this.factoryWriter = new FileWriterFactory();
    this.factoryReader = new FileReaderFactory();
    this.onlineReader = new AlphaVantageReader();
    Scanner s = new Scanner(in);
    this.view = view;
    this.model = (model != null) ? model : this.buildModel(this.onlineReader);
    this.inputHandler = new InputHandler(s, this.view);
    this.initialiseCommands();
  }

  private OngoingTransactionModel buildModel(IOnlineReader onlineReader) {
    Function<String, Map<LocalDate, List<Double>>> dataGetter = onlineReader::getData;
    return new OngoingTransactionModelImpl(dataGetter);
  }

  /**
   * Returns the function that gets the online data.
   *
   * @return the function that gets the online data.
   */
  @Override
  public Function<String, Map<LocalDate, List<Double>>> getOnlineDataFunc() {
    return onlineReader::getData;
  }

  /**
   * Sets the model of the main menu controller.
   *
   * @param model the model of the main menu controller.
   */
  @Override
  public void setModel(OngoingTransactionModel model) {
    this.model = model;
  }

  /**
   * Sets the view of the main menu controller.
   *
   * @param view the view of the main menu controller.
   */

  @Override
  public void setView(IGraphingView view) {
    this.view = view;
    this.features.setView(view);
  }

  /**
   * Returns the features objects  of the main menu controller.
   *
   * @return the features object of the main menu controller.
   */

  @Override
  public IFeatures getFeatures() {
    this.features = new FeaturesImpl(this.model, this.view, this.factoryWriter);
    return this.features;
  }


  private void initialiseCommands() {
    commands.put("1", this::createMutablePortfolioCommand);
    commands.put("2", this::createImmutablePortfolioCommand);
    commands.put("3", this::createGetPortfolioValueCommand);
    commands.put("4", this::createGetPortfolioCompositionCommand);
    commands.put("5", this::createGetCostBasisCommand);
    commands.put("6", this::createLoadPortfolioCommand);
    commands.put("7", this::createSavePortfolioCommand);
    commands.put("8", this::createBuyStockCommand);
    commands.put("9", this::createSellStockCommand);
    commands.put("10", this::createGetDailyStockPerformanceCommand);
    commands.put("11", this::createPeriodStockPerformanceCommand);
    commands.put("12", this::createXDayMovingAverageCommand);
    commands.put("13", this::createGetCrossOverDaysCommand);
    commands.put("14", this::createMovingAverageCrossOverCommand);
    commands.put("15", this::createAnalyzeStockPerformanceCommand);
    commands.put("16", this::createAnalyzePortfolioPerformanceCommand);
    commands.put("17", this::createWeightedAddStockCommand);
    commands.put("18", this::createDollarCostAveragingCommand);
  }

  private void loop() {
    boolean flag = true;
    while (flag) {
      view.displayMainMenu();
      String choice = inputHandler.getInputString("Choose an option:",
          s -> commands.containsKey(s) || s.equals("q"), "Invalid choice");
      if ("q".equals(choice)) {
        flag = false;
      } else {

        Supplier<ICommand> commandSupplier = commands.get(choice);
        if (commandSupplier != null) {
          ICommand command = commandSupplier.get();
          try {
            command.execute();
          } catch (IllegalArgumentException e) {
            view.displayErrorMessage(e);
          }
        }
      }
    }
  }


  private String askStockTicker() {
    return inputHandler.getInputString("Enter the ticker symbol of the stock",
        (String s) -> s != null && !s.trim().isEmpty(), "Ticker symbol "
                    + "cannot be empty!");
  }

  private int askStockQuantity() {
    return inputHandler.getInputInteger("Enter the quantity of stocks you want to buy",
        (Integer s) -> s > 0, "Invalid input. Please enter a "
                    + "valid positive Integer.");
  }


  private boolean isTickerValid(String ticker) {
    try {
      new AlphaVantageReader().getData(ticker);
      return true;
    } catch (Exception e) {
      return false;
    }
  }


  private ICommand createMutablePortfolioCommand() {
    String portfolioName = inputHandler.getInputString("Enter the name of the portfolio",
        (String s) -> s != null && !s.trim().isEmpty() && !(this.model.isPortfolioPresent(s)),
            "Invalid portfolio name! Please make sure to enter a unique and "
                    + "non-empty portfolio name.");
    return new CreateMutablePortfolio(this.model, this.view, portfolioName);
  }

  private ICommand createImmutablePortfolioCommand() {
    return new CreateImmutablePortfolio(this.model, this.view, inputHandler);
  }

  private ICommand createGetDailyStockPerformanceCommand() {
    String ticker = inputHandler.getInputString("Enter the ticker symbol of the stock",
        (String s) -> s != null && !s.trim().isEmpty(), "Ticker symbol "
                    + "cannot be empty!");
    LocalDate date = inputHandler.getInputDate("Enter the date in the format yyyy-MM-dd",
            null, "Invalid Date. Please enter a valid date in the "
                    + "format yyyy-MM-dd");
    return new DayStockPerformance(this.model, this.view, ticker, date);
  }


  private ICommand createGetCostBasisCommand() {
    String portfolioName = inputHandler.getInputString("Enter the name of the portfolio",
        (String s) -> s != null && !s.trim().isEmpty() && (this.model.isPortfolioPresent(s)),
            "Portfolio name cannot be empty!");
    LocalDate date = inputHandler.getInputDate("Enter the date in the format yyyy-MM-dd",
            null, "Invalid Date. Please enter a valid date in the "
                    + "format yyyy-MM-dd");
    return new GetCostBasis(this.model, this.view, portfolioName, date);
  }


  private ICommand createGetCrossOverDaysCommand() {
    String ticker = inputHandler.getInputString("Enter the ticker symbol of the stock",
        (String s) -> s != null && !s.trim().isEmpty() && this.isTickerValid(s),
            "Invalid Ticker Symbol!Please Provide a valid ticker Symbol!");

    LocalDate startDate = inputHandler.getInputDate("Enter the start Date in the "
                    + "format yyyy-MM-dd",
        (LocalDate d) -> (new ArrayList<>(this.model.getStockPrices(ChronoUnit.DAYS, ticker,
                    d, 30, true, 1).values()).size() != 30),
            "Invalid Date. Please enter a valid date in the format yyyy-MM-dd");
    LocalDate endDate = inputHandler.getInputDate(
            "Enter the date in the format yyyy-MM-dd",
        (LocalDate d) -> !d.isAfter(startDate), "Invalid Date. "
                    + "Please enter a valid date (format yyyy-MM-dd) end date must be greater "
                    + "than start date.");
    return new GetCrossOverDays(this.model, this.view, ticker, startDate, endDate,
            new XDayMovingAverage(this.model, this.view, ticker, 30, startDate));
  }


  private ICommand createGetPortfolioCompositionCommand() {
    String portfolioName = this.inputHandler.getInputString("Enter the name of "
                    + "the portfolio",
        (String s) -> s != null && !s.isEmpty() && this.model.isPortfolioPresent(s),
            "Invalid Portfolio Name. Please enter a valid Portfolio Name");
    return new GetPortfolioComposition(this.model, this.view, portfolioName);
  }


  private ICommand createGetPortfolioValueCommand() {
    String portfolioName = this.inputHandler.getInputString("Enter the name of "
                    + "the portfolio",
        (String s) -> s != null && !s.isEmpty() && this.model.isPortfolioPresent(s),
            "Invalid Portfolio Name. Please enter a valid Portfolio Name");
    LocalDate date = inputHandler.getInputDate("Enter the date in the format yyyy-MM-dd",
            null, "Invalid Date. Please enter a valid date "
                    + "in the format yyyy-MM-dd");
    return new GetPortfolioValue(this.model, this.view, portfolioName, date);
  }


  private ICommand createLoadPortfolioCommand() {
    int portfolioType = this.inputHandler.getInputInteger("Enter the type of "
                    + "portfolio that you want to Load\n"
                    + "1. Mutable Portfolio\n"
                    + "2. Immutable Portfolio\n",
        (Integer s) -> s.equals(1) || s.equals(2), "Invalid Portfolio Type");
    String filePath = inputHandler.getInputString("Enter the path"
                    + " of the portfolio file: ",
        s -> s != null && !s.trim().isEmpty(), "Path cannot be empty");
    return new LoadPortfolio(model, view, factoryReader, portfolioType, filePath);
  }

  private ICommand createSavePortfolioCommand() {
    String portfolioName = inputHandler.getInputString("Enter the name of the portfolio",
        (String s) -> s != null && !s.trim().isEmpty() && (this.model.isPortfolioPresent(s)),
            "Portfolio name cannot be empty!");
    String filePath = inputHandler.getInputString("Enter the path of the portfolio file: ",
        s -> s != null && !s.trim().isEmpty(),
            "Path cannot be empty");
    return new SavePortfolio(model, view, factoryWriter, portfolioName, filePath);
  }

  private ICommand createBuyStockCommand() {
    String mutablePortfolioName = inputHandler.getInputString("Enter the name of the"
                    + " portfolio",
        (String s) -> s != null && !s.isEmpty() && this.model.isPortfolioPresent(s)
                    && this.model.isPortfolioMutable(s),
            "Invalid Portfolio Name. Please enter a valid Portfolio "
                    + "Name(Mutable and Non-empty)");
    String stockTicker = this.askStockTicker();
    int quantity = this.askStockQuantity();
    LocalDate date = inputHandler.getInputDate("Enter the date in the format yyyy-MM-dd",
            null, "Invalid Date. Please enter a valid date in the "
                    + "format yyyy-MM-dd");
    return new BuyStock(this.model, this.view, mutablePortfolioName, stockTicker, quantity, date);
  }

  private ICommand createSellStockCommand() {
    String mutablePortfolioName = inputHandler.getInputString("Enter the name of "
                    + "the portfolio",
        (String s) -> s != null && !s.isEmpty() && this.model.isPortfolioPresent(s)
                    && this.model.isPortfolioMutable(s),
            "Invalid Portfolio Name. Please enter a valid Portfolio "
                    + "Name(Mutable and Non-empty)");
    String stockTicker = this.askStockTicker();
    int quantity = inputHandler.getInputInteger("Enter the quantity of stocks you want "
                    + "to sell",
        (Integer s) -> s > 0, "Invalid input. Please enter a "
                    + "valid positive Integer.");
    LocalDate date = inputHandler.getInputDate("Enter the date in the format yyyy-MM-dd",
            null, "Invalid Date. Please enter a valid date in the "
                    + "format yyyy-MM-dd");
    return new BuyStock(this.model, this.view, mutablePortfolioName, stockTicker, quantity, date);
  }


  private ICommand createPeriodStockPerformanceCommand() {
    LocalDate startDate = inputHandler.getInputDate("Enter the start date "
                    + "in the format yyyy-MM-dd", null,
            "Invalid Date. Please enter a valid date in the format yyyy-MM-dd");
    LocalDate endDate = inputHandler.getInputDate("Enter the end date in the format"
            + " yyyy-MM-dd", (LocalDate d) -> !d.isAfter(startDate), "Invalid Date. "
            + "Please enter a valid date in the format yyyy-MM-dd");
    String ticker = inputHandler.getInputString("Enter the ticker symbol of the stock",
        (String s) -> s != null && !s.trim().isEmpty(), "Ticker "
                    + "symbol cannot be empty!");
    return new PeriodStockPerformance(this.model, this.view, ticker, startDate, endDate);
  }


  private ICommand createXDayMovingAverageCommand() {
    String ticker = inputHandler.getInputString("Enter the ticker symbol of the stock",
        (String s) -> s != null && !s.isEmpty() && this.isTickerValid(s),
            "Ticker "
                    + "symbol cannot be empty!");
    int days = inputHandler.getInputInteger("Enter the the no of days for moving average",
        (Integer s) -> s != null && s > 0, "Invalid input. Please enter a valid "
                    + "positive Integer.");
    LocalDate date = inputHandler.getInputDate("Enter the date in the format yyyy-MM-dd",
        (LocalDate d) -> (new ArrayList<>(this.model.getStockPrices(ChronoUnit.DAYS, ticker,
                    d, days, true, 1).values()).size() != days),
            "Invalid Date. The dates entered should be valid and the"
                    + " data should be available for the given days.");
    return new XDayMovingAverage(this.model, this.view, ticker, days, date);

  }


  private ICommand createMovingAverageCrossOverCommand() {
    String ticker = inputHandler.getInputString("Enter the ticker symbol of the stock",
        (String s) -> s != null && !s.trim().isEmpty() && this.isTickerValid(s),
            "Invalid Ticker symbol");
    int xDays = inputHandler.getInputInteger("Enter the number of days for "
                    + "shorter moving average",
        (Integer s) -> s != null && s > 0, "Invalid input. "
                    + "Please enter a valid positive Integer.");
    int yDays = inputHandler.getInputInteger("Enter the number of days "
                    + "for longer moving average",
        (Integer s) -> s != null && s > 0 && s > xDays, "Invalid input. "
                    + "Please enter "
                    + "a valid positive Integer greater than number of days for shorter "
                    + "moving average");
    LocalDate startDate = inputHandler.getInputDate("Enter the start Date in the "
                    + "format yyyy-MM-dd",
        (LocalDate d) -> (new ArrayList<>(this.model.getStockPrices(ChronoUnit.DAYS, ticker,
                    d, yDays, true, 1).values()).size() != yDays),
            "Invalid Date. Please enter a valid date in the format yyyy-MM-dd");
    LocalDate endDate = inputHandler.getInputDate(
            "Enter the end date in the format yyyy-MM-dd",
        (LocalDate d) -> !d.isAfter(startDate), "Invalid Date. "
                    + "Please enter a valid date (format yyyy-MM-dd) end date must be greater "
                    + "than start date.");
    return new MovingAverageCrossOver(this.model, this.view,
            new XDayMovingAverage(this.model, this.view, ticker, 30, startDate),
            ticker, startDate, endDate, xDays, yDays);
  }


  private ICommand createAnalyzeStockPerformanceCommand() {
    String ticker = inputHandler.getInputString("Enter the ticker symbol of the stock",
        (String s) -> s != null && !s.isEmpty(), "Ticker symbol cannot be empty!");
    LocalDate startDate = inputHandler.getInputDate("Enter the start date in the format "
                    + "yyyy-MM-dd",
            null, "Invalid Date. Please enter a valid date in the format "
                    + "yyyy-MM-dd");
    LocalDate endDate = inputHandler.getInputDate("Enter the end date in the "
                   + "format yyyy-MM-dd. "
                    + "End date should be at least 2 market trading days"
                    + " and no more than 30 years after the start date.",
            null, "Invalid Date. Please enter a valid date in the "
                    + "format yyyy-MM-dd");
    return new AnalyzeStockPerformance(this.model, this.view, ticker, startDate, endDate);
  }


  private ICommand createAnalyzePortfolioPerformanceCommand() {
    String portfolioName = inputHandler.getInputString("Enter the name of the portfolio",
        (String s) -> s != null && !s.isEmpty() && this.model.isPortfolioPresent(s),
            "Portfolio name cannot be empty!");
    LocalDate startDate = inputHandler.getInputDate("Enter the start date in the "
                    + "format yyyy-MM-dd",
            null, "Invalid Date. Please enter a valid date in the format"
                    + " yyyy-MM-dd");
    LocalDate endDate = inputHandler.getInputDate("Enter the end date in the format "
                    + "yyyy-MM-dd. "
                    + "End date should be at least 2 market trading days"
                    + " and no more than 30 years after the start date.",
            null, "Invalid Date. Please enter a valid date in the "
                    + "format yyyy-MM-dd");
    return new AnalyzePortfolioPerformance(this.model, this.view, portfolioName,
            startDate, endDate);
  }


  private ICommand createWeightedAddStockCommand() {
    LocalDate date = inputHandler.getInputDate("Enter the date in the format yyyy-MM-dd",
            null, "Invalid Date. Please enter a valid date in the " +
                    "format yyyy-MM-dd");
    String portfolioName = inputHandler.getInputString("Enter the name of the portfolio",
        (String s) -> s != null && !s.isEmpty() && this.model.isPortfolioPresent(s),
            "Portfolio name cannot be empty!");
    Map<String, Integer> weights = new HashMap<>();
    double total = inputHandler.getInputDouble("Enter the total amount you want to invest",
        (Double s) -> s > 0, "Invalid input. Please enter a valid " +
                    "positive Double.");
    int count = inputHandler.getInputInteger("Enter the number of stocks you want to add",
        (Integer s) -> s > 0, "Invalid input. Please enter a valid " +
                    "positive Integer.");
    int temp = count;
    while (count > 0) {
      view.displayMessage("Enter the details of the stock " + (temp - count + 1) + " you want " +
              "to add");
      String ticker = inputHandler.getInputString("Enter the ticker symbol of the stock",
          (String s) -> s != null && !s.trim().isEmpty() && this.isTickerValid(s),
              "Ticker symbol "
                      + "cannot be empty!");
      int weight = inputHandler.getInputInteger("Enter the weight of the stock " +
                      "in the portfolio",
          (Integer s) -> s > 0, "Invalid input. Please enter a valid " +
                      "positive Integer.");
      weights.put(ticker, weight);
      count = count - 1;
    }
    return new WeightedAddStock(this.model, this.view, date, portfolioName, weights, total);
  }

  private ICommand createDollarCostAveragingCommand() {
    Map<String, ChronoUnit> unitMap = new HashMap<>();
    unitMap.put("days", ChronoUnit.DAYS);
    unitMap.put("weeks", ChronoUnit.WEEKS);
    unitMap.put("months", ChronoUnit.MONTHS);
    unitMap.put("years", ChronoUnit.YEARS);
    String portfolioName = inputHandler.getInputString("Enter the name of the portfolio",
        (String s) -> s != null && !s.isEmpty() && !(this.model.isPortfolioPresent(s)),
            "Portfolio name cannot be empty!");
    LocalDate startDate = inputHandler.getInputDate("Enter the start date in the format " +
                    "yyyy-MM-dd",
            null, "Invalid Date. Please enter a valid date in the format " +
                    "yyyy-MM-dd");
    String specifyEndDate = inputHandler.getInputString("Do you want to specify an " +
                    "end date? (Y/N)",
        input -> input.equalsIgnoreCase("Y") || input.equalsIgnoreCase(
                    "N"),
            "Invalid input. Please enter Y for Yes or N for No.");
    LocalDate endDate = null;
    if (specifyEndDate.equalsIgnoreCase("Y")) {
      endDate = inputHandler.getInputDate("Enter the end date (YYYY-MM-DD):",
        (LocalDate d) -> !d.isAfter(startDate), "Invalid date. Please enter a " +
                      "valid date in the format YYYY-MM-DD.");
    }
    double totalAmount = inputHandler.getInputDouble("Enter the total amount for each " +
                    "transaction:",
        (Double s) -> s > 0, "Invalid input. Please enter a valid " +
                    "positive number.");
    String unit = inputHandler.getInputString("Enter one of the following units:  " +
                    "DAYS, WEEKS " +
                    "MONTHS, YEARS",
        (String s) -> s != null && !s.trim().isEmpty() &&
                    (s.equalsIgnoreCase("days") ||
                            s.equalsIgnoreCase("weeks") ||
                            s.equalsIgnoreCase("months") ||
                            s.equalsIgnoreCase("years")),
            "Invalid input. Please enter a valid unit.");
    ChronoUnit chronoUnit = unitMap.get(unit.toLowerCase());
    int frequency = inputHandler.getInputInteger("Enter the frequency of " +
                    "transactions in " + unit + ":",
        (Integer s) -> s > 0, "Invalid input. Please enter a " +
                    "valid positive Integer.");
    Map<String, Double> investmentAmounts;
    boolean validWeghts;
    do {
      investmentAmounts = new HashMap<>();
      int count = inputHandler.getInputInteger("Enter the number of stocks you want to add",
          (Integer s) -> s > 0, "Invalid input. Please enter a valid " +
                      "positive Integer.");
      int temp = count;
      while (count > 0) {
        view.displayMessage("Enter the details of the stock " + (temp - count + 1) + " you " +
                "want to add");
        String ticker = inputHandler.getInputString("Enter the ticker symbol of the stock",
            (String s) -> s != null && !s.trim().isEmpty() && this.isTickerValid(s),
                "Invalid Ticket symbol"
                        + "Please enter a valid ticker symbol.(Non-empty)!");
        Double weight = inputHandler.getInputDouble("Enter the weight of the stock " +
                        "in the " +
                        "portfolio",
            (Double s) -> s > 0, "Invalid input. Please enter a "
                                     + "valid positive Integer.");
        investmentAmounts.merge(ticker, weight, Double::sum);
        count = count - 1;
      }
      double totalWeights = investmentAmounts.values().stream()
                              .mapToDouble(Double::doubleValue).sum();
      validWeghts = Math.abs(totalWeights - 100.00) <= 0.0001;
      if (!validWeghts) {
        view.displayMessage("Total weights do not add up to 100. Please enter the weights again.");
      }
    }
    while (!validWeghts);
    Map<String, Integer> integerMap = new HashMap<>();
    investmentAmounts.forEach((ticker, value) -> integerMap.put(ticker,
            Math.toIntExact(Math.round(value))));
    return new DollarCostAveraging(model, view, portfolioName,
            new LocalDate[]{startDate, endDate}, integerMap,
            totalAmount, frequency, chronoUnit);
  }

  /**
   * Executes the main menu controller.
   */
  @Override
  public void execute() {
    this.loop();
  }
}


