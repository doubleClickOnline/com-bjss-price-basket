package com.price.basket;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Available items in shop
 */
public class ShopItems {

  private List<Item> storeShopItems;

  /**
   * @return all shop items
   */
  public List<Item> getShopItems() {

    if (null == storeShopItems) {
      storeShopItems = createStoreShopItems();
    }
    return storeShopItems;
  }

  /**
   * Get shop item from name
   * @param name item name
   * @return Get shop item from name
   */
  public Optional<Item> getShopItem(String name) {

    return getShopItems()
        .stream()
        .filter(item -> item.getName().equalsIgnoreCase(name))
        .findFirst();
  }

  /**
   * Check if item exist in shop
   * @param name item name
   * @return Check if item exist in shop
   */
  public boolean existStoreShopItem(String name) {

    return !getShopItems()
        .stream()
        .filter(item -> item.getName().equalsIgnoreCase(name))
        .collect(Collectors.toList())
        .isEmpty();
  }

  private List<Item> createStoreShopItems() {

    List<Item> allShopItems = new LinkedList<>();
    allShopItems.add(new Item("Soup", "Tin", BigDecimal.valueOf(0.65)));
    allShopItems.add(new Item("Bread", "Loaf", BigDecimal.valueOf(0.80)));
    allShopItems.add(new Item("Milk", "Bottle", BigDecimal.valueOf(1.30)));
    allShopItems.add(new Item("Apples", "Bag", BigDecimal.valueOf(1.00)));
    return allShopItems;
  }
}
