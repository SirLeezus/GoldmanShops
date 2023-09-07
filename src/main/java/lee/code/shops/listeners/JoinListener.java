package lee.code.shops.listeners;

import lee.code.shops.Shops;
import lee.code.shops.database.cache.CachePlayers;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class JoinListener implements Listener {
  private final Shops shops;

  public JoinListener(Shops shops) {
    this.shops = shops;
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent e) {
    final UUID playerID = e.getPlayer().getUniqueId();
    final CachePlayers cachePlayers = shops.getCacheManager().getCachePlayers();
    if (!cachePlayers.hasPlayerData(playerID)) cachePlayers.createPlayerData(playerID);
  }
}
