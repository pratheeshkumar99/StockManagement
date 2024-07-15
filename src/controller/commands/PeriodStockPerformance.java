package controller.commands;

import java.time.LocalDate;

import model.IHistorianPortfolioManager;
import view.text.IGraphingView;

/**
 * PeriodStockPerformance is a class that Analyzes the performance of a stock in a period.
 */


public class PeriodStockPerformance extends AbstractCommand {

  private final String ticker;
  private final LocalDate startDate;
  private final LocalDate endDate;

  /**
   * Constructs a PeriodStockPerformance object with the given model, view and input handler.
   *
   * @param model     the model object to be used.
   * @param view      the view object to be used.
   * @param ticker    the ticker symbol to be used.
   * @param startDate the startDate to be used.
   * @param endDate   the endDate ot be used.
   */

  public PeriodStockPerformance(IHistorianPortfolioManager model, IGraphingView view,
                                String ticker, LocalDate startDate, LocalDate endDate) {
    super(model, view);
    this.ticker = ticker;
    this.startDate = startDate;
    this.endDate = endDate;
  }


  private void analysePeriodStockPerformance() {
    try {
      double openingPrice = this.model.getStockPrice(this.ticker, this.startDate, true);
      double closingPrice = this.model.getStockPrice(this.ticker, this.endDate, true);
      view.showNet(this.startDate, this.endDate, this.ticker, openingPrice, closingPrice);
    } catch (IllegalArgumentException e) {
      this.view.displayErrorMessage(e);
    }
  }

  /**
   * Executes the command to get the  PeriodStockPerformance.
   */

  public void execute() {
    this.analysePeriodStockPerformance();
  }


}
