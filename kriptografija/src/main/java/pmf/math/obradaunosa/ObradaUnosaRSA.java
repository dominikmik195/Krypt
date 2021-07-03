package pmf.math.obradaunosa;

import pmf.math.algoritmi.TeorijaBrojeva;

import java.math.BigInteger;

public class ObradaUnosaRSA {
  public static boolean provjeriD(int _p, int _q, int _d) {
    // Funkcija koja provjerava postoji li za zadani d (ili ekvivalentno e) odgovarajući e
    // (ili ekvivalentno d) tako da zadovoljavaju uvjete sustava.
    int fi = TeorijaBrojeva.posebnaEulerovaFunkcija(_p, _q);
    return TeorijaBrojeva.relativnoProsti(_d, fi);
  }

  public static boolean provjeriDiE(int _p, int _q, int _d, int _e) {
    // Provjerava zadovojljavaju li uneseni d i e uvjete sustava.
    BigInteger ee = new BigInteger(String.valueOf(_e));
    BigInteger dd = new BigInteger(String.valueOf(_d));
    BigInteger mod = new BigInteger(String.valueOf(TeorijaBrojeva.posebnaEulerovaFunkcija(_p, _q)));
    return dd.multiply(ee).mod(mod).intValue() == 1;
  }

  public static boolean provjeriNUmnozakProstih(int n) {
    // Provjerava može li se n zapisati kao umnožak dvaju prostih brojeva.
    for (int i = 2; i < n; i++) {
      if (TeorijaBrojeva.prost(i) && n % i == 0) {
        if (TeorijaBrojeva.prost(n / i)) return true;
      }
    }
    return false;
  }
}
