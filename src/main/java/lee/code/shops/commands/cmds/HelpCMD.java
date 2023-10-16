package lee.code.shops.commands.cmds;

import lee.code.shops.Shops;
import lee.code.shops.commands.SubCommand;
import lee.code.shops.lang.Lang;
import lee.code.shops.utils.CoreUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class HelpCMD extends SubCommand {
  private final Shops shops;

  public HelpCMD(Shops shops) {
    this.shops = shops;
  }

  @Override
  public String getName() {
    return "help";
  }

  @Override
  public String getDescription() {
    return "All shop commands.";
  }

  @Override
  public String getSyntax() {
    return "/shop help";
  }

  @Override
  public String getPermission() {
    return "shops.command.help";
  }

  @Override
  public boolean performAsync() {
    return true;
  }

  @Override
  public boolean performAsyncSynchronized() {
    return false;
  }

  @Override
  public void perform(Player player, String[] args) {
    performSender(player, args);
  }

  @Override
  public void performConsole(CommandSender console, String[] args) {
    performSender(console, args);
  }

  @Override
  public void performSender(CommandSender sender, String[] args) {
    int number = 1;
    final Map<SubCommand, String> commands = new HashMap<>();
    for (SubCommand subCommand : shops.getCommandManager().getSubCommandList()) commands.put(subCommand, subCommand.getName());
    final Map<SubCommand, String> sortedCommands = CoreUtil.sortByValue(commands, Comparator.naturalOrder());
    final List<Component> lines = new ArrayList<>();
    lines.add(Lang.COMMAND_HELP_TITLE.getComponent(null));
    lines.add(Component.text(""));

    for (SubCommand subCommand : sortedCommands.keySet()) {
      if (sender.hasPermission(subCommand.getPermission())) {
        final Component helpSubCommand = Lang.COMMAND_HELP_SUB_COMMAND.getComponent(new String[] { String.valueOf(number), subCommand.getSyntax() })
          .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.SUGGEST_COMMAND, CoreUtil.getTextBeforeCharacter(subCommand.getSyntax(), '&')))
          .hoverEvent(Lang.COMMAND_HELP_SUB_COMMAND_HOVER.getComponent(new String[] { subCommand.getDescription() }));
        lines.add(helpSubCommand);
        number++;
      }
    }

    lines.add(Component.text(""));
    lines.add(Lang.COMMAND_HELP_DIVIDER.getComponent(null));
    for (Component line : lines) sender.sendMessage(line);
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, String[] args) {
    return new ArrayList<>();
  }
}
