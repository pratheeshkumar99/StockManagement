package controller.commands;

import controller.filereader.IFileReader;
import controller.filereader.IFileReaderFactory;

import java.util.Map;
import java.util.Set;
import model.IHistorianPortfolioManager;
import model.IStock;
import view.text.IGraphingView;

/**
 * Command class that Loads the portfolio data based on the given file.
 * reading the stock names and quantities. Then it creates a portfolio with the read data.
 */

public class LoadPortfolio extends AbstractCommand {

  private final IFileReaderFactory fileReaderFactory;

  private final int option;
  private final String filePath;

  /**
   * Constructs the LoadPortfolio command object.
   *
   * @param model         The portfolio manager object.
   * @param view          The view object.
   * @param factoryReader The file reader factory object.
   * @param portfolioType The type of portfolio to be loaded.
   * @param path          The path of the file to be read.
   */

  public LoadPortfolio(IHistorianPortfolioManager model, IGraphingView view,
                       IFileReaderFactory factoryReader, int portfolioType, String path) {
    super(model, view);
    this.fileReaderFactory = factoryReader;
    this.option = portfolioType;
    this.filePath = path;
  }


  private void loadPortfolio() {
    if (this.option == 1) {
      this.loadMutablePortfolio();
    } else {
      this.loadImmutablePortfolio();
    }
  }

  /**
   * Loads the mutable portfolio.
   */

  private void loadMutablePortfolio() {
    IFileReader fReader = fileReaderFactory.createFileReader(this.filePath);
    try {
      Map<String, Set<IStock>> data = fReader.read(filePath);
      String portfolioName = data.keySet().iterator().next();
      this.model.createPortfolio(portfolioName);
      Set<IStock> stocks = data.values().iterator().next();
      for (IStock stock : stocks) {
        System.out.println(stock.getName() + " " + stock.getQuantity()
                + " " + stock.getDate() + " " + portfolioName);
        this.model.addStock(stock.getName(), stock.getQuantity(),
                stock.getDate(), portfolioName);
      }
      view.displayMessage("Portfolio Loaded Successfully");
    } catch (Exception e) {
      view.displayErrorMessage(e);
    }
  }

  /**
   * Loads the immutable portfolio.
   */

  private void loadImmutablePortfolio() {
    IFileReader fReader = fileReaderFactory.createFileReader(this.filePath);
    try {
      Map<String, Set<IStock>> data = fReader.read(filePath);

      String portfolioName = data.keySet().iterator().next();
      this.model.createPortfolio(portfolioName);
      Set<IStock> stocks = data.values().iterator().next();
      for (IStock stock : stocks) {
        this.model.addStock(stock.getName(), stock.getQuantity(),
                stock.getDate(), portfolioName);
      }

      this.model.flipMutability(portfolioName);
      view.displayMessage("Portfolio Loaded Successfully");
    } catch (Exception e) {
      view.displayMessage(e.getMessage());
    }
  }

  /**
   * Executes the command to load the portfolio.
   */

  @Override
  public void execute() {
    this.loadPortfolio();
  }
}
