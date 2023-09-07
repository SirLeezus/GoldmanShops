package lee.code.shops.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SubSyntax {
  COMMAND_ADMIN_REMOVE_SPAWN_SYNTAX("/shop admin removespawn &f<player>"),
  ;

  @Getter private final String string;
}
