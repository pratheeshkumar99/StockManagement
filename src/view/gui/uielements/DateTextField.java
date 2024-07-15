package view.gui.uielements;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Simple wrapper for a text field that has input validation for only date related entries.
 */
public class DateTextField extends InputValidationTextField {
  private final String title;

  /**
   * Public Constructor for a DateTextField.
   * @param title the title of the text field.
   */
  public DateTextField(String title) {
    super(title + " (yyyy-mm-dd)", 10, (s -> s.matches("[0-9-]+")));
    this.title = title;
  }

  /**
   * Get the date stored in this text field.
   * @return the date of the field.
   * @throws IllegalArgumentException if the date is not fully entered or malformed.
   */
  public LocalDate getDate() throws IllegalArgumentException {
    if (this.getTextField().getText().length() != 10) {
      throw new IllegalArgumentException("Please finish entering date in field: " + title);
    }
    try {
      return LocalDate.parse(getTextField().getText());
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Date is not correctly formed in field: " + title);
    }
  }

}
