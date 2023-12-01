package lee.code.shops.menus.menu.menudata.shop.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public enum BasicBlockItem {
  TERRACOTTA(new ItemStack(Material.TERRACOTTA)),
  SAND(new ItemStack(Material.SAND)),
  RED_SAND(new ItemStack(Material.RED_SAND)),
  DIRT(new ItemStack(Material.DIRT)),
  COBBLESTONE(new ItemStack(Material.COBBLESTONE)),
  COBBLED_DEEPSLATE(new ItemStack(Material.COBBLED_DEEPSLATE)),
  GRANITE(new ItemStack(Material.GRANITE)),
  DIORITE(new ItemStack(Material.DIORITE)),
  ANDESITE(new ItemStack(Material.ANDESITE)),
  END_STONE(new ItemStack(Material.END_STONE)),
  NETHERRACK(new ItemStack(Material.NETHERRACK)),
  MUD(new ItemStack(Material.MUD)),
  WHITE_WOOL(new ItemStack(Material.WHITE_WOOL)),
  SLIME_BLOCK(new ItemStack(Material.SLIME_BLOCK)),
  SPONGE(new ItemStack(Material.SPONGE)),

  ;

  @Getter private final ItemStack item;
}
