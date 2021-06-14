package pmf.math.algoritmi;

import junit.framework.TestCase;

public class AbecedaTest extends TestCase {

  public void testUBroj() {
    char A = 'A', B = 'B', Z = 'Z';

    assertEquals(Abeceda.uBroj(A), 0);
    assertEquals(Abeceda.uBroj(B), 1);
    assertEquals(Abeceda.uBroj(Z), 25);
  }

  public void testUSlovo() {
    int a = 0, b = 1, z = 25;

    assertEquals(Abeceda.uSlovo(a), 'A');
    assertEquals(Abeceda.uSlovo(b), 'B');
    assertEquals(Abeceda.uSlovo(z), 'Z');
  }

  public void testZbroj() {
    int a = 0, b = 1, f = 5, z = 25;
    int ab = 1, af = 5, bz = 0, fz = 4, ff = 10;

    assertEquals(Abeceda.zbroj(a, b), ab);
    assertEquals(Abeceda.zbroj(a, f), af);
    assertEquals(Abeceda.zbroj(b, z), bz);
    assertEquals(Abeceda.zbroj(f, z), fz);
    assertEquals(Abeceda.zbroj(f, f), ff);

    char A = 'A', B = 'B', F = 'F', Z = 'Z';
    char AB = 'B', AF = 'F', BZ = 'A', FZ = 'E', FF = 'K';

    assertEquals(Abeceda.zbroj(A, B), AB);
    assertEquals(Abeceda.zbroj(A, F), AF);
    assertEquals(Abeceda.zbroj(B, Z), BZ);
    assertEquals(Abeceda.zbroj(F, Z), FZ);
    assertEquals(Abeceda.zbroj(F, F), FF);
  }

  public void testInverz() {
    int a = 0, b = 1, f = 5, z = 25;
    int aa = 0, bb = 25, ff = 21, zz = 1;

    assertEquals(Abeceda.inverz(a), aa);
    assertEquals(Abeceda.inverz(b), bb);
    assertEquals(Abeceda.inverz(f), ff);
    assertEquals(Abeceda.inverz(z), zz);

    char A = 'A', B = 'B', F = 'F', Z = 'Z';
    char AA = 'A', BB = 'Z', FF = 'V', ZZ = 'B';

    assertEquals(Abeceda.inverz(A), AA);
    assertEquals(Abeceda.inverz(B), BB);
    assertEquals(Abeceda.inverz(F), FF);
    assertEquals(Abeceda.inverz(Z), ZZ);
  }

}