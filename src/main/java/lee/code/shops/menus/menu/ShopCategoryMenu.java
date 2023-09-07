package lee.code.shops.menus.menu;

import lee.code.shops.Data;
import lee.code.shops.lang.Lang;
import lee.code.shops.menus.menu.menudata.MenuItem;
import lee.code.shops.menus.menu.menudata.shop.Rout;
import lee.code.shops.menus.menu.menudata.shop.category.RedstoneItem;
import lee.code.shops.menus.menu.menudata.shop.category.ToolItem;
import lee.code.shops.menus.system.MenuButton;
import lee.code.shops.menus.system.MenuManager;
import lee.code.shops.menus.system.MenuPaginatedGUI;
import lee.code.shops.utils.CoreUtil;
import lee.code.shops.utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ShopCategoryMenu extends MenuPaginatedGUI {
  private final MenuManager menuManager;
  private final Data data;
  private final Rout rout;

  public ShopCategoryMenu(MenuManager menuManager, Data data, Rout rout) {
    this.menuManager = menuManager;
    this.rout = rout;
    this.data =  data;
    setInventory();
  }

  @Override
  protected Inventory createInventory() {
    return Bukkit.createInventory(null, 54, Lang.MENU_TITLE_SHOP.getComponent(null));
  }

  @Override
  public void decorate(Player player) {
    addBorderGlass();
    final List<ItemStack> items = getCategoryItems(rout, true);
    int slot = 0;
    for (int i = 0; i < maxItemsPerPage; i++) {
      index = maxItemsPerPage * page + i;
      if (index >= items.size()) break;
      final ItemStack targetItem = items.get(index);
      addButton(paginatedSlots.get(slot), createButton(player, targetItem));
      slot++;
    }
    addPaginatedButtons(player);
    super.decorate(player);
  }

  private MenuButton createButton(Player player, ItemStack itemStack) {
    final ItemStack displayItem = new ItemStack(itemStack);
    final ItemMeta itemMeta = displayItem.getItemMeta();
    ItemUtil.setItemLore(itemMeta, Lang.MENU_SHOP_ITEM_LORE.getString(new String[]{
      Lang.VALUE_FORMAT.getString(new String[]{CoreUtil.parseValue(data.getItemBuyValue(itemStack))}),
      Lang.VALUE_FORMAT.getString(new String[]{CoreUtil.parseValue(data.getItemSellValue(itemStack))})
    }));
    displayItem.setItemMeta(itemMeta);
    ItemUtil.hideItemFlags(displayItem);
    return new MenuButton()
      .creator(p-> displayItem)
      .consumer(e -> {
        menuManager.openMenu(new ShopItemMenu(menuManager, data, itemStack, rout), player);
      });
  }

  private List<ItemStack> getCategoryItems(Rout rout, boolean sort) {
    final List<ItemStack> items = new ArrayList<>();
    switch (rout) {
      case REDSTONE -> {
        for (RedstoneItem redstoneItem : RedstoneItem.values()) items.add(redstoneItem.getItem());
      }
      case TOOLS -> {
        for (ToolItem toolItem : ToolItem.values()) items.add(toolItem.getItem());
      }
    }
    if (sort) {
      items.sort((item1, item2) -> {
        final String name1 = item1.getType().name();
        final String name2 = item2.getType().name();
        return name1.compareTo(name2);
      });
    }
    return items;
  }

  private void addPaginatedButtons(Player player) {
    addButton(51, new MenuButton().creator(p -> MenuItem.NEXT_PAGE.createItem())
      .consumer(e -> {
        if (!((index + 1) >= getCategoryItems(rout, false).size())) {
          page += 1;
          clearInventory();
          clearButtons();
          decorate(player);
        } else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NEXT_PAGE.getComponent(null)));
      }));
    addButton(47, new MenuButton().creator(p -> MenuItem.PREVIOUS_PAGE.createItem())
      .consumer(e -> {
        if (page == 0) {
          player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PREVIOUS_PAGE.getComponent(null)));
        } else {
          page -= 1;
          clearInventory();
          clearButtons();
          decorate(player);
        }
      }));
    addButton(49, new MenuButton()
      .creator(p -> MenuItem.BACK_MENU.createItem())
      .consumer(e -> {
        page = 0;
        menuManager.openMenu(new ShopMainMenu(menuManager, data), player);
      }));
  }
}
