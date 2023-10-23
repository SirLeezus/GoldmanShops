package lee.code.shops.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum GlobalSetting {
  WAND_ID(3),
  ;
  @Getter private final int id;
}
