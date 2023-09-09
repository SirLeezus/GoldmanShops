package lee.code.shops.menus.menu;

import lee.code.shops.Data;
import lee.code.shops.Shops;
import lee.code.shops.lang.Lang;
import lee.code.shops.menus.menu.menudata.MenuItem;
import lee.code.shops.menus.menu.menudata.shop.Rout;
import lee.code.shops.menus.menu.menudata.shop.category.*;
import lee.code.shops.menus.system.MenuButton;
import lee.code.shops.menus.system.MenuManager;
import lee.code.shops.menus.system.MenuPaginatedGUI;
import lee.code.shops.utils.CoreUtil;
import lee.code.shops.utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ShopCategoryMenu extends MenuPaginatedGUI {
  private final MenuManager menuManager;
  private final Shops shops;
  private final Rout rout;
  private int currentPage;

  public ShopCategoryMenu(MenuManager menuManager, Shops shops, Rout rout, int currentPage) {
    this.menuManager = menuManager;
    this.rout = rout;
    this.shops = shops;
    this.currentPage = currentPage;
    setInventory();
  }

  @Override
  protected Inventory createInventory() {
    return Bukkit.createInventory(null, 54, Lang.MENU_TITLE_CATEGORY_SHOP.getComponent(new String[]{CoreUtil.capitalize(rout.name())}));
  }

  @Override
  public void decorate(Player player) {
    addBorderGlass();
    final List<ItemStack> items = getCategoryItems(rout);
    int slot = 0;
    page = currentPage;
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
      Lang.VALUE_FORMAT.getString(new String[]{CoreUtil.parseValue(shops.getData().getItemBuyValue(itemStack))}),
      Lang.VALUE_FORMAT.getString(new String[]{CoreUtil.parseValue(shops.getData().getItemSellValue(itemStack))})
    }));
    displayItem.setItemMeta(itemMeta);
    ItemUtil.hideItemFlags(displayItem);
    return new MenuButton()
      .creator(p-> displayItem)
      .consumer(e -> {
        menuManager.openMenu(new ShopItemMenu(menuManager, shops, itemStack, rout, currentPage), player);
      });
  }

  private List<ItemStack> getCategoryItems(Rout rout) {
    final List<ItemStack> items = new ArrayList<>();
    switch (rout) {
      case TOOL -> {
        for (ToolItem toolItem : ToolItem.values()) items.add(toolItem.getItem());
      }
      case CROP -> {
        for (CropItem cropItem : CropItem.values()) items.add(cropItem.getItem());
      }
      case WOOD -> {
        for (WoodItem woodItem : WoodItem.values()) items.add(woodItem.getItem());
      }
      case DYE -> {
        for (DyeItem dyeItem : DyeItem.values()) items.add(dyeItem.getItem());
      }
      case BASIC_BLOCK -> {
        for (BasicBlockItem basicBlockItem : BasicBlockItem.values()) items.add(basicBlockItem.getItem());
      }
      case VEHICLE -> {
        for (VehicleItem vehicleItem : VehicleItem.values()) items.add(vehicleItem.getItem());
      }
      case ARMOR -> {
        for (ArmorItem armorItem : ArmorItem.values()) items.add(armorItem.getItem());
      }
      case SPAWNER -> {
        for (SpawnerItem spawnerItem : SpawnerItem.values()) items.add(spawnerItem.getItem());
      }
    }
    return items;
  }

  private void addPaginatedButtons(Player player) {
    addButton(51, new MenuButton().creator(p -> MenuItem.NEXT_PAGE.createItem())
      .consumer(e -> {
        if (!((index + 1) >= getCategoryItems(rout).size())) {
          page += 1;
          currentPage = page;
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
          currentPage = page;
          clearInventory();
          clearButtons();
          decorate(player);
        }
      }));
    addButton(49, new MenuButton()
      .creator(p -> MenuItem.BACK_MENU.createItem())
      .consumer(e -> {
        page = 0;
        menuManager.openMenu(new ShopMainMenu(menuManager, shops), player);
      }));
  }
}
