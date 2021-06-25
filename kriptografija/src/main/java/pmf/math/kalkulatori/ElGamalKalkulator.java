package pmf.math.kalkulatori;

import pmf.math.algoritmi.TeorijaBrojeva;
import pmf.math.baza.dao.ElGamalDAO;
import pmf.math.kriptosustavi.ElGamalKriptosustav;
import pmf.math.obradaunosa.ObradaUnosaElGamal;
import pmf.math.router.Konzola;

import javax.swing.*;

public class ElGamalKalkulator {
  private static final ElGamalKriptosustav stroj = new ElGamalKriptosustav();

  public JPanel glavniPanel;
  public Konzola konzola;

  private JLabel prostBrojLabel;
  private JLabel tajniKljucLabel;
  private JLabel tajniBrojLabel;
  private JLabel otvoreniTekstLabel;
  private JLabel sifratLabel;
  private JTextField prostBrojField;
  private JTextField tajniKljucField;
  private JTextField alfaField;
  private JTextField betaField;
  private JTextField tajniBrojField;
  private JTextArea otvoreniTekstArea;
  private JTextArea sifratArea;
  private JButton sifrirajButton;
  private JButton desifrirajButton;
  private JButton provjeriIIspraviButton;
  private JButton ocistiPoljaButton;
  private JProgressBar progressBar;
  private JButton lijevoButton;
  private JButton desnoButton;
  private JLabel pbBazaLabel;
  private JLabel tkBazaLabel;
  private JLabel alfaBazaLabel;
  private JLabel betaBazaLabel;
  private JLabel tbBazaLabel;
  private JButton odaberiPodatkeButton;
  private JPanel podatciJPanel;

  private ElGamalDAO elGamalDao = new ElGamalDAO();
  private int trenutniPrikaz = 0;

