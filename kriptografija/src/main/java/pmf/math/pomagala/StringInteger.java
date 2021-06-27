package pmf.math.pomagala;

public class StringInteger {

  public static String intRedUString(int[] red) {
    StringBuilder izlazniTekst = new StringBuilder();
    if(red.length > 0) {
      izlazniTekst.append(red[0]);
    }
    else {
      return "";
    }
    for(int i = 1; i < red.length; i++){
      izlazniTekst.append(",").append(red[i]);
    }
    return izlazniTekst.toString();
  }

  public static int[] stringUIntRed(String red) {
    String[] razlomljeniRed= red.split(",");
    int[] izlazniRed = new int[razlomljeniRed.length];
    for(int i = 0; i < izlazniRed.length; i++) {
      izlazniRed[i] = Integer.parseInt(razlomljeniRed[i]);
    }
    return izlazniRed;
  }

}
