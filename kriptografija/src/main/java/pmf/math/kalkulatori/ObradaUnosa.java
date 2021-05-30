package pmf.math.kalkulatori;

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
  public static boolean kriviUnos(String tekst) {
    // Dozvoljena su samo slova engleske abecede.
    if (!tekst.matches("^[a-zA-Z]*$")) {
      // TODO: Ispis u konzolu.
      System.out.println("Tekst smije sadržavati samo slova engleske abecede");
      return true;
    } else return false;
  }

  public static String ocisti(String tekst) {
    return tekst.replaceAll("\\s+", "").toUpperCase(Locale.ROOT);
  }
}
