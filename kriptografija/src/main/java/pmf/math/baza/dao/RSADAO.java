package pmf.math.baza.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.table.TableUtils;
import pmf.math.baza.tablice.RSAPovijest;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static pmf.math.baza.BazaPodataka.poveznicaBaze;

public class RSADAO {
    private Dao<RSAPovijest, String> RSADao;
    public final int maxBrojElemenata = 10;

    public RSADAO() {
        try {
            RSADao = DaoManager.createDao(poveznicaBaze, RSAPovijest.class);
            TableUtils.createTableIfNotExists(poveznicaBaze, RSAPovijest.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int brojElemenata() {
        try {
            return (int) RSADao.countOf();
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
            RSAPovijest element =
                    RSADao.queryBuilder().orderBy("vrijemeStvaranja", false).query().get(indeks);
            return new Integer[] {
                    element.getP(),
                    element.getQ(),
                    element.getN(),
                    element.getD(),
                    element.getE()
            };
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void ubaciElement(Integer[] brojevi) {
        try {
            RSAPovijest element =
                    new RSAPovijest(
                            0, brojevi[0], brojevi[1], brojevi[2], brojevi[3], brojevi[4], new Date());
            izbaciDuplikate(element);
            izbaciVisak();
            RSADao.create(element);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void izbaciDuplikate(RSAPovijest element) {
        try {
            DeleteBuilder<RSAPovijest, String> deleteBuilder = RSADao.deleteBuilder();
            deleteBuilder.setWhere(
                    deleteBuilder
                            .where()
                            .eq("p", element.getP()).and()
                            .eq("q", element.getQ()).and()
                            .eq("n", element.getN()).and()
                            .eq("d", element.getD()).and()
                            .eq("e", element.getE()));
            deleteBuilder.delete();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void izbaciVisak() {
        try {
            List<RSAPovijest> elementi =
                    RSADao.queryBuilder().orderBy("vrijemeStvaranja", false).query();

            if (elementi.size() >= maxBrojElemenata) {
                DeleteBuilder<RSAPovijest, String> deleteBuilder = RSADao.deleteBuilder();
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
