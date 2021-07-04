package pmf.math.kalkulatori;

import org.jfree.ui.HorizontalAlignment;
import pmf.math.baza.dao.StupcanaDAO;
import pmf.math.baza.tablice.StupcanaPovijest;
import pmf.math.kriptosustavi.StupcanaTranspozicijaSustav;
import pmf.math.obradaunosa.ObradaUnosa;
import pmf.math.router.Konzola;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.AbstractList;
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
  private JPanel povijestPanel;

  private final Konzola konzola;
  private final Vector<JSpinner> spinnerVector;
  private StupcanaDAO stupcanaDao = new StupcanaDAO();

  private static final StupcanaTranspozicijaSustav stroj = new StupcanaTranspozicijaSustav();

  public StupcanaTranspozicijaKalkulator(Konzola _konzola) {
    konzola = _konzola;
    spinnerVector = new Vector<>();
    stupciSpinner.setValue(2);
    GridLayout spinnerLayout = new GridLayout(2, 10, 2, 2);
    spinnerlPanel.setLayout(spinnerLayout);
    sifratArea.setWrapStyleWord(true);
    sifratArea.setLineWrap(true);
    otvoreniTekstArea.setWrapStyleWord(true);
    otvoreniTekstArea.setLineWrap(true);
    for (int i = 0; i < 2; i++) {
      JSpinner novi = new JSpinner(new SpinnerNumberModel(i + 1, 1, 2, 1));
      postaviKljucSpinner(novi);
      novi.setMinimumSize(new Dimension(50, 10));
      spinnerVector.add(novi);
      spinnerlPanel.add(novi);
    }
    povijestPanel.setLayout(new GridLayout(5, 1));
    postaviTipke();
    prikaziPovijest();
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
          spinnerlPanel.removeAll();

          for (int i = 0; i < broj; i++) {
            JSpinner novi;
            if (i < tmpV.size()) {
              int stavi = tmpV.get(i);
              while (stavi > tmpV.size()) stavi--;
              novi = new JSpinner(new SpinnerNumberModel(stavi, 1, broj, 1));
            } else novi = new JSpinner(new SpinnerNumberModel(1, 1, broj, 1));
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
        e ->
            new Thread(
                    () -> {
                      stroj.reinicijaliziraj();
                      String unos = otvoreniTekstArea.getText();
                      postaviVrijednosti();
                      if (!stroj.provjeriVrijednosti()) {
                        String tehnikalija;
                        int br = (int) stupciSpinner.getValue();
                        if (br < 5) tehnikalija = "različita broja";
                        else tehnikalija = "različitih brojeva";
                        stroj.setOK(false);
                        stroj.prosiriPoruku(
                            "Ključ mora sadržavati točno "
                                + br
                                + " "
                                + tehnikalija
                                + ", po jedan za svaki stupac.");
                      } else if (!ObradaUnosa.dobarUnosHrvEng(unos)) {
                        stroj.setOK(false);
                        stroj.prosiriPoruku(
                            "Unos smije sadržavati samo slova hrvatske ili engleske"
                                + " abecede te interpunkcijske znakove i praznine.");
                      } else {
                        unos = ObradaUnosa.ocistiStupcana(unos);
                        stroj.setOtvoreniTekst(unos);
                        stroj.sifriraj();
                      }

                      SwingUtilities.invokeLater(
                          () -> {
                            if (stroj.isOK()) {
                              otvoreniTekstArea.setText(stroj.formatirajOtvoreniTekst());
                              sifratArea.setText(stroj.getSifrat());
                              konzola.ispisiPoruku("Uspješno šifriranje stupčanom transpozicijom!");
                              stupcanaDao.ubaciElement(kljucLabel.getText());
                              prikaziPovijest();
                            } else {
                              konzola.ispisiGresku(stroj.getPoruke());
                            }
                          });
                    })
                .start());

    desifrirajButton.addActionListener(
        e ->
            new Thread(
                    () -> {
                      stroj.reinicijaliziraj();
                      String sifrat = sifratArea.getText();
                      postaviVrijednosti();
                      if (!stroj.provjeriVrijednosti()) {
                        String tehnikalija;
                        int br = (int) stupciSpinner.getValue();
                        if (br < 5) tehnikalija = "različita broja";
                        else tehnikalija = "različitih brojeva";
                        stroj.setOK(false);
                        stroj.prosiriPoruku(
                            "Ključ mora sadržavati točno "
                                + br
                                + " "
                                + tehnikalija
                                + ", po jedan za svaki stupac.");
                      } else if (!ObradaUnosa.dobarUnosHrvEng(sifrat)) {
                        stroj.setOK(false);
                        stroj.prosiriPoruku(
                            "Unos smije sadržavati samo slova hrvatske ili engleske"
                                + " abecede te interpunkcijske znakove i praznine.");
                      } else {
                        sifrat = ObradaUnosa.ocistiStupcana(sifrat);
                        stroj.setSifrat(sifrat);
                        stroj.desifriraj();
                      }

                      SwingUtilities.invokeLater(
                          () -> {
                            if (stroj.isOK()) {
                              otvoreniTekstArea.setText(stroj.formatirajOtvoreniTekst());
                              otvoreniTekstArea.setText(stroj.getOtvoreniTekst());
                              konzola.ispisiPoruku(
                                  "Uspješno dešifriranje stupčanom transpozicijom!");
                            } else {
                              konzola.ispisiGresku(stroj.getPoruke());
                            }
                          });
                    })
                .start());
  }

  private void postaviVrijednosti() {
    Vector<Integer> vrijednosti = new Vector<>();
    for (JSpinner spin : spinnerVector) {
      vrijednosti.add((int) spin.getValue());
    }
    stroj.setBrojStupaca(vrijednosti.size());
    stroj.setVrijednosti(vrijednosti);
  }

  private void postaviKljucSpinner(JSpinner spinner) {
    spinner.addChangeListener(e -> tekstKljuca());
  }

  private void tekstKljuca() {
    Vector<Integer> vrijednosti = new Vector<>();
    StringBuilder popis = new StringBuilder();
    for (JSpinner spin : spinnerVector) {
      vrijednosti.add((int) spin.getValue());
      popis.append(spin.getValue()).append("   ");
    }
    stroj.setVrijednosti(vrijednosti);
    kljucLabel.setText(popis.toString());
    if (stroj.provjeriVrijednosti()) {
      kljucLabel.setForeground(new Color(0, 143, 0));
    } else {
      kljucLabel.setForeground(new Color(169, 0, 0));
    }
  }

  private void createUIComponents() {
    stupciSpinner = new JSpinner(new SpinnerNumberModel(2, 2, 20, 1));
  }

  private void prikaziPovijest() {
      povijestPanel.removeAll();
      AbstractList<StupcanaPovijest> pov = (AbstractList<StupcanaPovijest>) stupcanaDao.dohvatiElemente();
      for (StupcanaPovijest p : pov) {
          JButton novi = new JButton(p.getKljuc());
          novi.setHorizontalAlignment(JButton.CENTER);
          postaviTipkuZaPovijest(novi);
          povijestPanel.add(novi);
      }
  }

  private void postaviTipkuZaPovijest(JButton tipka) {
      tipka.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              String kljuc = tipka.getText();
              kljucLabel.setText(kljuc);
              String[] tmpS = kljuc.split("   ");
              int velicina = tmpS.length;
              stupciSpinner.setValue(velicina);
              spinnerlPanel.removeAll();
              spinnerVector.clear();
              for (int i = 0; i < velicina; i++) {
                  JSpinner tmpJ = new JSpinner(new SpinnerNumberModel(Integer.parseInt(tmpS[i]),
                          1, velicina, 1));
                  postaviKljucSpinner(tmpJ);
                  tmpJ.setMinimumSize(new Dimension(50, 10));
                  spinnerVector.add(tmpJ);
                  spinnerlPanel.add(tmpJ);
              }
              tekstKljuca();
          }
      });
  }
}
