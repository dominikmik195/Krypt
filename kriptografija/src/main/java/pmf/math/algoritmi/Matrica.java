package pmf.math.algoritmi;

import java.util.function.BinaryOperator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Matrica {

  private int[][] matrica;

  public Matrica(int[][] podaci) {
    matrica = podaci;
  }

  public int getN() {
    return matrica.length;
  }

  public int getM() {
    return matrica[0].length;
  }

  public int dohvatiElement(int i, int j) {
    return matrica[i][j];
  }

  public int[] dohvatiRedak(int i) {
    int[] redak = new int[getM()];
    for (int j = 0; j < getM(); j++) {
      redak[j] = matrica[i][j];
    }
    return redak;
  }

  public int[] dohvatiStupac(int j) {
    int[] stupac = new int[getN()];
    for (int i = 0; i < getN(); i++) {
      stupac[i] = matrica[i][j];
    }
    return stupac;
  }

  public Matrica zbroji(Matrica B) {
    return binarnaOperacija(B, Integer::sum);
  }

  public Matrica binarnaOperacija(Matrica B, BinaryOperator<Integer> binarniOperator) {
    if (matrica.length == 0 || B.getMatrica().length == 0) {
      return null;
    }

    int An = getN(), Am = getM();
    int Bn = B.getN(), Bm = B.getM();

    if (An != Bn || Am != Bm) {
      return null;
    } else {
      int[][] podaci = new int[An][Am];
      for (int i = 0; i < An; i++) {
        for (int j = 0; j < An; j++) {
          podaci[i][j] = binarniOperator.apply(matrica[i][j], B.dohvatiElement(i, j));
        }
      }
      return new Matrica(podaci);
    }
  }

  public Matrica pomnozi(int alfa) {
    if (matrica.length == 0) {
      return null;
    }
    int An = getN(), Am = getM();
    int[][] podaci = new int[An][Am];
    for (int i = 0; i < An; i++) {
      for (int j = 0; j < An; j++) {
        podaci[i][j] *= alfa;
      }
    }
    return new Matrica(podaci);
  }

  public Matrica pomnozi(Matrica B) {
    if (matrica.length == 0 || B.getMatrica().length == 0) {
      return null;
    }

    int An = getN(), Am = getM();
    int Bn = B.getN(), Bm = B.getM();

    if (Am != Bn) {
      return null;
    } else {
      int[][] podaci = new int[An][Am];
      for (int i = 0; i < An; i++) {
        for (int j = 0; j < An; j++) {
          Vektor redak = new Vektor(dohvatiRedak(i));
          Vektor stupac = new Vektor(B.dohvatiStupac(j));
          podaci[i][j] = redak.pomnozi(stupac);
        }
      }
      return new Matrica(podaci);
    }
  }

  public Vektor pomnozi(Vektor v) {
    int[] rezultat = new int[getN()];

    for (int i = 0; i < getN(); i++) {
      Vektor redak = new Vektor(dohvatiRedak(i));
      rezultat[i] = redak.pomnozi(v);
    }
    return new Vektor(rezultat);
  }

  public boolean regularna() {
    if(getN() != getM()) return false;
    int D = determinant(matrica, getN());
    return D != 0;
  }

  public boolean involuirana() {
    if(getN() != getM()) return false;
    double[][] inverz = new double[getN()][getN()];
    inverse(matrica, inverz);
    if (inverz == null) {
      return false;
    }
    if (inverz.length == 0 || inverz.length != inverz[0].length) {
      return false;
    }

    int m = inverz.length;
    int[][] intInverz = new int[m][m];
    for (int i = 0; i < intInverz.length; i++) {
      for (int j = 0; j < intInverz.length; j++) {
        intInverz[i][j] = (int) inverz[i][j];
        if ((double) intInverz[i][j] != inverz[i][j]) {
          return false;
        }
        if (intInverz[i][j] != dohvatiElement(i, j)) {
          return false;
        }
      }
    }
    return true;
  }

  public String[][] pretvoriUStringArray() {
    String[][] izlaz = new String[getN()][getM()];
    for (int i = 0; i < getN(); i++) {
      for (int j = 0; j < getM(); j++) {
        izlaz[i][j] = String.valueOf(matrica[i][j]);
      }
    }
    return izlaz;
  }


//--------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------

  // izvučeno iz: https://www.geeksforgeeks.org/adjoint-inverse-matrix/
  // Function to get cofactor of A[p][q] in temp[][]. n is current
  // dimension of A[][]
  void getCofactor(int[][] A, int[][] temp, int p, int q, int n)
  {
    int i = 0, j = 0;

    // Looping for each element of the matrix
    for (int row = 0; row < n; row++)
    {
      for (int col = 0; col < n; col++)
      {
        //  Copying into temporary matrix only those element
        //  which are not in given row and column
        if (row != p && col != q)
        {
          temp[i][j++] = A[row][col];

          // Row is filled, so increase row index and
          // reset col index
          if (j == n - 1)
          {
            j = 0;
            i++;
          }
        }
      }
    }
  }

  /* Recursive function for finding determinant of matrix.
     n is current dimension of A[][]. */
  int determinant(int[][] A, int n)
  {
    int D = 0; // Initialize result

    //  Base case : if matrix contains single element
    if (n == 1)
      return A[0][0];

    int[][] temp = new int[n][n]; // To store cofactors

    int sign = 1;  // To store sign multiplier

    // Iterate for each element of first row
    for (int f = 0; f < n; f++)
    {
      // Getting Cofactor of A[0][f]
      getCofactor(A, temp, 0, f, n);
      D += sign * A[0][f] * determinant(temp, n - 1);

      // terms are to be added with alternate sign
      sign = -sign;
    }

    return D;
  }

  // Function to get adjoint of A[N][N] in adj[N][N].
  void adjoint(int[][] A,int[][] adj)
  {
    int N = A.length;
    if (N == 1)
    {
      adj[0][0] = 1;
      return;
    }

    // temp is used to store cofactors of A[][]
    int sign = 1;
    int[][] temp = new int[N][N];

    for (int i=0; i<N; i++)
    {
      for (int j=0; j<N; j++)
      {
        // Get cofactor of A[i][j]
        getCofactor(A, temp, i, j, N);

        // sign of adj[j][i] positive if sum of row
        // and column indexes is even.
        sign = ((i+j)%2==0)? 1: -1;

        // Interchanging rows and columns to get the
        // transpose of the cofactor matrix
        adj[j][i] = (sign)*(determinant(temp, N-1));
      }
    }
  }

  // Function to calculate and store inverse, returns false if
// matrix is singular
  boolean inverse(int[][] A, double[][] inverse)
  {
    if(A.length == 0 || A.length != A[0].length) return false;
    int N = A.length;

    // Find determinant of A[][]
    int det = determinant(A, N);
    if (det == 0)
    {
      return false;
    }

    // Find adjoint
    int[][] adj = new int[N][N];
    adjoint(A, adj);

    // Find Inverse using formula "inverse(A) = adj(A)/det(A)"
    for (int i=0; i<N; i++)
      for (int j=0; j<N; j++)
        inverse[i][j] = adj[i][j]/(double) det;

    return true;
  }
}
