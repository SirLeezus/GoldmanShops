package lee.code.shops.menus.menu.menudata.shop.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public enum WoodItem {
  OAK_LOG(new ItemStack(Material.OAK_LOG)),
  SPRUCE_LOG(new ItemStack(Material.SPRUCE_LOG)),
  BIRCH_LOG(new ItemStack(Material.BIRCH_LOG)),
  JUNGLE_LOG(new ItemStack(Material.JUNGLE_LOG)),
  ACACIA_LOG(new ItemStack(Material.ACACIA_LOG)),
  DARK_OAK_LOG(new ItemStack(Material.DARK_OAK_LOG)),
  MANGROVE_LOG(new ItemStack(Material.MANGROVE_LOG)),
  CHERRY_LOG(new ItemStack(Material.CHERRY_LOG)),
  WARPED_STEM(new ItemStack(Material.WARPED_STEM)),
  CRIMSON_STEM(new ItemStack(Material.CRIMSON_STEM)),
  BAMBOO_BLOCK(new ItemStack(Material.BAMBOO_BLOCK)),
  ;

  @Getter private final ItemStack item;
}