  public ElGamalKalkulator(Konzola _konzola) {
    konzola = _konzola;
    prikaziTrenutni();
    provjeriTipkeLijevoDesno();

    sifrirajButton.addActionListener(
            e -> {
              onemoguciSucelje();
              stroj.setOK(true);
              stroj.reinicijalizirajPoruke();
              int[] PAB = dohvatiPAB();
              if(PAB[3] == 0) {
                stroj.prosiriPoruku("Daljnji nastavak nije moguć!");
                stroj.setOK(false);
              }
              int tB = dohvatiVarijablu(4);
              if(tB < 0 && stroj.isOK()) {
                stroj.prosiriPoruku(vratiGresku(4, tB));
                stroj.prosiriPoruku("Daljnji nastavak nije moguć!");
                stroj.setOK(false);
              }
              int broj = dohvatiVarijablu(5);
              if(broj < 0 && stroj.isOK()) {
                stroj.prosiriPoruku(vratiGresku(5, broj));
                stroj.prosiriPoruku("Daljnji nastavak nije moguć!");
                stroj.setOK(false);
              }

              stroj.prostBroj = PAB[0];
              stroj.alfa = PAB[1];
              stroj.beta = PAB[2];
              stroj.setTajniBroj(tB);
              stroj.setOtvoreniTekst(broj);

              SwingUtilities.invokeLater(() -> {
                if(!stroj.dohvatiPoruke().equals("")) konzola.ispisiPoruku(stroj.dohvatiPoruke());
                if(stroj.isOK()) {
                  stroj.sifriraj();
                  sifratArea.setText(String.valueOf(stroj.vratiSifrat()));
                  konzola.ispisiPoruku("Šifriranje uspješno!");
                }
                else
                  konzola.ispisiGresku("Šifriranje neuspješno!");
                omoguciSucelje();
              });
            });

    desifrirajButton.addActionListener(
            e -> new Thread(() -> {
              onemoguciSucelje();
              stroj.setOK(true);
              stroj.reinicijalizirajPoruke();
              int[] PAB = dohvatiPAB();
              if(PAB[3] == 0) {
                stroj.prosiriPoruku("Daljnji nastavak nije moguć!");
                stroj.setOK(false);
              }
              int tK = dohvatiVarijablu(1);
              if(tK < 0 && stroj.isOK()) {
                stroj.prosiriPoruku(vratiGresku(1, tK));
                stroj.prosiriPoruku("Daljnji nastavak nije moguć!");
                stroj.setOK(false);
              }
              if(stroj.isOK()) {
                try {
                  String sifra = sifratArea.getText().strip();
                  if (!ObradaUnosaElGamal.provjeriSifru(sifra)) {
                    stroj.prosiriPoruku("Krivi format šifre!");
                    stroj.setOK(false);
                  }
                  else {
                    stroj.prostBroj = PAB[0];
                    stroj.alfa = PAB[1];
                    stroj.beta = PAB[2];
                    stroj.setTajniKljuc(tK);
                    stroj.pohraniSifrat(sifra);
                    stroj.prosiriPoruku("Dešifriranje uspješno!");
                  }
                } catch (StringIndexOutOfBoundsException | NumberFormatException ex) {
                  stroj.prosiriPoruku("Krivi format šifre!");
                  stroj.setOK(false);
                }
              }
              SwingUtilities.invokeLater(() -> {
                konzola.ispisiPoruku(stroj.dohvatiPoruke());
                if(stroj.isOK())
                  otvoreniTekstArea.setText(String.valueOf(stroj.desifriraj()));
                else
                  konzola.ispisiGresku("Neuspjelo dešifriranje!");
                omoguciSucelje();
              });
            }).start());
    provjeriIIspraviButton.addActionListener(e -> provjeriIIspravi());
    ocistiPoljaButton.addActionListener(e -> {
      prostBrojField.setText("");
      tajniKljucField.setText("");
      alfaField.setText("");
      betaField.setText("");
      tajniBrojField.setText("");
      otvoreniTekstArea.setText("");
      sifratArea.setText("");
    });

    desnoButton.addActionListener(e -> {
      trenutniPrikaz++;
      prikaziTrenutni();
      provjeriTipkeLijevoDesno();
    });

    lijevoButton.addActionListener(e -> {
      trenutniPrikaz--;
      prikaziTrenutni();
      provjeriTipkeLijevoDesno();
    });

    odaberiPodatkeButton.addActionListener(e -> {
      prostBrojField.setText(pbBazaLabel.getText());
      tajniKljucField.setText(tkBazaLabel.getText());
      alfaField.setText(alfaBazaLabel.getText());
      betaField.setText(betaBazaLabel.getText());
      tajniBrojField.setText(tbBazaLabel.getText());
    });
  }

  private int dohvatiVarijablu(int kod) {
    // Funkcija koja pokušava dohvatiti traženu varijablu, obzirom na dani kod.
    int trazeni = -1;
    try {
      switch (kod) {
        case 0 -> trazeni = Integer.parseInt(prostBrojField.getText());
        case 1 -> trazeni = Integer.parseInt(tajniKljucField.getText());
        case 2 -> trazeni = Integer.parseInt(alfaField.getText());
        case 3 -> trazeni = Integer.parseInt(betaField.getText());
        case 4 -> trazeni = Integer.parseInt(tajniBrojField.getText());
        case 5 -> trazeni = Integer.parseInt(otvoreniTekstArea.getText());
      }
      if (trazeni < 0) return -2;
      if(kod == 0 && !TeorijaBrojeva.prost(trazeni)) return -3;
    } catch (NumberFormatException ex) {
      return -1;
    }
    return trazeni;
  }

  private String vratiGresku(int kod, int rezultat) {
    // Funkcija koja ispisuje greške obzirom na kod varijable i rezultata dohvaćanja.
    // Ako je rezultat -1, unosa nema ili je neispravan. Ako je rezultat -2, unos je negativan broj.
    // Ako je rezultat -3, to znači da nisu zadovoljeni posebni uvjeti kriptosustava.
    String izraz = switch (kod) {
      case 0 -> "Prost broj";
      case 1 -> "Tajni ključ";
      case 2 -> "Alfa";
      case 3 -> "Beta";
      case 4 -> "Tajni broj";
      case 5 -> "Otvoreni tekst";
      default -> "";
    };
    if (rezultat == -1) {
      return izraz + " nije ispravnog formata!";
    } else if (rezultat == -2) {
      return izraz + " ne smije biti negativan!";
    } else if (rezultat == -3) {
      if (kod == 0) return "Uneseni broj p mora biti prost!";
      else if(kod == 2) return "Alfa nije primitivni korijen!";
      else if(kod == 3) return "Beta ne odgovara unesenom alfa!";
    }
    return "";
  }

