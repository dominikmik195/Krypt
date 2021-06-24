package pmf.math.kalkulatori;

import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;
import java.util.Locale;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import pmf.math.baza.dao.VigenereDAO;
import pmf.math.baza.tablice.VigenerePovijest;
import pmf.math.kriptosustavi.VigenereKriptosustav;
import pmf.math.router.Konzola;

public class VigenereKalkulator {

  private static final VigenereKriptosustav vigenereKriptosustav = new VigenereKriptosustav();

  public JPanel glavniPanel;
  public Konzola mojaKonzola;
  private final VigenereDAO vigenereDAO = new VigenereDAO();

  private JTextArea sifratTextArea;
  private JTextArea otvoreniTekstTextArea;
  private JButton sifrirajButton;
  private JButton desifrirajButton;
  private JTextField kljucTextField;
  private JComboBox<String> favoritiComboBox;
  private JLabel kljucLabel;
  private JLabel otvoreniTekstLabel;
  private JLabel sifratLabel;
  private JPanel tipkePanel;
  private JPanel otvorenitekstPanel;
  private JPanel kljucPanel;
  private JPanel unosPanel;
  private JRadioButton ponavljajuciKljucRadioButton;
  private JRadioButton vigenereovKvadratRadioButton;
  private JPanel nacinSifriranjaPanel;
  private JButton pronađiKljučButton;
  private JButton odustaniButton;
  private JProgressBar progressBar1;

  public enum NacinSifriranja {
    PONAVLJAJUCI,
    VIGENEREOV_KVADRAT
  }

  public VigenereKalkulator(Konzola konzola) {
    mojaKonzola = konzola;
    postaviTipke();
    postaviRubove();
    osvjeziFavorite();
  }

  private void postaviTipke() {
    // Unos ključa
    kljucTextField.addFocusListener(new FocusListener() {
      @Override
      public void focusGained(FocusEvent e) {
        // ništa
      }

      @Override
      public void focusLost(FocusEvent e) {
        String kljuc = kljucTextField.getText();
        kljucTextField.setText(kljuc.toUpperCase(Locale.ROOT).replaceAll("[^A-Z]", ""));
        sanitizirajTekst(otvoreniTekstTextArea);
        sanitizirajTekst(sifratTextArea);
      }
    });

    // Način šifriranja
    ButtonGroup nacinSifriranjaButtonGroup = new ButtonGroup();
    nacinSifriranjaButtonGroup.add(ponavljajuciKljucRadioButton);
    nacinSifriranjaButtonGroup.add(vigenereovKvadratRadioButton);

    ponavljajuciKljucRadioButton.addActionListener(e -> {
      sanitizirajTekst(otvoreniTekstTextArea);
      sanitizirajTekst(sifratTextArea);
    });
    vigenereovKvadratRadioButton.addActionListener(e -> {
      sanitizirajTekst(otvoreniTekstTextArea);
      sanitizirajTekst(sifratTextArea);
    });

    // Početna tablica
    ponavljajuciKljucRadioButton.setSelected(true);

    // Šifriraj / Dešifriraj
    sifrirajButton.addActionListener(e -> {
      if (otvoreniTekstTextArea.getText().equals("") || kljucTextField.getText().equals("")) {
        return;
      }
      if (ponavljajuciKljucRadioButton.isSelected() &&
          otvoreniTekstTextArea.getText().replaceAll(" ", "")
              .length() % kljucTextField.getText().length() != 0) {
        mojaKonzola.ispisiGresku("OPREZ! Unos mora biti višekratnik duljine ključa.");
        return;
      }
      try {
        NacinSifriranja nacin = ponavljajuciKljucRadioButton.isSelected() ?
            NacinSifriranja.PONAVLJAJUCI : NacinSifriranja.VIGENEREOV_KVADRAT;
        sifratTextArea.setText(vigenereKriptosustav
            .sifriraj(otvoreniTekstTextArea.getText(), kljucTextField.getText(), nacin));
        vigenereDAO.ubaciElement(kljucTextField.getText());
        osvjeziFavorite();
        mojaKonzola.ispisiPoruku("Šifriranje dovršeno.");
      } catch (Exception exception) {
        mojaKonzola.ispisiGresku("Pogreška pri šifriranju.");
      }
    });
    desifrirajButton.addActionListener(e -> {
      if (sifratTextArea.getText().equals("") || kljucTextField.getText().equals("")) {
        return;
      }
      if (ponavljajuciKljucRadioButton.isSelected() &&
          sifratTextArea.getText().replaceAll(" ", "")
              .length() % kljucTextField.getText().length() != 0) {
        mojaKonzola.ispisiGresku("OPREZ! Unos mora biti višekratnik duljine ključa.");
        return;
      }
      try {
        NacinSifriranja nacin = ponavljajuciKljucRadioButton.isSelected() ?
            NacinSifriranja.PONAVLJAJUCI : NacinSifriranja.VIGENEREOV_KVADRAT;
        otvoreniTekstTextArea.setText(
            vigenereKriptosustav
                .desifriraj(sifratTextArea.getText(), kljucTextField.getText(), nacin));
        vigenereDAO.ubaciElement(kljucTextField.getText());
        osvjeziFavorite();
        mojaKonzola.ispisiPoruku("Dešifriranje dovršeno.");
      } catch (Exception exception) {
        mojaKonzola.ispisiGresku("Pogreška pri dešifriranju.");
      }
    });

    // Sanitizacija unosa
    otvoreniTekstTextArea.addFocusListener(new FocusListener() {
      @Override
      public void focusGained(FocusEvent e) {
        // ništa
      }

      @Override
      public void focusLost(FocusEvent e) {
        sanitizirajTekst(otvoreniTekstTextArea);
      }
    });

    sifratTextArea.addFocusListener(new FocusListener() {
      @Override
      public void focusGained(FocusEvent e) {
        // ništa
      }

      @Override
      public void focusLost(FocusEvent e) {
        sanitizirajTekst(sifratTextArea);
      }
    });

    // Postavi favorite
    favoritiComboBox.addActionListener(e -> {
      if (favoritiComboBox.getSelectedItem() == null) {
        return;
      }
      String kljuc = String.valueOf(favoritiComboBox.getSelectedItem());
      if (!kljucTextField.getText().equals(kljuc)) {
        kljucTextField.setText(kljuc);
      }
    });
  }

