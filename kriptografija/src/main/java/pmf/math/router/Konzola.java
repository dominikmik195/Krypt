package pmf.math.router;

import java.awt.Color;
import java.time.LocalDateTime;
import javax.swing.JPanel;
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
  private String podnaslov = "";

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
}
