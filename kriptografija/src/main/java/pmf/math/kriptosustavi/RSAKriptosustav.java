package pmf.math.kriptosustavi;

import lombok.Getter;
import lombok.Setter;
import pmf.math.algoritmi.TeorijaBrojeva;
import pmf.math.baza.dao.BrojGrafDAO;
import pmf.math.konstante.RSAPrimjeri;
import pmf.math.obradaunosa.ObradaUnosaRSA;
import pmf.math.pomagala.Stoperica;

import java.util.Random;

@Getter
@Setter
public class RSAKriptosustav {
  private int p, q, d;
  public int n, e;

  private int sifrat;
  private int broj;

  private String poruke;
  private boolean OK;
  private int napredak;

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

  public void prosiriPoruku(String dodatak) {
    if(!dodatak.equals(""))
      poruke += dodatak + " ";
  }

  public void reinicijaliziraj() {
    p = -1;
    q = -1;
    n = -1;
    d = -1;
    e = -1;
    sifrat = 0;
    OK = true;
    poruke = "";
    napredak = 0;
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
    while (!ObradaUnosaRSA.provjeriD(_p, _q, _d)) {
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

  public static int[] simuliraj(BrojGrafDAO.VrstaSimulacije vrstaSimulacije, int brojIteracija) {
    int maxBrojZnamenaka = 4;
    int[] vremena = new int[maxBrojZnamenaka];
    RSAKriptosustav stroj = new RSAKriptosustav();
    Stoperica stoperica = new Stoperica();
    Random r = new Random();
    for (int i = 0; i < maxBrojZnamenaka; i++) {
      for (int j = 0; j < brojIteracija; j++) {
        int[][] primjeri = RSAPrimjeri.PRIMJERI[i];
        int pozicija = r.nextInt(primjeri.length);
        int[] primjer = primjeri[pozicija];
        int broj = r.nextInt(primjer[2]);
        stroj.setP(primjer[0]);
        stroj.setQ(primjer[1]);
        stroj.setN(primjer[2]);
        stroj.setD(primjer[3]);
        stroj.setE(primjer[4]);
        stoperica.resetiraj();
        stoperica.pokreni();
        switch (vrstaSimulacije) {
          case SIFRIRAJ -> stroj.sifriraj(broj);
          case DESIFRIRAJ ->{
            stroj.setSifrat(broj);
            stroj.desifriraj();
          }
        }
        stoperica.zaustavi();
        vremena[i] += stoperica.vrijemeMili();
      }
      vremena[i] = vremena[i] / brojIteracija;
    }
    return vremena;
  }
}
