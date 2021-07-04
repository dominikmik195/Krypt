package pmf.math.filteri;

import static pmf.math.algoritmi.Abeceda.filtrirajTekst;

import pmf.math.kalkulatori.VigenereKalkulator.NacinSifriranja;
import pmf.math.kriptosustavi.PlayfairKriptosustav.Jezik;

public class VigenereFilter {

  public static String filtriraj(String ulaz, int m, NacinSifriranja nacinSifriranja) {
    String tekst = filtrirajTekst(ulaz);
    if (m == 0 || nacinSifriranja == NacinSifriranja.VIGENEREOV_KVADRAT) {
      return tekst;
    }
    StringBuilder izlaz = new StringBuilder();

    for (int i = 0; i < tekst.length(); i += m) {
      izlaz.append(tekst, i, Math.min(i + m, tekst.length()))
          .append(i + m < tekst.length() ? " " : "");
    }

    return izlaz.toString();
  }

}
