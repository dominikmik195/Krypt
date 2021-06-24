package pmf.math.baza.dao;

import static pmf.math.baza.BazaPodataka.poveznicaBaze;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import pmf.math.baza.tablice.HillPovijest;

public class HillDAO {

  private Dao<HillPovijest, String> hillDao;
  public final int maxBrojElemenata = 10;

  public HillDAO() {
    try {
      hillDao = DaoManager.createDao(poveznicaBaze, HillPovijest.class);
      TableUtils.createTableIfNotExists(poveznicaBaze, HillPovijest.class);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public int brojElemenata() {
    try {
      return (int) hillDao.countOf();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return 0;
  }

  public String[][] dohvatiElement(int indeks) {
    if (brojElemenata() <= indeks) {
      return null;
    }
    try {
      HillPovijest element = hillDao.queryBuilder()
          .orderBy("vrijemeStvaranja", false).query()
          .get(indeks);
      return stringToTablica(element.getKljuc());
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return null;
  }

  public void ubaciElement(String[][] podaci) {
    try {
      String kljuc = tablicaToString(podaci);
      HillPovijest element = new HillPovijest(0, kljuc, new Date());
      izbaciDuplikate(element);
      izbaciVisak();
      hillDao.create(element);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  // Izbaci prethodne elemente za isti ključ
  private void izbaciDuplikate(HillPovijest element) {
    try {
      DeleteBuilder<HillPovijest, String> deleteBuilder = hillDao.deleteBuilder();
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
      List<HillPovijest> elementi = hillDao.queryBuilder()
          .orderBy("vrijemeStvaranja", false).query();

      if (elementi.size() >= maxBrojElemenata) {
        DeleteBuilder<HillPovijest, String> deleteBuilder = hillDao.deleteBuilder();
        deleteBuilder.setWhere(deleteBuilder.where()
            .le("vrijemeStvaranja", elementi.get(maxBrojElemenata - 1).getVrijemeStvaranja()));
        deleteBuilder.delete();
      }
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }

  // Pretvaranje tablicu u niz znakova odvojenih sa ',' odnosno ';'
  public String tablicaToString(String[][] tablica) {
    StringBuilder izlaz = new StringBuilder();
    Arrays.stream(tablica).forEach(red -> {
      Arrays.stream(red).forEach(element ->
          izlaz.append(element).append(","));
      izlaz.setCharAt(izlaz.length() - 1, ';');
    });
    izlaz.deleteCharAt(izlaz.length() - 1);

    return izlaz.toString();
  }

  // Primjer:
  // a,b,c;d,e,f;g,h,i ->
  //    a b c
  //    d e f
  //    g h i
  public String[][] stringToTablica(String string) {
    try {
      String[] redoviStringa = string.split(";");
      int n = redoviStringa.length;
      String[][] izlaz = new String[n][];
      for (int i = 0; i < n; i++) {
        izlaz[i] = redoviStringa[i].split(",");
      }
      return izlaz;
    } catch (IndexOutOfBoundsException e) {
      e.printStackTrace();
    }
    return null;
  }

  public String[][] intToStringTablica(int[][] podaci) {
    try {
      String[][] izlaz = new String[podaci.length][podaci.length];
      for (int i = 0; i < izlaz.length; i++) {
        for (int j = 0; j < izlaz.length; j++) {
          izlaz[i][j] = String.valueOf(podaci[i][j]);
        }
      }
      return izlaz;
    } catch (IndexOutOfBoundsException e) {
      e.printStackTrace();
    }
    return null;
  }

}
