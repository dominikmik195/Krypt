package pmf.math.kalkulatori;

import pmf.math.algoritmi.TeorijaBrojeva;
import pmf.math.kriptosustavi.RSAKriptosustav;
import pmf.math.obradaunosa.ObradaUnosaRSA;
import pmf.math.router.Konzola;

import javax.swing.*;

public class RSAKalkulator extends JDialog {
  private final Konzola konzola;
  public JPanel glavniPanel;
  private JTextField pBrojField;
  private JTextField otvoreniTekstArea;
  private JTextField sifratArea;
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
  private JButton lijevoButton;
  private JButton desnoButton;
    private JButton odaberiPodatkeButton;
  private JLabel pLabelBaza;
  private JLabel qLabelBaza;
  private JLabel nLabelBaza;
  private JLabel dLabelBaza;
  private JLabel eLabelBaza;
  private JPanel podatciPanel;

  private final static RSAKriptosustav stroj = new RSAKriptosustav();

  public RSAKalkulator(Konzola _konzola) {
    konzola = _konzola;

    sifrirajButton.addActionListener(
            ev -> {
              new Thread(() -> {
                stroj.reinicijaliziraj();
                int broj = PQDEBrojSifrat(4);
                if (broj < 0) {
                  System.out.println("flag");
                  stroj.prosiriPoruku("Uneseni otvoreni tekst nije ispravan.");
                  stroj.setOK(false);
                }
                int[] npq = dohvatiNPQ();
                if (npq[3] == 0) stroj.setOK(false);
                if (stroj.isOK()) {
                  // Nama su bitni p i q, jer računanje pomoću njih je puno lakše.
                  // Ako p i q nisu uneseni, oni se izračunaju obzirom na uneseni n (ako je ispravan, dakako).
                  int p = npq[1];
                  int q = npq[2];
                  int e = PQDEBrojSifrat(3);
                  if (e < 0) {
                    stroj.prosiriPoruku(vratiGreske(3, e));
                    stroj.setOK(false);
                  }
                  else {
                    stroj.setP(p);
                    stroj.setQ(q);
                    stroj.setN(p*q);
                    stroj.e = e;
                    stroj.sifriraj(broj);
                  }
                }

                SwingUtilities.invokeLater(() -> {
                  if(stroj.isOK()) {
                    sifratArea.setText(String.valueOf(stroj.vratiSifrat()));
                    konzola.ispisiPoruku("Šifriranje uspješno!");
                  }
                  else {
                    konzola.ispisiPoruku(stroj.getPoruke());
                    konzola.ispisiGresku("Šifriranje neuspješno!");
                  }
                });
              }).start();
            });
    desifrirajButton.addActionListener(
            ev -> {
              new Thread(() -> {
                stroj.setPoruke("");
                stroj.setNapredak(0);
                stroj.setOK(true);
                int sifrat = PQDEBrojSifrat(5);
                if (sifrat < 0) {
                  stroj.prosiriPoruku("Uneseni šifrat nije ispravan.");
                  stroj.setOK(false);
                }
                int[] npq = dohvatiNPQ();
                if (npq[3] == 0) stroj.setOK(false);
                if(stroj.isOK()) {
                  int p = npq[1];
                  int q = npq[2];
                  int d = PQDEBrojSifrat(2);
                  if (d < 0) {
                    stroj.prosiriPoruku(vratiGreske(3, d));
                    stroj.setOK(false);
                  }
                  else {
                    stroj.setP(p);
                    stroj.setQ(q);
                    stroj.setD(d);
                    stroj.setN(p*q);
                    stroj.postaviSifrat(sifrat);
                  }
                }

                SwingUtilities.invokeLater(() -> {
                  if(stroj.isOK()) {
                    otvoreniTekstArea.setText(String.valueOf(stroj.desifriraj()));
                    konzola.ispisiPoruku("Dešifriranje uspješno!");
                  }
                  else {
                    konzola.ispisiPoruku(stroj.getPoruke());
                    konzola.ispisiGresku("Dešifriranje neuspješno!");
                  }
                });
              }).start();
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
        stroj.prosiriPoruku(vratiGreske(0, npq[1]));
        stroj.prosiriPoruku(vratiGreske(1, npq[2]));
        stroj.prosiriPoruku(vratiGreske(2, npq[0]));
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

  private String vratiGreske(int kod, int rezultat) {
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
        return "Broj n nije ispravnog formata! Daljnji postupak nije moguć.";
      else return "Broj " + slovo + " nije ispravnog formata!";
    } else if (rezultat == -2) {
      if (kod == 2)
        return "Broj n ne smije biti negativan! Daljnji postupak nije moguć.";
      else return "Broj " + slovo + " ne smije biti negativan!";
    } else if (rezultat == -3) {
      if (kod <= 1) return "Broj " + slovo + " mora biti prost!";
      if (kod == 2)
        return "Broj n nije umnožak prostih brojeva! Daljnji postupak nije moguć.";
    }
    return "";
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
    new Thread(() -> {
      stroj.setNapredak(0);
      stroj.setPoruke("");
      stroj.setOK(true);
      int p, q, n, d, e;
      int[] pq = {-1, -1};
      p = PQDEBrojSifrat(0);
      q = PQDEBrojSifrat(1);
      if (p < 0 || q < 0) {
        // U slučaju da p ili q nisu u redu, ispisujemo greške i dohvaćamo n.
        stroj.prosiriPoruku(vratiGreske(0, p));
        stroj.prosiriPoruku(vratiGreske(1, q));
        n = probajN();
        if (n < 0) {
          // Ako niti n nije u redu, ne možemo više ništa.
          stroj.prosiriPoruku(vratiGreske(2, n));
          stroj.setOK(false);
        } else {
          // Ako je n u redu, postavljamo nove p i q.
          pq = RSAKriptosustav.rastaviNNaPiQ(n);
          p = pq[0];
          q = pq[1];
          stroj.prosiriPoruku("Postavljeni su novi p i q.");
        }
      } else {
        // Ako su i p i q u redu, dohvaćamo n i provjeravamo je li u redu te ga ispravljamo po potrebi.
        n = probajN();
        if (n > 0 && n == p * q)
          ;
        else {
          if (n > 0) stroj.prosiriPoruku("Uneseni n nije umnožak unesenih p i q! Ispravljam...");
          else stroj.prosiriPoruku("Neispravan n! Ispravljam...");
          n = p * q;
        }
      }
      if(stroj.isOK()) {
        d = PQDEBrojSifrat(2);
        e = PQDEBrojSifrat(3);
        stroj.prosiriPoruku(vratiGreske(3, d));
        stroj.prosiriPoruku(vratiGreske(4, e));
        if (d < 0 && e < 0) {
          // Ako niti d niti e nisu u redu postavljeni, postavljamo ih.
          int[] de = RSAKriptosustav.nadjiDiE(p, q);
          d = de[0];
          e = de[1];
          stroj.prosiriPoruku("Postavljam nove d i e.");
        } else if (e < 0) {
          // Ako samo e nije u redu postavljen, provjeravamo je li dobro postavljen d te,
          // ako je, postavljamo odgovarajući e, a ako nije, postavljamo novi d i e.
          if (ObradaUnosaRSA.provjeriD(p, q, d)) {
            e = RSAKriptosustav.nadjiDiliE(d, p, q);
            stroj.prosiriPoruku("Postavljam novi e.");
          } else {
            stroj.prosiriPoruku("Za uneseni d ne postoji odgovarajući e.");
            int[] de = RSAKriptosustav.nadjiDiE(p, q);
            d = de[0];
            e = de[1];
          }
        } else if (d < 0) {
          // ANalogno kao i u prethodnom slučaju.
          if (ObradaUnosaRSA.provjeriD(p, q, e)) {
            d = RSAKriptosustav.nadjiDiliE(e, p, q);
            stroj.prosiriPoruku("Postavljam novi d.");
          } else {
            stroj.prosiriPoruku("Za uneseni e ne postoji odgovarajući d.");
            int[] de = RSAKriptosustav.nadjiDiE(p, q);
            d = de[0];
            e = de[1];
          }
        } else {
          // Ako su oba broja dobrog formata, provjeravamo odgovaraju li jedan drugome i po potrebi ispravljamo.
          if (ObradaUnosaRSA.provjeriDiE(p, q, d, e))
            ;
          else {
            stroj.prosiriPoruku(
                    "Uneseni d i e nisu kongruentni s  jedan modulo Euler(n)! Ispravljam...");
            if (ObradaUnosaRSA.provjeriD(p, q, d)) {
              e = RSAKriptosustav.nadjiDiliE(d, p, q);
            } else if (ObradaUnosaRSA.provjeriD(p, q, e)) {
              d = RSAKriptosustav.nadjiDiliE(e, p, q);
            } else {
              int[] de = RSAKriptosustav.nadjiDiE(p, q);
              d = de[0];
              e = de[1];
            }
          }
        }

        stroj.setP(p);
        stroj.setQ(q);
        stroj.setN(n);
        stroj.setD(d);
        stroj.setE(e);
      }
      if(stroj.isOK()) {
        stroj.prosiriPoruku("Podatci su kompatibilni.");
      }

      SwingUtilities.invokeLater(() -> {
        konzola.ispisiPoruku(stroj.getPoruke());
        if(stroj.isOK()) {
          pBrojField.setText(String.valueOf(stroj.getP()));
          qBrojField.setText(String.valueOf(stroj.getQ()));
          nBrojField.setText(String.valueOf(stroj.getN()));
          dBrojField.setText(String.valueOf(stroj.getD()));
          eBrojField.setText(String.valueOf(stroj.getE()));
        }
        else {
          konzola.ispisiGresku("Neuspješan ispravak!");
        }
      });
    }).start();
  }
}
