package pmf.math.konstante;

public class OpisiKalkulatora {

  public static final String CEZAR_OPIS = """
    U kriptografiji, Cezarova šifra jedan je od najjednostavnijih i najrasprostranjenijih načina šifriranja. To je tip šifre zamjene (supstitucije), u kome se svako slovo otvorenog teksta zamjenjuje odgovarajućim slovom abecede, pomaknutim za određeni broj mjesta. 
    
    Na primjer, s pomakom 3, A se zamjenjuje slovom D, B slovom E itd. 
    
    Ova metoda je dobila ime po Juliju Cezaru, koji ju je koristio za razmjenu poruka sa svojim generalima.
      """;

  public static final String SUPSTITUCIJA_OPIS = """
    Opća supstitucijska šifra jedna je od najjednostavnijih šifri, u kojoj se svako slovo otvorenog teksta zamjenjuje odgovarajućim slovom abecede, prema zadanoj permutaciji slova.
    
    Postoje i mnoge podvrste supstitucijskih šifri, kao što su na primjer Cezarova šifra, Cezarova šifra s ključnom riječi ili afina šifra. U njima se također svako slovo zamjenjuje nekim drugim, ali prema posebnim pravilima koja određuju korištenu permutaciju slova.
      """;

  public static final String AFINA_OPIS = """
        
      """;

  public static final String HILL_OPIS = """
      Hill-ova šifra je poligrafska supstitucijska šifra u klasičnoj kriptografiji koja se temelji na linearnoj algebri.
     
      Izumio ju je Lester S. Hill 1929. godine i to je bila prva poligrafska šifra u kojoj je bilo praktično operirati s više od tri simbola odjednom.
      
      Šifriranje odnosno dešifriranje se vrši množenjem vektora induciranim pojedinom m-torkom slova iz teksta sa matricom ključa dimenzije m.
      Rezultirani vektor zatim se preslikava natrag u slova.
      
      Za šifriranje i dešifriranje je potrebno da je matrica ključa K regularna.
      Za dešifriranje je dodatno potrebno da je matrica ključa involuirana odnosno da vrijedi K = K^-1, jer će u protivnom doći do nepoželjnih rezultata.
      """;

  public static final String VIGENERE_OPIS = """
      Vigenèreovu šifra spada u polialfabetske kriptosustave kod kojih se svako slovo otvorenog teksta može preslikati u jedno od m mogućih slova (gdje je m duljina ključa), u ovisnosti o svom položaju unutar otvorenog teksta.
        
      Francuski diplomat Blaise de Vigenère je 1586. godine objavio knjigu "Traicte de Chiffres" u kojoj se nalazilo sve što se u to vrijeme znalo o kriptografiji (ali gotovo ništa o kriptoanalizi).
      U njoj je opisano više originalnih polialfabetskih sustava, među kojima se nalazi i ovaj.
      
      Šifriranje/dešifriranje vrši se pomoću danog ključa KLJUC duljine m.
      
      U slučaju ponavljajućeg ključa otvoreni tekst/sifrat najprije se razbija na riječi duljine m.
      Pojedina razbijena riječ se zatim šifrira/dešifrira uz sljedeće transformacije:
      +/- KLJUCKLJUCKLJUC...
          
      U slučaju Vigenèreovog kvadrata otvoreni tekst/sifrat OTVORENITEKST / SIFRAT šifrira/dešifrira se u riječ REZULTAT uz sljedeće transformacije:
      + KLJUCOTVORENITEKST / - KLJUCREZULTAT
      """;

  public static final String PLAYFAIR_OPIS = """
      Ovu šifru je izumio britanski znanstvenik Charles Wheatstone 1854. godine, a ime je dobila po njegovom prijatelju barunu Playfairu od St. Andrewsa koji ju je popularizirao.
            
      To je bigramska šifra, u smislu da se šifriraju parovi slova LD i to tako da rezultat ovisi i o jednom i o drugom slovu.
            
      Šifriranje(dešifriranje) vrši se za svaki par slova pomoću Playfairove 5x5 tablice na sljedeći način:
      (lijevo slovo: L, desno slovo: D)
          ■ Ako su L i D jednaki, onda se vraća LL.
          ■ Ako su L i D u istom retku, onda se vraćaju znakovi L'D' koji su dobiveni pomicanjem L i D za jedno mjesto udesno(ulijevo).
          ■ Ako su L i D u istom stupcu, onda se vraćaju znakovi L'D' koji su dobiveni pomicanjem L i D za jedno mjesto dolje(gore).
          ■ Preostao je slučaj kada L i D nisu u istom retku ni u istom stupcu. Tada oni čine pravokutnik sa L' i D'. Ako postavimo:
           L' = vrh pravokutnika u istom retku kao L
           D' = vrh pravokutnika u istom retku sa D
       onda se vraća L'D'.
      """;

  public static final String STUPCANA_OPIS = """
        
      """;

  public static final String RSA_OPIS = """
        
      """;

  public static final String EL_GAMAL_OPIS = """

      """;

  public static final String ANALIZA_TEKSTA_OPIS = """
      Analiza frekvencije pojedinih slova u šifratu može pomoći u supstitucijskim šiframa za otkrivanje otvorenog teksta.
      
      To se čini tako da se te frekvencije usporede sa prosječnim i očekivanim frekvencijama u otvorenom tekstu.
      
      Bitno je naglasiti da je ova metoda bolja što je tekst opsežniji i raznolikiji.
      
      Slijede srednje frekvencije slova, bigrama i trigrama na raznim jezicima:
      
      HRVATSKI   ENGLESKI    NJEMAČKI
      A   115    E   127     E   175
      I   98     T   91      N   98
      O   90     A   82      I   77
      E   84     O   75      R   75
      N   66     I   70      S   68
      S   56     N   67      A   65
      R   54     S   63      T   61
      J   51     H   61      D   48
      T   48     R   60      H   42
      U   43     D   43      U   42
      D   37     L   40      L   35
      K   36     C   28      G   31
      V   35     U   28      O   30
      L   33     M   24      C   27
      M   31     W   23      M   26
      P   29     F   22      B   19
      C   28     G   20      F   17
      Z   23     Y   20      W   15
      G   16     P   19      K   15
      B   15     B   15      Z   11
      H   8      V   10      P   10
      F   3      K   8       V   9
      Q   0      J   2       J   3
      Y   0      Q   1       Y   1
      X   0      X   1       X   0
      W   0      Z   1       Q   0
                  
                  
                  
      Zatim najčešći bigrami:
      
      HRVATSKI:
      JE, NA, RA, ST, AN, NI, KO, OS, TI, IJ, NO, EN, PR
      
      ENGLESKI:
      TH, HE, AN, IN, ER, RE, ON, ES, TI, AT
      
      NJEMAČKI:
      ER, EN, CH, DE, EI, ND, TE, IN, IE, GE
      
      
      
      Zatim najčešći trigrami:
      
      HRVATSKI:
      IJE, STA, OST, JED, KOJ, OJE, JEN
      
      ENGLESKI:
      THE, ING, AND, ION, TIO, ENT, ERE, HER
      
      NJEMAČKI:
      EIN, ICH, NDE, DIE, UND, DER, CHE, END
      
      """;
}
