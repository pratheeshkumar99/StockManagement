package view.text;

import static utils.Utils.formatDollarValue;

import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import model.IStock;

/**
 * Representation of a view that supports additional statistics and performance reports.
 */
public class GraphingTextView extends TextView implements IGraphingView {

  /**
   * Public constructor for a Text view.
   *
   * @param out the OutputStream the view should send information to.
   */
  public GraphingTextView(OutputStream out) {
    super(out);
  }


  @Override
  public void displayPortfolioComposition(Set<IStock> stockSet, String name) {
    StringBuilder str = new StringBuilder();
    str.append(String.format("%s composition:%s", name, System.lineSeparator()));
    if (stockSet.isEmpty()) {
      str.append("This portfolio does not contain any stocks");
      tryOutput(str.toString());
      return;
    }
    Function<IStock, String> lineFormatter = (s -> {
      String boughtOrSold = (s.getQuantity() > 0) ? "bought" : "sold";
      return String.format("%s: %.2f shares %s of %s%s",
        s.getDate().format(DateTimeFormatter.ofPattern("yyyy MMM dd")),
        Math.abs(s.getQuantity()), boughtOrSold, s.getName(), System.lineSeparator());
    });
    Comparator<IStock> c = Comparator.comparing(IStock::getDate).thenComparing(IStock::getName);
    str.append(stockSet.stream()
        .sorted(c)
        .map(lineFormatter)
        .collect(Collectors.joining()));

    tryOutput(str.toString());
  }

  @Override
  public void plotPerformance(SortedMap<LocalDate, Double> values, String name)
      throws IllegalStateException {

    if (values.size() < 2) {
      throw new IllegalStateException("Tried to view performance of zero or one data points.");
    } else if ((values.values().stream().min(Double::compareTo).orElse(1.0) < 0)) {
      throw new IllegalStateException("Values cannot be negative.");
    }

    StringBuilder str = new StringBuilder();
    String firstDate = values.firstKey().format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
    String lastDate = values.lastKey().format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
    str.append(String.format("Performance of %s from %s to %s", name, firstDate, lastDate));
    str.append(System.lineSeparator());

    long graphScale = calculateScaleMin(values.values());

    Function<Double, Integer> applyScale = (d -> Math.toIntExact(Math.round(d / graphScale)));
    Function<LocalDate, String> formatDate = (d -> d.format(
        DateTimeFormatter.ofPattern("yyyy MMM dd")));
    Function<Integer, String> formatValue = ("*"::repeat);
    Function<Entry<LocalDate, Double>, String> formatEntry = (e ->
        String.format("%s : %s%s",
        formatDate.apply(e.getKey()), formatValue.apply(applyScale.apply(e.getValue())),
        System.lineSeparator()));

    str.append(values.entrySet().stream().map(formatEntry).collect(Collectors.joining()));
    str.append("Scale: * = ").append(formatDollarValue(graphScale));
    tryOutput(str.toString());
  }

  @Override
  public void showBuySellDates(SortedMap<LocalDate, Boolean> dates, String title,
      String description) {
    StringBuilder str = new StringBuilder();
    str.append("Report: ").append(title).append(System.lineSeparator());
    String decision;
    if (dates.isEmpty()) {
      str.append("There are no buy sell dates in this report").append(System.lineSeparator());
      str.append(description);
      tryOutput(str.toString());
      return;
    }

    for (Entry<LocalDate, Boolean> pair : dates.entrySet()) {
      decision = (pair.getValue() ? "BUY" : "SELL");
      str.append(pair.getKey().format(DateTimeFormatter.ofPattern("dd MMM yyyy")))
        .append(" : ")
        .append(decision)
        .append(System.lineSeparator());
    }
    str.append(description);
    tryOutput(str.toString());
  }

  @Override
  public void showNet(LocalDate start, LocalDate end, String asset, double startValue,
      double endValue) {
    if (start.isAfter(end)) {
      throw new IllegalArgumentException(String.format(
        "Start date (%s) provided cannot be after the end date (%s)!", start, end));
    }
    String changeStatement;
    if (Math.abs(startValue - endValue) < 0.01) {
      changeStatement = "did not change in value";
    } else if (startValue > endValue) {
      changeStatement = String.format("decreased by %s", formatDollarValue(startValue - endValue));
    } else {
      changeStatement = String.format("increased by %s", formatDollarValue(endValue - startValue));
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLL yyyy");
    String dateRange = (start.isEqual(end))
        ? String.format("on %s", start.format(formatter))
        : String.format("between %s and %s", start.format(formatter), end.format(formatter));
    String result = String.format("%s %s %s", asset, changeStatement, dateRange);
    tryOutput(result);
  }

  @Override
  public void showCostBasis(LocalDate date, String asset, double value) {
    String output = String.format("CostBasis of %s on %s is: %s",
        asset, date.format(DateTimeFormatter.ofPattern("dd MMM yyyy")), formatDollarValue(value));
    tryOutput(output);
  }

  @Override
  public void displayMainMenu() {
    String output = "Welcome to the Investment Portfolio Manager!"
        + System.lineSeparator()
        + "Please select an option from the following menu:"
        + System.lineSeparator()
        + String.format("%1$s1.Create Mutable Portfolio%1$s"
            + "2.Create Immutable Portfolio%1$s"
        + "3.Get Portfolio Value%1$s"
        + "4.Get Portfolio Composition%1$s"
        + "5.Get Cost Basis%1$s"
        + "6.Load Portfolio%1$s"
        + "7.Save Portfolio%1$s"
        + "8.Buy Stocks%1$s"
        + "9.Sell Stocks%1$s"
        + "10.Get Daily Stock Performance%1$s"
        + "11.Get Period Stock Performance%1$s"
        + "12.Get X-Day Moving Average%1$s"
        + "13.Get Cross Over Days%1$s"
        + "14.Get Moving Average Cross Over%1$s"
        + "15.Analyze Stock Performance%1$s"
        + "16.Analyze Portfolio Performance%1$s"
           + "17. Invest with Weighted Strategy%1$s"
            + "18. Create Dollar Cost Averaging Portfolios%1$s"
        + "q.Quit", System.lineSeparator());
    tryOutput(output);
  }

  /**
   * Helper method to determine the correct scaling for plotting.
   *
   * @param values the values to be plotted.
   * @return a whole number integer that each value can be scaled to in less than 50 increments.
   */
  private static int calculateScaleMin(Collection<Double> values) {
    double max = values.stream().max(Double::compareTo).orElseThrow();
    //scale must be at least 10^(CEIL(Log10(max)))
    return Math.toIntExact(Math.round(Math.pow(10, Math.ceil(Math.log10(max / 50)))));
  }

}
