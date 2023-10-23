package lee.code.shops.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lee.code.shops.lang.Lang;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class ItemUtil {

  public static ItemStack createItem(Material material, String name, String lore, int modelData, String skin) {
    final ItemStack item = new ItemStack(material);
    final ItemMeta itemMeta = item.getItemMeta();
    if (itemMeta == null) return item;
    if (skin != null) applyHeadSkin(itemMeta, skin);
    if (lore != null) setItemLore(itemMeta, lore);
    if (name != null) itemMeta.displayName(CoreUtil.parseColorComponent(name));
    if (modelData != 0) itemMeta.setCustomModelData(modelData);
    item.setItemMeta(itemMeta);
    return item;
  }

  public static void applyHeadSkin(ItemMeta itemMeta, String base64) {
    try {
      final SkullMeta skullMeta = (SkullMeta) itemMeta;
      final GameProfile profile = new GameProfile(UUID.fromString("ffffffff-ffff-ffff-ffff-ffffffffffff"), "null");
      profile.getProperties().put("textures", new Property("textures", base64));
      if (skullMeta != null) {
        final Method mtd = skullMeta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
        mtd.setAccessible(true);
        mtd.invoke(skullMeta, profile);
      }
    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
      ex.printStackTrace();
    }
  }

  public static void setItemLore(ItemMeta itemMeta, String lore) {
    if (itemMeta == null) return;
    final String[] split = StringUtils.split(lore, "\n");
    final List<Component> pLines = new ArrayList<>();
    for (String line : split) pLines.add(CoreUtil.parseColorComponent(line));
    itemMeta.lore(pLines);
  }

  public static void hideItemFlags(ItemStack itemStack) {
    final ItemMeta itemMeta = itemStack.getItemMeta();
    if (itemMeta == null) return;
    itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    itemMeta.addItemFlags(ItemFlag.HIDE_DYE);
    itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
    itemMeta.addItemFlags(ItemFlag.HIDE_ARMOR_TRIM);
    itemStack.setItemMeta(itemMeta);
  }

  public static void enchantItem(ItemStack itemStack, Enchantment enchantment, int level) {
    final ItemMeta itemMeta = itemStack.getItemMeta();
    if (itemMeta == null) return;
    itemMeta.addEnchant(enchantment, level, false);
    itemStack.setItemMeta(itemMeta);
  }

  public static void giveItem(Player player, ItemStack item, int amount) {
    if (item.getMaxStackSize() < 64) {
      for (int i = 0; i < amount; i++) {
        player.getInventory().addItem(item);
      }
    } else {
      item.setAmount(amount);
      player.getInventory().addItem(item);
    }
  }

  public static void giveItemOrDrop(Player player, ItemStack item, int amount) {
    item.setAmount(1);
    if (canReceiveItems(player, item, amount)) giveItem(player, item, amount);
    else for (int i = 0; i < amount; i++) player.getWorld().dropItemNaturally(player.getLocation(), item);
  }

  public static boolean canReceiveItems(Player player, ItemStack item, int amount) {
    return getFreeSpace(player, item) >= amount;
  }

  public static int getFreeSpace(Player player, ItemStack item) {
    int freeSpaceCount = 0;
    for (int slot = 0; slot <= 35; slot++) {
      final ItemStack slotItem = player.getInventory().getItem(slot);
      if (slotItem == null || slotItem.getType() == Material.AIR) {
        freeSpaceCount += item.getMaxStackSize();
      } else if (slotItem.isSimilar(item))
        freeSpaceCount += Math.max(0, slotItem.getMaxStackSize() - slotItem.getAmount());
    }
    return freeSpaceCount;
  }

  public static int getItemAmount(Player player, ItemStack targetItem) {
    int amount = 0;
    for (ItemStack item : player.getInventory().getContents()) {
      if (item == null || !item.isSimilar(targetItem)) continue;
      amount += item.getAmount();
    }
    return amount;
  }

  public static void removePlayerItems(Player player, ItemStack item, int count, boolean handOnly) {
    if (!handOnly) {
      final Map<Integer, ItemStack> ammo = new HashMap<>();
      for (int i = 0; i < player.getInventory().getSize(); i++) {
        final ItemStack stack = player.getInventory().getItem(i);
        if (stack == null) continue;
        if (stack.isSimilar(item)) {
          ammo.put(i, stack);
        }
      }
      int found = 0;
      for (ItemStack stack : ammo.values()) found += stack.getAmount();
      if (count > found) return;
      for (Integer index : ammo.keySet()) {
        final ItemStack stack = ammo.get(index);
        if (stack.isSimilar(item)) {
          final int removed = Math.min(count, stack.getAmount());
          count -= removed;
          if (stack.getAmount() == removed) player.getInventory().setItem(index, null);
          else stack.setAmount(stack.getAmount() - removed);
          if (count <= 0) break;
        }
      }
    } else {
      final ItemStack handItem = player.getInventory().getItemInMainHand();
      handItem.setAmount(handItem.getAmount() - count);
    }
  }

  public static ItemStack createSpawner(EntityType type) {
    final ItemStack spawner = new ItemStack(Material.SPAWNER);
    final BlockStateMeta spawnerMeta = (BlockStateMeta) spawner.getItemMeta();
    if (spawnerMeta == null) return spawner;
    final CreatureSpawner spawnerCS = (CreatureSpawner) spawnerMeta.getBlockState();
    spawnerCS.setSpawnedType(type);
    spawnerMeta.setBlockState(spawnerCS);
    spawnerMeta.displayName(Lang.SPAWNER_NAME.getComponent(new String[]{CoreUtil.capitalize(type.name())}));
    spawner.setItemMeta(spawnerMeta);
    return spawner;
  }

  public static String serializeItemStack(ItemStack item) {
    try {
      final ByteArrayOutputStream io = new ByteArrayOutputStream();
      final BukkitObjectOutputStream os = new BukkitObjectOutputStream(io);
      os.writeObject(item);
      os.flush();
      final byte[] serializedObject = io.toByteArray();
      return Base64.getEncoder().encodeToString(serializedObject);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static ItemStack parseItemStack(String serializedItemStack) {
    try {
      final byte[] serializedObject = Base64.getDecoder().decode(serializedItemStack);
      final ByteArrayInputStream in = new ByteArrayInputStream(serializedObject);
      final BukkitObjectInputStream is = new BukkitObjectInputStream(in);
      return (ItemStack) is.readObject();
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static ItemStack createArmorWithTrim(ItemStack armor, TrimPattern trimPattern, TrimMaterial trimMaterial) {
    final ArmorMeta armorMeta = (ArmorMeta) armor.getItemMeta();
    final ArmorTrim trim = new ArmorTrim(trimMaterial, trimPattern);
    armorMeta.setTrim(trim);
    armor.setItemMeta(armorMeta);
    return armor;
  }

  public static ItemStack createPetCaptureItem() {
    final ItemStack lead = createItem(Material.LEAD, Lang.ITEM_CAPTURE_NAME.getString(), Lang.ITEM_CAPTURE_LORE.getString(), 1, null);
    enchantItem(lead, Enchantment.LUCK, 1);
    hideItemFlags(lead);
    return lead;
  }

  public static ItemStack createCaptureWandItem() {
    final ItemStack wand = createItem(Material.BLAZE_ROD, Lang.ITEM_CAPTURE_WAND_NAME.getString(), Lang.ITEM_CAPTURE_WAND_LORE.getString(), 2, null);
    enchantItem(wand, Enchantment.LUCK, 1);
    hideItemFlags(wand);
    return wand;
  }

  public static ItemStack createSellWandItem() {
    final ItemStack wand = createItem(Material.BLAZE_ROD, Lang.ITEM_SELL_WAND_NAME.getString(), Lang.ITEM_SELL_WAND_LORE.getString(), 3, null);
    enchantItem(wand, Enchantment.LUCK, 1);
    hideItemFlags(wand);
    return wand;
  }
}
