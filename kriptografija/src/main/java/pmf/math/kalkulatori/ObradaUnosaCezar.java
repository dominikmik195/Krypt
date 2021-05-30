package pmf.math.kalkulatori;

import pmf.math.kriptosustavi.Cezar;

public class ObradaUnosaCezar extends ObradaUnosa {
  public static String sifriraj(int pomak, String tekst) {
    tekst = ocisti(tekst);
    if (kriviUnos(tekst)) return "";

    return (new pmf.math.kriptosustavi.Cezar(pomak)).sifriraj(tekst);
  }

  public static String desifriraj(int pomak, String tekst) {
    tekst = ocisti(tekst);
    if (kriviUnos(tekst)) return "";

    return (new Cezar(pomak)).desifriraj(tekst);
  }

  public static String permutacija(int pomak) {
    Cezar stroj = new pmf.math.kriptosustavi.Cezar(pomak);
    char[] permutacija = stroj.dohvatiPermutacijuSlova();
    StringBuilder noviLabel = new StringBuilder(permutacija.length);
    for (char slovo : permutacija) {
      noviLabel.append(slovo).append(" ");
    }
    return noviLabel.toString();
  }
}
