package lee.code.shops.commands.cmds;

import lee.code.shops.Shops;
import lee.code.shops.commands.SubCommand;
import lee.code.shops.lang.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SetSpawnCMD extends SubCommand {
  private final Shops shops;

  public SetSpawnCMD(Shops shops) {
    this.shops = shops;
  }

  @Override
  public String getName() {
    return "setspawn";
  }

  @Override
  public String getDescription() {
    return "Set your shop spawn.";
  }

  @Override
  public String getSyntax() {
    return "/shop setspawn";
  }

  @Override
  public String getPermission() {
    return "shops.command.setspawn";
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
    shops.getCacheManager().getCachePlayers().setShopSpawn(player.getUniqueId(), player.getLocation());
    player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_SET_SPAWN_SUCCESS.getComponent(null)));
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
