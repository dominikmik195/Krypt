package pmf.math.kriptosustavi;

import junit.framework.TestCase;
import pmf.math.algoritmi.Matrica;

public class HillKriptosustavTest extends TestCase {

  private final HillKriptosustav hillKriptosustav = new HillKriptosustav();

  public void testSifriraj() {
    String otvoreniTekst, sifrat;
    Matrica kljuc = new Matrica(
        new int[][]{
            {6, 0, 25},
            {3, 5, 2},
            {17, 1, 13}
        });

    otvoreniTekst = "OVO JET EST NIP RIM JER";
    sifrat = "VPC ZNM LFT TDQ SAZ RLM";
    assertEquals(hillKriptosustav.sifriraj(otvoreniTekst, kljuc), sifrat);
  }

  public void testDesifriraj() {
    String otvoreniTekst, sifrat;
    Matrica kljuc = new Matrica(
        new int[][]{
            {4, -1},
            {15, -4}
        });

    otvoreniTekst = "TE ST IR AN JE";
    sifrat = "GR TK BC NA SB";
    assertEquals(hillKriptosustav.desifriraj(sifrat, kljuc), otvoreniTekst);
  }

  public void testIzracunajKljuc() {
    String otvoreniTekst, sifrat;

    otvoreniTekst = "UTO RAK";
    sifrat = "SPQ DYY";
    assertNotNull(hillKriptosustav.izracunajKljuc(3, otvoreniTekst, sifrat));

    otvoreniTekst = "OVOJ EPRO BNIT EKST";
    sifrat = "DNSJ AHWE BUDO IUWA";
    assertNotNull(hillKriptosustav.izracunajKljuc(4, otvoreniTekst, sifrat));

    otvoreniTekst = "OV ON IJ ED OB RO";
    sifrat = "DW AF WA EQ DS AM";
    assertNull(hillKriptosustav.izracunajKljuc(2, otvoreniTekst, sifrat));
  }

}