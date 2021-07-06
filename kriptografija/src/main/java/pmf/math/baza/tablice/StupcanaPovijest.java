package pmf.math.baza.tablice;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@DatabaseTable(tableName = "stupcana")
public class StupcanaPovijest {
    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
    int id;

    @DatabaseField
    String kljuc;

    @DatabaseField
    Date vrijemeStvaranja;
}
