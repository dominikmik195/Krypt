package pmf.math.obradaunosa;

import junit.framework.TestCase;

public class ObradaUnosaCezarKljucnaRijecTest extends TestCase {

  public void testSifriraj() {
    int pomak = 8;
    String kljuc = "kri pto grafija";
    String kriviKljuc = "kri pto grafijač";
    String otvoreniTekst =
        "Matematika je znanstvena disciplina nastala"
            + "proucavanjem brojeva i geometrijskih odnosa";
    String kriviTekst =
        "Matematika je znanstvena disciplina nastala"
            + "proučavanjem brojeva i geometrijskih odnosa";

    assertEquals(
        "TQCWT QCKIQ RWNOQ OBCEW OQVKB UKAPK OQOQB CQPQA JGDUQ EQORW TSJGR WEQKY WGTWC JKRBI KZGVO GBQ",
        ObradaUnosaCezarKljucnaRijec.sifriraj(pomak, kljuc, otvoreniTekst));
    assertEquals("", ObradaUnosaCezarKljucnaRijec.sifriraj(pomak, kljuc, kriviTekst));
    assertEquals("", ObradaUnosaCezarKljucnaRijec.sifriraj(pomak, kriviKljuc, otvoreniTekst));
  }

  public void testDesifriraj() {
    int pomak = 8;
    String kljuc = "kri pto grafija";
    String kriviKljuc = "kri pto grafijač";
    String sifrat =
        "TQCWT QCKIQ RWNOQOBCEWOQVKBUkAPK OQOQB CqPQA JGDUQ EQORW TSJGR WEQKY WGTWC JKRBI KZGVO GBQ";
    String kriviSifrat =
        "TQCWT QCKIQ RWNOQOBCEWOQVKBUkAPK OQOQB CqPQA JGDUQ EQORW TSJGR WEQKY WGTWC JKRBI KZGVO GBQč";

    assertEquals(
        "MATEM ATIKA JEZNA NSTVE NADIS CIPLI NANAS TALAP ROUCA VANJE MBROJ EVAIG EOMET RIJSK IHODN OSA",
        ObradaUnosaCezarKljucnaRijec.desifriraj(pomak, kljuc, sifrat));
    assertEquals("", ObradaUnosaCezarKljucnaRijec.desifriraj(pomak, kljuc, kriviSifrat));
    assertEquals("", ObradaUnosaCezarKljucnaRijec.desifriraj(pomak, kriviKljuc, sifrat));
  }

  public void testPermutacija() {
    int pomak = 8;
    String kljuc = "kri pto grafija";
    String kriviKljuc = "kri pto grafijač";

    assertEquals(
        "Q S U V W X Y Z K R I P T O G A F J B C D E H L M N ",
        ObradaUnosaCezarKljucnaRijec.permutacija(pomak, kljuc));

    assertEquals(
        "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z",
        ObradaUnosaCezarKljucnaRijec.permutacija(pomak, kriviKljuc));
  }
}
