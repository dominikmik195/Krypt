package pmf.math.obradaunosa;

import junit.framework.TestCase;
import org.junit.Before;

public class ObradaUnosaElGamalTest extends TestCase {

  @Before
  public void setUp() throws Exception {
    super.setUp();
  }

  public void testProvjeriSifru() {
    assertTrue(ObradaUnosaElGamal.provjeriSifru("(12, 124)"));
    assertFalse(ObradaUnosaElGamal.provjeriSifru("12, 124"));
    assertFalse(ObradaUnosaElGamal.provjeriSifru("123"));
  }
}
