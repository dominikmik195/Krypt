package pmf.math.kalkulatori;

import pmf.math.algoritmi.TeorijaBrojeva;
import pmf.math.kriptosustavi.ElGamalKriptosustav;
import pmf.math.router.Konzola;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ElGamalKalkulator {

  public JPanel glavniPanel;
  public Konzola konzola;

  private JLabel prostBrojLabel;
  private JLabel tajniKljucLabel;
  private JCheckBox alfaCheckBox;
  private JCheckBox betaCheckBox;
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

  public ElGamalKalkulator(Konzola _konzola) {
    konzola = _konzola;
    sifrirajButton.addActionListener(
            new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                try {
                  int pB = Integer.parseInt(prostBrojField.getText());
                  int tK = Integer.parseInt(tajniKljucField.getText());
                  int tB = Integer.parseInt(tajniBrojField.getText());
                  int broj = Integer.parseInt(otvoreniTekstArea.getText());
                  if (provjereUnosa()) {
                    ElGamalKriptosustav stroj = stvoriStroj();
                    stroj.sifriraj(broj, tB);
                    sifratArea.setText(stroj.vratiSifrat());
                  }
                }
                catch (NumberFormatException ex) {
                  konzola.ispisiGresku("Unos mora biti broj!");
                }
              }
            });

    desifrirajButton.addActionListener(
            new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                try {
                  int pB = Integer.parseInt(prostBrojField.getText());
                  int tK = Integer.parseInt(tajniKljucField.getText());

                  String šifra = sifratArea.getText().strip();
                  boolean OK = provjeriSifru(šifra);
                  if(!OK) {
                    konzola.ispisiGresku("Krivi format šifre!");
                    return;
                  }
                  if (provjereUnosa()) {
                    ElGamalKriptosustav stroj = stvoriStroj();
                    stroj.pohraniSifrat(šifra);
                    otvoreniTekstArea.setText(String.valueOf(stroj.desifriraj()));
                  }
                }
                catch(StringIndexOutOfBoundsException | NumberFormatException ex) {
                  konzola.ispisiGresku("Krivi format šifre!");
                }
              }
            });
  }

  private boolean provjereUnosa() {
    int pB = Integer.parseInt(prostBrojField.getText());
    int tK = Integer.parseInt(tajniKljucField.getText());
    int tB = Integer.parseInt(tajniBrojField.getText());
    boolean OK = true;
    if (!TeorijaBrojeva.prost(pB)) {
      konzola.ispisiGresku("U prvom polju treba biti prost broj!");
      OK = false;
    } else {
      if (alfaCheckBox.isSelected()) {
        int _alfa = Integer.parseInt(alfaField.getText());
        if (!ElGamalKriptosustav.provjeriAlfa(_alfa, pB)) {
          konzola.ispisiGresku("Alfa je pogrešno postavljena!");
          OK = false;
        }
        if (betaCheckBox.isSelected() && OK) {
          int _beta = Integer.parseInt(betaField.getText());
          if (!ElGamalKriptosustav.provjeriBeta(_alfa, _beta, pB, tK)) {
            konzola.ispisiGresku("Beta je pogrešno postavljen!");
            OK = false;
          }
        }
      }
    }
    return OK;
  }

  private ElGamalKriptosustav stvoriStroj() {
    int pB = Integer.parseInt(prostBrojField.getText());
    int tK = Integer.parseInt(tajniKljucField.getText());
    ElGamalKriptosustav stroj;
    if (alfaCheckBox.isSelected()) {
      int _alfa = Integer.parseInt(alfaField.getText());
      if (betaCheckBox.isSelected()) {
        int _beta = Integer.parseInt(betaField.getText());
        stroj = new ElGamalKriptosustav(pB, tK, _alfa, _beta);
      }
      else {
        stroj = new ElGamalKriptosustav(pB, tK, _alfa);
      }
    }
    else stroj = new ElGamalKriptosustav(pB, tK);
    return stroj;
  }

  private boolean provjeriSifru(String šifra) {
    if(šifra.charAt(0) != '(' || šifra.charAt(šifra.length()-1) != ')') return false;
    String novi = šifra.substring(1, šifra.length() - 1);
    String[] lista = novi.split(",");
    if (lista.length != 2) return false;
    return true;
  }
}
