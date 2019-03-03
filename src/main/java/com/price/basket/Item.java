package com.price.basket;

import lombok.*;

import java.math.BigDecimal;

/**
 *
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

