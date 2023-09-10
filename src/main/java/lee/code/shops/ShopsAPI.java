package lee.code.shops;

import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;
import java.util.Map;

public class ShopsAPI {

  public static double getItemSellValue(ItemStack item) {
    return Shops.getInstance().getData().getItemSellValue(item);
  }

  public static Map<ItemStack, Double> getAllItemSellValues() {
    return new LinkedHashMap<>(Shops.getInstance().getData().getItemSellValueSortedData());
  }
}
