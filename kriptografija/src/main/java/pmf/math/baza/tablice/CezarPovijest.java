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
@DatabaseTable(tableName = "cezarPovijest")
public class CezarPovijest {

  @DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
  int id;

  @DatabaseField int pomak;

  @DatabaseField String kljucnaRijec;

  @DatabaseField Date vrijemeStvaranja;
}
