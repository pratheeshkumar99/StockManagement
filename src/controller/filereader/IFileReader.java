package controller.filereader;

import java.util.Map;
import java.util.Set;

import model.IStock;

/**
 * This interface represents a file reader that reads the data from a file.
 */


public interface IFileReader {

  /**
   * Reads the data from a file.
   *
   * @param path the path of the file to be read.
   * @return the data read from the file.
   */

  Map<String, Set<IStock>> read(String path);
}
