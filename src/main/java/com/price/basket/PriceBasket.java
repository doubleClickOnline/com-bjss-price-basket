package com.price.basket;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;

/**
 * Entry point to implementation
 */
@Getter
public class PriceBasket {

  public static List<Item> shopCart;

  public static void main(String... args) throws ProductNotFoundException {

    PriceBasket priceBasket = new PriceBasket();
    // Create shop cart from items
    shopCart = new ShopCart(new ShopItems()).createShopCart(Arrays.asList(args));

    // Calc subtotal shop cart price
    System.out.println("Subtotal: £" + new ShopCart(new ShopItems()).countPrice(shopCart));

    // Apply discounts
    ShopDiscounts shopDiscounts = new ShopDiscounts();
    shopCart = shopDiscounts.applyDiscounts(shopCart, shopDiscounts.getAllShopDiscounts());

    // Calc total shop cart price
    System.out.println("Total: £" + new ShopCart(new ShopItems()).countPrice(shopCart));
  }
}
