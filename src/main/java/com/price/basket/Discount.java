package com.price.basket;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * Shop discount case
 */
@Getter
@EqualsAndHashCode
public class Discount {

    private List<Item> discountCaseItems;
    private List<Item> discountedItems;
    private String discountText;

    public Discount(String discountText) {

        this.discountCaseItems = new LinkedList<>();
        this.discountedItems = new LinkedList<>();
        this.discountText = discountText;
    }

    public void addDiscountCaseItem(Item item) {
        discountCaseItems.add(item);
    }

    public void addDiscountedItem(Item item) {
        discountedItems.add(item);
    }
}
