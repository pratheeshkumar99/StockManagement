package view.gui.uielements;

import java.awt.Toolkit;
import java.util.function.Predicate;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * Generic text field class with input validation and a JLabel title to accompany it. This text
 * field has an enum to provide
 */
public class InputValidationTextField extends JPanel {

  private final JTextField innerField;

  /**
   * Normal constructor for an InputValidationTextField.
   * @param title the field title.
   * @param maxLength the max length of characters allowed.
   * @param inputChecker some predicate to determine if a string can be included in the text field.
   */
  public InputValidationTextField(String title, int maxLength, Predicate<String> inputChecker) {
    super();
    JLabel label = new JLabel(title);
    add(label);
    this.innerField = new JTextField();
    innerField.setColumns(Math.min(maxLength * 2, 25));
    AbstractDocument doc = (AbstractDocument) innerField.getDocument();
    doc.setDocumentFilter(new ChildDocumentFilter(maxLength, inputChecker));
    add(innerField);
  }

  /**
   * Constructor without any predicates.
   * @param title the field title.
   * @param maxLength the max length of the characters allowed.
   */
  public InputValidationTextField(String title, int maxLength) {
    super();
    JLabel label = new JLabel(title);
    add(label);
    this.innerField = new JTextField();
    innerField.setColumns(Math.min(maxLength * 2, 25));
    AbstractDocument doc = (AbstractDocument) innerField.getDocument();
    doc.setDocumentFilter(new ChildDocumentFilter(maxLength, s -> true));
    add(innerField);
  }

  /**
   * Constructor that uses an enum type to create special input rules.
   * @param title the field title.
   * @param maxLength the max length of the characters allowed.
   * @param type the type of the field.
   */
  public InputValidationTextField(String title, int maxLength, TextFieldType type) {
    super();
    JLabel label = new JLabel(title);
    add(label);
    this.innerField = new JTextField();
    innerField.setColumns(Math.min(maxLength * 2, 25));
    AbstractDocument doc = (AbstractDocument) innerField.getDocument();
    add(innerField);
    doc.setDocumentFilter(new ChildDocumentFilter(maxLength, type.getPred()));
  }

  /**
   * A type of text field, with its own text formatting rules.
   */
  public enum TextFieldType {
    TICKER(s -> s.matches("[a-zA-Z]+"));

    private final Predicate<String> pred;

    TextFieldType(Predicate<String> pred) {
      this.pred = pred;
    }

    public Predicate<String> getPred() {
      return this.pred;
    }
  }


  /**
   * Get the text field within this.
   * @return the inner text field.
   */
  public JTextField getTextField() {
    return this.innerField;
  }

  /**
   * Add a function that runs whenever the text field is edited.
   *
   * @param onDocUpdateFunc the function that runs.
   */
  public void addListener(Runnable onDocUpdateFunc) {
    this.innerField.getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        onDocUpdateFunc.run();
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        onDocUpdateFunc.run();
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        onDocUpdateFunc.run();
      }
    });
  }


  /**
   * Get the text (if any in this text field).
   *
   * @return the text contained, or an empty string if there is nothing.
   */
  public String getText() {
    return innerField.getDocument() == null ? "" : innerField.getText();
  }

  private static class ChildDocumentFilter extends DocumentFilter {

    private final int maxLength;
    private final Predicate<String> inputChecker;

    private ChildDocumentFilter(int maxLength, Predicate<String> inputChecker) {
      super();
      this.inputChecker = inputChecker;
      this.maxLength = maxLength;
    }

    @Override
    public void insertString(FilterBypass fb, int offs, String str, AttributeSet a)
        throws BadLocationException {
      String textBefore = fb.getDocument().getText(0, fb.getDocument().getLength());
      String textAfter = new StringBuilder(textBefore).insert(offs, str).toString();

      if (textAfter.length() <= maxLength && inputChecker.test(textAfter)) {
        super.insertString(fb, offs, str, a);
      } else {
        Toolkit.getDefaultToolkit().beep();
      }
    }


    @Override
    public void replace(FilterBypass fb, int offset, int length, String str, AttributeSet attrs)
        throws BadLocationException {
      String textBefore = fb.getDocument().getText(0, fb.getDocument().getLength());
      String textAfter = new StringBuilder(textBefore).replace(offset, offset + length, str)
                           .toString();

      if (textAfter.length() <= maxLength && inputChecker.test(textAfter)) {
        super.replace(fb, offset, length, str, attrs);
      } else {
        Toolkit.getDefaultToolkit().beep();
      }
    }

  }
}
