package lee.code.shops.menus.menu.menudata.shop.category;

import lee.code.shops.utils.ItemUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public enum SpawnerItem {
  COW(ItemUtil.createSpawner(EntityType.COW)),
  MUSHROOM_COW(ItemUtil.createSpawner(EntityType.MUSHROOM_COW)),
  PIG(ItemUtil.createSpawner(EntityType.PIG)),
  CHICKEN(ItemUtil.createSpawner(EntityType.CHICKEN)),
  SHEEP(ItemUtil.createSpawner(EntityType.SHEEP)),
  HORSE(ItemUtil.createSpawner(EntityType.HORSE)),
  ZOMBIE_HORSE(ItemUtil.createSpawner(EntityType.ZOMBIE_HORSE)),
  SKELETON_HORSE(ItemUtil.createSpawner(EntityType.SKELETON_HORSE)),
  DONKEY(ItemUtil.createSpawner(EntityType.DONKEY)),
  MULE(ItemUtil.createSpawner(EntityType.MULE)),
  GOAT(ItemUtil.createSpawner(EntityType.GOAT)),
  POLAR_BEAR(ItemUtil.createSpawner(EntityType.POLAR_BEAR)),
  FOX(ItemUtil.createSpawner(EntityType.FOX)),
  SNIFFER(ItemUtil.createSpawner(EntityType.SNIFFER)),
  FROG(ItemUtil.createSpawner(EntityType.FROG)),
  TADPOLE(ItemUtil.createSpawner(EntityType.TADPOLE)),
  AXOLOTL(ItemUtil.createSpawner(EntityType.AXOLOTL)),
  CAT(ItemUtil.createSpawner(EntityType.CAT)),
  OCELOT(ItemUtil.createSpawner(EntityType.OCELOT)),
  PARROT(ItemUtil.createSpawner(EntityType.PARROT)),
  WANDERING_TRADER(ItemUtil.createSpawner(EntityType.WANDERING_TRADER)),
  PANDA(ItemUtil.createSpawner(EntityType.PANDA)),
  TURTLE(ItemUtil.createSpawner(EntityType.TURTLE)),
  BEE(ItemUtil.createSpawner(EntityType.BEE)),
  BAT(ItemUtil.createSpawner(EntityType.BAT)),
  CAMEL(ItemUtil.createSpawner(EntityType.CAMEL)),
  LLAMA(ItemUtil.createSpawner(EntityType.LLAMA)),
  ZOGLIN(ItemUtil.createSpawner(EntityType.ZOGLIN)),
  ALLAY(ItemUtil.createSpawner(EntityType.ALLAY)),
  DOLPHIN(ItemUtil.createSpawner(EntityType.DOLPHIN)),
  SQUID(ItemUtil.createSpawner(EntityType.SQUID)),
  GLOW_SQUID(ItemUtil.createSpawner(EntityType.GLOW_SQUID)),
  GUARDIAN(ItemUtil.createSpawner(EntityType.GUARDIAN)),
  SALMON(ItemUtil.createSpawner(EntityType.SALMON)),
  PUFFERFISH(ItemUtil.createSpawner(EntityType.PUFFERFISH)),
  TROPICAL_FISH(ItemUtil.createSpawner(EntityType.TROPICAL_FISH)),
  COD(ItemUtil.createSpawner(EntityType.COD)),
  RABBIT(ItemUtil.createSpawner(EntityType.RABBIT)),
  SNOWMAN(ItemUtil.createSpawner(EntityType.SNOWMAN)),
  SLIME(ItemUtil.createSpawner(EntityType.SLIME)),
  MAGMA_CUBE(ItemUtil.createSpawner(EntityType.MAGMA_CUBE)),
  SKELETON(ItemUtil.createSpawner(EntityType.SKELETON)),
  WITHER_SKELETON(ItemUtil.createSpawner(EntityType.WITHER_SKELETON)),
  STRAY(ItemUtil.createSpawner(EntityType.STRAY)),
  CREEPER(ItemUtil.createSpawner(EntityType.CREEPER)),
  ZOMBIE(ItemUtil.createSpawner(EntityType.ZOMBIE)),
  ZOMBIE_VILLAGER(ItemUtil.createSpawner(EntityType.ZOMBIE_VILLAGER)),
  HUSK(ItemUtil.createSpawner(EntityType.HUSK)),
  BLAZE(ItemUtil.createSpawner(EntityType.BLAZE)),
  CAVE_SPIDER(ItemUtil.createSpawner(EntityType.CAVE_SPIDER)),
  SPIDER(ItemUtil.createSpawner(EntityType.SPIDER)),
  DROWNED(ItemUtil.createSpawner(EntityType.DROWNED)),
  ENDERMAN(ItemUtil.createSpawner(EntityType.ENDERMAN)),
  ENDERMITE(ItemUtil.createSpawner(EntityType.ENDERMITE)),
  SILVERFISH(ItemUtil.createSpawner(EntityType.SILVERFISH)),
  PHANTOM(ItemUtil.createSpawner(EntityType.PHANTOM)),
  PIGLIN(ItemUtil.createSpawner(EntityType.PIGLIN)),
  ZOMBIFIED_PIGLIN(ItemUtil.createSpawner(EntityType.ZOMBIFIED_PIGLIN)),
  PIGLIN_BRUTE(ItemUtil.createSpawner(EntityType.PIGLIN_BRUTE)),
  PILLAGER(ItemUtil.createSpawner(EntityType.PILLAGER)),
  SHULKER(ItemUtil.createSpawner(EntityType.SHULKER)),
  VINDICATOR(ItemUtil.createSpawner(EntityType.VINDICATOR)),
  ILLUSIONER(ItemUtil.createSpawner(EntityType.ILLUSIONER)),
  GHAST(ItemUtil.createSpawner(EntityType.GHAST)),
  VILLAGER(ItemUtil.createSpawner(EntityType.VILLAGER)),
  IRON_GOLEM(ItemUtil.createSpawner(EntityType.IRON_GOLEM)),
  EVOKER(ItemUtil.createSpawner(EntityType.EVOKER)),

  ;
  @Getter private final ItemStack item;
}
