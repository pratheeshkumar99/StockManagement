package view.gui.hometab;

import controller.IFeatures;
import java.awt.Component;
import java.awt.Dimension;
import java.time.LocalDate;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.border.BevelBorder;
import view.gui.uielements.DateTextField;
import view.gui.uielements.InputValidationTextField;
import view.gui.uielements.InputValidationTextField.TextFieldType;
import view.gui.uielements.IntegerTextField;
import view.gui.uielements.PopUpMessage;
import view.gui.uielements.ProcessingBox;

/**
 * The JPanel that supports stock statistical analysis.
 */
public class StockStatsPanel extends JPanel {
  private final IFeatures features;
  private JButton periodNetButton;
  private JButton xDayMovingAverageButton;
  private  JButton crossoverButton;
  private JButton movingCrossoverButton;
  private final InputValidationTextField tickerTextField;
  private final DateTextField startDateField;
  private final DateTextField endDateField;
  private IntegerTextField movingCrossoverXLength;
  private IntegerTextField movingCrossoverYLength;
  private IntegerTextField xDayMovingAverageLength;


  /**
   * Public constructor for a stock statistical analysis panel.
   * @param features the features that help it calculate statistics.
   */
  public StockStatsPanel(IFeatures features) {
    super();
    this.features = features;
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


    JPanel stockAndDatesPanel = new JPanel();
    tickerTextField = new InputValidationTextField(
      "Stock Ticker Symbol", 5, TextFieldType.TICKER);
    tickerTextField.addListener(this::updateButtonLocking);
    startDateField = new DateTextField("Start Date");
    startDateField.addListener(this:: updateButtonLocking);
    endDateField = new DateTextField("End Date");
    endDateField.addListener(this::updateButtonLocking);
    stockAndDatesPanel.add(startDateField);
    stockAndDatesPanel.add(endDateField);
    stockAndDatesPanel.add(tickerTextField);
    stockAndDatesPanel.setPreferredSize(new Dimension(400, 100));
    add(stockAndDatesPanel);
    String msg = "For all stock statistics, please enter the New York Stock Exchange ticker symbol,"
                   + " as well as a start date and end date depending on the operation desired";
    JTextArea info = new JTextArea(msg);
    info.setEditable(false);
    info.setLineWrap(true);
    info.setWrapStyleWord(true);
    add(info);

    //Create separate panels for XDay Moving Average, arrange them in a Grid Pattern
    JPanel optionsPanel = new JPanel();
    optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
    optionsPanel.add(xDayMovingAverage());
    optionsPanel.add(periodStockPerformance());
    optionsPanel.add(crossovers());
    optionsPanel.add(movingCrossovers());
    add(optionsPanel);
    updateButtonLocking();
  }


  private JPanel xDayMovingAverage() {
    String txt = "Calculate the X-day moving average";
    String instructions = "Enter the number of days (must be a positive integer),"
                            + " as well as the stock ticker."
                            + " The \"End Date\" field determines the date of the average.";
    xDayMovingAverageLength = new IntegerTextField("X day length");
    xDayMovingAverageLength.addListener(this::updateButtonLocking);
    JPanel[] inputFields = new JPanel[] {xDayMovingAverageLength};
    xDayMovingAverageButton = new JButton("Calculate");
    xDayMovingAverageButton.addActionListener(a -> {
      if (checkStartAndEndDateFormatting() && xDayMovingAverageLength.getInt() > 0) {
        features.xDayMovingAverage(
            tickerTextField.getText(), xDayMovingAverageLength.getInt(), endDateField.getDate());
      } else if (xDayMovingAverageLength.getInt() <= 0) {
        PopUpMessage.displayErrorMessage("Moving Average length must be a positive integer", null);
      }
    });
    return calcOption(txt, instructions, inputFields, xDayMovingAverageButton);
  }

  private JPanel periodStockPerformance() {
    String txt = "Calculate the gains or losses on a given date or over a range.";
    String instructions = "Enter the \"Stock Ticker Symbol\", \"End Date\" and \"Start Date\" to "
                            + "view the gains/losses of the stock specified over a period of time."
                            + " Clear the \"Start Date\" or make it equal to \"End Date\" "
                            + "to view the gains/losses within a single day. "
                            + "The \"State Date\" cannot be later than the \"End Date.\"";
    periodNetButton = new JButton("Calculate");
    periodNetButton.addActionListener(a ->  {
      if (checkStartAndEndDateFormatting()) {
        String ticker = tickerTextField.getText();
        LocalDate start = startDateField.getText().isEmpty()
                            ? endDateField.getDate() : startDateField.getDate();
        LocalDate end = endDateField.getDate();
        runOperation(e -> features.stockPerformanceNet(ticker, start, end));
      }
    });
    return calcOption(txt, instructions, new JPanel[]{}, periodNetButton);
  }

  private JPanel crossovers() {
    String txt = "Calculate the Crossover Days";
    String instructions = "Enter the \"Stock Ticker Symbol\", \"Start Date\" and \"End Date\" "
                            + "to calculate the crossover days using a 30Day moving average.";
    crossoverButton = new JButton("Calculate");
    crossoverButton.addActionListener(a -> {
      if (checkStartAndEndDateFormatting()) {
        String ticker = tickerTextField.getText();
        LocalDate start = startDateField.getDate();
        LocalDate end = endDateField.getDate();
        runOperation(e -> features.crossoverDays(ticker, start, end));
      }
    });
    return calcOption(txt, instructions, new JPanel[]{}, crossoverButton);
  }

