package lee.code.shops.menus.menu;

import lee.code.shops.Shops;
import lee.code.shops.lang.Lang;
import lee.code.shops.menus.menu.menudata.MenuItem;
import lee.code.shops.menus.menu.menudata.shop.ShopMainMenuItem;
import lee.code.shops.menus.system.MenuButton;
import lee.code.shops.menus.system.MenuGUI;
import lee.code.shops.menus.system.MenuManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ShopMainMenu extends MenuGUI {
  private final MenuManager menuManager;
  private final Shops shops;

  public ShopMainMenu(MenuManager menuManager, Shops shops) {
    this.menuManager = menuManager;
    this.shops = shops;
    setInventory();
  }

  @Override
  protected Inventory createInventory() {
    return Bukkit.createInventory(null, 54, Lang.MENU_TITLE_SHOP.getComponent(null));
  }

  @Override
  public void decorate(Player player) {
    addFillerGlass();
    for (ShopMainMenuItem shopMainMenuItem : ShopMainMenuItem.values()) {
      addButton(shopMainMenuItem.getSlot(), createButton(player, shopMainMenuItem));
    }
    addPlayerShopsButton(player);
    super.decorate(player);
  }

  private MenuButton createButton(Player player, ShopMainMenuItem shopMainMenuItem) {
    return new MenuButton()
      .creator(p-> shopMainMenuItem.createItem())
      .consumer(e -> {
        menuManager.openMenu(new ShopCategoryMenu(menuManager, shops, shopMainMenuItem.getRout(), 0), player);
      });
  }

  private void addPlayerShopsButton(Player player) {
    addButton(49, new MenuButton()
      .creator(p -> MenuItem.PLAYER_SHOPS_MENU.createItem())
      .consumer(e -> {
        menuManager.openMenu(new ShopPlayerMenu(shops.getCacheManager().getCachePlayers().getShopSpawnData()), player);
      }));
  }
}
