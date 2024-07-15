package view.gui.hometab;

import controller.IFeatures;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.Document;
import view.gui.JFrameView;
import view.gui.uielements.AddStockByWeight;
import view.gui.uielements.DateTextField;
import view.gui.uielements.InputValidationTextField;
import view.gui.uielements.PopUpMessage;
import view.gui.uielements.ProcessingBox;

/**
 * The Home Tab that launching the GUI will show be default.
 * Handles operations for Stock Statistics and Creating Portfolios.
 */
public class HomeTab extends JPanel {
  private final static CardLayout layout = new CardLayout();

  private final JPanel controlPanel = new JPanel(layout);
  private final JFrameView view;
  private JCheckBox hasEndDateCBox;
  private DateTextField startDateDCA;
  private DateTextField endDateDCA;
  private AddStockByWeight addStockByWeight;

  /**
   * Public constructor for a HomeTab.
   * @param features the features that it calls upon.
   * @param view the view that it can use to add tabs when creating a portfolio.
   */
  public HomeTab(IFeatures features, JFrameView view) {
    this.view = view;
    setLayout(new BorderLayout());
    add(controlPanel, BorderLayout.CENTER);
    StockStatsPanel statsPanel = new StockStatsPanel(features);
    JPanel portfolioPanel = portfolioCreationPanel();
    JPanel mainPanel = new JPanel();
    mainPanel.add(statsPanel);
    mainPanel.add(portfolioPanel);
    controlPanel.add(mainPanel, "Main Panel");
    controlPanel.add(pCreateMutablePortfolio(), "Create Mutable");
    controlPanel.add(pCreateDCA(), "Create DCA");

  }

  private JPanel portfolioCreationPanel() {
    JPanel p = new JPanel();
    JButton createMutable = new JButton("Create Flexible Portfolio");
    createMutable.addActionListener(a -> layout.show(controlPanel, "Create Mutable"));
    JButton createDCA = new JButton("Create Portfolio with Dollar Cost Averaging");
    createDCA.addActionListener(a -> layout.show(controlPanel, "Create DCA"));
    JButton loadPortfolio = new JButton("Load Portfolio");
    loadPortfolio.addActionListener(a -> loadFile());
    p.add(createMutable);
    p.add(createDCA);
    p.add(loadPortfolio);
    return p;
  }

