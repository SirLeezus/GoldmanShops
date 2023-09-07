package lee.code.shops.database;

import lee.code.shops.Shops;
import lee.code.shops.database.cache.CachePlayers;
import lombok.Getter;

public class CacheManager {
  private final Shops shops;
  @Getter private final CachePlayers cachePlayers;

  public CacheManager(Shops shops, DatabaseManager databaseManager) {
    this.shops = shops;
    this.cachePlayers = new CachePlayers(databaseManager);
  }
}
