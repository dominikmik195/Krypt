package pmf.math.kalkulatori;

import pmf.math.baza.dao.CezarDAO;
import pmf.math.baza.tablice.CezarPovijest;
import pmf.math.kriptosustavi.CezarKljucnaRijecKriptosustav;
import pmf.math.kriptosustavi.CezarKriptosustav;
import pmf.math.obradaunosa.ObradaUnosa;
import pmf.math.router.Konzola;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;

public class CezarKalkulator extends JPanel {
  private final Konzola konzola; // Za ispis greške.
  private final CezarDAO cezarDAO = new CezarDAO();
  public JPanel kalkulatorPanel;
  private JSpinner pomakSpinner;
  private JCheckBox kljucCheckBox;
  private JTextArea otvoreniTekstArea;
  private JTextArea sifratArea;
  private JButton sifrirajButton;
  private JButton desifrirajButton;
  private JTextField kljucnaRijecTextField;
  private JLabel permutacijaLabel;
  private JButton desnoButton;
  private JButton lijevoButton;
  private JLabel pomakLabel;
  private JLabel kljucLabel;
  private JButton odaberiButton;
  private int indeksPovijest = 0;

  public CezarKalkulator(Konzola konzola) {
    this.konzola = konzola;
    SwingUtilities.invokeLater(() -> {
      prikaziTrenutni(); // Povijest.

      sifrirajButton.addActionListener(new Sifriraj());
      desifrirajButton.addActionListener(new Desifriraj());

      // O(ne)mogućavanje unosa ključne riječi.
      kljucCheckBox.addItemListener(
          e -> kljucnaRijecTextField.setEditable(e.getStateChange() != ItemEvent.DESELECTED));

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

      // Buttoni za povijest.
      odaberiButton.addActionListener(
          e -> {
            if (pomakLabel.getText().equals("-")) return;

            int pomak = Integer.parseInt(pomakLabel.getText());
            String kljucnaRijec = kljucLabel.getText();

            pomakSpinner.setValue(pomak);
            if (!kljucnaRijec.equals("-")) {
              kljucnaRijecTextField.setText(kljucnaRijec);
              kljucCheckBox.setSelected(true);
            } else {
              kljucnaRijecTextField.setText("");
              kljucCheckBox.setSelected(false);
            }
          });

      lijevoButton.addActionListener(
          e -> {
            if (cezarDAO.brojElemenata() == 0) return;

            indeksPovijest = (indeksPovijest - 1 + cezarDAO.brojElemenata) % cezarDAO.brojElemenata;
            prikaziTrenutni();
          });

      desnoButton.addActionListener(
          e -> {
            if (cezarDAO.brojElemenata() == 0) return;

            indeksPovijest = (indeksPovijest + 1) % cezarDAO.brojElemenata;
            prikaziTrenutni();
          });
    });
  }

  private void provjeriGumbe() {
    if (cezarDAO.brojElemenata() == 0) {
      lijevoButton.setEnabled(false);
      desnoButton.setEnabled(true);
      odaberiButton.setEnabled(false);
      return;
    }

    lijevoButton.setEnabled(indeksPovijest != 0);
    desnoButton.setEnabled(indeksPovijest != (cezarDAO.brojElemenata() - 1));
    odaberiButton.setEnabled(true);
  }

  private void prikaziTrenutni() {
    if (cezarDAO.brojElemenata() == 0) return;

    CezarPovijest trenutni = cezarDAO.dohvatiElement(indeksPovijest);
    if (trenutni != null) {
      pomakLabel.setText(String.valueOf(trenutni.getPomak()));
      kljucLabel.setText(trenutni.getKljucnaRijec());
    }
    provjeriGumbe();
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
      if (!kljucCheckBox.isSelected())
        sifrat = (new CezarKriptosustav(pomak)).sifriraj(otvoreniTekst);
      else
        sifrat = (new CezarKljucnaRijecKriptosustav(kljucnaRijec, pomak)).sifriraj(otvoreniTekst);

      if (!greska) {
        sifratArea.setText(sifrat);
        konzola.ispisiPoruku("Poruka uspješno šifrirana.");

        cezarDAO.ubaciElement(pomak, kljucCheckBox.isSelected() ? kljucnaRijec : "-");
        indeksPovijest = 0; // Resetiraj prikaz.
        prikaziTrenutni();
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
      if (!kljucCheckBox.isSelected())
        otvoreniTekst = (new CezarKriptosustav(pomak)).desifriraj(sifrat);
      else
        otvoreniTekst = (new CezarKljucnaRijecKriptosustav(kljucnaRijec, pomak)).desifriraj(sifrat);

      if (!greska) {
        otvoreniTekstArea.setText(otvoreniTekst);
        konzola.ispisiPoruku("Poruka uspješno dešifrirana.");

        cezarDAO.ubaciElement(pomak, kljucCheckBox.isSelected() ? kljucnaRijec : "-");
        indeksPovijest = (indeksPovijest + 1) % cezarDAO.brojElemenata;
        indeksPovijest = 0; // Resetiraj prikaz.
        prikaziTrenutni();
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
      else {
        if (!kljucCheckBox.isSelected())
          novaPermutacija = (new CezarKriptosustav(pomak)).dohvatiPermutacijuString();
        else
          novaPermutacija =
              (new CezarKljucnaRijecKriptosustav(kljucnaRijec, pomak)).dohvatiPermutacijuString();

        permutacijaLabel.setText(novaPermutacija.replaceAll("([A-Z])", "$0 "));
      }
    }
  }
}
