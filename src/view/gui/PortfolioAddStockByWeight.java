package view.gui;

import controller.IFeatures;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import view.gui.uielements.DateTextField;
import view.gui.uielements.DoubleTextField;
import view.gui.uielements.InputValidationTextField;
import view.gui.uielements.InputValidationTextField.TextFieldType;
import view.gui.uielements.IntegerTextField;
import view.gui.uielements.ProcessingBox;


/**
 * Panel for adding stocks to a portfolio by weight.
 */

public class PortfolioAddStockByWeight extends JPanel {
  private final JButton addButton;
  private final JButton removeButton;
  private final JButton investButton;
  private final Map<String, Integer> results;
  private final JTextField tickerField;
  private final JTextField percentageField;
  private final JList<String> addedStocksList;
  private final DefaultListModel<String> listModel;
  private final JTextField contributionAmountField;
  private final JTextField dateField;
  private  final IFeatures features;
  private final String portfolioName;
  private final JPanel contentPanel;
  private final CardLayout layout;

  /**
   * Constructor for PortfolioAddStockByWeight.
   * @param panel The JPanel to add this panel to.
   * @param layout The CardLayout to switch between panels.
   * @param features The IFeatures object to call the controller methods.
   * @param portfolioName The name of the portfolio to add stocks to.
   */

  public PortfolioAddStockByWeight(JPanel panel, CardLayout layout,IFeatures features,
                                   String portfolioName) {
    super(new GridBagLayout());
    this.contentPanel = panel;
    this.layout = layout;
    this.features = features;
    this.portfolioName = portfolioName;
    results = new HashMap<>();
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1;
    gbc.insets = new Insets(2, 5, 2, 5);

    DoubleTextField totalContributionAmount = new DoubleTextField("Total Contribution Value");
    contributionAmountField = totalContributionAmount.getTextField();
    DateTextField dateTextField = new DateTextField("Date in format yyyy/mm/dd");
    dateField = dateTextField.getTextField();
    InputValidationTextField ticker = new InputValidationTextField("Stock Ticker Symbol",
        5 , TextFieldType.TICKER);
    tickerField = ticker.getTextField();
    ticker.addListener(this::updateButtonLocking);
    IntegerTextField percentage = new IntegerTextField("Percent Contribution");
    percentage.addListener(this::updateButtonLocking);
    percentageField = percentage.getTextField();

    addButton = new JButton("Add");
    removeButton = new JButton("Remove Selected");
    investButton = new JButton("Invest");
    investButton.setEnabled(false);

    listModel = new DefaultListModel<>();
    addedStocksList = new JList<>(listModel);
    JScrollPane listScrollPane = new JScrollPane(addedStocksList);
    listScrollPane.setPreferredSize(new Dimension(200, 120));

    addComponent(totalContributionAmount, gbc);
    addComponent(dateTextField, gbc);
    addComponent(ticker, gbc);
    addComponent(percentage, gbc);
    gbc.gridwidth = 1;
    gbc.fill = GridBagConstraints.NONE;
    gbc.weightx = 0.5;
    addComponent(addButton, gbc);
    gbc.gridx = 1;
    addComponent(removeButton, gbc);
    gbc.gridx = 2;
    addComponent(investButton, gbc);

    gbc.gridx = 0;
    gbc.gridwidth = 3;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.weighty = 1;
    add(listScrollPane, gbc);

    setupListener();
    updateButtonLocking();
  }

  private void setupListener() {
    addButton.addActionListener(a -> {
      addEntry();
      clearStockEntry();
      updateButtonLocking();
    });
    removeButton.addActionListener(a -> {
      removeEntry();
      updateButtonLocking();
    });
    addedStocksList.addListSelectionListener(a -> updateButtonLocking());
    investButton.addActionListener(a -> investStocks());
  }

  private void addComponent(Component component, GridBagConstraints gbc) {
    add(component, gbc);
    gbc.gridx = 0;
    gbc.weightx = 1;
    gbc.gridwidth = GridBagConstraints.REMAINDER;
  }

