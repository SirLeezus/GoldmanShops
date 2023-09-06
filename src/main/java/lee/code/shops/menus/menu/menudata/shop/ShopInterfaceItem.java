package lee.code.shops.menus.menu.menudata.shop;

import lee.code.shops.lang.Lang;
import lee.code.shops.utils.CoreUtil;
import lee.code.shops.utils.ItemUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public enum ShopInterfaceItem {
  BUY_1(Material.LIME_STAINED_GLASS_PANE, "&a&lBuy", 1, 12, ShopInterfaceType.BUY),
  BUY_16(Material.LIME_STAINED_GLASS_PANE, "&a&lBuy", 16, 21, ShopInterfaceType.BUY),
  BUY_64(Material.LIME_STAINED_GLASS_PANE, "&a&lBuy", 64, 30, ShopInterfaceType.BUY),
  BUY_INVENTORY(Material.CHEST, "&a&lBuy Inventory", 0, 39, ShopInterfaceType.BUY),
  SELL_1(Material.RED_STAINED_GLASS_PANE, "&c&lSell", 1, 14, ShopInterfaceType.SELL),
  SELL_16(Material.RED_STAINED_GLASS_PANE, "&c&lSell", 16, 23, ShopInterfaceType.SELL),
  SELL_64(Material.RED_STAINED_GLASS_PANE, "&c&lSell", 64, 32, ShopInterfaceType.SELL),
  SELL_INVENTORY(Material.CHEST, "&c&lSell Inventory", 0, 41, ShopInterfaceType.SELL)
  ;

  private final Material material;
  private final String name;
  @Getter private final int amount;
  @Getter private final int slot;
  @Getter private final ShopInterfaceType shopInterfaceType;

  public ItemStack createSellInterfaceItem(double value, int amount) {
    return ItemUtil.createItem(material, name, Lang.MENU_SHOP_INTERFACE_ITEM_SELL_LORE.getString(new String[]{
      CoreUtil.parseValue(amount),
      Lang.VALUE_FORMAT.getString(new String[]{CoreUtil.parseValue(value)})
    }), 0, null);
  }

  public ItemStack createBuyInterfaceItem(double value, int amount) {
    return ItemUtil.createItem(material, name, Lang.MENU_SHOP_INTERFACE_ITEM_BUY_LORE.getString(new String[]{
      CoreUtil.parseValue(amount),
      Lang.VALUE_FORMAT.getString(new String[]{CoreUtil.parseValue(value)})
    }), 0, null);
  }
}
