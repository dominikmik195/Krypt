package pmf.math.router;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import pmf.math.baza.BazaPodataka;
import pmf.math.kalkulatori.*;
import pmf.math.konstante.ImenaKalkulatora;
import pmf.math.konstante.OpisiKalkulatora;
import pmf.math.konstante.UputeKalkulatora;

public class Router extends JPanel implements ActionListener {

  private static JFrame myFrame;

  private JButton cezarButton;
  private JButton afinaButton;
  private JButton supstitucijskaButton;
  private JButton hillovaButton;
  private JButton vigenerovaButton;
  private JButton playfairovaButton;
  private JButton stupcanaButton;
  private JButton rsaButton;
  private JButton elgamalButton;
  private JPanel glavniPanel;
  private JPanel tipkePanel;
  private JPanel formPanel;
  private JPanel lijeviStupac;
  private JPanel desniStupac;
  private JPanel srednjiStupac;

  private final BazaPodataka bazaPodataka = new BazaPodataka();
  private final Konzola konzola = new Konzola();
  private final Opis opis = new Opis();
  private final ElGamalKalkulator elGamalKalkulator = new ElGamalKalkulator(konzola);
  private final RSAKalkulator RSAkalkulator = new RSAKalkulator(konzola);
  private final PlayfairKalkulator playfairKalkulator = new PlayfairKalkulator(konzola);
  private final VigenereKalkulator vigenereKalkulator = new VigenereKalkulator(konzola);
  private final HillKalkulator hillKalkulator = new HillKalkulator(konzola);
  private final CezarKalkulator cezarKalkulator = new CezarKalkulator(konzola);
  private final SupstitucijskaKalkulator supstitucijskaKalkulator =
      new SupstitucijskaKalkulator(konzola);
  private final AfinaKalkulator afinaKalkulator = new AfinaKalkulator(konzola);

  public void Main() {
    SwingUtilities.invokeLater(this::stvoriGUI);
  }

  private void postaviRouter() {
    postaviOpis();
    postaviKalkulator();
    postaviKonzolu();
    postaviTipke();
  }

  private void stvoriGUI() {
    myFrame = new JFrame("Krypt");
    myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    postaviRouter();
    myFrame.add(glavniPanel);
    myFrame.pack();
    myFrame.setLocationRelativeTo(null);
    myFrame.setResizable(false);
    myFrame.setVisible(true);

    konzola.ispisiPoruku("Uspješno pokrenuta aplikacija!");
    konzola.ispisiGresku("Ovako izgleda ispis greške...");
  }

  private void postaviOpis() {
    lijeviStupac.add(opis.glavniPanel);
  }

  private void postaviKalkulator() {
    srednjiStupac.add("NULL", new JPanel());

    srednjiStupac.add(ImenaKalkulatora.CEZAROVA_SIFRA.toString(), cezarKalkulator.kalkulatorPanel);
    srednjiStupac.add(
        ImenaKalkulatora.SUPSTITUCIJSKA_SIFRA.toString(), supstitucijskaKalkulator.glavniPanel);
    srednjiStupac.add(ImenaKalkulatora.AFINA_SIFRA.toString(), afinaKalkulator.glavniPanel);
    srednjiStupac.add(ImenaKalkulatora.HILLOVA_SIFRA.toString(), hillKalkulator.glavniPanel);
    srednjiStupac.add(ImenaKalkulatora.VIGENEROVA_SIFRA.toString(), vigenereKalkulator.glavniPanel);
    srednjiStupac.add(
        ImenaKalkulatora.PLAYFAIROVA_SIFRA.toString(), playfairKalkulator.glavniPanel);
    //srednjiStupac.add(ImenaKalkulatora.STUPCANA_TRANSPOZICIJA.toString(), );
    srednjiStupac.add(ImenaKalkulatora.RSA_SIFRA.toString(), RSAkalkulator.glavniPanel);
    srednjiStupac.add(ImenaKalkulatora.EL_GAMALOVA_SIFRA.toString(), elGamalKalkulator.glavniPanel);

    cezarButton.doClick();
  }

