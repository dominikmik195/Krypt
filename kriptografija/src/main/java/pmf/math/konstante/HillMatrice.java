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

  public static int[][] kljucZaSimulaciju() {
    return new int[][] {
        {4,3,3},
        {-1,0,1},
        {-4,-4,-3}
    };
  }

}
