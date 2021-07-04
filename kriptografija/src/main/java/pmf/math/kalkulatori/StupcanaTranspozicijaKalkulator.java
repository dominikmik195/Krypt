package pmf.math.kalkulatori;

import pmf.math.kriptosustavi.StupcanaTranspozicijaSustav;
import pmf.math.router.Konzola;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class StupcanaTranspozicijaKalkulator {
  private JSpinner stupciSpinner;
  private JPanel spinnerlPanel;
  public JPanel glavniPanel;
  private JTextArea otvoreniTekstArea;
  private JTextArea sifratArea;
  private JButton sifrirajButton;
  private JButton desifrirajButton;
  private JLabel kljucLabel;

  private Konzola konzola;
  private Vector<JSpinner> spinnerVector;
  private GridLayout spinnerLayout;

  private static final StupcanaTranspozicijaSustav stroj = new StupcanaTranspozicijaSustav();

  public StupcanaTranspozicijaKalkulator(Konzola _konzola) {
    konzola = _konzola;
    spinnerVector = new Vector<>();
    stupciSpinner.setValue(2);
    spinnerLayout = new GridLayout(2, 10, 2, 2);
    spinnerlPanel.setLayout(spinnerLayout);
    for (int i = 0; i < 2; i++) {
      JSpinner novi = new JSpinner(new SpinnerNumberModel(1, 1, 2, 1));
      postaviKljucSpinner(novi);
      novi.setMinimumSize(new Dimension(50, 10));
      spinnerVector.add(novi);
      spinnerlPanel.add(novi);
    }
    postaviTipke();
  }

  private void postaviTipke() {
    stupciSpinner.addChangeListener(
        e -> {
          int broj = (int) stupciSpinner.getValue();
          System.out.println("stupci... " + broj);
          Vector<Integer> tmpV = new Vector<>();
          for (JSpinner spin : spinnerVector) {
            if (tmpV.size() >= broj) break;
            tmpV.add((int) spin.getValue());
          }
          spinnerVector.clear();

          spinnerlPanel.removeAll(); // ************

          for (int i = 0; i < broj; i++) {
            JSpinner novi;
            if (i < tmpV.size()) {
                int stavi = tmpV.get(i);
                while(stavi > tmpV.size()) stavi--;
                novi = new JSpinner(new SpinnerNumberModel(stavi, 1, broj, 1));
            }
            else novi = new JSpinner(new SpinnerNumberModel(1, 1, broj, 1));
            postaviKljucSpinner(novi);
            novi.setMinimumSize(new Dimension(50, 10));
            spinnerVector.add(novi);
            spinnerlPanel.add(novi);
          }

          spinnerlPanel.revalidate();
          spinnerlPanel.repaint();

          tekstKljuca();
        });

    sifrirajButton.addActionListener(
        e -> {
          stroj.setBrojStupaca(4);
          Vector<Integer> v = new Vector<>();
          v.add(3);
          v.add(1);
          v.add(2);
          v.add(4);
          stroj.setVrijednosti(v);
          stroj.setOtvoreniTekst("NEKIDULJITEKSTICWA");
          stroj.sifriraj();
          System.out.println(stroj.getSifrat());
        });

    desifrirajButton.addActionListener(
        e -> {
          stroj.setBrojStupaca(4);
          Vector<Integer> v = new Vector<>();
          v.add(3);
          v.add(1);
          v.add(2);
          v.add(4);
          stroj.setVrijednosti(v);
          stroj.setSifrat("KLEIXNDISWEUTTAIJKCX");
          stroj.desifriraj();
          System.out.println(stroj.getOtvoreniTekst());
        });
  }

  private void postaviKljucSpinner(JSpinner spinner) {
    spinner.addChangeListener(e -> tekstKljuca());
  }

  private void tekstKljuca() {
    Vector<Integer> vrijednosti = new Vector<>();
    String popis = "";
    for (JSpinner spin : spinnerVector) {
      vrijednosti.add((int) spin.getValue());
      popis += spin.getValue() + "   ";
    }
    stroj.setVrijednosti(vrijednosti);
    kljucLabel.setText(popis);
    if (stroj.provjeriVrijednosti()) {
      kljucLabel.setForeground(new Color(0, 143, 0));
    } else {
      kljucLabel.setForeground(new Color(169, 0, 0));
    }
  }

  private void createUIComponents() {
    stupciSpinner = new JSpinner(new SpinnerNumberModel(2, 2, 20, 1));
  }
}
