import controller.IFeatures;
import controller.IMainController;
import controller.OldMainMenu;
import javax.swing.JFrame;
import javax.swing.UIManager;
import model.OngoingTransactionModel;
import model.OngoingTransactionModelImpl;
import view.gui.JFrameView;
import view.text.GraphingTextView;
import view.text.IGraphingView;

/**
 * A program to help users plan out investment opportunities by.
 * managing and analyzing multiple portfolios.
 */


public class InvestmentSimulation {

  /**
   * Start the program.
   * @param args an unused set of string arguments.
   */

  public static void main(String[] args) {
    if (args.length == 0) {
      IMainController controller = new OldMainMenu(null, null, System.in);
      OngoingTransactionModel model = new OngoingTransactionModelImpl(
          controller.getOnlineDataFunc());
      controller.setModel(model);
      IFeatures features;
      features = controller.getFeatures();

      JFrameView.setDefaultLookAndFeelDecorated(false);
      JFrameView view = new JFrameView(features);
      controller.setView(view); //sets the view and features simultaneously
      view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      view.setVisible(true);

      try {
        // Attempt to set the Look and Feel to Nimbus if available
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
          if ("Nimbus".equals(info.getName())) {
            UIManager.setLookAndFeel(info.getClassName());
            break; // Successfully found and set Nimbus, so break out of the loop
          }
        }
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
      } catch (Exception e) {
        e.printStackTrace();
        // If Nimbus isn't found or any other issue arises,
        // you could opt for system default or cross-platform L&F
      }
    } else {
      IGraphingView view  = new GraphingTextView(System.out);
      IMainController controller = new OldMainMenu(null, view,System.in);
      controller.setModel(new OngoingTransactionModelImpl(controller.getOnlineDataFunc()));
      controller.execute();
    }
  }
}