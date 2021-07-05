package pmf.math.baza.tablice;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@DatabaseTable(tableName = "grafovi")
public class BrojGrafovi {
    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
    int id;

    @DatabaseField
    String imeKalkulatora;

    @DatabaseField
    String vrstaSimulacije;

    @DatabaseField
    String vremenaIzvodenja;
}
