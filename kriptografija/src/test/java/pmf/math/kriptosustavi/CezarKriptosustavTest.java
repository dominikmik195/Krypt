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
    String otvoreniTekst = "kriptografija";

    String sifrat = stroj.sifriraj(otvoreniTekst);

    assertEquals("PWNUY TLWFK NOF", sifrat);
  }

  public void testDesifriraj() {
    String sifrat = "pwnuytlwfknof";

    String otvoreniTekst = stroj.desifriraj(sifrat);

    assertEquals("KRIPT OGRAF IJA", otvoreniTekst);
  }

  public void testDohvatiPermutacijuSlova() {
    char[] permutacija = stroj.dohvatiPermutacijuSlova();

    Assert.assertArrayEquals("FGHIJKLMNOPQRSTUVWXYZABCDE".toCharArray(), permutacija);
  }
}
