package lee.code.shops.menus.system;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class MenuSoundManager {

  public void playClickSound(Player player) {
    player.playSound(player, Sound.UI_BUTTON_CLICK, (float) 1, (float) 1);
  }

  public void playPurchaseSound(Player player) {
    player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, (float) 1, (float) 1);
  }

  public void playSellSound(Player player) {
    player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, (float) 1, (float) 1);
  }

  public void playErrorSound(Player player) {
    player.playSound(player, Sound.ENTITY_VILLAGER_NO, (float) 1, (float) 1);
  }
}
