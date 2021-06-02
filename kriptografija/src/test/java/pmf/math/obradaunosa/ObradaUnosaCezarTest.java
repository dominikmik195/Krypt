package pmf.math.obradaunosa;

import junit.framework.TestCase;

public class ObradaUnosaCezarTest extends TestCase {

  public void testSifriraj() {
    int pomak = 5;
    String otvoreniTekst = "kri pto grafija";
    String kriviTekst = "kri pto grafijač";

    assertEquals("PWNUY TLWFK NOF", ObradaUnosaCezar.sifriraj(pomak, otvoreniTekst));
    assertEquals("", ObradaUnosaCezar.sifriraj(pomak, kriviTekst));
  }

  public void testDesifriraj() {
    int pomak = 5;
    String sifrat = "PwNUy TLWfKNOF";
    String kriviSifrat = "PwNUyčTLWfKNOF";

    assertEquals("KRIPT OGRAF IJA", ObradaUnosaCezar.desifriraj(pomak, sifrat));
    assertEquals("", ObradaUnosaCezar.desifriraj(pomak, kriviSifrat));
  }

  public void testPermutacija() {
    int pomak = 5;

    assertEquals(
        "F G H I J K L M N O P Q R S T U V W X Y Z A B C D E ",
        ObradaUnosaCezar.permutacija(pomak));
  }
}
