package pmf.math.kalkulatori;

import static pmf.math.algoritmi.Abeceda.filtrirajTekst;
import static pmf.math.konstante.DuljineTeksta.ANALIZIRANI_TEKST_REDAK_MAX;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import pmf.math.algoritmi.AnalizaTeksta;
import pmf.math.router.Konzola;

public class AnalizaTekstaKalkulator {

  private static final AnalizaTeksta analizaTeksta = new AnalizaTeksta();

  public JPanel glavniPanel;
  private JTextPane analiziraniTekst;
  private JButton analizirajButton;
  private JTextArea unosTekst;
  private JPanel slovaPanel;
  private JLabel slovaLabel;
  private JPanel trigramiPanel;
  private JLabel trigramiLabel;
  private JPanel tipkePanel;
  private JPanel bigramiPanel;
  private JLabel bigramiLabel;
  private JPanel analizaPanel;
  private JButton ocistiButton;
  private JLabel samoglasniciSuglasniciLabel;
  private JPanel detaljiPanel;
  public Konzola mojaKonzola;
  private ButtonGroup grupaZaIsticanjeMultigrama;
  private Map<String, List<Integer>> mapaBigrama;
  private Map<String, List<Integer>> mapaTrigrama;
  private final int MAX_BROJ_REDAKA = 28;

  public AnalizaTekstaKalkulator(Konzola konzola) {
    mojaKonzola = konzola;
    postaviTipke();
    postaviRubove();
  }

  public void postaviTipke() {
    grupaZaIsticanjeMultigrama = new ButtonGroup();

    // Analiziraj
    analizirajButton.addActionListener(e -> {
      grupaZaIsticanjeMultigrama.clearSelection();
      analiziraniTekst.setText(unosTekst.getText());
      sanitizirajTekst();
      postaviSlova();
      postaviBigrame();
      postaviTrigrame();
      postaviSamoglasnikeSuglasnike();
    });

    // Očisti
    ocistiButton.addActionListener(e -> {
      unosTekst.setText("");
      analiziraniTekst.setText("");
      samoglasniciSuglasniciLabel.setText("");
      postaviSlova();
      postaviBigrame();
      postaviTrigrame();
    });
  }

  public void postaviSlova() {
    slovaPanel.removeAll();
    slovaPanel.setLayout(new GridLayout(0, 1));
    analizaTeksta.pronadiSlova(analiziraniTekst.getText().replaceAll(" ", "")).
        forEach((slovo, vrijednost) -> {
          if (vrijednost > 0) {
            slovaPanel.add(stvoriRedak(slovo, vrijednost));
          }
        });
    slovaPanel.revalidate();
  }

  public JPanel stvoriRedak(String slovo, int vrijednost) {
    JPanel povratniPanel = new JPanel();
    povratniPanel.setLayout(new GridLayout(0, 2));
    JLabel slovoLabel = new JLabel(slovo + ":");
    slovoLabel.setFont(new Font("Monospaced", Font.PLAIN, 16));
    povratniPanel.add(slovoLabel);
    povratniPanel.add(new JLabel(String.valueOf(vrijednost)));
    return povratniPanel;
  }

  public void postaviBigrame() {
    bigramiPanel.removeAll();
    bigramiPanel.setLayout(new GridLayout(0, 1));
    mapaBigrama = analizaTeksta.pronadiBigrame(analiziraniTekst.getText().replaceAll(" ", ""));
    AtomicInteger count = new AtomicInteger();
    mapaBigrama.forEach(
        (bigram, vrijednost) -> {
          if (count.get() == MAX_BROJ_REDAKA) {
            trigramiPanel.revalidate();
            return;
          } else {
            count.getAndIncrement();
          }
          if (vrijednost.size() > 1) {
            bigramiPanel.add(stvoriRedakSaTipkom(bigram, vrijednost.size()));
          }
        });
    bigramiPanel.revalidate();
  }

  public void postaviTrigrame() {
    trigramiPanel.removeAll();
    trigramiPanel.setLayout(new GridLayout(0, 1));
    mapaTrigrama = analizaTeksta.pronadiTrigrame(analiziraniTekst.getText().replaceAll(" ", ""));
    AtomicInteger count = new AtomicInteger();
    mapaTrigrama.forEach(
        (trigram, vrijednost) -> {
          if (count.get() == MAX_BROJ_REDAKA) {
            trigramiPanel.revalidate();
            return;
          } else {
            count.getAndIncrement();
          }
          if (vrijednost.size() > 1) {
            trigramiPanel.add(stvoriRedakSaTipkom(trigram, vrijednost.size()));
          }
        });
    trigramiPanel.revalidate();
  }

