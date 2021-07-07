package pmf.math.kriptosustavi;

import junit.framework.TestCase;
import pmf.math.kalkulatori.VigenereKalkulator.NacinSifriranja;
import pmf.math.kriptosustavi.VigenereKriptosustav.Jezik;

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

  public void testPronadiKljuc() {
    String sifrat =
          "GSIQITUKQIEAOHRVUGLTAZGHXUHLPJMRTTNQRBZIAVBTG"
        + "QTBYMYAIVOMZTAIXJBTEDEWVQWADVWGOOKNQNTCIPEGPY"
        + "BOKUSECNWELLCPZUMIVWFUIJMYATUEXISLMZTNPGUJHTM"
        + "ERXJSYSIVWABGVWFDTZILNTIEDEFJMFAMPNQZBRSDIZPR"
        + "MLGVKFEDZXMVXVQMJXWSLEEQRMAEPRUJXIMFNT";
    Jezik jezik = Jezik.HRVATSKI;
    int m = 5;

    assertEquals(vigenereKriptosustav.pronadiDuljinuKljuca(jezik, sifrat), m);


    sifrat = "OVOJENEKITEKSTOVOJENEKITEKST";
    m = 1;

    assertEquals(vigenereKriptosustav.pronadiDuljinuKljuca(jezik, sifrat), m);

    sifrat = "";
    m = 0;

    assertEquals(vigenereKriptosustav.pronadiDuljinuKljuca(jezik, sifrat), m);
  }

}
