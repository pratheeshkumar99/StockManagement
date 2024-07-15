package controller.filewriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

import model.IStock;

/**
 * This class implements the IFileWriter interface and provides the functionality to write data
 * to a file.
 */

public class CSVWriter implements IFileWriter {

  /**
   * Writes the data to a file.
   *
   * @param path     the path to save the file.
   * @param fileName the name of the file.
   * @param data     the data to be saved.
   */


  @Override
  public void write(String path, String fileName, Set<IStock> data) {
    try {
      this.save(path, fileName, data);
    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  /**
   * Saves the data to a file.
   * @param path     the path to save the file.
   * @param fileName the name of the file.
   * @param data     the data to be saved.
   * @throws FileNotFoundException if the path is invalid.
   */
  private void save(String path, String fileName, Set<IStock> data)
          throws FileNotFoundException {

    String name = fileName + ".csv";
    try {
      java.io.FileWriter writer = new java.io.FileWriter(path + "/" + name);

      for (IStock stock : data) {
        System.out.println(stock.getName());
        System.out.println(stock.getQuantity());
        System.out.println(stock.getDate());
        writer.write(stock.getName() + "," + stock.getQuantity() + "," + stock.getDate() + "\n");

      }
      writer.close();
    } catch (IOException e) {
      throw new FileNotFoundException("Invalid Path.");
    }
  }

}