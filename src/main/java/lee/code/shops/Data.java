package lee.code.shops;

import lee.code.shops.enums.ItemValue;
import lee.code.shops.enums.SignBlock;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Data {
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
    for (ItemValue itemValue : ItemValue.values()) itemValueData.put(itemValue.getItem(), itemValue.name());
    for (SignBlock signBlock : SignBlock.values()) supportedSignBlocks.add(signBlock.getMaterial());
  }
}