  private void loadFile() {
    JFileChooser chooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "portfolio configs (.csv)", "csv");
    chooser.setFileFilter(filter);
    int retValue = chooser.showOpenDialog(this);
    if (retValue == JFileChooser.APPROVE_OPTION) {
      File f = chooser.getSelectedFile();
      ProcessingBox ld = new ProcessingBox(view);
      SwingWorker<Void, Void> worker = new SwingWorker<>() {
        @Override
        protected Void doInBackground() {
          view.loadPortfolio(f);
          return null;
        }

        @Override
        protected void done() {
          ld.dispose();
          layout.show(controlPanel, "Main Panel");
        }
      };
      worker.execute();
      ld.setVisible(true);
    }
  }

  private JPanel pCreateMutablePortfolio() {
    JPanel askNamePanel = new JPanel();
    InputValidationTextField portfolioName = new InputValidationTextField(
        "Please enter the portfolio name", 20);
    askNamePanel.add(portfolioName);
    askNamePanel.add(backButton());
    JButton submitButton = new JButton("Create");
    askNamePanel.add(submitButton);
    submitButton.addActionListener(a -> {
      view.addNewPortfolio(portfolioName.getText());
      layout.show(controlPanel, "Main Panel");
    });
    submitButton.setEnabled(false);
    Document d = portfolioName.getTextField().getDocument();
    portfolioName.addListener(() -> submitButton.setEnabled(d.getLength() != 0));
    return askNamePanel;
  }

  private JPanel pCreateDCA() {
    JPanel p = new JPanel();
    p.setLayout(new FlowLayout(FlowLayout.CENTER));

    JPanel namePanel = new JPanel();
    InputValidationTextField portfolioName = new InputValidationTextField(
        "Please enter the portfolio name", 20);
    namePanel.add(portfolioName);
    p.add(namePanel);

    JPanel repetitionsPanel = new JPanel();
    repetitionsPanel.setLayout(new BoxLayout(repetitionsPanel, BoxLayout.X_AXIS));
    repetitionsPanel.add(new JLabel("Repeat every"));
    ChronoUnit[] frequencyUnit = new ChronoUnit[] {
      ChronoUnit.DAYS, ChronoUnit.WEEKS, ChronoUnit.MONTHS, ChronoUnit.YEARS};
    SpinnerListModel unitModel = new SpinnerListModel(frequencyUnit);
    JSpinner unitSpinner = new JSpinner(unitModel);
    SpinnerNumberModel lengthModel = new SpinnerNumberModel(1, 1, 100, 1);
    JSpinner lengthSpinner = new JSpinner(lengthModel);
    repetitionsPanel.add(lengthSpinner);
    repetitionsPanel.add(unitSpinner);
    p.add(repetitionsPanel);

    JPanel datePanel = new JPanel();
    datePanel.setLayout(new GridLayout(3, 1));
    startDateDCA = new DateTextField("Start Date");
    hasEndDateCBox = new JCheckBox("Has an end date?", false);
    endDateDCA = new DateTextField("End Date");
    datePanel.add(startDateDCA);
    datePanel.add(hasEndDateCBox);
    datePanel.add(endDateDCA);
    endDateDCA.setVisible(false);
    hasEndDateCBox.addActionListener(e -> endDateDCA.setVisible(hasEndDateCBox.isSelected()));
    p.add(datePanel);


    JButton createButton = new JButton("Create Portfolio");


    Runnable updateButtonLock = (() -> {
      boolean hasName = !portfolioName.getText().isEmpty();
      boolean hasDates = startDateDCA.getText().length() == 10
                           && (!hasEndDateCBox.isSelected() || endDateDCA.getText().length() == 10);
      boolean hasTotal;
      boolean hasSplit;
      try {
        addStockByWeight.getContributionAmount();
        hasTotal = true;
        hasSplit = !addStockByWeight.getResults().isEmpty()
                     && addStockByWeight.getResults().values().stream()
                          .mapToInt(Integer::intValue).sum() == 100;
      } catch (IllegalArgumentException ex) {
        hasTotal = false;
        hasSplit = false;
      }
      createButton.setEnabled(hasName && hasDates && hasTotal && hasSplit);
    });
    portfolioName.addListener(updateButtonLock);
    startDateDCA.addListener(updateButtonLock);
    endDateDCA.addListener(updateButtonLock);

    createButton.addActionListener(a -> {
      String name = portfolioName.getText();
      ChronoUnit unit = (ChronoUnit) unitSpinner.getValue();
      int length = (int) lengthSpinner.getValue();
      try {
        LocalDate[] dates = (hasEndDateCBox.isSelected())
                              ? new LocalDate[]{startDateDCA.getDate(), endDateDCA.getDate() }
                              : new LocalDate[]{startDateDCA.getDate()};
        double total = addStockByWeight.getContributionAmount();
        Map<String, Integer> split = addStockByWeight.getResults();
        handleDCACreation(name, dates, total, split, unit, length);
      } catch (IllegalArgumentException e) {
        PopUpMessage.displayErrorMessage(e.getMessage(), this);
      }
    });

    addStockByWeight = new AddStockByWeight(updateButtonLock);
    p.add(addStockByWeight);

    p.add(backButton());
    p.add(createButton);

    return p;
  }

  private JButton backButton() {
    JButton backButton = new JButton("Back");
    backButton.addActionListener(a -> layout.show(controlPanel, "Main Panel"));
    return backButton;
  }

  private void handleDCACreation(String name, LocalDate[] dates, double total,
      Map<String, Integer> split, ChronoUnit unit, int length) {
    ProcessingBox ld = new ProcessingBox(view);
    SwingWorker<Void, Void> worker = new SwingWorker<>() {
      @Override
      protected Void doInBackground() {
        view.addNewPortfolioWithDCA(name, dates, total, split, unit, length);
        return null;
      }

      @Override
      protected void done() {
        ld.dispose();
        layout.show(controlPanel, "Main Panel");
      }
    };
    worker.execute();
    ld.setVisible(true);
  }

}
