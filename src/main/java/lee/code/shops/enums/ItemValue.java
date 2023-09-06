package lee.code.shops.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public enum ItemValue {
  REDSTONE(new ItemStack(Material.REDSTONE), 2, 1),
  GRASS_BLOCK(new ItemStack(Material.GRASS_BLOCK), 2, 1),
  DIRT(new ItemStack(Material.DIRT), 2, 1),
  SAND(new ItemStack(Material.SAND), 2, 1),
  RED_SAND(new ItemStack(Material.RED_SAND), 2, 1),
  COBBLESTONE(new ItemStack(Material.COBBLESTONE), 2, 1),
  END_STONE(new ItemStack(Material.END_STONE), 2, 1),
  GRAVEL(new ItemStack(Material.GRAVEL), 2, 1),

  ;
  @Getter private final ItemStack item;
  @Getter private final double buy;
  @Getter private final double sell;
}
