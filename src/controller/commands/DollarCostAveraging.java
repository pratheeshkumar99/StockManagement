package controller.commands;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import model.OngoingTransactionModel;
import view.text.IGraphingView;

/**
 * Command to execute a dollar cost averaging strategy.
 */

public class DollarCostAveraging implements ICommand {
  private final String portfolioName;
  private final LocalDate[] dates;
  private final Map<String, Double> investmentAmounts;
  private final int reps;
  private final int length;
  private final ChronoUnit unit;
  private final IGraphingView view;
  private final OngoingTransactionModel model;

  /**
   * Constructor for the DollarCostAveraging command.
   *
   * @param model             the model to execute the command on.
   * @param view              the view to execute the command on.
   * @param portfolioName     the name of the portfolio to execute the command on.
   * @param dates             the dates to execute the command on.
   * @param investmentAmounts the investment amounts to execute the command on.
   * @param totalAmount       the total amount to execute the command on.
   * @param length            the length to execute the command on.
   * @param unit              the unit to execute the command on.
   * @throws IllegalArgumentException if the ticker symbol is invalid or the data  req.
   *                                  for the ticker and its corresponding date  is invalid.
   */
  public DollarCostAveraging(OngoingTransactionModel model, IGraphingView view,
                             String portfolioName, LocalDate[] dates,
                             Map<String, Integer> investmentAmounts, double totalAmount,
                             int length, ChronoUnit unit) throws IllegalArgumentException {

    if (investmentAmounts.isEmpty()) {
      throw new IllegalArgumentException("Provided null investmentAccounts");
    } else if (dates.length > 2 || dates.length < 1) {
      throw new IllegalArgumentException("Number of dates supplied is incorrect");
    }
    this.portfolioName = portfolioName;
    this.investmentAmounts = calculateInvestmentAmounts(investmentAmounts, totalAmount);

    this.reps = calculateReps(unit, dates, length);
    this.unit = unit;

    this.dates = dates;
    this.model = model;
    this.view = view;
    this.length = length;
  }

  /**
   * Executes the command for the dollar cost averaging strategy.
   */
  @Override
  public void execute() {
    try {
      model.addDollarCostAveragingPortfolio(portfolioName, investmentAmounts
              , dates[0], unit, length, reps);

      view.displayMessage("Dollar Cost Averaging strategy for '" + portfolioName
              + "' initiated successfully.");
    } catch (IllegalArgumentException e) {
      view.displayErrorMessage(e);
    }
  }

  private int calculateReps(ChronoUnit unit, LocalDate[] dates, int length) {
    if (dates.length == 1) {
      return -1;
    } else {
      return Math.toIntExact(unit.between(dates[0], dates[1]) / length);
    }
  }

  private Map<String, Double> calculateInvestmentAmounts(Map<String, Integer> weights,
                                                         double totalAmount) {
    Map<String, Double> investmentAmounts = new HashMap<>();
    weights.forEach((ticker, percent) ->
            investmentAmounts.put(ticker, totalAmount * ((double) percent / 100.00)));
    return investmentAmounts;
  }
}
