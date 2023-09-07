package lee.code.shops.menus.menu;

import lee.code.shops.lang.Lang;
import lee.code.shops.menus.system.MenuButton;
import lee.code.shops.menus.system.MenuGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ShopSignItemPreviewMenu extends MenuGUI {
  private final ItemStack previewItem;

  public ShopSignItemPreviewMenu(ItemStack previewItem) {
    this.previewItem = previewItem;
    setInventory();
  }

  @Override
  protected Inventory createInventory() {
    return Bukkit.createInventory(null, 27, Lang.MENU_TITLE_SHOP_SIGN_PREVIEW.getComponent(null));
  }

  @Override
  public void decorate(Player player) {
    addFillerGlass();
    addButton(13, new MenuButton().creator(p -> previewItem).consumer(e->{}));
    super.decorate(player);
  }
}
