package view.text;

import java.io.IOException;
import java.io.OutputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import model.IStock;

/**
 * Simple text based View for an InvestmentSimulationView. This is typically to be used in a command
 * console but can work with any output stream. Formats messages and outputs from the controller.
 */
public class TextView implements IView {

  private final OutputStream out;

  /**
   * Public constructor for a Text view.
   *
   * @param out the OutputStream the view should send information to.
   */
  public TextView(OutputStream out) {
    this.out = out;
  }

  @Override
  public void displayListOfPortfolios(String[] portfolioNames) {
    if (portfolioNames.length == 0) {
      tryOutput("There are no loaded portfolios");
      return;
    }
    StringBuilder str = new StringBuilder();
    str.append("Portfolios managed:").append(System.lineSeparator());
    for (int i = 0; i < portfolioNames.length; i++) {
      str.append("- ").append(portfolioNames[i]);
      if (i < portfolioNames.length - 1) {
        str.append(System.lineSeparator());
      }
    }

    tryOutput(str.toString());
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

    for (IStock s : stockSet) {
      DecimalFormat valueFormatter = new DecimalFormat("#,###.000");
      valueFormatter.setRoundingMode(RoundingMode.HALF_UP);
      str.append(String.format("Ticker: %s, Number of Shares: %s",
          s.getName(), valueFormatter.format(s.getQuantity())));
      str.append(System.lineSeparator());
    }
    int lastSeparator = str.lastIndexOf(System.lineSeparator());
    if (lastSeparator > 0) {
      str.delete(lastSeparator, str.length());
    }
    tryOutput(str.toString());
  }

  @Override
  public void displayValueOfAsset(String asset, double value, LocalDate date) {
    DecimalFormat valueFormatter = new DecimalFormat("$#,##0.00");
    valueFormatter.setRoundingMode(RoundingMode.HALF_UP);

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
    String text = date.format(dateFormatter);

    String output = String.format("Value of %s on %s is: %s",
        asset, text, valueFormatter.format(value));
    tryOutput(output);
  }

  @Override
  public void displayErrorMessage(Exception e) {
    tryOutput("Input Error: " + e.getMessage());
  }

  @Override
  public void displayMessage(String message) {
    tryOutput(message);
  }

  /**
   * Protected method to output some String to the outputStream.
   * @param s some string to return.
   */
  protected void tryOutput(String s) {
    try {
      String output = s + System.lineSeparator();
      out.write(output.getBytes());
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }
}
