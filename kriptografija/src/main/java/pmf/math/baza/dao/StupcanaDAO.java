package pmf.math.baza.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.table.TableUtils;
import pmf.math.baza.tablice.StupcanaPovijest;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static pmf.math.baza.BazaPodataka.poveznicaBaze;

public class StupcanaDAO {
    private Dao<StupcanaPovijest, String> stupcanaDao;
    public final int maxBrojElemenata = 5;

    public StupcanaDAO() {
        try {
            stupcanaDao = DaoManager.createDao(poveznicaBaze, StupcanaPovijest.class);
            TableUtils.createTableIfNotExists(poveznicaBaze, StupcanaPovijest.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int brojElemenata() {
        try {
            return (int) stupcanaDao.countOf();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public List<StupcanaPovijest> dohvatiElemente() {
        try {
            return stupcanaDao.queryBuilder().orderBy("vrijemeStvaranja", false).query();
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
            StupcanaPovijest element = new StupcanaPovijest(0, kljuc, new Date());
            izbaciDuplikate(element);
            izbaciVisak();
            stupcanaDao.create(element);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    // Izbaci prethodne elemente za isti ključ
    private void izbaciDuplikate(StupcanaPovijest element) {
        try {
            DeleteBuilder<StupcanaPovijest, String> deleteBuilder = stupcanaDao.deleteBuilder();
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
            List<StupcanaPovijest> elementi = stupcanaDao.queryBuilder()
                    .orderBy("vrijemeStvaranja", false).query();

            if (elementi.size() >= maxBrojElemenata) {
                DeleteBuilder<StupcanaPovijest, String> deleteBuilder = stupcanaDao.deleteBuilder();
                deleteBuilder.setWhere(deleteBuilder.where()
                        .le("vrijemeStvaranja", elementi.get(maxBrojElemenata - 1).getVrijemeStvaranja()));
                deleteBuilder.delete();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
