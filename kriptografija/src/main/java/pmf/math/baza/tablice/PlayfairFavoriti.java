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
@DatabaseTable(tableName = "playfair")
public class PlayfairFavoriti {

  @DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
  int id;

  @DatabaseField
  String kljuc;

  @DatabaseField()
  int jezik;

  @DatabaseField
  Date vrijemeStvaranja;

}
