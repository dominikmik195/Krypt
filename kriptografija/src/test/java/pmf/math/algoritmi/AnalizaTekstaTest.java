package pmf.math.algoritmi;

import java.util.List;
import java.util.Map;
import junit.framework.TestCase;

public class AnalizaTekstaTest extends TestCase {

  private final AnalizaTeksta analizaTeksta = new AnalizaTeksta();

  public void testPronadiSlova() {
    String tekst = "OVOJETESTNIPRIMJERTESTA";
    Map<String, Integer> mapaSlova = analizaTeksta.pronadiSlova(tekst);
    int a = 1, o = 2, t = 4;

    assertEquals((int) mapaSlova.get("A"), a);
    assertEquals((int) mapaSlova.get("O"), o);
    assertEquals((int) mapaSlova.get("T"), t);

    assertEquals((int) mapaSlova.get("B"), 0);
    assertEquals((int) mapaSlova.get("X"), 0);
  }

  public void testPronadiBigrame() {
    String tekst = "OVOJETESTNIPRIMJERTESTA";
    Map<String, List<Integer>> mapaBigrama = analizaTeksta.pronadiBigrame(tekst);
    int je = 2, te = 2, tn = 1;
    int jeX = 3, teX = 5, tnX = 8;

    assertEquals(mapaBigrama.get("JE").size(), je);
    assertEquals(mapaBigrama.get("TE").size(), te);
    assertEquals(mapaBigrama.get("TN").size(), tn);

    assertEquals((int) mapaBigrama.get("JE").get(0), jeX);
    assertEquals((int) mapaBigrama.get("TE").get(0), teX);
    assertEquals((int) mapaBigrama.get("TN").get(0), tnX);

    assertFalse(mapaBigrama.containsKey("SZ"));
    assertFalse(mapaBigrama.containsKey("XA"));
  }

  public void testPronadiTrigrame() {
    String tekst = "OVOJETESTNIPRIMJERTESTA";
    Map<String, List<Integer>> mapaTrigrama = analizaTeksta.pronadiTrigrame(tekst);
    int ovo = 1, tes = 2, jet = 1;
    int ovoX = 0, tesX = 5, jetX = 3;

    assertEquals(mapaTrigrama.get("OVO").size(), ovo);
    assertEquals(mapaTrigrama.get("TES").size(), tes);
    assertEquals(mapaTrigrama.get("JET").size(), jet);

    assertEquals((int) mapaTrigrama.get("OVO").get(0), ovoX);
    assertEquals((int) mapaTrigrama.get("TES").get(0), tesX);
    assertEquals((int) mapaTrigrama.get("JET").get(0), jetX);

    assertFalse(mapaTrigrama.containsKey("BBB"));
    assertFalse(mapaTrigrama.containsKey("MJN"));
  }
}