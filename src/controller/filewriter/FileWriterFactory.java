package controller.filewriter;

/**
 * This class implements the IFileWriter interface and provides the functionality to write.
 * data to a file.
 */

public class FileWriterFactory implements IFileWriterFactory {

  /**
   * Creates a new file writer.
   * @return a new file writer.
   */


  public IFileWriter createFileWriter() {
    return this.getFileWriter();
  }


  private IFileWriter getFileWriter() {
    return new CSVWriter();
  }

}