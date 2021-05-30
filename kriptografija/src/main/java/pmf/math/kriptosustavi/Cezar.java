package pmf.math.kriptosustavi;

public class Cezar extends Supstitucijska {
  // Tajni ključ = pomak.
  public Cezar(int tajniKljuc) {
    // Izračunaj permutaciju prema pomaku (tajnom ključu).
    this.permutacija = new int[26];
    for (int i = 0; i < 26; i++) permutacija[i] = (i + tajniKljuc + 26) % 26;

    // Izračunaj inverznu permutaciju prema pomaku (tajnom ključu).
    this.inverznaPermutacija = new int[26];
    for (int i = 0; i < 26; i++) inverznaPermutacija[i] = (i - tajniKljuc + 26) % 26;
  }
}
