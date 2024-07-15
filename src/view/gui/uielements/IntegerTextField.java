package view.gui.uielements;

/**
 * Special text field that only has integer values.
 */
public class IntegerTextField extends InputValidationTextField {

  public IntegerTextField(String title) {
    super(title, 10, (s -> s.matches("[0-9]+")));
  }

  /**
   * get the value stored in the text field.
   * @return the value stored.
   * @throws IllegalArgumentException if the input is too large to be stored in an int.
   */
  public int getInt() throws IllegalArgumentException {
    String s = getTextField().getText();
    try {
      return Integer.parseInt(s);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Could not parse input of:" + s);
    }
  }
}
