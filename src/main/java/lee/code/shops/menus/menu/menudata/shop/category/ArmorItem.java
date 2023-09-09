package lee.code.shops.menus.menu.menudata.shop.category;

import lee.code.shops.utils.ItemUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

@AllArgsConstructor
public enum ArmorItem {
  DIAMOND_HELMET(ItemUtil.createArmorWithTrim(new ItemStack(Material.DIAMOND_HELMET), TrimPattern.DUNE, TrimMaterial.GOLD)),
  DIAMOND_CHESTPLATE(ItemUtil.createArmorWithTrim(new ItemStack(Material.DIAMOND_CHESTPLATE), TrimPattern.DUNE, TrimMaterial.GOLD)),
  DIAMOND_LEGGINGS(ItemUtil.createArmorWithTrim(new ItemStack(Material.DIAMOND_LEGGINGS), TrimPattern.DUNE, TrimMaterial.GOLD)),
  DIAMOND_BOOTS(ItemUtil.createArmorWithTrim(new ItemStack(Material.DIAMOND_BOOTS), TrimPattern.DUNE, TrimMaterial.GOLD)),
  LEATHER_HORSE_ARMOR(new ItemStack(Material.LEATHER_HORSE_ARMOR)),
  IRON_HORSE_ARMOR(new ItemStack(Material.IRON_HORSE_ARMOR)),
  DIAMOND_HORSE_ARMOR(new ItemStack(Material.DIAMOND_HORSE_ARMOR)),
  ;

  @Getter private final ItemStack item;
}
