package controllertest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;

import model.IStock;
import view.text.IGraphingView;

/**
 * A mock view class that logs all the messages that are passed to it.
 */

public class MockView implements IGraphingView {

  private List<String> log = new ArrayList<>();

  /**
   * Gets the Log of the messages.
   *
   * @return the log of the messages.
   */

  public List<String> getLog() {
    return log;
  }


  @Override
  public void plotPerformance(SortedMap<LocalDate, Double> values, String name)
          throws IllegalArgumentException {
    log.add("values: " + values.toString());
    log.add("Name: " + name);
  }

  @Override
  public void showBuySellDates(SortedMap<LocalDate, Boolean> dates, String title,
                               String description) {
    log.add("dates and values:" + dates.toString());
    log.add("title: " + title);
    log.add("description:" + description);
  }

  @Override
  public void showNet(LocalDate start, LocalDate end, String asset, double startValue,
                      double endValue) throws IllegalArgumentException {
    log.add("Start: " + start + " End: " + end + " Asset: " + asset + " Start Value: " +
            startValue + " End Value: " + endValue);
  }

  @Override
  public void showCostBasis(LocalDate date, String asset, double value) {
    log.add("date: " + date);
    log.add("asset: " + asset);
    log.add("value: " + value);
  }

  @Override
  public void displayListOfPortfolios(String[] portfolioNames) {
    log.add("Names: " + Arrays.toString(portfolioNames));
  }

  @Override
  public void displayPortfolioComposition(Set<IStock> stockSet, String name) {
    log.add("Portfolio: " + name);
    log.add(stockSet.toString());
  }

  @Override
  public void displayValueOfAsset(String asset, double value, LocalDate date) {
    log.add("Asset: " + asset + " Value: " + value + " Date: " + date);
  }

  @Override
  public void displayErrorMessage(Exception e) {
    log.add(e.getMessage());
  }

  @Override
  public void displayMessage(String message) {
    log.add(message);
  }

  @Override
  public void displayMainMenu() {
    log.add("Main Menu");
  }
}
