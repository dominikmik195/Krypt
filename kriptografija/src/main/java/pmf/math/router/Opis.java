package pmf.math.router;

import static pmf.math.konstante.DuljineTeksta.OPIS_TEKST_REDAK_MAX;

import java.util.Arrays;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Opis {

  public JPanel glavniPanel;
  private JTextArea gornjiTextPane;
  private JTextArea donjiTextPane;
  private JPanel gornjiPanel;
  private JPanel donjiPanel;

  public void postaviTekst(String gornji, String donji) {
    gornjiTextPane.setText(razlomiTekst(gornji));
    gornjiTextPane.setCaretPosition(0);
    donjiTextPane.setText(razlomiTekst(donji));
    donjiTextPane.setCaretPosition(0);
  }

  private String razlomiTekst(String tekst) {
    StringBuilder izlazniTekst = new StringBuilder();
    Arrays.stream(tekst.split("\n")).forEach(redak -> {
      izlazniTekst.append(razlomiRedak(redak)).append("\n");
    });
    return izlazniTekst.toString();
  }

  private String razlomiRedak(String redak) {
    String izlazniString = redak;
    int div = redak.length() / OPIS_TEKST_REDAK_MAX;
    for(int i = 1; i <= div; i++) {
      int pozicija = OPIS_TEKST_REDAK_MAX * i - 1;
      if(pozicija + 2 <= izlazniString.length() &&
          izlazniString.substring(pozicija, pozicija + 2).matches("[a-zA-Z0-9čćšđžČĆŠŽĐ]*")) {
        if(izlazniString.substring(pozicija - 1, pozicija).matches("[a-zA-Z0-9čćšđžČĆŠŽĐ]*")) {
          izlazniString = dodajUString(izlazniString, pozicija, "-");
        }
        else {
          izlazniString = dodajUString(izlazniString, pozicija, " ");
        }
        div = izlazniString.length() / OPIS_TEKST_REDAK_MAX;
      }
    }
    return izlazniString;
  }

  private String dodajUString(String tekst, int pocetak, String dodatak) {
    return tekst.substring(0, pocetak) + dodatak + tekst.substring(pocetak);
  }
}
