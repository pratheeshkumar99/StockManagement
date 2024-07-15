package controller.filewriter;

import java.util.Set;

import model.IStock;

/**
 * This interface represents a file writer that writes the data to a file.
 */
public interface IFileWriter {

  /**
   * Writes the data to a file.
   * @param path          the path to save the file.
   * @param portfolioName the name of the portfolio.
   * @param stockData     the data to be saved.
   * @throws IllegalArgumentException if the path is invalid.
   */
  void write(String path, String portfolioName, Set<IStock> stockData)
          throws IllegalArgumentException;
}
