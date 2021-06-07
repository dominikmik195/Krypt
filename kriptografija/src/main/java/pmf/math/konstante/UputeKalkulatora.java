package pmf.math.konstante;

public class UputeKalkulatora {

  public static final String CEZAR_UPUTE = """
      Za (de)šifriranje pomoću Cezarove šifre potrebno je unijeti pomak (broj).
      
      Za (de)šifriranje pomoću Cezarove šifre s ključnom riječi, treba označiti odgovarajuću kućicu te unijeti ključnu riječ i pomak (broj), koji označava mjesto od koje ključna riječ počinje u abecedi šifrata.
      
      Sav uneseni tekst (otvoreni tekst, šifrat i ključna riječ) smije sadržavati isključivo slova engleske abecede.
      
      Za pokretanje postupka, pritisnuti odgovarajuću strelicu ↓ ili ↑.
      """;

  public static final String SUPSTITUCIJA_UPUTE = """
      Za (de)šifriranje pomoću opće supstitucijske šifre, potrebno je unijeti zamjensko slovo za svako od navedenih slova engleske abecede. Ukoliko zamjensko slovo za neko od slova nije upisano, ono će u šifratu ili otvorenom tekstu biti prikazano kao _.
      
      Prilikom unosa zamjenskih slova, potrebno je pripaziti da nikoja dva slova nisu zamijenjena istim slovom (primjerice, slovo A i slovo B ne smiju oba biti zamijenjena slovom Q).
      
      Sav uneseni tekst (otvoreni tekst i šifrat) smije sadržavati isključivo slova engleske abecede.
      
      Za pokretanje postupka, pritisnuti odgovarajuću strelicu ↓ ili ↑.
      """;

  public static final String AFINA_UPUTE = """
        
      """;

  public static final String HILL_UPUTE = """
      Hillov kriptosustav ima mogućnost šifriranja i dešifriranja ovisno o zadanom ključu (matrici).
      
      Šifriranje odnosno dešifriranje se vrši množenjem vektora induciranim pojedinom m-torkom slova iz teksta sa matricom ključa.
      Rezultirani vektor zatim se preslikava natrag u slova.
      
      Za šifriranje i dešifriranje je potrebno da je matrica ključa K regularna.
      Za dešifriranje je dodatno potrebno da je matrica ključa INVOLUIRANA odnosno da vrijedi K = K^-1, jer će u protivnom doći do nepoželjnih rezultata.
      
      Ćelije matrice mogu biti peteroznamenkasti brojevi sa ili bez predznaka. Dimenzije matrice također se mogu mijenjati (m je od 2 do 5).
      Kljuc je moguće i zaključati kako bi se izbjegao slučajan gubitak podataka.
      
      Ako je unesen otvoreni tekst i šifrat jednakih i ispravnih duljina (length % m == 0), onda je moguće pokušati odgonetnuti ključ.
      OPREZ: vrlo je teško pronaći validan ključ za veći unos. Preporučljivo je unositi maksimalno m m-torki.
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
      Za šifriranje je potrebno unijeti broj n (ili p i q), i broj e.
      Za dešifriranje je potrebno unijeti broj n (ili p i q), i broj d.
      Ukoliko želite provjeriti jesu li vaši podatci valjani, tj. međusobno kompatibilni, pritisnite odgovarajuću tipku.
      Ako podatci nisu u redu, tim postupkom će biti ispravljeni. Da bi to bilo moguće, potrebno je unijeti barem prost broj i tajni ključ.
      """;

  public static final String EL_GAMAL_UPUTE = """
      Za šifriranje je potrebno unijeti prost broj, alfu, betu i tajni broj. Za pokretanje postupka, pritisnete strelicu ↓.
      Za dešifriranje je potrebno unijeti prost broj, alfu, betu i tajni ključ. Za pokretanje postupka, pritisnete strelicu ↑.
      Ukoliko želite provjeriti jesu li vaši podatci valjani, tj. međusobno kompatibilni, pritisnite odgovarajuću tipku.
      Ako podatci nisu u redu, tim postupkom će biti ispravljeni. Da bi to bilo moguće, potrebno je unijeti barem valjani n (ili p i q).
      """;
}
