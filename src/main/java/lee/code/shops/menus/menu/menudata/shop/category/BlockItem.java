package lee.code.shops.menus.menu.menudata.shop.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public enum BlockItem {
  TERRACOTTA(new ItemStack(Material.TERRACOTTA)),
  WHITE_WOOL(new ItemStack(Material.WHITE_WOOL)),
  SAND(new ItemStack(Material.SAND)),
  RED_SAND(new ItemStack(Material.RED_SAND)),
  DIRT(new ItemStack(Material.DIRT)),
  COBBLESTONE(new ItemStack(Material.COBBLESTONE)),
  END_STONE(new ItemStack(Material.END_STONE)),
  NETHERRACK(new ItemStack(Material.NETHERRACK)),
  SLIME_BLOCK(new ItemStack(Material.SLIME_BLOCK)),

  ;

  @Getter private final ItemStack item;
}
