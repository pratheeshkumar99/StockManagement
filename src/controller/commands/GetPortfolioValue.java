package controller.commands;

import java.time.LocalDate;

import model.IHistorianPortfolioManager;
import view.text.IGraphingView;

/**
 * Gets the portfolio value based on the given portfolio and its stocks.
 */

public class GetPortfolioValue extends AbstractCommand {

  private final String portfolioName;
  private final LocalDate date;

  /**
   * Constructs the GetPortfolioValue object with the given model, view, portfolio name and date.
   *
   * @param model         the HistorianPortfolioManager object.
   * @param view          the IGraphingView object.
   * @param portfolioName the name of the portfolio.
   * @param date          the date.
   */

  public GetPortfolioValue(IHistorianPortfolioManager model, IGraphingView view,
                           String portfolioName, LocalDate date) {
    super(model, view);
    this.portfolioName = portfolioName;
    this.date = date;
  }

  /**
   * Gets the portfolio value based on the given portfolio and its stocks.
   */

  private void getPortfolioValue() {
    double value = this.model.getPortfolioValue(this.portfolioName, this.date);
    view.displayValueOfAsset(this.portfolioName, value, this.date);
  }


  /**
   * Executes the command to get the portfolio value.
   */

  @Override
  public void execute() {
    this.getPortfolioValue();
  }
}