  private int[] dohvatiPAB() {
    // Funkcija dohvaća prost broj, alfa i beta. Koristi se kako bi se skratio kod jer se funkcija koristi
    // i u šifriranju i u dešifriranju. Sve tri varijable moraju biti ispravno unesene!
    int[] PAB = {-1, -1, -1, 1};
    PAB[0] = dohvatiVarijablu(0);
    stroj.prosiriPoruku(vratiGresku(0, PAB[0]));
    PAB[1] = dohvatiVarijablu(2);
    stroj.prosiriPoruku(vratiGresku(2, PAB[1]));
    PAB[2] = dohvatiVarijablu(3);
    stroj.prosiriPoruku(vratiGresku(3, PAB[2]));
    if(PAB[0] < 0 || PAB[1] < 0 || PAB[2] < 0) PAB[3] = 0;
    return PAB;
  }

  private void provjeriIIspravi() {
    new Thread(() -> {
      onemoguciSucelje();
      stroj.setNapredak(0);
      stroj.reinicijalizirajPoruke();
      stroj.setOK(true);
      // Funkcija koja provjerava unose i provjerava jesu li međusobno kopatibilni te ih po potrebi ispravlja.
      int pB, tK, alfa, beta, tB;
      // Bez prostog broja i tajnog ključa, ne možemo ništa provjeravati.
        pB = dohvatiVarijablu(0);
      if(pB < 0) {
        stroj.prosiriPoruku(vratiGresku(0, pB));
        stroj.prosiriPoruku("Daljnji nastavak nije moguć.");
        stroj.setOK(false);
      }
      stroj.setNapredak(20);
      tK = dohvatiVarijablu(1);
      if(tK < 0 && stroj.isOK()) {
        stroj.prosiriPoruku(vratiGresku(1, tK));
        stroj.prosiriPoruku("Daljnji nastavak nije moguć.");
        stroj.setOK(false);
      }
      stroj.setNapredak(40);
      if(stroj.isOK()) {
      alfa = dohvatiVarijablu(2);
      // Ovdje dodjeljujemo 'poseban' kod za alfu, ako nisu zadovoljeni uvjeti kriptosustava
      if(!ObradaUnosaElGamal.provjeriAlfa(alfa, pB)) alfa = -3;
      beta = dohvatiVarijablu(3);
      // Ovdje dodjeljujemo 'poseban' kod za betu, ako nisu zadovoljeni uvjeti kriptosustava
      if(!ObradaUnosaElGamal.provjeriBeta(alfa, beta, pB, tK)) beta = -3;
      if(alfa < 0) {
        // Ako alfa nije ispravan, računamo novi te postavljamo novi beta također.
        stroj.prosiriPoruku(vratiGresku(2, alfa));
        stroj.prosiriPoruku("Tražim alfa i beta...");
        alfa = ElGamalKriptosustav.noviAlfa(pB);
        stroj.setNapredak(60);
        beta = ElGamalKriptosustav.noviBeta(pB, alfa, tK);
      }
      else if(alfa > 0) {
        stroj.setNapredak(60);
        if(beta < 0) {
          // Ako je alfa u redu a beta nije - nalazimo novi beta.
          stroj.prosiriPoruku(vratiGresku(3, beta));
          stroj.prosiriPoruku("Tražim beta...");
          beta = ElGamalKriptosustav.noviBeta(pB, alfa, tK);
        }
      }
      stroj.setNapredak(80);
      tB = dohvatiVarijablu(4);
      if(tB < 0) {
        // Ako tajni broj nije unesen, to nije katastrofa - postavljamo ga na bilo koji broj, u ovom slučaju 5.
        stroj.prosiriPoruku(vratiGresku(4, tB));
        tB = 5;
        stroj.prosiriPoruku("Postavljam tajni broj na 5...");
      }
      stroj.prosiriPoruku("Podatci su kompatibilni.");
      stroj.prostBroj = pB;
      stroj.alfa = alfa;
      stroj.beta = beta;
      stroj.setTajniKljuc(tK);
      stroj.setTajniBroj(tB);
      stroj.setNapredak(100);
      }

      SwingUtilities.invokeLater(() -> {
        konzola.ispisiPoruku(stroj.dohvatiPoruke());
        omoguciSucelje();
        if(stroj.isOK()){
          prostBrojField.setText(String.valueOf(stroj.prostBroj));
          tajniKljucField.setText(String.valueOf(stroj.getTajniKljuc()));
          alfaField.setText(String.valueOf(stroj.alfa));
          betaField.setText(String.valueOf(stroj.beta));
          tajniBrojField.setText(String.valueOf(stroj.getTajniBroj()));
          noviElement(stroj.prostBroj, stroj.getTajniKljuc(), stroj.alfa, stroj.beta, stroj.getTajniBroj());
        }
        else
          konzola.ispisiGresku("Podatci nisu kompatibilni. Ispravak nije uspješan!");
      });
    }).start();

    new Thread(() -> {
      progressBar.setVisible(true);
      while(progressBar.isVisible()) {
        SwingUtilities.invokeLater(() -> progressBar.setValue(stroj.getNapredak()));
      }
    }).start();
  }

