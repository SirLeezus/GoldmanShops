package lee.code.shops.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.apache.commons.lang3.text.WordUtils;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

public class CoreUtil {
  private final static DecimalFormat amountFormatter = new DecimalFormat("#,###.##");
  private final static Pattern numberDoublePattern = Pattern.compile("^(?=.*[1-9])(\\d*\\.?\\d*)$");

  public static String parseValue(int value) {
    if (value == 0) return "0";
    return amountFormatter.format(value);
  }

  public static String parseValue(double value) {
    if (value == 0) return "0";
    return amountFormatter.format(value);
  }

  public static Component parseColorComponent(String text) {
    final LegacyComponentSerializer serializer = LegacyComponentSerializer.legacyAmpersand();
    return (Component.empty().decoration(TextDecoration.ITALIC, false)).append(serializer.deserialize(text));
  }

  @SuppressWarnings("deprecation")
  public static String capitalize(String message) {
    final String format = message.toLowerCase().replaceAll("_", " ");
    return WordUtils.capitalize(format);
  }

  public static boolean isPositiveDoubleNumber(String numbers) {
    return numberDoublePattern.matcher(numbers).matches();
  }
}
