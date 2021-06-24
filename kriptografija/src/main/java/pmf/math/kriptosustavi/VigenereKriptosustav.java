package pmf.math.kriptosustavi;

import static pmf.math.algoritmi.Abeceda.inverz;
import static pmf.math.algoritmi.Abeceda.zbroj;

import java.util.Arrays;
import pmf.math.kalkulatori.VigenereKalkulator.NacinSifriranja;

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

}
