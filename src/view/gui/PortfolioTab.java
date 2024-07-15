package view.gui;

import controller.IFeatures;
import view.gui.uielements.ProcessingBox;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;
import view.text.IGraphingView;

/**
 * The View class associated with the PortfolioTab.
 */
public class PortfolioTab extends JPanel {
  String quantity;
  String ticker;
  String date;
  private final IFeatures features;
  private final String portfolioName;
  private final CardLayout layout = new CardLayout();
  private final JPanel contentPanel = new JPanel(layout);
  private final JPanel operationPanel = new JPanel(new
          GridLayout(0, 1, 5, 5));

  /**
   * Constructor for the PortfolioTab class.
   *
   * @param features the features object.
   * @param name     the name of the portfolio.
   * @param view     the GUI view.
   */
  public PortfolioTab(IFeatures features, String name, IGraphingView view) {
    this.features = features;
    this.portfolioName = name;
    setLayout(new BorderLayout());

    initializeOperations();

    JScrollPane scrollPane = new JScrollPane(operationPanel);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

    contentPanel.add(scrollPane, "Operations");

    add(contentPanel, BorderLayout.CENTER);
    layout.show(contentPanel, "Operations");
  }

  private void initializeOperations() {
    JButton buyButton = createSmallButton("Buy Stock");
    buyButton.addActionListener(e -> showBuyStockForm());
    operationPanel.add(buyButton);

    JButton sellButton = createSmallButton("Sell Stock");
    sellButton.addActionListener(e -> showSellStockForm());
    operationPanel.add(sellButton);

    JButton getCostBasis = createSmallButton("Get cost basis");
    getCostBasis.addActionListener(e -> showGetCostBasisForm());
    operationPanel.add(getCostBasis);

    JButton getPortfolioValue = createSmallButton("Get Portfolio Value");
    getPortfolioValue.addActionListener(e -> showGetPortfoliValueForm());
    operationPanel.add(getPortfolioValue);

    JButton savePortfolio = createSmallButton("Save Portfolio");
    savePortfolio.addActionListener(e -> savePortfolioAction());
    operationPanel.add(savePortfolio);

    JButton weightedPortfolioInvestment = createSmallButton("Weighted Portfolio Investment");
    weightedPortfolioInvestment.addActionListener(e -> showWeightedPortfolioInvestmentForm());
    operationPanel.add(weightedPortfolioInvestment);

    operationPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10,
            10));
  }


  private JButton createSmallButton(String text) {
    JButton button = new JButton(text);
    button.setPreferredSize(new Dimension(100, 20));
    return button;
  }

  private void showBuyStockForm() {
    JPanel buyPanel = createStockTransactionBuyForm();
    contentPanel.add(buyPanel, "Buy");
    layout.show(contentPanel, "Buy");
  }

  private void showSellStockForm() {
    JPanel sellPanel = createStockTransactionSellForm();
    contentPanel.add(sellPanel, "Sell");
    layout.show(contentPanel, "Sell");
  }

  private void showGetCostBasisForm() {
    JPanel costBasisPanel = getPortfolioCostBasisForm();
    contentPanel.add(costBasisPanel, "Get Cost Basis");
    layout.show(contentPanel, "Get Cost Basis");
  }

  private void showGetPortfoliValueForm() {
    JPanel getPortfolioValuePanel = getPortfolioValueForm();
    contentPanel.add(getPortfolioValuePanel, "Get Portfolio Value");
    layout.show(contentPanel, "Get Portfolio Value");
  }

  private void showWeightedPortfolioInvestmentForm() {
    JPanel weightedPortfolioInvestmentPanel = getWeightedPortfolioInvestmentForm();
    contentPanel.add(weightedPortfolioInvestmentPanel, "Weighted Portfolio Investment");
    layout.show(contentPanel, "Weighted Portfolio Investment");
  }

  private JPanel createStockTransactionBuyForm() {
    JPanel formPanel = new JPanel();


    formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
    JTextField tickerField = new JTextField();
    tickerField.setMaximumSize(new Dimension(Integer.MAX_VALUE,
            tickerField.getPreferredSize().height));
    JTextField quantityField = new JTextField();
    quantityField.setMaximumSize(new Dimension(Integer.MAX_VALUE,
            tickerField.getPreferredSize().height));
    JButton submitButton = createSmallButton("Submit");
    JButton backButton = createSmallButton("Back");

    formPanel.add(new JLabel("Ticker:"));
    formPanel.add(tickerField);
    formPanel.add(new JLabel("Quantity:"));
    formPanel.add(quantityField);
    JLabel label = new JLabel("Enter date (yyyy-mm-dd):");
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    formPanel.add(label);


    JTextField dateTextField = new JTextField(10);
    dateTextField.setMaximumSize(new Dimension(150, 25));
    dateTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
    formPanel.add(Box.createVerticalStrut(10));
    formPanel.add(dateTextField);
    JPanel buttonPanel = new JPanel();
    formPanel.add(buttonPanel);
    formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    buttonPanel.add(backButton);
    buttonPanel.add(submitButton);
    formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    submitButton.addActionListener(new AbstractAction() {

      /**
       * Invoked when an action occurs.
       * @param e the event to be processed.
       */
      @Override
      public void actionPerformed(ActionEvent e) {
        ticker = tickerField.getText().trim();
        quantity = quantityField.getText().trim();
        date = dateTextField.getText().trim();
        if (ticker.isEmpty() || quantity.isEmpty() || date.isEmpty()) {
          JOptionPane.showMessageDialog(null, "Please fill in all fields");
        } else {
          ProcessingBox processingDialog = new ProcessingBox((JFrame)
                  SwingUtilities.getWindowAncestor(contentPanel));
          SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

            /**
             * Computes a result, or throws an exception if unable to do so.
             * @return the computed result.
             * @throws Exception if unable to compute a result.
             */
            @Override
            protected Void doInBackground() throws Exception {
              try {
                features.buyStock(ticker, quantity, date, portfolioName);
              } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
              }
              return null;
            }

            /**
             * Collapses the frame after the operation is done.
             */
            @Override
            protected void done() {
              processingDialog.dispose();
              layout.show(contentPanel, "Operations");
            }
          };
          worker.execute();
          processingDialog.setVisible(true);
        }
      }
    });
    backButton.addActionListener(e -> layout.show(contentPanel, "Operations"));
    return formPanel;
  }


  private JPanel createStockTransactionSellForm() {
    JPanel formPanel = new JPanel();


    formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
    JTextField tickerField = new JTextField();
    tickerField.setMaximumSize(new Dimension(Integer.MAX_VALUE,
            tickerField.getPreferredSize().height));
    JTextField quantityField = new JTextField();
    quantityField.setMaximumSize(new Dimension(Integer.MAX_VALUE,
            tickerField.getPreferredSize().height));
    JButton submitButton = createSmallButton("Submit");
    JButton backButton = createSmallButton("Back");

    formPanel.add(new JLabel("Ticker:"));
    formPanel.add(tickerField);
    formPanel.add(new JLabel("Quantity:"));
    formPanel.add(quantityField);
    JLabel label = new JLabel("Enter date (yyyy-mm-dd):");
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    formPanel.add(label);


    JTextField dateTextField = new JTextField(10);
    dateTextField.setMaximumSize(new Dimension(150, 25));
    dateTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
    formPanel.add(Box.createVerticalStrut(10));
    formPanel.add(dateTextField);

    JPanel buttonPanel = new JPanel();

    formPanel.add(buttonPanel);
    formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    buttonPanel.add(backButton);
    buttonPanel.add(submitButton);
    formPanel.add(Box.createRigidArea(new Dimension(0, 10)));


    formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    submitButton.addActionListener(new AbstractAction() {

      /**
       * Action performed when the Submit button is clicked.
       * @param e the event to be processed.
       */
      @Override
      public void actionPerformed(ActionEvent e) {
        ticker = tickerField.getText().trim();
        quantity = quantityField.getText().trim();
        date = dateTextField.getText().trim();
        if (ticker.isEmpty() || quantity.isEmpty() || date.isEmpty()) {
          JOptionPane.showMessageDialog(null, "Please fill in all fields");
        } else {
          ProcessingBox processingDialog
              = new ProcessingBox((JFrame) SwingUtilities.getWindowAncestor(contentPanel));
          SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            /**
             * Computes a result, or throws an exception if unable to do so.
             * @return the computed result.
             * @throws Exception if unable to compute a result.
             */
            @Override
            protected Void doInBackground() throws Exception {
              try {
                features.sellStock(ticker, quantity, date, portfolioName);
              } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
              }
              return null;
            }

            /**
             * Collapse the frame after the operation is done.
             */
            @Override
            protected void done() {
              processingDialog.dispose();
              layout.show(contentPanel, "Operations");
            }
          };
          worker.execute();
          processingDialog.setVisible(true);

        }
      }
    });
    backButton.addActionListener(e -> layout.show(contentPanel, "Operations"));
    return formPanel;
  }


  private JPanel getPortfolioCostBasisForm() {
    JPanel formPanel = new JPanel();
    formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

    formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JLabel label = new JLabel("Enter date (yyyy-mm-dd):");
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    formPanel.add(label);


    JTextField dateTextField = new JTextField(10);
    dateTextField.setMaximumSize(new Dimension(150, 25));
    dateTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
    formPanel.add(Box.createVerticalStrut(10));
    formPanel.add(dateTextField);


    JButton submitButton = new JButton("Submit");
    JButton backButton = createSmallButton("Back");
    JPanel buttonPanel = new JPanel();
    formPanel.add(buttonPanel);
    formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    buttonPanel.add(backButton);
    buttonPanel.add(submitButton);
    formPanel.add(Box.createRigidArea(new Dimension(0, 10)));

    backButton.addActionListener(e -> layout.show(contentPanel, "Operations"));

    submitButton.addActionListener(new AbstractAction() {
      /**
       * Action performed when the Submit button is clicked.
       * @param e the event to be processed.
       */
      @Override
      public void actionPerformed(ActionEvent e) {
        String date = dateTextField.getText().trim();
        if (date.isEmpty()) {
          String msg = "Date field cannot be empty."
                         + " Please enter a valid date in the format yyyy-MM-dd";
          JOptionPane.showMessageDialog(null, msg);
        } else {
          try {

            features.costBasis(portfolioName, date);

            layout.show(contentPanel, "Operations");
          } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
          }
        }
      }
    });
    return formPanel;
  }


  private JPanel getPortfolioValueForm() {
    JPanel formPanel = new JPanel();
    formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

    formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JLabel label = new JLabel("Enter date (yyyy-mm-dd):");
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    formPanel.add(label);


    JTextField dateTextField = new JTextField(10);
    dateTextField.setMaximumSize(new Dimension(150, 25));
    dateTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
    formPanel.add(Box.createVerticalStrut(10));
    formPanel.add(dateTextField);


    JButton submitButton = new JButton("Submit");
    JButton backButton = createSmallButton("Back");
    JPanel buttonPanel = new JPanel();
    formPanel.add(buttonPanel);
    formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    buttonPanel.add(backButton);
    buttonPanel.add(submitButton);
    formPanel.add(Box.createRigidArea(new Dimension(0, 10)));

    backButton.addActionListener(e -> layout.show(contentPanel, "Operations"));

    submitButton.addActionListener(new AbstractAction() {

      /**
       * Action performed when the Submit button is clicked.
       * @param e the event to be processed.
       */
      @Override
      public void actionPerformed(ActionEvent e) {
        String date = dateTextField.getText().trim();
        if (date.isEmpty()) {
          String msg = "Date field cannot be empty."
                         + " Please enter a valid date in the format yyyy-MM-dd";
          JOptionPane.showMessageDialog(null, msg);
        } else {
          try {
            features.portfolioValue(portfolioName, date);
            layout.show(contentPanel, "Operations");
          } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
          }
        }
      }
    });
    return formPanel;
  }

  private void savePortfolioAction() {
    JFileChooser fileChooser = new JFileChooser(".");
    fileChooser.setDialogTitle("Specify a directory to save the file");
    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    FileNameExtensionFilter fileTypes = new FileNameExtensionFilter("CSV files (*.csv)",
            "csv");
    fileChooser.setFileFilter(fileTypes);
    fileChooser.setAcceptAllFileFilterUsed(false);

    int userSelection = fileChooser.showSaveDialog(null);
    if (userSelection == JFileChooser.APPROVE_OPTION) {
      String fileToSave = fileChooser.getSelectedFile().getAbsolutePath();
      if (fileToSave.isEmpty()) {
        String msg = "Please enter a valid file name";
        JOptionPane.showMessageDialog(null, msg);
        return;
      }
      try {
        features.savePortfolio(portfolioName, fileToSave);
      } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, ex.getMessage());
      }
    }
  }

  private JPanel getWeightedPortfolioInvestmentForm() {
    PortfolioAddStockByWeight addStockByWeight = new PortfolioAddStockByWeight(contentPanel,
            layout, this.features, this.portfolioName);
    JPanel panel = addStockByWeight.getPanel();
    JPanel wrapperPanel = new JPanel(new BorderLayout());
    wrapperPanel.add(panel, BorderLayout.CENTER);
    JButton backButton = new JButton("Back");
    wrapperPanel.add(backButton, BorderLayout.SOUTH);
    backButton.addActionListener(e -> layout.show(contentPanel, "Operations"));
    return wrapperPanel;
  }
}

