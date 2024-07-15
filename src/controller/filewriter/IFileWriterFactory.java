package controller.filewriter;

/**
 * An interface used by the model to write its components to a file.
 */
public interface IFileWriterFactory {

  /**
   * Creates a new instance of a file writer.
   * @return a new instance of a file writer.
   */
  IFileWriter createFileWriter() ;

}
