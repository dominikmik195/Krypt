package pmf.math.kriptosustavi;

import junit.framework.TestCase;
import org.junit.Before;

import java.io.File;

public class RSAKriptosustavTest extends TestCase {
    private RSAKriptosustav stroj;

    @Before
    public void setUp() throws Exception {
        File lib = new File("lib/TeorijaBrojeva.dll");
        System.load(lib.getAbsolutePath());
        stroj = new RSAKriptosustav(3, 11);
        assertNotNull(stroj);
    }

    public void testSifriraj() {
        stroj.e = 7;
        stroj.sifriraj(17);
        assertEquals("8", stroj.vratiSifrat());
    }

    public void testDesifriraj() {
        stroj.setD(3);
        stroj.postaviSifrat(8);
        assertEquals(17, stroj.desifriraj());
    }

    public void testNadjiDiliE() {
        assertEquals(7, RSAKriptosustav.nadjiDiliE(3, 3, 11));
        assertEquals(3, RSAKriptosustav.nadjiDiliE(7, 3, 11));
    }

    public void testNadjiDiE() {
        int[] de = RSAKriptosustav.nadjiDiE(3, 11);
        assertEquals(3, de[0]);
        assertEquals(7, de[1]);
    }

    public void testRastaviNNaPiQ() {
        int[] pq = RSAKriptosustav.rastaviNNaPiQ(33);
        assertEquals(3, pq[0]);
        assertEquals(11, pq[1]);
    }
}