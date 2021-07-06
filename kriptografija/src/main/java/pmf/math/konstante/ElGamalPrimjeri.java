package pmf.math.konstante;

public class ElGamalPrimjeri {
  public static final int[][] JEDNOZNAMENKASTI = {{7, 6, 5, 1, 5}, {5, 7, 7, 3, 9}, {3, 8, 2, 1, 4}};
  public static final int[][] DVOZNAMENKASTI = {
    {11, 23, 7, 2, 41}, {43, 57, 5, 26, 34}, {97, 89, 13, 83, 76}
  };
  public static final int[][] TROZNAMENKASTI = {
    {127, 213, 23, 33, 286}, {479, 557, 29, 415, 321}, {829, 916, 313, 509, 764}
  };
  public static final int[][] CETVEROZNAMENKASTI = {
    {1279, 2314, 211, 494, 1743}, {4649, 5627, 217, 2865, 6734}, {9403, 8731, 231, 4114, 9132}
  };
  public static final int[][] PETEROZNAMENKASTI = {
    {13049, 18562, 213, 8639, 21054},
    {41479, 54268, 231, 16037, 40671},
    {92791, 84921, 6, 50829, 92364}
  };
  public static final int[][][] PRIMJERI = {
    JEDNOZNAMENKASTI, DVOZNAMENKASTI, TROZNAMENKASTI, CETVEROZNAMENKASTI, PETEROZNAMENKASTI
  };
}
