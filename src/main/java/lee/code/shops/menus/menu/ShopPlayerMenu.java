package lee.code.shops.menus.menu;

import lee.code.colors.ColorAPI;
import lee.code.playerdata.PlayerDataAPI;
import lee.code.shops.database.cache.data.ShopSpawnData;
import lee.code.shops.lang.Lang;
import lee.code.shops.menus.menu.menudata.MenuItem;
import lee.code.shops.menus.system.MenuButton;
import lee.code.shops.menus.system.MenuPaginatedGUI;
import lee.code.shops.utils.CoreUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ShopPlayerMenu extends MenuPaginatedGUI {
  private final ShopSpawnData shopSpawnData;

  public ShopPlayerMenu(ShopSpawnData shopSpawnData) {
    this.shopSpawnData = shopSpawnData;
    setInventory();
  }

  @Override
  protected Inventory createInventory() {
    return Bukkit.createInventory(null, 54, Lang.MENU_TITLE_SHOP_PLAYER.getComponent(null));
  }

  @Override
  public void decorate(Player player) {
    addBorderGlass();
    final ConcurrentHashMap<UUID, String> shopMap = shopSpawnData.getAllShopSpawns();
    final List<UUID> players = new ArrayList<>(shopMap.keySet());
    int slot = 0;
    for (int i = 0; i < maxItemsPerPage; i++) {
      index = maxItemsPerPage * page + i;
      if (index >= players.size()) break;
      final UUID shopOwner = players.get(index);
      addButton(paginatedSlots.get(slot), createButton(player, shopOwner, shopMap.get(shopOwner)));
      slot++;
    }
    addPaginatedButtons(player);
    super.decorate(player);
  }

  private MenuButton createButton(Player player, UUID shopOwner, String location) {
    final String targetName = ColorAPI.getNameColor(shopOwner, PlayerDataAPI.getName(shopOwner));
    final ItemStack head = PlayerDataAPI.getPlayerHead(shopOwner);
    final ItemMeta headMeta = head.getItemMeta();
    headMeta.displayName(Lang.MENU_SHOP_PLAYER_HEAD_NAME.getComponent(new String[]{targetName}));
    headMeta.lore(Collections.singletonList(Lang.MENU_SHOP_PLAYER_HEAD_LORE.getComponent(null)));
    head.setItemMeta(headMeta);
    return new MenuButton()
      .creator(p -> head)
      .consumer(e -> {
        getMenuSoundManager().playClickSound(player);
        player.teleportAsync(CoreUtil.parseLocation(location)).thenAccept(result -> {
          if (result) player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.MENU_SHOP_PLAYER_TELEPORT_SUCCESSFUL.getComponent(new String[]{targetName})));
          else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.MENU_SHOP_PLAYER_TELEPORT_FAILED.getComponent(new String[]{targetName})));
        });
      });
  }

  private void addPaginatedButtons(Player player) {
    addButton(51, new MenuButton().creator(p -> MenuItem.NEXT_PAGE.createItem())
      .consumer(e -> {
        if (!((index + 1) >= shopSpawnData.getAllShopSpawns().size())) {
          page += 1;
          getMenuSoundManager().playClickSound(player);
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
          getMenuSoundManager().playClickSound(player);
          clearInventory();
          clearButtons();
          decorate(player);
        }
      }));
  }
}
