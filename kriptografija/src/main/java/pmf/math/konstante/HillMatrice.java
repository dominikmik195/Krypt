package pmf.math.konstante;

public class HillMatrice {

  public static String[][] Identiteta(int m) {
    String[][] izlaz = new String[m][m];
    for(int i = 0; i < m; i++) {
      for(int j = 0; j < m; j++) {
        if(i == j) izlaz[i][j] = "1";
        else izlaz[i][j] = "0";
      }
    }
    return izlaz;
  }

  public static String[] Naslov(int m) {
    return new String[m];
  }

}
