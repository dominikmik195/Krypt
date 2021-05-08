package pmf.math.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

public class MainWindow extends JPanel implements ActionListener {
  private static MainWindow myPanel;
  private static JFrame myFrame;

  private JPanel mainPanel;
  private JButton elGamalovaSifraButton;
  private JButton RSAButton;
  private JButton stupčanaTranspozicijaButton;
  private JButton playfairovaSifraButton;
  private JButton vigenerovaSifraButton;
  private JButton hillovaSifraButton;
  private JButton supstitucijskaSifraButton;
  private JButton cezarovaSifraButton;
  private JSpinner spinner1;
  private JCheckBox kljucCheckBox;
  private JProgressBar progressBar1;
  private JTextArea textArea2;
  private JTextPane opisTextPane;
  private JTextPane uputeTextPane;
  private JTextPane extraTextPane;
  private JTextArea textArea3;
  private JButton vButton;
  private JButton button2;
  private JTextField textField1;
  private JTextArea konzolaTextArea;

  public static void Main() {
    SwingUtilities.invokeLater(
        new Runnable() {
          public void run() {
            createAndShowGui();
          }
        });
  }

  public Dimension getPreferredSize() {
    return new Dimension(700, 350);
  }

  private static void createAndShowGui() {
    myFrame = new JFrame("Krypt");
    myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    myPanel = new MainWindow();
    myFrame.add(myPanel.mainPanel);
    myFrame.pack();
    myFrame.setLocationRelativeTo(null);
    myFrame.setVisible(true);
  }

  public void actionPerformed(ActionEvent ae) {
    String choice = ae.getActionCommand();
    if (choice.equals("Quit")) {
      System.exit(0);
    }
  }
}
