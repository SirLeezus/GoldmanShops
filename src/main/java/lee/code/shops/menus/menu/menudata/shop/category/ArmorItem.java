package lee.code.shops.menus.menu.menudata.shop.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public enum ArmorItem {
  DIAMOND_HELMET(new ItemStack(Material.DIAMOND_HELMET)),
  DIAMOND_CHESTPLATE(new ItemStack(Material.DIAMOND_CHESTPLATE)),
  DIAMOND_LEGGINGS(new ItemStack(Material.DIAMOND_LEGGINGS)),
  DIAMOND_BOOTS(new ItemStack(Material.DIAMOND_BOOTS)),
  LEATHER_HORSE_ARMOR(new ItemStack(Material.LEATHER_HORSE_ARMOR)),
  IRON_HORSE_ARMOR(new ItemStack(Material.IRON_HORSE_ARMOR)),
  DIAMOND_HORSE_ARMOR(new ItemStack(Material.DIAMOND_HORSE_ARMOR)),
  ;

  @Getter private final ItemStack item;
}
