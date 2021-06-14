package pmf.math.baza;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;

public class BazaPodataka {

  public static ConnectionSource poveznicaBaze;

  public BazaPodataka() {
    try {
      poveznicaBaze = new JdbcConnectionSource("jdbc:sqlite:krypt.db");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
