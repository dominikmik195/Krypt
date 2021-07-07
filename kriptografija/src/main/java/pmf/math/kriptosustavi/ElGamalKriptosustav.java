package pmf.math.kriptosustavi;

import pmf.math.algoritmi.TeorijaBrojeva;
import pmf.math.baza.dao.BrojGrafDAO;
import pmf.math.konstante.ElGamalPrimjeri;
import pmf.math.pomagala.Stoperica;

import java.math.BigInteger;
import java.util.Random;

public class ElGamalKriptosustav {
  public int prostBroj, alfa, beta;
  private int tajniKljuc;
  private int tajniBroj;
  private int otvoreniTekst;
  private boolean OK;
  public int[] sifrat = new int[2];

  private String poruke;
  public static boolean prekid;

  private int napredak;

  public ElGamalKriptosustav() {
    prostBroj = -1;
    tajniKljuc = -1;
    tajniBroj = -1;
    alfa = -1;
    beta = -1;
    poruke = "";
    OK = true;
    prekid = false;
  }

  public ElGamalKriptosustav(int pB, int al, int be) {
    prostBroj = pB;
    alfa = al;
    beta = be;
  }

  public void setTajniKljuc(int tK) {
    tajniKljuc = tK;
  }

  public int getTajniKljuc() {
    return tajniKljuc;
  }

  public int getTajniBroj() {
    return tajniBroj;
  }

  public void setTajniBroj(int tajniBroj) {
    this.tajniBroj = tajniBroj;
  }

  public boolean isOK() {
    return OK;
  }

  public void setOK(boolean OK) {
    this.OK = OK;
  }

  public int getNapredak() {
    return napredak;
  }

  public void setNapredak(int napredak) {
    this.napredak = napredak;
  }

  public int getOtvoreniTekst() {
    return otvoreniTekst;
  }

  public void setOtvoreniTekst(int otvoreniTekst) {
    this.otvoreniTekst = otvoreniTekst;
  }

  public void reinicijalizirajPoruke() {
    poruke = "";
  }

  public void prosiriPoruku(String dodatak) {
    if(!dodatak.equals(""))
      poruke += dodatak + " ";
  }

  public String dohvatiPoruke() {
    return poruke;
  }

  public void sifriraj() {
    BigInteger br = new BigInteger(String.valueOf(otvoreniTekst));
    BigInteger pB = new BigInteger(String.valueOf(prostBroj));
    sifrat[0] = (int)TeorijaBrojeva.modularnoPotenciranje(alfa, tajniBroj, prostBroj);
    sifrat[1] =
        (br.mod(pB)
                .multiply(
                    new BigInteger(
                        String.valueOf(
                            TeorijaBrojeva.modularnoPotenciranje(beta, tajniBroj, prostBroj)))))
            .mod(pB)
            .intValue();
  }

  public int desifriraj() {
    int temp = (int)TeorijaBrojeva.modularnoPotenciranje(sifrat[0], tajniKljuc, prostBroj);
    return (TeorijaBrojeva.inverz(temp, prostBroj) * sifrat[1]) % prostBroj;
  }

  public static int noviAlfa(int pB) {
    // Funkcija koja računa najmanji alfa koji je primtivni korijen danog prostog broja.
    return TeorijaBrojeva.najmanjiPrimitivniKorijen(pB);
  }

  public static int noviBeta(int pB, int a, int tK) {
    // Funkcija koja računa pripadni broj beta za dane varijable.
    return (int)TeorijaBrojeva.modularnoPotenciranje(a, tK, pB);
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

  public static int[] simuliraj(BrojGrafDAO.VrstaSimulacije vrstaSimulacije, int brojIteracija, int maxBrojZnamenaka) {
    int[] vremena = new int[maxBrojZnamenaka];
    ElGamalKriptosustav stroj = new ElGamalKriptosustav();
    Stoperica stoperica = new Stoperica();
    Random r = new Random();
    for (int i = 0; i < maxBrojZnamenaka; i++) {
      for (int j = 0; j < brojIteracija; j++) {
        int[][] primjeri = ElGamalPrimjeri.PRIMJERI[i];
        int pozicija = r.nextInt(primjeri.length);
        int[] primjer = primjeri[pozicija];
        int broj = r.nextInt(primjer[0]);
        int[] s = {r.nextInt(primjer[0]), r.nextInt(primjer[0])};
        stroj.prostBroj = primjer[0];
        stroj.setTajniKljuc(primjer[1]);
        stroj.alfa = primjer[2];
        stroj.beta = primjer[3];
        stroj.setTajniBroj(primjer[4]);
        stoperica.resetiraj();
        stoperica.pokreni();
        switch (vrstaSimulacije) {
          case SIFRIRAJ ->{
            stroj.otvoreniTekst = broj;
            stroj.sifriraj();
          }
          case DESIFRIRAJ ->{
            stroj.sifrat = s;
            stroj.desifriraj();
          }
        }
        stoperica.zaustavi();
        vremena[i] += stoperica.vrijeme();
      }
      vremena[i] = vremena[i] / brojIteracija;
    }
    return vremena;
  }
}
