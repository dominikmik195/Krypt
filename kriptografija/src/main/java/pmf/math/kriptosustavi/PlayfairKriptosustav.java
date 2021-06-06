package pmf.math.kriptosustavi;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Locale;
import pmf.math.konstante.PlayfairAbecede;

public class PlayfairKriptosustav {

  private String[][] trenutnaTablica;

  public enum Jezik {
    HRVATSKI,
    ENGLESKI
  }

  public enum Smijer {
    SIFRIRAJ,
    DESIFRIRAJ
  }

  public String[][] dohvatiPlayfairTablicu(Jezik jezik, String kljuc) {
    trenutnaTablica = new String[5][5];
    Deque<String> abeceda = new LinkedList<String>();
    switch (jezik) {
      case HRVATSKI -> abeceda.addAll(Arrays.asList(PlayfairAbecede.HRVATSKA_ABECEDA.split(" ")));
      case ENGLESKI -> abeceda.addAll(Arrays.asList(PlayfairAbecede.ENGLESKA_ABECEDA.split(" ")));
    }
    Arrays.stream(pripremiString(kljuc).split("")).forEach(slovoKljuca -> {
      if (jezik == Jezik.ENGLESKI && slovoKljuca.equals("J") && abeceda.removeIf(slovo -> slovo.equals("I"))) {
        abeceda.addFirst("I");
      }
      if (jezik == Jezik.HRVATSKI && slovoKljuca.equals("W") && abeceda.removeIf(slovo -> slovo.equals("V"))) {
        abeceda.addFirst("V");
      }
      if (abeceda.removeIf(slovo -> slovo.equals(slovoKljuca))) {
        abeceda.addFirst(slovoKljuca);
      }
    });

    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        trenutnaTablica[i][j] = abeceda.remove();
      }
    }
    return trenutnaTablica;
  }

  public String sifriraj(String otvoreniTekst) {
    return playfairTransformirajTekst(otvoreniTekst, Smijer.SIFRIRAJ);
  }

  public String desifriraj(String sifrat) {
    return playfairTransformirajTekst(sifrat, Smijer.DESIFRIRAJ);
  }

  public String playfairTransformirajTekst(String tekst, Smijer smijer) {
    StringBuilder noviTekst = new StringBuilder();
    tekst = tekst.replaceAll(" ", "");
    for (int i = 0; i < tekst.length(); i += 2) {
      String parSlova = tekst.substring(i, Math.min(i + 2, tekst.length()));
      noviTekst.append(parSlova.length() == 2 ?
          playFairTransformirajSlova(parSlova.substring(0, 1), parSlova.substring(1, 2), smijer)
          : parSlova)
          .append(i + 2 < tekst.length() ? " " : "");
    }
    return noviTekst.toString();
  }

  public String playFairTransformirajSlova(String lijevo, String desno, Smijer smijer) {
    if(lijevo.equals(desno)) return lijevo + desno;
    int lijevoX = -1, lijevoY = -1, desnoX = -1, desnoY = -1, pronadeno = 0;
    for(int i = 0; i < 5 && pronadeno < 2; i++) {
      for(int j = 0; j < 5 && pronadeno < 2; j++) {
        if (trenutnaTablica[i][j].equals(lijevo)) {
          lijevoX = i;
          lijevoY = j;
          pronadeno++;
        }
        if (trenutnaTablica[i][j].equals(desno)) {
          desnoX = i;
          desnoY = j;
          pronadeno++;
        }
      }
    }
    int pomak = 0;
    if(smijer == Smijer.SIFRIRAJ) pomak = 1;
    if(smijer == Smijer.DESIFRIRAJ) pomak = 4;
    if(lijevoX == desnoX) {
      return trenutnaTablica[lijevoX][(lijevoY + pomak)%5] + trenutnaTablica[desnoX][(desnoY + pomak)%5];
    }
    else if(lijevoY == desnoY) {
      return trenutnaTablica[(lijevoX + pomak)%5][lijevoY] + trenutnaTablica[(desnoX + pomak)%5][desnoY];
    }
    else  {
      return trenutnaTablica[lijevoX][desnoY] + trenutnaTablica[desnoX][lijevoY];
    }
  }

  public String pripremiString(String string) {
    StringBuilder filtriraniString = new StringBuilder();
    Arrays.stream(string.toUpperCase(Locale.ROOT).split("")).distinct().forEach(filtriraniString::append);
    StringBuilder output = new StringBuilder();
    for (int i = filtriraniString.toString().length() - 1; i >= 0; i--) {
      output.append(filtriraniString.toString().charAt(i));
    }
    return output.toString();
  }

}
