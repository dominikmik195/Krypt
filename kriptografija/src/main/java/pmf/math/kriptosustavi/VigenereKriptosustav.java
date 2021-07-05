package pmf.math.kriptosustavi;

import static pmf.math.algoritmi.Abeceda.inverz;
import static pmf.math.algoritmi.Abeceda.zbroj;

import java.util.Arrays;
import pmf.math.algoritmi.Matrica;
import pmf.math.baza.dao.TekstGrafDAO.VrstaSimulacije;
import pmf.math.filteri.HillFilter;
import pmf.math.filteri.VigenereFilter;
import pmf.math.kalkulatori.VigenereKalkulator.NacinSifriranja;
import pmf.math.konstante.HillMatrice;
import pmf.math.pomagala.GeneratorTeksta;
import pmf.math.pomagala.Stoperica;

public class VigenereKriptosustav {

  public String vigenere(String otvoreniTekst, String kljuc, NacinSifriranja nacinSifriranja,
      boolean zbrajanje) {
    if (otvoreniTekst.equals("")) {
      return "";
    }
    char[] kljucArray = kljuc.toCharArray();
    StringBuilder sifrat = new StringBuilder();

    switch (nacinSifriranja) {
      case PONAVLJAJUCI -> {
        Arrays.stream(otvoreniTekst.split(" ")).forEach(blok -> {
          char[] blokArray = blok.toCharArray();
          for (int i = 0; i < blokArray.length; i++) {
            if (zbrajanje) {
              sifrat.append(zbroj(blokArray[i], kljucArray[i]));
            } else {
              sifrat.append(zbroj(blokArray[i], inverz(kljucArray[i])));
            }
          }
          sifrat.append(" ");
        });
        sifrat.deleteCharAt(sifrat.length() - 1);
        return sifrat.toString();
      }

      case VIGENEREOV_KVADRAT -> {
        char[] tekstArray = otvoreniTekst.toCharArray();
        int m = kljucArray.length;
        for (int i = 0; i < tekstArray.length; i++) {
          if (i < m) {
            if (zbrajanje) {
              sifrat.append(zbroj(tekstArray[i], kljucArray[i]));
            } else {
              sifrat.append(zbroj(tekstArray[i], inverz(kljucArray[i])));
            }
          } else {
            if (zbrajanje) {
              sifrat.append(zbroj(tekstArray[i], tekstArray[i - m]));
            } else {
              sifrat.append(zbroj(tekstArray[i], inverz(sifrat.charAt(i - m))));
            }
          }
        }
        return sifrat.toString();
      }

      default -> {
        return "";
      }
    }
  }

  public String sifriraj(String otvoreniTekst, String kljuc, NacinSifriranja nacinSifriranja) {
    return vigenere(otvoreniTekst, kljuc, nacinSifriranja, true);
  }

  public String desifriraj(String sifrat, String kljuc, NacinSifriranja nacinSifriranja) {
    return vigenere(sifrat, kljuc, nacinSifriranja, false);
  }

  public static int[] simuliraj(int[] duljineTekstova, VrstaSimulacije vrstaSimulacije, int brojIteracija) {
    VigenereKriptosustav vigenereKriptosustav = new VigenereKriptosustav();
    String kljuc = "VIGENERE";
    NacinSifriranja nacinSifriranja = NacinSifriranja.PONAVLJAJUCI;
    int[] vremenaIzvodenja = new int[duljineTekstova.length];
    Stoperica stoperica = new Stoperica();
    for (int i = 0; i < duljineTekstova.length; i++) {
      for(int t = 0; t < brojIteracija; t++) {
        String tekst = VigenereFilter.filtriraj(
            GeneratorTeksta.generirajTekst(duljineTekstova[i]), kljuc.length(),
            nacinSifriranja);
        System.out.println(duljineTekstova[i]);
        System.out.println(tekst);
        stoperica.resetiraj();
        stoperica.pokreni();
        switch (vrstaSimulacije) {
          case SIFRIRAJ -> vigenereKriptosustav.sifriraj(tekst, kljuc, nacinSifriranja);
          case DESIFRIRAJ -> vigenereKriptosustav.desifriraj(tekst, kljuc, nacinSifriranja);
        }
        stoperica.zaustavi();
        vremenaIzvodenja[i] += stoperica.vrijeme();
      }
      vremenaIzvodenja[i] = vremenaIzvodenja[i] / brojIteracija;
    }

    return vremenaIzvodenja;
  }

}
