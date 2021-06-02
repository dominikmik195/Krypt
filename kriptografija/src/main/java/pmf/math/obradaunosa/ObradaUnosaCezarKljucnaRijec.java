package pmf.math.obradaunosa;

import pmf.math.kriptosustavi.CezarKljucnaRijecKriptosustav;

public class ObradaUnosaCezarKljucnaRijec extends ObradaUnosa {
  public static String sifriraj(int pomak, String kljucnaRijec, String tekst) {
    tekst = ocisti(tekst);
    kljucnaRijec = ocisti(kljucnaRijec);
    if (kriviUnos(tekst) || kriviUnos(kljucnaRijec)) return "";

    return (new CezarKljucnaRijecKriptosustav(kljucnaRijec, pomak)).sifriraj(tekst);
  }

  public static String desifriraj(int pomak, String kljucnaRijec, String tekst) {
    tekst = ocisti(tekst);
    kljucnaRijec = ocisti(kljucnaRijec);
    if (kriviUnos(tekst) || kriviUnos(kljucnaRijec)) return "";

    return (new CezarKljucnaRijecKriptosustav(kljucnaRijec, pomak)).desifriraj(tekst);
  }

  public static String permutacija(int pomak, String kljucnaRijec) {
    kljucnaRijec = ocisti(kljucnaRijec);
    if (kriviUnos(kljucnaRijec)) return "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z";

    CezarKljucnaRijecKriptosustav stroj = new CezarKljucnaRijecKriptosustav(kljucnaRijec, pomak);
    char[] permutacija = stroj.dohvatiPermutacijuSlova();
    StringBuilder noviLabel = new StringBuilder(permutacija.length);
    for (char slovo : permutacija)
      noviLabel.append(slovo).append(" ");

    return noviLabel.toString();
  }
}