  public void onemoguciSucelje() {
    prostBrojField.setEnabled(false);
    tajniKljucField.setEnabled(false);
    tajniBrojField.setEnabled(false);
    alfaField.setEnabled(false);
    betaField.setEnabled(false);
    ocistiPoljaButton.setEnabled(false);
    provjeriIIspraviButton.setEnabled(false);
    sifrirajButton.setEnabled(false);
    desifrirajButton.setEnabled(false);
    sifratArea.setEnabled(false);
    otvoreniTekstArea.setEnabled(false);
  }

  public void omoguciSucelje() {
    prostBrojField.setEnabled(true);
    tajniKljucField.setEnabled(true);
    tajniBrojField.setEnabled(true);
    alfaField.setEnabled(true);
    betaField.setEnabled(true);
    ocistiPoljaButton.setEnabled(true);
    provjeriIIspraviButton.setEnabled(true);
    sifrirajButton.setEnabled(true);
    desifrirajButton.setEnabled(true);
    sifratArea.setEnabled(true);
    otvoreniTekstArea.setEnabled(true);
    progressBar.setVisible(false);
  }

  private void prikaziTrenutni() {
    if(elGamalDao.brojElemenata() == 0 || podatciJPanel == null) return;
    Integer[] podaci = elGamalDao.dohvatiElement(trenutniPrikaz);
    if (podaci == null) {
      podatciJPanel.setVisible(false);
      konzola.ispisiGresku("Greška pri učitavanju povijesti ključeva.");
    } else {
      podatciJPanel.setVisible(true);
      pbBazaLabel.setText(String.valueOf(podaci[0]));
      tkBazaLabel.setText(String.valueOf(podaci[1]));
      alfaBazaLabel.setText(String.valueOf(podaci[2]));
      betaBazaLabel.setText(String.valueOf(podaci[3]));
      tbBazaLabel.setText(String.valueOf(podaci[4]));
    }
  }

  private void provjeriTipkeLijevoDesno() {
    if(elGamalDao.brojElemenata() == 0) {
      lijevoButton.setEnabled(false);
      desnoButton.setEnabled(false);
      odaberiPodatkeButton.setEnabled(false);
    }
    else {
      odaberiPodatkeButton.setEnabled(true);
      lijevoButton.setEnabled(true);
      desnoButton.setEnabled(true);
      if(trenutniPrikaz == 0) lijevoButton.setEnabled(false);
      if (trenutniPrikaz == (elGamalDao.brojElemenata()-1)) desnoButton.setEnabled(false);
    }
  }

  private void noviElement(int _pB, int _tK, int _a, int _b, int _tB) {
    Integer podatci[] = new Integer[]{_pB, _tK, _a, _b, _tB};
    elGamalDao.ubaciElement(podatci);
    trenutniPrikaz = 0;
    prikaziTrenutni();
    provjeriTipkeLijevoDesno();
  }
}
