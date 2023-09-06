package lee.code.shops.lang;

import lee.code.shops.utils.CoreUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.Component;

@AllArgsConstructor
public enum Lang {
  PREFIX("&#FF9719&lShops &e➔ "),
  VALUE_FORMAT("&6${0}"),
  MENU_TITLE_SHOP("&#FF9719&lShop"),
  MENU_SHOP_CATEGORY_ITEM_LORE("&7» Click to open category menu!"),
  MENU_SHOP_ITEM_LORE("&a&lBuy&7: {0}\n&c&lSell&7: {1}"),
  MENU_SHOP_INTERFACE_ITEM_BUY_LORE("&eAmount&7: &2{0}\n&eCost&7: {1}"),
  MENU_SHOP_INTERFACE_ITEM_SELL_LORE("&eAmount&7: &2{0}\n&eReceive&7: {1}"),
  MENU_SHOP_INTERFACE_ITEM_BUY_SUCCESS("&aYou successfully bought &3x{0} {display-name-item} &afor {1}&a!"),
  MENU_SHOP_INTERFACE_ITEM_SELL_SUCCESS("&aYou successfully sold &3x{0} {display-name-item} &afor {1}&a!"),
  SPAWNER_NAME("&e{0} Spawner"),
  ERROR_PREVIOUS_PAGE("&7You are already on the first page."),
  ERROR_NEXT_PAGE("&7You are on the last page."),
  ERROR_SHOP_INTERFACE_ITEM_BUY_INVENTORY_SPACE("&cYou do not have enough inventory space for this transaction."),
  ERROR_SHOP_INTERFACE_ITEM_BUY_AMOUNT_ZERO("&cYou can't buy zero items."),
  ERROR_SHOP_INTERFACE_ITEM_SELL_AMOUNT_ZERO("&cYou can't sell zero items."),
  ERROR_SHOP_INTERFACE_ITEM_BUY_INSUFFICIENT_FUNDS("&cYou need at least {0} &cfor this transaction but you only have {1}&c."),
  ERROR_SHOP_INTERFACE_ITEM_SELL_AMOUNT("&cYou do not have &3x{0} {display-name-item} &cto sell."),
  ERROR_NO_PERMISSION("&cYou do not have permission for this."),
  ERROR_ONE_COMMAND_AT_A_TIME("&cYou're currently processing another command, please wait for it to finish."),

  ;
  @Getter private final String string;

  public String getString(String[] variables) {
    String value = string;
    if (variables == null || variables.length == 0) return value;
    for (int i = 0; i < variables.length; i++) value = value.replace("{" + i + "}", variables[i]);
    return value;
  }

  public Component getComponent(String[] variables) {
    String value = string;
    if (variables == null || variables.length == 0) return CoreUtil.parseColorComponent(value);
    for (int i = 0; i < variables.length; i++) value = value.replace("{" + i + "}", variables[i]);
    return CoreUtil.parseColorComponent(value);
  }
}
