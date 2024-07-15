package controller.commands;

import java.time.LocalDate;

import controller.inputhandler.IInputHandler;
import model.IHistorianPortfolioManager;
import view.text.IGraphingView;

/**
 * CreatePortfolio is a command class that creates a Immutable portfolio, given the portfolio name.
 * and the stocks that the user wants to buy.
 */

public class CreateImmutablePortfolio extends AbstractCommand {

  private final IInputHandler inputHandler;

  /**
   * Constructs a CreatePortfolio object with the given model, view and input handler.
   *
   * @param model        the HistorianPortfolioManager object to be used.
   * @param view         the GraphingView object to be used.
   * @param inputHandler the InputHandler object to be used.
   */

  public CreateImmutablePortfolio(IHistorianPortfolioManager model, IGraphingView view,
                                  IInputHandler inputHandler) {
    super(model, view);
    this.inputHandler = inputHandler;

  }


  private String askPortfolioName() {
    return inputHandler.getInputString("Enter the name of the portfolio",
        (String s) -> s != null && !s.trim().isEmpty() && !(this.model.isPortfolioPresent(s)),
            "Invalid portfolio name! Please make sure to enter a unique and "
                    + "non-empty portfolio name.");
  }

  private int askNoOfSharesToBuy() {
    return inputHandler.getInputInteger("Enter the number of stocks you want to buy",
        (Integer s) -> s > 0, "Invalid input. Please enter a valid "
                    + "positive Integer.");
  }

  private String askStockTicker() {
    return inputHandler.getInputString("Enter the ticker symbol of the stock",
        (String s) -> s != null && !s.trim().isEmpty(), "Ticker symbol "
                    + "cannot be empty!");
  }

  private Integer askStockQuantity() {
    return inputHandler.getInputInteger("Enter the quantity of stocks you want to buy",
        (Integer s) -> s > 0, "Invalid input. Please enter a"
                    + " valid positive Integer.");
  }


  /**
   * Creates an immutable portfolio by making a call to the model methods.
   */

  private void createImmutablePortfolio() {
    String portfolioName = this.askPortfolioName();
    this.model.createPortfolio(portfolioName);
    int noOfShares = this.askNoOfSharesToBuy();
    int temp = noOfShares;
    while (noOfShares > 0) {
      view.displayMessage("Please enter details for the Stock " + ((temp - noOfShares) + 1));
      String stockTicker = this.askStockTicker();
      int stockQuantity = this.askStockQuantity();
      LocalDate date = LocalDate.now().minusDays(1);
      try {
        this.model.addStock(stockTicker, stockQuantity, date, portfolioName);
        noOfShares = noOfShares - 1;
      } catch (Exception e) {
        view.displayErrorMessage(e);
      }
    }
    this.model.flipMutability(portfolioName);
  }

  private void createPortfolio() {
    this.createImmutablePortfolio();
  }

  /**
   * Executes the command for creating a Immutable portfolio.
   */


  @Override
  public void execute() {
    this.createPortfolio();
  }
}


