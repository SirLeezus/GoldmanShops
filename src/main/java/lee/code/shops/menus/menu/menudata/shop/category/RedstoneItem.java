package lee.code.shops.menus.menu.menudata.shop.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public enum RedstoneItem {
  REDSTONE(new ItemStack(Material.REDSTONE)),

  ;

  @Getter private final ItemStack item;
}
