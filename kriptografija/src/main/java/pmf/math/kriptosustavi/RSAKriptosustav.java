package pmf.math.kriptosustavi;

import pmf.math.algoritmi.TeorijaBrojeva;
import pmf.math.obradaunosa.ObradaUnosaRSA;

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
    // Konstruktor koji postavlja p, q i n. Ostale varijable postavljaju se 'ručno' po potebi.
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

  public static int nadjiDiliE(int zadani, int _p, int _q) {
    // Budući da su d i e u istoj svezi i da ih možemo birati na jednak način,
    // imamo funkciju koja samo računa d ukoliko je zadan e tražeći pripadni inverz ili obratno.
    return TeorijaBrojeva.inverz(zadani, TeorijaBrojeva.posebnaEulerovaFunkcija(_p, _q));
  }

  public static int[] nadjiDiE(int _p, int _q) {
    // Funkcija koja za zadani p i q nalazi najmanji d i pripadni e koji zadovoljavaju uvjete sustava.
    int[] de = {-1, -1};
    int _d = 2;
    while (true) {
      if (ObradaUnosaRSA.provjeriD(_p, _q, _d)) break;
      _d++;
    }
    de[0] = _d;
    de[1] = nadjiDiliE(_d, _p, _q);
    return de;
  }

  public static int[] rastaviNNaPiQ(int _n) {
    // Funckija koja broj n rastavlja na njegova dva prosta faktora (ukoliko je moguće
    // - povjeru ne izvršavamo u ovoj funkciji jer budemo je izvršavali prije poziva.).
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