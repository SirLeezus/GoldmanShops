package lee.code.shops.database.cache;

import lee.code.shops.database.DatabaseManager;
import lee.code.shops.database.handlers.DatabaseHandler;
import lee.code.shops.database.tables.PlayerTable;
import lee.code.shops.utils.CoreUtil;
import org.bukkit.Location;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CachePlayers extends DatabaseHandler {
  private final ConcurrentHashMap<UUID, PlayerTable> playersCache = new ConcurrentHashMap<>();

  public CachePlayers(DatabaseManager databaseManager) {
    super(databaseManager);
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

  public boolean hasShopSpawn(UUID uuid) {
    return getPlayerTable(uuid).getShopSpawn() != null;
  }

  public Location getShopSpawn(UUID uuid) {
    return CoreUtil.parseLocation(getPlayerTable(uuid).getShopSpawn());
  }

  public void setShopSpawn(UUID uuid, Location location) {
    final PlayerTable playerTable = getPlayerTable(uuid);
    playerTable.setShopSpawn(CoreUtil.serializeLocation(location));
    updatePlayerDatabase(playerTable);
  }
}
