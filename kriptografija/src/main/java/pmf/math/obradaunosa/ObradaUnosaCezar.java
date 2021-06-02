package pmf.math.obradaunosa;

import pmf.math.kriptosustavi.CezarKriptosustav;

public class ObradaUnosaCezar extends ObradaUnosa {
  public static String sifriraj(int pomak, String tekst) {
    tekst = ocisti(tekst);
    if (kriviUnos(tekst)) return "";

    return (new CezarKriptosustav(pomak)).sifriraj(tekst);
  }

  public static String desifriraj(int pomak, String tekst) {
    tekst = ocisti(tekst);
    if (kriviUnos(tekst)) return "";

    return (new CezarKriptosustav(pomak)).desifriraj(tekst);
  }

  public static String permutacija(int pomak) {
    CezarKriptosustav stroj = new CezarKriptosustav(pomak);
    char[] permutacija = stroj.dohvatiPermutacijuSlova();
    StringBuilder noviLabel = new StringBuilder(permutacija.length);
    for (char slovo : permutacija)
      noviLabel.append(slovo).append(" ");

    return noviLabel.toString();
  }
}
