package lee.code.shops.menus.menu.menudata.shop.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public enum VehicleItem {
  OAK_BOAT(new ItemStack(Material.OAK_BOAT)),
  OAK_CHEST_BOAT(new ItemStack(Material.OAK_CHEST_BOAT)),
  BAMBOO_RAFT(new ItemStack(Material.BAMBOO_RAFT)),
  BAMBOO_CHEST_RAFT(new ItemStack(Material.BAMBOO_CHEST_RAFT)),
  MINECART(new ItemStack(Material.MINECART)),

  ;

  @Getter private final ItemStack item;
}