  public void osvjeziFavorite() {
    favoritiComboBox.removeAllItems();
    if (vigenereDAO.brojElemenata() == 0) {
      favoritiComboBox.setEnabled(false);
      return;
    } else {
      favoritiComboBox.setEnabled(true);
    }
    List<VigenerePovijest> favoriti = vigenereDAO.dohvatiElemente();
    String[] favoritiArray = new String[favoriti.size()];
    for (int i = 0; i < favoriti.size(); i++) {
      favoritiArray[i] = favoriti.get(i).getKljuc();
    }
    favoritiComboBox.setModel(new DefaultComboBoxModel<>(favoritiArray));
  }

  public void sanitizirajTekst(JTextArea textArea) {
    String tekst = textArea.getText().toUpperCase(Locale.ROOT).replaceAll("[^A-Z]", "");
    int m = kljucTextField.getText().length();
    if (m == 0 || vigenereovKvadratRadioButton.isSelected()) {
      textArea.setText(tekst);
      return;
    }
    StringBuilder izlaz = new StringBuilder();

    for (int i = 0; i < tekst.length(); i += m) {
      izlaz.append(tekst, i, Math.min(i + m, tekst.length()))
          .append(i + m < tekst.length() ? " " : "");
    }
    if (!izlaz.toString().equals(textArea.getText())
        && textArea.getText().replaceAll(" ", "").length() % m != 0) {
      mojaKonzola
          .ispisiGresku("OPREZ! Unos mora biti višekratnik duljine ključa.");
    }

    textArea.setText(izlaz.toString());
  }

  public void postaviRubove() {
    otvoreniTekstTextArea.setMargin(new Insets(10, 10, 10, 10));
    sifratTextArea.setMargin(new Insets(10, 10, 10, 10));
  }

}
