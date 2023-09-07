package lee.code.shops.commands.cmds;

import lee.code.colors.ColorAPI;
import lee.code.playerdata.PlayerDataAPI;
import lee.code.shops.Shops;
import lee.code.shops.commands.SubCommand;
import lee.code.shops.commands.SubSyntax;
import lee.code.shops.database.cache.CachePlayers;
import lee.code.shops.lang.Lang;
import lee.code.shops.utils.CoreUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AdminCMD extends SubCommand {
  private final Shops shops;

  public AdminCMD(Shops shops) {
    this.shops = shops;
  }

  @Override
  public String getName() {
    return "admin";
  }

  @Override
  public String getDescription() {
    return "Admin commands for player shops.";
  }

  @Override
  public String getSyntax() {
    return "/shop admin <removespawn/bypass>";
  }

  @Override
  public String getPermission() {
    return "shops.command.admin";
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
    final CachePlayers cachePlayers = shops.getCacheManager().getCachePlayers();
    final String option = args[1].toLowerCase();
    final UUID playerID = player.getUniqueId();
    switch (option) {
      case "bypass" -> {
        final boolean result = !cachePlayers.hasAdminBypass(playerID);
        cachePlayers.setAdminBypass(playerID, result);
        final String resultString = result ? Lang.ON.getString() : Lang.OFF.getString();
        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_ADMIN_BYPASS_SUCCESS.getComponent(new String[]{resultString})));
      }
      case "removespawn" -> {
        if (args.length < 3) {
          player.sendMessage(Lang.USAGE.getComponent(new String[]{SubSyntax.COMMAND_ADMIN_REMOVE_SPAWN_SYNTAX.getString()}));
          return;
        }
        final String targetString = args[2];
        final UUID targetID = PlayerDataAPI.getUniqueId(targetString);
        if (targetID == null) {
          player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NO_PLAYER_DATA.getComponent(new String[]{targetString})));
          return;
        }
        final String targetName = ColorAPI.getNameColor(targetID, targetString);
        if (!cachePlayers.getShopSpawnData().hasSpawn(targetID)) {
          player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_ADMIN_REMOVE_SPAWN_NO_SPAWN.getComponent(new String[]{targetName})));
          return;
        }
        cachePlayers.getShopSpawnData().removeSpawn(targetID);
        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_ADMIN_REMOVE_SPAWN_SUCCESS.getComponent(new String[]{targetName})));
      }
    }
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
    if (args.length == 3) return StringUtil.copyPartialMatches(args[2], CoreUtil.getOnlinePlayers(), new ArrayList<>());
    return new ArrayList<>();
  }
}
