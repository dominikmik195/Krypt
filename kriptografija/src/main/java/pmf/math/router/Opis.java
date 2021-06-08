package pmf.math.router;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

public class Opis {

  public JPanel glavniPanel;
  private JTextPane gornjiTextPane;
  private JTextPane donjiTextPane;
  private JPanel gornjiPanel;
  private JPanel donjiPanel;

  public void postaviTekst(String gornji, String donji) {
    gornjiTextPane.setText(gornji);
    donjiTextPane.setText(donji);
  }
}
