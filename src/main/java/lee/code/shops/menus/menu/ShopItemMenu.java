package lee.code.shops.menus.menu;

import lee.code.economy.EcoAPI;
import lee.code.shops.Data;
import lee.code.shops.lang.Lang;
import lee.code.shops.menus.menu.menudata.MenuItem;
import lee.code.shops.menus.menu.menudata.shop.Rout;
import lee.code.shops.menus.menu.menudata.shop.ShopInterfaceItem;
import lee.code.shops.menus.system.*;
import lee.code.shops.utils.CoreUtil;
import lee.code.shops.utils.ItemUtil;
import lee.code.shops.utils.VariableUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class ShopItemMenu extends MenuGUI {
  private final MenuManager menuManager;
  private final Data data;
  private final ItemStack item;
  private final Rout rout;

  public ShopItemMenu(MenuManager menuManager, Data data, ItemStack item, Rout rout) {
    this.menuManager = menuManager;
    this.data = data;
    this.item = item;
    this.rout = rout;
    setInventory();
  }

  @Override
  protected Inventory createInventory() {
    return Bukkit.createInventory(null, 54, Lang.MENU_TITLE_SHOP.getComponent(null));
  }

  @Override
  public void decorate(Player player) {
    addFillerGlass();
    for (ShopInterfaceItem shopInterfaceItem : ShopInterfaceItem.values()) {
      addButton(shopInterfaceItem.getSlot(), createInterfaceButton(player, shopInterfaceItem));
    }
    addDisplayItemButtons();
    addBackButton(player);
    super.decorate(player);
  }

  private MenuButton createInterfaceButton(Player player, ShopInterfaceItem shopInterfaceItem) {
    switch (shopInterfaceItem.getShopType()) {
      case BUY -> {
        final int amount = shopInterfaceItem.getAmount() == 0 ? ItemUtil.getFreeSpace(player, item) : shopInterfaceItem.getAmount();
        final double value = data.getItemBuyValue(item);
        return new MenuButton()
          .creator(p-> shopInterfaceItem.createBuyInterfaceItem(value * amount, amount))
          .consumer(e -> {
            final int playerSpace = ItemUtil.getFreeSpace(player, item);
            final int playerAmount = shopInterfaceItem.getAmount() == 0 ? playerSpace : shopInterfaceItem.getAmount();
            final double playerCost = value * playerAmount;
            final boolean result = processBuyTransaction(player, item, playerCost, playerAmount);
            if (result) updateInventoryItems(player);
          });
      }
      case SELL -> {
        final int amount = shopInterfaceItem.getAmount() == 0 ? ItemUtil.getItemAmount(player, item) : shopInterfaceItem.getAmount();
        final double value = data.getItemSellValue(item);
        return new MenuButton()
          .creator(p-> shopInterfaceItem.createSellInterfaceItem(value * amount, amount))
          .consumer(e -> {
            final int inventoryAmount = ItemUtil.getItemAmount(player, item);
            final int playerAmount = shopInterfaceItem.getAmount() == 0 ? inventoryAmount : shopInterfaceItem.getAmount();
            final double playerReceive = value * playerAmount;
            final boolean result = processSellTransaction(player, item, playerReceive, playerAmount);
            if (result) updateInventoryItems(player);
          });
      }
    }
    return null;
  }

  private void addDisplayItemButtons() {
    final ItemStack displayItemOne = new ItemStack(item);
    addButton(13, new MenuButton().creator(p-> displayItemOne).consumer(e-> {}));
    final ItemStack displayItemTwo = new ItemStack(item);
    displayItemTwo.setAmount(16);
    addButton(22, new MenuButton().creator(p-> displayItemTwo).consumer(e-> {}));
    final ItemStack displayItemThree = new ItemStack(item);
    displayItemThree.setAmount(64);
    addButton(31, new MenuButton().creator(p-> displayItemThree).consumer(e-> {}));
  }

  private void addBackButton(Player player) {
    addButton(49, new MenuButton()
      .creator(p -> MenuItem.BACK_MENU.createItem())
      .consumer(e -> {
        menuManager.openMenu(new ShopCategoryMenu(menuManager, data, rout), player);
      }));
  }

  private void updateInventoryItems(Player player) {
    final double buyValue = data.getItemBuyValue(item);
    final int space = ItemUtil.getFreeSpace(player, item);
    final ItemStack buyInventory = ShopInterfaceItem.BUY_INVENTORY.createBuyInterfaceItem(buyValue * space, space);
    addButton(ShopInterfaceItem.BUY_INVENTORY.getSlot(), new MenuButton()
      .creator(p -> buyInventory)
      .consumer(e -> {
        final int playerSpace = ItemUtil.getFreeSpace(player, item);
        final double playerCost = buyValue * playerSpace;
        final boolean result = processBuyTransaction(player, item, playerCost, playerSpace);
        if (result) updateInventoryItems(player);
      }));
    getInventory().setItem(ShopInterfaceItem.BUY_INVENTORY.getSlot(), buyInventory);

    final int inventoryAmount = ItemUtil.getItemAmount(player, item);
    final double sellValue = data.getItemSellValue(item);
    final ItemStack sellInventory = ShopInterfaceItem.SELL_INVENTORY.createSellInterfaceItem( sellValue * inventoryAmount, inventoryAmount);
    addButton(ShopInterfaceItem.SELL_INVENTORY.getSlot(), new MenuButton()
      .creator(p -> sellInventory)
      .consumer(e -> {
        final int playerAmount = ItemUtil.getItemAmount(player, item);
        final double playerReceive = sellValue * playerAmount;
        final boolean result = processSellTransaction(player, item, playerReceive, playerAmount);
        if (result) updateInventoryItems(player);
      }));
    getInventory().setItem(ShopInterfaceItem.SELL_INVENTORY.getSlot(), sellInventory);
  }

  private boolean processBuyTransaction(Player player, ItemStack item, double cost, int amount) {
    final UUID playerID = player.getUniqueId();
    final double playerBalance = EcoAPI.getBalance(playerID);
    final int playerSpace = ItemUtil.getFreeSpace(player, item);
    if (playerBalance < cost) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SHOP_INTERFACE_ITEM_BUY_INSUFFICIENT_FUNDS.getComponent(new String[]{
        Lang.VALUE_FORMAT.getString(new String[]{CoreUtil.parseValue(cost)}),
        Lang.VALUE_FORMAT.getString(new String[]{CoreUtil.parseValue(playerBalance)}),
      })));
      return false;
    }
    if (amount == 0) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SHOP_INTERFACE_ITEM_BUY_AMOUNT_ZERO.getComponent(null)));
      return false;
    }
    if (playerSpace < amount) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SHOP_INTERFACE_ITEM_BUY_INVENTORY_SPACE.getComponent(null)));
      return false;
    }
    EcoAPI.removeBalance(playerID, cost);
    ItemUtil.giveItem(player, new ItemStack(item), amount);
    player.sendMessage(VariableUtil.parseVariables(Lang.PREFIX.getComponent(null).append(Lang.MENU_SHOP_INTERFACE_ITEM_BUY_SUCCESS.getComponent(new String[]{
      CoreUtil.parseValue(amount),
      Lang.VALUE_FORMAT.getString(new String[]{CoreUtil.parseValue(cost)})
    })), item));
    return true;
  }

  private boolean processSellTransaction(Player player, ItemStack item, double receive, int amount) {
    final UUID playerID = player.getUniqueId();
    final int inventoryAmount = ItemUtil.getItemAmount(player, item);
    if (inventoryAmount < amount) {
      player.sendMessage(VariableUtil.parseVariables(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SHOP_INTERFACE_ITEM_SELL_AMOUNT.getComponent(new String[]{
        CoreUtil.parseValue(amount)
      })), item));
      return false;
    }
    if (amount == 0) {
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_SHOP_INTERFACE_ITEM_SELL_AMOUNT_ZERO.getComponent(null)));
      return false;
    }
    ItemUtil.removePlayerItems(player, item, amount, false);
    EcoAPI.addBalance(playerID, receive);
    player.sendMessage(VariableUtil.parseVariables(Lang.PREFIX.getComponent(null).append(Lang.MENU_SHOP_INTERFACE_ITEM_SELL_SUCCESS.getComponent(new String[]{
      CoreUtil.parseValue(amount),
      Lang.VALUE_FORMAT.getString(new String[]{CoreUtil.parseValue(receive)})
    })), item));
    return true;
  }
}
