package pmf.math.kriptosustavi;

import pmf.math.algoritmi.Abeceda;
import pmf.math.baza.dao.TekstGrafDAO;
import pmf.math.obradaunosa.ObradaUnosa;
import pmf.math.pomagala.GeneratorTeksta;
import pmf.math.pomagala.Stoperica;

import java.util.Arrays;

public class CezarKljucnaRijecKriptosustav extends SupstitucijskaKriptosustav {
  // Ključ = (ključna riječ, mjesto od koje počinje)
  public CezarKljucnaRijecKriptosustav(String kljucnaRijec, int pomak) {
    boolean[] iskoristeni = new boolean[26];
    Arrays.fill(iskoristeni, false);

    // Računamo permutacije prema zadanom ključu.
    this.permutacija = new int[26];
    this.inverznaPermutacija = new int[26];

    // Ako je upisana kriva ključna riječ, stvori nevažeću permutaciju.
    if (ObradaUnosa.kriviUnos(kljucnaRijec)) {
      Arrays.fill(this.permutacija, 0);
      Arrays.fill(this.inverznaPermutacija, 0);
      return;
    }

    pomak = ((pomak % 26) + 26) % 26; // Da ne ispadne negativan.
    // Upisujemo slova ključne riječi.
    int j = pomak;
    for (char slovo : kljucnaRijec.toCharArray()) {
      int broj = Abeceda.uBroj(slovo);
      if (!iskoristeni[broj]) {
        permutacija[j % 26] = broj;
        inverznaPermutacija[broj] = j % 26;
        ++j;
        iskoristeni[broj] = true;
      }
    }
    // Upisujemo preostala slova.
    for (int i = 0; i < 26; i++) {
      if (!iskoristeni[i]) {
        permutacija[j % 26] = i;
        inverznaPermutacija[i] = j % 26;
        ++j;
      }
    }
  }

  public static int[] simuliraj(int[] duljineTekstova, TekstGrafDAO.VrstaSimulacije vrstaSimulacije, int brojIteracija) {
    CezarKljucnaRijecKriptosustav cezarKljucnaRijecKriptosustav = new CezarKljucnaRijecKriptosustav("KRIPTOGRAFIJA", 8);
    int[] vremenaIzvodenja = new int[duljineTekstova.length];
    Stoperica stoperica = new Stoperica();
    for (int i = 0; i < duljineTekstova.length; i++) {
      for(int t = 0; t < brojIteracija; t++) {
        String tekst = GeneratorTeksta.generirajTekst(duljineTekstova[i]);
        stoperica.resetiraj();
        stoperica.pokreni();
        switch (vrstaSimulacije) {
          case SIFRIRAJ -> cezarKljucnaRijecKriptosustav.sifriraj(tekst);
          case DESIFRIRAJ -> cezarKljucnaRijecKriptosustav.desifriraj(tekst);
        }
        stoperica.zaustavi();
        vremenaIzvodenja[i] += stoperica.vrijeme();
      }
      vremenaIzvodenja[i] = vremenaIzvodenja[i] / brojIteracija;
    }

    return vremenaIzvodenja;
  }
}
