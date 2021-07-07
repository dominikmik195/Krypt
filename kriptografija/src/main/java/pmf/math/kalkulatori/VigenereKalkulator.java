package pmf.math.kalkulatori;

import static pmf.math.algoritmi.Abeceda.filtrirajTekst;

import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;
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
import javax.swing.SwingUtilities;
import pmf.math.baza.dao.VigenereDAO;
import pmf.math.baza.tablice.VigenerePovijest;
import pmf.math.filteri.VigenereFilter;
import pmf.math.kriptosustavi.VigenereKriptosustav;
import pmf.math.kriptosustavi.VigenereKriptosustav.Jezik;
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
  private JButton pronadiKljucButton;
  private JButton odustaniButton;
  private JProgressBar kljucProgressBar;
  private JComboBox jezikComboBox;

  public enum NacinSifriranja {
    PONAVLJAJUCI,
    VIGENEREOV_KVADRAT
  }

  public VigenereKalkulator(Konzola konzola) {
    mojaKonzola = konzola;
    postaviTipke();
    postaviRubove();
    osvjeziFavorite();
    omoguciSucelje();
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
        kljucTextField.setText(filtrirajTekst(kljuc));
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
        if(sifratTextArea.getText().length() > kljucTextField.getText().length()) {
          pronadiKljucButton.setEnabled(true);
          jezikComboBox.setEnabled(true);
        }
        else {
          pronadiKljucButton.setEnabled(false);
          jezikComboBox.setEnabled(false);
        }
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

    // Računanje ključa
    pronadiKljucButton.addActionListener(e -> {
      new Thread(() -> {
        onemoguciSucelje();
        vigenereKriptosustav.setExitThread(false);
        Jezik jezik = Jezik.values()[jezikComboBox.getSelectedIndex()];
        final int duljina = vigenereKriptosustav.pronadiDuljinuKljuca(
            jezik, sifratTextArea.getText());
        vigenereKriptosustav.setExitThread(true);

        SwingUtilities.invokeLater(() -> {
          if (duljina != 0) {
            mojaKonzola.ispisiPoruku(
                "Vjerojatna duljina ključa za dani šifrat je " + duljina + ".");
          } else {
            mojaKonzola.ispisiGresku("Neuspješno računanje duljine ključa.");
          }
          omoguciSucelje();
        });
      }).start();

      new Thread(() -> {
        while (kljucProgressBar.isVisible()) {
          SwingUtilities.invokeLater(() -> {
            kljucProgressBar.setValue(vigenereKriptosustav.getNapredak());
          });
        }
      }).start();
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
    String tekst = filtrirajTekst(textArea.getText());
    NacinSifriranja nacinSifriranja = vigenereovKvadratRadioButton.isSelected() ?
        NacinSifriranja.VIGENEREOV_KVADRAT : NacinSifriranja.PONAVLJAJUCI;
    int m = kljucTextField.getText().length();

    String izlaz = VigenereFilter.filtriraj(tekst, m, nacinSifriranja);

    if (m != 0 && !izlaz.equals(textArea.getText())
        && textArea.getText().replaceAll(" ", "").length() % m != 0) {
      mojaKonzola
          .ispisiGresku("OPREZ! Unos mora biti višekratnik duljine ključa.");
    }

    textArea.setText(izlaz);
  }

  public void postaviRubove() {
    otvoreniTekstTextArea.setMargin(new Insets(10, 10, 10, 10));
    sifratTextArea.setMargin(new Insets(10, 10, 10, 10));
  }

  public void onemoguciSucelje() {
    kljucTextField.setEnabled(false);
    otvoreniTekstTextArea.setEnabled(false);
    sifratTextArea.setEnabled(false);
    sifrirajButton.setEnabled(false);
    desifrirajButton.setEnabled(false);
    favoritiComboBox.setEnabled(false);

    odustaniButton.setEnabled(true);
    kljucProgressBar.setVisible(true);
  }

  public void omoguciSucelje() {
    kljucTextField.setEnabled(true);
    otvoreniTekstTextArea.setEnabled(true);
    sifratTextArea.setEnabled(true);
    sifrirajButton.setEnabled(true);
    desifrirajButton.setEnabled(true);
    favoritiComboBox.setEnabled(false);

    odustaniButton.setEnabled(false);
    kljucProgressBar.setVisible(false);
  }

}
