package pmf.math.baza.dao;

import static pmf.math.baza.BazaPodataka.poveznicaBaze;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.table.TableUtils;
import java.util.Date;
import java.sql.SQLException;
import java.util.List;
import pmf.math.baza.tablice.PlayfairFavoriti;
import pmf.math.kriptosustavi.PlayfairKriptosustav.Jezik;

public class PlayfairDAO {

  private Dao<PlayfairFavoriti, String> playfairDao;
  public final int brojFavorita = 5;

  public PlayfairDAO() {
    try {
      playfairDao = DaoManager.createDao(poveznicaBaze, PlayfairFavoriti.class);
      TableUtils.createTableIfNotExists(poveznicaBaze, PlayfairFavoriti.class);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public List<PlayfairFavoriti> dohvatiFavorite() {
    try {
      return playfairDao.queryBuilder().orderBy("vrijemeStvaranja", false).query();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return List.of();
  }

  public void ubaciFavorit(String kljuc, Jezik jezik) {
    if(kljuc.equals("")) return;
    try {
      PlayfairFavoriti favorit = new PlayfairFavoriti(0, kljuc, jezik.ordinal(), new Date());
      izbaciDuplikate(favorit);
      izbaciVisak();
      playfairDao.create(favorit);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  // Izbaci prethodne favorite za isti ključ
  private void izbaciDuplikate(PlayfairFavoriti favorit) {
    try {
      DeleteBuilder<PlayfairFavoriti, String> deleteBuilder = playfairDao.deleteBuilder();
      deleteBuilder.setWhere(deleteBuilder.where()
          .eq("kljuc", favorit.getKljuc()));
      deleteBuilder.delete();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  // Obriši stare favorite, max je "brojFavorita"
  private void izbaciVisak() {
    try {
      List<PlayfairFavoriti> favoriti = playfairDao.queryBuilder()
          .orderBy("vrijemeStvaranja", false).query();

      if(favoriti.size() >= brojFavorita) {
        DeleteBuilder<PlayfairFavoriti, String> deleteBuilder = playfairDao.deleteBuilder();
        deleteBuilder.setWhere(deleteBuilder.where()
            .le("vrijemeStvaranja", favoriti.get(brojFavorita - 1).getVrijemeStvaranja()));
        deleteBuilder.delete();
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

}
