package pmf.math.kalkulatori;

import pmf.math.algoritmi.TeorijaBrojeva;
import pmf.math.kriptosustavi.ElGamalKriptosustav;
import pmf.math.obradaunosa.ObradaUnosaElGamal;
import pmf.math.router.Konzola;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ElGamalKalkulator {

  public JPanel glavniPanel;
  public Konzola konzola;

  private JLabel prostBrojLabel;
  private JLabel tajniKljucLabel;
  private JLabel tajniBrojLabel;
  private JLabel otvoreniTekstLabel;
  private JLabel sifratLabel;
  private JTextField prostBrojField;
  private JTextField tajniKljucField;
  private JTextField alfaField;
  private JTextField betaField;
  private JTextField tajniBrojField;
  private JTextArea otvoreniTekstArea;
  private JTextArea sifratArea;
  private JButton sifrirajButton;
  private JButton desifrirajButton;
  private JButton provjeriIIspraviButton;
  private JButton ocistiPoljaButton;

  public ElGamalKalkulator(Konzola _konzola) {
    konzola = _konzola;
    sifrirajButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            int[] PAB = dohvatiPAB();
            if(PAB[3] == 0) {
              konzola.ispisiGresku("Daljnji nastavak nije moguć!");
              return;
            }
            int tB = dohvatiVarijablu(4);
            if(tB < 0) {
              ispisiGreske(4, tB);
              konzola.ispisiGresku("Daljnji nastavak nije moguć!");
              return;
            }
            int broj = dohvatiVarijablu(5);
            if(broj < 0) {
              ispisiGreske(5, broj);
              konzola.ispisiGresku("Daljnji nastavak nije moguć!");
              return;
            }
            ElGamalKriptosustav stroj = new ElGamalKriptosustav(PAB[0], PAB[1], PAB[2]);
            stroj.sifriraj(broj, tB);
            sifratArea.setText(String.valueOf(stroj.vratiSifrat()));
            konzola.ispisiPoruku("Šifriranje uspješno!");
          }
        });

    desifrirajButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            int[] PAB = dohvatiPAB();
            if(PAB[3] == 0) {
              konzola.ispisiGresku("Daljnji nastavak nije moguć!");
              return;
            }
            int tK = dohvatiVarijablu(1);
            if(tK < 0) {
              ispisiGreske(1, tK);
              konzola.ispisiGresku("Daljnji nastavak nije moguć!");
              return;
            }
            try {
              String sifra = sifratArea.getText().strip();
              if (!ObradaUnosaElGamal.provjeriSifru(sifra)) {
                konzola.ispisiGresku("Krivi format šifre!");
                return;
              }
              ElGamalKriptosustav stroj = new ElGamalKriptosustav(PAB[0], PAB[1], PAB[2]);
              stroj.setTajniKljuc(tK);
              stroj.pohraniSifrat(sifra);
              otvoreniTekstArea.setText(String.valueOf(stroj.desifriraj()));
              konzola.ispisiPoruku("Dešifriranje uspješno!");
            } catch (StringIndexOutOfBoundsException | NumberFormatException ex) {
              konzola.ispisiGresku("Krivi format šifre!");
            }
          }
        });
    provjeriIIspraviButton.addActionListener(e -> provjeriIIspravi());
    ocistiPoljaButton.addActionListener(e -> {
      prostBrojField.setText("");
      tajniKljucField.setText("");
      alfaField.setText("");
      betaField.setText("");
      tajniBrojField.setText("");
      otvoreniTekstArea.setText("");
      sifratArea.setText("");
    });
  }

  private int dohvatiVarijablu(int kod) {
    // Funkcija koja pokušava dohvatiti traženu varijablu, obzirom na dani kod.
    int trazeni = -1;
    try {
      switch (kod) {
        case 0 -> trazeni = Integer.parseInt(prostBrojField.getText());
        case 1 -> trazeni = Integer.parseInt(tajniKljucField.getText());
        case 2 -> trazeni = Integer.parseInt(alfaField.getText());
        case 3 -> trazeni = Integer.parseInt(betaField.getText());
        case 4 -> trazeni = Integer.parseInt(tajniBrojField.getText());
        case 5 -> trazeni = Integer.parseInt(otvoreniTekstArea.getText());
      }
      if (trazeni < 0) return -2;
      if(kod == 0 && !TeorijaBrojeva.prost(trazeni)) return -3;
    } catch (NumberFormatException ex) {
      return -1;
    }
    return trazeni;
  }

  private void ispisiGreske(int kod, int rezultat) {
    // Funkcija koja ispisuje greške obzirom na kod varijable i rezultata dohvaćanja.
    // Ako je rezultat -1, unosa nema ili je neispravan. Ako je rezultat -2, unos je negativan broj.
    // Ako je rezultat -3, to znači da nisu zadovoljeni posebni uvjeti kriptosustava.
    String izraz = switch (kod) {
      case 0 -> "Prost broj";
      case 1 -> "Tajni ključ";
      case 2 -> "Alfa";
      case 3 -> "Beta";
      case 4 -> "Tajni broj";
      case 5 -> "Otvoreni tekst";
      default -> "";
    };
    if (rezultat == -1) {
      konzola.ispisiGresku(izraz + " nije ispravnog formata!");
    } else if (rezultat == -2) {
      konzola.ispisiGresku(izraz + " ne smije biti negativan!");
    } else if (rezultat == -3) {
      if (kod == 0) konzola.ispisiGresku("Uneseni broj p mora biti prost!");
      else if(kod == 2) konzola.ispisiGresku("Alfa nije primitivni korijen!");
      else if(kod == 3) konzola.ispisiGresku("Beta ne odgovara unesenom alfa!");
    }
  }

  private int[] dohvatiPAB() {
    // Funkcija dohvaća prost broj, alfa i beta. Koristi se kako bi se skratio kod jer se funkcija koristi
    // i u šifriranju i u dešifriranju. Sve tri varijable moraju biti ispravno unesene!
    int[] PAB = {-1, -1, -1, 1};
    PAB[0] = dohvatiVarijablu(0);
    ispisiGreske(0, PAB[0]);
    PAB[1] = dohvatiVarijablu(2);
    ispisiGreske(2, PAB[1]);
    PAB[2] = dohvatiVarijablu(3);
    ispisiGreske(3, PAB[2]);
    if(PAB[0] < 0 || PAB[1] < 0 || PAB[2] < 0) PAB[3] = 0;
    return PAB;
  }

  private void provjeriIIspravi() {
    // Funkcija koja provjerava unose i provjerava jesu li međusobno kopatibilni te ih po potrebi ispravlja.
    int pB, tK, alfa, beta, tB;
    // Bez prostog broja i tajnog ključa, ne možemo ništa provjeravati.
    pB = dohvatiVarijablu(0);
    if(pB < 0) {
      ispisiGreske(0, pB);
      konzola.ispisiGresku("Daljnji nastavak nije moguć.");
      return;
    }
    tK = dohvatiVarijablu(1);
    if(tK < 0) {
      ispisiGreske(1, tK);
      konzola.ispisiGresku("Daljnji nastavak nije moguć.");
      return;
    }
    alfa = dohvatiVarijablu(2);
    // Ovdje dodjeljujemo 'poseban' kod za alfu, ako nisu zadovoljeni uvjeti kriptosustava
    if(!ObradaUnosaElGamal.provjeriAlfa(alfa, pB)) alfa = -3;
    beta = dohvatiVarijablu(3);
    // Ovdje dodjeljujemo 'poseban' kod za betu, ako nisu zadovoljeni uvjeti kriptosustava
    if(!ObradaUnosaElGamal.provjeriBeta(alfa, beta, pB, tK)) beta = -3;
    if(alfa < 0) {
      // Ako alfa nije ispravan, računamo novi te postavljamo novi beta također.
      ispisiGreske(2, alfa);
      konzola.ispisiPoruku("Tražim alfa i beta...");
      alfa = ElGamalKriptosustav.noviAlfa(pB);
      alfaField.setText(String.valueOf(alfa));
      betaField.setText(String.valueOf(ElGamalKriptosustav.noviBeta(pB, alfa, tK)));
    }
    else if(alfa > 0) {
      if(beta < 0) {
        // Ako je alfa u redu a beta nije - nalazimo novi beta.
        ispisiGreske(3, beta);
        konzola.ispisiPoruku("Tražim beta...");
        betaField.setText(String.valueOf(ElGamalKriptosustav.noviBeta(pB, alfa, tK)));
      }
    }
    tB = dohvatiVarijablu(4);
    if(tB < 0) {
      // Ako tajni broj nije unesen, to nije katastrofa - postavljamo ga na bilo koji broj, u ovom slučaju 5.
      ispisiGreske(4, tB);
      tajniBrojField.setText(String.valueOf(5));
      konzola.ispisiPoruku("Postavljam tajni broj na 5...");
    }
    konzola.ispisiPoruku("Podatci su kompatibilni.");
  }
}
