package pmf.math.router;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

public class Opis {

  public JPanel glavniPanel;
  private JTextPane gornjiTextPane;
  private JTextPane srednjiTextPane;
  private JTextPane donjiTextPane;

  public void postaviTekst(String gornji, String srednji, String donji) {
    gornjiTextPane.setText(gornji);
    srednjiTextPane.setText(srednji);
    donjiTextPane.setText(donji);
  }
}
