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
  private JCheckBox brojECheckBox;
  private JLabel otvoreniTekstLabel;
  private JTextArea otvoreniTekstArea;
  private JTextArea sifratArea;
  private JLabel sifratLabel;
  private JButton sifrirajButton;
  private JButton desifrirajButton;
  private JTextField qBrojField;
  private JTextField nBrojField;
  private JTextField dBrojField;
  private JTextField eBrojField;
  private JButton provjeriIIspraviPodatkeButton;
  private JCheckBox brojNCheckBox;
  private JButton buttonOK;

  public RSAKalkulator(Konzola _konzola) {
    konzola = _konzola;

    sifrirajButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent ev) {
            int broj = PQDEBrojSifrat(4);
            if(broj < 0) {
              konzola.ispisiGresku("Uneseni otvoreni tekst nije ispravan.");
              return;
            }
            int p = PQDEBrojSifrat(0);
            int q = PQDEBrojSifrat(1);
            ispisGresaka(0, p);
            ispisGresaka(1, q);
            int n = -1;
            if(p < 0 || q < 0) {
              n = probajN(true);
              if(n < 0) {
                ispisGresaka(2, n);
                return;
              }
              else {
                int[] pq = RSAKriptosustav.rastaviNNaPiQ(n);
                p = pq[0];
                q = pq[1];
              }
            }
            int e = PQDEBrojSifrat(3);
            if(e < 0) ispisGresaka(3, e);
            else {
              RSAKriptosustav stroj = new RSAKriptosustav(p, q);
              stroj.e = e;
              stroj.sifriraj(broj);
              sifratArea.setText(String.valueOf(stroj.vratiSifrat()));
              konzola.ispisiPoruku("Šifriranje uspješno!");
            }
          }
        });
    desifrirajButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent ev) {
            int sifrat = PQDEBrojSifrat(5);
            if(sifrat < 0) {
              konzola.ispisiGresku("Uneseni šifrat nije ispravan.");
              return;
            }
            int p = PQDEBrojSifrat(0);
            int q = PQDEBrojSifrat(1);
            ispisGresaka(0, p);
            ispisGresaka(1, q);
            int n = -1;
            if(p < 0 || q < 0) {
              n = probajN(true);
              if(n < 0) {
                ispisGresaka(2, n);
                return;
              }
              else {
                int[] pq = RSAKriptosustav.rastaviNNaPiQ(n);
                p = pq[0];
                q = pq[1];
              }
            }
            int d = PQDEBrojSifrat(2);
            if(d < 0) ispisGresaka(2, d);
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
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            provjeriIspravi();
          }
        });
  }

  private int probajN(boolean flagUmnozak) {
    int n = -1;
    try {
      n = Integer.parseInt(nBrojField.getText());
      if (n < 0) return -2;
      if (flagUmnozak && !ObradaUnosaRSA.provjeriNUmnozakProstih(n)) return -3;
    } catch (NumberFormatException ex) {
      return -1;
    }
    return n;
  }

  private int PQDEBrojSifrat(int kod) {
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
    String slovo = "";
    switch (kod) {
      case 0:
        slovo = "p";
        break;
      case 1:
        slovo = "q";
        break;
      case 2:
        slovo = "n";
        break;
      case 3:
        slovo = "d";
        break;
      case 4:
        slovo = "e";
        break;
    }
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
    int[] de = RSAKriptosustav.nadjiDiE(p, q);
    dBrojField.setText(String.valueOf(de[0]));
    eBrojField.setText(String.valueOf(de[1]));
    konzola.ispisiPoruku("Postavljeni su novi d i e.");
  }

  private void provjeriIspravi() {
    int p = -1, q = -1, n = -1, d = -1, e = -1;
    int[] de = {-1, -1}, pq = {-1, -1};
    p = PQDEBrojSifrat(0);
    q = PQDEBrojSifrat(1);
    if (p < 0 || q < 0) {
      ispisGresaka(0, p);
      ispisGresaka(1, q);
      n = probajN(true);
      if (n < 0) {
        ispisGresaka(2, n);
        return;
      } else {
        pq = RSAKriptosustav.rastaviNNaPiQ(n);
        p = pq[0];
        q = pq[1];
        pBrojField.setText(String.valueOf(pq[0]));
        qBrojField.setText(String.valueOf(pq[1]));
        konzola.ispisiPoruku("Postavljeni su novi p i q.");
      }
    } else {
      n = probajN(true);
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
      postaviDiE(p, q);
    } else if (e < 0) {
      if (ObradaUnosaRSA.provjeriD(p, q, d)) {
        e = RSAKriptosustav.nadjiDiliE(d, p, q);
        eBrojField.setText(String.valueOf(e));
        konzola.ispisiPoruku("Postavljam novi e.");
      } else {
        konzola.ispisiGresku("Za uneseni d ne postoji odgovarajući e.");
        postaviDiE(p, q);
      }
    } else if (d < 0) {
      if (ObradaUnosaRSA.provjeriD(p, q, e)) {
        d = RSAKriptosustav.nadjiDiliE(e, p, q);
        dBrojField.setText(String.valueOf(d));
        konzola.ispisiPoruku("Postavljam novi d.");
      } else {
        konzola.ispisiGresku("Za uneseni e ne postoji odgovarajući d.");
        postaviDiE(p, q);
      }
    } else {
      if (ObradaUnosaRSA.provjeriDiE(p, q, d, e))
        ;
      else {
        konzola.ispisiGresku(
            "Uneseni d i e nisu kongruentnis  jedan modulo Euler(n)! Ispravljam...");
        if (ObradaUnosaRSA.provjeriD(p, q, d)) {
          eBrojField.setText(String.valueOf(RSAKriptosustav.nadjiDiliE(d, p, q)));
        } else if (ObradaUnosaRSA.provjeriD(p, q, e)) {
          dBrojField.setText(String.valueOf(RSAKriptosustav.nadjiDiliE(e, p, q)));
        } else {
          postaviDiE(p, q);
        }
      }
    }
  }
}
