package lee.code.shops.listeners;

import lee.code.colors.ColorAPI;
import lee.code.economy.EcoAPI;
import lee.code.playerdata.PlayerDataAPI;
import lee.code.shops.Shops;
import lee.code.shops.database.cache.CachePlayers;
import lee.code.shops.enums.ShopType;
import lee.code.shops.lang.Lang;
import lee.code.shops.menus.menu.ShopSignItemPreviewMenu;
import lee.code.shops.utils.CoreUtil;
import lee.code.shops.utils.ItemUtil;
import lee.code.shops.utils.ShopSignUtil;
import lee.code.shops.utils.VariableUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShopSignListener implements Listener {
  private final Shops shops;

  public ShopSignListener(Shops shops) {
    this.shops = shops;
  }

  @EventHandler
  public void onShopCreate(SignChangeEvent e) {
    final List<Component> lines = new ArrayList<>(e.lines());
    if (lines.isEmpty()) return;
    final PlainTextComponentSerializer plainTextComponentSerializer = PlainTextComponentSerializer.plainText();
    final String shopString = plainTextComponentSerializer.serialize(lines.get(0));
    if (!shopString.equalsIgnoreCase("[shop]")) return;
    final Player player = e.getPlayer();
    final UUID playerID = player.getUniqueId();
    if (lines.size() < 3) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SHOP_SIGN_CREATE_INVALID_FORMAT.getComponent(null)));
      return;
    }
    final String option = plainTextComponentSerializer.serialize(lines.get(1));
    if (!option.equalsIgnoreCase("buy") && !option.equalsIgnoreCase("sell")) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SHOP_SIGN_CREATE_INVALID_TYPE.getComponent(new String[]{option})));
      return;
    }
    final boolean isBuyShop = !option.equalsIgnoreCase("sell");
    final ShopType signShopType = isBuyShop ? ShopType.BUY : ShopType.SELL;
    final String valueString = plainTextComponentSerializer.serialize(lines.get(2));
    if (!CoreUtil.isPositiveDoubleNumber(valueString)) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SHOP_SIGN_CREATE_INVALID_VALUE.getComponent(new String[]{valueString})));
      return;
    }
    final double cost = Double.parseDouble(valueString);
    final Block block = e.getBlock();
    if (!(block.getBlockData() instanceof Directional directional)) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SHOP_SIGN_CREATE_DIRECTION.getComponent(null)));
      return;
    }
    if (block.getType().name().endsWith("HANGING_SIGN")) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SHOP_SIGN_CREATE_HANGING_SIGN.getComponent(null)));
      return;
    }
    final Block blockBehind = block.getRelative(directional.getFacing().getOppositeFace());
    if (!shops.getData().getSupportedSignBlocks().contains(blockBehind.getType())) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SHOP_SIGN_CREATE_NOT_SUPPORTED_BLOCK.getComponent(new String[]{CoreUtil.capitalize(blockBehind.getType().name())})));
      return;
    }
    if (getShopSign(blockBehind) != null) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SHOP_SIGN_CREATE_ALREADY_HAS_SIGN.getComponent(null)));
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
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SHOP_SIGN_CREATE_NO_ITEM.getComponent(null)));
      return;
    }
    final int targetItemAmount = targetItem.getAmount();
    targetItem.setAmount(1);
    final String itemString = ItemUtil.serializeItemStack(targetItem);
    if (itemString == null) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SHOP_SIGN_CREATE_NO_ITEM.getComponent(null)));
      return;
    }

    final List<Component> newLines = new ArrayList<>();
    newLines.add(Lang.SHOP_SIGN_TITLE.getComponent(null));
    final String shopTypeOption = signShopType.equals(ShopType.BUY) ? Lang.BUY.getString() : Lang.SELL.getString();
    newLines.add(Lang.SHOP_SIGN_TYPE.getComponent(new String[]{shopTypeOption}));
    newLines.add(VariableUtil.parseVariables(Lang.SHOP_SIGN_AMOUNT.getComponent(new String[]{CoreUtil.parseValue(targetItemAmount)}), targetItem));
    newLines.add(Lang.SHOP_SIGN_COST.getComponent(new String[]{Lang.VALUE_FORMAT.getString(new String[]{CoreUtil.parseValue(cost)})}));
    for (int i = 0; i < newLines.size(); i++) e.line(i, newLines.get(i));

    final TileState state = (TileState) block.getState();
    final PersistentDataContainer signContainer = state.getPersistentDataContainer();
    final NamespacedKey shopOwner = new NamespacedKey(shops, "shop-owner");
    final NamespacedKey shopItem = new NamespacedKey(shops, "shop-item");
    final NamespacedKey shopAmount = new NamespacedKey(shops, "shop-amount");
    final NamespacedKey shopType = new NamespacedKey(shops, "shop-type");
    final NamespacedKey shopCost = new NamespacedKey(shops, "shop-cost");
    final NamespacedKey shopProfit = new NamespacedKey(shops, "shop-profit");

    signContainer.set(shopOwner, PersistentDataType.STRING, playerID.toString());
    signContainer.set(shopItem, PersistentDataType.STRING, itemString);
    signContainer.set(shopAmount, PersistentDataType.INTEGER, targetItemAmount);
    signContainer.set(shopType, PersistentDataType.STRING, signShopType.name());
    signContainer.set(shopCost, PersistentDataType.DOUBLE, cost);
    signContainer.set(shopProfit, PersistentDataType.DOUBLE, 0.0);
    state.update(true, false);
    player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.SHOP_SIGN_CREATE_SUCCESS.getComponent(null)));
  }

  @EventHandler
  public void onShopInteract(PlayerInteractEvent e) {
    if (!e.getAction().isRightClick()) return;
    final Player player = e.getPlayer();
    final UUID playerID = player.getUniqueId();
    final Block block = e.getClickedBlock();
    if (block == null) return;
    if (block.getState().getBlockData() instanceof WallSign) {
      final Directional directional = (Directional) block.getState().getBlockData();
      final Sign sign = (Sign) block.getState();
      final UUID ownerID = getShopOwner(sign);
      if (ownerID == null) return;
      e.setCancelled(true);
      final ItemStack item = getShopItem(sign);
      final ShopType shopType = getShopType(sign);
      if (item == null) return;
      if (shopType == null) return;
      final double cost = getShopCost(sign);
      final int amount = getShopAmount(sign);
      final double profit = getShopProfit(sign);
      if (player.isSneaking()) {
        final ItemStack previewItem = new ItemStack(item);
        previewItem.setAmount(amount);
        shops.getMenuManager().openMenu(new ShopSignItemPreviewMenu(previewItem), player);
        return;
      }
      final Block shopBlock = block.getRelative(directional.getFacing().getOppositeFace());
      final Inventory shopBlockInventory = getContainerInventory(shopBlock);
      if (ownerID.equals(playerID)) {
        sendShopInfoMessage(player, sign, shopBlockInventory, item, ownerID, shopType, amount, cost, profit);
        return;
      }
      final CachePlayers cachePlayers = shops.getCacheManager().getCachePlayers();
      switch (shopType) {
        case SELL -> {
          final int shopFreeSpace = ShopSignUtil.getFreeSpace(shopBlockInventory, item);
          if (shopFreeSpace < amount) {
            updateSignTitle(sign, true);
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SHOP_SIGN_SELL_NO_SPACE.getComponent(null)));
            return;
          }
          if (EcoAPI.getBalance(ownerID) < cost) {
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SHOP_SIGN_SELL_OWNER_INSUFFICIENT_FUNDS.getComponent(null)));
            return;
          }
          if (ItemUtil.getItemAmount(player, item) < amount) {
            player.sendMessage(VariableUtil.parseVariables(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SHOP_SIGN_SELL_PLAYER_INSUFFICIENT_ITEMS.getComponent(new String[]{CoreUtil.parseValue(amount)})), item));
            return;
          }
          EcoAPI.removeBalance(ownerID, cost);
          EcoAPI.addBalance(playerID, cost);
          ShopSignUtil.addShopItems(shopBlock, item, amount);
          ItemUtil.removePlayerItems(player, item, amount, false);
          setShopProfit(sign, profit + cost);
          updateSignTitle(sign, (shopFreeSpace - amount) < amount);
          runPurchaseEffectAndSound(block);
          player.sendMessage(VariableUtil.parseVariables(Lang.PREFIX.getComponent(null).append(Lang.SHOP_SIGN_SELL_SUCCESS.getComponent(new String[]{CoreUtil.parseValue(amount), Lang.VALUE_FORMAT.getString(new String[]{CoreUtil.parseValue(cost)}), ColorAPI.getNameColor(ownerID, PlayerDataAPI.getName(ownerID))})), item));
          if (cachePlayers.hasNotificationsOn(ownerID)) PlayerDataAPI.sendPlayerMessageIfOnline(ownerID, VariableUtil.parseVariables(Lang.PREFIX.getComponent(null).append(Lang.SHOP_SIGN_SELL_OWNER_SUCCESS.getComponent(new String[]{ColorAPI.getNameColor(playerID, player.getName()), CoreUtil.parseValue(amount), Lang.VALUE_FORMAT.getString(new String[]{CoreUtil.parseValue(cost)})})), item));
        }
        case BUY -> {
          final int shopStock = ShopSignUtil.getItemAmount(shopBlockInventory, item);
          if (shopStock < amount) {
            updateSignTitle(sign, true);
            player.sendMessage(VariableUtil.parseVariables(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SHOP_SIGN_BUY_INSUFFICIENT_STOCK.getComponent(new String[]{CoreUtil.parseValue(amount)})), item));
            return;
          }
          if (EcoAPI.getBalance(playerID) < cost) {
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SHOP_SIGN_BUY_INSUFFICIENT_FUNDS.getComponent(null)));
            return;
          }
          if (ItemUtil.getFreeSpace(player, item) < amount) {
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SHOP_SIGN_BUY_INSUFFICIENT_SPACE.getComponent(null)));
            return;
          }
          EcoAPI.removeBalance(playerID, cost);
          EcoAPI.addBalance(ownerID, cost);
          ShopSignUtil.removeShopItems(shopBlock, item, amount);
          ItemUtil.giveItem(player, item, amount);
          setShopProfit(sign, profit + cost);
          updateSignTitle(sign, (shopStock - amount) < amount);
          runPurchaseEffectAndSound(block);
          player.sendMessage(VariableUtil.parseVariables(Lang.PREFIX.getComponent(null).append(Lang.SHOP_SIGN_BUY_SUCCESS.getComponent(new String[]{CoreUtil.parseValue(amount), Lang.VALUE_FORMAT.getString(new String[]{CoreUtil.parseValue(cost)}), ColorAPI.getNameColor(ownerID, PlayerDataAPI.getName(ownerID))})), item));
          if (cachePlayers.hasNotificationsOn(ownerID)) PlayerDataAPI.sendPlayerMessageIfOnline(ownerID, VariableUtil.parseVariables(Lang.PREFIX.getComponent(null).append(Lang.SHOP_SIGN_BUY_OWNER_SUCCESS.getComponent(new String[]{ColorAPI.getNameColor(playerID, player.getName()), CoreUtil.parseValue(amount), Lang.VALUE_FORMAT.getString(new String[]{CoreUtil.parseValue(cost)})})), item));
        }
      }
    } else if (shops.getData().getSupportedSignBlocks().contains(block.getType())) {
      final Sign sign = getShopSign(block);
      if (sign == null) return;
      final UUID ownerID = getShopOwner(sign);
      if (ownerID == null) return;
      if (!ownerID.equals(playerID)) {
        e.setCancelled(true);
        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SHOP_SIGN_NOT_OWNER_OPEN.getComponent(null)));
      }
    }
  }

  @EventHandler
  public void onShopBlockBreak(BlockBreakEvent e) {
    final Player player = e.getPlayer();
    final Block block = e.getBlock();
    final UUID playerID = player.getUniqueId();
    if (shops.getData().getSupportedSignBlocks().contains(e.getBlock().getType())) {
      final Sign sign = getShopSign(block);
      if (sign == null) return;
      final UUID ownerID = getShopOwner(sign);
      if (ownerID == null) return;
      if (!ownerID.equals(playerID)) {
        if (!shops.getCacheManager().getCachePlayers().hasAdminBypass(playerID)) {
          e.setCancelled(true);
          player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SHOP_SIGN_NOT_OWNER_BREAK.getComponent(null)));
          return;
        }
      }
      block.getWorld().playSound(block.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.SHOP_SIGN_BREAK_SUCCESS.getComponent(null)));
    } else if (block.getState().getBlockData() instanceof WallSign) {
      final Sign sign = (Sign) block.getState();
      final UUID ownerID = getShopOwner(sign);
      if (ownerID == null) return;
      if (!ownerID.equals(playerID)) {
        if (!shops.getCacheManager().getCachePlayers().hasAdminBypass(playerID)) {
          e.setCancelled(true);
          player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SHOP_SIGN_NOT_OWNER_BREAK.getComponent(null)));
          return;
        }
      }
      block.getWorld().playSound(block.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.SHOP_SIGN_BREAK_SUCCESS.getComponent(null)));
    }
  }

  @EventHandler
  public void onShopExplode(EntityExplodeEvent e) {
    for (Block block : new ArrayList<>(e.blockList())) {
      if (shops.getData().getSupportedSignBlocks().contains(block.getType())) {
        final TileState shopSign = getShopSign(block);
        if (shopSign != null) e.blockList().remove(block);
      } else if (block.getState().getBlockData() instanceof WallSign) {
        final Sign sign = (Sign) block.getState();
        final UUID ownerID = getShopOwner(sign);
        if (ownerID != null) e.blockList().remove(block);
      }
    }
  }

  private Sign getShopSign(Block block) {
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
                  final PersistentDataContainer container = sign.getPersistentDataContainer();
                  final NamespacedKey owner = new NamespacedKey(shops, "shop-owner");
                  if (container.has(owner, PersistentDataType.STRING)) return sign;
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
          final PersistentDataContainer container = sign.getPersistentDataContainer();
          final NamespacedKey owner = new NamespacedKey(shops, "shop-owner");
          if (container.has(owner, PersistentDataType.STRING)) return sign;
        }
      }
    }
    return null;
  }

  private void sendShopInfoMessage(Player player, Sign sign, Inventory shopBlockInventory, ItemStack item, UUID ownerID, ShopType shopType, int amount, double cost, double profit) {
    final List<Component> shopInfoLines = new ArrayList<>();
    shopInfoLines.add(Lang.SHOP_SIGN_INFO_HEADER.getComponent(null));
    shopInfoLines.add(Component.text(" "));
    shopInfoLines.add(Lang.SHOP_SIGN_INFO_OWNER.getComponent(new String[]{ColorAPI.getNameColor(ownerID, PlayerDataAPI.getName(ownerID))}));
    final String shopTypeColored = shopType.equals(ShopType.BUY) ? Lang.BUY.getString() : Lang.SELL.getString();
    shopInfoLines.add(Lang.SHOP_SIGN_INFO_TYPE.getComponent(new String[]{shopTypeColored}));
    shopInfoLines.add(VariableUtil.parseVariables(Lang.SHOP_SIGN_INFO_ITEM.getComponent(null), item));
    shopInfoLines.add(Lang.SHOP_SIGN_INFO_AMOUNT.getComponent(new String[]{CoreUtil.parseValue(amount)}));
    shopInfoLines.add(Lang.SHOP_SIGN_INFO_STOCK.getComponent(new String[]{CoreUtil.parseValue(ShopSignUtil.getItemAmount(shopBlockInventory, item))}));
    shopInfoLines.add(Lang.SHOP_SIGN_INFO_COST.getComponent(new String[]{Lang.VALUE_FORMAT.getString(new String[]{CoreUtil.parseValue(cost)})}));
    if (shopType.equals(ShopType.BUY)) shopInfoLines.add(Lang.SHOP_SIGN_INFO_SOLD.getComponent(new String[]{Lang.VALUE_FORMAT.getString(new String[]{CoreUtil.parseValue(profit)})}));
    else shopInfoLines.add(Lang.SHOP_SIGN_INFO_BOUGHT.getComponent(new String[]{Lang.VALUE_FORMAT.getString(new String[]{CoreUtil.parseValue(profit)})}));
    shopInfoLines.add(Component.text(" "));
    shopInfoLines.add(Lang.SHOP_SIGN_INFO_FOOTER.getComponent(null));
    for (Component line : shopInfoLines) player.sendMessage(line);
    if (shopType.equals(ShopType.BUY)) updateSignTitle(sign, ShopSignUtil.getItemAmount(shopBlockInventory, item) < amount);
    else if (shopType.equals(ShopType.SELL)) updateSignTitle(sign, ShopSignUtil.getFreeSpace(shopBlockInventory, item) < amount);
  }

  private void updateSignTitle(Sign sign, boolean isFullOrOutOfStock) {
    if (isFullOrOutOfStock) {
      sign.getSide(Side.FRONT).line(0, Lang.SHOP_SIGN_TITLE_OUT_OF_STOCK_OR_FULL.getComponent(null));
      sign.update(true, false);
    } else {
      sign.getSide(Side.FRONT).line(0, Lang.SHOP_SIGN_TITLE.getComponent(null));
      sign.update(true, false);
    }
  }

  private void runPurchaseEffectAndSound(Block block) {
    block.getWorld().spawnParticle(Particle.ITEM_CRACK,
      block.getBoundingBox().getCenter().getX(),
      block.getBoundingBox().getCenter().getY(),
      block.getBoundingBox().getCenter().getZ(),
      10, 0.2, 0.2, 0, 0.09, new ItemStack(Material.EMERALD));
    block.getWorld().playSound(block.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, (float) 1, (float) 1);
  }

  private Inventory getContainerInventory(Block container) {
    if (container.getState() instanceof ShulkerBox shulkerBox) return shulkerBox.getInventory();
    else if (container.getState() instanceof Barrel barrel) return barrel.getInventory();
    final Chest chest = (Chest) container.getState();
    return chest.getInventory();
  }

  private UUID getShopOwner(Sign sign) {
    final PersistentDataContainer container = sign.getPersistentDataContainer();
    final NamespacedKey key = new NamespacedKey(shops, "shop-owner");
    final String owner = container.get(key, PersistentDataType.STRING);
    if (owner != null) return UUID.fromString(owner);
    else return null;
  }

  private double getShopCost(Sign sign) {
    final PersistentDataContainer container = sign.getPersistentDataContainer();
    final NamespacedKey key = new NamespacedKey(shops, "shop-cost");
    return container.getOrDefault(key, PersistentDataType.DOUBLE, 0.0);
  }

  private double getShopProfit(Sign sign) {
    final PersistentDataContainer container = sign.getPersistentDataContainer();
    final NamespacedKey key = new NamespacedKey(shops, "shop-profit");
    return container.getOrDefault(key, PersistentDataType.DOUBLE, 0.0);
  }

  private void setShopProfit(Sign sign, double profit) {
    final PersistentDataContainer container = sign.getPersistentDataContainer();
    final NamespacedKey key = new NamespacedKey(shops, "shop-profit");
    container.set(key, PersistentDataType.DOUBLE, profit);
    sign.update(true, false);
  }

  private int getShopAmount(Sign sign) {
    final PersistentDataContainer container = sign.getPersistentDataContainer();
    final NamespacedKey key = new NamespacedKey(shops, "shop-amount");
    return container.getOrDefault(key, PersistentDataType.INTEGER, 0);
  }

  private ShopType getShopType(Sign sign) {
    final PersistentDataContainer container = sign.getPersistentDataContainer();
    final NamespacedKey key = new NamespacedKey(shops, "shop-type");
    final String type = container.get(key, PersistentDataType.STRING);
    if (type != null) return ShopType.valueOf(type);
    else return null;
  }

  private ItemStack getShopItem(Sign sign) {
    final PersistentDataContainer container = sign.getPersistentDataContainer();
    final NamespacedKey key = new NamespacedKey(shops, "shop-item");
    final String item = container.get(key, PersistentDataType.STRING);
    if (item != null) return ItemUtil.parseItemStack(item);
    else return null;
  }
}
