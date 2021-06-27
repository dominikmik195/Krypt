package pmf.math.filteri;

import static pmf.math.algoritmi.Abeceda.filtrirajTekst;

import pmf.math.kriptosustavi.PlayfairKriptosustav.Jezik;

public class PlayfairFilter {

  public static String filtriraj(String ulaz, Jezik jezik) {
    String tekst = filtrirajTekst(ulaz);
    switch (jezik) {
      case HRVATSKI -> tekst = tekst.replaceAll("W", "V");
      case ENGLESKI -> tekst = tekst.replaceAll("J", "I");
    }
    StringBuilder izlaz = new StringBuilder();
    for (int i = 0; i < tekst.length(); i += 2) {
      izlaz.append(tekst, i, Math.min(i + 2, tekst.length()))
          .append(i + 2 < tekst.length() ? " " : "");
    }
    return izlaz.toString();
  }

}
