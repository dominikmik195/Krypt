package pmf.math.kriptosustavi;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import pmf.math.konstante.PlayfairAbecede;

public class PlayfairKriptosustav {

  public enum Jezik {
    HRVATSKI,
    ENGLESKI
  }

  public String[][] dohvatiPlayfairTablicu(Jezik jezik, String kljuc) {
    String[][] playfairTableValues = new String[5][5];
    Deque<String> abeceda = new LinkedList<String>();
    switch (jezik) {
      case HRVATSKI -> abeceda.addAll(Arrays.asList(PlayfairAbecede.HRVATSKA_ABECEDA.split(" ")));
      case ENGLESKI -> abeceda.addAll(Arrays.asList(PlayfairAbecede.ENGLESKA_ABECEDA.split(" ")));
    }
    Arrays.stream(pripremiString(kljuc).split("")).forEach(slovoKljuca -> {
      if (abeceda.removeIf(slovo -> slovo.equals(slovoKljuca))) {
        abeceda.addFirst(slovoKljuca);
      }
      if (jezik == Jezik.ENGLESKI && (slovoKljuca.equals("I") || slovoKljuca.equals("J")) && abeceda.removeIf(slovo -> slovo.equals("IJ"))) {
        abeceda.addFirst("IJ");
      }
      if (jezik == Jezik.HRVATSKI && (slovoKljuca.equals("V") || slovoKljuca.equals("W")) && abeceda.removeIf(slovo -> slovo.equals("VW"))) {
        abeceda.addFirst("VW");
      }
    });

    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        playfairTableValues[i][j] = abeceda.remove();
      }
    }
    return playfairTableValues;
  }

  public String sifriraj(String otvoreniTekst) {
    return "";
  }

  public String desifriraj(String sifrat) {
    return "";
  }

  public String pripremiString(String string) {
    StringBuilder filtriraniString = new StringBuilder();
    Arrays.stream(string.split("")).distinct().forEach(filtriraniString::append);
    StringBuilder output = new StringBuilder();
    for (int i = filtriraniString.toString().length() - 1; i >= 0; i--) {
      output.append(filtriraniString.toString().charAt(i));
    }
    return output.toString();
  }

}
