package lee.code.shops.menus.menu.menudata.shop;

import lee.code.shops.lang.Lang;
import lee.code.shops.utils.ItemUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public enum ShopMainMenuItem {
  BASIC_BLOCK(Material.TERRACOTTA, "&e&lBasic Block Category", 1, Rout.BASIC_BLOCK),
  TOOL(Material.DIAMOND_AXE, "&e&lTool Category", 2, Rout.TOOL),
  CROP(Material.BEETROOT, "&e&lCrop Category", 3, Rout.CROP),
  WOOD(Material.OAK_LOG, "&e&lWood Category", 4, Rout.WOOD),
  DYE(Material.RED_DYE, "&e&lDye Category", 5, Rout.DYE),
  VEHICLE(Material.MINECART, "&e&lVehicle Category", 6, Rout.VEHICLE),
  ARMOR(Material.DIAMOND_CHESTPLATE, "&e&lArmor Category", 7, Rout.ARMOR),

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
