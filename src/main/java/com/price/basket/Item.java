package com.price.basket;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Shop item
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class Item {

  private final String name;
  private final String unit;
  private BigDecimal price;

  @Override
  public int hashCode() {
    return name.hashCode() + unit.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (null != obj && obj instanceof Item) {
      if (name.equalsIgnoreCase(((Item) obj).getName()) &&
          unit.equalsIgnoreCase(((Item) obj).getUnit())) {
        return true;
      }
    }
    return false;
  }
}

