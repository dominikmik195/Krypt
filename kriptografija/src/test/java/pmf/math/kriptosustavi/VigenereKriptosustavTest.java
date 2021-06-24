package pmf.math.kriptosustavi;

import junit.framework.TestCase;
import pmf.math.kalkulatori.VigenereKalkulator.NacinSifriranja;

public class VigenereKriptosustavTest extends TestCase {

  private final VigenereKriptosustav vigenereKriptosustav = new VigenereKriptosustav();

  public void testSifriraj() {
    String kljuc = "BROJ";
    String otvoreniTekst, sifrat;
    NacinSifriranja nacin;

    otvoreniTekst = "KRIP TOLO GIJA";
    sifrat = "LIWY UFZX HZXJ";
    nacin = NacinSifriranja.PONAVLJAJUCI;

    assertEquals(vigenereKriptosustav.sifriraj(otvoreniTekst, kljuc, nacin), sifrat);
    assertEquals(vigenereKriptosustav.desifriraj(sifrat, kljuc, nacin), otvoreniTekst);

    otvoreniTekst = "KRIPTOLOGIJA";
    sifrat = "LIWYDFTDZWUO";
    nacin = NacinSifriranja.VIGENEREOV_KVADRAT;

    assertEquals(vigenereKriptosustav.sifriraj(otvoreniTekst, kljuc, nacin), sifrat);
    assertEquals(vigenereKriptosustav.desifriraj(sifrat, kljuc, nacin), otvoreniTekst);
  }

}
