package lee.code.shops.lang;

import lee.code.shops.utils.CoreUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.Component;

@AllArgsConstructor
public enum Lang {
  PREFIX("&#F97D00&lShops &6➔ "),
  USAGE("&6&lUsage: &e{0}"),
  VALUE_FORMAT("&6${0}"),
  SELL("&c&lSell"),
  BUY("&2&lBuy"),
  ON("&2&lON"),
  OFF("&c&lOFF"),
  ITEM_CAPTURE_NAME("&5&lPet Capture Lead"),
  ITEM_CAPTURE_LORE("&7To capture a animal, fish or\n&7monster &eright-click &7one with\n&7this lead when they have\n&elow health&7."),
  COMMAND_SET_SPAWN_SUCCESS("&aYou successfully set your shop spawn!"),
  COMMAND_REMOVE_SPAWN_SUCCESS("&aYou successfully removed your shop spawn!"),
  COMMAND_NOTIFICATIONS_SUCCESS("&aYou successfully toggled player shop purchase notifications {0}&a!"),
  COMMAND_ADMIN_BYPASS_SUCCESS("&aYou successfully toggled player shop admin bypass {0}&a!"),
  COMMAND_ADMIN_REMOVE_SPAWN_SUCCESS("&aYou successfully removed &6{0}'s &aplayer shop spawn!"),
  COMMAND_HELP_DIVIDER("&a--------------------------------"),
  COMMAND_HELP_TITLE("&a--------- &7[ &#F97D00&lShop Help &7] &a---------"),
  COMMAND_HELP_SUB_COMMAND("&3{0}&b. &e{1}"),
  COMMAND_HELP_SUB_COMMAND_HOVER("&6{0}"),
  COMMAND_SIGN_HELP_DIVIDER("&2▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"),
  COMMAND_SIGN_HELP_TITLE("             &a&l&nHow to Create a Shop With a Sign"),
  COMMAND_SIGN_HELP_STEP_1("&6Step 1&7: &ePlace down a chest, shulker box or barrel."),
  COMMAND_SIGN_HELP_STEP_2("&6Step 2&7: &ePlace the item and stack size you want to sell/buy per-transaction in the container."),
  COMMAND_SIGN_HELP_STEP_3("&6Step 3&7: &ePlace a sign on the container and type the following:"),
  COMMAND_SIGN_HELP_STEP_3_SIGN_1("&6Line 1&7: &f[shop]"),
  COMMAND_SIGN_HELP_STEP_3_SIGN_2("&6Line 2&7: &f<buy/sell>"),
  COMMAND_SIGN_HELP_STEP_3_SIGN_3("&6Line 3&7: &f<cost>"),
  COMMAND_SIGN_HELP_DONE("&eCongratulations! If you followed this guide correctly you should have created a new shop with the container you placed the sign on!"),
  SHOP_SIGN_BREAK_SUCCESS("&7You broke a player shop sign."),
  SHOP_SIGN_SELL_SUCCESS("&aYou successfully sold &3x{0} {display-name-item} &afor {1} &ato &6{2}&a!"),
  SHOP_SIGN_SELL_OWNER_SUCCESS("&aThe player &6{0} &asold you &3x{1} {display-name-item} &afor {2}&a!"),
  SHOP_SIGN_BUY_SUCCESS("&aYou successfully bought &3x{0} {display-name-item} &afor {1}&a from &6{2}&a!"),
  SHOP_SIGN_BUY_OWNER_SUCCESS("&aThe player &6{0} &abought &3x{1} {display-name-item} &afor {2}&a!"),
  SHOP_SIGN_CREATE_SUCCESS("&aYou successfully created a new shop!"),
  SHOP_SIGN_INFO_HEADER("&a----- &e[ &2&lShop Info &e] &a-----"),
  SHOP_SIGN_INFO_OWNER("&3&lOwner&7: &e{0}"),
  SHOP_SIGN_INFO_TYPE("&3&lShop Type&7: &e{0}"),
  SHOP_SIGN_INFO_ITEM("&3&lItem&7: &e{display-name-item}"),
  SHOP_SIGN_INFO_AMOUNT("&3&lTransaction Amount&7: &e{0}"),
  SHOP_SIGN_INFO_COST("&3&lCost&7: {0}"),
  SHOP_SIGN_INFO_STOCK("&3&lStock&7: &e{0}"),
  SHOP_SIGN_INFO_SOLD("&3&lSold Total&7: &e{0}"),
  SHOP_SIGN_INFO_BOUGHT("&3&lBought Total&7: &e{0}"),
  SHOP_SIGN_INFO_FOOTER("&a------------------------"),
  SHOP_SIGN_TITLE("&2[&a&lShop&2]"),
  SHOP_SIGN_TITLE_OUT_OF_STOCK_OR_FULL("&7[&7&lShop&7]"),
  SHOP_SIGN_TYPE("{0}"),
  SHOP_SIGN_AMOUNT("&3x{0} {display-name-item}"),
  SHOP_SIGN_COST("{0}"),
  MENU_TITLE_SHOP("&#F97D00&lShops"),
  MENU_TITLE_CATEGORY_SHOP("&#F97D00&lShops &8-> &e&l{0}"),
  MENU_SHOP_PLAYER_TELEPORT_SUCCESSFUL("&aYou successfully teleported to &6{0}'s &ashop!"),
  MENU_SHOP_PLAYER_TELEPORT_FAILED("&cFailed to teleport to &6{0}'s &cshop."),
  MENU_TITLE_SHOP_PLAYER("&5&lPlayer Shops"),
  MENU_SHOP_PLAYER_HEAD_NAME("{0}'s Shop"),
  MENU_SHOP_PLAYER_HEAD_LORE("&6» &aClick to teleport to shop!"),
  MENU_TITLE_SHOP_SIGN_PREVIEW("&2&lShop Item Preview"),
  MENU_SHOP_CATEGORY_ITEM_LORE("&6» &aClick to open shop!"),
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
  ERROR_SHOP_SIGN_BUY_INSUFFICIENT_STOCK("&cThis shop does not have &3x{0} {display-name-item} &cto sell."),
  ERROR_SHOP_SIGN_SELL_NO_SPACE("&cThis shop does not have enough space to process this transaction."),
  ERROR_SHOP_SIGN_SELL_OWNER_INSUFFICIENT_FUNDS("&cThe owner of this shop does not have enough money process this transaction."),
  ERROR_SHOP_SIGN_SELL_PLAYER_INSUFFICIENT_ITEMS("&cYou do not have &3x{0} {display-name-item} &cto sell."),
  ERROR_SHOP_SIGN_CREATE_NO_ITEM("&cYou need to put the item you want to sell in the container along with the stack size of your desired per-transaction item amount."),
  ERROR_SHOP_SIGN_CREATE_ALREADY_HAS_SIGN("&cThis container already has a shop sign, so another cannot be created on it."),
  ERROR_SHOP_SIGN_CREATE_NOT_SUPPORTED_BLOCK("&cYou cannot create a shop sign on the block &3{0}&c."),
  ERROR_SHOP_SIGN_CREATE_HANGING_SIGN("&cYou cannot create a shop with a hanging sign."),
  ERROR_SHOP_SIGN_CREATE_DIRECTION("&cYou can only create shop signs attached to supported containers."),
  ERROR_SHOP_SIGN_CREATE_INVALID_VALUE("&cThe input &3{0} &cis a invalid value."),
  ERROR_SHOP_SIGN_CREATE_INVALID_TYPE("&cThe input &3{0} &cis a invalid shop type."),
  ERROR_SHOP_SIGN_CREATE_INVALID_FORMAT("&cInvalid shop sign, to learn how to create one correctly run the command &e/shop signhelp&c."),
  ERROR_NOT_CONSOLE_COMMAND("&cThis command does not work in console."),
  ERROR_NO_PLAYER_DATA("&cCould not find any player data for &6{0}&c."),
  ERROR_COMMAND_SPAWN_NO_SPAWN("&cThe player &6{0} &cdoes not have a shop spawn set."),
  ERROR_COMMAND_SPAWN_NOT_SAFE("&cThe player &6{0} &cdoes not have a safe shop spawn. Please contact the shop owner to fix this."),
  ERROR_COMMAND_REMOVE_SPAWN_NO_SPAWN("&cYou don't have a player shop spawn set."),
  ERROR_COMMAND_ADMIN_REMOVE_SPAWN_NO_SPAWN("&cThe player &6{0} &cdoes not have a player shop spawn."),

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
