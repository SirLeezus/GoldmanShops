package lee.code.shops;

import lee.code.shops.enums.ItemValue;
import lee.code.shops.enums.SignBlock;
import lee.code.shops.utils.CoreUtil;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Data {
  @Getter private final LinkedHashMap<ItemStack, Double> itemSellValueSortedData = new LinkedHashMap<>();
  private final ConcurrentHashMap<ItemStack, String> itemValueData = new ConcurrentHashMap<>();
  @Getter private final Set<Material> supportedSignBlocks = new HashSet<>();

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
    final HashMap<ItemStack, Double> itemSellValueMap = new HashMap<>();
    for (ItemValue itemValue : ItemValue.values()) {
      if (itemValue.getSell() > 0) itemSellValueMap.put(itemValue.getItem(), itemValue.getSell());
      itemValueData.put(itemValue.getItem(), itemValue.name());
    }
    itemSellValueSortedData.putAll(CoreUtil.sortByValue(itemSellValueMap, Comparator.reverseOrder()));
    for (SignBlock signBlock : SignBlock.values()) supportedSignBlocks.add(signBlock.getMaterial());
  }
}
