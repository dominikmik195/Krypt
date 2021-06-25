package pmf.math.kalkulatori;

import pmf.math.algoritmi.TeorijaBrojeva;
import pmf.math.kriptosustavi.RSAKriptosustav;
import pmf.math.obradaunosa.ObradaUnosaRSA;
import pmf.math.router.Konzola;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RSAKalkulator extends JDialog {
  private final Konzola konzola;
  public JPanel glavniPanel;
  private JTextField pBrojField;
  private JTextArea otvoreniTekstArea;
  private JTextArea sifratArea;
  private JButton sifrirajButton;
  private JButton desifrirajButton;
  private JTextField qBrojField;
  private JTextField nBrojField;
  private JTextField dBrojField;
  private JTextField eBrojField;
  private JButton provjeriIIspraviPodatkeButton;
  private JButton ocistiPoljaButton;
  private JLabel otvoreniTekstLabel;
  private JLabel sifratLabel;

  public RSAKalkulator(Konzola _konzola) {
    konzola = _konzola;

    sifrirajButton.addActionListener(
            ev -> {
              int broj = PQDEBrojSifrat(4);
              if (broj < 0) {
                konzola.ispisiGresku("Uneseni otvoreni tekst nije ispravan.");
                return;
              }
              int[] npq = dohvatiNPQ();
              if (npq[3] == 0) return;
              // Nama su bitni p i q, jer računanje pomoću njih je puno lakše.
              // Ako p i q nisu uneseni, oni se izračunaju obzirom na uneseni n (ako je ispravan, dakako).
              int p = npq[1];
              int q = npq[2];
              int e = PQDEBrojSifrat(3);
              if (e < 0) ispisGresaka(3, e);
              else {
                RSAKriptosustav stroj = new RSAKriptosustav(p, q);
                stroj.e = e;
                stroj.sifriraj(broj);
                sifratArea.setText(String.valueOf(stroj.vratiSifrat()));
                konzola.ispisiPoruku("Šifriranje uspješno!");
              }
            });
    desifrirajButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent ev) {
            int sifrat = PQDEBrojSifrat(5);
            if (sifrat < 0) {
              konzola.ispisiGresku("Uneseni šifrat nije ispravan.");
              return;
            }
            int[] npq = dohvatiNPQ();
            if (npq[3] == 0) return;
            int p = npq[1];
            int q = npq[2];
            int d = PQDEBrojSifrat(2);
            if (d < 0) ispisGresaka(2, d);
            else {
              RSAKriptosustav stroj = new RSAKriptosustav(p, q);
              stroj.setD(d);
              stroj.postaviSifrat(sifrat);
              otvoreniTekstArea.setText(String.valueOf(stroj.desifriraj()));
              konzola.ispisiPoruku("Dešifriranje uspješno!");
            }
          }
        });
    provjeriIIspraviPodatkeButton.addActionListener(
            e -> provjeriIspravi());
    ocistiPoljaButton.addActionListener(
            e -> {
              pBrojField.setText("");
              qBrojField.setText("");
              nBrojField.setText("");
              dBrojField.setText("");
              eBrojField.setText("");
              otvoreniTekstArea.setText("");
              sifratArea.setText("");
            });
  }

  private int[] dohvatiNPQ() {
    // Funckija koja objedinjuje nekoliko donjih funkcija te dohvaća n, p i q.
    // Definirana je kako bi se skratio kod, jer je i za šifriranje i dešifriranje potreban isti postupak.
    // Funkciju implementiramo na taj način da je dovoljno unijeti valjani p i q ili valjani n za nastavak procesa.
    // Ukoliko su unesene sve tri varijable, uzimaju se p i q ako su ispravni (bez obzira na n).
    int[] npq = {-1, -1, -1, 1};
    npq[1] = PQDEBrojSifrat(0);
    npq[2] = PQDEBrojSifrat(1);
    if (npq[1] < 0 || npq[2] < 0) {
      npq[0] = probajN();
      if (npq[0] < 0) {
        ispisGresaka(0, npq[1]);
        ispisGresaka(1, npq[2]);
        ispisGresaka(2, npq[0]);
        npq[3] = 0;
      } else {
        int[] pq = RSAKriptosustav.rastaviNNaPiQ(npq[0]);
        npq[1] = pq[0];
        npq[2] = pq[1];
      }
    }
    return npq;
  }

  private int probajN() {
    // Funkcija koja pokušava dohvatiti n. Ako je neuspjelo, vraća -1 (ako ulaz nije broj ili ulaza nema),
    // -2 (ako je ulaz negativan) ili -3 (ako ulaz nije umnožak dvaju prostih brojeva). Inače vraća dohvaćeni broj.
    int n;
    try {
      n = Integer.parseInt(nBrojField.getText());
      if (n < 0) return -2;
      if (!ObradaUnosaRSA.provjeriNUmnozakProstih(n)) return -3;
    } catch (NumberFormatException ex) {
      return -1;
    }
    return n;
  }

  private int PQDEBrojSifrat(int kod) {
    // Funkcija koja, ovisno o kodu varijable, dohvaća pripadnu varijablu.
    // Vraća negativni cijeli broj ako je dohvaćanje neuspješno:
    // ako je rezultat -1, to znači da unosa nema ili nije broj
    // ako je rezultat -2, znači da je unos negativan broj
    // ako je unos -3, znači da ulaz nije prost(p ili q).
    // Inače, vraća pročitani broj.
    int trazeni = -1;
    try {
      if (kod == 0) trazeni = Integer.parseInt(pBrojField.getText());
      else if (kod == 1) trazeni = Integer.parseInt(qBrojField.getText());
      else if (kod == 2) trazeni = Integer.parseInt(dBrojField.getText());
      else if (kod == 3) trazeni = Integer.parseInt(eBrojField.getText());
      else if (kod == 4) trazeni = Integer.parseInt(otvoreniTekstArea.getText());
      else if (kod == 5) trazeni = Integer.parseInt(sifratArea.getText());
      if (trazeni < 0) return -2;
      else if (kod <= 1 && !TeorijaBrojeva.prost(trazeni)) return -3;
    } catch (NumberFormatException ex) {
      return -1;
    }
    return trazeni;
  }

  private void ispisGresaka(int kod, int rezultat) {
    // Funkcija koja ispisuje greške ovisno o kodu varijable i rezultatu dohvaćanja varijable.
    String slovo = switch (kod) {
      case 0 -> "p";
      case 1 -> "q";
      case 2 -> "n";
      case 3 -> "d";
      case 4 -> "e";
      default -> "";
    };
    if (rezultat == -1) {
      if (kod == 2)
        konzola.ispisiGresku("Broj n nije ispravnog formata! Daljnji postupak nije moguć.");
      else konzola.ispisiGresku("Broj " + slovo + " nije ispravnog formata!");
    } else if (rezultat == -2) {
      if (kod == 2)
        konzola.ispisiGresku("Broj n ne smije biti negativan! Daljnji postupak nije moguć.");
      else konzola.ispisiGresku("Broj " + slovo + " ne smije biti negativan!");
    } else if (rezultat == -3) {
      if (kod <= 1) konzola.ispisiGresku("Broj " + slovo + " mora biti prost!");
      if (kod == 2)
        konzola.ispisiGresku("Broj n nije umnožak prostih brojeva! Daljnji postupak nije moguć.");
    }
  }

  private void postaviDiE(int p, int q) {
    // Funkcija koja računa d i e za dane p i q i postavlja ih u odgovarajuća polja.
    int[] de = RSAKriptosustav.nadjiDiE(p, q);
    dBrojField.setText(String.valueOf(de[0]));
    eBrojField.setText(String.valueOf(de[1]));
    konzola.ispisiPoruku("Postavljeni su novi d i e.");
  }

  private void provjeriIspravi() {
    // Funkcija koja izvršava provjere unesenih brojeva i, po mogućnosti, ispravlja unose.
    int p, q, n, d, e;
    int[] pq = {-1, -1};
    p = PQDEBrojSifrat(0);
    q = PQDEBrojSifrat(1);
    if (p < 0 || q < 0) {
      // U slučaju da p ili q nisu u redu, ispisujemo greške i dohvaćamo n.
      ispisGresaka(0, p);
      ispisGresaka(1, q);
      n = probajN();
      if (n < 0) {
        // Ako niti n nije u redu, ne možemo više ništa.
        ispisGresaka(2, n);
        return;
      } else {
        // Ako je n u redu, postavljamo nove p i q.
        pq = RSAKriptosustav.rastaviNNaPiQ(n);
        p = pq[0];
        q = pq[1];
        pBrojField.setText(String.valueOf(pq[0]));
        qBrojField.setText(String.valueOf(pq[1]));
        konzola.ispisiPoruku("Postavljeni su novi p i q.");
      }
    } else {
      // Ako su i p i q u redu, dohvaćamo n i provjeravamo je li u redu te ga ispravljamo po potrebi.
      n = probajN();
      if (n > 0 && n == p * q)
        ;
      else {
        if (n > 0) konzola.ispisiGresku("Uneseni n nije umnožak unesenih p i q! Ispravljam...");
        else konzola.ispisiGresku("Neispravan n! Ispravljam...");
        n = p * q;
        nBrojField.setText(String.valueOf(n));
      }
    }
    d = PQDEBrojSifrat(2);
    e = PQDEBrojSifrat(3);
    ispisGresaka(2, d);
    ispisGresaka(3, e);
    if (d < 0 && e < 0) {
      // Ako niti d niti e nisu u redu postavljeni, postavljamo ih.
      postaviDiE(p, q);
    } else if (e < 0) {
      // Ako samo e nije u redu postavljen, provjeravamo je li dobro postavljen d te,
      // ako je, postavljamo odgovarajući e, a ako nije, postavljamo novi d i e.
      if (ObradaUnosaRSA.provjeriD(p, q, d)) {
        e = RSAKriptosustav.nadjiDiliE(d, p, q);
        eBrojField.setText(String.valueOf(e));
        konzola.ispisiPoruku("Postavljam novi e.");
      } else {
        konzola.ispisiGresku("Za uneseni d ne postoji odgovarajući e.");
        postaviDiE(p, q);
      }
    } else if (d < 0) {
      // ANalogno kao i u prethodnom slučaju.
      if (ObradaUnosaRSA.provjeriD(p, q, e)) {
        d = RSAKriptosustav.nadjiDiliE(e, p, q);
        dBrojField.setText(String.valueOf(d));
        konzola.ispisiPoruku("Postavljam novi d.");
      } else {
        konzola.ispisiGresku("Za uneseni e ne postoji odgovarajući d.");
        postaviDiE(p, q);
      }
    } else {
      // Ako su oba broja dobrog formata, provjeravamo odgovaraju li jedan drugome i po potrebi ispravljamo.
      if (ObradaUnosaRSA.provjeriDiE(p, q, d, e))
        ;
      else {
        konzola.ispisiGresku(
            "Uneseni d i e nisu kongruentni s  jedan modulo Euler(n)! Ispravljam...");
        if (ObradaUnosaRSA.provjeriD(p, q, d)) {
          eBrojField.setText(String.valueOf(RSAKriptosustav.nadjiDiliE(d, p, q)));
        } else if (ObradaUnosaRSA.provjeriD(p, q, e)) {
          dBrojField.setText(String.valueOf(RSAKriptosustav.nadjiDiliE(e, p, q)));
        } else {
          postaviDiE(p, q);
        }
      }
    }
    konzola.ispisiPoruku("Podatci su kompatibilni.");
  }
}
