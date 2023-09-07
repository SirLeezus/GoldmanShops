package lee.code.shops.database.cache;

import lee.code.shops.database.DatabaseManager;
import lee.code.shops.database.cache.data.ShopSpawnData;
import lee.code.shops.database.handlers.DatabaseHandler;
import lee.code.shops.database.tables.PlayerTable;
import lee.code.shops.utils.CoreUtil;
import lombok.Getter;
import org.bukkit.Location;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CachePlayers extends DatabaseHandler {
  @Getter private final ShopSpawnData shopSpawnData;
  private final ConcurrentHashMap<UUID, PlayerTable> playersCache = new ConcurrentHashMap<>();

  public CachePlayers(DatabaseManager databaseManager) {
    super(databaseManager);
    this.shopSpawnData = new ShopSpawnData(this);
  }

  public PlayerTable getPlayerTable(UUID uuid) {
    return playersCache.get(uuid);
  }

  public void setPlayerTable(PlayerTable playerTable) {
    playersCache.put(playerTable.getUniqueId(), playerTable);
  }

  public boolean hasPlayerData(UUID uuid) {
    return playersCache.containsKey(uuid);
  }

  public void createPlayerData(UUID uuid) {
    final PlayerTable playerTable = new PlayerTable(uuid);
    setPlayerTable(playerTable);
    createPlayerDatabase(playerTable);
  }
}
