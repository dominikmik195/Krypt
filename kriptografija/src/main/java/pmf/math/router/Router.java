package pmf.math.router;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import pmf.math.baza.BazaPodataka;
import pmf.math.kalkulatori.AnalizaTekstaKalkulator;
import pmf.math.kalkulatori.CezarKalkulator;
import pmf.math.kalkulatori.ElGamalKalkulator;
import pmf.math.kalkulatori.HillKalkulator;
import pmf.math.kalkulatori.RSAKalkulator;
import pmf.math.kalkulatori.PlayfairKalkulator;
import pmf.math.kalkulatori.SupstitucijskaKalkulator;
import pmf.math.kalkulatori.VigenereKalkulator;
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
  private JButton analizaTekstaButton;

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
  private final AnalizaTekstaKalkulator analizaTekstaKalkulator =
      new AnalizaTekstaKalkulator(konzola);

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
    //srednjiStupac.add(ImenaKalkulatora.AFINA_SIFRA.toString(), );
    srednjiStupac.add(ImenaKalkulatora.HILLOVA_SIFRA.toString(), hillKalkulator.glavniPanel);
    srednjiStupac.add(ImenaKalkulatora.VIGENEROVA_SIFRA.toString(), vigenereKalkulator.glavniPanel);
    srednjiStupac.add(
        ImenaKalkulatora.PLAYFAIROVA_SIFRA.toString(), playfairKalkulator.glavniPanel);
    //srednjiStupac.add(ImenaKalkulatora.STUPCANA_TRANSPOZICIJA.toString(), );
    srednjiStupac.add(ImenaKalkulatora.RSA_SIFRA.toString(), RSAkalkulator.glavniPanel);
    srednjiStupac.add(ImenaKalkulatora.EL_GAMALOVA_SIFRA.toString(), elGamalKalkulator.glavniPanel);
    srednjiStupac.add(ImenaKalkulatora.ANALIZA_TEKSTA.toString(), analizaTekstaKalkulator.glavniPanel);

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

      case ANALIZA_TEKSTA -> {
        prikaz.show(srednjiStupac, ImenaKalkulatora.ANALIZA_TEKSTA.toString());
        opis.postaviTekst(OpisiKalkulatora.ANALIZA_TEKSTA_OPIS, UputeKalkulatora.ANALIZA_TEKSTA_UPUTE);
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
    omoguciTipku(cezarButton, true);
    omoguciTipku(supstitucijskaButton, true);
    omoguciTipku(afinaButton, true);
    omoguciTipku(hillovaButton, true);
    omoguciTipku(vigenerovaButton, true);
    omoguciTipku(playfairovaButton, true);
    omoguciTipku(stupcanaButton, true);
    omoguciTipku(rsaButton, true);
    omoguciTipku(elgamalButton, true);
    omoguciTipku(analizaTekstaButton, true);
  }

  private void omoguciTipku(JButton button, boolean omoguci) {
    button.setEnabled(omoguci);
    button.setFocusable(omoguci);
    button.setOpaque(omoguci);
    if(omoguci) {
      button.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }
    else {
      button.setBorder(BorderFactory.createEmptyBorder());
    }
  }

  private void postaviTipke() {
    cezarButton.addActionListener(e -> {
      omoguciSveTipke();
      omoguciTipku(cezarButton, false);
      postaviKalkulator(ImenaKalkulatora.CEZAROVA_SIFRA);
    });
    supstitucijskaButton.addActionListener(e -> {
      omoguciSveTipke();
      omoguciTipku(supstitucijskaButton, false);
      postaviKalkulator(ImenaKalkulatora.SUPSTITUCIJSKA_SIFRA);
    });
    afinaButton.addActionListener(e -> {
      omoguciSveTipke();
      omoguciTipku(afinaButton, false);
      postaviKalkulator(ImenaKalkulatora.AFINA_SIFRA);
    });
    hillovaButton.addActionListener(e -> {
      omoguciSveTipke();
      omoguciTipku(hillovaButton, false);
      postaviKalkulator(ImenaKalkulatora.HILLOVA_SIFRA);
    });
    vigenerovaButton.addActionListener(e -> {
      omoguciSveTipke();
      omoguciTipku(vigenerovaButton, false);
      postaviKalkulator(ImenaKalkulatora.VIGENEROVA_SIFRA);
    });
    playfairovaButton.addActionListener(e -> {
      omoguciSveTipke();
      omoguciTipku(playfairovaButton, false);
      postaviKalkulator(ImenaKalkulatora.PLAYFAIROVA_SIFRA);
    });
    stupcanaButton.addActionListener(e -> {
      omoguciSveTipke();
      omoguciTipku(stupcanaButton, false);
      postaviKalkulator(ImenaKalkulatora.STUPCANA_TRANSPOZICIJA);
    });
    rsaButton.addActionListener(e -> {
      omoguciSveTipke();
      omoguciTipku(rsaButton, false);
      postaviKalkulator(ImenaKalkulatora.RSA_SIFRA);
    });
    elgamalButton.addActionListener(e -> {
      omoguciSveTipke();
      omoguciTipku(elgamalButton, false);
      postaviKalkulator(ImenaKalkulatora.EL_GAMALOVA_SIFRA);
    });
    analizaTekstaButton.addActionListener(e -> {
      omoguciSveTipke();
      omoguciTipku(analizaTekstaButton, false);
      postaviKalkulator(ImenaKalkulatora.ANALIZA_TEKSTA);
    });
  }

  public void actionPerformed(ActionEvent ae) {
    String choice = ae.getActionCommand();
    if (choice.equals("Quit")) {
      System.exit(0);
    }
  }
}
