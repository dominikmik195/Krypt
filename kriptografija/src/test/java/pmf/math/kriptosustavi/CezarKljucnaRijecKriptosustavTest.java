package pmf.math.kriptosustavi;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;

public class CezarKljucnaRijecKriptosustavTest extends TestCase {

  CezarKljucnaRijecKriptosustav stroj;

  @Before
  public void setUp() {
    stroj = new CezarKljucnaRijecKriptosustav("KRIPTOGRAFIJA", 8);
    assertNotNull(stroj);
  }

  public void testSifriraj() {
    String otvoreniTekst =
        "MATEMATIKAJEZNANSTVENADISCIPLINANASTALAPROUCAVANJEMBROJEVAIGEOMETRIJSKIHODNOSA";

    String sifrat = stroj.sifriraj(otvoreniTekst);

    assertEquals(
        "TQCWT QCKIQ RWNOQ OBCEW OQVKB UKAPK OQOQB CQPQA JGDUQ EQORW TSJGR WEQKY WGTWC JKRBI KZGVO GBQ",
        sifrat);
  }

  public void testDesifriraj() {
    String sifrat =
        "TQCWTQCKIQRWNOQOBCEWOQVKBUKAPKOQOQBCQPQAJGDUQEQORWTSJGRWEQKYWGTWCJKRBIKZGVOGBQ";

    String otvoreniTekst = stroj.desifriraj(sifrat);

    assertEquals(
        "MATEM ATIKA JEZNA NSTVE NADIS CIPLI NANAS TALAP ROUCA VANJE MBROJ EVAIG EOMET RIJSK IHODN OSA",
        otvoreniTekst);
  }

  public void testDohvatiPermutacijuSlova() {
    char[] permutacija = stroj.dohvatiPermutacijuSlova();

    Assert.assertArrayEquals("QSUVWXYZKRIPTOGAFJBCDEHLMN".toCharArray(), permutacija);
  }
}
