package pmf.math.util;

public class Abeceda {
  private static final String abeceda = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final char[] charAbeceda = abeceda.toCharArray();

  public static int uBroj(char slovo) {
    return abeceda.indexOf(Character.toUpperCase(slovo));
  }

  public static char uSlovo(int broj) {
    return charAbeceda[broj % 26];
  }
}
