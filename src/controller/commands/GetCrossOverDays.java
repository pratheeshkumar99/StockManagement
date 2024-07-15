package controller.commands;

import java.time.LocalDate;
import java.util.SortedMap;
import java.util.TreeMap;

import model.IHistorianPortfolioManager;
import view.text.IGraphingView;

/**
 * This class represents the command to get the crossover days of a stock.
 */

public class GetCrossOverDays extends AbstractCommand {

  private final XDayMovingAverage command;
  private final String ticker;
  private final LocalDate startDate;
  private final LocalDate endDate;

  /**
   * Constructs a GetCrossOverDays object.
   *
   * @param model     the model.
   * @param view      the view.
   * @param ticker    the ticker symbol.
   * @param startDate the start date.
   * @param endDate   the  end Date.
   * @param command   XDayMovingAverage command.
   */

  public GetCrossOverDays(IHistorianPortfolioManager model, IGraphingView view,
                          String ticker, LocalDate startDate, LocalDate endDate,
                          XDayMovingAverage command) {
    super(model, view);
    this.command = command;
    this.ticker = ticker;
    this.startDate = startDate;
    this.endDate = endDate;
  }


  private double getMovingAverage(String ticker, LocalDate date, int days) {
    try {
      return command.getMovingAverage(ticker, date, days);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid Date!");
    }
  }


  private void analyseCrossOvers(String ticker, LocalDate startDate, LocalDate endDate) {

    SortedMap<LocalDate, Boolean> crossOverData = new TreeMap<>();
    Double previousClosingPrice = null;
    Double previousMovingAverage = null;
    for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
      try {
        double currentClosingPrice = this.model.getStockPrice(ticker, date, true);
        double currentMovingAverage = this.getMovingAverage(ticker, date, 30);
        if (previousClosingPrice != null && previousMovingAverage != null) {

          boolean isPositiveCrossOver = previousClosingPrice < previousMovingAverage
                  && currentClosingPrice > currentMovingAverage;
          boolean isNegativeCrossOver = previousClosingPrice > previousMovingAverage
                  && currentClosingPrice < currentMovingAverage;

          if (isPositiveCrossOver || isNegativeCrossOver) {
            crossOverData.put(date, isPositiveCrossOver);
          }
        }
        previousMovingAverage = currentMovingAverage;
        previousClosingPrice = currentClosingPrice;
      } catch (Exception ignored) {

      }
    }
    view.showBuySellDates(crossOverData, "Crossover signals", "Dates are"
            + "calculated using 30 day moving average.");
  }



  private void getCrossOver() {
    this.analyseCrossOvers(this.ticker, this.startDate, this.endDate);

  }

  /**
   * Executes the command  for getting the crossover days.
   */

  @Override
  public void execute() {
    this.getCrossOver();
  }
}
