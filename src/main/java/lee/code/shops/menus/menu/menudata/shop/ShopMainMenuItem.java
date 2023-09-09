package lee.code.shops.menus.menu.menudata.shop;

import lee.code.shops.lang.Lang;
import lee.code.shops.utils.ItemUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public enum ShopMainMenuItem {
  BASIC_BLOCK(Material.TERRACOTTA, "&e&lBasic Block Shop", 10, Rout.BASIC_BLOCK),
  SHEARS(Material.SHEARS, "&e&lTool Shop", 12, Rout.TOOL),
  CROP(Material.CARROT, "&e&lCrop Shop", 14, Rout.CROP),
  WOOD(Material.OAK_LOG, "&e&lWood Shop", 16, Rout.WOOD),
  DYE(Material.BLUE_DYE, "&e&lDye Shop", 28, Rout.DYE),
  VEHICLE(Material.OAK_BOAT, "&e&lVehicle Shop", 30, Rout.VEHICLE),
  ARMOR(Material.DIAMOND_CHESTPLATE, "&e&lArmor Shop", 32, Rout.ARMOR),
  SPAWNER(Material.CREEPER_HEAD, "&e&lSpawner Shop", 34, Rout.SPAWNER),
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
