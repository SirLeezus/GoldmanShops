package lee.code.shops.database.cache.data;

import lee.code.shops.database.cache.CachePlayers;
import lee.code.shops.database.tables.PlayerTable;
import lee.code.shops.utils.CoreUtil;
import org.bukkit.Location;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ShopSpawnData {
  private final CachePlayers cachePlayers;
  private final ConcurrentHashMap<UUID, String> shopSpawnCache = new ConcurrentHashMap<>();

  public ShopSpawnData(CachePlayers cachePlayers) {
    this.cachePlayers = cachePlayers;
  }

  public void cacheShopSpawn(PlayerTable playerTable) {
    if (playerTable.getShopSpawn() == null) return;
    shopSpawnCache.put(playerTable.getUniqueId(), playerTable.getShopSpawn());
  }

  public void setSpawn(UUID uuid, Location location) {
    final PlayerTable playerTable = cachePlayers.getPlayerTable(uuid);
    playerTable.setShopSpawn(CoreUtil.serializeLocation(location));
    cacheShopSpawn(playerTable);
    cachePlayers.updatePlayerDatabase(playerTable);
  }

  public void removeSpawn(UUID uuid) {
    final PlayerTable playerTable = cachePlayers.getPlayerTable(uuid);
    playerTable.setShopSpawn(null);
    shopSpawnCache.remove(uuid);
    cachePlayers.updatePlayerDatabase(playerTable);
  }

  public Location getSpawn(UUID uuid) {
    return CoreUtil.parseLocation(shopSpawnCache.get(uuid));
  }

  public boolean hasSpawn(UUID uuid) {
    return shopSpawnCache.containsKey(uuid);
  }

  public ConcurrentHashMap<UUID, String> getAllShopSpawns() {
    return new ConcurrentHashMap<>(shopSpawnCache);
  }
}
