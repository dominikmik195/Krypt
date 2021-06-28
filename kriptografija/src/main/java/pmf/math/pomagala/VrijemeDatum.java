package pmf.math.pomagala;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VrijemeDatum {

  public static String dohvatiVrijemeDatum(Date datum) {
    return dohvatiVrijemeDatum(datum, "dd/MM/yyyy");
  }

  public static String dohvatiVrijemeDatum(Date datum, String forma) {
    DateFormat df = new SimpleDateFormat(forma);
    return df.format(datum);
  }
}
