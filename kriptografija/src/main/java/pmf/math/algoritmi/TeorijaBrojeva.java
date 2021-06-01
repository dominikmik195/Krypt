package pmf.math.algoritmi;

import java.lang.Math;
import java.math.BigInteger;

public class TeorijaBrojeva {
    public static boolean prost(int broj) {
        if(broj == 1) return false;
        for(int i = 2; i < Math.floor(Math.sqrt(broj))+1; i++) {
            if(broj % i == 0) return false;
        }
        return true;
    }
    public static int velikiModulo(int baza, int exp, int modulo) {
        BigInteger br = new BigInteger(String.valueOf(baza));
        BigInteger mod = new BigInteger(String.valueOf(modulo));
        BigInteger x = br.mod(mod);
        System.out.println("x - bigInt " + x);
        int potencija = 2;
        while (potencija <= exp) {
            System.out.println("x  " + x);
            x = x.pow(2).mod(mod);
            potencija *= 2;
        }
        potencija /= 2;
        for(int i = potencija; i < exp; i++) {
            x = x.multiply(br.mod(mod)).mod(mod);
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

    public static boolean relativnoProsti(int broj1, int broj2) {
        for (int i = 2; i < Math.max(broj1, broj2); i++) {
            if(broj1%i == 0 && broj2%i == 0) return false;
        }
        return true;
    }

    public static int eulerovaFunkcija(int broj) {
        int kolicina = 0;
        for(int i = 1; i <= broj; i++) {
            if(relativnoProsti(i, broj)) kolicina++;
        }
        return kolicina;
    }

    public static int posebnaEulerovaFunkcija(int n, int prost1, int prost2) {
        if(n == prost1*prost2) return n-prost1-prost2+1;
        else return -1;
    }
}
