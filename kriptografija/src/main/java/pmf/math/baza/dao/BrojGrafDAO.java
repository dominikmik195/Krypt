package pmf.math.baza.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.table.TableUtils;
import pmf.math.baza.tablice.BrojGrafovi;
import pmf.math.konstante.ImenaKalkulatora;
import pmf.math.kriptosustavi.ElGamalKriptosustav;
import pmf.math.kriptosustavi.RSAKriptosustav;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import static pmf.math.baza.BazaPodataka.poveznicaBaze;
import static pmf.math.pomagala.StringInteger.intRedUString;

public class BrojGrafDAO {
    private Dao<BrojGrafovi, String> brojGrafDao;
    public final int brojIteracijaSimulacije = 10;
    public static final int maxBrojZnamenakaRSA = 8;
    public static final int maxBrojZnamenakaEG = 5;

    public enum VrstaSimulacije {
        SIFRIRAJ,
        DESIFRIRAJ
    }

    public String vrstaSimulacijeToString(BrojGrafDAO.VrstaSimulacije vrstaSimulacije) {
        return switch (vrstaSimulacije) {
            case SIFRIRAJ -> "Šifriranje";
            case DESIFRIRAJ -> "Dešifriranje";
        };
    }

    public BrojGrafDAO() {
        try {
            brojGrafDao = DaoManager.createDao(poveznicaBaze, BrojGrafovi.class);
            TableUtils.createTableIfNotExists(poveznicaBaze, BrojGrafovi.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public BrojGrafovi dohvatiElement(ImenaKalkulatora imeKalkulatora,
                                       BrojGrafDAO.VrstaSimulacije vrstaSimulacije, boolean osvjezi) {
        try {
            List<BrojGrafovi> brojGraf = brojGrafDao.queryBuilder().where()
                    .eq("imeKalkulatora", imeKalkulatora.toString()).and()
                    .eq("vrstaSimulacije", vrstaSimulacijeToString(vrstaSimulacije)).query();
            if (brojGraf.isEmpty()) {
                BrojGrafovi element = noviElement(imeKalkulatora, vrstaSimulacije);
                brojGrafDao.create(element);
                return element;
            } else if(osvjezi) {
                BrojGrafovi element = noviElement(imeKalkulatora, vrstaSimulacije);
                brojGraf.get(0).setVremenaIzvodenja(element.getVremenaIzvodenja());
                brojGrafDao.update(brojGraf.get(0));
                return element;
            } else {
                return brojGraf.get(0);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
        }

    public BrojGrafovi noviElement(ImenaKalkulatora imeKalkulatora,
                                    BrojGrafDAO.VrstaSimulacije vrstaSimulacije) {
        String vremenaIzvodenja = switch (imeKalkulatora) {
            case RSA_SIFRA -> intRedUString(Objects.requireNonNull(
                    RSAKriptosustav.simuliraj(vrstaSimulacije, brojIteracijaSimulacije, maxBrojZnamenakaRSA)));

            case EL_GAMALOVA_SIFRA -> intRedUString(Objects.requireNonNull(
                    ElGamalKriptosustav.simuliraj(vrstaSimulacije, brojIteracijaSimulacije, maxBrojZnamenakaEG)));

            default -> throw new IllegalStateException("Neočekivana vrijednost: " + imeKalkulatora);
        };
        return new BrojGrafovi(0, imeKalkulatora.toString(), vrstaSimulacijeToString(vrstaSimulacije),
                vremenaIzvodenja);
    }
}
