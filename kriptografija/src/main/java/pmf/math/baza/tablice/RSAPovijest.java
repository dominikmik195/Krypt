package pmf.math.baza.tablice;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@DatabaseTable(tableName = "elgamal")
public class RSAPovijest {

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
    int id;

    @DatabaseField
    int p;

    @DatabaseField
    int q;

    @DatabaseField
    int n;

    @DatabaseField
    int d;

    @DatabaseField
    int e;

    @DatabaseField
    Date vrijemeStvaranja;
}
