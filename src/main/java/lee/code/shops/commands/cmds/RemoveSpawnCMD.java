package lee.code.shops.commands.cmds;

import lee.code.shops.Shops;
import lee.code.shops.commands.SubCommand;
import lee.code.shops.database.cache.data.ShopSpawnData;
import lee.code.shops.lang.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RemoveSpawnCMD extends SubCommand {
  private final Shops shops;

  public RemoveSpawnCMD(Shops shops) {
    this.shops = shops;
  }

  @Override
  public String getName() {
    return "removespawn";
  }

  @Override
  public String getDescription() {
    return "Remove your player shop spawn.";
  }

  @Override
  public String getSyntax() {
    return "/shop removespawn";
  }

  @Override
  public String getPermission() {
    return "shops.command.removespawn";
  }

  @Override
  public boolean performAsync() {
    return true;
  }

  @Override
  public boolean performAsyncSynchronized() {
    return false;
  }

  @Override
  public void perform(Player player, String[] args) {
    final ShopSpawnData shopSpawnData = shops.getCacheManager().getCachePlayers().getShopSpawnData();
    final UUID playerID = player.getUniqueId();
    if (!shopSpawnData.hasSpawn(playerID)) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_REMOVE_SPAWN_NO_SPAWN.getComponent(null)));
      return;
    }
    shopSpawnData.removeSpawn(playerID);
    player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_REMOVE_SPAWN_SUCCESS.getComponent(null)));
  }

  @Override
  public void performConsole(CommandSender console, String[] args) {
    console.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
  }

  @Override
  public void performSender(CommandSender sender, String[] args) {
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, String[] args) {
    return new ArrayList<>();
  }
}
