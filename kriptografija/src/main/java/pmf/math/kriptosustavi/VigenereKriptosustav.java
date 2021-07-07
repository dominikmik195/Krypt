package pmf.math.kriptosustavi;

import static pmf.math.algoritmi.Abeceda.inverz;
import static pmf.math.algoritmi.Abeceda.zbroj;

import java.util.Arrays;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import pmf.math.algoritmi.AnalizaTeksta;
import pmf.math.baza.dao.TekstGrafDAO.VrstaSimulacije;
import pmf.math.filteri.VigenereFilter;
import pmf.math.kalkulatori.VigenereKalkulator.NacinSifriranja;
import pmf.math.konstante.FrekvencijeSlova;
import pmf.math.pomagala.GeneratorTeksta;
import pmf.math.pomagala.Stoperica;

@Setter
@Getter
public class VigenereKriptosustav {

  private boolean exitThread;
  private int napredak = 0;

  public enum Jezik {
    HRVATSKI,
    ENGLESKI,
    NJEMACKI
  }

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

  public static int[] simuliraj(int[] duljineTekstova, VrstaSimulacije vrstaSimulacije,
      int brojIteracija) {
    VigenereKriptosustav vigenereKriptosustav = new VigenereKriptosustav();
    String kljuc = "VIGENERE";
    NacinSifriranja nacinSifriranja = NacinSifriranja.PONAVLJAJUCI;
    int[] vremenaIzvodenja = new int[duljineTekstova.length];
    Stoperica stoperica = new Stoperica();
    for (int i = 0; i < duljineTekstova.length; i++) {
      for (int t = 0; t < brojIteracija; t++) {
        String tekst = VigenereFilter.filtriraj(
            GeneratorTeksta.generirajTekst(duljineTekstova[i]), kljuc.length(),
            nacinSifriranja);
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

  public int pronadiDuljinuKljuca(Jezik jezik, String sifrat) {
    napredak = 0;
    String tekst = sifrat.replaceAll(" ", "");
    int duljinaTeksta = tekst.length();
    if (duljinaTeksta == 0) {
      return 0;
    }

    double suma, indeks;
    double indeksKoincidencije = switch (jezik) {
      case HRVATSKI -> FrekvencijeSlova.INDEKS_KOINCIDENCIJE_HRVATSKI;
      case ENGLESKI -> FrekvencijeSlova.INDEKS_KOINCIDENCIJE_ENGLESKI;
      case NJEMACKI -> FrekvencijeSlova.INDEKS_KOINCIDENCIJE_NJEMACKI;
    };
    AnalizaTeksta analizaTeksta = new AnalizaTeksta();

    // m = potencijalna duljina ključa
    for(int m = 1; m < 100; m++) {
      indeks = 0;
      // razdvajanje teksta na m traka
      String[] podStringovi = new String[m];
      for(int i = 0; i < m; i++) {
        StringBuilder string = new StringBuilder();
        int j = i;
        while(j < duljinaTeksta) {
          string.append(tekst.charAt(j));
          j += m;
        }
        podStringovi[i] = string.toString();
      }
      for (String podString : podStringovi) {
        suma = 0;
        Integer[] slova = analizaTeksta.pronadiSlova(podString).values().toArray(new Integer[26]);
        for(Integer slovo : slova) {
          suma += Math.pow((double) slovo / (double) podString.length(), 2);
        }
        indeks += suma;
      }
      indeks = indeks / m;
      if(indeks > indeksKoincidencije * 0.9) {
        return m;
      }
      else {
        napredak++;
      }
    }

    return 0;
  }

}
