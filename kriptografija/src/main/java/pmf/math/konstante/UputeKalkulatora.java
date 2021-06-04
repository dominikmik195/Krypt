package pmf.math.konstante;

public class UputeKalkulatora {

  public static final String CEZAR_UPUTE = """
      Za šifriranje pomoću Cezarove šifre dovoljno je unijeti pomak (broj).
      Za šifriranje pomoću Cezarove šifre s ključnom riječi, treba označiti
      odgovarajuću kućicu te unijeti ključnu riječ i pomak (broj), koji označava
      mjesto od koje ključna riječ počinje u abecedi šifrata.
      Sav uneseni tekst smije sadržavati isključivo slova engleske abecede.
      Za pokretanje postupka, pritisnuti odgovarajuću strjelicu.
      """;

  public static final String SUPSTITUCIJA_UPUTE = """
        
      """;

  public static final String AFINA_UPUTE = """
        
      """;

  public static final String HILL_UPUTE = """
        
      """;

  public static final String VIGENERE_UPUTE = """
        
      """;

  public static final String PLAYFAIR_UPUTE = """
      Za šifriranje/dešifriranje dovoljno je kliknuti pripadni gumb ↓ ↑.
            
      Šifriranje(dešifriranje) vrši se za svaki par slova pomoću Playfairove 5x5 tablice na sljedeći način:
      (lijevo slovo: L, desno slovo: D)
          ■ Ako su L i D jednaki, onda se vraća LL.
          ■ Ako su L i D u istom retku, onda se vraćaju znakovi L'D' koji su dobiveni pomicanjem L i D za jedno mjesto udesno(ulijevo).
          ■ Ako su L i D u istom stupcu, onda se vraćaju znakovi L'D' koji su dobiveni pomicanjem L i D za jedno mjesto dolje(gore).
          ■ Preostao je slučaj kada L i D nisu u istom retku ni u istom stupcu. Tada oni čine pravokutnik sa L' i D'. Ako postavimo:
           L' = vrh pravokutnika u istom retku kao L
           D' = vrh pravokutnika u istom retku sa D
       onda se vraća L'D'.
          
      Playfairov pravokutnik može se izmijeniti dodavanjem ključa ili izmjenom abecede.
      """;

  public static final String STUPCANA_UPUTE = """
        
      """;

  public static final String RSA_UPUTE = """
        
      """;

  public static final String EL_GAMAL_UPUTE = """
      Za šifriranje je dovoljno unijeti prost broj, tajni ključ, tajni broj i otvoreni tekst - svugdje po JEDAN broj.
      U tom slučaju će se alfa i beta izračunati automatski.
      Ukoliko želite ručno unijeti alfa ili beta, označite odgovarajuću kućicu i unesite željeni broj , ali - pazite: za alfa i beta vrijede posebni uvjeti!
      Osim toga, možete unijeti samo alfu ili i alfu i betu, no ne možete unijeti samo betu!
      Za dešifriranje je dovoljno unijeti prost broj, tajni ključ i šifrat u formatu: (broj1, broj2)
      Za alfu i betu vrijede ista pravila kao i u šifriranju.
      Za pokretanje postupka, pritisnuti odgovarajuću strjelicu.)
      """;
}
