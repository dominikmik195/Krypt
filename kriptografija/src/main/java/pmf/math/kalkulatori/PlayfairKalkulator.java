package pmf.math.kalkulatori;

import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Locale;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import pmf.math.kriptosustavi.PlayfairKriptosustav;
import pmf.math.kriptosustavi.PlayfairKriptosustav.Jezik;
import pmf.math.router.Konzola;

public class PlayfairKalkulator {

  private static final PlayfairKriptosustav playfairKriptosustav = new PlayfairKriptosustav();

  public JPanel glavniPanel;
  public Konzola mojaKonzola;

  private JTextField kljucTekstField;
  private JTextArea otvoreniTekstTextArea;
  private JRadioButton hrvatskiRadioButton;
  private JRadioButton engleskiRadioButton;
  private JButton sifrirajTipka;
  private JButton desifrirajTipka;
  private JTextArea sifratTextArea;
  private JTable playfairTable;
  private JLabel kljucLabel;
  private JLabel jezikLabel;
  private JLabel otvoreniTekstLabel;
  private JLabel sifratLabel;
  private JPanel jezikRadioPanel;
  private JPanel tiplePanel;
  private JPanel playfairPanel;
  private JPanel postavkeKljucaPanel;

  public PlayfairKalkulator(Konzola konzola) {
    mojaKonzola = konzola;
    postaviTipke();
    postaviRubove();
  }

  private void postaviTipke() {
    // Jezik
    ButtonGroup jezikButtonGroup = new ButtonGroup();
    jezikButtonGroup.add(hrvatskiRadioButton);
    jezikButtonGroup.add(engleskiRadioButton);

    hrvatskiRadioButton.addActionListener(e -> {
      postaviJezik(Jezik.HRVATSKI);
    });
    engleskiRadioButton.addActionListener(e -> {
      postaviJezik(Jezik.ENGLESKI);
    });
    // početna tipka - engleski
    engleskiRadioButton.doClick();

    // Ključ
    kljucTekstField.addFocusListener(new FocusListener() {
      @Override
      public void focusGained(FocusEvent e) {
        // ništa
      }

      @Override
      public void focusLost(FocusEvent e) {
        postaviKljuc();
      }
    });

    // Šifriraj / Dešifriraj
    sifrirajTipka.addActionListener(e -> {
      try {
        sifratTextArea.setText(playfairKriptosustav.sifriraj(otvoreniTekstTextArea.getText()));
        mojaKonzola.ispisiPoruku("Šifriranje dovršeno.");
      }
      catch(Exception exception) { mojaKonzola.ispisiGresku("Pogreška pri šifriranju."); }
    });
    desifrirajTipka.addActionListener(e -> {
      try {
        otvoreniTekstTextArea.setText(playfairKriptosustav.desifriraj(sifratTextArea.getText()));
        mojaKonzola.ispisiPoruku("Dešifriranje dovršeno.");
      }
      catch(Exception exception) { mojaKonzola.ispisiGresku("Pogreška pri dešifriranju."); }
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
  }

  public void postaviJezik(Jezik jezik) {
    String kljuc = kljucTekstField.getText();
    switch (jezik) {
      case HRVATSKI -> postaviTablicu(
          playfairKriptosustav.dohvatiPlayfairTablicu(Jezik.HRVATSKI, kljuc));
      case ENGLESKI -> postaviTablicu(
          playfairKriptosustav.dohvatiPlayfairTablicu(Jezik.ENGLESKI, kljuc));
    }
    sanitizirajTekst(otvoreniTekstTextArea);
    sanitizirajTekst(sifratTextArea);
  }

  public void postaviKljuc() {
    String kljuc = kljucTekstField.getText();
    if (hrvatskiRadioButton.isSelected()) {
      postaviTablicu(playfairKriptosustav.dohvatiPlayfairTablicu(Jezik.HRVATSKI, kljuc));
    }
    if (engleskiRadioButton.isSelected()) {
      postaviTablicu(playfairKriptosustav.dohvatiPlayfairTablicu(Jezik.ENGLESKI, kljuc));
    }
    sanitizirajTekst(otvoreniTekstTextArea);
    sanitizirajTekst(sifratTextArea);
  }

  public void postaviTablicu(String[][] podaci) {
    playfairTable.setModel(new DefaultTableModel(podaci, new String[]{"", "", "", "", ""}));
    // Centriraj ćelije
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    for (int i = 0; i < playfairTable.getModel().getColumnCount(); i++) {
      playfairTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }
  }

  public void sanitizirajTekst(JTextArea textArea) {
    String tekst = textArea.getText().toUpperCase(Locale.ROOT).replaceAll("[^A-Z]", "");
    if(hrvatskiRadioButton.isSelected()) tekst = tekst.replaceAll("W", "V");
    if(engleskiRadioButton.isSelected()) tekst = tekst.replaceAll("J", "I");
    StringBuilder izlaz = new StringBuilder();

    for (int i = 0; i < tekst.length(); i += 2) {
      izlaz.append(tekst.substring(i, Math.min(i + 2, tekst.length())))
          .append(i + 2 < tekst.length() ? " " : "");
    }
    if (!izlaz.toString().equals(textArea.getText())
        && textArea.getText().replaceAll(" ", "").length() % 2 != 0) {
      mojaKonzola.ispisiGresku("OPREZ! Unos mora biti parne duljine. Zadnje slovo neće biti izmijenjeno.");
    }

    textArea.setText(izlaz.toString());
  }

  public void postaviRubove() {
    otvoreniTekstTextArea.setMargin(new Insets(10,10,10,10));
    sifratTextArea.setMargin(new Insets(10,10,10,10));
  }

}
