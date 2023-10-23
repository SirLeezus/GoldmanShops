package lee.code.shops;

import com.mojang.brigadier.tree.LiteralCommandNode;
import lee.code.shops.commands.CommandManager;
import lee.code.shops.commands.TabCompletion;
import lee.code.shops.database.CacheManager;
import lee.code.shops.database.DatabaseManager;
import lee.code.shops.listeners.JoinListener;
import lee.code.shops.listeners.SellWandListener;
import lee.code.shops.listeners.ShopSignListener;
import lee.code.shops.managers.DelayManager;
import lee.code.shops.menus.system.MenuListener;
import lee.code.shops.menus.system.MenuManager;
import lombok.Getter;
import me.lucko.commodore.CommodoreProvider;
import me.lucko.commodore.file.CommodoreFileReader;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class Shops extends JavaPlugin {
  @Getter private static Shops instance;
  @Getter private CommandManager commandManager;
  @Getter private MenuManager menuManager;
  @Getter private Data data;
  @Getter private CacheManager cacheManager;
  @Getter private DelayManager delayManager;
  private DatabaseManager databaseManager;

  @Override
  public void onEnable() {
    instance = this;
    this.databaseManager = new DatabaseManager(this);
    this.cacheManager = new CacheManager(this, databaseManager);
    this.commandManager = new CommandManager(this);
    this.menuManager = new MenuManager();
    this.delayManager = new DelayManager(this);
    this.data = new Data();

    databaseManager.initialize(false);
    registerCommands();
    registerListeners();
  }

  @Override
  public void onDisable() {
    databaseManager.closeConnection();
  }

  private void registerListeners() {
    getServer().getPluginManager().registerEvents(new MenuListener(menuManager), this);
    getServer().getPluginManager().registerEvents(new ShopSignListener(this), this);
    getServer().getPluginManager().registerEvents(new JoinListener(this), this);
    getServer().getPluginManager().registerEvents(new SellWandListener(this), this);
  }

  private void registerCommands() {
    getCommand("shop").setExecutor(commandManager);
    getCommand("shop").setTabCompleter(new TabCompletion(commandManager));
    loadCommodoreData();
  }

  private void loadCommodoreData() {
    try {
      final LiteralCommandNode<?> towns = CommodoreFileReader.INSTANCE.parse(getResource("shop.commodore"));
      CommodoreProvider.getCommodore(this).register(getCommand("shop"), towns);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
