package pmf.math.filteri;

import static pmf.math.algoritmi.Abeceda.filtrirajTekst;

public class HillFilter {

  public static String filtriraj(String ulaz, int m) {
    String tekst = filtrirajTekst(ulaz);
    StringBuilder izlaz = new StringBuilder();

    for (int i = 0; i < tekst.length(); i += m) {
      izlaz.append(tekst, i, Math.min(i + m, tekst.length()))
          .append(i + m < tekst.length() ? " " : "");
    }

    return izlaz.toString().trim();
  }

  public static String odreziOstatak(String ulaz, int m) {
    String tekst = ulaz.replaceAll(" ", "");
    if (tekst.length() < m) {
      return "";
    }
    return tekst.substring(0, tekst.length() - tekst.length() % m);
  }

}
