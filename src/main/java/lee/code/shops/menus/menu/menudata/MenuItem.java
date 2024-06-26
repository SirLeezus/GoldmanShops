package lee.code.shops.menus.menu.menudata;

import lee.code.shops.utils.ItemUtil;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public enum MenuItem {
  FILLER_GLASS(Material.BLACK_STAINED_GLASS_PANE, "", null, false, false, null),
  NEXT_PAGE(Material.PAPER, "&e&lNext Page ->", null, false, false, null),
  PREVIOUS_PAGE(Material.PAPER, "&e&l<- Prev Page", null, false, false, null),
  BACK_MENU(Material.BARRIER, "&c&l<-- Back", null, false, false, null),
  PLAYER_SHOPS_MENU(Material.CHEST, "&5&lPlayer Shops", "&6» &aClick to open player shop menu!", false, false, null),

  ;

  private final Material material;
  private final String name;
  private final String lore;
  private final boolean hideItemFlags;
  private final boolean enchantItem;
  private final String skin;

  public ItemStack createItem() {
    final ItemStack item = ItemUtil.createItem(material, name, lore, 0, skin);
    if (hideItemFlags) ItemUtil.hideItemFlags(item);
    if (enchantItem) ItemUtil.enchantItem(item, Enchantment.ARROW_INFINITE, 1);
    return item;
  }
}
