package lee.code.shops.commands.cmds;

import lee.code.shops.Shops;
import lee.code.shops.commands.SubCommand;
import lee.code.shops.lang.Lang;
import lee.code.shops.menus.menu.ShopPlayerMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerShopsCMD extends SubCommand {
  private final Shops shops;

  public PlayerShopsCMD(Shops shops) {
    this.shops = shops;
  }

  @Override
  public String getName() {
    return "playershops";
  }

  @Override
  public String getDescription() {
    return "Menu list of player shops.";
  }

  @Override
  public String getSyntax() {
    return "/shop playershops";
  }

  @Override
  public String getPermission() {
    return "shops.command.playershops";
  }

  @Override
  public boolean performAsync() {
    return false;
  }

  @Override
  public boolean performAsyncSynchronized() {
    return false;
  }

  @Override
  public void perform(Player player, String[] args) {
    shops.getMenuManager().openMenu(new ShopPlayerMenu(shops.getCacheManager().getCachePlayers().getShopSpawnData()), player);
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
