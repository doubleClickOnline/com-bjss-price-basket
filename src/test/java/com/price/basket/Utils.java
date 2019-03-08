package com.price.basket;

import java.util.List;
import java.util.Optional;

public class Utils {

  public static Optional<Item> findItem(String itemName, List<Item> shopCart) {

    return shopCart
        .stream()
        .filter(item -> item.getName().equalsIgnoreCase(itemName))
        .findFirst();
  }

}
