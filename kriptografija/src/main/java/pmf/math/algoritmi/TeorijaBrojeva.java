package pmf.math.algoritmi;

import pmf.math.kriptosustavi.ElGamalKriptosustav;
import pmf.math.kriptosustavi.RSAKriptosustav;

import java.math.BigInteger;

public class TeorijaBrojeva {
  public static native boolean prost(long broj);

  public static native long modularnoPotenciranje(long baza, long exp, long modulo);

  public static int redElementa(int element, int modulo) {
    int red = 1;
    long ostatak = modularnoPotenciranje(element, red, modulo);
    while (ostatak != 1) {
      if(ElGamalKriptosustav.prekid || RSAKriptosustav.prekid){
        return -1;
      }
      red++;
      ostatak = modularnoPotenciranje(element, red, modulo);
    }
    return red;
  }

  public static int najmanjiPrimitivniKorijen(int modulo) {
    // Ova funkcija je malo nepotpuna, ali je u redu jer
    // ćemo je primijenjivati samo na onom modulu koji je prost broj
    int korijen = 1;
    int red = redElementa(korijen, modulo);
    while (red != modulo - 1) {
      if(ElGamalKriptosustav.prekid || RSAKriptosustav.prekid){
        return -1;
      }
      korijen++;
      red = redElementa(korijen, modulo);
    }
    return korijen;
  }

  public static boolean primitivniKorijen(int element, int modulo) {
    // Ova funkcija je malo nepotpuna, ali je u redu jer
    // ćemo je primijenjivati samo na onom modulu koji je prost broj
    return redElementa(element, modulo) == modulo - 1;
  }

  // Multiplikativni inverzi modulo 26. Spremamo ih ovdje kako se ne bi morali uvijek iznova
  // računati budući da se često koriste. -1 je oznaka da inverz ne postoji.
  public static final int[] inverziModulo26 = {
    -1, 1, -1, 9, -1, 21, -1, 15, -1, 3, -1, 19, -1, -1, -1, 7, -1, 23, -1, 11, -1, 5, -1, 17, -1,
    25
  };

  public static int inverz(int broj, int modulo) {
    // Računamo inverz i koristimo se brute force metodom. Funkcija je relativno brza, ali treba
    // paziti na granicu.
    // Naime, pokušamo kao granicu definirati modulo*modulo, ali ako je taj modulo preveliki,
    // onda će granica (umnožak) biti prevelika, pa je definiramo kao maksimalnu moguću vrijednost u
    // tom slučaju.
    int granica = modulo * modulo;
    BigInteger br = new BigInteger(String.valueOf(broj));
    BigInteger mod = new BigInteger(String.valueOf(modulo));
    if (granica < 0) granica = Integer.MAX_VALUE;
    for (int i = 0; i < granica; i++) {
      if(ElGamalKriptosustav.prekid || RSAKriptosustav.prekid){
        return -1;
      }
      if (br.multiply(new BigInteger(String.valueOf(i))).mod(mod).intValue() == 1) {
        return i;
      }
    }
    return -1;
  }

  public static boolean relativnoProsti(int broj1, int broj2) {
    for (int i = 2; i < Math.max(broj1, broj2); i++) {
      if (broj1 % i == 0 && broj2 % i == 0) return false;
    }
    return true;
  }

  public static int posebnaEulerovaFunkcija(int prost1, int prost2) {
    // Trivijalna funkcija, ali definirana je na ovaj način kako bismo naznačili da doista radimo s
    // Eulerovom funkcijom.
    return prost1 * prost2 - prost1 - prost2 + 1;
  }
}
