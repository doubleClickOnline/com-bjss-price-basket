package com.price.basket;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * Available items in shop
 */
public class ShopItems {

    private List<Item> shopItems;

    /**
     *
     * Get all items list in shop
     *
     * @return
     */
    public List<Item> getShopItems() {

        if (null == shopItems) {
            shopItems = createShopItems();
        }
        return shopItems;
    }

    /**
     *  // TODO Implement factory pattern
     * Get shop item from name
     *
     * @param name
     * @return
     */
    public Optional<Item> getShopItem(String name) {

        return getShopItems()
                .stream()
                .filter(item -> item.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    /**
     *
     * Check if item exist in shop
     *
     * @param name
     * @return
     */
    public boolean existShopItem(String name) {

        return !getShopItems()
                .stream()
                .filter(item -> item.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList())
                .isEmpty();
    }

    private List<Item> createShopItems() {

        List<Item> allShopItems = new LinkedList<Item>();
        allShopItems.add(new Item("Soup", "Tin", BigDecimal.valueOf(0.65)));
        allShopItems.add(new Item("Bread", "Loaf", BigDecimal.valueOf(0.80)));
        allShopItems.add(new Item("Milk", "Bottle", BigDecimal.valueOf(1.30)));
        allShopItems.add(new Item("Apples", "Bag", BigDecimal.valueOf(1.00)));
        return allShopItems;
    }
}
