package lee.code.shops.commands.cmds;

import lee.code.colors.ColorAPI;
import lee.code.playerdata.PlayerDataAPI;
import lee.code.shops.Shops;
import lee.code.shops.commands.SubCommand;
import lee.code.shops.database.cache.data.ShopSpawnData;
import lee.code.shops.lang.Lang;
import lee.code.shops.utils.CoreUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SpawnCMD extends SubCommand {
  private final Shops shops;

  public SpawnCMD(Shops shops) {
    this.shops = shops;
  }

  @Override
  public String getName() {
    return "spawn";
  }

  @Override
  public String getDescription() {
    return "Teleport to a target player shop.";
  }

  @Override
  public String getSyntax() {
    return "/shop spawn &f<player>";
  }

  @Override
  public String getPermission() {
    return "shops.command.spawn";
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
    if (args.length < 2) {
      player.sendMessage(Lang.USAGE.getComponent(new String[]{getSyntax()}));
      return;
    }
    final String targetString = args[1];
    final UUID targetID = PlayerDataAPI.getUniqueId(targetString);
    if (targetID == null) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NO_PLAYER_DATA.getComponent(new String[]{targetString})));
      return;
    }
    final ShopSpawnData shopSpawnData = shops.getCacheManager().getCachePlayers().getShopSpawnData();
    final String targetName = ColorAPI.getNameColor(targetID, targetString);
    if (!shopSpawnData.hasSpawn(targetID)) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_SPAWN_NO_SPAWN.getComponent(new String[]{targetName})));
      return;
    }
    CoreUtil.teleportShop(player, shopSpawnData.getSpawn(targetID), targetName);
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
    if (args.length == 2) return StringUtil.copyPartialMatches(args[1], CoreUtil.getOnlinePlayers(), new ArrayList<>());
    return new ArrayList<>();
  }
}
