package pmf.math.kriptosustavi;

import pmf.math.util.Abeceda;

import java.util.Arrays;

public class CezarKljucnaRijec extends Supstitucijska {
  // Ključ = (ključna riječ, mjesto od koje počinje)
  private String ključnaRiječ;
  int pomak;

  public CezarKljucnaRijec(String ključnaRiječ, int pomak) {
    this.ključnaRiječ = ključnaRiječ;
    this.pomak = pomak % 26;
    boolean[] iskoristeni = new boolean[26];
    Arrays.fill(iskoristeni, false);

    // Računamo permutacije prema zadanom ključu.
    this.permutacija = new int[26];
    this.inverznaPermutacija = new int[26];

    // Upisujemo slova ključne riječi.
    int j = pomak;
    for (char slovo : ključnaRiječ.toCharArray()) {
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
