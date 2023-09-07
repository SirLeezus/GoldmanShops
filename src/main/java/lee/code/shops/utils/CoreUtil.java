package lee.code.shops.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;

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

  public static String serializeLocation(Location location) {
    if (location == null) return null;
    else if (location.getWorld() == null) return null;
    return location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getYaw() + "," + location.getPitch();
  }

  public static Location parseLocation(String location) {
    if (location == null) return null;
    final String[] split = location.split(",", 6);
    return new Location(Bukkit.getWorld(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]), (float) Double.parseDouble(split[4]), (float) Double.parseDouble(split[5]));
  }
}
