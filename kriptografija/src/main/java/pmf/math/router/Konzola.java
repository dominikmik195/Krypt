package pmf.math.router;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.time.LocalDateTime;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import lombok.Setter;

@Setter
public class Konzola {

  public JPanel glavniPanel;
  private JTextPane ispisKonzole;
  private JTextArea biljeskeTextArea;
  private JButton ocistiButton;
  private JSlider fontSlider;
  private JScrollPane ispisKonzolePane;
  private JPanel biljeskePanel;
  private String podnaslov = "";

  public Konzola() {
    postaviTipke();
    postaviRubove();
  }

  public void ispisiPoruku(String poruka){
    SimpleAttributeSet set = new SimpleAttributeSet();
    StyledDocument tekst = ispisKonzole.getStyledDocument();

    StyleConstants.setForeground(set, Color.WHITE);
    try { tekst.insertString(tekst.getLength(), dohvatiVrijeme() + " ", set); }
    catch (BadLocationException ignored) { }

    StyleConstants.setForeground(set, Color.BLUE);
    try { tekst.insertString(tekst.getLength(), podnaslov, set); }
    catch (BadLocationException ignored) { }

    StyleConstants.setForeground(set, Color.WHITE);
    try { tekst.insertString(tekst.getLength(), poruka + "\n", set); }
    catch (BadLocationException ignored) { }
    glavniPanel.revalidate();
  }

  public void ispisiGresku(String poruka) {
    SimpleAttributeSet set = new SimpleAttributeSet();
    StyledDocument tekst = ispisKonzole.getStyledDocument();

    StyleConstants.setForeground(set, Color.WHITE);
    try { tekst.insertString(tekst.getLength(), dohvatiVrijeme() + " ", set); }
    catch (BadLocationException ignored) { }

    StyleConstants.setForeground(set, Color.BLUE);
    try { tekst.insertString(tekst.getLength(), podnaslov, set); }
    catch (BadLocationException ignored) { }

    StyleConstants.setForeground(set, Color.RED);
    try { tekst.insertString(tekst.getLength(), poruka + "\n", set); }
    catch (BadLocationException ignored) { }
    glavniPanel.revalidate();
  }

  public String dohvatiVrijeme() {
    LocalDateTime dt = LocalDateTime.now();
    String hours = dt.getHour() < 10 ? "0" + dt.getHour() : Integer.toString(dt.getHour());
    String minutes = dt.getMinute() < 10 ? "0" + dt.getMinute() : Integer.toString(dt.getMinute());
    return hours + ":" + minutes + "";
  }

  public void postaviTipke() {
    // Očisti
    ocistiButton.addActionListener(e -> {
      biljeskeTextArea.setText("");
    });

    // Promijeni veličinu fonta
    fontSlider.addChangeListener(e -> {
      Font font = biljeskeTextArea.getFont();
      biljeskeTextArea.setFont(new Font(
          font.getName(), font.getStyle(),
          fontSlider.getValue()
      ));
    });

    fontSlider.setValue(18);
  }

  public void prikazi(boolean prikazi) {
    ispisKonzolePane.setVisible(prikazi);
    biljeskePanel.setVisible(prikazi);
  }

  public void postaviRubove() {
    biljeskeTextArea.setMargin(new Insets(10, 10, 10, 10));
  }
}
