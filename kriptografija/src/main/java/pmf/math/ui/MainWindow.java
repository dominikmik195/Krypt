package pmf.math.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.util.Locale;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import pmf.math.kriptosustavi.Cezar;
import pmf.math.kriptosustavi.CezarKljucnaRijec;

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
  private JSpinner pomakSpinner;
  private JCheckBox kljucCheckBox;
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
  private JPanel kalkulatorPanel;
  private JLabel permutacijaLabel;

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

    // TODO: Podesiti gumbe za switch šifre --- već napravljeno na grani elGamal.

    cezarSetUp();
  }

  private static void cezarSetUp() {
    myPanel.kalkulatorPanel.setVisible(true);
    // TODO: Sakrij sve ostale panele?
    // TODO: Hoće li raditi s drugim šiframa? Treba li svaka šifra imati svoj button ili treba
    // maknuti stare actionListenere?

    // -----------------------------------------------------------------------------------------------------------------
    // Šifriranje i dešifriranje.
    // -----------------------------------------------------------------------------------------------------------------
    myPanel.šifrirajButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            String otvoreniTekst = myPanel.otvoreniTekstArea.getText();
            // Počisti šifrat.
            // TODO: Što ako sadrži nedozvoljene znakove?
            otvoreniTekst = otvoreniTekst.replaceAll("\\s+", "").toUpperCase(Locale.ROOT);

            // Obična Cezarova šifra.
            if (!myPanel.kljucCheckBox.isSelected()) {
              int pomak = (Integer) myPanel.pomakSpinner.getValue() % 26;
              Cezar stroj = new Cezar(pomak);
              myPanel.šifratArea.setText(stroj.šifriraj(otvoreniTekst));
            }
            // Cezarova šifra s ključnom riječi.
            else {
              int pomak = (Integer) myPanel.pomakSpinner.getValue() % 26;
              // Dohvati i očisti ključnu riječ.
              String ključnaRiječ =
                  myPanel
                      .kljucnaRijecTextField
                      .getText()
                      .replaceAll("\\s+", "")
                      .toUpperCase(Locale.ROOT);
              // TODO: Pomak za sada glumi početak ključne riječi.
              CezarKljucnaRijec stroj = new CezarKljucnaRijec(ključnaRiječ, pomak);
              myPanel.šifratArea.setText(stroj.šifriraj(otvoreniTekst));
            }
          }
        });

    myPanel.dešifrirajButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            String šifrat = myPanel.šifratArea.getText();
            // Počisti šifrat.
            // TODO: Što ako sadrži nedozvoljene znakove?
            šifrat = šifrat.replaceAll("\\s+", "").toUpperCase(Locale.ROOT);

            // Obična Cezarova šifra.
            if (!myPanel.kljucCheckBox.isSelected()) {
              int pomak = (Integer) myPanel.pomakSpinner.getValue() % 26;
              Cezar stroj = new Cezar(pomak);
              myPanel.otvoreniTekstArea.setText(stroj.dešifriraj(šifrat));
            }
            // Cezarova šifra s ključnom riječi.
            else {
              int pomak = (Integer) myPanel.pomakSpinner.getValue() % 26;
              // Dohvati i očisti ključnu riječ.
              String ključnaRiječ =
                  myPanel
                      .kljucnaRijecTextField
                      .getText()
                      .replaceAll("\\s+", "")
                      .toUpperCase(Locale.ROOT);
              // TODO: Pomak za sada glumi početak ključne riječi.
              CezarKljucnaRijec stroj = new CezarKljucnaRijec(ključnaRiječ, pomak);
              myPanel.otvoreniTekstArea.setText(stroj.dešifriraj(šifrat));
            }
          }
        });

    // TODO: Dodati tekst u panele.

    // -----------------------------------------------------------------------------------------------------------------
    // Prikaz supstitucije u UI-ju.
    // -----------------------------------------------------------------------------------------------------------------
    // TODO: Trebalo bi se promijeniti kad se promijeni i ključna riječ.
    myPanel.pomakSpinner.addChangeListener(
        new ChangeListener() {
          @Override
          public void stateChanged(ChangeEvent e) {
            int pomak = (Integer) myPanel.pomakSpinner.getValue() % 26;
            // Obična Cezarova šifra.
            if (!myPanel.kljucCheckBox.isSelected()) {
              Cezar stroj = new Cezar(pomak);
              char[] permutacija = stroj.dohvatiPermutacijuSlova();
              StringBuilder noviLabel = new StringBuilder(permutacija.length);
              for (char slovo : permutacija) {
                noviLabel.append(slovo).append(" ");
              }
              myPanel.permutacijaLabel.setText(noviLabel.toString());
            }
            // Cezarova šifra s ključnom riječi.
            else {
              String ključnaRiječ =
                  myPanel
                      .kljucnaRijecTextField
                      .getText()
                      .replaceAll("\\s+", "")
                      .toUpperCase(Locale.ROOT);
              // TODO: Pomak za sada glumi početak ključne riječi.
              CezarKljucnaRijec stroj = new CezarKljucnaRijec(ključnaRiječ, pomak);
              char[] permutacija = stroj.dohvatiPermutacijuSlova();
              StringBuilder noviLabel = new StringBuilder(permutacija.length);
              for (char slovo : permutacija) {
                noviLabel.append(slovo).append(" ");
              }
              myPanel.permutacijaLabel.setText(noviLabel.toString());
            }
          }
        });
  }

  public void actionPerformed(ActionEvent ae) {
    String choice = ae.getActionCommand();
    if (choice.equals("Quit")) {
      System.exit(0);
    }
  }
}
