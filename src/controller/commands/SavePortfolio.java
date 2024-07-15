package controller.commands;

import controller.filewriter.IFileWriter;
import controller.filewriter.IFileWriterFactory;
import model.IHistorianPortfolioManager;
import view.text.IGraphingView;

/**
 * SavePortfolio is a class that saves a portfolio to a file.
 */

public class SavePortfolio extends AbstractCommand {
  private final IFileWriterFactory fileWriterFactory;
  private final String portfolioName;
  private final String filePath;

  /**
   * Constructs the SavePortfolio command object.
   *
   * @param model             The portfolio manager object.
   * @param view              The view object.
   * @param fileWriterFactory The file writer factory object.
   * @param portfolioName     The name of the portfolio to be saved.
   * @param filePath          The path of the file to be saved.
   */
  public SavePortfolio(IHistorianPortfolioManager model, IGraphingView view,
                       IFileWriterFactory fileWriterFactory, String portfolioName,
                       String filePath) {
    super(model, view);
    this.fileWriterFactory = fileWriterFactory;
    this.portfolioName = portfolioName;
    this.filePath = filePath;
  }


  private void save() {
    IFileWriter writer = this.fileWriterFactory.createFileWriter();
    try {
      model.savePortfolio(writer, this.portfolioName, this.filePath);
      view.displayMessage("Portfolio saved successfully!");
    } catch (Exception e) {
      view.displayErrorMessage(e);
    }
  }

  /**
   * Executes the command to SavePortfolio .
   */
  public void execute() {
    this.save();
  }

}
