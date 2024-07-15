package controller.commands;

import java.util.Set;

import model.IHistorianPortfolioManager;
import model.IStock;
import view.text.IGraphingView;

/**
 * This class represents a command to get the composition of a portfolio.
 */

public class GetPortfolioComposition extends AbstractCommand {

  private final String portfolioName;

  /**
   * Constructs a GetPortfolioComposition object with the given model, view and portfolio name.
   *
   * @param model         the HistorianPortfolioManager object to be used.
   * @param view          the GraphingView object to be used.
   * @param portfolioName the name of the portfolio.
   */
  public GetPortfolioComposition(IHistorianPortfolioManager model,
                                 IGraphingView view, String portfolioName) {
    super(model, view);
    this.portfolioName = portfolioName;
  }


  private void getPortfolioComponents() {
    try {
      Set<IStock> stockSet = this.model.getPortfolioComposition(this.portfolioName);
      view.displayPortfolioComposition(stockSet, portfolioName);
    } catch (Exception e) {
      view.displayErrorMessage(e);
    }
  }


  /**
   * Executes the command to get the composition of a portfolio.
   */

  public void execute() {
    this.getPortfolioComponents();
  }

}
