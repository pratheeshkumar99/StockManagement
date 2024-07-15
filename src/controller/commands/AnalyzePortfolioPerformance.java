package controller.commands;

import utils.Utils;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map.Entry;
import java.util.SortedMap;
import model.IHistorianPortfolioManager;
import view.text.IGraphingView;

/**
 * This class represents the command to analyze the performance of a portfolio.
 */

public class AnalyzePortfolioPerformance extends AbstractCommand {

  private final String portfolioName;
  private final LocalDate startDate;
  private final LocalDate endDate;

  /**
   * Constructs the command to analyze the performance of a portfolio.
   *
   * @param model         the historian portfolio manager.
   * @param view          the graphing view.
   * @param portfolioName the name of the portfolio.
   * @param startDate     the start date.
   * @param endDate       the end date.
   */

  public AnalyzePortfolioPerformance(IHistorianPortfolioManager model, IGraphingView view,
                                     String portfolioName, LocalDate startDate, LocalDate endDate) {
    super(model, view);
    this.portfolioName = portfolioName;
    this.startDate = startDate;
    this.endDate = endDate;
  }


  /**
   * Gets the stock performance of a portfolio.
   */

  private void getStockPerformance() {
    if (endDate.isBefore(startDate.plusDays(2))
            || endDate.isAfter(startDate.plusYears(30))) {
      view.displayErrorMessage(new IllegalArgumentException(
              "End date must be at least 2 market trading days "
                      + "and less than 30 years from the start date."));
      return;
    }
    Entry<ChronoUnit, Integer> unitWithLength;
    ChronoUnit unit;
    int length = 1;
    try {
      unitWithLength = Utils.getAppropriateUnit(startDate, endDate, 5, 30);
    } catch (IllegalStateException e) {
      unitWithLength = Utils.getAppropriateUnit(startDate, endDate, 2, 30);
    }
    unit = unitWithLength.getKey();
    length = unitWithLength.getValue();

    int size = Math.toIntExact(unit.between(startDate, endDate));
    SortedMap<LocalDate, Double> values;
    try {
      values = model.getPortfolioValues(unit, portfolioName, endDate, size, length);
      view.plotPerformance(values, portfolioName);
    } catch (IllegalArgumentException e) {
      view.displayErrorMessage(e);
    }
  }

  /**
   * Executes the command to analyze the performance of a portfolio.
   */
  @Override
  public void execute() {
    this.getStockPerformance();
  }
}
