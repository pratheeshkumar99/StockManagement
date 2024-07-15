package view.gui.uielements;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * Display a processing box the user is waiting for a process to complete.
 */

public class ProcessingBox extends JDialog {

  /**
   * Constructor for a processing box.
   * @param parent the parent container.
   */
  public ProcessingBox(JFrame parent) {
    super(parent, "Processing", true);
    setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    setSize(300, 100);
    setLocationRelativeTo(parent);

    JLabel label = new JLabel("Processing...");
    label.setHorizontalAlignment(SwingConstants.CENTER);
    add(label);
  }
}

