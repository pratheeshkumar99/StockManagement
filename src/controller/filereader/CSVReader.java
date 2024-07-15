package controller.filereader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.IStock;
import model.Stock;

/**
 * This class implements the IFileReader interface and provides the functionality to read data
 * from a file.
 */

class CSVReader implements IFileReader {

  /**
   * Reads the data from a file.
   *
   * @param path the path of the file to be read.
   * @return the data read from the file.
   */

  public Map<String, Set<IStock>> read(String path) {
    Map<String, Set<IStock>> data = new HashMap<>();
    Set<IStock> stocks = this.readFile(path);
    String portfolioName = Paths.get(path).getFileName().toString();
    portfolioName =
            portfolioName.indexOf(".") > 0
                    ? portfolioName.substring(0, portfolioName.lastIndexOf("."))
                    : portfolioName;
    data.put(portfolioName, stocks);
    return data;
  }

  private Set<IStock> readFile(String path) {
    Set<IStock> stocks = new HashSet<>();
    try {
      BufferedReader buffer = new BufferedReader(new FileReader(path));
      String line;
      while ((line = buffer.readLine()) != null) {
        String[] data = line.split(",");
        if (data.length != 3) {
          throw new IllegalArgumentException("Invalid file data. Please enter a valid file data.");
        }
        stocks.add(new Stock(data[0], Double.parseDouble(data[1]), LocalDate.parse(data[2])));
      }
      return stocks;
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    } catch (IOException e) {
      throw new RuntimeException("Invalid file path. Please enter a valid file path.");
    }
  }
}
