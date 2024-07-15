package controller.filereader;

import java.io.File;

/**
 * This class implements the IFileReaderFactory interface and provides the functionality to create a
 * new instance of a file reader.
 */

public class FileReaderFactory implements IFileReaderFactory {

  /**
   * Constructs a new FileReaderFactory object.
   */
  public FileReaderFactory() {
    //Constructs the object.
  }

  /**
   * Creates a new instance of a file reader.
   *
   * @param path the path of the file to be read.
   * @return a new instance of a file reader.
   */


  public IFileReader createFileReader(String path) {
    return this.getFileReader(path);
  }

  private IFileReader getFileReader(String path) {
    File file = new File(path);
    String fileName = file.getName();
    String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
    if (extension.equals("csv")) {
      return new CSVReader();
    } else {
      throw new IllegalArgumentException("Invalid file type. Please enter a valid file type(CSV).");
    }
  }


}
