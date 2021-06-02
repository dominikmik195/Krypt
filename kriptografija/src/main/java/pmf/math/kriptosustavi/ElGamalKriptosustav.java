package pmf.math.kriptosustavi;

import java.lang.Math;
import java.math.BigInteger;
import pmf.math.algoritmi.TeorijaBrojeva;

public class ElGamalKriptosustav {
  public int prostBroj, alfa, beta;
  private int tajniKljuc;
  public int[] sifrat = new int[2];

  public ElGamalKriptosustav() {
    prostBroj = 7;
    tajniKljuc = 3;
    postaviAlfa();
    postaviBeta();
  }

  public ElGamalKriptosustav(int pB, int tK) {
    prostBroj = pB;
    tajniKljuc = tK;
    postaviAlfa();
    postaviBeta();
  }

  public ElGamalKriptosustav(int pB, int tK, int al) {
    prostBroj = pB;
    tajniKljuc = tK;
    alfa = al;
    postaviBeta();
  }

  public ElGamalKriptosustav(int pB, int tK, int al, int be) {
    prostBroj = pB;
    tajniKljuc = tK;
    alfa = al;
    beta = be;
  }

  public void sifriraj(int broj, int tajniBroj) {
    BigInteger br = new BigInteger(String.valueOf(broj));
    BigInteger pB = new BigInteger(String.valueOf(prostBroj));
    sifrat[0] = TeorijaBrojeva.velikiModulo(alfa, tajniBroj, prostBroj);
    sifrat[1] = (br.mod(pB).multiply(new BigInteger(String.valueOf(TeorijaBrojeva.velikiModulo(beta, tajniBroj, prostBroj))))).mod(pB).intValue();
  }

  public int desifriraj() {
    int temp = TeorijaBrojeva.velikiModulo(sifrat[0], tajniKljuc, prostBroj);
    return (TeorijaBrojeva.inverz(temp, prostBroj) * sifrat[1]) % prostBroj;
  }

  private void postaviAlfa() {
    alfa = TeorijaBrojeva.najmanjiPrimitivniKorijen(prostBroj);
  }

  private void postaviBeta() {
    beta = (int) (Math.pow(alfa, tajniKljuc)) % prostBroj;
    beta = TeorijaBrojeva.velikiModulo(alfa, tajniKljuc, prostBroj);
  }

  public String vratiSifrat() {
    return "(" + sifrat[0] + ", " + sifrat[1] + ")";
  }

  public void pohraniSifrat(String tekst) {
    String novi = tekst.substring(1, tekst.length() - 1);
    String[] lista = novi.split(",");
    sifrat[0] = Integer.parseInt(lista[0].strip());
    sifrat[1] = Integer.parseInt(lista[1].strip());
  }

  public static boolean provjeriAlfa(int _alfa, int _pB) {
    return TeorijaBrojeva.primitivniKorijen(_alfa, _pB);
  }

  public static boolean provjeriBeta(int _alfa, int _betica, int _pB, int _tK) {
    BigInteger _beta = new BigInteger(String.valueOf(_betica));
    BigInteger _ostatak = _beta.mod(new BigInteger(String.valueOf(_pB)));
    return _ostatak.intValue() == TeorijaBrojeva.velikiModulo(_alfa, _tK, _pB);
  }
}
