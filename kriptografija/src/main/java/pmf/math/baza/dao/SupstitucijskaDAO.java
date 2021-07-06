package pmf.math.baza.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.table.TableUtils;
import pmf.math.baza.tablice.SupstitucijskaPovijest;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static pmf.math.baza.BazaPodataka.poveznicaBaze;

public class SupstitucijskaDAO {
  public final int brojElemenata = 5;
  private Dao<SupstitucijskaPovijest, String> supstitucijskaPovijestDao;

  public SupstitucijskaDAO() {
    try {
      supstitucijskaPovijestDao = DaoManager.createDao(poveznicaBaze, SupstitucijskaPovijest.class);
      TableUtils.createTableIfNotExists(poveznicaBaze, SupstitucijskaPovijest.class);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public List<SupstitucijskaPovijest> dohvatiElemente() {
    try {
      return supstitucijskaPovijestDao.queryBuilder().orderBy("vrijemeStvaranja", false).query();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return List.of();
  }

  public void ubaciElement(String permutacija) {
    try {
      SupstitucijskaPovijest element = new SupstitucijskaPovijest(0, permutacija, new Date());
      izbaciDuplikate(element);
      izbaciVisak();
      supstitucijskaPovijestDao.create(element);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  // Izbaci prethodne elemente za isti ključ
  private void izbaciDuplikate(SupstitucijskaPovijest element) {
    try {
      DeleteBuilder<SupstitucijskaPovijest, String> deleteBuilder =
          supstitucijskaPovijestDao.deleteBuilder();
      deleteBuilder.setWhere(deleteBuilder.where().eq("permutacija", element.getPermutacija()));
      deleteBuilder.delete();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  // Obriši stare elemente, max je "brojElemenata"
  private void izbaciVisak() {
    try {
      List<SupstitucijskaPovijest> elementi =
          supstitucijskaPovijestDao.queryBuilder().orderBy("vrijemeStvaranja", false).query();

      if (elementi.size() >= brojElemenata) {
        DeleteBuilder<SupstitucijskaPovijest, String> deleteBuilder =
            supstitucijskaPovijestDao.deleteBuilder();
        deleteBuilder.setWhere(
            deleteBuilder
                .where()
                .le("vrijemeStvaranja", elementi.get(brojElemenata - 1).getVrijemeStvaranja()));
        deleteBuilder.delete();
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }
}