  private void removeEntry() {
    String toRemove = addedStocksList.getSelectedValue().split(",")[0];
    results.remove(toRemove);
    listModel.remove(addedStocksList.getSelectedIndex());
    updateButtonLocking();
  }



  private void updateButtonLocking() {
    int totalPercentage = calculateTotalPercentage();
    addButton.setEnabled(!tickerField.getText().isEmpty() && !percentageField.getText().isEmpty());
    removeButton.setEnabled(!addedStocksList.isSelectionEmpty());
    investButton.setEnabled(totalPercentage == 100);
  }

  private int calculateTotalPercentage() {
    return results.values().stream().mapToInt(Integer::intValue).sum();
  }

  private void clearStockEntry() {
    tickerField.setText("");
    percentageField.setText("");
  }

  private void addEntry() {
    String tickerInput = tickerField.getText().trim();
    if (tickerInput.isEmpty()) {
      JOptionPane.showMessageDialog(this,
              "Ticker symbol cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }
    int percentageInput = parsePercentageInput();
    if (percentageInput == -1) {
      return;
    }

    int newTotalPercentageForTicker = results.getOrDefault(tickerInput,
            0) + percentageInput;
    if (newTotalPercentageForTicker > 100) {
      JOptionPane.showMessageDialog(this,
              "Total weight for " + tickerInput + " would exceed 100%.",
              "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    int runningTotalExcludingCurrentTicker = calculateTotalPercentage()
                                                - results.getOrDefault(tickerInput, 0);
    if (runningTotalExcludingCurrentTicker + newTotalPercentageForTicker > 100) {
      JOptionPane.showMessageDialog(this,
              "Total weight cannot exceed 100%.",
              "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    results.put(tickerInput, newTotalPercentageForTicker);
    updateListModelForExistingTicker(tickerInput, newTotalPercentageForTicker);
    clearStockEntry();
    updateButtonLocking();
  }

  private int parsePercentageInput() {
    try {
      int percentageInput = Integer.parseInt(percentageField.getText().trim());
      if (percentageInput <= 0 || percentageInput > 100) {
        JOptionPane.showMessageDialog(this,
                "Percentage must be a positive number less than or equal to 100.",
                "Error", JOptionPane.ERROR_MESSAGE);
        return -1;
      }
      return percentageInput;
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(this,
              "Percentage must be a positive number less than or equal to 100.",
              "Error", JOptionPane.ERROR_MESSAGE);
      return -1;
    }
  }

  private void updateListModelForExistingTicker(String tickerInput, int newPercentage) {
    for (int i = 0; i < listModel.size(); i++) {
      if (listModel.get(i).startsWith(tickerInput + ",")) {
        listModel.remove(i);
        break;
      }
    }
    listModel.addElement(tickerInput + ", " + newPercentage + "%");
  }

  private void investStocks() {
    if (contributionAmountField.getText().isEmpty()) {
      JOptionPane.showMessageDialog(this,
              "Contribution amount cannot be empty.",
              "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }
    if (dateField.getText().isEmpty()) {
      JOptionPane.showMessageDialog(this,
              "Date cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }
    if (results.isEmpty()) {
      JOptionPane.showMessageDialog(this,
              "No stocks to invest in.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    ProcessingBox processingDialog = new ProcessingBox((JFrame)
            SwingUtilities.getWindowAncestor(contentPanel));
    SwingWorker<Void, Void> worker = new SwingWorker<>() {
      @Override
      protected Void doInBackground() {
        try {
          features.investFixedAmount(portfolioName,
              Double.parseDouble(contributionAmountField.getText()),
              dateField.getText(), results);
          layout.show(contentPanel, "Operations");
        } catch (Exception e) {
          processingDialog.dispose();
          JOptionPane.showMessageDialog(PortfolioAddStockByWeight.this,
              e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
      }

      protected void done() {
        processingDialog.dispose();
        layout.show(contentPanel, "Operations");
      }
    };
    worker.execute();
    processingDialog.setVisible(true);
  }


  /**
   * Gets the  current JPanel frame.
   * @return the JPanel.
   */
  public JPanel getPanel() {
    return this;
  }
}
