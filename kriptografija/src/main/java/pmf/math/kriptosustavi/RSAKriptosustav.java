package pmf.math.kriptosustavi;
import pmf.math.algoritmi.TeorijaBrojeva;

public class RSAKriptosustav {
    private int p, q, d;
    public int n, e;

    private int sifrat;

    public RSAKriptosustav() {
        p = 2; q = 3; n = 6;
        d = 1; e = 1;
        sifrat = 0;
    }

    public RSAKriptosustav(int _p, int _q, int _d) {
        p = _p; q = _q;
        n = _p*_q;
        d = _d;
        postaviE();
        sifrat = 0;
    }

    public RSAKriptosustav(int _p, int _q, int _d, int _e) {
        p = _p; q = _q;
        n = _p*_q;
        d = _d; e = _e;
        sifrat = 0;
    }

    private void postaviE() {
        int fi = TeorijaBrojeva.posebnaEulerovaFunkcija(n, p, q);
        System.out.println("fi: " + fi);
        int _e = 1;
        int granica = n*n;
        if(granica < 0) granica = Integer.MAX_VALUE;
        while(_e < granica) {
            if((d*_e) % fi == 1) {
                e = _e;
                return;
            }
            _e++;
        }
    }

    public int sifriraj(int broj) {
        sifrat = TeorijaBrojeva.velikiModulo(broj, e, n);
        return sifrat;
    }

    public void postaviSifrat(int _sifrat) {
        sifrat = _sifrat;
    }

    public String vratiSifrat() {
        return String.valueOf(sifrat);
    }

    public int desifriraj() {
        return TeorijaBrojeva.velikiModulo(sifrat, d, n);
    }

    public static boolean provjeriDiE(int _p, int _q, int _n, int _d, int _e) {
        return (_d*_e) % TeorijaBrojeva.posebnaEulerovaFunkcija(_n, _p, _q) == 1;
    }

    public static int provjeriD(int _p, int _q, int _n, int _d) {
        int fi = TeorijaBrojeva.posebnaEulerovaFunkcija(_n, _p, _q);
        if(fi == 0) return 0;
        int _e = 1;
        int granica = _n*_n;
        if(granica < 0) granica = Integer.MAX_VALUE;
        while(_e < granica) {
            if((_d*_e) % fi == 1) {
                return _e;
            }
            _e++;
        }
        return 0;
    }

    public static boolean provjeriN(int _n) {
        if(TeorijaBrojeva.prost(_n)) return false;
        for (int i = 2; i < _n; i++) {
            if(_n%i == 0 && !TeorijaBrojeva.prost(i)) return false;
            else if(_n%i == 0 && TeorijaBrojeva.prost(i) && !TeorijaBrojeva.prost(_n/i)) return false;
        }
        return true;
    }
}
