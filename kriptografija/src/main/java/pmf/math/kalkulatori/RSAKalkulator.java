package pmf.math.kalkulatori;

import pmf.math.algoritmi.TeorijaBrojeva;
import pmf.math.kriptosustavi.ElGamalKriptosustav;
import pmf.math.kriptosustavi.RSAKriptosustav;
import pmf.math.router.Konzola;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RSAKalkulator extends JDialog {
  public JPanel glavniPanel;
  private Konzola konzola;

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
  private JCheckBox brojNCheckBox;
  private JButton buttonOK;

  public RSAKalkulator(Konzola _konzola) {
    konzola = _konzola;

    sifrirajButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            int nB = Integer.MIN_VALUE;
            boolean koristimoN = false;
            if (brojNCheckBox.isSelected()) {
              try {
                nB = Integer.parseInt(nBrojField.getText());
                koristimoN = true;
              } catch (NumberFormatException ex) {
                konzola.ispisiGresku("Broj n nije ispravnog formata!");
              }
            }
            try {
              int pB = Integer.parseInt(pBrojField.getText());
              int qB = Integer.parseInt(qBrojField.getText());
              int dB = Integer.parseInt(dBrojField.getText());
              int broj = Integer.parseInt(otvoreniTekstArea.getText());
              if (provjereUnosa(true)) {
                if (koristimoN && nB != qB * pB)
                  konzola.ispisiGresku("Uneseni n nije umnožak unesenih p i q!");
                if (!koristimoN) {
                  if (qB * pB < 0) {
                    konzola.ispisiGresku("Uneseni brojevi su preveliki, pokušajte ponovno!");
                    return;
                  } else nBrojField.setText(String.valueOf(pB * qB));
                }
                RSAKriptosustav stroj = stvoriStroj();
                eBrojField.setText(String.valueOf(stroj.e));
                stroj.sifriraj(broj);
                sifratArea.setText(stroj.vratiSifrat());
              }
            } catch (NumberFormatException ex) {
              konzola.ispisiGresku("Unos ili nije broj ili je preveliki broj!");
            }
          }
        });
    desifrirajButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            int nB = Integer.MIN_VALUE;
            boolean koristimoN = false;
            if (brojNCheckBox.isSelected()) {
              try {
                nB = Integer.parseInt(nBrojField.getText());
                koristimoN = true;
              } catch (NumberFormatException ex) {
                konzola.ispisiGresku("Broj n nije ispravnog formata!");
              }
            }
            try {
              int pB = Integer.parseInt(pBrojField.getText());
              int qB = Integer.parseInt(qBrojField.getText());
              int dB = Integer.parseInt(dBrojField.getText());
              int sifra = Integer.parseInt(sifratArea.getText());
              if (provjereUnosa(true)) {
                if (koristimoN && nB != qB * pB)
                  konzola.ispisiGresku("Uneseni n nije umnožak unesenih p i q!");
                if (!koristimoN) {
                  System.out.println("q: " + qB + "  p: " + pB + "   q*b: " + qB*pB);
                  if (qB * pB < 0) {
                    konzola.ispisiGresku("Uneseni brojevi su preveliki, pokušajte ponovno!");
                    return;
                  } else nBrojField.setText(String.valueOf(pB * qB));
                }
                RSAKriptosustav stroj = stvoriStroj();
                eBrojField.setText(String.valueOf(stroj.e));
                stroj.postaviSifrat(sifra);
                otvoreniTekstArea.setText(String.valueOf(stroj.desifriraj()));
              }
            } catch (NumberFormatException ex) {
              konzola.ispisiGresku("Unos ili nije broj ili je preveliki broj!");
            }
          }
        });

    brojECheckBox.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            if (brojECheckBox.isSelected()) eBrojField.setEditable(true);
            else eBrojField.setEditable(false);
          }
        });

    brojNCheckBox.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            if (brojNCheckBox.isSelected()) {
              nBrojField.setEditable(true);
            } else {
              nBrojField.setEditable(false);
            }
          }
        });
  }

  public boolean provjereUnosa(boolean sifriranje) {
    int pB = Integer.parseInt(pBrojField.getText());
    int qB = Integer.parseInt(qBrojField.getText());
    int dB = Integer.parseInt(dBrojField.getText());
    boolean OK = true;
    if (!TeorijaBrojeva.prost(pB)) {
      OK = false;
      konzola.ispisiGresku("Broj p mora biti prost!");
    }
    if (!TeorijaBrojeva.prost(qB)) {
      OK = false;
      konzola.ispisiGresku("Broj q mora biti prost!");
    }
    if (brojECheckBox.isSelected()) {
      int eB = Integer.parseInt(eBrojField.getText());
      if (!RSAKriptosustav.provjeriDiE(pB, qB, pB * qB, dB, eB)) {
        OK = false;
        konzola.ispisiGresku("Umnožak brojeva d i e mora biti kongruentan s 1 modulo Euler(n)!");
      }
    } else {
      int eB = RSAKriptosustav.provjeriD(pB, qB, pB * qB, dB);
      if (eB == 0) {
        konzola.ispisiGresku("Za dani broj d ne postoji niti jedan odgovarajući broj e!");
        OK = false;
      }
    }
    return OK;
  }

  private RSAKriptosustav stvoriStroj() {
    RSAKriptosustav stroj;
    int pB = Integer.parseInt(pBrojField.getText());
    int qB = Integer.parseInt(qBrojField.getText());
    int dB = Integer.parseInt(dBrojField.getText());

    if (brojECheckBox.isSelected()) {
      int eB = Integer.parseInt(eBrojField.getText());
      stroj = new RSAKriptosustav(pB, qB, dB, eB);
    } else {
      stroj = new RSAKriptosustav(pB, qB, dB);
    }
    return stroj;
  }
}
