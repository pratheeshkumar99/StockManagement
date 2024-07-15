package view.gui;

import controller.IFeatures;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import model.IStock;
import view.gui.hometab.HomeTab;
import view.gui.uielements.PopUpMessage;
import view.text.GraphingTextView;
import view.text.IGraphingView;

/**
 * JFrame (Java Swing) based view of an investment simulation.
 * Is an object adapter of the GraphingTextView to facilitate code reuse.
 */
public class JFrameView extends JFrame implements  IGraphingView {
  private final JTabbedPane tabbedPane;
  private final IFeatures features;
  private final IGraphingView textView;
  private final ByteArrayOutputStream out;

  /**
   * Public constructor for the JFrame based GUI.
   * @param features the features which the UI elements can call upon for events.
   */
  public JFrameView(IFeatures features) {
    super();
    this.out = new ByteArrayOutputStream();
    this.textView = new GraphingTextView(out);
    setTitle("Investment Simulation");
    setSize(760, 750);
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    tabbedPane = new JTabbedPane();
    tabbedPane.addTab("Home", new HomeTab(features, this));
    mainPanel.add(tabbedPane);
    add(mainPanel);
    this.features = features;
  }


  @Override
  public void plotPerformance(SortedMap<LocalDate, Double> values, String name)
      throws IllegalArgumentException {
    //not supported for now. do nothing.
  }

  @Override
  public void showBuySellDates(SortedMap<LocalDate, Boolean> dates, String title,
      String description) {
    try {
      textView.showBuySellDates(dates, title, description);
      PopUpMessage.displayInfoMessage(out.toString(), null);
    } catch (IllegalArgumentException iae) {
      PopUpMessage.displayErrorMessage(iae.getMessage(), null);
    }
    out.reset();
  }

  @Override
  public void showNet(LocalDate start, LocalDate end, String asset, double startValue,
      double endValue) throws IllegalArgumentException {
    //display the net in the active tab
    try {
      textView.showNet(start, end, asset, startValue, endValue);
      PopUpMessage.displayInfoMessage(out.toString(), null);
    } catch (IllegalArgumentException iae) {
      PopUpMessage.displayErrorMessage(iae.getMessage(), null);
    }
    out.reset();
  }

  @Override
  public void showCostBasis(LocalDate date, String asset, double value) {
    //display in the active tab. asset must be a portfolioName.
  }

  @Override
  public void displayMainMenu() {
    //do nothing for now.
  }

  @Override
  public void displayListOfPortfolios(String[] portfolioNames) {
    //do nothing.
  }

  @Override
  public void displayPortfolioComposition(Set<IStock> stockSet, String name) {
    //do nothing.
  }

  @Override
  public void displayValueOfAsset(String asset, double value, LocalDate date) {
    try {
      textView.displayValueOfAsset(asset, value, date);
      PopUpMessage.displayInfoMessage(out.toString(), null);
    } catch (IllegalArgumentException iae) {
      PopUpMessage.displayErrorMessage(iae.getMessage(), null);
    }
    out.reset();
  }

  @Override
  public void displayErrorMessage(Exception e) {
    PopUpMessage.displayErrorMessage(e.getMessage(), null);
  }

  private void addTab(String title) {
    //check that this was appropriately done in the model first
    if (alreadyInModel(title)) {
      JPanel newTab = new PortfolioTab(features, title, this);
      tabbedPane.addTab(title, newTab);
      tabbedPane.setSelectedComponent(newTab);
    }
    else {
      String msg = "Tried to call addTab in JFrameView when a portfolio wasn't created";
      PopUpMessage.displayErrorMessage(msg, null);
    }
  }

  @Override
  public void displayMessage(String message) {
    PopUpMessage.displayInfoMessage(message, null);
  }

  /**
   * Package Private method for subclasses to load a portfolio.
   * @param f the file to be loaded
   */
  public void loadPortfolio(File f) {
    int pos = f.getName().lastIndexOf(".");
    String name = f.getName().substring(0, pos);
    if (alreadyInModel(name)) {
      PopUpMessage.displayErrorMessage("Portfolio name is already used", null);
    } else if (features.loadPortfolio(f)) {
      addTab(name);
    }
  }

  /**
   * Method to be used by the Home Tab to add a new portfolio.
   * @param name the name of the new tab
   */
  public void addNewPortfolio(String name) {
    if (alreadyInModel(name)) {
      PopUpMessage.displayErrorMessage("Portfolio name is already used", null);
    } else if (features.addPortfolio(name)) {
      addTab(name);
    }
  }

  /**
   * Method to be used by the home tab to create a portfolio with Dollar Cost Averaging.
   * @param name the portfolio name.
   * @param dates the portfolio dates.
   * @param total the total contribution amount.
   * @param split the splits of contributions.
   * @param unit the unit of time.
   * @param length the length of time between contributions.
   */
  public void addNewPortfolioWithDCA(String name, LocalDate[] dates, double total,
      Map<String, Integer> split, ChronoUnit unit, int length) {
    if (alreadyInModel(name)) {
      PopUpMessage.displayErrorMessage("Portfolio name is already used", null);
    } else if (features.dollarCostAverage(name, dates, total, split, unit, length)) {
      addTab(name);
    }
  }

  private boolean alreadyInModel(String name) {
    return Arrays.asList(features.listPortfolios()).contains(name);
  }
}
