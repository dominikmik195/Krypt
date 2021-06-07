package pmf.math.kriptosustavi;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Assert;

public class CezarKriptosustavTest extends TestCase {

  private CezarKriptosustav stroj;

  @Before
  public void setUp() {
    stroj = new CezarKriptosustav(5);
    assertNotNull(stroj);
  }

  public void testSifriraj() {
    String otvoreniTekst = "krip t og rafija";
    String kriviOtvoreniTekst = "kriptografijač";

    String sifrat = stroj.sifriraj(otvoreniTekst);
    String kriviSifrat = stroj.sifriraj(kriviOtvoreniTekst);

    assertEquals("PWNUY TLWFK NOF", sifrat);
    assertEquals("", kriviSifrat);
  }

  public void testDesifriraj() {
    String sifrat = "pw n u ytlw fknof";
    String kriviSifrat = "pwnuytlwfknofč";

    String otvoreniTekst = stroj.desifriraj(sifrat);
    String kriviOtvoreniTekst = stroj.desifriraj(kriviSifrat);

    assertEquals("KRIPT OGRAF IJA", otvoreniTekst);
    assertEquals("", kriviOtvoreniTekst);
  }

  public void testDohvatiPermutacijuSlova() {
    char[] permutacija = stroj.dohvatiPermutacijuSlova();

    Assert.assertArrayEquals("FGHIJKLMNOPQRSTUVWXYZABCDE".toCharArray(), permutacija);
  }

  public void testDohvatiPermutacijuString() {
    String permutacija = stroj.dohvatiPermutacijuString();

    assertEquals("FGHIJKLMNOPQRSTUVWXYZABCDE", permutacija);
  }
}
