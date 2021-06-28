package pmf.math.baza.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.table.TableUtils;
import pmf.math.baza.tablice.ElGamalPovijest;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static pmf.math.baza.BazaPodataka.poveznicaBaze;

public class ElGamalDAO {
  private Dao<ElGamalPovijest, String> elGamalDao;
  public final int maxBrojElemenata = 10;

  public ElGamalDAO() {
    try {
      elGamalDao = DaoManager.createDao(poveznicaBaze, ElGamalPovijest.class);
      TableUtils.createTableIfNotExists(poveznicaBaze, ElGamalPovijest.class);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public int brojElemenata() {
    try {
      return (int) elGamalDao.countOf();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return 0;
  }

  public Integer[] dohvatiElement(int indeks) {
    if (brojElemenata() <= indeks) {
      return null;
    }
    try {
      ElGamalPovijest element =
          elGamalDao.queryBuilder().orderBy("vrijemeStvaranja", false).query().get(indeks);
      return new Integer[] {
        element.getProstBroj(),
        element.getTajniKljuc(),
        element.getAlfa(),
        element.getBeta(),
        element.getTajniBroj()
      };
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return null;
  }

  public void ubaciElement(Integer[] brojevi) {
    try {
      ElGamalPovijest element =
          new ElGamalPovijest(
              0, brojevi[0], brojevi[1], brojevi[2], brojevi[3], brojevi[4], new Date());
      izbaciDuplikate(element);
      izbaciVisak();
      elGamalDao.create(element);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  public void izbaciDuplikate(ElGamalPovijest element) {
    try {
      DeleteBuilder<ElGamalPovijest, String> deleteBuilder = elGamalDao.deleteBuilder();
      deleteBuilder.setWhere(
          deleteBuilder
              .where()
              .eq("prostBroj", element.getProstBroj()).and()
              .eq("tajniKljuc", element.getTajniKljuc()).and()
              .eq("alfa", element.getAlfa()).and()
              .eq("beta", element.getBeta()).and()
              .eq("tajniBroj", element.getTajniBroj()));
      deleteBuilder.delete();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  public void izbaciVisak() {
    try {
      List<ElGamalPovijest> elementi =
          elGamalDao.queryBuilder().orderBy("vrijemeStvaranja", false).query();

      if (elementi.size() >= maxBrojElemenata) {
        DeleteBuilder<ElGamalPovijest, String> deleteBuilder = elGamalDao.deleteBuilder();
        deleteBuilder.setWhere(
            deleteBuilder
                .where()
                .le("vrijemeStvaranja", elementi.get(maxBrojElemenata - 1).getVrijemeStvaranja()));
        deleteBuilder.delete();
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }
}
