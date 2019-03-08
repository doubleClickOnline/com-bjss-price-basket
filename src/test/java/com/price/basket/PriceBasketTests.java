package com.price.basket;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PriceBasketTests {

  @Test
  public void mainTestSuccess() {

    List<Item> updatedShopCart = Arrays.asList(
        new Item("Apples", "Bag", BigDecimal.valueOf(0.90)));

    PriceBasket priceBasket = new PriceBasket();
    priceBasket.main("Apples");

    Assertions.assertEquals(updatedShopCart, priceBasket.shopCart);
    Assertions.assertEquals(
        Utils.findItem("Apples", updatedShopCart).get().getPrice(),
        Utils.findItem("Apples", priceBasket.shopCart).get().getPrice());
  }

  @Test
  public void mainTestFail() {

    List<Item> notUpdatedShopCart = Arrays.asList(
        new Item("Apples", "Bag", BigDecimal.valueOf(1.00)));

    PriceBasket priceBasket = new PriceBasket();
    priceBasket.main("Apples");

    Assertions.assertEquals(notUpdatedShopCart, priceBasket.shopCart);
    Assertions.assertNotEquals(
        Utils.findItem("Apples", notUpdatedShopCart).get().getPrice(),
        Utils.findItem("Apples", priceBasket.shopCart).get().getPrice());
  }

  @Test
  public void mainTestProductNotFoundExceptionSuccess() {

    // thrown ProductNotFoundException exception
    assertThrows(ProductNotFoundException.class, () -> {
      new PriceBasket().main("NotExistItem");
    });
  }

}
