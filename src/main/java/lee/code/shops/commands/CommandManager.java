package lee.code.shops.commands;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import lee.code.shops.Shops;
import lee.code.shops.commands.cmds.*;
import lee.code.shops.lang.Lang;
import lee.code.shops.menus.menu.ShopMainMenu;
import lee.code.shops.menus.system.MenuManager;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CommandManager implements CommandExecutor {
  private final Object synchronizedThreadLock = new Object();
  @Getter private final ConcurrentHashMap<String, SubCommand> subCommands = new ConcurrentHashMap<>();
  private final ConcurrentHashMap<UUID, ScheduledTask> asyncTasks = new ConcurrentHashMap<>();
  private final Shops shops;

  public CommandManager(Shops shops) {
    this.shops = shops;
    storeSubCommands();
  }

  private void storeSubCommands() {
    storeSubCommand(new PlayerShopsCMD(shops));
    storeSubCommand(new SpawnCMD(shops));
    storeSubCommand(new RemoveSpawnCMD(shops));
    storeSubCommand(new SetSpawnCMD(shops));
    storeSubCommand(new NotificationsCMD(shops));
    storeSubCommand(new HelpCMD(shops));
    storeSubCommand(new AdminCMD(shops));
    storeSubCommand(new SignHelpCMD());
  }

  private void storeSubCommand(SubCommand subCommand) {
    subCommands.put(subCommand.getName(), subCommand);
  }

  public SubCommand getSubCommand(String command) {
    return subCommands.get(command);
  }

  public List<SubCommand> getSubCommandList() {
    return new ArrayList<>(subCommands.values());
  }

  @Override
  public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String[] args) {
    if (args.length > 0) {
      if (sender instanceof Player player) {
        if (subCommands.containsKey(args[0].toLowerCase())) {
          final SubCommand subCommand = getSubCommand(args[0].toLowerCase());
          if (!player.hasPermission(subCommand.getPermission())) {
            player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NO_PERMISSION.getComponent(null)));
            return true;
          }
          if (subCommand.performAsync()) performAsync(player, subCommand, args);
          else subCommand.perform(player, args);
          return true;
        }
      } else {
        if (subCommands.containsKey(args[0].toLowerCase())) {
          final SubCommand subCommand = getSubCommand(args[0].toLowerCase());
          if (subCommand.performAsync()) performAsync(sender, subCommand, args);
          else subCommand.performConsole(sender, args);
          return true;
        }
      }
    }
    if (sender instanceof Player player) {
      final MenuManager menuManager = shops.getMenuManager();
      menuManager.openMenu(new ShopMainMenu(menuManager, shops), player);
    } else {
      performAsync(sender, getSubCommand("help"), args);
    }
    return true;
  }

  public void performAsync(CommandSender sender, SubCommand subCommand, String[] args) {
    if (sender instanceof Player player) {
      final UUID uuid = player.getUniqueId();
      if (asyncTasks.containsKey(uuid)) {
        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_ONE_COMMAND_AT_A_TIME.getComponent(null)));
        return;
      }
      if (subCommand.performAsyncSynchronized()) {
        synchronized (synchronizedThreadLock) {
          performPlayerSubCommandAsync(player, uuid, subCommand, args);
        }
      } else {
        performPlayerSubCommandAsync(player, uuid, subCommand, args);
      }
    } else if (sender instanceof ConsoleCommandSender) {
      if (subCommand.performAsyncSynchronized()) {
        synchronized (synchronizedThreadLock) {
          performConsoleSubCommandAsync(sender, args, subCommand);
        }
      } else {
        performConsoleSubCommandAsync(sender, args, subCommand);
      }
    }
  }

  private void performPlayerSubCommandAsync(Player player, UUID uuid, SubCommand subCommand, String[] args) {
    asyncTasks.put(uuid, Bukkit.getAsyncScheduler().runNow(shops, scheduledTask -> {
      try {
        subCommand.perform(player, args);
      } finally {
        asyncTasks.remove(uuid);
      }
    }));
  }

  private void performConsoleSubCommandAsync(CommandSender sender, String[] args, SubCommand subCommand) {
    Bukkit.getAsyncScheduler().runNow(shops, scheduledTask -> subCommand.performConsole(sender, args));
  }
}
