package lee.code.shops.menus.menu.menudata.shop.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public enum ToolItem {
  DIAMOND_SHOVEL(new ItemStack(Material.DIAMOND_SHOVEL)),
  DIAMOND_PICKAXE(new ItemStack(Material.DIAMOND_PICKAXE)),
  DIAMOND_AXE(new ItemStack(Material.DIAMOND_AXE)),
  DIAMOND_HOE(new ItemStack(Material.DIAMOND_HOE)),
  DIAMOND_SWORD(new ItemStack(Material.DIAMOND_SWORD)),
  BUCKET(new ItemStack(Material.BUCKET)),
  FISHING_ROD(new ItemStack(Material.FISHING_ROD)),
  CARROT_ON_A_STICK(new ItemStack(Material.CARROT_ON_A_STICK)),
  WARPED_FUNGUS_ON_A_STICK(new ItemStack(Material.WARPED_FUNGUS_ON_A_STICK)),
  FLINT_AND_STEEL(new ItemStack(Material.FLINT_AND_STEEL)),
  SHEARS(new ItemStack(Material.SHEARS)),
  BRUSH(new ItemStack(Material.BRUSH)),
  NAME_TAG(new ItemStack(Material.NAME_TAG)),
  LEAD(new ItemStack(Material.LEAD)),
  COMPASS(new ItemStack(Material.COMPASS)),
  RECOVERY_COMPASS(new ItemStack(Material.RECOVERY_COMPASS)),
  CLOCK(new ItemStack(Material.CLOCK)),
  SPYGLASS(new ItemStack(Material.SPYGLASS)),
  MAP(new ItemStack(Material.MAP)),
  WRITABLE_BOOK(new ItemStack(Material.WRITABLE_BOOK)),
  ENDER_PEARL(new ItemStack(Material.ENDER_PEARL)),
  SADDLE(new ItemStack(Material.SADDLE)),
  TOTEM_OF_UNDYING(new ItemStack(Material.TOTEM_OF_UNDYING)),

  ;

  @Getter private final ItemStack item;
}
