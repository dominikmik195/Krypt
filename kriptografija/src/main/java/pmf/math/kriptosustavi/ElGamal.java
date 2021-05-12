package pmf.math.kriptosustavi;

import java.lang.Math;

public class ElGamal {
  public int prostBroj, alfa, beta;
  private int tajniKljuč;
  public int[] šifrat = new int[2];

  public ElGamal() {
    prostBroj = 7;
    tajniKljuč = 3;
    postaviAlfa();
    postaviBeta();
    System.out.println("alfa " + alfa);
    System.out.println("beta " + beta);
  }

  public ElGamal(int pB, int tK) {
    prostBroj = pB;
    tajniKljuč = tK;
    postaviAlfa();
    postaviBeta();
    System.out.println("alfa " + alfa);
    System.out.println("beta " + beta);
  }

  public void šifriraj(int broj, int tajniBroj) {
    šifrat[0] = (int) (Math.pow(alfa, tajniBroj) % prostBroj);
    šifrat[1] = (broj * (int) Math.pow(beta, tajniBroj)) % prostBroj;
  }

  public int dešifriraj() {
    int temp = (šifrat[0] ^ tajniKljuč) % prostBroj;
    return (inverz(temp, prostBroj) * šifrat[1]) % prostBroj;
  }

  private void postaviAlfa() {
    alfa = primitivniKorijen(prostBroj);
  }

  private void postaviBeta() {
    beta = (int) (Math.pow(tajniKljuč, alfa)) % prostBroj;
  }

  private int redElementa(int element, int modulo) {
    int red = 1;
    int ostatak = (int) Math.pow(element, red) % modulo;
    while (ostatak != 1) {
      red++;
      ostatak = (int) Math.pow(element, red) % modulo;
    }
    return red;
  }

  private int primitivniKorijen(int modulo) {
    int korijen = 1;
    int red = redElementa(korijen, modulo);
    while (red != modulo - 1) {
      korijen++;
      red = redElementa(korijen, modulo);
    }
    return korijen;
  }

  private int inverz(int broj, int modulo) {
    for (int i = 0; i < (modulo * modulo); i++) {
      if (i * broj % modulo == 1) return i;
    }
    return -1;
  }

  public String vratiŠifrat() {
    return "(" + šifrat[0] + ", " + šifrat[1] + ")";
  }

  public void pohraniŠifrat(String tekst) {
    String novi = tekst.substring(1, tekst.length() - 1);
    String[] lista = novi.split(",");
    šifrat[0] = Integer.parseInt(lista[0].strip());
    šifrat[1] = Integer.parseInt(lista[1].strip());
  }
}
