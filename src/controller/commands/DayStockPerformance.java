package controller.commands;

import java.time.LocalDate;

import model.IHistorianPortfolioManager;
import view.text.IGraphingView;

/**
 * DayStockPerformance is a class that Analyzes the performance of a stock in a day.
 */

public class DayStockPerformance extends AbstractCommand {

  private final String ticker;
  private final LocalDate date;

  /**
   * Constructs a DayStockPerformance object.
   *
   * @param model  the model.
   * @param view   the view.
   * @param ticker the ticker symbol.
   * @param date   the date.
   */

  public DayStockPerformance(IHistorianPortfolioManager model, IGraphingView view,
                             String ticker, LocalDate date) {
    super(model, view);
    this.ticker = ticker;
    this.date = date;

  }

  /**
   * Analyzes the performance of a stock in a day.
   */

  private void analyseDayStockPerformance() {
    try {

      double openingPrice = this.model.getStockPrice(ticker, date, false);
      double closingPrice = this.model.getStockPrice(ticker, date, true);

      view.showNet(date, date, ticker, openingPrice, closingPrice);

    } catch (IllegalArgumentException e) {
      this.view.displayErrorMessage(e);
    }

  }

  /**
   * Executes the command for analyzing the performance of a stock in a day.
   */

  @Override
  public void execute() {
    this.analyseDayStockPerformance();
  }
}
