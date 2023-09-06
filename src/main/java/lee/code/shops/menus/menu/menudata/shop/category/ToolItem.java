package lee.code.shops.menus.menu.menudata.shop.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public enum ToolItem {
  DIAMOND_PICKAXE(new ItemStack(Material.DIAMOND_PICKAXE)),

  ;

  @Getter private final ItemStack item;
}