  public void postaviSamoglasnikeSuglasnike() {
    int brojSlova = analiziraniTekst.getText().length();
    if (brojSlova == 0) {
      return;
    }
    int postotakSamoglasnika =
        analizaTeksta.pronadiSamoglasnike(analiziraniTekst.getText()) * 100 / brojSlova;
    int postotakSuglasnika = 100 - postotakSamoglasnika;

    samoglasniciSuglasniciLabel.setText(postotakSamoglasnika + "% / " + postotakSuglasnika + "%");
  }

  public JPanel stvoriRedakSaTipkom(String multigram, int vrijednost) {
    JPanel povratniPanel = new JPanel();
    povratniPanel.setLayout(new GridLayout(0, 3));
    JLabel multigramLabel = new JLabel(multigram + ":");
    multigramLabel.setFont(new Font("Monospaced", Font.PLAIN, 16));
    povratniPanel.add(multigramLabel);
    povratniPanel.add(new JLabel(String.valueOf(vrijednost)));
    JRadioButton tipkaZaIsticanjeMultigrama = new JRadioButton();
    grupaZaIsticanjeMultigrama.add(tipkaZaIsticanjeMultigrama);
    tipkaZaIsticanjeMultigrama.addActionListener(e -> {
      istakniMultigram(multigram);
    });
    povratniPanel.add(tipkaZaIsticanjeMultigrama);
    return povratniPanel;
  }

  public void istakniMultigram(String multigram) {
    List<Integer> lokacijeMultigrama;
    int m = multigram.length();
    switch (m) {
      case 2:
        lokacijeMultigrama = mapaBigrama.get(multigram);
        break;

      case 3:
        lokacijeMultigrama = mapaTrigrama.get(multigram);
        break;

      default:
        return;
    }
    String tekst = analiziraniTekst.getText().replaceAll(" ", "");
    SimpleAttributeSet aset = new SimpleAttributeSet();

    StyleConstants.setForeground(aset, Color.BLACK);
    StyleConstants.setBold(aset, false);
    analiziraniTekst.setCharacterAttributes(aset, false);

    analiziraniTekst.setText(tekst);

    StyleConstants.setForeground(aset, Color.BLUE);
    StyleConstants.setBold(aset, true);
    analiziraniTekst.setCharacterAttributes(aset, false);
    try {
      for (Integer integer : lokacijeMultigrama) {
        analiziraniTekst.getStyledDocument().remove(integer, m);
        analiziraniTekst.setCaretPosition(integer);
        analiziraniTekst.getStyledDocument()
            .insertString(analiziraniTekst.getCaretPosition(), multigram, aset);
      }
      int div = tekst.length() / ANALIZIRANI_TEKST_REDAK_MAX;
      for (int i = div; i > 0; i--) {
        analiziraniTekst.setCaretPosition(ANALIZIRANI_TEKST_REDAK_MAX * i);
        analiziraniTekst.getStyledDocument()
            .insertString(analiziraniTekst.getCaretPosition(), " ", aset);
      }
    } catch (BadLocationException ex) {
      ex.printStackTrace();
    }

    StyleConstants.setForeground(aset, Color.BLACK);
    StyleConstants.setBold(aset, false);
    analiziraniTekst.setCharacterAttributes(aset, false);
  }

  public void sanitizirajTekst() {
    StringBuilder izlazniTekst = new StringBuilder();
    String tekst = filtrirajTekst(analiziraniTekst.getText());
    for (int i = 0; i < tekst.length() - ANALIZIRANI_TEKST_REDAK_MAX;
        i += ANALIZIRANI_TEKST_REDAK_MAX) {
      izlazniTekst.append(tekst, i, i + ANALIZIRANI_TEKST_REDAK_MAX).append(" ");
    }
    izlazniTekst.append(tekst,
        tekst.length() - tekst.length() % ANALIZIRANI_TEKST_REDAK_MAX, tekst.length());
    analiziraniTekst.setText(izlazniTekst.toString());
  }

  public void postaviRubove() {
    unosTekst.setMargin(new Insets(10, 10, 10, 10));
  }

  public boolean samoglasnik(String slovo) {
    if (slovo.length() != 1) {
      return false;
    }
    String slovoUpper = slovo.toUpperCase(Locale.ROOT);
    if (slovoUpper.contains("A") ||
        slovoUpper.contains("E") ||
        slovoUpper.contains("I") ||
        slovoUpper.contains("O") ||
        slovoUpper.contains("U")) {
      return true;
    } else {
      return false;
    }
  }

}
