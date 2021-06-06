package pmf.math.kriptosustavi;

import java.math.BigInteger;
import pmf.math.algoritmi.TeorijaBrojeva;

public class ElGamalKriptosustav {
  public int prostBroj, alfa, beta;
  private int tajniKljuc;
  public int[] sifrat = new int[2];

  public ElGamalKriptosustav() {
    prostBroj = -1;
    tajniKljuc = -1;
    alfa = -1;
    beta = -1;
  }

  public ElGamalKriptosustav(int pB, int al, int be) {
    prostBroj = pB;
    alfa = al;
    beta = be;
  }

  public void setTajniKljuc(int tK) {
    tajniKljuc = tK;
  }

  public void sifriraj(int broj, int tajniBroj) {
    BigInteger br = new BigInteger(String.valueOf(broj));
    BigInteger pB = new BigInteger(String.valueOf(prostBroj));
    sifrat[0] = TeorijaBrojeva.modularnoPotenciranje(alfa, tajniBroj, prostBroj);
    sifrat[1] = (br.mod(pB).multiply(new BigInteger(String.valueOf(TeorijaBrojeva.modularnoPotenciranje(beta, tajniBroj, prostBroj))))).mod(pB).intValue();
  }

  public int desifriraj() {
    int temp = TeorijaBrojeva.modularnoPotenciranje(sifrat[0], tajniKljuc, prostBroj);
    return (TeorijaBrojeva.inverz(temp, prostBroj) * sifrat[1]) % prostBroj;
  }

  public static int noviAlfa(int pB) {
    // Funkcija koja računa najmanji alfa koji je primtivni korijen danog prostog broja.
    return TeorijaBrojeva.najmanjiPrimitivniKorijen(pB);
  }

  public static int noviBeta(int pB, int a, int tK) {
    // Funkcija koja računa pripadni broj beta za dane varijable.
    return TeorijaBrojeva.modularnoPotenciranje(a, tK, pB);
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
}
