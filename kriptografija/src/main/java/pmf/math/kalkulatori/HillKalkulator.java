package pmf.math.kalkulatori;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Locale;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import pmf.math.algoritmi.Matrica;
import pmf.math.konstante.HillMatrice;
import pmf.math.kriptosustavi.HillKriptosustav;
import pmf.math.router.Konzola;

public class HillKalkulator {

  private static final HillKriptosustav hillKriptosustav = new HillKriptosustav();

  public JPanel glavniPanel;

  private JTextArea otvoreniTekstTextArea;
  private JButton sifrirajButton;
  private JButton desifrirajButton;
  private JTextArea sifratTextArea;
  private JLabel sifratLabel;
  private JLabel otvoreniTekstLabel;
  private JPanel tipkePanel;
  private JPanel tekstPanel;
  private JPanel kljucPanel;
  private JButton kljucButton;
  private JTable kljucTable;
  private JLabel kljucLabel;
  private JLabel dimenzijaLabel;
  private JPanel matricaKljucaPanel;
  private JSlider dimenzijaSlider;
  private JCheckBox zakljucajCheckBox;
  private JButton prekiniButton;
  private JProgressBar kljucProgressbar;
  private final Konzola mojaKonzola;

  public HillKalkulator(Konzola konzola) {
    mojaKonzola = konzola;
    postaviTipke();
    postaviRubove();
  }

  private void postaviTipke() {
    // Dimenzija ključa
    dimenzijaSlider.setModel(new DefaultBoundedRangeModel(3, 0, 2, 5));
    dimenzijaSlider.getModel().addChangeListener(
        e -> {
          int m = dimenzijaSlider.getModel().getValue();
          if (kljucTable.getColumnCount() == m) {
            return;
          }
          postaviTablicu(HillMatrice.Identiteta(m), m);
          sanitizirajTekst(otvoreniTekstTextArea);
          sanitizirajTekst(sifratTextArea);
        });
    postaviTablicu(HillMatrice.Identiteta(3), 3);
    zakljucajCheckBox.addActionListener(e -> {
      kljucTable.setEnabled(!kljucTable.isEnabled());
      dimenzijaSlider.setEnabled(!dimenzijaSlider.isEnabled());
    });

    // Unos u tablicu ključa
    kljucTable.addPropertyChangeListener(e -> {
      int row = kljucTable.getSelectedRow();
      int column = kljucTable.getSelectedColumn();
      if ("tableCellEditor".equals(e.getPropertyName())) {

        // Uređivanje ćelije je završeno - filtriraj unos
        if (!kljucTable.isEditing()) {
          String unos = (String) kljucTable.getValueAt(row, column);

          // Izbaci sve osim znamenki - default = 0
          try {
            int parseInt = Integer.parseInt(unos.substring(0, Math.min(6, unos.length())));
            if (parseInt > 0 && unos.length() > 5) {
              parseInt /= 10;
            }
            kljucTable.setValueAt(parseInt, row, column);
          } catch (NumberFormatException numberFormatException) {
            kljucTable.setValueAt("0", row, column);
          }
        }
        // Uređivanje je počelo - postavi na *blank*
        else {
          JTextField editor = (JTextField) kljucTable.getEditorComponent();
          editor.setText("");
        }
      }
    });
    provjeriTipkuZaRacunanjeKljuca();

    // Šifriraj / Dešifriraj
    sifrirajButton.addActionListener(e -> {
      if (!sifrirajButton.isEnabled()) {
        return;
      }
      if (otvoreniTekstTextArea.getText().isEmpty()) {
        return;
      }
      if (otvoreniTekstTextArea.getText().replaceAll(" ", "")
          .length() % dimenzijaSlider.getValue() != 0) {
        mojaKonzola.ispisiGresku("OPREZ! Unos mora biti djeljiv sa dimenzijom matrice m.");
        return;
      }
      try {
        if (!dohvatiTablicu().regularna()) {
          mojaKonzola.ispisiGresku("Matrica ključa nije regularna.");
        } else {
          sifratTextArea.setText(
              hillKriptosustav.sifriraj(otvoreniTekstTextArea.getText(), dohvatiTablicu()));
          provjeriTipkuZaRacunanjeKljuca();
          mojaKonzola.ispisiPoruku("Šifriranje dovršeno.");
        }
      } catch (Exception exception) {
        mojaKonzola.ispisiGresku("Pogreška pri šifriranju.");
      }
    });
    desifrirajButton.addActionListener(e -> {
      if (!desifrirajButton.isEnabled()) {
        return;
      }
      if (sifratTextArea.getText().isEmpty()) {
        return;
      }
      if (sifratTextArea.getText().replaceAll(" ", "")
          .length() % dimenzijaSlider.getValue() != 0) {
        mojaKonzola.ispisiGresku("OPREZ! Unos mora biti djeljiv sa dimenzijom matrice m.");
        return;
      }
      //try {
      if (!dohvatiTablicu().regularna()) {
        mojaKonzola.ispisiGresku("Matrica ključa nije regularna.");
      } else if (!dohvatiTablicu().involuirana()) {
        mojaKonzola.ispisiGresku(
            "Matrica ključa nije involuirana. Za dešifriranje treba vrijediti K = K^-1.");
      } else {
        otvoreniTekstTextArea.setText(
            hillKriptosustav.desifriraj(sifratTextArea.getText(), dohvatiTablicu()));
        provjeriTipkuZaRacunanjeKljuca();
        mojaKonzola.ispisiPoruku("Dešifriranje dovršeno.");
      }
      //} catch (Exception exception) {
      //  mojaKonzola.ispisiGresku("Pogreška pri dešifriranju.");
      //}
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
        if (otvoreniTekstTextArea.isEditable()) {
          provjeriTipkuZaRacunanjeKljuca();
        }
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
        if (sifratTextArea.isEditable()) {
          provjeriTipkuZaRacunanjeKljuca();
        }
      }
    });

