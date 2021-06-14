package pmf.math.kalkulatori;

import static pmf.math.algoritmi.VrijemeDatum.dohvatiVrijemeDatum;

import java.awt.Dimension;
import java.awt.GridLayout;
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
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import pmf.math.baza.dao.PlayfairDAO;
import pmf.math.baza.tablice.PlayfairFavoriti;
import pmf.math.kriptosustavi.PlayfairKriptosustav;
import pmf.math.kriptosustavi.PlayfairKriptosustav.Jezik;
import pmf.math.router.Konzola;

public class PlayfairKalkulator {

  private static final PlayfairKriptosustav playfairKriptosustav = new PlayfairKriptosustav();

  public JPanel glavniPanel;
  public Konzola mojaKonzola;
  private final PlayfairDAO playfairDAO = new PlayfairDAO();

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
  private JPanel favoritiPanel;

  public PlayfairKalkulator(Konzola konzola) {
    mojaKonzola = konzola;
    postaviTipke();
    postaviRubove();
    osvjeziFavorite();
  }

  private void postaviTipke() {
    // Jezik
    ButtonGroup jezikButtonGroup = new ButtonGroup();
    jezikButtonGroup.add(hrvatskiRadioButton);
    jezikButtonGroup.add(engleskiRadioButton);

    hrvatskiRadioButton.addActionListener(e -> {
      postaviKljuc(kljucTekstField.getText());
    });
    engleskiRadioButton.addActionListener(e -> {
      postaviKljuc(kljucTekstField.getText());
    });

    // Početna tablica
    hrvatskiRadioButton.setSelected(true);
    postaviTablicu(playfairKriptosustav.dohvatiPlayfairTablicu(Jezik.HRVATSKI, ""));

    // Ključ
    kljucTekstField.addFocusListener(new FocusListener() {
      @Override
      public void focusGained(FocusEvent e) {
        // ništa
      }

      @Override
      public void focusLost(FocusEvent e) {
        postaviKljuc(kljucTekstField.getText());
      }
    });

    // Šifriraj / Dešifriraj
    sifrirajTipka.addActionListener(e -> {
      if(otvoreniTekstTextArea.getText().equals("")) return;
      try {
        sifratTextArea.setText(playfairKriptosustav.sifriraj(otvoreniTekstTextArea.getText()));
        Jezik jezik = hrvatskiRadioButton.isSelected() ? Jezik.HRVATSKI : Jezik.ENGLESKI;
        playfairDAO.ubaciFavorit(kljucTekstField.getText(), jezik);
        osvjeziFavorite();
        mojaKonzola.ispisiPoruku("Šifriranje dovršeno.");
      } catch (Exception exception) {
        mojaKonzola.ispisiGresku("Pogreška pri šifriranju.");
      }
    });
    desifrirajTipka.addActionListener(e -> {
      if(sifratTextArea.getText().equals("")) return;
      try {
        otvoreniTekstTextArea.setText(playfairKriptosustav.desifriraj(sifratTextArea.getText()));
        Jezik jezik = hrvatskiRadioButton.isSelected() ? Jezik.HRVATSKI : Jezik.ENGLESKI;
        playfairDAO.ubaciFavorit(kljucTekstField.getText(), jezik);
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
  }

  public void postaviKljuc(String kljuc) {
    kljuc = kljuc.toUpperCase(Locale.ROOT).replaceAll("[^A-Z]", "");
    if (kljuc.length() >= 25) {
      kljuc = kljuc.substring(0, 25);
    }
    kljucTekstField.setText(kljuc);
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


  public void osvjeziFavorite() {
    favoritiPanel.removeAll();
    favoritiPanel.setLayout(new GridLayout(0, 1));
    playfairDAO.dohvatiFavorite()
        .forEach(favorit -> favoritiPanel.add(stvoriRedakFavorita(favorit)));
    GridLayout layout = (GridLayout) favoritiPanel.getLayout();
    layout.setVgap(0);
    favoritiPanel.revalidate();
  }

  public JPanel stvoriRedakFavorita(PlayfairFavoriti favorit) {
    String jezik = favorit.getJezik() == Jezik.HRVATSKI.ordinal() ? "HR" : "EN";
    String datum = dohvatiVrijemeDatum(favorit.getVrijemeStvaranja());

    JPanel izlazniPanel = new JPanel();
    izlazniPanel.add("kljuc", stvoriGumbFavorita(favorit));
    izlazniPanel.add("jezik", stvoriOznakuJezika(jezik));
    izlazniPanel.add("datum", stvoriOznakuDatuma(datum));

    return izlazniPanel;
  }

  public JButton stvoriGumbFavorita(PlayfairFavoriti favorit) {
    JButton izlazniGumb = new JButton(favorit.getKljuc());
    izlazniGumb.setPreferredSize(new Dimension(150, 30));

    izlazniGumb.addActionListener(e -> {
      postaviKljuc(favorit.getKljuc());
    });

    return izlazniGumb;
  }

  public JLabel stvoriOznakuJezika(String jezik) {
    JLabel izlaznaOznaka = new JLabel(jezik);
    izlaznaOznaka.setPreferredSize(new Dimension(30, 30));
    izlaznaOznaka.setHorizontalTextPosition(SwingConstants.CENTER);

    return izlaznaOznaka;
  }

  public JLabel stvoriOznakuDatuma(String datum) {
    JLabel izlaznaOznaka = new JLabel(datum);
    izlaznaOznaka.setPreferredSize(new Dimension(70, 30));

    return izlaznaOznaka;
  }

  public void sanitizirajTekst(JTextArea textArea) {
    String tekst = textArea.getText().toUpperCase(Locale.ROOT).replaceAll("[^A-Z]", "");
    if (hrvatskiRadioButton.isSelected()) {
      tekst = tekst.replaceAll("W", "V");
    }
    if (engleskiRadioButton.isSelected()) {
      tekst = tekst.replaceAll("J", "I");
    }
    StringBuilder izlaz = new StringBuilder();

    for (int i = 0; i < tekst.length(); i += 2) {
      izlaz.append(tekst.substring(i, Math.min(i + 2, tekst.length())))
          .append(i + 2 < tekst.length() ? " " : "");
    }
    if (!izlaz.toString().equals(textArea.getText())
        && textArea.getText().replaceAll(" ", "").length() % 2 != 0) {
      mojaKonzola
          .ispisiGresku("OPREZ! Unos mora biti parne duljine. Zadnje slovo neće biti izmijenjeno.");
    }

    textArea.setText(izlaz.toString());
  }

  public void postaviRubove() {
    otvoreniTekstTextArea.setMargin(new Insets(10, 10, 10, 10));
    sifratTextArea.setMargin(new Insets(10, 10, 10, 10));
  }

}
