package pmf.math.kalkulatori;

import pmf.math.algoritmi.Abeceda;
import pmf.math.obradaunosa.ObradaUnosa;
import pmf.math.obradaunosa.ObradaUnosaCezar;
import pmf.math.obradaunosa.ObradaUnosaCezarKljucnaRijec;
import pmf.math.obradaunosa.ObradaUnosaSupstitucijska;
import pmf.math.router.Konzola;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;

public class SupstitucijskaKalkulator {
  public JPanel glavniPanel;

  private JTextArea otvoreniTekstArea;
  private JButton sifrirajButton;
  private JButton desifrirajButton;
  private JTextArea sifratArea;
  private JFormattedTextField textField0;
  private JFormattedTextField textField1;
  private JFormattedTextField textField2;
  private JFormattedTextField textField3;
  private JFormattedTextField textField4;
  private JFormattedTextField textField5;
  private JFormattedTextField textField6;
  private JFormattedTextField textField7;
  private JFormattedTextField textField8;
  private JFormattedTextField textField9;
  private JFormattedTextField textField10;
  private JFormattedTextField textField11;
  private JFormattedTextField textField12;
  private JFormattedTextField textField13;
  private JFormattedTextField textField14;
  private JFormattedTextField textField15;
  private JFormattedTextField textField16;
  private JFormattedTextField textField17;
  private JFormattedTextField textField18;
  private JFormattedTextField textField19;
  private JFormattedTextField textField20;
  private JFormattedTextField textField21;
  private JFormattedTextField textField22;
  private JFormattedTextField textField23;
  private JFormattedTextField textField24;
  private JFormattedTextField textField25;
  private JButton ocistiButton;

  private JFormattedTextField[] textFields = new JFormattedTextField[26];

  private final Konzola konzola; // Za ispis greške.

  public SupstitucijskaKalkulator(Konzola konzola) {
    this.konzola = konzola;
    napuniTextFields();
    postaviTextFields();

    sifrirajButton.addActionListener(new Sifriraj());
    desifrirajButton.addActionListener(new Desifriraj());

    /*textField0.addPropertyChangeListener(
    new PropertyChangeListener() {
      @Override
      public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("Value changed " + evt.getNewValue());
        char newChar = evt.getNewValue().toString().charAt(0);
        if (Character.isLowerCase(newChar))
          textField0.setValue(Character.toUpperCase(newChar));
        // textField0.setText(new StringBuilder(Character.toUpperCase(newChar)).toString());
      }
    });*/

    // TODO: Napisati ovo malo ljepše.
    ocistiButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < 26; i++) {
              textFields[i].setValue(null);
            }
          }
        });
  }

  // -----------------------------------------------------------------------------------------------------------------
  // Listeneri.
  private class Sifriraj implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      String otvoreniTekst = otvoreniTekstArea.getText();
      int[] permutacija = dohvatiTextFields(); // Permutacija kojom šifriramo.

      // Provjeri unose (zbog ispisa poruka).
      boolean greska = false;
      if (ObradaUnosaSupstitucijska.kriviUnos(permutacija)) {
        konzola.ispisiGresku("Niti jedna dva slova se ne smiju preslikati u isto!");
        greska = true;
      }
      if (ObradaUnosa.kriviUnos(otvoreniTekst)) {
        konzola.ispisiGresku("Otvoreni tekst smije sadržavati samo slova engleske abecede!");
        greska = true;
      }

      if (!greska) {
        String sifrat = ObradaUnosaSupstitucijska.sifriraj(permutacija, otvoreniTekst);
        sifratArea.setText(sifrat);
        konzola.ispisiPoruku("Poruka uspješno šifrirana općom supstitucijskom šifrom.");
      }
    }
  }

  private class Desifriraj implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      String sifrat = sifratArea.getText();
      int[] permutacija = dohvatiTextFields(); // Permutacija kojom šifriramo.

      // Provjeri unose (zbog ispisa poruka).
      boolean greska = false;
      if (ObradaUnosaSupstitucijska.kriviUnos(permutacija)) {
        konzola.ispisiGresku("Niti jedna dva slova se ne smiju preslikati u isto!");
        greska = true;
      }
      if (ObradaUnosa.kriviUnos(sifrat)) {
        konzola.ispisiGresku("Šifrat smije sadržavati samo slova engleske abecede!");
        greska = true;
      }

      if (!greska) {
        String otvoreniTekst = ObradaUnosaSupstitucijska.desifriraj(permutacija, sifrat);
        otvoreniTekstArea.setText(otvoreniTekst);
        konzola.ispisiPoruku("Poruka uspješno dešifrirana Cezarovom šifrom.");
      }
    }
  }

  private int[] dohvatiTextFields() {
    int[] vrijednosti = new int[26];

    for (int i = 0; i < 26; i++) vrijednosti[i] = Abeceda.uBroj(textFields[i].getText().charAt(0));

    return vrijednosti;
  }

  private void postaviTextFields() {
    try {
      // Jedno uppercase slovo.
      // FIXME: Trenutno prihvaća znakove Č, Ć itd.
      MaskFormatter mask = new MaskFormatter("*");
      mask.setPlaceholderCharacter('_');
      mask.setValidCharacters("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_");
      DefaultFormatterFactory factory = new DefaultFormatterFactory(mask);
      for (int i = 0; i < 26; i++) {
        textFields[i].setFormatterFactory(factory);
        textFields[i].setValue(Abeceda.uSlovo(i));
      }
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  // FIXME: Za testiranje.
  public static void main(String[] args) {
    Konzola konzola = new Konzola();
    JFrame frame = new JFrame("SupstitucijskaKalkulator");
    frame.setContentPane(new SupstitucijskaKalkulator(konzola).glavniPanel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }

  private void napuniTextFields() {
    textFields[0] = textField0;
    textFields[1] = textField1;
    textFields[2] = textField2;
    textFields[3] = textField3;
    textFields[4] = textField4;
    textFields[5] = textField5;
    textFields[6] = textField6;
    textFields[7] = textField7;
    textFields[8] = textField8;
    textFields[9] = textField9;
    textFields[10] = textField10;
    textFields[11] = textField11;
    textFields[12] = textField12;
    textFields[13] = textField13;
    textFields[14] = textField14;
    textFields[15] = textField15;
    textFields[16] = textField16;
    textFields[17] = textField17;
    textFields[18] = textField18;
    textFields[19] = textField19;
    textFields[20] = textField20;
    textFields[21] = textField21;
    textFields[22] = textField22;
    textFields[23] = textField23;
    textFields[24] = textField24;
    textFields[25] = textField25;
  }
}
