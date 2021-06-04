package pmf.math.obradaunosa;

import pmf.math.kriptosustavi.CezarKriptosustav;
import pmf.math.kriptosustavi.SupstitucijskaKriptosustav;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ObradaUnosaSupstitucijska extends ObradaUnosa {
  // Provjerava točnost permutacije i teksta.
  public static boolean kriviUnos(int[] permutacija) {
    // Niti jedan broj u permutaciji (osim -1) se ne smije pojaviti dvaput.
    int[] counts = new int[26];
    for (int i = 0; i < 26; i++) if (permutacija[i] != -1) counts[permutacija[i]]++;
    for (int i = 0; i < 26; i++) if (counts[i] > 1) return true;

    return false;
  }

  public static String sifriraj(int[] permutacija, String tekst) {
    tekst = ocisti(tekst);
    if (kriviUnos(tekst) || kriviUnos(permutacija)) return "";

    return (new SupstitucijskaKriptosustav(permutacija)).sifriraj(tekst);
  }

  public static String desifriraj(int[] permutacija, String tekst) {
    tekst = ocisti(tekst);
    if (kriviUnos(tekst) || kriviUnos(permutacija)) return "";

    return (new SupstitucijskaKriptosustav(permutacija)).desifriraj(tekst);
  }
}
