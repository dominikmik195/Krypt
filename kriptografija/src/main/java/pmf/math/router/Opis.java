package pmf.math.router;

import static pmf.math.konstante.DuljineTeksta.OPIS_TEKST_REDAK_MAX;
import static pmf.math.pomagala.StringInteger.stringUIntRed;

import java.awt.GridLayout;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import pmf.math.baza.dao.TekstGrafDAO;
import pmf.math.baza.dao.TekstGrafDAO.VrstaSimulacije;
import pmf.math.baza.tablice.TekstGrafovi;
import pmf.math.konstante.ImenaKalkulatora;

public class Opis {

  public JPanel glavniPanel;
  private JTextArea gornjiTextPane;
  private JTextArea donjiTextPane;
  private JPanel gornjiPanel;
  private JPanel donjiPanel;
  private JPanel grafPanel;
  private JButton osvjeziGrafButton;

  private final TekstGrafDAO tekstGrafDAO = new TekstGrafDAO();
  private ImenaKalkulatora imeKalkulatora;
  private boolean postavljanjeGrafa = false;

  public Opis() {
    postaviTipke();
    new Thread(this::postaviPrazanGraf).start();
  }

  private void postaviTipke() {
    osvjeziGrafButton.addActionListener(e -> {
      if (postavljanjeGrafa) {
        return;
      }
      new Thread(() -> postaviGraf(true)).start();
    });
  }

  public void postaviTekst(String gornji, String donji, ImenaKalkulatora ime) {
    gornjiTextPane.setText(razlomiTekst(gornji));
    gornjiTextPane.setCaretPosition(0);
    donjiTextPane.setText(razlomiTekst(donji));
    donjiTextPane.setCaretPosition(0);
    imeKalkulatora = ime;
  }

  public void postaviGraf(boolean osvjezi) {
    postavljanjeGrafa = true;
    osvjeziGrafButton.setEnabled(false);

    TekstGrafovi grafSifriraj = tekstGrafDAO
      .dohvatiElement(imeKalkulatora, VrstaSimulacije.SIFRIRAJ, osvjezi);
    TekstGrafovi grafDesifriraj = tekstGrafDAO
        .dohvatiElement(imeKalkulatora, VrstaSimulacije.DESIFRIRAJ, osvjezi);

    postaviGrafUzPodatke(dohvatiPodatke(grafSifriraj, grafDesifriraj));

    osvjeziGrafButton.setEnabled(true);
    postavljanjeGrafa = false;
  }

  public void postaviGrafUzPodatke(DefaultCategoryDataset podaci) {
    JFreeChart linijskiDijagram = ChartFactory.createLineChart(
        "",
        "Duljina teksta (broj slova)", "Vrijeme (µs)",
        podaci,
        PlotOrientation.VERTICAL,
        true, false, false);
    linijskiDijagram.setBackgroundPaint(grafPanel.getBackground());

    grafPanel.removeAll();
    grafPanel.setLayout(new GridLayout());

    ChartPanel grafPodloga = new ChartPanel(linijskiDijagram);
    grafPanel.add(grafPodloga);
    grafPanel.revalidate();
  }

  public void postaviPrazanGraf() {
    postaviGrafUzPodatke(new DefaultCategoryDataset());
  }

  private String razlomiTekst(String tekst) {
    StringBuilder izlazniTekst = new StringBuilder();
    Arrays.stream(tekst.split("\n")).forEach(redak -> {
      izlazniTekst.append(razlomiRedak(redak)).append("\n");
    });
    return izlazniTekst.toString();
  }

  private String razlomiRedak(String redak) {
    String izlazniString = redak;
    int div = redak.length() / OPIS_TEKST_REDAK_MAX;
    for (int i = 1; i <= div; i++) {
      int pozicija = OPIS_TEKST_REDAK_MAX * i - 1;
      if (pozicija + 2 <= izlazniString.length() &&
          izlazniString.substring(pozicija, pozicija + 2).matches("[a-zA-Z0-9čćšđžČĆŠŽĐ]*")) {
        if (izlazniString.substring(pozicija - 1, pozicija).matches("[a-zA-Z0-9čćšđžČĆŠŽĐ]*")) {
          izlazniString = dodajUString(izlazniString, pozicija, "-");
        } else {
          izlazniString = dodajUString(izlazniString, pozicija, " ");
        }
        div = izlazniString.length() / OPIS_TEKST_REDAK_MAX;
      }
    }
    return izlazniString;
  }

  private String dodajUString(String tekst, int pocetak, String dodatak) {
    return tekst.substring(0, pocetak) + dodatak + tekst.substring(pocetak);
  }

  private DefaultCategoryDataset dohvatiPodatke(TekstGrafovi grafA, TekstGrafovi grafB) {
    DefaultCategoryDataset podaci = new DefaultCategoryDataset();
    int[] duljineTekstaA = stringUIntRed(grafA.getDuljineTeksta());
    int[] vremenaIzvodenjaA = stringUIntRed(grafA.getVremenaIzvodenja());
    String vrstaSimulacijeA = grafA.getVrstaSimulacije();

    int[] duljineTekstaB = stringUIntRed(grafB.getDuljineTeksta());
    int[] vremenaIzvodenjaB = stringUIntRed(grafB.getVremenaIzvodenja());
    String vrstaSimulacijeB = grafB.getVrstaSimulacije();

    for (int i = 0; i < duljineTekstaA.length; i++) {
      podaci.addValue(duljineTekstaA[i], vrstaSimulacijeA, String.valueOf(vremenaIzvodenjaA[i]));
    }
    for (int i = 0; i < duljineTekstaB.length; i++) {
      podaci.addValue(duljineTekstaB[i], vrstaSimulacijeB, String.valueOf(vremenaIzvodenjaB[i]));
    }
    return podaci;
  }
}
