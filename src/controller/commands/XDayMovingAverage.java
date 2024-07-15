package controller.commands;

import static utils.Utils.formatDollarValue;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import model.IHistorianPortfolioManager;
import view.text.IGraphingView;

/**
 * This class represents the command to get the XDayMovingAverage of a stock.
 */


public class XDayMovingAverage extends AbstractCommand {

  private final String ticker;
  private final int days;
  private final LocalDate date;

  /**
   * Constructs a XDayMovingAverage object with the given model, view and input handler.
   *
   * @param model  the model object to be used.
   * @param view   the view object to be used.
   * @param ticker the ticker symbol to be used.
   * @param days   the number of days to be used.
   * @param date   the date to be used.
   */
  public XDayMovingAverage(IHistorianPortfolioManager model, IGraphingView view,
                           String ticker, int days, LocalDate date) {
    super(model, view);
    this.ticker = ticker;
    this.days = days;
    this.date = date;

  }

  double getMovingAverage(String ticker, LocalDate date, int days) {
    List<Double> movingAverage = new ArrayList<>(this.model.getStockPrices(ChronoUnit.DAYS,
            ticker, date, days, true, 1).values());
    return movingAverage.stream().mapToDouble(Double::doubleValue).average().orElse(Double.NaN);
  }

  private void analyseXDayMovingAverage() {
    try {
      double result = this.getMovingAverage(this.ticker, this.date, this.days);
      this.view.displayMessage("The moving average of the stock " + ticker + " on "
              + date + " is " + formatDollarValue(result));
    } catch (IllegalArgumentException e) {
      this.view.displayErrorMessage(e);
    }
  }

  /**
   * Executes the command for the XDayMovingAverage.
   */

  @Override
  public void execute() {
    this.analyseXDayMovingAverage();
  }

}
