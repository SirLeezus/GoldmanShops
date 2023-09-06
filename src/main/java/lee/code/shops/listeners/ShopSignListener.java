package lee.code.shops.listeners;

import lee.code.economy.EcoAPI;
import lee.code.shops.Shops;
import lee.code.shops.enums.ShopType;
import lee.code.shops.lang.Lang;
import lee.code.shops.utils.CoreUtil;
import lee.code.shops.utils.ItemUtil;
import lee.code.shops.utils.VariableUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.block.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ShopSignListener implements Listener {
  private final Shops shops;

  public ShopSignListener(Shops shops) {
    this.shops = shops;
  }

  @EventHandler
  public void onShopCreate(SignChangeEvent e) {
    //[shop]
    // sell/buy
    // value
    final Player player = e.getPlayer();
    final UUID playerID = player.getUniqueId();
    final List<Component> lines = new ArrayList<>(e.lines());
    if (lines.isEmpty()) return;
    final PlainTextComponentSerializer plainTextComponentSerializer = PlainTextComponentSerializer.plainText();
    final String shopString = plainTextComponentSerializer.serialize(lines.get(0));
    if (!shopString.equalsIgnoreCase("[shop]")) return;
    System.out.println("Shop sign!");
    if (lines.size() < 3) {
      System.out.println("Wrong format");
      player.sendMessage(Lang.PREFIX.getComponent(null));
      return;
    }
    final String option = plainTextComponentSerializer.serialize(lines.get(1));
    boolean isBuyShop = true;
    if (option.equalsIgnoreCase("sell")) {
      System.out.println("SELL SIGN");
      isBuyShop = false;
    }
    if (option.equalsIgnoreCase("buy")) {
      System.out.println("BUY SIGN");
    }
    final ShopType signShopType = isBuyShop ? ShopType.BUY : ShopType.SELL;
    final String valueString = plainTextComponentSerializer.serialize(lines.get(2));
    if (!CoreUtil.isPositiveDoubleNumber(valueString)) {
      System.out.println("NOT VALUE");
      return;
    }
    final double value = Double.parseDouble(valueString);
    final Block block = e.getBlock();
    final BlockData blockData = block.getBlockData();
    if (!(blockData instanceof final Directional directional)) {
      System.out.println("NOT SIGN");
      return;
    }

    //TODO check if hanging sign
    if ((Sign)e.getBlock() instanceof HangingSign) {
      System.out.println("Hanging sign");
      return;
    }

    final Block blockBehind = block.getRelative(directional.getFacing().getOppositeFace());
    if (!shops.getData().getSupportedSignBlocks().contains(blockBehind.getType())) {
      System.out.println("NOT SUPPORTED SIGN BLOCK");
      return;
    }
    if (getShopSign(blockBehind) != null) {
      System.out.println("THIS CONTAINER ALREADY HAS A SHOP SIGN");
      return;
    }
    final Inventory containerInventory = getContainerInventory(blockBehind);
    ItemStack targetItem = new ItemStack(Material.AIR);
    for (ItemStack cItem : containerInventory.getContents()) {
      if (cItem != null) {
        targetItem = new ItemStack(cItem);
        break;
      }
    }
    if (targetItem.getType().equals(Material.AIR)) {
      System.out.println("NO ITEM FOUND");
      return;
    }
    final int targetItemAmount = targetItem.getAmount();
    targetItem.setAmount(1);
    final String itemString = ItemUtil.serializeItemStack(targetItem);
    if (itemString == null) {
      System.out.println("NO ITEM FOUND");
      return;
    }

    System.out.println("Creating shop sign.....");
    final List<Component> newLines = new ArrayList<>();
    newLines.add(Lang.SHOP_SIGN_TITLE.getComponent(null));
    final String shopTypeOption = signShopType.equals(ShopType.BUY) ? Lang.BUY.getString() : Lang.SELL.getString();
    newLines.add(Lang.SHOP_SIGN_TYPE.getComponent(new String[]{shopTypeOption}));
    newLines.add(VariableUtil.parseVariables(Lang.SHOP_SIGN_AMOUNT.getComponent(new String[]{
      CoreUtil.parseValue(targetItemAmount),
    }), targetItem));
    newLines.add(Lang.SHOP_SIGN_COST.getComponent(new String[]{Lang.VALUE_FORMAT.getString(new String[]{CoreUtil.parseValue(targetItemAmount)})}));
    for (int i = 0; i < newLines.size(); i++) {
      e.line(i, newLines.get(i));
    }

    final TileState state = (TileState) block.getState();
    final PersistentDataContainer signContainer = state.getPersistentDataContainer();
    final NamespacedKey shopOwner = new NamespacedKey(shops, "shop-owner");
    final NamespacedKey shopItem = new NamespacedKey(shops, "shop-item");
    final NamespacedKey shopAmount = new NamespacedKey(shops, "shop-amount");
    final NamespacedKey shopType = new NamespacedKey(shops, "shop-type");
    final NamespacedKey shopPrice = new NamespacedKey(shops, "shop-price");
    final NamespacedKey shopProfit = new NamespacedKey(shops, "shop-profit");

    signContainer.set(shopOwner, PersistentDataType.STRING, playerID.toString());
    signContainer.set(shopItem, PersistentDataType.STRING, itemString);
    signContainer.set(shopAmount, PersistentDataType.INTEGER, targetItemAmount);
    signContainer.set(shopType, PersistentDataType.STRING, signShopType.name());
    signContainer.set(shopPrice, PersistentDataType.DOUBLE, value);
    signContainer.set(shopProfit, PersistentDataType.DOUBLE, 0.0);
    state.update();
  }

  @EventHandler
  public void onShopBlockBreak(BlockBreakEvent e) {
    final Block block = e.getBlock();
    final UUID playerID = e.getPlayer().getUniqueId();
    if (shops.getData().getSupportedSignBlocks().contains(e.getBlock().getType())) {
      final TileState shopSign = getShopSign(block);
      if (shopSign == null) return;;
      final UUID ownerID = getShopOwner(shopSign);
      if (ownerID == null) return;
      if (!ownerID.equals(playerID)) {
        System.out.println("NOT OWNER");
        e.setCancelled(true);
        return;
      }
      block.getWorld().playSound(block.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
      System.out.println("Shop removed");
    } else if (block.getState().getBlockData() instanceof WallSign) {
      final TileState shopSign = (TileState) block.getState();
      final UUID ownerID = getShopOwner(shopSign);
      if (ownerID == null) return;
      if (!ownerID.equals(playerID)) {
        System.out.println("NOT OWNER");
        e.setCancelled(true);
      }
      block.getWorld().playSound(block.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
      System.out.println("Shop removed");
    }
  }

  @EventHandler
  public void onShopExplode(EntityExplodeEvent e) {
    for (Block block : new ArrayList<>(e.blockList())) {
      if (shops.getData().getSupportedSignBlocks().contains(block.getType())) {
        final TileState shopSign = getShopSign(block);
        if (shopSign != null) e.blockList().remove(block);
      } else if (block.getState().getBlockData() instanceof WallSign) {
        final TileState state = (TileState) block.getState();
        final UUID ownerID = getShopOwner(state);
        if (ownerID != null) e.blockList().remove(block);
      }
    }
  }

  @EventHandler
  public void onShopHopperMove(InventoryMoveItemEvent e) {
    if (e.getSource().getLocation() == null) return;
    final Block block = e.getSource().getLocation().getBlock();
    if (!shops.getData().getSupportedSignBlocks().contains(block.getType())) return;
    final TileState shopSign = getShopSign(block);
    if (shopSign != null) e.setCancelled(true);
  }

  @EventHandler
  public void onShopInteract(PlayerInteractEvent e) {
    if (!e.getAction().isRightClick()) return;
    final Player player = e.getPlayer();
    final UUID playerID = player.getUniqueId();
    final Block block = e.getClickedBlock();
    if (block == null) return;
    if (!shops.getData().getSupportedSignBlocks().contains(block.getType())) {
      final TileState shopSign = getShopSign(block);
      if (shopSign == null) return;
      final UUID ownerID = getShopOwner(shopSign);
      if (ownerID == null) return;
      if (!ownerID.equals(playerID)) {
        System.out.println("You do not own the shop!");
        e.setCancelled(true);
      }
    } else if (block.getState().getBlockData() instanceof WallSign) {
      final Directional directional = (Directional) block.getState().getBlockData();
      final TileState shopSign = (TileState) block.getState();
      final UUID ownerID = getShopOwner(shopSign);
      if (ownerID == null) return;
      final ItemStack item = getShopItem(shopSign);
      if (item == null) return;
      final double value = getShopValue(shopSign);
      final int amount = getShopAmount(shopSign);
      final ShopType shopType = getShopType(shopSign);
      if (shopType == null) return;
      final Block shopBlock = block.getRelative(directional.getFacing().getOppositeFace());
      final Inventory shopBlockInventory = getContainerInventory(shopBlock);
      switch (shopType) {
        case SELL -> {

        }
        case BUY -> {
          final int stock = getItemAmount(shopBlockInventory, item);
          if (stock < amount) {
            System.out.println("NOT ENOUGH STOCK");
            return;
          }
          final double cost = value * amount;
          if (EcoAPI.getBalance(playerID) < cost) {
            System.out.println("NOT ENOUGH MONEY");
            return;
          }
          final boolean removedFromShop = removeShopItems(shopBlock, item, amount);
          if (!removedFromShop) {
            System.out.println("Could not remove from shop");
            return;
          }
          ItemUtil.giveItem(player, item, amount);
          System.out.println("You were given item and items were removed from shop container");
        }
      }
    }
  }

  private TileState getShopSign(Block block) {
    final BlockState blockState = block.getState();
    final BlockFace[] faces = new BlockFace[]{BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};
    for (BlockFace face : faces) {
      final Block relativeBlock = block.getRelative(face);
      final BlockState relativeBlockState = relativeBlock.getState();
      if (blockState instanceof Chest chest && relativeBlockState instanceof Chest relativeChest) {
        final InventoryHolder inventoryHolder = chest.getInventory().getHolder();
        final InventoryHolder relativeInventoryHolder = relativeChest.getInventory().getHolder();
        if (inventoryHolder instanceof DoubleChest && relativeInventoryHolder instanceof DoubleChest) {
          final DoubleChestInventory inventory = (DoubleChestInventory) inventoryHolder.getInventory();
          final DoubleChestInventory relativeInventory = (DoubleChestInventory) relativeInventoryHolder.getInventory();
          final Location location = inventory.getLocation();
          final Location relativeLocation = relativeInventory.getLocation();
          if (location != null && location.equals(relativeLocation)) {
            for (BlockFace relativeFace : faces) {
              final Block relative = relativeBlock.getRelative(relativeFace);
              final BlockState relativeState = relative.getState();
              if (relativeState.getBlockData() instanceof WallSign) {
                final Sign sign = (Sign) relativeState;
                final Directional signDirectional = (Directional) sign.getBlockData();
                final Block relativeBlockBehind = relative.getRelative(signDirectional.getFacing().getOppositeFace());
                if (relativeBlockBehind.equals(relativeBlock)) {
                  final TileState state = (TileState) relativeState;
                  final PersistentDataContainer container = state.getPersistentDataContainer();
                  final NamespacedKey owner = new NamespacedKey(shops, "shop-owner");
                  if (container.has(owner, PersistentDataType.STRING)) return state;
                }
              }
            }
          }
        }
      } else if (relativeBlockState.getBlockData() instanceof WallSign) {
        final Sign sign = (Sign) relativeBlockState;
        final Directional signDirectional = (Directional) sign.getBlockData();
        final Block relativeBlockBehind = relativeBlock.getRelative(signDirectional.getFacing().getOppositeFace());
        if (relativeBlockBehind.equals(block)) {
          final TileState state = (TileState) relativeBlockState;
          final PersistentDataContainer container = state.getPersistentDataContainer();
          final NamespacedKey owner = new NamespacedKey(shops, "shop-owner");
          if (container.has(owner, PersistentDataType.STRING)) return state;
        }
      }
    }
    return null;
  }

  private Inventory getContainerInventory(Block container) {
    if (container.getState() instanceof ShulkerBox shulkerBox) return shulkerBox.getInventory();
    else if (container.getState() instanceof Barrel barrel) return barrel.getInventory();
    final Chest chest = (Chest) container.getState();
    return chest.getInventory();
  }

  private UUID getShopOwner(TileState tileState) {
    final PersistentDataContainer container = tileState.getPersistentDataContainer();
    final NamespacedKey key = new NamespacedKey(shops, "shop-owner");
    final String owner = container.get(key, PersistentDataType.STRING);
    if (owner != null) return UUID.fromString(owner);
    else return null;
  }

  private double getShopValue(TileState tileState) {
    final PersistentDataContainer container = tileState.getPersistentDataContainer();
    final NamespacedKey key = new NamespacedKey(shops, "shop-price");
    return container.getOrDefault(key, PersistentDataType.DOUBLE, 0.0);
  }

  private double getShopProfit(TileState tileState) {
    final PersistentDataContainer container = tileState.getPersistentDataContainer();
    final NamespacedKey key = new NamespacedKey(shops, "shop-profit");
    return container.getOrDefault(key, PersistentDataType.DOUBLE, 0.0);
  }

  private int getShopAmount(TileState tileState) {
    final PersistentDataContainer container = tileState.getPersistentDataContainer();
    final NamespacedKey key = new NamespacedKey(shops, "shop-amount");
    return container.getOrDefault(key, PersistentDataType.INTEGER, 0);
  }

  private ShopType getShopType(TileState tileState) {
    final PersistentDataContainer container = tileState.getPersistentDataContainer();
    final NamespacedKey key = new NamespacedKey(shops, "shop-type");
    final String type = container.get(key, PersistentDataType.STRING);
    if (type != null) return ShopType.valueOf(type);
    else return null;
  }

  private ItemStack getShopItem(TileState tileState) {
    final PersistentDataContainer container = tileState.getPersistentDataContainer();
    final NamespacedKey key = new NamespacedKey(shops, "shop-item");
    final String item = container.get(key, PersistentDataType.STRING);
    if (item != null) return ItemUtil.parseItemStack(item);
    else return null;
  }

  private boolean removeShopItems(Block container, ItemStack item, int amount) {
    if (container.getState() instanceof Chest chest) {
      final Inventory inventory = chest.getInventory();
      if (getItemAmount(inventory, item) >= amount) {
        consumeItems(inventory, item, amount);
        return true;
      }

    } else if (container.getState() instanceof ShulkerBox shulkerBox) {
      final Inventory inventory = shulkerBox.getInventory();
      if (getItemAmount(inventory, item) >= amount) {
        consumeItems(inventory, item, amount);
        return true;
      }

    } else if (container.getState() instanceof Barrel barrel) {
      final Inventory inventory = barrel.getInventory();
      if (getItemAmount(inventory, item) >= amount) {
        consumeItems(inventory, item, amount);
        return true;
      }
    }
    return false;
  }

  private void consumeItems(Inventory inventory, ItemStack item, int amount) {
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

  private int getItemAmount(Inventory inventory, ItemStack item) {
    int amount = 0;
    for (int i = 0; i < inventory.getSize(); i++) {
      final ItemStack slot = inventory.getItem(i);
      if (slot == null || !slot.isSimilar(item)) continue;
      amount += slot.getAmount();
    }
    return amount;
  }
}
