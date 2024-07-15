package controller.commands;

import model.IHistorianPortfolioManager;
import view.text.IGraphingView;

/**
 * AbstractCommand is a base class the various command classes, acting as an implementation of.
 * ICommand and providing the necessary methods.
 */

public class AbstractCommand implements ICommand {


  protected final IHistorianPortfolioManager model;
  protected final IGraphingView view;

  /**
   * Constructs an AbstractCommand object with the given model, view, and inputHandler.
   *
   * @param model the model to be used.
   * @param view  the view to be used.
   */

  public AbstractCommand(
          IHistorianPortfolioManager model, IGraphingView view) {
    this.model = model;
    this.view = view;
  }

  /**
   * Executes the command for the given command object.
   */

  @Override
  public void execute() {
     //This method is overridden by the concrete command classes.

  }
}
