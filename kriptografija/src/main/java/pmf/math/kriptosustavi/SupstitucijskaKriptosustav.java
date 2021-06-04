package pmf.math.kriptosustavi;

import pmf.math.algoritmi.Abeceda;
import pmf.math.kalkulatori.SupstitucijskaKalkulator;

import java.util.ArrayList;
import java.util.Arrays;

public class SupstitucijskaKriptosustav {
  // Permutacije se koriste kod (de)šifriranja svih supstitucijskih šifri, a računaju
  // se na različit način ovisno o šiframa (koje nasljeđuju ovu klasu). Tajni ključ supstitucijskih
  // šifri se koristi za računanje ovih permutacija i nije ga potrebno čuvati.
  protected int[] permutacija;
  protected int[] inverznaPermutacija;

  public SupstitucijskaKriptosustav() {}

  public SupstitucijskaKriptosustav(int[] permutacija) {
    this.permutacija = permutacija;

    // Sami računamo inverznu permutaciju.
    inverznaPermutacija = new int[26];
    Arrays.fill(inverznaPermutacija, -1);
    for (int i = 0; i < 26; i++) if (permutacija[i] != -1) inverznaPermutacija[permutacija[i]] = i;
  }

  // Šifrira otvoreni tekst i vraća šifrat.
  public String sifriraj(String otvoreniTekst) {
    StringBuilder sifrat = new StringBuilder();

    for (char slovo : otvoreniTekst.toCharArray()) {
      char novoSlovo = Abeceda.uSlovo(permutacija[Abeceda.uBroj(slovo)]);
      sifrat.append(novoSlovo);
    }

    return sifrat.toString().replaceAll("(.{5})", "$0 "); // Razmak svako peto slovo.
  }
  // Dešifrira šifrat i vraća otvoreni tekst.
  public String desifriraj(String sifrat) {
    StringBuilder otvoreniTekst = new StringBuilder();

    for (char slovo : sifrat.toCharArray()) {
      char novoSlovo = Abeceda.uSlovo(inverznaPermutacija[Abeceda.uBroj(slovo)]);
      otvoreniTekst.append(novoSlovo);
    }

    return otvoreniTekst.toString().replaceAll("(.{5})", "$0 "); // Razmak svako peto slovo.
  }

  public char[] dohvatiPermutacijuSlova() {
    char[] slova = new char[26];
    for (int i = 0; i < 26; i++) slova[i] = Abeceda.uSlovo(permutacija[i]);

    return slova;
  }
}
