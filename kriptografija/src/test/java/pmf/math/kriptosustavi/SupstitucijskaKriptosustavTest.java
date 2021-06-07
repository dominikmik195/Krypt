package pmf.math.kriptosustavi;

import junit.framework.TestCase;
import org.junit.Assert;

public class SupstitucijskaKriptosustavTest extends TestCase {

  private SupstitucijskaKriptosustav stroj;
  private SupstitucijskaKriptosustav stroj2;
  private SupstitucijskaKriptosustav kriviStroj;

  public void setUp() {
    int[] permutacija =
        new int[] {
          0, 25, 4, 17, 19, 24, 20, 8, 14, 15, 16, 18, 3, 5, 6, 7, 9, 10, 11, 12, 22, 23, 2, 21, 1,
          13
        }; // = "AZERTYUIOPQSDFGHJKLMWXCVBN"
    int[] permutacija2 =
        new int[] {
          -1, -1, -1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23,
          24, 25
        }; // = "???DEFGHIJKLMNOPQRSTUVWXYZ"
    int[] krivaPermutacija =
        new int[] {
          0, 0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24,
          25
        }; // = "AACDEFGHIJKLMNOPQRSTUVWXYZ"

    stroj = new SupstitucijskaKriptosustav(permutacija);
    stroj2 = new SupstitucijskaKriptosustav(permutacija2);
    kriviStroj = new SupstitucijskaKriptosustav(krivaPermutacija);

    assertNotNull(stroj);
    assertNotNull(stroj2);
    assertNotNull(kriviStroj);
  }

  public void testSifriraj() {
    String otvoreniTekst =
        "Matematika JE ZNANSTVENA DISCIPLINA NASTALA PROUCAVANJEM BROJEVA I GEOMETRIJSKIH ODNOSA";
    String kriviOtvoreniTekst =
        "Matematika JE ZNANSTVENA DISCIPLINA NASTALA PROUČAVANJEM BROJEVA I GEOMETRIJSKIH ODNOSA";

    String sifrat = stroj.sifriraj(otvoreniTekst);
    String sifrat2 = stroj2.sifriraj(otvoreniTekst);
    String kriviSifrat = stroj.sifriraj(kriviOtvoreniTekst);
    String kriviSifrat2 = kriviStroj.sifriraj(otvoreniTekst);

    assertEquals(
        "DAMTD AMOQA PTNFA FLMXT FAROL EOHSO FAFAL MASAH KGWEA XAFPT DZKGP TXAOU TGDTM KOPLQ OIGRF GLA",
        sifrat);
    assertEquals(
        "M_TEM _TIK_ JEZN_ NSTVE N_DIS _IPLI N_N_S T_L_P ROU__ V_NJE M_ROJ EV_IG EOMET RIJSK IHODN OS_",
        sifrat2);
    assertEquals("", kriviSifrat);
    assertEquals("", kriviSifrat2);
  }

  public void testDesifriraj() {
    String sifrat =
        "dAmTdAmOqApT nFaFlMxTfA rOlEoHsOf AfAlMaSaHkGwE aXaFpTdZkGpTxAoUtGdTmKoPlQoIgRfGlA";
    String kriviSifrat = "čabc";

    String otvoreniTekst = stroj.desifriraj(sifrat);
    String otvoreniTekst2 = stroj2.desifriraj(sifrat);
    String kriviOtvoreniTekst = stroj.desifriraj(kriviSifrat);
    String kriviOtvoreniTekst2 = kriviStroj.desifriraj(sifrat);

    assertEquals(
        "MATEM ATIKA JEZNA NSTVE NADIS CIPLI NANAS TALAP ROUCA VANJE MBROJ EVAIG EOMET RIJSK IHODN OSA",
        otvoreniTekst);
    assertEquals(
        "D_MTD _MOQ_ PTNF_ FLMXT F_ROL EOHSO F_F_L M_S_H KGWE_ X_FPT DZKGP TX_OU TGDTM KOPLQ OIGRF GL_",
        otvoreniTekst2);
    assertEquals("", kriviOtvoreniTekst);
    assertEquals("", kriviOtvoreniTekst2);
  }

  public void testDohvatiPermutacijuSlova() {
    char[] permutacija =
        new char[] {
          'A', 'Z', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'Q', 'S', 'D', 'F', 'G', 'H', 'J', 'K',
          'L', 'M', 'W', 'X', 'C', 'V', 'B', 'N'
        };
    char[] permutacija2 =
        new char[] {
          '_', '_', '_', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
          'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
        };
    char[] krivaPermutacija =
        new char[] {
          'A', 'A', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
          'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
        };
    Assert.assertArrayEquals(permutacija, stroj.dohvatiPermutacijuSlova());
    Assert.assertArrayEquals(permutacija2, stroj2.dohvatiPermutacijuSlova());
    Assert.assertArrayEquals(krivaPermutacija, kriviStroj.dohvatiPermutacijuSlova());
  }

  public void testDohvatiPermutacijuString() {
    String permutacija = "AZERTYUIOPQSDFGHJKLMWXCVBN";
    String permutacija2 = "___DEFGHIJKLMNOPQRSTUVWXYZ";
    String krivaPermutacija = "AACDEFGHIJKLMNOPQRSTUVWXYZ";

    assertEquals(permutacija, stroj.dohvatiPermutacijuString());
    assertEquals(permutacija2, stroj2.dohvatiPermutacijuString());
    assertEquals(krivaPermutacija, kriviStroj.dohvatiPermutacijuString());
  }
}
