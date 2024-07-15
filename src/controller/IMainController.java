package controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import model.OngoingTransactionModel;
import view.text.IGraphingView;

/**
 * Provides the functionality for the main controller of the program, having a necessary.
 * exception for.
 * any invalid portfolio data.
 */

public interface IMainController {

  /**
   * Executes the command.
   */

  void execute();

  /**
   * Returns the function that retrieves online data.
   *
   * @return the function that retrieves online data.
   */

  Function<String, Map<LocalDate, List<Double>>> getOnlineDataFunc();

  /**
   * Sets the model for the controller.
   *
   * @param model the model to be set.
   */

  void setModel(OngoingTransactionModel model);

  /**
   * Sets the view for the controller.
   *
   * @param view the view to be set.
   */

  void setView(IGraphingView view);

  /**
   * Returns the model for the controller.
   *
   * @return the model for the controller.
   */

  IFeatures getFeatures();
}
