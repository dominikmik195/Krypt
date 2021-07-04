package pmf.math.obradaunosa;

import java.util.Locale;

/*
Sadrži alate za validaciju i sanitizaciju teksta te ispis u konzolu u slučaju greške. Ovi alati se dodatno
mogu poboljšati tako da dozvole unos nekih znakova (npr. interpunkcijskih), ali ih onda uklanjaju
prilikom sanitizacije.

Osim toga, klase koje je nasljeđuju sadrže metode za pozivanje (de)šifriranja odgovarajućih klasa
unutar paketa pmf.math.kriptosustavi. Ove klase su stvorene zbog lakšeg testiranja, kao i izdvajanja
logike aplikacije izvan datoteka koje pripadaju formama.
 */

public class ObradaUnosa {
  public static String ocisti(String tekst) {
    return tekst.replaceAll("\s+", "").toUpperCase(Locale.ROOT);
  }

  public static boolean kriviUnos(String tekst) {
    // Dozvoljena su samo slova engleske abecede i bjeline (koje se čiste).
    return !tekst.matches("^[a-zA-Z\s]*$");
  }

  public static boolean dobarUnosHrvEng(String tekst) {
    // Dozvoljena su slova hrvatske i engleske abecede, interpunkcijski znakovi i bjeline (koje se čiste).
    if(tekst.strip().equals("")) return false;
    return tekst.matches("^[a-zčćžšđA-ZČĆŽŠĐ,.;:?!\s\n]*$");
  }

  public static String ocistiStupcana(String tekst) {
    return tekst.replaceAll("\s+", "")
            .replaceAll("\\.", "")
            .replaceAll(",", "")
            .replaceAll(";", "")
            .replaceAll(":", "")
            .replaceAll("!", "")
            .replaceAll("\\?", "")
            .replaceAll("\n", "")
            .toUpperCase();
  }

  public static boolean kriviUnos(int[] permutacija) {
    // Niti jedan broj u permutaciji (osim -1) se ne smije pojaviti dvaput.
    int[] counts = new int[26];
    for (int i = 0; i < 26; i++) if (permutacija[i] != -1) counts[permutacija[i]]++;
    for (int i = 0; i < 26; i++) if (counts[i] > 1) return true;

    return false;
  }
}
