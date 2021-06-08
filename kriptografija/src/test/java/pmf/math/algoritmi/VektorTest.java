package pmf.math.algoritmi;

import junit.framework.TestCase;

public class VektorTest extends TestCase {

  private final Vektor x = new Vektor(new int[]{1, 2, 3});
  private final Matrica A = new Matrica(new int[][]{
      {1, 2},
      {0, 3},
      {1, 9}
  });
  private final Vektor y = new Vektor(new int[]{0, 3, 1});

  public void testPomnozi() {
    Vektor xA = new Vektor(new int[] {4, 35});

    assertNotNull(x.pomnozi(A));
    assertEquals(x.pomnozi(A).getVektor()[0], xA.getVektor()[0]);
    assertEquals(x.pomnozi(A).getVektor()[1], xA.getVektor()[1]);

    int xy = 9;

    assertEquals(x.pomnozi(y), xy);

  }
}