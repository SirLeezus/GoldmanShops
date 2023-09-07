package lee.code.shops.lang;

import lee.code.shops.utils.CoreUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.Component;

@AllArgsConstructor
public enum Lang {
  PREFIX("&#FF9719&lShops &e➔ "),
  VALUE_FORMAT("&6${0}"),
  SHOP_SIGN_BREAK_SUCCESS("&7You broke a shop sign."),
  SHOP_SIGN_SELL_SUCCESS("&aYou successfully sold &3x{0} {display-name-item} &afor {1}&a!"),
  SHOP_SIGN_BUY_SUCCESS("&aYou successfully bought &3x{0} {display-name-item} &afor {1}&a!"),
  SHOP_SIGN_CREATE_SUCCESS("&aYou successfully created a new shop!"),
  SHOP_SIGN_INFO_HEADER("&a----- &e[ &2&lShop Info &e] &a-----"),
  SHOP_SIGN_INFO_OWNER("&3&lOwner&7: &e{0}"),
  SHOP_SIGN_INFO_TYPE("&3&lShop Type&7: &e{0}"),
  SHOP_SIGN_INFO_ITEM("&3&lItem&7: &e{display-name-item}"),
  SHOP_SIGN_INFO_AMOUNT("&3&lTransaction Amount&7: &e{0}"),
  SHOP_SIGN_INFO_PRICE("&3&lPrice&7: {0}"),
  SHOP_SIGN_INFO_STOCK("&3&lStock&7: &e{0}"),
  SHOP_SIGN_INFO_SOLD("&3&lSold Total&7: &e{0}"),
  SHOP_SIGN_INFO_BOUGHT("&3&lBought Total&7: &e{0}"),
  SHOP_SIGN_INFO_FOOTER("&a--------------------------"),
  SHOP_SIGN_TITLE("&2[&a&lShop&2]"),
  SHOP_SIGN_TITLE_OUT_OF_STOCK_OR_FULL("&7[&7&lShop&7]"),
  SELL("&c&lSell"),
  BUY("&2&lBuy"),
  SHOP_SIGN_TYPE("{0}"),
  SHOP_SIGN_AMOUNT("&3x{0} {display-name-item}"),
  SHOP_SIGN_COST("{0}"),
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
  ERROR_SHOP_SIGN_NOT_OWNER_OPEN("&cOnly the owner of the shop can open this."),
  ERROR_SHOP_SIGN_NOT_OWNER_BREAK("&cOnly the owner of the shop can break this."),
  ERROR_SHOP_SIGN_BUY_INSUFFICIENT_SPACE("&cYou do not have enough inventory space for this transaction."),
  ERROR_SHOP_SIGN_BUY_INSUFFICIENT_FUNDS("&cYou do not have enough money to process this transaction."),
  ERROR_SHOP_SIGN_BUY_INSUFFICIENT_STOCK("&cThis shop does not have enough stock to process this transaction."),
  ERROR_SHOP_SIGN_SELL_NO_SPACE("&cThis shop does not have enough space to process this transaction."),
  ERROR_SHOP_SIGN_SELL_OWNER_INSUFFICIENT_FUNDS("&cThe owner of this shop does not have enough money process this transaction."),
  ERROR_SHOP_SIGN_SELL_PLAYER_INSUFFICIENT_ITEMS("&cYou do not have &3x{0} {display-name-item} &cto to sell."),
  ERROR_SHOP_SIGN_CREATE_NO_ITEM("&cYou need to put the item you want to sell in the container along with the stack size of your desired per-transaction item amount."),
  ERROR_SHOP_SIGN_CREATE_ALREADY_HAS_SIGN("&cThis container already has a shop sign, so another cannot be created on it."),
  ERROR_SHOP_SIGN_CREATE_NOT_SUPPORTED_BLOCK("&cYou cannot create a shop sign on the block &3{0}&c."),
  ERROR_SHOP_SIGN_CREATE_HANGING_SIGN("&cYou cannot create a shop with a hanging sign."),
  ERROR_SHOP_SIGN_CREATE_DIRECTION("&cYou can only create shop signs attached to supported containers."),
  ERROR_SHOP_SIGN_CREATE_INVALID_VALUE("&cThe input &3{0} &cis a invalid value."),
  ERROR_SHOP_SIGN_CREATE_INVALID_TYPE("&cThe input &3{0} &cis a invalid shop type."),
  ERROR_SHOP_SIGN_CREATE_INVALID_FORMAT("&cInvalid shop sign, to learn how to create one correctly run the command &e/shop signhelp&c."),


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
