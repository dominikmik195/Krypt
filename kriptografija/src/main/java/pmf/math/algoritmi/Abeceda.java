package pmf.math.algoritmi;

public class Abeceda {
  // _ se koristi u supstitucijskoj šifri kada vrijednost slova nije zadana, a vrijednost mu je -1.
  private static final String abeceda = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final char[] charAbeceda = abeceda.toCharArray();

  public static int uBroj(char slovo) {
    if (slovo == '_' || slovo == ' ') return -1;
    else return abeceda.indexOf(Character.toUpperCase(slovo));
  }

  public static char uSlovo(int broj) {
    if (broj == -1) return '_';
    else return charAbeceda[broj % 26];
  }
}
