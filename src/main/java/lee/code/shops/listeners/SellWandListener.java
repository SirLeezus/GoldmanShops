package lee.code.shops.listeners;

import lee.code.economy.EcoAPI;
import lee.code.shops.Shops;
import lee.code.shops.enums.GlobalSetting;
import lee.code.shops.lang.Lang;
import lee.code.shops.utils.CoreUtil;
import org.bukkit.block.Barrel;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SellWandListener implements Listener {
  private final Shops shops;

  public SellWandListener(Shops shops) {
    this.shops = shops;
  }

  @EventHandler (priority = EventPriority.MONITOR)
  public void onSellWand(PlayerInteractEvent e) {
    if (!e.useInteractedBlock().equals(Event.Result.ALLOW)) return;
    if (!e.hasBlock()) return;
    final Player player = e.getPlayer();
    final ItemStack handItem = player.getInventory().getItemInMainHand();
    final ItemMeta handMeta = handItem.getItemMeta();
    if (handMeta == null) return;
    if (!handMeta.hasCustomModelData() || GlobalSetting.WAND_ID.getId() != handMeta.getCustomModelData()) return;
    e.setCancelled(true);
    if (shops.getDelayManager().isOnDelay(player.getUniqueId(), "wand")) return;
    shops.getDelayManager().setOnDelay(player.getUniqueId(), "wand", 500);
    final Block block = e.getClickedBlock();
    if (block == null) return;
    if (!shops.getData().getSupportedContainerType().contains(block.getType())) return;
    final Inventory inventory = getContainerInventory(block);
    final List<ItemStack> zeroValueItems = new ArrayList<>();
    double totalValue = 0;
    int totalAmount = 0;
    for (ItemStack item : inventory.getContents()) {
      if (item == null || item.getType().isAir()) continue;
      final ItemStack clone = item.clone();
      clone.setAmount(1);
      final double worth = shops.getData().getItemSellValue(clone);
      if (worth == 0) {
        zeroValueItems.add(item);
      } else {
        totalValue += (worth * item.getAmount());
        totalAmount += item.getAmount();
      }
    }
    if (totalValue == 0) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SELL_WAND_ZERO_VALUE.getComponent(null)));
      return;
    }
    inventory.clear();
    for (ItemStack zeroValueItem : zeroValueItems) inventory.addItem(zeroValueItem);
    EcoAPI.addBalance(player.getUniqueId(), totalValue);
    player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ITEM_SELL_WAND_USE_SUCCESS.getComponent(new String[]{CoreUtil.parseValue(totalAmount), Lang.VALUE_FORMAT.getString(new String[]{CoreUtil.parseValue(totalValue)})})));
  }

  private Inventory getContainerInventory(Block container) {
    if (container.getState() instanceof ShulkerBox shulkerBox) return shulkerBox.getInventory();
    else if (container.getState() instanceof Barrel barrel) return barrel.getInventory();
    final Chest chest = (Chest) container.getState();
    return chest.getInventory();
  }
}
