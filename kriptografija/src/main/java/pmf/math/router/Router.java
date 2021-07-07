package pmf.math.router;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import pmf.math.baza.BazaPodataka;
import pmf.math.kalkulatori.*;
import pmf.math.konstante.ImenaKalkulatora;
import pmf.math.konstante.OpisiKalkulatora;
import pmf.math.konstante.PodnasloviKonzole;
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
  private JButton uvodnaStranicaButton;

  private final BazaPodataka baza = new BazaPodataka();
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
  private final AnalizaTekstaKalkulator analizaTekstaKalkulator =
      new AnalizaTekstaKalkulator(konzola);
  private final StupcanaTranspozicijaKalkulator stupcanaKalkulator =
          new StupcanaTranspozicijaKalkulator(konzola);
  private final UvodnaStranica uvodnaStranica = new UvodnaStranica();

  private final int sirinaOpisa = 420;
  private final int sirinaKonzole = 330;

  public void Main() {
    stvoriGUI();
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
    try {
      myFrame.setIconImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/logo.png"))));
    } catch (IOException e) {
      e.printStackTrace();
    }

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
    srednjiStupac.add(ImenaKalkulatora.STUPCANA_TRANSPOZICIJA.toString(),stupcanaKalkulator.glavniPanel );
    srednjiStupac.add(ImenaKalkulatora.RSA_SIFRA.toString(), RSAkalkulator.glavniPanel);
    srednjiStupac.add(ImenaKalkulatora.EL_GAMALOVA_SIFRA.toString(), elGamalKalkulator.glavniPanel);
    srednjiStupac.add(ImenaKalkulatora.ANALIZA_TEKSTA.toString(), analizaTekstaKalkulator.glavniPanel);
    srednjiStupac.add(ImenaKalkulatora.UVODNA_STRANICA.toString(), uvodnaStranica.glavniPanel);
  }

  private void postaviKalkulator(ImenaKalkulatora imeKalkulatora) {
    CardLayout prikaz = (CardLayout) srednjiStupac.getLayout();
    if(imeKalkulatora == ImenaKalkulatora.UVODNA_STRANICA) {
      konzola.prikazi(false);
      desniStupac.setPreferredSize(new Dimension(0, 0));
      opis.prikazi(false);
      lijeviStupac.setPreferredSize(new Dimension(0,0));
    }
    else {
      konzola.prikazi(true);
      desniStupac.setPreferredSize(new Dimension(330, 0));
      opis.prikazi(true);
      lijeviStupac.setPreferredSize(new Dimension(420,0));
    }
    switch (imeKalkulatora) {
      case HILLOVA_SIFRA -> {
        konzola.setPodnaslov(PodnasloviKonzole.HILLOVA_SIFRA_PODNASLOV);
        prikaz.show(srednjiStupac, ImenaKalkulatora.HILLOVA_SIFRA.toString());
        opis.postaviTekst(OpisiKalkulatora.HILL_OPIS,
            UputeKalkulatora.HILL_UPUTE,
            imeKalkulatora);
        new Thread(() -> opis.postaviGraf(false)).start();
      }

      case VIGENEROVA_SIFRA -> {
        prikaz.show(srednjiStupac, ImenaKalkulatora.VIGENEROVA_SIFRA.toString());
        konzola.setPodnaslov(PodnasloviKonzole.VIGENEREOVA_SIFRA_PODNASLOV);
        opis.postaviTekst(OpisiKalkulatora.VIGENERE_OPIS,
            UputeKalkulatora.VIGENERE_UPUTE,
            imeKalkulatora);
        new Thread(() -> opis.postaviGraf(false)).start();
      }

      case PLAYFAIROVA_SIFRA -> {
        konzola.setPodnaslov(PodnasloviKonzole.PLAYFAIROVA_SIFRA_PODNASLOV);
        prikaz.show(srednjiStupac, ImenaKalkulatora.PLAYFAIROVA_SIFRA.toString());
        opis.postaviTekst(OpisiKalkulatora.PLAYFAIR_OPIS,
            UputeKalkulatora.PLAYFAIR_UPUTE,
            imeKalkulatora);
        new Thread(() -> opis.postaviGraf(false)).start();
      }

      case EL_GAMALOVA_SIFRA -> {
        konzola.setPodnaslov(PodnasloviKonzole.EL_GAMALOVA_SIFRA_PODNASLOV);
        prikaz.show(srednjiStupac, ImenaKalkulatora.EL_GAMALOVA_SIFRA.toString());
        opis.postaviTekst(OpisiKalkulatora.EL_GAMAL_OPIS,
            UputeKalkulatora.EL_GAMAL_UPUTE,
            imeKalkulatora);
        new Thread(()->opis.postaviGraf(false)).start();
      }

      case RSA_SIFRA -> {
        konzola.setPodnaslov(PodnasloviKonzole.RSA_SIFRA_PODNASLOV);
        prikaz.show(srednjiStupac, ImenaKalkulatora.RSA_SIFRA.toString());
        opis.postaviTekst(OpisiKalkulatora.RSA_OPIS,
            UputeKalkulatora.RSA_UPUTE,
            imeKalkulatora);
        new Thread(()->opis.postaviGraf(false)).start();
      }

      case SUPSTITUCIJSKA_SIFRA -> {
        konzola.setPodnaslov(PodnasloviKonzole.SUPSTITUCIJSKA_SIFRA_PODNASLOV);
        prikaz.show(srednjiStupac, ImenaKalkulatora.SUPSTITUCIJSKA_SIFRA.toString());
        opis.postaviTekst(OpisiKalkulatora.SUPSTITUCIJA_OPIS,
            UputeKalkulatora.SUPSTITUCIJA_UPUTE,
            imeKalkulatora);
        new Thread(() -> opis.postaviGraf(false)).start();
      }

      case CEZAROVA_SIFRA -> {
        konzola.setPodnaslov(PodnasloviKonzole.CEZAROVA_SIFRA_PODNASLOV);
        prikaz.show(srednjiStupac, ImenaKalkulatora.CEZAROVA_SIFRA.toString());
        opis.postaviTekst(
            OpisiKalkulatora.CEZAR_OPIS,
            UputeKalkulatora.CEZAR_UPUTE,
            imeKalkulatora);
        new Thread(() -> opis.postaviGraf(false)).start();
      }

      case AFINA_SIFRA -> {
        konzola.setPodnaslov(PodnasloviKonzole.AFINA_SIFRA_PODNASLOV);
        prikaz.show(srednjiStupac, ImenaKalkulatora.AFINA_SIFRA.toString());
        opis.postaviTekst(
                OpisiKalkulatora.AFINA_OPIS,
                UputeKalkulatora.AFINA_UPUTE,
                imeKalkulatora);
        new Thread(() -> opis.postaviGraf(false)).start();
      }

      case ANALIZA_TEKSTA -> {
        konzola.setPodnaslov(PodnasloviKonzole.ANALIZA_TEKSTA_PODNASLOV);
        prikaz.show(srednjiStupac, ImenaKalkulatora.ANALIZA_TEKSTA.toString());
        opis.postaviTekst(
            OpisiKalkulatora.ANALIZA_TEKSTA_OPIS,
            UputeKalkulatora.ANALIZA_TEKSTA_UPUTE,
            imeKalkulatora);
        new Thread(opis::postaviPrazanGraf).start();
      }

      case STUPCANA_TRANSPOZICIJA -> {
        konzola.setPodnaslov(PodnasloviKonzole.STUPCANA_TRANSPOZICIJA_PODNASLOV);
        prikaz.show(srednjiStupac, ImenaKalkulatora.STUPCANA_TRANSPOZICIJA.toString());
        opis.postaviTekst(
                OpisiKalkulatora.STUPCANA_OPIS,
                UputeKalkulatora.STUPCANA_UPUTE,
                imeKalkulatora);
        new Thread(() -> opis.postaviGraf(false)).start();
      }

      case UVODNA_STRANICA -> {
        prikaz.show(srednjiStupac, ImenaKalkulatora.UVODNA_STRANICA.toString());
      }

      default -> {
        konzola.setPodnaslov("");
        prikaz.show(srednjiStupac, "NULL");
        opis.postaviTekst("", "", null);
        new Thread(opis::postaviPrazanGraf).start();
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
    omoguciTipku(uvodnaStranicaButton, true);
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
      srednjiStupac.requestFocus();
    });
    supstitucijskaButton.addActionListener(e -> {
      omoguciSveTipke();
      omoguciTipku(supstitucijskaButton, false);
      postaviKalkulator(ImenaKalkulatora.SUPSTITUCIJSKA_SIFRA);
      srednjiStupac.requestFocus();
    });
    afinaButton.addActionListener(e -> {
      omoguciSveTipke();
      omoguciTipku(afinaButton, false);
      postaviKalkulator(ImenaKalkulatora.AFINA_SIFRA);
      srednjiStupac.requestFocus();
    });
    hillovaButton.addActionListener(e -> {
      omoguciSveTipke();
      omoguciTipku(hillovaButton, false);
      postaviKalkulator(ImenaKalkulatora.HILLOVA_SIFRA);
      srednjiStupac.requestFocus();
    });
    vigenerovaButton.addActionListener(e -> {
      omoguciSveTipke();
      omoguciTipku(vigenerovaButton, false);
      postaviKalkulator(ImenaKalkulatora.VIGENEROVA_SIFRA);
      srednjiStupac.requestFocus();
    });
    playfairovaButton.addActionListener(e -> {
      omoguciSveTipke();
      omoguciTipku(playfairovaButton, false);
      postaviKalkulator(ImenaKalkulatora.PLAYFAIROVA_SIFRA);
      srednjiStupac.requestFocus();
    });
    stupcanaButton.addActionListener(e -> {
      omoguciSveTipke();
      omoguciTipku(stupcanaButton, false);
      postaviKalkulator(ImenaKalkulatora.STUPCANA_TRANSPOZICIJA);
      srednjiStupac.requestFocus();
    });
    rsaButton.addActionListener(e -> {
      omoguciSveTipke();
      omoguciTipku(rsaButton, false);
      postaviKalkulator(ImenaKalkulatora.RSA_SIFRA);
      srednjiStupac.requestFocus();
    });
    elgamalButton.addActionListener(e -> {
      omoguciSveTipke();
      omoguciTipku(elgamalButton, false);
      postaviKalkulator(ImenaKalkulatora.EL_GAMALOVA_SIFRA);
      srednjiStupac.requestFocus();
    });
    analizaTekstaButton.addActionListener(e -> {
      omoguciSveTipke();
      omoguciTipku(analizaTekstaButton, false);
      postaviKalkulator(ImenaKalkulatora.ANALIZA_TEKSTA);
      srednjiStupac.requestFocus();
    });
    stupcanaButton.addActionListener(e -> {
      omoguciSveTipke();
      omoguciTipku(stupcanaButton, false);
      postaviKalkulator(ImenaKalkulatora.STUPCANA_TRANSPOZICIJA);
      srednjiStupac.requestFocus();
    });
    uvodnaStranicaButton.addActionListener(e -> {
      omoguciSveTipke();
      omoguciTipku(uvodnaStranicaButton, false);
      postaviKalkulator(ImenaKalkulatora.UVODNA_STRANICA);
      srednjiStupac.requestFocus();
    });

    uvodnaStranicaButton.doClick();
  }

  public void actionPerformed(ActionEvent ae) {
    String choice = ae.getActionCommand();
    if (choice.equals("Quit")) {
      System.exit(0);
    }
  }
}
