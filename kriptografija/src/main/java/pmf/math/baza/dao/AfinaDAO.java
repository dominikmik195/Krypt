package pmf.math.baza.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.table.TableUtils;
import pmf.math.baza.tablice.AfinaPovijest;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static pmf.math.baza.BazaPodataka.poveznicaBaze;

public class AfinaDAO {

  private Dao<AfinaPovijest, String> afinaPovijestDao;
  public final int brojElemenata = 5;

  public AfinaDAO() {
    try {
      afinaPovijestDao = DaoManager.createDao(poveznicaBaze, AfinaPovijest.class);
      TableUtils.createTableIfNotExists(poveznicaBaze, AfinaPovijest.class);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public List<AfinaPovijest> dohvatiElemente() {
    try {
      return afinaPovijestDao.queryBuilder().orderBy("vrijemeStvaranja", false).query();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return List.of();
  }

  public void ubaciElement(int a, int b) {
    try {
      AfinaPovijest element = new AfinaPovijest(0, a, b, new Date());
      izbaciDuplikate(element);
      izbaciVisak();
      afinaPovijestDao.create(element);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  // Izbaci prethodne elemente za isti ključ
  private void izbaciDuplikate(AfinaPovijest element) {
    try {
      DeleteBuilder<AfinaPovijest, String> deleteBuilder = afinaPovijestDao.deleteBuilder();
      deleteBuilder.setWhere(
          deleteBuilder.where().eq("a", element.getA()).and().eq("b", element.getB()));
      deleteBuilder.delete();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  // Obriši stare elemente, max je "brojElemenata"
  private void izbaciVisak() {
    try {
      List<AfinaPovijest> elementi =
          afinaPovijestDao.queryBuilder().orderBy("vrijemeStvaranja", false).query();

      if (elementi.size() >= brojElemenata) {
        DeleteBuilder<AfinaPovijest, String> deleteBuilder = afinaPovijestDao.deleteBuilder();
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
