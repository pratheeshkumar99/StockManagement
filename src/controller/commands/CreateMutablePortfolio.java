package controller.commands;


import model.IHistorianPortfolioManager;
import view.text.IGraphingView;

/**
 * A command object that creates a Mutable portfolio.
 */

public class CreateMutablePortfolio extends AbstractCommand {

  private String portfolioName;

  /**
   * Constructs a CreatePortfolio object with the given model, view and input handler.
   *
   * @param model the HistorianPortfolioManager object to be used.
   * @param view  the GraphingView object to be used.
   */

  public CreateMutablePortfolio(IHistorianPortfolioManager model, IGraphingView view, String name) {
    super(model, view);
    this.portfolioName = name;
  }


  private void createMutablePortfolio() throws IllegalArgumentException {
    try {
      this.model.createPortfolio(this.portfolioName);
      this.view.displayMessage("Portfolio created successfully");
    } catch (IllegalArgumentException e) {
      this.view.displayErrorMessage(e);
    }
  }

  /**
   * Executes the command to create a mutable portfolio.
   */

  @Override
  public void execute() {
    this.createMutablePortfolio();
  }
}
