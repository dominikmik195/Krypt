package pmf.math.kriptosustavi;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;

public class CezarKljucnaRijecKriptosustavTest extends TestCase {

  CezarKljucnaRijecKriptosustav stroj;
  CezarKljucnaRijecKriptosustav kriviStroj;

  @Before
  public void setUp() {
    stroj = new CezarKljucnaRijecKriptosustav("KRIPTOGRAFIJA", 8);
    kriviStroj = new CezarKljucnaRijecKriptosustav("KRIPTOGRAFIJAČ", 8);

    assertNotNull(stroj);
    assertNotNull(kriviStroj);
  }

  public void testSifriraj() {
    String otvoreniTekst =
        "Matematika JE ZNANSTVENA DISCIPLINA NASTALA PROUCAVANJEM BROJEVA I GEOMETRIJSKIH ODNOSA";
    String kriviOtvoreniTekst =
        "Matematika JE ZNANSTVENA DISCIPLINA NASTALA PROUČAVANJEM BROJEVA I GEOMETRIJSKIH ODNOSA";

    String sifrat = stroj.sifriraj(otvoreniTekst);
    String kriviSifrat = stroj.desifriraj(kriviOtvoreniTekst);
    String kriviSifrat2 = kriviStroj.desifriraj(otvoreniTekst);

    assertEquals(
        "TQCWT QCKIQ RWNOQ OBCEW OQVKB UKAPK OQOQB CQPQA JGDUQ EQORW TSJGR WEQKY WGTWC JKRBI KZGVO GBQ",
        sifrat);
    assertEquals("", kriviSifrat);
    assertEquals("", kriviSifrat2);
  }

  public void testDesifriraj() {
    String sifrat =
        "TQCWTQCK IQRWNOQOBCEW OQVkbukaPKOQOQBC QPQAJGDUQ EQORWTSJGRWEQKYWGTWCJKRBIKZGVOGBQ";
    String kriviSifrat = "ČABC";

    String otvoreniTekst = stroj.desifriraj(sifrat);
    String kriviOtvoreniTekst = stroj.desifriraj(kriviSifrat);
    String kriviOtvoreniTekst2 = kriviStroj.desifriraj(sifrat);

    assertEquals(
        "MATEM ATIKA JEZNA NSTVE NADIS CIPLI NANAS TALAP ROUCA VANJE MBROJ EVAIG EOMET RIJSK IHODN OSA",
        otvoreniTekst);
    assertEquals("", kriviOtvoreniTekst);
    assertEquals("", kriviOtvoreniTekst2);
  }

  public void testDohvatiPermutacijuSlova() {
    char[] permutacija = stroj.dohvatiPermutacijuSlova();
    char[] krivaPermutacija = kriviStroj.dohvatiPermutacijuSlova();

    Assert.assertArrayEquals("QSUVWXYZKRIPTOGAFJBCDEHLMN".toCharArray(), permutacija);
    Assert.assertArrayEquals("AAAAAAAAAAAAAAAAAAAAAAAAAA".toCharArray(), krivaPermutacija);
  }

  public void testDohvatiPermutacijuString() {
    String permutacija = stroj.dohvatiPermutacijuString();
    String krivaPermutacija = kriviStroj.dohvatiPermutacijuString();

    assertEquals("QSUVWXYZKRIPTOGAFJBCDEHLMN", permutacija);
    assertEquals("AAAAAAAAAAAAAAAAAAAAAAAAAA", krivaPermutacija);
  }
}
