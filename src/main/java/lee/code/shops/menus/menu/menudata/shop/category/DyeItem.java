package lee.code.shops.menus.menu.menudata.shop.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public enum DyeItem {
  WHITE_DYE(new ItemStack(Material.WHITE_DYE)),
  LIGHT_GRAY_DYE(new ItemStack(Material.LIGHT_GRAY_DYE)),
  GRAY_DYE(new ItemStack(Material.GRAY_DYE)),
  BLACK_DYE(new ItemStack(Material.BLACK_DYE)),
  BROWN_DYE(new ItemStack(Material.BROWN_DYE)),
  RED_DYE(new ItemStack(Material.RED_DYE)),
  ORANGE_DYE(new ItemStack(Material.ORANGE_DYE)),
  YELLOW_DYE(new ItemStack(Material.YELLOW_DYE)),
  LIME_DYE(new ItemStack(Material.LIME_DYE)),
  GREEN_DYE(new ItemStack(Material.GREEN_DYE)),
  CYAN_DYE(new ItemStack(Material.CYAN_DYE)),
  LIGHT_BLUE_DYE(new ItemStack(Material.LIGHT_BLUE_DYE)),
  BLUE_DYE(new ItemStack(Material.BLUE_DYE)),
  PURPLE_DYE(new ItemStack(Material.PURPLE_DYE)),
  MAGENTA_DYE(new ItemStack(Material.MAGENTA_DYE)),
  PINK_DYE(new ItemStack(Material.PINK_DYE)),
  ;

  @Getter private final ItemStack item;
}
