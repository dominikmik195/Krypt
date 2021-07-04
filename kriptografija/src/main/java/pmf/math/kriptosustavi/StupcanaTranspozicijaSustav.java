package pmf.math.kriptosustavi;

import lombok.Getter;
import lombok.Setter;
import pmf.math.obradaunosa.ObradaUnosa;

import java.util.Vector;

@Getter
@Setter
public class StupcanaTranspozicijaSustav {
  private int brojStupaca;
  private Vector<Integer> vrijednosti;
  private String otvoreniTekst;
  private String sifrat;
  private int brojRedaka;
  private boolean OK;
  private String poruke;

  public StupcanaTranspozicijaSustav() {
    vrijednosti = new Vector<>();
  }

  public void reinicijaliziraj() {
    poruke = "";
    OK = true;
    sifrat = "";
    otvoreniTekst = "";
  }

  public void prosiriPoruku(String dodatak) {
    poruke += dodatak + " ";
  }

  public String formatirajOtvoreniTekst() {
    StringBuilder noviTekst = new StringBuilder();
    String tmp = ObradaUnosa.ocistiStupcana(otvoreniTekst);
    for (int i = 0; i < tmp.length(); i++) {
      noviTekst.append(tmp.charAt(i));
      if(i%brojStupaca == brojStupaca-1) noviTekst.append("\n");
    }
    return noviTekst.toString();
  }

  public void sifriraj() {
    brojRedaka = (int)Math.ceil((double)otvoreniTekst.length()/brojStupaca);
    char[][] matricaUnos = new char[brojRedaka][brojStupaca];
    char[][] matricaIzlaz = new char[brojStupaca][brojRedaka];
    while(otvoreniTekst.length() < brojStupaca*brojRedaka) {
      otvoreniTekst += "X";
    }
    for (int i = 0; i < brojRedaka; i++) {
      for (int j = 0; j < brojStupaca; j++) {
        matricaUnos[i][j] = otvoreniTekst.charAt(i*brojStupaca + j);
      }
    }
    for (int i = 0; i < brojStupaca; i++) {
      for (int j = 0; j < brojRedaka; j++) {
        matricaIzlaz[vrijednosti.get(i)-1][j] = matricaUnos[j][i];
      }
    }
    sifrat = "";
    for (int i = 0; i < brojStupaca; i++) {
      for (int j = 0; j < brojRedaka; j++) {
        sifrat += matricaIzlaz[i][j];
      }
      sifrat += "\n";
    }
  }

  public void desifriraj() {
    otvoreniTekst = "";
    brojRedaka = sifrat.length()/brojStupaca;
    char[][] matricaSifrat = new char[brojStupaca][brojRedaka];
    char[][] matricaUnos = new char[brojRedaka][brojStupaca];
    for (int i = 0; i < brojStupaca; i++) {
      for(int j = 0; j < brojRedaka; j++) {
        matricaSifrat[i][j] = sifrat.charAt(brojRedaka*i + j);
      }
    }
    for (int i = 0; i < brojStupaca; i++) {
      for (int j = 0; j < brojRedaka; j++) {
        matricaUnos[j][vrijednosti.indexOf(i+1)] = matricaSifrat[i][j];
      }
    }
    otvoreniTekst = "";
    for (int i = 0; i < brojRedaka; i++) {
      for (int j = 0; j < brojStupaca; j++) {
        otvoreniTekst += matricaUnos[i][j];
      }
      otvoreniTekst += "\n";
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
