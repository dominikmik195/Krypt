package pmf.math.kriptosustavi;

import java.net.URL;
import java.util.Objects;
import junit.framework.TestCase;
import org.junit.Before;

import java.io.File;
import pmf.math.MainClass;

public class ElGamalKriptosustavTest extends TestCase {

  private ElGamalKriptosustav stroj;

  @Before
  public void setUp() {
    URL url = Objects.requireNonNull(
        MainClass.class.getClassLoader().getResource("TeorijaBrojeva.dll"));
    System.load(url.getPath());
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
