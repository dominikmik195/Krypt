package pmf.math.kriptosustavi;

import pmf.math.algoritmi.TeorijaBrojeva;

public class RSAKriptosustav {
  private int p, q, d;
  public int n, e;

  private int sifrat;

  public RSAKriptosustav() {
    p = -1;
    q = -1;
    n = -1;
    d = -1;
    e = -1;
    sifrat = 0;
  }

  public RSAKriptosustav(int _p, int _q) {
    p = _p;
    q = _q;
    n = _p * _q;
    d = -1;
    e = -1;
    sifrat = 0;
  }

  public void setD(int _d) {
    d = _d;
  }

  public int getD() {
    return d;
  }

  public int sifriraj(int broj) {
    sifrat = TeorijaBrojeva.modularnoPotenciranje(broj, e, n);
    return sifrat;
  }

  public void postaviSifrat(int _sifrat) {
    sifrat = _sifrat;
  }

  public String vratiSifrat() {
    return String.valueOf(sifrat);
  }

  public int desifriraj() {
    return TeorijaBrojeva.modularnoPotenciranje(sifrat, d, n);
  }

  public static boolean provjeriDiE(int _p, int _q, int _d, int _e) {
    return (_d * _e) % TeorijaBrojeva.posebnaEulerovaFunkcija(_p, _q) == 1;
  }

  public static boolean provjeriD(int _p, int _q, int _d) {
    int fi = TeorijaBrojeva.posebnaEulerovaFunkcija(_p, _q);
    return TeorijaBrojeva.relativnoProsti(_d, fi);
  }

  public static int nadjiDiliE(int zadani, int _p, int _q) {
    return TeorijaBrojeva.inverz(zadani, TeorijaBrojeva.posebnaEulerovaFunkcija(_p, _q));
  }

  public static int[] nadjiDiE(int _p, int _q) {
    int[] de = {-1, -1};
    int _d = 2;
    while (true) {
      if (provjeriD(_p, _q, _d)) break;
      _d++;
    }
    de[0] = _d;
    de[1] = nadjiDiliE(_d, _p, _q);
    return de;
  }

  public static int[] rastaviNNaPiQ(int _n) {
    int[] pq = {-1, -1};
    for (int _p = 2; _p < _n; _p++) {
      if (_n % _p == 0 && TeorijaBrojeva.prost(_p)) {
        if (TeorijaBrojeva.prost(_n / _p)) {
          pq[0] = _p;
          pq[1] = _n / _p;
          return pq;
        }
      }
    }
    return pq;
  }
}
