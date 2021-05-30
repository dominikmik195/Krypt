package pmf.math.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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

    // TODO: Podesiti gumbe za switch šifre --- već napravljeno na grani elGamal, čeka merge.

    cezarSetUp();
  }

  // TODO: Ovo treba refaktorizirati kad se malo sredi kod za UI.
  private static void cezarSetUp() {
    myPanel.kalkulatorPanel.setVisible(true);
    // TODO: Sakrij sve ostale panele?
    // FIXME: Hoće li raditi s drugim šiframa? Treba li svaka šifra imati svoj button ili treba
    // maknuti stare actionListenere?

    // -----------------------------------------------------------------------------------------------------------------
    // Tekst u lijevom stupcu.
    // -----------------------------------------------------------------------------------------------------------------
    myPanel.uputeTextPane.setText(
        "Za šifriranje pomoću Cezarove šifre dovoljno je unijeti pomak (broj).\n"
            + "Za šifriranje pomoću Cezarove šifre s ključnom riječi, treba označiti "
            + "odgovarajuću kućicu te unijeti ključnu riječ i pomak (broj), koji označava "
            + "mjesto od koje ključna riječ počinje u abecedi šifrata.\n"
            + "Sav uneseni tekst smije sadržavati isključivo slova engleske abecede.\n"
            + "Za pokretanje postupka, pritisnuti odgovarajuću strjelicu.");

    myPanel.opisTextPane.setText(
        "U kriptografiji, Cezarova šifra jedan je od najjednostavnijih"
            + " i najrasprostranjenijih načina šifriranja. To je tip šifre zamjene (supstitucije),"
            + " u kome se svako slovo otvorenog teksta zamjenjuje odgovarajućim slovom abecede, "
            + "pomaknutim za određeni broj mjesta. Na primjer, s pomakom 3, A se zamjenjuje slovom D, "
            + "B slovom E itd. Ova metoda je dobila ime po Juliju Cezaru, koji ju je koristio za "
            + "razmjenu poruka sa svojim generalima. ");

    // -----------------------------------------------------------------------------------------------------------------
    // Šifriranje i dešifriranje.
    // -----------------------------------------------------------------------------------------------------------------
    myPanel.šifrirajButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            String otvoreniTekst = myPanel.otvoreniTekstArea.getText();
            // Počisti otvoreni tekst.
            otvoreniTekst = otvoreniTekst.replaceAll("\\s+", "").toUpperCase(Locale.ROOT);
            // Nedozvoljeni znakovi.
            if (!otvoreniTekst.matches("^[A-Z]*$")) {
              myPanel.konzolaTextArea.append(
                  "\nŠifrat smije sadržavati samo slova engleske abecede!\n");
              return;
            }

            // Obična Cezarova šifra.
            if (!myPanel.kljucCheckBox.isSelected()) {
              int pomak = (Integer) myPanel.pomakSpinner.getValue() % 26;
              Cezar stroj = new Cezar(pomak);
              myPanel.šifratArea.setText(stroj.sifriraj(otvoreniTekst));
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
              // Pomak određuje početak ključne riječi.
              CezarKljucnaRijec stroj = new CezarKljucnaRijec(ključnaRiječ, pomak);
              myPanel.šifratArea.setText(stroj.sifriraj(otvoreniTekst));
            }
          }
        });

    myPanel.dešifrirajButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            String šifrat = myPanel.šifratArea.getText();
            // Počisti šifrat.
            šifrat = šifrat.replaceAll("\\s+", "").toUpperCase(Locale.ROOT);
            // Nedozvoljeni znakovi u šifratu.
            if (!šifrat.matches("^[A-Z]*$")) {
              myPanel.konzolaTextArea.append(
                  "\nŠifrat smije sadržavati samo slova engleske abecede!\n");
              return;
            }

            // Obična Cezarova šifra.
            if (!myPanel.kljucCheckBox.isSelected()) {
              int pomak = (Integer) myPanel.pomakSpinner.getValue() % 26;
              Cezar stroj = new Cezar(pomak);
              myPanel.otvoreniTekstArea.setText(stroj.desifriraj(šifrat));
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
              // Nedozvoljeni znakovi u ključnoj riječi.
              if (!ključnaRiječ.matches("^[A-Z]*$")) {
                myPanel.konzolaTextArea.append(
                    "\nKljučna riječ smije sadržavati samo slova engleske abecede!\n");
                return;
              }
              // Pomak određuje početak ključne riječi.
              CezarKljucnaRijec stroj = new CezarKljucnaRijec(ključnaRiječ, pomak);
              myPanel.otvoreniTekstArea.setText(stroj.desifriraj(šifrat));
            }
          }
        });

    // TODO: Dodati tekst u panele.

    // -----------------------------------------------------------------------------------------------------------------
    // Prikaz supstitucije u UI-ju.
    // -----------------------------------------------------------------------------------------------------------------
    myPanel.pomakSpinner.addChangeListener(
        new ChangeListener() {
          @Override
          public void stateChanged(ChangeEvent e) {
            azurirajPermutacijaLabel();
          }
        });

    myPanel.kljucCheckBox.addChangeListener(
        new ChangeListener() {
          @Override
          public void stateChanged(ChangeEvent e) {
            azurirajPermutacijaLabel();
          }
        });

    myPanel
        .kljucnaRijecTextField
        .getDocument()
        .addDocumentListener(
            new DocumentListener() {
              private void update(DocumentEvent e) {
                azurirajPermutacijaLabel();
              }

              @Override
              public void insertUpdate(DocumentEvent e) {
                update(e);
              }

              @Override
              public void removeUpdate(DocumentEvent e) {
                update(e);
              }

              @Override
              public void changedUpdate(DocumentEvent e) {
                update(e);
              }
            });
  }

  private static void azurirajPermutacijaLabel() {
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
      String ključnaRiječ = myPanel.kljucnaRijecTextField.getText();
      // Nedozvoljeni znakovi u ključnoj riječi.
      if (!ključnaRiječ.matches("^[a-zA-Z]*$")) {
        myPanel.konzolaTextArea.append(
            "\nKljučna riječ smije sadržavati samo slova engleske abecede!\n");
        return;
      }
      ključnaRiječ = ključnaRiječ.replaceAll("\\s+", "").toUpperCase(Locale.ROOT);
      // Pomak određuje početak ključne riječi.
      CezarKljucnaRijec stroj = new CezarKljucnaRijec(ključnaRiječ, pomak);
      char[] permutacija = stroj.dohvatiPermutacijuSlova();
      StringBuilder noviLabel = new StringBuilder(permutacija.length);
      for (char slovo : permutacija) {
        noviLabel.append(slovo).append(" ");
      }
      myPanel.permutacijaLabel.setText(noviLabel.toString());
    }
  }

  public void actionPerformed(ActionEvent ae) {
    String choice = ae.getActionCommand();
    if (choice.equals("Quit")) {
      System.exit(0);
    }
  }
}
