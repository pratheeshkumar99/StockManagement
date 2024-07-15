package controller;

import controller.commands.BuyStock;
import controller.commands.CreateMutablePortfolio;
import controller.commands.DayStockPerformance;
import controller.commands.DollarCostAveraging;
import controller.commands.GetCostBasis;
import controller.commands.GetCrossOverDays;
import controller.commands.GetPortfolioValue;
import controller.commands.ICommand;
import controller.commands.LoadPortfolio;
import controller.commands.MovingAverageCrossOver;
import controller.commands.PeriodStockPerformance;
import controller.commands.SavePortfolio;
import controller.commands.SellStock;
import controller.commands.WeightedAddStock;
import controller.commands.XDayMovingAverage;
import controller.filereader.FileReaderFactory;
import controller.filewriter.IFileWriterFactory;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import model.OngoingTransactionModel;
import view.text.IGraphingView;

/**
 * The FeaturesImpl class implements the IFeatures interface and provides the implementation for.
 * the methods in the interface.
 */

public class FeaturesImpl implements IFeatures {
  private final OngoingTransactionModel model;
  private IGraphingView view;
  private IFileWriterFactory fileWriter;

  /**
   * Constructs a FeaturesImpl object.
   *
   * @param model         the model.
   * @param view          the view.
   * @param factoryWriter the file writer factory.
   */
  public FeaturesImpl(OngoingTransactionModel model, IGraphingView view, IFileWriterFactory
          factoryWriter) {
    this.model = model;
    this.view = view;
    this.fileWriter = factoryWriter;
  }

  /**
   * Sets the view for the controller.
   *
   * @param view the view.
   */

  @Override
  public void setView(IGraphingView view) {
    this.view = view;
  }

  /**
   * Buys a stock.
   *
   * @param ticker        the stock Ticker.
   * @param strQuantity   the quantity of the given stock. Must be a non-0 integer.
   * @param strDate       the date of the transaction.
   * @param portfolioName the name of the portfolio the transaction occurs in.
   */

