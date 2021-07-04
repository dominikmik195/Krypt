package pmf.math.kalkulatori;

import pmf.math.algoritmi.TeorijaBrojeva;
import pmf.math.baza.dao.AfinaDAO;
import pmf.math.baza.tablice.AfinaPovijest;
import pmf.math.kriptosustavi.AfiniKriptosustav;
import pmf.math.obradaunosa.ObradaUnosa;
import pmf.math.router.Konzola;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AfinaKalkulator {
  public JPanel glavniPanel;

  private JSpinner aSpinner;
  private JSpinner bSpinner;
  private JButton ocistiButton;
  private JTextArea otvoreniTekstArea;
  private JTextArea sifratArea;
  private JButton sifrirajButton;
  private JButton desifrirajButton;
  private JLabel permutacijaLabel;
  private JPanel povijestPanel;

  private final Konzola konzola;

  private final AfinaDAO afinaDAO = new AfinaDAO();

  public AfinaKalkulator(Konzola konzola) {
    this.konzola = konzola;

    sifrirajButton.addActionListener(new Sifriraj());
    desifrirajButton.addActionListener(new Desifriraj());

    aSpinner.addChangeListener(e -> (new Permutacija()).azuriraj());
    bSpinner.addChangeListener(e -> (new Permutacija()).azuriraj());

    ocistiButton.addActionListener(
        e -> {
          otvoreniTekstArea.setText("");
          sifratArea.setText("");
          aSpinner.setValue(1);
          bSpinner.setValue(0);
        });

    aSpinner.setValue(1); // Identiteta.

    osvjeziPovijest();
  }

  private class Sifriraj implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      int a = (Integer) aSpinner.getValue() % 26;
      int b = (Integer) bSpinner.getValue() % 26;
      String otvoreniTekst = otvoreniTekstArea.getText();

      // Provjeri unose (zbog ispisa poruka).
      boolean greska = false;
      if (ObradaUnosa.kriviUnos(otvoreniTekst)) {
        konzola.ispisiGresku("Otvoreni tekst smije sadržavati samo slova engleske abecede!");
        greska = true;
      }
      if (!TeorijaBrojeva.relativnoProsti(a, 26)) {
        konzola.ispisiGresku("Uneseni broj a mora biti relativno prost s brojem 26!");
        greska = true;
      }

      String sifrat = (new AfiniKriptosustav(a, b)).sifriraj(otvoreniTekst);

      if (!greska) {
        sifratArea.setText(sifrat);
        konzola.ispisiPoruku("Poruka uspješno šifrirana afinom šifrom.");

        // Ispis i ažuriranje povijesti.
        afinaDAO.ubaciElement((Integer) aSpinner.getValue(), (Integer) bSpinner.getValue());
        osvjeziPovijest();
      }
    }
  }

  private class Desifriraj implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      int a = (Integer) aSpinner.getValue() % 26;
      int b = (Integer) bSpinner.getValue() % 26;
      String sifrat = sifratArea.getText();

      // Provjeri unose (zbog ispisa poruka).
      boolean greska = false;
      if (ObradaUnosa.kriviUnos(sifrat)) {
        konzola.ispisiGresku("Šifrat smije sadržavati samo slova engleske abecede!");
        greska = true;
      }
      if (!TeorijaBrojeva.relativnoProsti(a, 26)) {
        konzola.ispisiGresku("Uneseni broj a mora biti relativno prost s brojem 26!");
        greska = true;
      }

      String otvoreniTekst = (new AfiniKriptosustav(a, b)).desifriraj(sifrat);

      if (!greska) {
        otvoreniTekstArea.setText(otvoreniTekst);
        konzola.ispisiPoruku("Poruka uspješno dešifrirana afinom šifrom.");

        // Ispis i ažuriranje povijesti.
        afinaDAO.ubaciElement((Integer) aSpinner.getValue(), (Integer) bSpinner.getValue());
        osvjeziPovijest();
      }
    }
  }

  public JButton stvoriGumbPovijesti(AfinaPovijest povijest) {
    String tekst = "<html>a = " + povijest.getA() + "<br>b = " + povijest.getB() + "</html>";
    JButton noviGumb = new JButton(tekst);

    noviGumb.addActionListener(
        e -> {
          aSpinner.setValue(povijest.getA());
          bSpinner.setValue(povijest.getB());
        });

    return noviGumb;
  }

  public void osvjeziPovijest() {
    povijestPanel.removeAll();
    povijestPanel.setLayout(new GridLayout(1, 1));
    povijestPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
    afinaDAO.dohvatiElemente().forEach(element -> povijestPanel.add(stvoriGumbPovijesti(element)));
    povijestPanel.revalidate();
  }

  private class Permutacija {
    private void azuriraj() {
      int a = (Integer) aSpinner.getValue() % 26;
      int b = (Integer) bSpinner.getValue() % 26;

      if (TeorijaBrojeva.relativnoProsti(a, 26)) {
        String novaPermutacija = (new AfiniKriptosustav(a, b)).dohvatiPermutacijuString();
        permutacijaLabel.setText(novaPermutacija.replaceAll("([A-Z])", "$0 "));
      }
    }
  }
}
