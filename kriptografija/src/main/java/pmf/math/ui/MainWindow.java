package pmf.math.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import pmf.math.algoritmi.TeorijaBrojeva;
import pmf.math.kriptosustavi.ElGamalKriptosustav;

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
  private JProgressBar progressBar1;
  private JTextArea otvoreniTekstArea;
  private JTextPane opisTextPane;
  private JTextPane uputeTextPane;
  private JTextPane extraTextPane;
  private JTextArea šifratArea;
  private JButton šifrirajButton;
  private JButton dešifrirajButton;
  private JTextField kljucnaRijecTextField;
  private JTextArea konzolaTextArea;
  private JPanel elGamalPanel;
  private JTextField tajniKljučField;
  private JTextField prostBrojField;
  private JTextField alfaField;
  private JTextField betaField;
  private JTextField tajniBrojField;
  private JPanel kalkulatorPanel;
  private JCheckBox alfaCheckBox;
  private JCheckBox betaCheckBox;
  private JLabel permutacijaLabel;
  private JSpinner pomakSpinner;
  private JCheckBox kljucCheckBox;

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
            new MainWindow().elGamalSetUp();
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

  private boolean provjereUnosa() {
    int pB = Integer.parseInt(myPanel.prostBrojField.getText());
    int tK = Integer.parseInt(myPanel.tajniKljučField.getText());
    int tB = Integer.parseInt(myPanel.tajniBrojField.getText());
    boolean OK = true;
    if (!TeorijaBrojeva.prost(pB)) {
      myPanel.konzolaTextArea.setText("U prvom polju treba biti prost broj!");
      OK = false;
    } else {
      if (myPanel.alfaCheckBox.isSelected()) {
        int _alfa = Integer.parseInt(myPanel.alfaField.getText());
        if (!ElGamalKriptosustav.provjeriAlfa(_alfa, pB)) {
          myPanel.konzolaTextArea.setText("Alfa je pogrešno postavljena!");
          OK = false;
        }
        if (myPanel.betaCheckBox.isSelected() && OK) {
          int _beta = Integer.parseInt(myPanel.betaField.getText());
          if (!ElGamalKriptosustav.provjeriBeta(_alfa, _beta, pB, tK)) {
            myPanel.konzolaTextArea.setText("Beta je pogrešno postavljen!");
            OK = false;
          }
        }
      }
    }
    return OK;
  }

  private ElGamalKriptosustav stvoriStroj() {
    int pB = Integer.parseInt(myPanel.prostBrojField.getText());
    int tK = Integer.parseInt(myPanel.tajniKljučField.getText());
    ElGamalKriptosustav stroj;
      if (myPanel.alfaCheckBox.isSelected()) {
        int _alfa = Integer.parseInt(myPanel.alfaField.getText());
        if (myPanel.betaCheckBox.isSelected()) {
          int _beta = Integer.parseInt(myPanel.betaField.getText());
          stroj = new ElGamalKriptosustav(pB, tK, _alfa, _beta);
        }
        else {
          stroj = new ElGamalKriptosustav(pB, tK, _alfa);
        }
      }
      else stroj = new ElGamalKriptosustav(pB, tK);
      return stroj;
  }

  private boolean provjeriŠifru(String šifra) {
    if(šifra.charAt(0) != '(' || šifra.charAt(šifra.length()-1) != ')') return false;
    String novi = šifra.substring(1, šifra.length() - 1);
    String[] lista = novi.split(",");
    if (lista.length != 2) return false;
    myPanel.konzolaTextArea.setText("");
    return true;
  }

  private void elGamalSetUp() {
    myPanel.elGamalPanel.setVisible(true);
    myPanel.šifrirajButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            try {
              int pB = Integer.parseInt(myPanel.prostBrojField.getText());
              int tK = Integer.parseInt(myPanel.tajniKljučField.getText());
              int tB = Integer.parseInt(myPanel.tajniBrojField.getText());
              int broj = Integer.parseInt(myPanel.otvoreniTekstArea.getText());
              if (provjereUnosa()) {
                ElGamalKriptosustav stroj = stvoriStroj();
                stroj.sifriraj(broj, tB);
                myPanel.šifratArea.setText(stroj.vratiSifrat());
                myPanel.konzolaTextArea.setText("");
              }
            }
            catch (NumberFormatException ex) {
              myPanel.konzolaTextArea.setText("Unos mora biti broj!");
            }
          }
        });
    myPanel.dešifrirajButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            try {
              int pB = Integer.parseInt(myPanel.prostBrojField.getText());
              int tK = Integer.parseInt(myPanel.tajniKljučField.getText());

              String šifra = myPanel.šifratArea.getText().strip();
              boolean OK = provjeriŠifru(šifra);
              if(!OK) {
                myPanel.konzolaTextArea.setText("Krivi format šifre!");
                return;
              }
              if (provjereUnosa()) {
                ElGamalKriptosustav stroj = stvoriStroj();
                stroj.pohraniSifrat(šifra);
                //stroj.desifriraj();
                myPanel.otvoreniTekstArea.setText(String.valueOf(stroj.desifriraj()));
                myPanel.konzolaTextArea.setText("");
              }
            }
            catch(StringIndexOutOfBoundsException | NumberFormatException ex) {
              myPanel.konzolaTextArea.setText("Krivi format šifre!");
            }
          }
        });
    myPanel.uputeTextPane.setText(
        "Za šifriranje je dovoljno unijeti prost broj, "
            + "tajni ključ, tajni broj i otvoreni tekst - svugdje po JEDAN broj. "
                + "U tom slučaju će se alfa i beta izračunati automatski. "
                + "Ukoliko želite ručno unijeti alfa ili beta, označite odgovarajuću kućicu i unesite željeni broj"
                + ", ali - pazite: za alfa i beta vrijede posebni uvjeti! "
                + "Osim toga,možete unijeti samo alfu ili i alfu i betu, no ne možete unijeti samo betu! "
            + "Za dešifriranje je dovoljno unijeti prost broj, tajni ključ i šifrat u formatu:\n"
            + "     (broj1, broj2)\n Za alfu i betu vrijede ista pravila kao i u šifriranju. "
            + "Za pokretanje postupka, pritisnuti odgovarajuću strjelicu.");
    myPanel.opisTextPane.setText("Nekakav opis ElGamalovog sustava...");
  }

  public void actionPerformed(ActionEvent ae) {
    String choice = ae.getActionCommand();
    if (choice.equals("Quit")) {
      System.exit(0);
    }
  }
}
