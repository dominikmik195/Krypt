package pmf.math.kriptosustavi;

import pmf.math.util.Abeceda;

public class Supstitucijska {
  // Permutacije se koriste kod (de)šifriranja svih supstitucijskih šifri, a računaju
  // se na različit način ovisno o šiframa (koje nasljeđuju ovu klasu).
  protected int[] permutacija;
  protected int[] inverznaPermutacija;

  // Šifrira otvoreni tekst i vraća šifrat.
  public String šifriraj(String otvoreniTekst) {
    String šifrat = "";

    for (char slovo : otvoreniTekst.toCharArray()) {
      char novoSlovo = Abeceda.uSlovo(permutacija[Abeceda.uBroj(slovo)]);
      šifrat += novoSlovo;
    }

    return šifrat;

    // TODO: Dodati razmake svako peto slovo.
  }
  // Dešifrira šifrat i vraća otvoreni tekst.
  public String dešifriraj(String šifrat) {
    String otvoreniTekst = "";

    for (char slovo : šifrat.toCharArray()) {
      char novoSlovo = Abeceda.uSlovo(inverznaPermutacija[Abeceda.uBroj(slovo)]);
      otvoreniTekst += novoSlovo;
    }

    return otvoreniTekst;

    // TODO: Dodati razmake svako peto slovo.
  }

  public char[] dohvatiPermutacijuSlova() {
    char[] slova = new char[26];
    for (int i = 0; i < 26; i++)
      slova[i] = Abeceda.uSlovo(permutacija[i]);

    return slova;
  }
}
