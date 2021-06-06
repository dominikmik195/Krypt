package pmf.math.algoritmi;

import java.math.BigInteger;

public class TeorijaBrojeva {
  public static boolean prost(int broj) {
    if (broj == 1) return false;
    for (int i = 2; i < Math.floor(Math.sqrt(broj)) + 1; i++) {
      if (broj % i == 0) return false;
    }
    return true;
  }

  public static int modularnoPotenciranje(int baza, int exp, int modulo) {
    // Budući da nam niti klasa BigInteger nije dovoljna za baratanje jako velikim brojevima,
    // moramo se poslužiti modularnim potenciranjem kako bismo mogli izračunati neke kongruencije.
    BigInteger br = new BigInteger(String.valueOf(baza));
    BigInteger mod = new BigInteger(String.valueOf(modulo));
    BigInteger x = br.mod(mod);
    int potencija = 2;
    while (potencija <= exp) {
      x = x.pow(2).mod(mod);
      potencija *= 2;
    }
    potencija /= 2;
    for (int i = potencija; i < exp; i++) {
      x = x.multiply(br.mod(mod)).mod(mod);
    }
    return x.intValue();
  }

  public static int redElementa(int element, int modulo) {
    int red = 1;
    int ostatak = modularnoPotenciranje(element, red, modulo);
    while (ostatak != 1) {
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

  public static int inverz(int broj, int modulo) {
    // Računamo inverz i koristimo se brute force metodom. Funkcija je relativno brza, ali treba paziti na granicu.
    // Naime, pokušamo kao granicu definirati modulo*modulo, ali ako je taj modulo preveliki,
    // onda će granica (umnožak) biti prevelika, pa je definiramo kao maksimalnu moguću vrijednost u tom slučaju.
    int granica = modulo * modulo;
    if (granica < 0) granica = Integer.MAX_VALUE;
    for (int i = 0; i < granica; i++) {
      if ((i * broj) % modulo == 1) {
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
    // Trivijalna funkcija, ali definirana je na ovaj način kako bismo naznačili da doista radimo s Eulerovom funkcijom.
    return prost1 * prost2 - prost1 - prost2 + 1;
  }
}
