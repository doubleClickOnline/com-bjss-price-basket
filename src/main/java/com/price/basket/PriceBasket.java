package com.price.basket;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;

/**
 * Entry point to implementation
 */
@Getter
public class PriceBasket {

  public static void main(String... args) {

    // Create shop cart from items
    List<Item> shopCart = new ShopCart(new ShopItems()).createShopCart(Arrays.asList(args));

    // Calc subtotal shop cart price
    System.out.println("Subtotal: £" + new ShopCart(new ShopItems()).countPrice(shopCart));

    // Apply discounts
    ShopDiscounts shopDiscounts = new ShopDiscounts();
    shopCart = shopDiscounts.applyDiscounts(shopCart, shopDiscounts.getAllShopDiscounts());

    // Calc total shop cart price
    System.out.println("Total: £" + new ShopCart(new ShopItems()).countPrice(shopCart));
  }
}
