package pmf.math.konstante;

public class RSAPrimjeri {
  public static final int[][] JEDNOZNAMENKASTI = {{3, 3, 9, 3, 3}};
  public static final int[][] DVOZNAMENKASTI = {
    {5, 7, 35, 11, 11}, {7, 3, 21, 13, 13}, {11, 7, 77, 13, 37}
  };
  public static final int[][] TROZNAMENKASTI = {
    {11, 53, 583, 17, 153}, {23, 31, 713, 113, 257}, {17, 19, 323, 151, 103}
  };
  public static final int[][] CETVEROZNAMENKASTI = {
    {31, 71, 2201, 1133, 797}, {43, 67, 2881, 613, 2053}, {23, 89, 2047, 701, 997}
  };
  public static final int[][] PETEROZNAMENKASTI = {
    {113, 617, 69721, 11311, 51407}, {353, 251, 88603, 21407, 80543}, {173, 353, 61069, 3517, 22293}
  };

  public static final int[][] SESTEROZNAMENKASTI = {
    {257, 449, 115393, 56731, 49299},
    {751, 653, 490403, 71371, 202531},
    {877, 907, 795439, 14693, 647813}
  };
  public static final int[][] SEDMEROZNAMENKASTI = {
    {1277, 953, 1216981, 713715, 195003},
    {4129, 1889, 7799681, 1469357, 3933221},
    {1744, 2731, 4732823, 1469357, 3027893}
  };
  public static final int[][] OSMEROZNAMENKASTI = {
    {3251, 4231, 13754981, 61327, 12855763},
    {6763, 6113, 41342219, 4317553, 14806033},
    {8017, 9601, 76971217, 43175531, 20053571}
  };
  public static final int[][][] PRIMJERI = {
    JEDNOZNAMENKASTI,
    DVOZNAMENKASTI,
    TROZNAMENKASTI,
    CETVEROZNAMENKASTI,
    PETEROZNAMENKASTI,
    SESTEROZNAMENKASTI,
    SEDMEROZNAMENKASTI,
    OSMEROZNAMENKASTI
  };
}
