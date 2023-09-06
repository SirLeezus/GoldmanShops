package lee.code.shops.utils;

import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ShopSignUtil {

  public static  void consumeItems(Inventory inventory, ItemStack item, int amount) {
    final Material mat = item.getType();
    final Map<Integer, ? extends ItemStack> ammo = inventory.all(mat);
    int found = 0;
    for (ItemStack stack : ammo.values()) found += stack.getAmount();
    if (amount > found) return;
    for (Integer index : ammo.keySet()) {
      final ItemStack stack = ammo.get(index);
      if (stack.isSimilar(item)) {
        final int removed = Math.min(amount, stack.getAmount());
        amount -= removed;
        if (stack.getAmount() == removed) inventory.setItem(index, null);
        else stack.setAmount(stack.getAmount() - removed);
        if (amount <= 0) break;
      }
    }
  }

  public static int getItemAmount(Inventory inventory, ItemStack item) {
    int amount = 0;
    for (int i = 0; i < inventory.getSize(); i++) {
      final ItemStack slot = inventory.getItem(i);
      if (slot == null || !slot.isSimilar(item)) continue;
      amount += slot.getAmount();
    }
    return amount;
  }

  public static int getFreeSpace(Inventory inventory, ItemStack item) {
    int freeSpaceCount = 0;
    for (int slot = 0; slot <= inventory.getSize() - 1; slot++) {
      final ItemStack slotItem = inventory.getItem(slot);
      if (slotItem == null || slotItem.getType() == Material.AIR) {
        freeSpaceCount += item.getMaxStackSize();
      } else if (slotItem.isSimilar(item))
        freeSpaceCount += Math.max(0, slotItem.getMaxStackSize() - slotItem.getAmount());
    }
    return freeSpaceCount;
  }

  public static void storeItem(Inventory inventory, ItemStack item, int amount) {
    if (item.getMaxStackSize() < 64) {
      for (int i = 0; i < amount; i++) {
        inventory.addItem(item);
      }
    } else {
      item.setAmount(amount);
      inventory.addItem(item);
    }
  }

  public static void addShopItems(Block container, ItemStack item, int amount) {
    if (container.getState() instanceof Chest chest) {
      final Inventory inventory = chest.getInventory();
      ShopSignUtil.storeItem(inventory, item, amount);

    } else if (container.getState() instanceof ShulkerBox shulkerBox) {
      final Inventory inventory = shulkerBox.getInventory();
      ShopSignUtil.storeItem(inventory, item, amount);

    } else if (container.getState() instanceof Barrel barrel) {
      final Inventory inventory = barrel.getInventory();
      ShopSignUtil.storeItem(inventory, item, amount);
    }
  }

  public static void removeShopItems(Block container, ItemStack item, int amount) {
    if (container.getState() instanceof Chest chest) {
      final Inventory inventory = chest.getInventory();
      consumeItems(inventory, item, amount);

    } else if (container.getState() instanceof ShulkerBox shulkerBox) {
      final Inventory inventory = shulkerBox.getInventory();
      consumeItems(inventory, item, amount);

    } else if (container.getState() instanceof Barrel barrel) {
      final Inventory inventory = barrel.getInventory();
      consumeItems(inventory, item, amount);
    }
  }
}
