package lee.code.shops.menus.menu;

import lee.code.shops.Data;
import lee.code.shops.lang.Lang;
import lee.code.shops.menus.menu.menudata.shop.ShopMainMenuItem;
import lee.code.shops.menus.system.MenuButton;
import lee.code.shops.menus.system.MenuGUI;
import lee.code.shops.menus.system.MenuManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ShopMainMenu extends MenuGUI {
  private final MenuManager menuManager;
  private final Data data;

  public ShopMainMenu(MenuManager menuManager, Data data) {
    this.menuManager = menuManager;
    this.data = data;
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
    super.decorate(player);
  }

  private MenuButton createButton(Player player, ShopMainMenuItem shopMainMenuItem) {
    return new MenuButton()
      .creator(p-> shopMainMenuItem.createItem())
      .consumer(e -> {
        menuManager.openMenu(new ShopCategoryMenu(menuManager, data, shopMainMenuItem.getRout(), 0), player);
      });
  }
}
