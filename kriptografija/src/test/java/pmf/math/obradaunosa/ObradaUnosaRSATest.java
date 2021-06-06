package pmf.math.obradaunosa;

import junit.framework.TestCase;
import org.junit.Before;
import pmf.math.kriptosustavi.RSAKriptosustav;

public class ObradaUnosaRSATest extends TestCase {

  @Before
  public void setUp() throws Exception {
    super.setUp();
  }

  public void testProvjeriD() {
    assertTrue(ObradaUnosaRSA.provjeriD(3, 11, 3));
    assertFalse(ObradaUnosaRSA.provjeriD(3, 11, 4));
  }

  public void testProvjeriDiE() {
    assertTrue(ObradaUnosaRSA.provjeriDiE(3, 11, 3, 7));
    assertFalse(ObradaUnosaRSA.provjeriDiE(3, 11, 3, 6));
  }

  public void testProvjeriNUmnozakProstih() {
    assertTrue(ObradaUnosaRSA.provjeriNUmnozakProstih(33));
    assertFalse(ObradaUnosaRSA.provjeriNUmnozakProstih(44));
  }
}
