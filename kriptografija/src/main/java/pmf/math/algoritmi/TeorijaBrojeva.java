package pmf.math.algoritmi;

import java.lang.Math;
import java.math.BigInteger;

public class TeorijaBrojeva {
    public static boolean prost(int broj) {
        for(int i = 2; i < Math.floor(Math.sqrt(broj))+1; i++) {
            if(broj % i == 0) return false;
        }
        return true;
    }
    public static int velikiModulo(int baza, int exp, int prost) {
        BigInteger br = new BigInteger(String.valueOf(baza));
        BigInteger pB = new BigInteger(String.valueOf(prost));
        BigInteger x = br.mod(pB);
        int potencija = 2;
        while (potencija <= exp) {
            x = x.pow(2).mod(pB);
            potencija *= 2;
        }
        potencija /= 2;
        for(int i = potencija; i < exp; i++) {
            x = x.multiply(br.mod(pB)).mod(pB);
        }
        return x.intValue();
    }

    public static int redElementa(int element, int modulo) {
        int red = 1;
        int ostatak = velikiModulo(element, red, modulo);
        while (ostatak != 1) {
            red++;
            ostatak = velikiModulo(element, red, modulo);
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
        return redElementa(element, modulo) == modulo-1;
    }

    public static int inverz(int broj, int modulo) {
        for (int i = 0; i < (modulo * modulo); i++) {
            if ((i * broj) % modulo == 1) {
                return i;
            }
        }
        return -1;
    }
}
