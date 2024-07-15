package controller.filereader;

/**
 * This interface represents a file reader factory that creates a file reader.
 */

public interface IFileReaderFactory {

  /**
   * Creates a new instance of a file reader.
   *
   * @param path the path of the file to be read.
   * @return a new instance of a file reader.
   */

  IFileReader createFileReader(String path);
}
