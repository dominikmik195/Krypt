package pmf.math.kriptosustavi;

import junit.framework.TestCase;
import org.junit.Before;

public class ElGamalKriptosustavTest extends TestCase {

  static {
    System.loadLibrary("TeorijaBrojeva");
  }

  private ElGamalKriptosustav stroj;

  @Before
  public void setUp() {
    stroj = new ElGamalKriptosustav(107, 2, 94);
    stroj.setOtvoreniTekst(66);
    stroj.setTajniBroj(45);
    assertNotNull(stroj);
  }

  public void testSifriraj() {
    stroj.sifriraj();
    assertEquals("(28, 9)", stroj.vratiSifrat());
  }

  public void testDesifriraj() {
    stroj.setTajniKljuc(67);
    stroj.pohraniSifrat("(28, 9)");
    assertEquals(66, stroj.desifriraj());
  }

  public void testNoviBeta() {
    assertEquals(94, ElGamalKriptosustav.noviBeta(107, 2, 67));
  }
}