    // Računanje ključa
    kljucButton.addActionListener(e -> {
      new Thread(() -> {
        onemoguciSucelje();
        hillKriptosustav.setExitThread(false);
        int m = dimenzijaSlider.getModel().getValue();
        String[][] kljuc = hillKriptosustav.izracunajKljuc(m,
            otvoreniTekstTextArea.getText(),
            sifratTextArea.getText()
        );
        hillKriptosustav.setExitThread(true);

        SwingUtilities.invokeLater(() -> {
          if (kljuc != null) {
            postaviTablicu(kljuc, m);
            mojaKonzola.ispisiPoruku("Uspješno računanje ključa.");
          } else {
            mojaKonzola.ispisiGresku("Neuspješno računanje ključa.");
          }
          omoguciSucelje();
        });
      }).start();

      new Thread(() -> {
        kljucProgressbar.setVisible(true);
        while (kljucProgressbar.isVisible()) {
          SwingUtilities.invokeLater(() -> {
            kljucProgressbar.setValue(hillKriptosustav.getNapredak());
          });
        }
      }).start();
    });

    prekiniButton.addActionListener(e -> {
      omoguciSucelje();
      hillKriptosustav.setExitThread(true);
    });
  }

  private void postaviTablicu(String[][] podaci, int m) {
    kljucTable.setPreferredSize(new Dimension(m * 50, m * 50));
    kljucTable.setModel(new DefaultTableModel(podaci, HillMatrice.Naslov(m)));

    // Centriranje teksta u ćelijama
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    for (int i = 0; i < kljucTable.getModel().getColumnCount(); i++) {
      kljucTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }
  }

  private Matrica dohvatiTablicu() {
    TableModel model = kljucTable.getModel();
    int m = dimenzijaSlider.getModel().getValue();
    int[][] izlaz = new int[m][m];
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < m; j++) {
        izlaz[i][j] = Integer.parseInt(model.getValueAt(i, j).toString());
      }
    }
    return new Matrica(izlaz);
  }

  public void sanitizirajTekst(JTextArea textArea) {
    String tekst = textArea.getText().toUpperCase(Locale.ROOT).replaceAll("[^A-Z]", "");
    StringBuilder izlaz = new StringBuilder();
    int m = dimenzijaSlider.getModel().getValue();

    for (int i = 0; i < tekst.length(); i += m) {
      izlaz.append(tekst.substring(i, Math.min(i + m, tekst.length())))
          .append(i + m < tekst.length() ? " " : "");
    }
    textArea.setText(izlaz.toString().trim());
  }

  public void provjeriTipkuZaRacunanjeKljuca() {
    if (otvoreniTekstTextArea.getText().isEmpty() || sifratTextArea.getText().isEmpty()) {
      return;
    }
    int m = dimenzijaSlider.getModel().getValue();
    kljucButton.setEnabled(
        otvoreniTekstTextArea.getText().replaceAll(" ", "").length() % m == 0 &&
            otvoreniTekstTextArea.getText().length() == sifratTextArea.getText().length());
    kljucButton.setToolTipText(kljucButton.isEnabled() ? null :
        "Otvoreni tekst i šifrat moraju sadržavati tekstove jednake duljine i djeljive sa m.");
  }

  public void postaviRubove() {
    otvoreniTekstTextArea.setMargin(new Insets(10, 10, 10, 10));
    sifratTextArea.setMargin(new Insets(10, 10, 10, 10));
  }

  public void onemoguciSucelje() {
    if (!zakljucajCheckBox.isSelected()) {
      zakljucajCheckBox.doClick();
    }
    otvoreniTekstTextArea.setEditable(false);
    sifratTextArea.setEditable(false);
    zakljucajCheckBox.setEnabled(false);
    sifrirajButton.setEnabled(false);
    desifrirajButton.setEnabled(false);
    kljucButton.setEnabled(false);
    prekiniButton.setVisible(true);
    kljucProgressbar.setVisible(true);
  }

  public void omoguciSucelje() {
    otvoreniTekstTextArea.setEditable(true);
    sifratTextArea.setEditable(true);
    zakljucajCheckBox.setEnabled(true);
    sifrirajButton.setEnabled(true);
    desifrirajButton.setEnabled(true);
    kljucButton.setEnabled(true);
    prekiniButton.setVisible(false);
    kljucProgressbar.setVisible(false);
  }
}
