package pmf.math.kriptosustavi;

import lombok.Getter;
import lombok.Setter;

import java.util.Vector;

@Getter
@Setter
public class StupcanaTranspozicijaSustav {
  private int brojStupaca;
  private Vector<Integer> vrijednosti;
  private String otvoreniTekst;
  private String sifrat;
  private int brojRedaka;

  public StupcanaTranspozicijaSustav(Vector<Integer> v) {
    vrijednosti = v;
    brojStupaca = v.size();
  }

  public StupcanaTranspozicijaSustav() {
    vrijednosti = new Vector<>();
  }

  public void sifriraj() {
    brojRedaka = (int)Math.ceil((double)otvoreniTekst.length()/brojStupaca);
    char[][] matrica = new char[brojRedaka][brojStupaca];
    while(otvoreniTekst.length() < brojStupaca*brojRedaka) {
      otvoreniTekst += "X";
    }
    for (int i = 0; i < brojRedaka; i++) {
      for (int j = 0; j < brojStupaca; j++) {
        matrica[i][j] = otvoreniTekst.charAt(i*brojStupaca + j);
      }
    }
    sifrat = "";
    for (int stupac : vrijednosti) {
      for (int i = 0; i < brojRedaka; i++) {
        sifrat += matrica[i][stupac-1];
      }
    }
  }

  public void desifriraj() {
    otvoreniTekst = "";
    brojRedaka = sifrat.length()/brojStupaca;
    char[][] matrica = new char[brojRedaka][brojStupaca];
    for (int i = 0; i < brojRedaka; i++) {
      for(int j = 0; j < brojStupaca; j++) {
        matrica[i][vrijednosti.get(j)-1] = sifrat.charAt(brojRedaka*j + i);
      }
    }
    for (int i = 0; i < brojRedaka; i++) {
      for (int j = 0; j < brojStupaca; j++) {
        otvoreniTekst += matrica[i][j];
      }
    }
  }

  public boolean provjeriVrijednosti() {
    Vector<Integer> tmp = new Vector<>();
    for (int i = 0; i < vrijednosti.size(); i++) {
      tmp.add(i+1);
    }
    for (Integer broj : vrijednosti) {
      if (!tmp.contains(broj)) return false;
    }
    for (Integer broj : tmp) {
      if (!vrijednosti.contains(broj)) return false;
    }
    return true;
  }
}
