package lee.code.shops.utils;

import lee.code.shops.lang.Lang;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

  public static List<String> getOnlinePlayers() {
    return Bukkit.getOnlinePlayers().stream()
      .filter(player -> !player.getGameMode().equals(GameMode.SPECTATOR))
      .map(Player::getName)
      .collect(Collectors.toList());
  }

  public static <K, V extends Comparable<? super V>> HashMap<K, V> sortByValue(Map<K, V> hm, Comparator<V> comparator) {
    final HashMap<K, V> temp = new LinkedHashMap<>();
    hm.entrySet().stream()
      .sorted(Map.Entry.comparingByValue(comparator))
      .forEachOrdered(entry -> temp.put(entry.getKey(), entry.getValue()));
    return temp;
  }

  public static String getTextBeforeCharacter(String input, char character) {
    final int index = input.indexOf(character);
    if (index == -1) return input;
    else return input.substring(0, index);
  }

  public static void teleportShop(Player player, Location targetLocation, String targetShop) {
    player.getWorld().getChunkAtAsync(targetLocation).thenAccept(chunk -> {
      final Block targetBlock1 = targetLocation.clone().add(0, 2, 0).getBlock();
      final Block targetBlock2 = targetLocation.clone().add(0, 1, 0).getBlock();
      final Block targetBlock3 = targetLocation.getBlock();
      if (!targetBlock1.getType().isAir() || !targetBlock2.getType().isAir() || !targetBlock3.getType().isAir()) {
        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_SPAWN_NOT_SAFE.getComponent(new String[]{targetShop})));
        return;
      }
      final Block targetBlock4 = targetLocation.clone().subtract(0, 1, 0).getBlock();
      final Material material = targetBlock4.getType();
      if (material.isAir() || material.equals(Material.LAVA) || material.equals(Material.FIRE) || material.equals(Material.COBWEB)) {
        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_COMMAND_SPAWN_NOT_SAFE.getComponent(new String[]{targetShop})));
        return;
      }
      player.teleportAsync(targetLocation).thenAccept(result -> {
        if (result) player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.MENU_SHOP_PLAYER_TELEPORT_SUCCESSFUL.getComponent(new String[]{targetShop})));
        else player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.MENU_SHOP_PLAYER_TELEPORT_FAILED.getComponent(new String[]{targetShop})));
      });
    });
  }
}
