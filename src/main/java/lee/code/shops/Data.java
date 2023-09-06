package lee.code.shops;

import lee.code.shops.enums.ItemValue;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ConcurrentHashMap;

public class Data {
  private final ConcurrentHashMap<ItemStack, String> itemValueData = new ConcurrentHashMap<>();

  public double getItemBuyValue(ItemStack item) {
    if (!itemValueData.containsKey(item)) return 0;
    return ItemValue.valueOf(itemValueData.get(item)).getBuy();
  }

  public double getItemSellValue(ItemStack item) {
    if (!itemValueData.containsKey(item)) return 0;
    return ItemValue.valueOf(itemValueData.get(item)).getSell();
  }

  public Data() {
    loadData();
  }

  private void loadData() {
    for (ItemValue itemValue : ItemValue.values()) itemValueData.put(itemValue.getItem(), itemValue.name());
  }
}
