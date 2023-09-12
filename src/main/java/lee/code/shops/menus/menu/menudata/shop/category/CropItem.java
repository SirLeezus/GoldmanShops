package lee.code.shops.menus.menu.menudata.shop.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public enum CropItem {
  CHORUS_FLOWER(new ItemStack(Material.CHORUS_FLOWER)),
  SWEET_BERRIES(new ItemStack(Material.SWEET_BERRIES)),
  GLOW_BERRIES(new ItemStack(Material.GLOW_BERRIES)),
  CARROT(new ItemStack(Material.CARROT)),
  POTATO(new ItemStack(Material.POTATO)),
  WHEAT_SEEDS(new ItemStack(Material.WHEAT_SEEDS)),
  MELON_SEEDS(new ItemStack(Material.MELON_SEEDS)),
  PUMPKIN_SEEDS(new ItemStack(Material.PUMPKIN_SEEDS)),
  BEETROOT_SEEDS(new ItemStack(Material.BEETROOT_SEEDS)),
  COCOA_BEANS(new ItemStack(Material.COCOA_BEANS)),
  TORCHFLOWER_SEEDS(new ItemStack(Material.TORCHFLOWER_SEEDS)),
  NETHER_WART(new ItemStack(Material.NETHER_WART)),
  PITCHER_POD(new ItemStack(Material.PITCHER_POD)),
  CACTUS(new ItemStack(Material.CACTUS)),
  SUGAR_CANE(new ItemStack(Material.SUGAR_CANE)),
  BAMBOO(new ItemStack(Material.BAMBOO)),
  OAK_SAPLING(new ItemStack(Material.OAK_SAPLING)),
  SPRUCE_SAPLING(new ItemStack(Material.SPRUCE_SAPLING)),
  BIRCH_SAPLING(new ItemStack(Material.BIRCH_SAPLING)),
  JUNGLE_SAPLING(new ItemStack(Material.JUNGLE_SAPLING)),
  ACACIA_SAPLING(new ItemStack(Material.ACACIA_SAPLING)),
  DARK_OAK_SAPLING(new ItemStack(Material.DARK_OAK_SAPLING)),
  CHERRY_SAPLING(new ItemStack(Material.CHERRY_SAPLING)),
  MANGROVE_PROPAGULE(new ItemStack(Material.MANGROVE_PROPAGULE)),

  ;

  @Getter private final ItemStack item;
}
