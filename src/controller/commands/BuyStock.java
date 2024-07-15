package controller.commands;

import java.io.IOException;
import java.time.LocalDate;
import model.IHistorianPortfolioManager;
import view.text.IGraphingView;

/**
 * BuStock is a command class that buys the stocks, given the stock ticker, quantity, date and.
 * mutable portfolio name.
 */

public class BuyStock extends AbstractCommand {

  private String portfolioName;
  private String stockTicker;
  private double quantity;
  private LocalDate date;

  /**
   * Constructs a BuyStock object with the given model, view and input handler.
   *
   * @param model the HistorianPortfolioManager object to be used.
   * @param view  the GraphingView object to be used.
   */

  public BuyStock(IHistorianPortfolioManager model,
                  IGraphingView view, String portfolioName,
                  String stockTicker, double quantity, LocalDate date) {
    super(model, view);
    this.portfolioName = portfolioName;
    this.stockTicker = stockTicker;
    this.quantity = quantity;
    this.date = date;

  }

  /**
   * Buys the stocks given the stock ticker, quantity, date and mutable portfolio name.
   */

  private void buyStocks() {
    try {
      this.model.addStock(this.stockTicker, this.quantity, this.date, this.portfolioName);
      view.displayMessage("Stock bought successfully!");
    } catch (IllegalArgumentException | IOException e) {
      view.displayErrorMessage(e);
    }
  }


  /**
   * Executes the command for buying the stocks.
   */

  @Override
  public void execute() {
    this.buyStocks();
  }
}
