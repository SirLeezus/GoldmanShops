package lee.code.shops.commands.cmds;

import lee.code.shops.Shops;
import lee.code.shops.commands.SubCommand;
import lee.code.shops.database.cache.CachePlayers;
import lee.code.shops.lang.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NotificationsCMD extends SubCommand {
  private final Shops shops;

  public NotificationsCMD(Shops shops) {
    this.shops = shops;
  }

  @Override
  public String getName() {
    return "notifications";
  }

  @Override
  public String getDescription() {
    return "Toggle being able to see player shop purchase notifications.";
  }

  @Override
  public String getSyntax() {
    return "/shop notifications";
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
    final CachePlayers cachePlayers = shops.getCacheManager().getCachePlayers();
    final UUID playerID = player.getUniqueId();
    final boolean result = !cachePlayers.hasNotificationsOn(playerID);
    cachePlayers.setNotifications(playerID, result);
    final String resultString = result ? Lang.ON.getString() : Lang.OFF.getString();
    player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_NOTIFICATIONS_SUCCESS.getComponent(new String[]{resultString})));
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