  private JPanel movingCrossovers() {
    String txt = "Calculate the moving crossover days";
    String instructions = "Enter the \"Stock Ticker Symbol\", \"Start Date\", \"End Date\","
                            + " \"X-Day Length\", and \"Y-Day Length\" fields "
                            + "to calculate the moving crossover days such that the"
                            + " x-day moving average crosses the y-day moving average. "
                            + "The \"Y-Day Length\" must be greater than the \"X-Day Length\".";
    movingCrossoverXLength = new IntegerTextField("X Day Length");
    movingCrossoverXLength.addListener(this::updateButtonLocking);
    movingCrossoverYLength = new IntegerTextField("Y Day Length");
    movingCrossoverYLength.addListener(this::updateButtonLocking);
    movingCrossoverButton = new JButton("Calculate");
    movingCrossoverButton.addActionListener(a -> {
      boolean hasX = !movingCrossoverXLength.getText().isEmpty();
      boolean hasY = !movingCrossoverYLength.getText().isEmpty();
      boolean yGreaterThanX = hasX && hasY && movingCrossoverXLength.getInt() > 0
                               && movingCrossoverYLength.getInt() > movingCrossoverXLength.getInt();
      if (checkStartAndEndDateFormatting() && yGreaterThanX) {
        String ticker = tickerTextField.getText();
        int x = movingCrossoverXLength.getInt();
        int y = movingCrossoverYLength.getInt();
        LocalDate start = startDateField.getDate();
        LocalDate end = endDateField.getDate();
        runOperation(c -> features.movingCrossOverDays(ticker, x, y, start, end));
      } else if (movingCrossoverXLength.getInt() < 1) {
        PopUpMessage.displayErrorMessage("X Day Length must be at least 1", null);
      } else if (movingCrossoverYLength.getInt() <= movingCrossoverXLength.getInt()) {
        PopUpMessage.displayErrorMessage("Y Day Length must be greater than X Day Length", null);
      }
    });
    return calcOption(txt, instructions,
      new JPanel[] {movingCrossoverXLength, movingCrossoverYLength}, movingCrossoverButton);
  }

  private JPanel calcOption(String label, String info, JPanel[] inputs, JButton calcButton) {
    JPanel main = new JPanel();
    main.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
    //add the label at the top
    JLabel jLabel = new JLabel(label);
    jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    main.add(jLabel);

    JTextArea jInstructions = new JTextArea(info);
    jInstructions.setEditable(false);
    jInstructions.setLineWrap(true);
    jInstructions.setWrapStyleWord(true);
    main.add(jInstructions);

    //Create a sub-panel to contain all inputs and the button
    JPanel ioPanel = new JPanel();
    ioPanel.setLayout(new BoxLayout(ioPanel, BoxLayout.X_AXIS));
    if (inputs.length > 0) {
      JPanel fieldsPanel = new JPanel();
      fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
      for (JPanel input : inputs) {
        fieldsPanel.add(input);
      }
      ioPanel.add(fieldsPanel);
    }
    //Create a separate panel for the button, such that it's preferred size is used
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(calcButton);
    calcButton.setPreferredSize(new Dimension(100, 30));
    ioPanel.add(buttonPanel);
    main.add(ioPanel);
    return main;
  }

  private void updateButtonLocking() {
    boolean hasTicker = !tickerTextField.getTextField().getText().isEmpty();
    boolean hasStartDate = startDateField.getTextField().getText().length() == 10;
    boolean hasEndDate = endDateField.getTextField().getText().length() == 10;
    boolean startDateEmpty = startDateField.getTextField().getText().isEmpty();
    boolean hasAverageLength = !xDayMovingAverageLength.getTextField().getText().isEmpty();
    boolean hasXDayLength = !movingCrossoverXLength.getTextField().getText().isEmpty();
    boolean hasYDayLength = !movingCrossoverYLength.getTextField().getText().isEmpty();


    xDayMovingAverageButton.setEnabled(hasTicker && hasEndDate && hasAverageLength);
    periodNetButton.setEnabled(hasTicker && hasEndDate && (startDateEmpty || hasStartDate));
    crossoverButton.setEnabled(hasTicker && hasStartDate && hasEndDate);
    movingCrossoverButton.setEnabled(hasTicker && hasStartDate && hasEndDate
                                       && hasXDayLength && hasYDayLength);
  }

  private boolean checkStartAndEndDateFormatting() {
    try {
      LocalDate start = startDateField.getDate();
      LocalDate end = endDateField.getDate();
      return end.isAfter(start) || end.isEqual(start);
    } catch (IllegalArgumentException e) {
      PopUpMessage.displayErrorMessage(e.getMessage(), this);
      return false;
    }
  }

  private void runOperation(Consumer<Void> operation) {
    ProcessingBox ld = new ProcessingBox(null);
    SwingWorker<Void, Void> worker = new SwingWorker<>() {
      @Override
      protected Void doInBackground() {
        operation.accept(null);
        return null;
      }

      @Override
      protected void done() {
        ld.dispose();
      }
    };
    worker.execute();
    ld.setVisible(true);
  }
}