  private void postaviKalkulator(ImenaKalkulatora imeKalkulatora) {
    CardLayout prikaz = (CardLayout) srednjiStupac.getLayout();
    switch (imeKalkulatora) {
      case HILLOVA_SIFRA -> {
        prikaz.show(srednjiStupac, ImenaKalkulatora.HILLOVA_SIFRA.toString());
        opis.postaviTekst(OpisiKalkulatora.HILL_OPIS, UputeKalkulatora.HILL_UPUTE);
      }

      case VIGENEROVA_SIFRA -> {
        prikaz.show(srednjiStupac, ImenaKalkulatora.VIGENEROVA_SIFRA.toString());
        opis.postaviTekst(OpisiKalkulatora.VIGENERE_OPIS, UputeKalkulatora.VIGENERE_UPUTE);
      }

      case PLAYFAIROVA_SIFRA -> {
        prikaz.show(srednjiStupac, ImenaKalkulatora.PLAYFAIROVA_SIFRA.toString());
        opis.postaviTekst(OpisiKalkulatora.PLAYFAIR_OPIS, UputeKalkulatora.PLAYFAIR_UPUTE);
      }

      case EL_GAMALOVA_SIFRA -> {
        prikaz.show(srednjiStupac, ImenaKalkulatora.EL_GAMALOVA_SIFRA.toString());
        opis.postaviTekst(OpisiKalkulatora.EL_GAMAL_OPIS, UputeKalkulatora.EL_GAMAL_UPUTE);
      }

      case RSA_SIFRA -> {
        prikaz.show(srednjiStupac, ImenaKalkulatora.RSA_SIFRA.toString());
        opis.postaviTekst(OpisiKalkulatora.RSA_OPIS, UputeKalkulatora.RSA_UPUTE);
      }

      case SUPSTITUCIJSKA_SIFRA -> {
        prikaz.show(srednjiStupac, ImenaKalkulatora.SUPSTITUCIJSKA_SIFRA.toString());
        opis.postaviTekst(OpisiKalkulatora.SUPSTITUCIJA_OPIS, UputeKalkulatora.SUPSTITUCIJA_UPUTE);
      }

      case CEZAROVA_SIFRA -> {
        prikaz.show(srednjiStupac, ImenaKalkulatora.CEZAROVA_SIFRA.toString());
        opis.postaviTekst(OpisiKalkulatora.CEZAR_OPIS, UputeKalkulatora.CEZAR_UPUTE);
      }

      case AFINA_SIFRA -> {
        prikaz.show(srednjiStupac, ImenaKalkulatora.AFINA_SIFRA.toString());
        opis.postaviTekst(OpisiKalkulatora.AFINA_OPIS, UputeKalkulatora.AFINA_UPUTE);
      }

      default -> {
        prikaz.show(srednjiStupac, "NULL");
        opis.postaviTekst("", "");
      }
    }
    myFrame.revalidate();
  }

  private void postaviKonzolu() {
    desniStupac.add(konzola.glavniPanel);
  }

  private void omoguciSveTipke() {
    omoguciTipku(cezarButton);
    omoguciTipku(supstitucijskaButton);
    omoguciTipku(afinaButton);
    omoguciTipku(hillovaButton);
    omoguciTipku(vigenerovaButton);
    omoguciTipku(playfairovaButton);
    omoguciTipku(stupcanaButton);
    omoguciTipku(rsaButton);
    omoguciTipku(elgamalButton);
  }

  private void omoguciTipku(JButton button) {
    button.setEnabled(true);
    button.setFocusable(true);
  }

  private void postaviTipke() {
    cezarButton.addActionListener(e -> {
      omoguciSveTipke();
      cezarButton.setEnabled(false);
      cezarButton.setFocusable(false);
      postaviKalkulator(ImenaKalkulatora.CEZAROVA_SIFRA);
    });
    supstitucijskaButton.addActionListener(e -> {
      omoguciSveTipke();
      supstitucijskaButton.setEnabled(false);
      supstitucijskaButton.setFocusable(false);
      postaviKalkulator(ImenaKalkulatora.SUPSTITUCIJSKA_SIFRA);
    });
    afinaButton.addActionListener(e -> {
      omoguciSveTipke();
      afinaButton.setEnabled(false);
      afinaButton.setFocusable(false);
      postaviKalkulator(ImenaKalkulatora.AFINA_SIFRA);
    });
    hillovaButton.addActionListener(e -> {
      omoguciSveTipke();
      hillovaButton.setEnabled(false);
      hillovaButton.setFocusable(false);
      postaviKalkulator(ImenaKalkulatora.HILLOVA_SIFRA);
    });
    vigenerovaButton.addActionListener(e -> {
      omoguciSveTipke();
      vigenerovaButton.setEnabled(false);
      vigenerovaButton.setFocusable(false);
      postaviKalkulator(ImenaKalkulatora.VIGENEROVA_SIFRA);
    });
    playfairovaButton.addActionListener(e -> {
      omoguciSveTipke();
      playfairovaButton.setEnabled(false);
      playfairovaButton.setFocusable(false);
      postaviKalkulator(ImenaKalkulatora.PLAYFAIROVA_SIFRA);
    });
    stupcanaButton.addActionListener(e -> {
      omoguciSveTipke();
      stupcanaButton.setEnabled(false);
      stupcanaButton.setFocusable(false);
      postaviKalkulator(ImenaKalkulatora.STUPCANA_TRANSPOZICIJA);
    });
    rsaButton.addActionListener(e -> {
      omoguciSveTipke();
      rsaButton.setEnabled(false);
      rsaButton.setFocusable(false);
      postaviKalkulator(ImenaKalkulatora.RSA_SIFRA);
    });
    elgamalButton.addActionListener(e -> {
      omoguciSveTipke();
      elgamalButton.setEnabled(false);
      elgamalButton.setFocusable(false);
      postaviKalkulator(ImenaKalkulatora.EL_GAMALOVA_SIFRA);
    });
  }

  public void actionPerformed(ActionEvent ae) {
    String choice = ae.getActionCommand();
    if (choice.equals("Quit")) {
      System.exit(0);
    }
  }
}
