package pmf.math.kalkulatori;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
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

  private static PlayfairKriptosustav playfairKriptosustav = new PlayfairKriptosustav();

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
  private ButtonGroup jezikButtonGroup;

  public PlayfairKalkulator(Konzola konzola) {
    mojaKonzola = konzola;
    postaviTipke();
  }

  private void postaviTipke() {
    // Jezik
    jezikButtonGroup = new ButtonGroup();
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
        // nothing
      }

      @Override
      public void focusLost(FocusEvent e) {
        postaviKljuc();
      }
    });

    // Šifriraj / Dešifriraj
    sifrirajTipka.addActionListener(e -> {
      sifratTextArea.setText(playfairKriptosustav.sifriraj(otvoreniTekstTextArea.getText()));
    });
    sifrirajTipka.addActionListener(e -> {
      otvoreniTekstTextArea.setText(playfairKriptosustav.desifriraj(sifratTextArea.getText()));
    });
  }

  public void postaviJezik(Jezik jezik) {
    String kljuc = kljucTekstField.getText();
    switch (jezik) {
      case HRVATSKI -> postaviTablicu(playfairKriptosustav.dohvatiPlayfairTablicu(Jezik.HRVATSKI, kljuc));
      case ENGLESKI -> postaviTablicu(playfairKriptosustav.dohvatiPlayfairTablicu(Jezik.ENGLESKI, kljuc));
    }
  }

  public void postaviKljuc() {
    String kljuc = kljucTekstField.getText();
    if (hrvatskiRadioButton.isSelected()) {
      postaviTablicu(playfairKriptosustav.dohvatiPlayfairTablicu(Jezik.HRVATSKI, kljuc));
    }
    if (engleskiRadioButton.isSelected()) {
      postaviTablicu(playfairKriptosustav.dohvatiPlayfairTablicu(Jezik.ENGLESKI, kljuc));
    }
  }

  public void postaviTablicu(String[][] podaci) {
    playfairTable.setModel(new DefaultTableModel(podaci, new String[]{"", "", "", "", ""}));
    // Centriraj ćelije
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment( JLabel.CENTER );
    for(int i=0; i < playfairTable.getModel().getColumnCount(); i++){
      playfairTable.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
    }
  }

}
