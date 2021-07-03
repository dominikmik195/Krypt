package pmf.math.kriptosustavi;

import pmf.math.algoritmi.TeorijaBrojeva;
import pmf.math.baza.dao.TekstGrafDAO;
import pmf.math.obradaunosa.ObradaUnosa;
import pmf.math.pomagala.GeneratorTeksta;
import pmf.math.pomagala.Stoperica;

import java.util.Arrays;

public class AfiniKriptosustav extends SupstitucijskaKriptosustav {
  // Tajni ključ = (a, b).
  public AfiniKriptosustav(int a, int b) {
    a = ObradaUnosa.ocisti(a);
    b = ObradaUnosa.ocisti(b);
    // Krivi unos. Stvori nevažeću permutaciju.
    permutacija = new int[26];
    inverznaPermutacija = new int[26];

    if (!TeorijaBrojeva.relativnoProsti(a, 26)) {
      Arrays.fill(this.permutacija, 0);
      Arrays.fill(this.inverznaPermutacija, 0);
      return;
    }

    // Izračunaj permutaciju za ispravan ključ (a, b).
    for (int i = 0; i < 26; i++) permutacija[i] = (a * i + b) % 26;
    for (int i = 0; i < 26; i++)
      inverznaPermutacija[i] = ObradaUnosa.ocisti(TeorijaBrojeva.inverziModulo26[a] * (i - b));
  }

  public static int[] simuliraj(int[] duljineTekstova, TekstGrafDAO.VrstaSimulacije vrstaSimulacije, int brojIteracija) {
    AfiniKriptosustav afiniKriptosustav = new AfiniKriptosustav(7, 14);
    int[] vremenaIzvodenja = new int[duljineTekstova.length];
    Stoperica stoperica = new Stoperica();
    for (int i = 0; i < duljineTekstova.length; i++) {
      for(int t = 0; t < brojIteracija; t++) {
        String tekst = GeneratorTeksta.generirajTekst(duljineTekstova[i]);
        stoperica.resetiraj();
        stoperica.pokreni();
        switch (vrstaSimulacije) {
          case SIFRIRAJ -> afiniKriptosustav.sifriraj(tekst);
          case DESIFRIRAJ -> afiniKriptosustav.desifriraj(tekst);
        }
        stoperica.zaustavi();
        vremenaIzvodenja[i] += stoperica.vrijeme();
      }
      vremenaIzvodenja[i] = vremenaIzvodenja[i] / brojIteracija;
    }

    return vremenaIzvodenja;
  }
}