  @Override
  public void buyStock(String ticker, String strQuantity, String strDate, String portfolioName) {
    double quantity;
    LocalDate date;
    try {
      quantity = Double.parseDouble(strQuantity);
    } catch (NumberFormatException e) {
      view.displayErrorMessage(new IllegalArgumentException("Please Enter a Valid positive" +
              " quantity."));
      return;
    }
    if (quantity <= 0) {
      view.displayErrorMessage(new IllegalArgumentException("Quantity must be greater than 0."));
      return;
    }
    try {
      date = LocalDate.parse(strDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    } catch (Exception e) {
      view.displayErrorMessage(new IllegalArgumentException("Invalid date format. Please enter " +
              "a valid date in the format yyyy-MM-dd"));
      return;
    }
    try {
      ICommand c = new BuyStock(model, view, portfolioName, ticker, quantity, date);
      c.execute();
    } catch (Exception e) {
      view.displayErrorMessage(e);
    }
  }

  /**
   * Sells a stock.
   *
   * @param ticker        the stock Ticker.
   * @param strQuantity   the quantity of the given stock. Must be a non-0 integer.
   * @param strDate       the date of the transaction.
   * @param portfolioName the name of the portfolio the transaction occurs in.
   */

  @Override
  public void sellStock(String ticker, String strQuantity, String strDate, String portfolioName) {
    double quantity;
    LocalDate date;
    try {
      quantity = Double.parseDouble(strQuantity);
    } catch (NumberFormatException e) {
      view.displayErrorMessage(new IllegalArgumentException("Please Enter a Valid positive" +
              " quantity."));
      return;
    }
    if (quantity <= 0) {
      view.displayErrorMessage(new IllegalArgumentException("Quantity must be greater than 0"));
      return;
    }
    try {
      date = LocalDate.parse(strDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    } catch (Exception e) {
      view.displayErrorMessage(new IllegalArgumentException("Invalid date format. Please enter " +
              "a valid date in the format yyyy-MM-dd"));
      return;
    }
    try {
      ICommand c = new SellStock(model, view, portfolioName, ticker, quantity, date);
      c.execute();
    } catch (Exception e) {
      view.displayErrorMessage(e);
    }
  }

  /**
   * Adds a portfolio.
   *
   * @param portfolioName the portfolio name.
   * @return true if the portfolio was added successfully, false otherwise.
   * @throws IllegalArgumentException if the portfolio name is invalid.
   */
  @Override
  public boolean addPortfolio(String portfolioName) throws IllegalArgumentException {
    ICommand c = new CreateMutablePortfolio(model, view, portfolioName);
    try {
      c.execute();
      return true;
    } catch (IllegalArgumentException e) {
      view.displayErrorMessage(e);
      return false;
    }
  }

  /**
   * Crosses over days.
   *
   * @param ticker the ticker symbol.
   * @param start  the start date.
   * @param end    the end date.
   */
  @Override
  public void crossoverDays(String ticker, LocalDate start, LocalDate end) {
    ICommand c = new GetCrossOverDays(model, view, ticker, start, end,
            new XDayMovingAverage(model, view, ticker, 30, start));
    c.execute();
  }

  /**
   * Loads a portfolio.
   *
   * @param file the file to be added.
   * @return The true if the portfolio is present.
   */


  @Override
  public boolean loadPortfolio(File file) {
    int type = 1;
    ICommand c = new LoadPortfolio(model, view, new FileReaderFactory(), type, file.getPath());
    c.execute();
    int pos = file.getName().lastIndexOf(".");
    String name = file.getName().substring(0, pos);
    return model.isPortfolioPresent(name);
  }

  /**
   * Calculates the moving average crossover days.
   *
   * @param ticker    the stock's ticker.
   * @param xDay      the smaller bound of moving averages to calculate with.
   * @param yDay      the larger bound of moving averages to calculate with.
   * @param startDate the starting date.
   * @param endDate   the ending date.
   */

  @Override
  public void movingCrossOverDays(String ticker, int xDay, int yDay, LocalDate startDate,
                                  LocalDate endDate) {
    ICommand c = new MovingAverageCrossOver(model, view, new XDayMovingAverage(model,
            view, ticker, 30, startDate), ticker, startDate, endDate, xDay, yDay);
    c.execute();
  }

  /**
   * Saves the portfolio at a given path if valid.
   *
   * @param portfolioName the portfolio's name.
   * @param path          the path to save the portfolio to.
   */
  @Override
  public void savePortfolio(String portfolioName, String path) {
    try {
      System.out.println(portfolioName + " " + path);
      ICommand command = new SavePortfolio(this.model, this.view, this.fileWriter,
              portfolioName, path);
      command.execute();
    } catch (Exception e) {
      view.displayErrorMessage(e);
    }
  }

  /**
   * Calculates the x-day moving average.
   *
   * @param ticker     the stock's ticker symbol.
   * @param xDayLength the length of the moving average, in number of days.
   * @param date       the date to calculate from.
   */
  @Override
  public void xDayMovingAverage(String ticker, int xDayLength, LocalDate date) {
    ICommand c = new XDayMovingAverage(model, view, ticker, xDayLength, date);
    c.execute();
  }

  /**
   * Calculates the cost basis of a portfolio.
   *
   * @param portfolioName the name of the portfolio.
   * @param date          a past date.
   */
  @Override
  public void costBasis(String portfolioName, String date) {
    LocalDate validDate;
    try {
      try {
        validDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
      } catch (Exception e) {
        view.displayErrorMessage(new IllegalArgumentException("Invalid date format. Please enter " +
                "a valid date in the format yyyy-MM-dd"));
        return;
      }
      ICommand c = new GetCostBasis(model, view, portfolioName, validDate);
      c.execute();
    } catch (IllegalArgumentException e) {
      view.displayErrorMessage(e);
    }
  }

  /**
   * Calculates the value of a portfolio.
   *
   * @param portfolioName the name of the portfolio.
   * @param date          a past date.
   */

  @Override
  public void portfolioValue(String portfolioName, String date) {
    LocalDate validDate;
    try {
      try {
        validDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
      } catch (Exception e) {
        view.displayErrorMessage(new IllegalArgumentException("Invalid date format. Please enter " +
                "a valid date in the format yyyy-MM-dd"));
        return;
      }
      ICommand c = new GetPortfolioValue(model, view, portfolioName, validDate);
      c.execute();
    } catch (IllegalArgumentException e) {
      view.displayErrorMessage(e);
    }
  }

  /**
   * Calculates the net performance of a stock.
   *
   * @param ticker the stock ticker symbol.
   * @param start  the start date.
   * @param end    the end date.
   */
  @Override
  public void stockPerformanceNet(String ticker, LocalDate start, LocalDate end) {
    ICommand c;
    if (start.isEqual(end)) {
      c = new DayStockPerformance(model, view, ticker, end);
    } else {
      c = new PeriodStockPerformance(model, view, ticker, start, end);
    }
    c.execute();
  }

  /**
   * Invests a fixed amount into a portfolio.
   *
   * @param portfolioName the portfolio name.
   * @param contribution  the total contribution amount.
   * @param strDate   the date the contribution was made on.
   * @param split  The percent of the contribution amount each stock gets by its ticker.
   */

  @Override
  public void investFixedAmount(String portfolioName, double contribution, String strDate,
                                Map<String, Integer> split) {
    LocalDate date;
    try {
      date = LocalDate.parse(strDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    } catch (Exception e) {
      view.displayErrorMessage(new IllegalArgumentException("Invalid date format. Please enter " +
              "a valid date in the format yyyy-MM-dd"));
      return;
    }
    try {
      ICommand command = new WeightedAddStock(this.model, this.view, date, portfolioName, split,
              contribution);
      command.execute();
      view.displayMessage("Investment Successful");
    } catch (Exception e) {
      view.displayErrorMessage(e);
    }
  }

  /**
   * Invests a fixed amount into a portfolio.
   *
   * @param portfolioName      the name of the portfolio.
   * @param dates              an Array of Dates, containing 1 or 2 values.
   * @param contributionAmount the repeating contribution amount.
   * @param split              how much each stock should be split by. The total must be 100.
   * @param unit               the unit of measurement between contribution events.
   * @param length             the length of time between each contribution event.
   */

  @Override
  public boolean dollarCostAverage(String portfolioName, LocalDate[] dates,
                                   double contributionAmount,
                                   Map<String, Integer> split, ChronoUnit unit, int length) {
    ICommand c = new DollarCostAveraging(
            model, view, portfolioName, dates, split, contributionAmount, length, unit);
    c.execute();
    return model.isPortfolioPresent(portfolioName);
  }

  /**
   * Lists the portfolios.
   *
   * @return a list of portfolio names.
   */

  @Override
  public String[] listPortfolios() {
    return model.listPortfolioNames();
  }
}
