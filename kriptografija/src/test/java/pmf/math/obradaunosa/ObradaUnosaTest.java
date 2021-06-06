package pmf.math.obradaunosa;

import junit.framework.TestCase;

public class ObradaUnosaTest extends TestCase {

  public void testKriviUnos() {
    String kriviTekst = "krivi tekst!";
    String tocanTekst = "tocan tekst";

    assertTrue(ObradaUnosa.kriviUnos(kriviTekst));
    assertFalse(ObradaUnosa.kriviUnos(tocanTekst));

    int[] krivaPermutacija = new int[26];
    int[] tocnaPermutacija = new int[26];
    for (int i = 0; i < 26; i++) krivaPermutacija[i] = tocnaPermutacija[i] = i;
    krivaPermutacija[0] = 5;
    tocnaPermutacija[0] = tocnaPermutacija[1] = -1;

    assertTrue(ObradaUnosa.kriviUnos(krivaPermutacija));
    assertFalse(ObradaUnosa.kriviUnos(tocnaPermutacija));
  }

  public void testOcisti() {
    String tekst = "tekst za pocistiti";

    assertEquals("TEKSTZAPOCISTITI", ObradaUnosa.ocisti(tekst));
  }
}
