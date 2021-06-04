package pmf.math.kriptosustavi;

import java.util.Arrays;
import junit.framework.TestCase;
import pmf.math.kriptosustavi.PlayfairKriptosustav.Jezik;
import pmf.math.kriptosustavi.PlayfairKriptosustav.Smijer;

public class PlayfairKriptosustavTest extends TestCase {

  PlayfairKriptosustav playfairKriptosustav = new PlayfairKriptosustav();

  public void testDohvatiPlayfairTablicu() {
    String[][] tablicaHR = playfairKriptosustav.dohvatiPlayfairTablicu(Jezik.HRVATSKI, "KLJUC");
    assertEquals(tablicaHR.length, 5);
    assertEquals(tablicaHR[0].length, 5);
    assertEquals(tablicaHR[2][2], "I");
    assertEquals(tablicaHR[1][4], "F");

    String[][] tablicaEN = playfairKriptosustav.dohvatiPlayfairTablicu(Jezik.ENGLESKI, "");
    assertEquals(tablicaEN.length, 5);
    assertEquals(tablicaEN[0].length, 5);
    assertEquals(tablicaEN[2][2], "N");
    assertEquals(tablicaEN[1][4], "K");
  }

  public void testPlayfairTransformirajTekst() {
    String otvoreniTekst, sifrat;
    playfairKriptosustav.dohvatiPlayfairTablicu(Jezik.ENGLESKI, "PLAYFAIR");

    otvoreniTekst = "CR YP TO GR AP HY";
    sifrat = playfairKriptosustav.sifriraj(otvoreniTekst);
    assertEquals(sifrat, "DB FL NQ OG YL KA");

    playfairKriptosustav.dohvatiPlayfairTablicu(Jezik.HRVATSKI, "TAJNOPIS");

    sifrat = "CK FL ET IJ KS VI XG IE QO SA GA PL TE AU KH CA";
    otvoreniTekst = playfairKriptosustav.desifriraj(sifrat);
    assertEquals(otvoreniTekst, "PR EM DA SA MP LA YF AI RN IJ EN IK AD TV RD IO");
  }

  public void testPlayFairTransformirajSlova() {
    String[][] tablicaEN = playfairKriptosustav.dohvatiPlayfairTablicu(Jezik.ENGLESKI, "KLJUC");
    String slova;

    slova = playfairKriptosustav.playFairTransformirajSlova("A", "B", Smijer.SIFRIRAJ);
    assertEquals(slova, "BD");

    slova = playfairKriptosustav.playFairTransformirajSlova("A", "P", Smijer.SIFRIRAJ);
    assertEquals(slova, "GV");

    slova = playfairKriptosustav.playFairTransformirajSlova("A", "I", Smijer.SIFRIRAJ);
    assertEquals(slova, "DK");

    slova = playfairKriptosustav.playFairTransformirajSlova("A", "B", Smijer.DESIFRIRAJ);
    assertEquals(slova, "FA");

    slova = playfairKriptosustav.playFairTransformirajSlova("A", "P", Smijer.DESIFRIRAJ);
    assertEquals(slova, "KG");

    slova = playfairKriptosustav.playFairTransformirajSlova("A", "T", Smijer.DESIFRIRAJ);
    assertEquals(slova, "FP");
  }
}