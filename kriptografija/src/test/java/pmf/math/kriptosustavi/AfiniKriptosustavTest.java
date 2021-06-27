package pmf.math.kriptosustavi;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AfiniKriptosustavTest {

  private AfiniKriptosustav stroj;
  private AfiniKriptosustav kriviStroj;

  @Before
  public void setUp() {
    stroj = new AfiniKriptosustav(9, 2);
    kriviStroj = new AfiniKriptosustav(2, 4);

    assertNotNull(stroj);
    assertNotNull(kriviStroj);
  }

  @Test
  public void sifriraj() {
    String otvoreniTekst = "KRIPTOgrafija ZNAcI TAJNOPIS";
    String kriviOtvoreniTekst = "Kriptografija znaci tajnopis!";

    String sifrat = stroj.sifriraj(otvoreniTekst);
    String kriviSifrat = stroj.sifriraj(kriviOtvoreniTekst);
    String kriviSifrat2 = kriviStroj.sifriraj(otvoreniTekst);

    assertEquals("OZWHR YEZCV WFCTP CUWRC FPYHW I", sifrat);
    assertEquals("", kriviSifrat);
    assertEquals("", kriviSifrat2);
  }

  @Test
  public void desifriraj() {
    String sifrat = "OZWHR YEZCVWF    Ctp CUWRC FPYHW I";
    String kriviSifrat = "OZWHR!";

    String otvoreniTekst = stroj.desifriraj(sifrat);
    String kriviOtvoreniTekst = stroj.desifriraj(kriviSifrat);
    String kriviOtvoreniTekst2 = kriviStroj.desifriraj(kriviSifrat);

    assertEquals("KRIPT OGRAF IJAZN ACITA JNOPI S", otvoreniTekst);
    assertEquals("", kriviOtvoreniTekst);
    assertEquals("", kriviOtvoreniTekst2);
  }

  @Test
  public void dohvatiPermutacijuSlova() {
    char[] permutacija = stroj.dohvatiPermutacijuSlova();
    char[] krivaPermutacija = kriviStroj.dohvatiPermutacijuSlova();

    Assert.assertArrayEquals("CLUDMVENWFOXGPYHQZIRAJSBKT".toCharArray(), permutacija);
    Assert.assertArrayEquals("AAAAAAAAAAAAAAAAAAAAAAAAAA".toCharArray(), krivaPermutacija);
  }

  @Test
  public void dohvatiPermutacijuString() {
    String permutacija = stroj.dohvatiPermutacijuString();
    String krivaPermutacija = kriviStroj.dohvatiPermutacijuString();

    assertEquals("CLUDMVENWFOXGPYHQZIRAJSBKT", permutacija);
    assertEquals("AAAAAAAAAAAAAAAAAAAAAAAAAA", krivaPermutacija);
  }
}
