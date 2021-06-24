package pmf.math.baza.dao;

import static pmf.math.baza.BazaPodataka.poveznicaBaze;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import pmf.math.baza.tablice.VigenerePovijest;

public class VigenereDAO {


  private Dao<VigenerePovijest, String> vigenereDao;
  public final int maxBrojElemenata = 5;

  public VigenereDAO() {
    try {
      vigenereDao = DaoManager.createDao(poveznicaBaze, VigenerePovijest.class);
      TableUtils.createTableIfNotExists(poveznicaBaze, VigenerePovijest.class);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public int brojElemenata() {
    try {
      return (int) vigenereDao.countOf();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return 0;
  }

  public List<VigenerePovijest> dohvatiElemente() {
    try {
      return vigenereDao.queryBuilder().orderBy("vrijemeStvaranja", false).query();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return List.of();
  }

  public void ubaciElement(String kljuc) {
    if (kljuc.equals("")) {
      return;
    }
    try {
      VigenerePovijest element = new VigenerePovijest(0, kljuc, new Date());
      izbaciDuplikate(element);
      izbaciVisak();
      vigenereDao.create(element);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }


  // Izbaci prethodne elemente za isti ključ
  private void izbaciDuplikate(VigenerePovijest element) {
    try {
      DeleteBuilder<VigenerePovijest, String> deleteBuilder = vigenereDao.deleteBuilder();
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
      List<VigenerePovijest> elementi = vigenereDao.queryBuilder()
          .orderBy("vrijemeStvaranja", false).query();

      if (elementi.size() >= maxBrojElemenata) {
        DeleteBuilder<VigenerePovijest, String> deleteBuilder = vigenereDao.deleteBuilder();
        deleteBuilder.setWhere(deleteBuilder.where()
            .le("vrijemeStvaranja", elementi.get(maxBrojElemenata - 1).getVrijemeStvaranja()));
        deleteBuilder.delete();
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }


}
