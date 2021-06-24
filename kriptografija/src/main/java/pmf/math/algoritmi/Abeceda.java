package pmf.math.algoritmi;

public class Abeceda {
  // _ se koristi u supstitucijskoj šifri kada vrijednost slova nije zadana, a vrijednost mu je -1.
  public static final String abeceda = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  public static final char[] charAbeceda = abeceda.toCharArray();

  public static int uBroj(char slovo) {
    if (slovo == '_' || slovo == ' ') return -1;
    else return abeceda.indexOf(Character.toUpperCase(slovo));
  }

  public static char uSlovo(int broj) {
    if (broj == -1) return '_';
    else return charAbeceda[broj % 26];
  }

  public static int zbroj(int a, int b) {
    int zbroj = a + b;
    while(zbroj < 0) zbroj += 26;
    return zbroj % 26;
  }

  public static char zbroj(char a, char b) {
    return uSlovo(zbroj(uBroj(a), uBroj(b)));
  }

  public static int inverz(int broj) {
    while(broj < 0) broj += 26;
    return (26 - broj) % 26;
  }

  public static char inverz(char slovo) {
    return charAbeceda[(26 - uBroj(slovo)) % 26];
  }
}
