package pmf.math.kriptosustavi;

import pmf.math.algoritmi.Abeceda;
import pmf.math.obradaunosa.ObradaUnosa;

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
      Arrays.fill(this.permutacija, 0);
      return;
    }

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
}
