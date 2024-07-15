package view.gui.uielements;

/**
 * Simple extension of the InputValidationTextField used for getting double values.
 */
public class DoubleTextField extends InputValidationTextField {

  /**
   * Constructor for a DoubleTextField.
   * @param title the text field title.
   */
  public DoubleTextField(String title) {
    super(title, 10, s -> s.matches("(\\d+\\.?\\d*)|(\\.\\d+)"));
  }
}
