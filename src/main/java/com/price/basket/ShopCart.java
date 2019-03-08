package com.price.basket;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ShopCart {

  private ShopItems shopItems;

  /**
   * Create store shop cart from items names
   *
   * @param itemNames item names
   * @return store shop cart items
   */
  public List<Item> createShopCart(List<String> itemNames) {

    List<Item> shopCart = new LinkedList<>();
    for (String item : itemNames) {
      Optional<Item> shopItem = shopItems.getShopItem(item);
      if (shopItem.isPresent()) {
        shopCart.add(shopItem.get());
      } else {
        throw new ProductNotFoundException(
            String.format("There is missing %s in shop", item));
      }
    }
    return shopCart;
  }

  /**
   *
   * Calculate store shop items price
   *
   * @param shopCart store shop cart items
   * @return Calculated store shop items price
   */
  public BigDecimal countPrice(List<Item> shopCart) {
    return shopCart
        .stream()
        .map(item -> item.getPrice())
        .reduce(BigDecimal.ZERO, BigDecimal::add)
        .setScale(2);
  }

}
