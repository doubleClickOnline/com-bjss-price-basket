package com.price.basket;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Shop item
 */
@Getter
@AllArgsConstructor
public class Item {

  private String name;
  private String unit;
  private BigDecimal price;

  @Override
  public int hashCode() {
    return name.hashCode() + unit.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Item && name.equalsIgnoreCase(((Item) obj).getName()) &&
        unit.equalsIgnoreCase(((Item) obj).getUnit())) {
      return true;
    }
    return false;
  }
}

