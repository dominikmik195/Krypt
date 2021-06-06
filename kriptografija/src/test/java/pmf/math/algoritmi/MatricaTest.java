package pmf.math.algoritmi;

import junit.framework.TestCase;

public class MatricaTest extends TestCase {

  private final Matrica A = new Matrica(new int[][]{
      {1, 2, 3, 4, 5},
      {0, 1, 0, 0, 0},
      {0, 0, 4, 2, 1}
  });
  private final Matrica B = new Matrica(new int[][]{
      {1, -2, 3, 4},
      {0, -1, 0, 0},
      {0, 0, 4, -2},
      {0, 0, 0, -3}
  });

  private final Matrica C = new Matrica(new int[][]{
      {1, 0},
      {3, 0}
  });

  private final Matrica D = new Matrica(new int[][]{
      {4, -1},
      {15, -4}
  });


  public void testGetN() {
    assertEquals(A.getN(), 3);
    assertEquals(B.getN(), 4);
  }

  public void testGetM() {
    assertEquals(A.getM(), 5);
    assertEquals(B.getM(), 4);
  }

  public void testZbroji() {
    Matrica AA = new Matrica(new int[][]{
        {2, 4, 6, 8, 10},
        {0, 2, 0, 0, 0},
        {0, 0, 8, 4, 2}
    });
    assertNotNull(A.zbroji(A));
    assertEquals(A.zbroji(A).dohvatiElement(0, 0), AA.dohvatiElement(0, 0));
    assertEquals(A.zbroji(A).dohvatiElement(2, 2), AA.dohvatiElement(2, 2));
    assertEquals(A.zbroji(A).dohvatiElement(1, 3), AA.dohvatiElement(1, 3));
  }

  public void testPomnozi() {
    int alfa = 2;
    Matrica alfaA = new Matrica(new int[][]{
        {2, 4, 6, 8, 10},
        {0, 2, 0, 0, 0},
        {0, 0, 8, 4, 2}
    });
    assertNotNull(A.pomnozi(alfa));
    assertEquals(A.pomnozi(alfa).dohvatiElement(0, 3), alfaA.dohvatiElement(0, 3));
    assertEquals(A.pomnozi(alfa).dohvatiElement(2, 2), alfaA.dohvatiElement(2, 2));
    assertEquals(A.pomnozi(alfa).dohvatiElement(1, 3), alfaA.dohvatiElement(1, 3));

    Matrica BB = new Matrica(new int[][]{
        {1, 0, 15, -14},
        {0, 1, 0, 0},
        {0, 0, 16, -2},
        {0, 0, 0, 9}
    });
    assertNotNull(B.pomnozi(B));
    assertEquals(B.pomnozi(B).dohvatiElement(0, 0), BB.dohvatiElement(0, 0));
    assertEquals(B.pomnozi(B).dohvatiElement(0, 2), BB.dohvatiElement(0, 2));
    assertEquals(B.pomnozi(B).dohvatiElement(2, 2), BB.dohvatiElement(2, 2));

    Vektor v = new Vektor(new int[]{2, 3, 1, 8});
    Vektor Bv = new Vektor(new int[]{31, -3, -12, -24});
    assertNotNull(B.pomnozi(v));
    assertEquals(B.pomnozi(v).getVektor()[0], Bv.getVektor()[0]);
    assertEquals(B.pomnozi(v).getVektor()[1], Bv.getVektor()[1]);
    assertEquals(B.pomnozi(v).getVektor()[2], Bv.getVektor()[2]);
    assertEquals(B.pomnozi(v).getVektor()[3], Bv.getVektor()[3]);
  }

  public void testRegularna() {
    assertFalse(A.regularna());
    assertTrue(B.regularna());
    assertFalse(C.regularna());
    assertTrue(D.regularna());
  }

  public void testInvoluirana() {
    assertFalse(A.involuirana());
    assertFalse(B.involuirana());
    assertFalse(C.involuirana());
    assertTrue(D.involuirana());
  }
}