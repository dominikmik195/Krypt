package pmf.math.baza.dao;

import static pmf.math.baza.BazaPodataka.poveznicaBaze;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.table.TableUtils;
import java.util.Date;
import java.sql.SQLException;
import java.util.List;
import pmf.math.baza.tablice.PlayfairPovijest;
import pmf.math.kriptosustavi.PlayfairKriptosustav.Jezik;

public class PlayfairDAO {

  private Dao<PlayfairPovijest, String> playfairDao;
  public final int brojElemenata = 5;

  public PlayfairDAO() {
    try {
      playfairDao = DaoManager.createDao(poveznicaBaze, PlayfairPovijest.class);
      TableUtils.createTableIfNotExists(poveznicaBaze, PlayfairPovijest.class);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public List<PlayfairPovijest> dohvatiElemente() {
    try {
      return playfairDao.queryBuilder().orderBy("vrijemeStvaranja", false).query();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return List.of();
  }

  public void ubaciElement(String kljuc, Jezik jezik) {
    if(kljuc.equals("")) return;
    try {
      PlayfairPovijest element = new PlayfairPovijest(0, kljuc, jezik.ordinal(), new Date());
      izbaciDuplikate(element);
      izbaciVisak();
      playfairDao.create(element);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  // Izbaci prethodne elemente za isti ključ
  private void izbaciDuplikate(PlayfairPovijest element) {
    try {
      DeleteBuilder<PlayfairPovijest, String> deleteBuilder = playfairDao.deleteBuilder();
      deleteBuilder.setWhere(deleteBuilder.where()
          .eq("kljuc", element.getKljuc()));
      deleteBuilder.delete();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  // Obriši stare elemente, max je "brojElemenata"
  private void izbaciVisak() {
    try {
      List<PlayfairPovijest> elementi = playfairDao.queryBuilder()
          .orderBy("vrijemeStvaranja", false).query();

      if(elementi.size() >= brojElemenata) {
        DeleteBuilder<PlayfairPovijest, String> deleteBuilder = playfairDao.deleteBuilder();
        deleteBuilder.setWhere(deleteBuilder.where()
            .le("vrijemeStvaranja", elementi.get(brojElemenata - 1).getVrijemeStvaranja()));
        deleteBuilder.delete();
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

}
