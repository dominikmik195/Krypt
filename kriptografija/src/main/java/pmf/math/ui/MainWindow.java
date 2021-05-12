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

import pmf.math.kriptosustavi.ElGamal;

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
  private JPanel elGamalPanel;
  private JTextArea otvoreniTekstArea;
  private JTextArea šifratArea;
  private JButton šifrirajButton;
  private JButton dešifrirajButton;
  private JTextField tajniKljučField;
  private JTextField prostBrojField;
  private JTextField alfaField;
  private JTextField betaField;
  private JTextField tajniBrojField;
  private JPanel kalkulatorPanel;

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
    myPanel.elGamalovaSifraButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            myPanel.kalkulatorPanel.setVisible(false);
            elGamalSetUp();
            myPanel.elGamalovaSifraButton.setEnabled(false);
            myPanel.cezarovaSifraButton.setEnabled(true);
          }
        });
    myPanel.cezarovaSifraButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            myPanel.kalkulatorPanel.setVisible(true);
            myPanel.elGamalPanel.setVisible(false);
            myPanel.elGamalovaSifraButton.setEnabled(true);
            myPanel.cezarovaSifraButton.setEnabled(false);
          }
        });
  }

  private static void elGamalSetUp() {
    myPanel.elGamalPanel.setVisible(true);
    myPanel.šifrirajButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            int pB = Integer.parseInt(myPanel.prostBrojField.getText());
            int tK = Integer.parseInt(myPanel.tajniKljučField.getText());
            int tB = Integer.parseInt(myPanel.tajniBrojField.getText());
            int broj = Integer.parseInt(myPanel.otvoreniTekstArea.getText());
            ElGamal stroj = new ElGamal(pB, tK);
            stroj.šifriraj(broj, tB);
            myPanel.šifratArea.setText(stroj.vratiŠifrat());
          }
        });
    myPanel.dešifrirajButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            int pB = Integer.parseInt(myPanel.prostBrojField.getText());
            int tK = Integer.parseInt(myPanel.tajniKljučField.getText());
            String šifra = myPanel.šifratArea.getText().strip();
            ElGamal stroj = new ElGamal(pB, tK);
            stroj.pohraniŠifrat(šifra);
            stroj.dešifriraj();
            myPanel.otvoreniTekstArea.setText(String.valueOf(stroj.dešifriraj()));
          }
        });
    myPanel.uputeTextPane.setText(
        "Za šifriranje je dovoljno unijeti prost broj, "
            + "tajni ključ, tajni broj i otvoreni tekst - svugdje po JEDAN broj."
            + "Za dešifriranje je dovoljno unijeti prost broj, tajni ključ i šifrat u formatu:\n"
            + "     (broj1, broj2)\n Ostale funkcionalnosti bit će dodane naknadno."
            + "Za pokretanje postupka, pritisnuti odgovarajuću strjelicu.");
  }

  public void actionPerformed(ActionEvent ae) {
    String choice = ae.getActionCommand();
    if (choice.equals("Quit")) {
      System.exit(0);
    }
  }
}
