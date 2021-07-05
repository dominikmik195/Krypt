package pmf.math.kriptosustavi;

import junit.framework.TestCase;
import org.junit.Before;

import java.util.Vector;

public class StupcanaTranspozicijaSustavTest extends TestCase {

  private StupcanaTranspozicijaSustav stroj;
  private Vector<Integer> vals;

  @Before
  public void setUp() throws Exception {
    super.setUp();
    stroj = new StupcanaTranspozicijaSustav();
    vals = new Vector<>();
    vals.add(2);
    vals.add(5);
    vals.add(1);
    vals.add(4);
    vals.add(3);
    stroj.setBrojStupaca(5);
    stroj.setVrijednosti(vals);
    assertNotNull(stroj);
  }

  public void testFormatirajOtvoreniTekst() {
    String otvTekst = "  nE  Ki.!? Tek\n" + "   St.:";
    stroj.setOtvoreniTekst(otvTekst);
    String rezultat = stroj.formatirajOtvoreniTekst();
    assertEquals("NEKIT\nEKST", rezultat);
  }

  public void testSifriraj() {
    String otvTekst = "KRIPTOGRAFIJAIKRIPTOANALIZA";
    stroj.setOtvoreniTekst(otvTekst);
    stroj.sifriraj();
    assertEquals("""
            IRAPAX
            KOIRAZ
            TFKOIX
            PAITLX
            RGJINA
            """, stroj.getSifrat());
  }

  public void testDesifriraj() {
    String sifrat = "IRAPAXKOIRAZTFKOIXPAITLXRGJINA";
    stroj.setSifrat(sifrat);
    stroj.desifriraj();
    assertEquals("KRIPT\nOGRAF\nIJAIK\nRIPTO\nANALI\nZAXXX\n", stroj.getOtvoreniTekst());
  }

  public void testProvjeriVrijednosti() {
    assertTrue(stroj.provjeriVrijednosti());
    vals.clear();
    vals.add(2); vals.add(2); vals.add(3); vals.add(4);
    stroj.setBrojStupaca(4);
    assertFalse(stroj.provjeriVrijednosti());
  }
}
