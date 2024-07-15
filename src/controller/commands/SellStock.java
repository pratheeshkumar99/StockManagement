package controller.commands;

import java.io.IOException;
import java.time.LocalDate;

import model.IHistorianPortfolioManager;
import view.text.IGraphingView;

/**
 * The sell stock class is a command class that sells a stock from a portfolio.
 */


public class SellStock extends AbstractCommand {
  private final String portfolioName;
  private final String stockTicker;
  private final double quantity;
  private final LocalDate date;

  /**
   * Constructs a SellStock object with the given model, view and input handler.
   *
   * @param model         The IHistorianPortfolioManager object to be used.
   * @param view          The view object to be used.
   * @param portfolioName The name of the portfolio.
   * @param stockTicker   The ticker symbol of the stock.
   * @param quantity      The quantity of the stock.
   * @param date          The date of the stock.
   */
  public SellStock(IHistorianPortfolioManager model,
                   IGraphingView view, String portfolioName,
                   String stockTicker, double quantity, LocalDate date) {
    super(model, view);
    this.portfolioName = portfolioName;
    this.stockTicker = stockTicker;
    this.quantity = quantity;
    this.date = date;
  }

  private void sellStocks() {
    try {
      this.model.addStock(this.stockTicker, -1 * this.quantity, this.date,
              this.portfolioName);
      view.displayMessage("Stock sold successfully!");
    } catch (IllegalArgumentException | IOException e) {
      view.displayErrorMessage(e);
    }
  }


  /**
   * Executes the Command to sell the stock.
   */

  @Override
  public void execute() {
    this.sellStocks();
  }
}
