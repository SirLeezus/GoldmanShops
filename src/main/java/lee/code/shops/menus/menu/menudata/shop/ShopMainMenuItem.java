package lee.code.shops.menus.menu.menudata.shop;

import lee.code.shops.lang.Lang;
import lee.code.shops.utils.ItemUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public enum ShopMainMenuItem {
  RED_STONE(Material.REDSTONE, "&c&lRestone Category", 0, Rout.REDSTONE),
  BLOCKS(Material.BRICK, "&9&lBlock Category", 1, Rout.BLOCKS),
  TOOLS(Material.DIAMOND_AXE, "&d&lTool Category", 2, Rout.TOOLS),

  ;

  private final Material material;
  private final String name;
  @Getter private final int slot;
  @Getter private final Rout rout;

  public ItemStack createItem() {
    final ItemStack item = ItemUtil.createItem(material, name, Lang.MENU_SHOP_CATEGORY_ITEM_LORE.getString(null), 0, null);
    ItemUtil.hideItemFlags(item);
    return item;
  }
}
