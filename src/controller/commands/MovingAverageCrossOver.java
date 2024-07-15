package controller.commands;

import java.time.LocalDate;
import java.util.SortedMap;
import java.util.TreeMap;

import model.IHistorianPortfolioManager;
import view.text.IGraphingView;


/**
 * This class represents the command to get the MovingCrossover days of a stock.
 */

public class MovingAverageCrossOver extends AbstractCommand {
  private final XDayMovingAverage command;
  private final String ticker;
  private final LocalDate startDate;
  private final LocalDate endDate;
  private final int xDays;
  private final int yDays;


  /**
   * Constructs a MovingAverageCrossOver object with the given model, view and input handler.
   *
   * @param model     the model object to be used.
   * @param view      the view object to be used.
   * @param command   the command object to be used.
   * @param ticker    the ticker symbol to be used.
   * @param startDate the startDate to be used.
   * @param endDate   the endDate ot be used.
   * @param xDays     the number of days for shorter moving average.
   * @param yDays     the number of days for longer moving average.
   */
  public MovingAverageCrossOver(IHistorianPortfolioManager model, IGraphingView view,
                                XDayMovingAverage command, String ticker, LocalDate startDate,
                                LocalDate endDate, int xDays, int yDays) {
    super(model, view);
    this.command = command;
    this.ticker = ticker;
    this.startDate = startDate;
    this.endDate = endDate;
    this.xDays = xDays;
    this.yDays = yDays;
  }


  private void analyseMovingAverageCrossOver(String ticker, LocalDate startDate, LocalDate
          endDate, int xDays, int yDays) {

    SortedMap<LocalDate, Boolean> crossOverData = new TreeMap<>();
    Double previousShorterMovingAverage = null;
    Double previousLongerMovingAverage = null;
    for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
      double currentShorterMovingAverage = this.command.getMovingAverage(ticker, date, xDays);
      double currentLongerMovingAverage = this.command.getMovingAverage(ticker, date, yDays);
      if (previousShorterMovingAverage != null && previousLongerMovingAverage != null) {
        if (previousShorterMovingAverage < previousLongerMovingAverage
                && currentShorterMovingAverage > currentLongerMovingAverage) {
          crossOverData.put(date, true);
        } else if (previousShorterMovingAverage > previousLongerMovingAverage
                && currentShorterMovingAverage < currentLongerMovingAverage) {
          crossOverData.put(date, false);
        }
      }
      previousLongerMovingAverage = currentLongerMovingAverage;
      previousShorterMovingAverage = currentShorterMovingAverage;
    }

    view.showBuySellDates(crossOverData, "Moving crossover signals.",
            "Dates are calculated using " + xDays + " and " + yDays + " " +
                    "moving averages.");
  }


  private void getMovingAverageCrossOvers() {
    this.analyseMovingAverageCrossOver(this.ticker, this.startDate, this.endDate, this.xDays,
            this.yDays);
  }

  /**
   * Executes the command to get the MovingCrossover days of a stock.
   */
  public void execute() {
    this.getMovingAverageCrossOvers();
  }


}


