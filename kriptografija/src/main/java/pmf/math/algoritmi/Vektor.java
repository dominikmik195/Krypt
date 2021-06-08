package pmf.math.algoritmi;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vektor {

  private int[] vektor;

  public Vektor(int[] podaci) {
    vektor = new int[podaci.length];
    System.arraycopy(podaci, 0, vektor, 0, podaci.length);
  }

  public int getN() {
    return vektor.length;
  }

  public Vektor pomnozi(Matrica A) {
    int[] rezultat = new int[A.getM()];

    for (int i = 0; i < A.getM(); i++) {
      Vektor stupac = new Vektor(A.dohvatiStupac(i));
      rezultat[i] = pomnozi(stupac);
    }
    return new Vektor(rezultat);
  }

  public int pomnozi(Vektor V) {
    if (getN() != V.getN()) {
      return -1;
    }
    int izlaz = 0;
    for (int i = 0; i < getN(); i++) {
      izlaz += vektor[i] * V.getVektor()[i];
    }
    return izlaz;
  }

  public Vektor moduliraj(int mod) {
    for(int i = 0; i < getN(); i++) {
      while(vektor[i] < 0) {
        vektor[i] += mod;
      }
    }
    return this;
  }
}
