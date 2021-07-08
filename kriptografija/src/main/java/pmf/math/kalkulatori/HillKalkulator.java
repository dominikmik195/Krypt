package pmf.math.kalkulatori;

import static pmf.math.algoritmi.Abeceda.filtrirajTekst;

import java.awt.Dimension;
import java.awt.Insets;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import pmf.math.algoritmi.Matrica;
import pmf.math.baza.dao.HillDAO;
import pmf.math.filteri.HillFilter;
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
  private JPanel favoritiPanel;
  private JButton prethodniFavorit;
  private JButton sljedeciFavorit;
  private JButton postaviKljucButton;
  private JPanel dimenzijaPanel;
  private JTable favoritTable;
  private final Konzola mojaKonzola;
  private final HillDAO hillDao = new HillDAO();
  private int trenutniFavorit = 0;

  public HillKalkulator(Konzola konzola) {
    mojaKonzola = konzola;
    SwingUtilities.invokeLater(() -> {
      postaviTipke();
      postaviRubove();
    });
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
          postaviTablicu(HillMatrice.Identiteta(m), m, kljucTable);
          sanitizirajTekst(otvoreniTekstTextArea);
          sanitizirajTekst(sifratTextArea);
        });
    postaviTablicu(HillMatrice.Identiteta(3), 3, kljucTable);
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
      if (!sifrirajButton.isEnabled() || otvoreniTekstTextArea.getText().isEmpty()) {
        return;
      }
      if (otvoreniTekstTextArea.getText().replaceAll(" ", "")
          .length() % dimenzijaSlider.getValue() != 0) {
        mojaKonzola.ispisiGresku("OPREZ! Unos mora biti djeljiv sa dimenzijom matrice m.");
        return;
      }
      try {
        if (!dohvatiTablicu(kljucTable).regularna()) {
          mojaKonzola.ispisiGresku("Matrica ključa nije regularna.");
        } else {
          Matrica kljuc = dohvatiTablicu(kljucTable);
          sifratTextArea.setText(
              hillKriptosustav.sifriraj(otvoreniTekstTextArea.getText(), kljuc));
          provjeriTipkuZaRacunanjeKljuca();
          mojaKonzola.ispisiPoruku("Šifriranje dovršeno.");
          dodajNoviFavorit();
        }
      } catch (Exception exception) {
        mojaKonzola.ispisiGresku("Pogreška pri šifriranju.");
      }
    });
    desifrirajButton.addActionListener(e -> {
      if (!desifrirajButton.isEnabled() || sifratTextArea.getText().isEmpty()) {
        return;
      }
      if (sifratTextArea.getText().replaceAll(" ", "")
          .length() % dimenzijaSlider.getValue() != 0) {
        mojaKonzola.ispisiGresku("OPREZ! Unos mora biti djeljiv sa dimenzijom matrice m.");
        return;
      }
      try {
        if (!dohvatiTablicu(kljucTable).regularna()) {
          mojaKonzola.ispisiGresku("Matrica ključa nije regularna.");
        } else if (!dohvatiTablicu(kljucTable).involuirana()) {
          mojaKonzola.ispisiGresku(
              "Matrica ključa nije involuirana. Za dešifriranje treba vrijediti K = K^-1.");
        } else {
          otvoreniTekstTextArea.setText(
              hillKriptosustav.desifriraj(sifratTextArea.getText(), dohvatiTablicu(kljucTable)));
          provjeriTipkuZaRacunanjeKljuca();
          mojaKonzola.ispisiPoruku("Dešifriranje dovršeno.");
          dodajNoviFavorit();
        }
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
            postaviTablicu(kljuc, m, kljucTable);
            mojaKonzola.ispisiPoruku("Uspješno računanje ključa.");
            dodajNoviFavorit();
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

    // Tipke za listanje favorite
    prethodniFavorit.addActionListener(e -> {
      trenutniFavorit--;
      prikaziFavorit();
      provjeriTipkeZaFavorite();
    });

    sljedeciFavorit.addActionListener(e -> {
      trenutniFavorit++;
      prikaziFavorit();
      provjeriTipkeZaFavorite();
    });
    provjeriTipkeZaFavorite();
    prikaziFavorit();

    // Tipka za postavljanje favorita
    postaviKljucButton.addActionListener(e -> {
      postaviFavorit();
    });
  }

  private void postaviTablicu(String[][] podaci, int m, JTable tablica) {
    tablica.setPreferredSize(new Dimension(m * 40, m * 30));
    tablica.setModel(new DefaultTableModel(podaci, HillMatrice.Naslov(m)));

    // Centriranje teksta u ćelijama
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    for (int i = 0; i < tablica.getModel().getColumnCount(); i++) {
      tablica.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }
  }

  private Matrica dohvatiTablicu(JTable tablica) {
    TableModel model = tablica.getModel();
    int m = model.getColumnCount();
    int[][] izlaz = new int[m][m];
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < m; j++) {
        izlaz[i][j] = Integer.parseInt(model.getValueAt(i, j).toString());
      }
    }
    return new Matrica(izlaz);
  }

  public void sanitizirajTekst(JTextArea textArea) {
    int m = dimenzijaSlider.getModel().getValue();
    textArea.setText(HillFilter.filtriraj(textArea.getText(), m));
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

  public void prikaziFavorit() {
    if(hillDao.brojElemenata() == 0) return;
    String[][] podaci = hillDao.dohvatiElement(trenutniFavorit);
    if (podaci == null) {
      mojaKonzola.ispisiGresku("Greška pri učitavanju povijesti ključeva.");
    } else {
      postaviTablicu(podaci, podaci.length, favoritTable);
    }
  }

  public void provjeriTipkeZaFavorite() {
    int brojFavorita = hillDao.brojElemenata();
    prethodniFavorit.setEnabled(trenutniFavorit > 0);
    sljedeciFavorit.setEnabled(trenutniFavorit < brojFavorita);
    postaviKljucButton.setEnabled(brojFavorita != 0);
  }

  public void postaviFavorit() {
    String[][] podaci = hillDao.intToStringTablica(dohvatiTablicu(favoritTable).getMatrica());
    if (podaci == null) {
      mojaKonzola.ispisiGresku("Greška pri učitavanju ključa.");
    } else {
      dimenzijaSlider.setValue(podaci.length);
      postaviTablicu(podaci, podaci.length, kljucTable);
    }
  }

  public void dodajNoviFavorit() {
    hillDao.ubaciElement(hillDao.intToStringTablica(dohvatiTablicu(kljucTable).getMatrica()));
    trenutniFavorit = 0;
    postaviFavorit();
    provjeriTipkeZaFavorite();
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
    prekiniButton.setEnabled(true);
    kljucProgressbar.setVisible(true);
    sljedeciFavorit.setEnabled(false);
    prethodniFavorit.setEnabled(false);
    postaviKljucButton.setEnabled(false);
    favoritTable.setEnabled(false);
  }

  public void omoguciSucelje() {
    otvoreniTekstTextArea.setEditable(true);
    sifratTextArea.setEditable(true);
    zakljucajCheckBox.setEnabled(true);
    sifrirajButton.setEnabled(true);
    desifrirajButton.setEnabled(true);
    prekiniButton.setEnabled(false);
    kljucProgressbar.setVisible(false);
    sljedeciFavorit.setEnabled(true);
    provjeriTipkeZaFavorite();
    provjeriTipkuZaRacunanjeKljuca();
  }
}
