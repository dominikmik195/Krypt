package pmf.math.kalkulatori;

import pmf.math.obradaunosa.ObradaUnosa;
import pmf.math.obradaunosa.ObradaUnosaCezar;
import pmf.math.obradaunosa.ObradaUnosaCezarKljucnaRijec;
import pmf.math.router.Konzola;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CezarKalkulator extends JPanel {
  public JPanel kalkulatorPanel;

  private JSpinner pomakSpinner;
  private JCheckBox kljucCheckBox;
  private JTextArea otvoreniTekstArea;
  private JTextArea sifratArea;
  private JButton sifrirajButton;
  private JButton desifrirajButton;
  private JTextField kljucnaRijecTextField;
  private JLabel permutacijaLabel;

  private final Konzola konzola; // Za ispis greške.

  public CezarKalkulator(Konzola konzola) {
    this.konzola = konzola;

    sifrirajButton.addActionListener(new Sifriraj());
    desifrirajButton.addActionListener(new Desifriraj());

    // -----------------------------------------------------------------------------------------------------------------
    // Prikaz supstitucije u UI-ju.
    pomakSpinner.addChangeListener(e -> (new Permutacija()).azuriraj());
    kljucCheckBox.addItemListener(e -> (new Permutacija()).azuriraj());
    kljucnaRijecTextField
        .getDocument()
        .addDocumentListener(
            new DocumentListener() {
              private void update() {
                (new Permutacija()).azuriraj();
              }

              @Override
              public void insertUpdate(DocumentEvent e) {
                update();
              }

              @Override
              public void removeUpdate(DocumentEvent e) {
                update();
              }

              @Override
              public void changedUpdate(DocumentEvent e) {
                update();
              }
            });
  }

  // -----------------------------------------------------------------------------------------------------------------
  // Listeneri.
  private class Sifriraj implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      int pomak = (Integer) pomakSpinner.getValue() % 26;
      String otvoreniTekst = otvoreniTekstArea.getText();
      String kljucnaRijec = kljucnaRijecTextField.getText();

      // Provjeri unose (zbog ispisa poruka).
      boolean greska = false;
      if (ObradaUnosa.kriviUnos(otvoreniTekst)) {
        konzola.ispisiGresku("Otvoreni tekst smije sadržavati samo slova engleske abecede!");
        greska = true;
      }
      if (ObradaUnosa.kriviUnos(kljucnaRijec) && kljucCheckBox.isSelected()) {
        konzola.ispisiGresku("Ključna riječ smije sadržavati samo slova engleske abecede!");
        greska = true;
      }

      String sifrat;
      if (!kljucCheckBox.isSelected()) sifrat = ObradaUnosaCezar.sifriraj(pomak, otvoreniTekst);
      else sifrat = ObradaUnosaCezarKljucnaRijec.sifriraj(pomak, kljucnaRijec, otvoreniTekst);

      if (!greska) {
        sifratArea.setText(sifrat);
        konzola.ispisiPoruku("Poruka uspješno šifrirana Cezarovom šifrom.");
      }
    }
  }

  private class Desifriraj implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      int pomak = (Integer) pomakSpinner.getValue() % 26;
      String sifrat = sifratArea.getText();
      String kljucnaRijec = kljucnaRijecTextField.getText();

      // Provjeri unose (zbog ispisa poruka).
      boolean greska = false;
      if (ObradaUnosa.kriviUnos(sifrat)) {
        konzola.ispisiGresku("Šifrat smije sadržavati samo slova engleske abecede!");
        greska = true;
      }
      if (ObradaUnosa.kriviUnos(kljucnaRijec) && kljucCheckBox.isSelected()) {
        konzola.ispisiGresku("Ključna riječ smije sadržavati samo slova engleske abecede!");
        greska = true;
      }

      String otvoreniTekst;
      if (!kljucCheckBox.isSelected()) otvoreniTekst = ObradaUnosaCezar.desifriraj(pomak, sifrat);
      else otvoreniTekst = ObradaUnosaCezarKljucnaRijec.desifriraj(pomak, kljucnaRijec, sifrat);

      if (!greska) {
        otvoreniTekstArea.setText(otvoreniTekst);
        konzola.ispisiPoruku("Poruka uspješno dešifrirana Cezarovom šifrom.");
      }
    }
  }
  // Za prikaz supstitucije u UI-ju.
  private class Permutacija {
    private void azuriraj() {
      int pomak = (Integer) pomakSpinner.getValue() % 26;
      String kljucnaRijec = kljucnaRijecTextField.getText();

      String novaPermutacija;

      if (ObradaUnosa.kriviUnos(kljucnaRijec) && kljucCheckBox.isSelected())
        konzola.ispisiGresku("Ključna riječ smije sadržavati samo slova engleske abecede!");

      if (!kljucCheckBox.isSelected()) novaPermutacija = ObradaUnosaCezar.permutacija(pomak);
      else novaPermutacija = ObradaUnosaCezarKljucnaRijec.permutacija(pomak, kljucnaRijec);

      permutacijaLabel.setText(novaPermutacija);
    }
  }
}

// -----------------------------------------------------------------------------------------------------------------
// Tekst u lijevom stupcu.
// -----------------------------------------------------------------------------------------------------------------
/*
String upute =
        "Za šifriranje pomoću Cezarove šifre dovoljno je unijeti pomak (broj).\n"
                + "Za šifriranje pomoću Cezarove šifre s ključnom riječi, treba označiti "
                + "odgovarajuću kućicu te unijeti ključnu riječ i pomak (broj), koji označava "
                + "mjesto od koje ključna riječ počinje u abecedi šifrata.\n"
                + "Sav uneseni tekst smije sadržavati isključivo slova engleske abecede.\n"
                + "Za pokretanje postupka, pritisnuti odgovarajuću strjelicu."

String opis =
        "U kriptografiji, Cezarova šifra jedan je od najjednostavnijih"
                + " i najrasprostranjenijih načina šifriranja. To je tip šifre zamjene (supstitucije),"
                + " u kome se svako slovo otvorenog teksta zamjenjuje odgovarajućim slovom abecede, "
                + "pomaknutim za određeni broj mjesta. Na primjer, s pomakom 3, A se zamjenjuje slovom D, "
                + "B slovom E itd. Ova metoda je dobila ime po Juliju Cezaru, koji ju je koristio za "
                + "razmjenu poruka sa svojim generalima. "
*/
