package pmf.math.kriptosustavi;

public class Cezar extends Supstitucijska {
  // Ključ = pomak.
  private int tajniKljuč;

  public Cezar(int tajniKljuč) {
    this.tajniKljuč = tajniKljuč % 26;
    // Izračunaj permutaciju prema pomaku (tajnom ključu).
    this.permutacija = new int[26];
    for (int i = 0; i < 26; i++) permutacija[i] = (i + this.tajniKljuč + 26) % 26;

    // Izračunaj inverznu permutaciju prema pomaku (tajnom ključu).
    this.inverznaPermutacija = new int[26];
    for (int i = 0; i < 26; i++) inverznaPermutacija[i] = (i - this.tajniKljuč + 26) % 26;
  }
}
