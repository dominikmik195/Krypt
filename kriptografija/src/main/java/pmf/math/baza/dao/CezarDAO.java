package pmf.math.baza.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.table.TableUtils;
import pmf.math.baza.tablice.CezarPovijest;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static pmf.math.baza.BazaPodataka.poveznicaBaze;

public class CezarDAO {
  public final int brojElemenata = 5;
  private Dao<CezarPovijest, String> cezarPovijestDao;

  public CezarDAO() {
    try {
      cezarPovijestDao = DaoManager.createDao(poveznicaBaze, CezarPovijest.class);
      TableUtils.createTableIfNotExists(poveznicaBaze, CezarPovijest.class);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public int brojElemenata() {
    try {
      return (int) cezarPovijestDao.countOf();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }

  public CezarPovijest dohvatiElement(int indeks) {
    if (brojElemenata() <= indeks) {
      return null;
    }
    try {
      return cezarPovijestDao.queryBuilder().query().get(indeks);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public void ubaciElement(int pomak, String kljucnaRijec) {
    try {
      CezarPovijest element = new CezarPovijest(0, pomak, kljucnaRijec, new Date());
      izbaciDuplikate(element);
      izbaciVisak();
      cezarPovijestDao.create(element);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  // Izbaci prethodne elemente za isti ključ
  private void izbaciDuplikate(CezarPovijest element) {
    try {
      DeleteBuilder<CezarPovijest, String> deleteBuilder = cezarPovijestDao.deleteBuilder();
      deleteBuilder.setWhere(
          deleteBuilder
              .where()
              .eq("pomak", element.getPomak())
              .and()
              .eq("kljucnaRijec", element.getKljucnaRijec()));
      deleteBuilder.delete();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  // Obriši stare elemente, max je "brojElemenata"
  private void izbaciVisak() {
    try {
      List<CezarPovijest> elementi =
          cezarPovijestDao.queryBuilder().orderBy("vrijemeStvaranja", false).query();

      if (elementi.size() >= brojElemenata) {
        DeleteBuilder<CezarPovijest, String> deleteBuilder = cezarPovijestDao.deleteBuilder();
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
