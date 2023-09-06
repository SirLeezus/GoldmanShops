package lee.code.shops.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.regex.Pattern;

public class VariableUtil {
  private static final Pattern displayNamePattern = Pattern.compile("\\{display-name-item\\}");

  public static Component parseVariables(Component component, ItemStack itemStack) {
    Component targetMessage = component;
    final ItemMeta itemMeta = itemStack.getItemMeta();
    if (itemMeta == null) return targetMessage;
    if (itemMeta.hasDisplayName()) targetMessage = targetMessage.replaceText(createTextReplacementConfig(displayNamePattern, itemStack.getItemMeta().displayName()));
    if (!itemMeta.hasDisplayName()) targetMessage = targetMessage.replaceText(createTextReplacementConfig(displayNamePattern, CoreUtil.parseColorComponent(CoreUtil.capitalize(itemStack.getType().name()))));
    return targetMessage;
  }

  private static TextReplacementConfig createTextReplacementConfig(Pattern pattern, String message) {
    return TextReplacementConfig.builder().match(pattern).replacement(message).build();
  }

  private static TextReplacementConfig createTextReplacementConfig(Pattern pattern, Component message) {
    return TextReplacementConfig.builder().match(pattern).replacement(message).build();
  }
}
