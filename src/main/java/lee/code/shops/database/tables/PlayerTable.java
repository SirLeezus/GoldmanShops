package lee.code.shops.database.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@DatabaseTable(tableName = "players")
public class PlayerTable {
  @DatabaseField(id = true, canBeNull = false)
  private UUID uniqueId;

  @DatabaseField(columnName = "shop_spawn")
  private String shopSpawn;

  @DatabaseField(columnName = "notifications", canBeNull = false)
  private boolean notifications;

  @DatabaseField(columnName = "admin_bypass", canBeNull = false)
  private boolean adminBypass;

  public PlayerTable(UUID uniqueId) {
    this.uniqueId = uniqueId;
    this.notifications = true;
    this.adminBypass = false;
  }
}
