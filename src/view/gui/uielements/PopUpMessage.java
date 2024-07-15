package view.gui.uielements;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Utility class for creating pop up messages in the GUI.
 */
public class PopUpMessage {

  /**
   * Create an error pop-up warning.
   * @param msg the message contained.
   * @param parent the parent container that this message belongs to.
   */
  public static void displayErrorMessage(String msg, JComponent parent) {
    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
        parent, "Error: " + msg, "Error", JOptionPane.ERROR_MESSAGE));
  }

  /**
   * Create a normal pop-up message.
   * @param msg the message to be displayed.
   * @param parent the parent container.
   */
  public static void displayInfoMessage(String msg, JComponent parent) {
    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
        parent, msg, "Message", JOptionPane.INFORMATION_MESSAGE));
  }
}
