package com.price.basket;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ShopCart {

  private ShopItems shopItems;

  public List<Item> createShopCart(List<String> itemNames) throws ProductNotFoundException {

    List<Item> shopCart = new LinkedList<Item>();
    for (String item : itemNames) {
      if (shopItems.getShopItem(item).isPresent()) {
        shopCart.add(shopItems.getShopItem(item).get());
      } else {
        throw new ProductNotFoundException(
            String.format("There is missing %s in shop", item));
      }
    }
    return shopCart;
  }

  /**
   * Calculcate shop cart price
   */
  public BigDecimal countPrice(List<Item> shopCart) {
    return shopCart
        .stream()
        .map(item -> item.getPrice())
        .reduce(BigDecimal.ZERO, BigDecimal::add)
        .setScale(2);
  }

}
