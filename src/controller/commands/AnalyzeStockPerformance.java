package controller.commands;

import static utils.Utils.getAppropriateUnit;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map.Entry;
import java.util.SortedMap;
import model.IHistorianPortfolioManager;
import view.text.IGraphingView;


/**
 * This class represents the command to analyze the performance of a stock.
 */

public class AnalyzeStockPerformance extends AbstractCommand {
  private final String ticker;
  private final LocalDate startDate;
  private final LocalDate endDate;

  /**
   * Constructs a AnalyzeStockPerformance object with the given model, view and input handler.
   *
   * @param model     the model object to be used.
   * @param view      the view object to be used.
   * @param ticker    the ticker symbol to be used.
   * @param startDate the startDate to be used.
   * @param endDate   the endDate ot be used.
   */
  public AnalyzeStockPerformance(IHistorianPortfolioManager model, IGraphingView view,
                                 String ticker, LocalDate startDate, LocalDate endDate) {
    super(model, view);
    this.ticker = ticker;
    this.startDate = startDate;
    this.endDate = endDate;
  }


  private void getStockPerformance() {
    if (endDate.isBefore(startDate.plusDays(2))) {
      view.displayErrorMessage(new IllegalArgumentException(
              "End date must be at least 2 market trading days from the start date."));
      return;
    }
    Entry<ChronoUnit, Integer> unitWithLength;
    ChronoUnit unit;
    int length;
    try {
      unitWithLength = getAppropriateUnit(startDate, endDate, 5, 30);
    } catch (IllegalStateException e) {
      //Open up the range only if it fails the first time.
      unitWithLength = getAppropriateUnit(startDate, endDate, 2, 30);
    }
    unit = unitWithLength.getKey();
    length = unitWithLength.getValue();
    int size = Math.toIntExact(unit.between(startDate, endDate));
    SortedMap<LocalDate, Double> values;
    try {
      values = model.getStockPrices(unit, ticker, endDate, size, true, length);
      view.plotPerformance(values, "Stock: " + ticker);
    } catch (IllegalArgumentException e) {
      view.displayErrorMessage(e);
    }
  }

  /**
   * Executes the command to analyze the stock performance.
   */
  @Override
  public void execute() {
    this.getStockPerformance();
  }
}
