package pmf.math.kalkulatori;

import pmf.math.router.Konzola;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.Vector;

public class StupcanaTranspozicijaKalkulator {
    private JSpinner stupciSpinner;
    private JPanel spinnerlPanel;
    public JPanel glavniPanel;
    private JTextArea otvoreniTekstArea;
    private JTextArea sifratArea;
    private JButton button1;
    private JButton button2;

    private Konzola konzola;
    private Vector<JSpinner> spinnerVector;
    private GridLayout spinnerLayout;

    public StupcanaTranspozicijaKalkulator(Konzola _konzola) {
        konzola = _konzola;
        spinnerVector = new Vector<>();
        stupciSpinner.setValue(2);
        spinnerLayout = new GridLayout(2, 10, 2, 2);
        spinnerlPanel.setLayout(spinnerLayout);
        postaviTipke();
    }

    private void postaviTipke() {
    stupciSpinner.addChangeListener(
            e -> {
                if((int)stupciSpinner.getValue() < 2) stupciSpinner.setValue(2);
                if((int)stupciSpinner.getValue() > 20) stupciSpinner.setValue(20);
              int broj = (int) stupciSpinner.getValue();
              System.out.println(broj);

              for (JSpinner spin : spinnerVector) {
                  spinnerlPanel.remove(spin);
              }

              spinnerVector.clear();
              for (int i = 0; i < broj; i++) {
                  System.out.println("prolaz");
                JSpinner novi = new JSpinner(new SpinnerNumberModel(1, 1, broj, 1));
                postaviKljucSpinner(novi);
                novi.setMinimumSize(new Dimension(50, 10));
                spinnerVector.add(novi);
                spinnerlPanel.add(novi);
              }

              spinnerlPanel.revalidate();
              spinnerlPanel.repaint();
            });
    }

    private void postaviKljucSpinner(JSpinner spinner) {
        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                for (JSpinner spin : spinnerVector) {
                    System.out.print(spin.getValue() + " ");
                }
                System.out.println();
            }
        });
    }
}
