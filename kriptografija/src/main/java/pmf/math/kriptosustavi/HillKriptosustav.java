package pmf.math.kriptosustavi;

import static pmf.math.algoritmi.Abeceda.uBroj;
import static pmf.math.algoritmi.Abeceda.uSlovo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Getter;
import lombok.Setter;
import pmf.math.algoritmi.Matrica;
import pmf.math.algoritmi.Vektor;
import pmf.math.baza.dao.TekstGrafDAO.VrstaSimulacije;

@Setter
@Getter
public class HillKriptosustav {

  private boolean exitThread;
  private int napredak = 0;

  public String sifriraj(String otvoreniTekst, Matrica kljuc) {
    int m = kljuc.getN();
    nadopuniTekst(otvoreniTekst, m);
    StringBuilder sifrat = new StringBuilder();
    Arrays.stream(otvoreniTekst.split(" ")).forEach(podtekst -> {
      Vektor x = new Vektor(pretvoriUintArray(podtekst.toCharArray()));
      Vektor y = new Vektor(x.pomnozi(kljuc).getVektor()).moduliraj(26);
      char[] izlaz = pretvoriUcharArray(y.getVektor());
      for (char c : izlaz) {
        sifrat.append(c);
      }
      sifrat.append(" ");
    });
    return sifrat.toString().trim();
  }

  public String desifriraj(String sifrat, Matrica kljuc) {
    return sifriraj(sifrat, kljuc);
  }

  public String[][] izracunajKljuc(int dimenzija, String otvoreniTekst, String sifrat) {
    napredak = 0;
    Map<Integer, List<Vektor>> kandidati = new HashMap<>();
    for (int l = 0; l < dimenzija; l++) {
      kandidati.put(l, new LinkedList<Vektor>());
    }
    for (int i = 0; i < dimenzija; i++) {
      int[] maxBrojac = new int[dimenzija];
      int[] brojac = new int[dimenzija];
      for (int j = 0; j < dimenzija; j++) {
        maxBrojac[j] = 26;
        brojac[i] = -25;
      }
      do {
        napredak = Math.round(
            100.0f / dimenzija * (i + (float) brojac[dimenzija - 1] / maxBrojac[dimenzija - 1]));
        if (exitThread) {
          return null;
        }
        if (Arrays.stream(brojac).max().getAsInt() == 0) {
          continue;
        }
        Vektor k = new Vektor(brojac);
        if (provjeriVektor(k, otvoreniTekst, sifrat, i)) {
          kandidati.get(i).add(k);
        }
      } while (dodajNaBrojac(brojac, maxBrojac, 0) && kandidati.get(i).size() < 100);
      if (kandidati.get(i).size() == 0) {
        return null;
      }
    }

    int[] maxBrojac = new int[dimenzija];
    int[] brojac = new int[dimenzija];
    for (int l = 0; l < dimenzija; l++) {
      maxBrojac[l] = kandidati.get(l).size();
    }
    do {
      napredak = Math.round(IntStream.of(brojac).sum() * 100.0f / IntStream.of(maxBrojac).sum());
      if (exitThread) {
        return null;
      }
      int[][] podaci = new int[dimenzija][dimenzija];
      for (int p = 0; p < dimenzija; p++) {
        int[] vektor = kandidati.get(p).get(brojac[p]).getVektor();
        for (int q = 0; q < dimenzija; q++) {
          podaci[q][p] = vektor[q];
        }
      }
      Matrica K = new Matrica(podaci);
      if (K.regularna()) {
        return K.pretvoriUStringArray();
      }
    }
    while (dodajNaBrojac(brojac, maxBrojac, 0));
    return null;
  }

  public boolean dodajNaBrojac(int[] brojac, int[] maxBrojac, int dubina) {
    if (dubina == brojac.length) {
      return false;
    }
    if (brojac[dubina] + 1 == maxBrojac[dubina]) {
      brojac[dubina] = 0;
      return dodajNaBrojac(brojac, maxBrojac, dubina + 1);
    } else {
      brojac[dubina]++;
    }
    return true;
  }

  public boolean provjeriVektor(Vektor k, String x, String y, int pozicija) {
    String[] xText = x.split(" ");
    String[] yText = y.split(" ");
    for (int i = 0; i < xText.length; i++) {
      Vektor xVektor = new Vektor(pretvoriUintArray(xText[i].toCharArray()));
      int Y = uBroj(yText[i].toCharArray()[pozicija]);
      if (xVektor.pomnozi(k) % 26 != Y) {
        return false;
      }
    }
    return true;
  }

  private void nadopuniTekst(String tekst, int puta) {
    tekst += IntStream.range(0, puta).mapToObj(i -> "A").collect(Collectors.joining(""));
  }

  public int[] pretvoriUintArray(char[] array) {
    int[] rezultat = new int[array.length];
    for (int i = 0; i < array.length; i++) {
      rezultat[i] = uBroj(array[i]);
    }
    return rezultat;
  }

  public char[] pretvoriUcharArray(int[] array) {
    char[] rezultat = new char[array.length];
    for (int i = 0; i < array.length; i++) {
      rezultat[i] = uSlovo(array[i]);
    }
    return rezultat;
  }

  public static int[] simuliraj(int[] duljineTekstova, VrstaSimulacije vrstaSimulacije, int brojIteracija) {
    return switch (vrstaSimulacije) {
      case SIFRIRAJ -> duljineTekstova;
      case DESIFRIRAJ -> new int[duljineTekstova.length];
    };
  }
}
