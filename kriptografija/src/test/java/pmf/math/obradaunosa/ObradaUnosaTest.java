package pmf.math.obradaunosa;

import junit.framework.TestCase;

public class ObradaUnosaTest extends TestCase {

  public void testKriviUnos() {
    String kriviTekst = "krivi tekst!";
    String tocanTekst = "tocan tekst";

    assertTrue(ObradaUnosa.kriviUnos(kriviTekst));
    assertFalse(ObradaUnosa.kriviUnos(tocanTekst));
  }

  public void testOcisti() {
    String tekst = "tekst za pocistiti";

    assertEquals("TEKSTZAPOCISTITI", ObradaUnosa.ocisti(tekst));
  }
}
