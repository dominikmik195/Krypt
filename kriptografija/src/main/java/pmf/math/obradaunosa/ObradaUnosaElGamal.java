package pmf.math.obradaunosa;

import pmf.math.algoritmi.TeorijaBrojeva;

import java.math.BigInteger;

public class ObradaUnosaElGamal {
  public static boolean provjeriAlfa(int _alfa, int _pB) {
    // Funkcija koja provjerava je li alfa primitivni korijen danog prostog broja.
    if (_alfa < 0 || _alfa == _pB) return false;
    return TeorijaBrojeva.primitivniKorijen(_alfa, _pB);
  }

  public static boolean provjeriBeta(int _alfa, int _betica, int _pB, int _tK) {
    // Funkcija koaj provjerava zadovoljavaju li alfa i beta uvjete sustava.
    if (_betica < 0 || _alfa < 0) return false;
    BigInteger _beta = new BigInteger(String.valueOf(_betica));
    BigInteger _ostatak = _beta.mod(new BigInteger(String.valueOf(_pB)));
    return _ostatak.intValue() == TeorijaBrojeva.modularnoPotenciranje(_alfa, _tK, _pB);
  }

  public static boolean provjeriSifru(String sifra) {
    // Funkcija koja provjerava je li format šifre ispravan.
    if (sifra.charAt(0) != '(' || sifra.charAt(sifra.length() - 1) != ')') return false;
    String novi = sifra.substring(1, sifra.length() - 1);
    String[] lista = novi.split(",");
    return lista.length == 2;
  }
}
