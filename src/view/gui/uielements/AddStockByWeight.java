package view.gui.uielements;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicBorders.MarginBorder;
import view.gui.uielements.InputValidationTextField.TextFieldType;

/**
 * JPanel used by the home tab to add stocks by weight for Dollar Cost Averaging.
 */
public class AddStockByWeight extends JPanel {
  private final JButton addButton;
  private final JButton removeButton;
  private final Map<String, Integer> results;
  private final JTextField tickerField;
  private final JTextField contributionAmountField;
  private final JTextField percentageField;
  private final JList<String> addedStocksList;

  private final DefaultListModel<String> listModel;

  private final Runnable onReadyFunc;

  /**
   * Constructor for an AddStockByWeight panel.
   * @param onReadyFunc a function that happens
   *                    whenever the panel has the necessary contribution total and splits.
   */
  public AddStockByWeight(Runnable onReadyFunc) {
    super();
    this.onReadyFunc = onReadyFunc;
    setLayout(new GridLayout(1, 2));
    results = new HashMap<>();
    DoubleTextField totalContributionAmount = new DoubleTextField("Total Contribution Value");
    contributionAmountField = totalContributionAmount.getTextField();
    InputValidationTextField ticker = new InputValidationTextField(
        "Stock Ticker Symbol", 5, TextFieldType.TICKER);
    tickerField = ticker.getTextField();
    IntegerTextField percentage = new IntegerTextField("Percent Contribution");
    percentageField = percentage.getTextField();
    addButton = new JButton("Add");
    removeButton = new JButton("Remove Selected");

    JPanel inputPanel = new JPanel();
    inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
    inputPanel.add(totalContributionAmount);
    inputPanel.add(ticker);
    inputPanel.add(percentage);
    inputPanel.add(addButton);
    inputPanel.add(removeButton);
    add(inputPanel);

    JPanel listPanel = new JPanel();
    listModel = new DefaultListModel<>();
    addedStocksList = new JList<>(listModel);
    addedStocksList.setPreferredSize(new Dimension(80, 200));
    addedStocksList.setBorder(new MarginBorder());
    listPanel.add(addedStocksList);
    add(listPanel);

    updateButtonLocking();
    ticker.addListener(this::updateButtonLocking);
    percentage.addListener(this::updateButtonLocking);
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
  }

  private void removeEntry() {
    String toRemove = addedStocksList.getSelectedValue().split(",")[0];
    results.remove(toRemove);
    listModel.remove(addedStocksList.getSelectedIndex());
  }

  /**
   * Get a breakdown of the weight of each stock.
   * @return A Map of each stock and its percentage.
   */
  public Map<String, Integer> getResults() {
    return this.results;
  }

  /**
   * Get the total contribution amount in this JPanel.
   * @return the double value if present, otherwise return null.
   */
  public Double getContributionAmount() {
    return (this.contributionAmountField.getText().isEmpty())
             ? null : Double.parseDouble(this.contributionAmountField.getText());
  }

  private void updateButtonLocking() {
    addButton.setEnabled(!tickerField.getText().isEmpty() && !percentageField.getText().isEmpty());
    removeButton.setEnabled(!addedStocksList.isSelectionEmpty());
    if (calcSum() == 100) {
      onReadyFunc.run();
    }
  }

  private void clearStockEntry() {
    tickerField.setText("");
    percentageField.setText("");
    //TODO: this doesn't work not sure why.
  }

  private int calcSum() {
    return this.results.values().stream().mapToInt(Integer::intValue).sum();
  }

  private void addEntry() {
    String tickerInput = tickerField.getText();
    int percentageInput;
    try {
      percentageInput = Integer.parseInt(percentageField.getText());
      if (calcSum() + percentageInput > 100) {
        PopUpMessage.displayErrorMessage("Sum of percentages must be 100%", null);
      } else if (results.containsKey(tickerInput)) {
        PopUpMessage.displayErrorMessage(tickerInput + "is already present", null);
      } else {
        results.put(tickerInput, percentageInput);
        listModel.addElement(tickerInput + ", " + percentageInput + "%");
      }
    } catch (NumberFormatException ignored) { }
  }

}
