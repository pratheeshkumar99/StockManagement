package view.text;

import java.time.LocalDate;
import java.util.Set;
import model.IStock;

/**
 * The view for an Investment Simulation program. A view is controlled by the controller in a
 * standard MVC paradigm. Final rendering and formatting choices are determined by the view.
 */
public interface IView {

  /**
   * Display a list of portfolio names.
   *
   * @param portfolioNames the names of the portfolios.
   */
  void displayListOfPortfolios(String[] portfolioNames);


  /**
   * Display the contents of a portfolio.
   *
   * @param stockSet A set of stocks that the portfolio contains.
   * @param name     The name of the portfolio being displayed.
   */
  void displayPortfolioComposition(Set<IStock> stockSet, String name);

  /**
   * Display the value of the portfolio on a specified date.
   *
   * @param asset the name of the portfolio or stock. If a stock, the name should indicate as such.
   * @param value the value of the asset.
   * @param date  the date of the value of the asset.
   */
  void displayValueOfAsset(String asset, double value, LocalDate date);

  /**
   * Display a given error message from the controller.
   *
   * @param e an exception to be displayed to the user.
   */
  void displayErrorMessage(Exception e);

  /**
   * Display a given message from the controller.
   *
   * @param message a message for the view to display as is.
   */
  void displayMessage(String message);


}