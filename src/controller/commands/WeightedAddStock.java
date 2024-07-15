package controller.commands;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import model.IHistorianPortfolioManager;
import view.text.IGraphingView;

/**
 * Command to add multiple stock (even fraction shares) of a set listing of Stocks,
 * in a single transaction, where all stocks share a date,
 * and their number of shares is determined by weight.
 */


public class WeightedAddStock extends AbstractCommand {
  private final LocalDate date;
  private final String portfolioName;
  private final Map<String, Integer> weights;
  private final double total;

  /**
   * Constructs an AbstractCommand object with the given model, view, and inputHandler.
   *
   * @param model the model to be used.
   * @param view  the view to be used.
   */
  public WeightedAddStock(IHistorianPortfolioManager model, IGraphingView view, LocalDate date,
                          String portfolioName, Map<String, Integer> weights, double total) {
    super(model, view);
    this.date = date;
    this.portfolioName = portfolioName;
    this.weights = weights;
    this.total = total;
  }

  /**
   * Adds the weighted stock to the portfolio.
   */

  private void addWeightedStock() {
    if (weights.values().stream().mapToInt(Integer::intValue).sum() != 100) {
      throw new IllegalArgumentException("Weights do not add up to 100");
    }
    Map<String, Double> numShares = new HashMap<>();
    for (String ticker : weights.keySet()) {

      try {
        double quantity = total * ((double) weights.get(ticker) / 100L) /
                model.getStockPrice(ticker, date, true);
        model.addStock(ticker, quantity, date, portfolioName);
      } catch (IOException e) {
        view.displayErrorMessage(e);
      }

    }
  }

  /**
   * Executes the command to add the weighted stock to the portfolio.
   */
  @Override
  public void execute() {
    this.addWeightedStock();
  }
}
