package controller.commands;

import java.time.LocalDate;

import model.IHistorianPortfolioManager;
import view.text.IGraphingView;

/**
 * This class represents a command to get the cost basis of a portfolio.
 */

public class GetCostBasis extends AbstractCommand {

  private final String portfolioName;
  private final LocalDate date;

  /**
   * Constructs a GetCostBasis object.
   *
   * @param model         the model.
   * @param view          the view.
   * @param portfolioName the name of the portfolio.
   * @param date          the date.
   */
  public GetCostBasis(IHistorianPortfolioManager model, IGraphingView view,
                      String portfolioName, LocalDate date) {
    super(model, view);
    this.portfolioName = portfolioName;
    this.date = date;

  }


  /**
   * Gets the cost basic of a mutable portfolio.
   */

  private void getCostBasic() throws IllegalArgumentException {
    double value = this.model.getCostBasis(this.portfolioName, this.date);
    view.displayValueOfAsset(portfolioName, value, date);
  }

  /**
   * Executes the command for getting the cost basis of a portfolio.
   */
  @Override
  public void execute() {
    this.getCostBasic();
  }


}
