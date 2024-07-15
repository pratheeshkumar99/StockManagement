package viewtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import model.IStock;

import model.Stock;
import org.junit.Before;
import org.junit.Test;

import view.text.IView;
import view.text.TextView;

/**
 * Test the methods of the TextView class.
 */
public class TextViewTest {

  protected OutputStream out;
  private IView v;
  private String expected;

  @Before
  public void setUp() {
    out = new ByteArrayOutputStream();
    v = createView(out);
  }

  /**
   * Create an IVew.
   * @param out the outputstream the view reads from.
   * @return The IView.
   */
  protected IView createView(OutputStream out) {
    return new TextView(out);
  }

  @Test
  public void testDisplayListOfPortfoliosEmpty() {
    expected = "There are no loaded portfolios" + System.lineSeparator();
    v.displayListOfPortfolios(new String[0]);
    assertEquals(expected, out.toString());
  }

  @Test
  public void testDisplayListOfPortfoliosNonEmpty() {
    String[] arr = new String[]{"Portfolio 1", "Test2"};
    expected = "Portfolios managed:" + System.lineSeparator()
            + "- Portfolio 1" + System.lineSeparator()
            + "- Test2";
    v.displayListOfPortfolios(arr);
    assertEquals(expected + System.lineSeparator(), out.toString());
  }


  @Test
  public void testDisplayPortfolioCompositionEmpty() {
    expected = "Portfolio 1 composition:" + System.lineSeparator()
      + "This portfolio does not contain any stocks";
    Set<IStock> emptySet = new HashSet<>();
    v.displayPortfolioComposition(emptySet, "Portfolio 1");
    assertEquals(expected + System.lineSeparator(), out.toString());
  }

  @Test
  public void testDisplayPortfolioCompositionNonEmpty() {
    String[] expected = new String[]{
      "Portfolio 1 composition:",
      "Ticker: NFLX, Number of Shares: 45.000",
      "Ticker: AMZN, Number of Shares: 56.000",
      "Ticker: GUTS, Number of Shares: 1.000"
    };
    Set<IStock> actualSet = new HashSet<>();
    actualSet.add(new Stock("NFLX", 45, LocalDate.now()));
    actualSet.add(new Stock("AMZN", 56, LocalDate.now()));
    actualSet.add(new Stock("GUTS", 1, LocalDate.now()));
    v.displayPortfolioComposition(actualSet, "Portfolio 1");
    for (String s : expected) {
      assertTrue(out.toString().contains(s));
    }
  }

  @Test
  public void testDisplayValueOfAsset() {
    LocalDate junefifth2008 = LocalDate.of(2008, 6, 5);
    double value = 1405234.23;
    String portfolioName = "Portfolio 1";
    v.displayValueOfAsset(portfolioName, value, junefifth2008);
    expected = "Value of Portfolio 1 on 05 June 2008 is: $1,405,234.23";
    assertEquals(expected + System.lineSeparator(), out.toString());
  }

  @Test
  public void testDisplayValueOfAssetRoundDown() {
    expected = "Value of Portfolio1 on 29 February 2024 is: $0.45" + System.lineSeparator();
    LocalDate d = LocalDate.of(2024, 2, 29);
    v.displayValueOfAsset("Portfolio1", .4544449f, d);
    assertEquals(expected, out.toString());
  }

  @Test
  public void testDisplayValueOfStockRoundUp() {
    expected = "Value of Portfolio1 on 29 February 2024 is: $0.45" + System.lineSeparator();
    LocalDate d = LocalDate.of(2024, 2, 29);
    v.displayValueOfAsset("Portfolio1", .449, d);
    assertEquals(expected, out.toString());
  }

  @Test
  public void testDisplayValueOfStockNegative() {
    expected = "Value of Portfolio1 on 29 February 2024 is: -$0.45" + System.lineSeparator();
    LocalDate d = LocalDate.of(2024, 2, 29);
    v.displayValueOfAsset("Portfolio1", -.449, d);
    assertEquals(expected, out.toString());
  }


  @Test
  public void testErrorMessage() {
    v.displayErrorMessage(new IllegalArgumentException("Test error"));
    String expected = "Input Error: Test error";
    assertEquals(expected + System.lineSeparator(), out.toString());
  }

  @Test
  public void testDisplayMessage() {
    v.displayMessage("test");
    assertEquals("test" + System.lineSeparator(), out.toString());
  }
}
