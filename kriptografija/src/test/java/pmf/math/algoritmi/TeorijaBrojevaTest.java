package pmf.math.algoritmi;

import junit.framework.TestCase;

public class TeorijaBrojevaTest extends TestCase {
    static {
        System.loadLibrary("TeorijaBrojeva");
    }

    public void testProst() {
      assertTrue(TeorijaBrojeva.prost(4219));
      assertFalse(TeorijaBrojeva.prost(104524));
    }

    public void testModularnoPotenciranje() {
      assertEquals(819, TeorijaBrojeva.modularnoPotenciranje(24, 7, 3579));
      assertEquals(5115, TeorijaBrojeva.modularnoPotenciranje(13, 8, 25846));
    }

    public void testRedElementa() {
      assertEquals(5, TeorijaBrojeva.redElementa(3, 242));
      assertEquals(11, TeorijaBrojeva.redElementa(7, 329554457));
    }

    public void testNajmanjiPrimitivniKorijen() {
      assertEquals(3, TeorijaBrojeva.najmanjiPrimitivniKorijen(223));
      assertEquals(7, TeorijaBrojeva.najmanjiPrimitivniKorijen(2111));
    }

    public void testInverz() {
      assertEquals(4, TeorijaBrojeva.inverz(3, 11));
      assertEquals(12, TeorijaBrojeva.inverz(10, 17));
    }

    public void testRelativnoProsti() {
      assertTrue(TeorijaBrojeva.relativnoProsti(12, 25));
      assertTrue(TeorijaBrojeva.relativnoProsti(9335088, 9150625));
      assertFalse(TeorijaBrojeva.relativnoProsti(2578125, 3226944));
    }
}