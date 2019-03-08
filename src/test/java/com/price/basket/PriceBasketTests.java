package com.price.basket;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class PriceBasketTests {

  @Test
  public void mainTestProductNotFoundExceptionSuccess() {

    // thrown ProductNotFoundException exception
    assertThrows(ProductNotFoundException.class, () -> {
      new PriceBasket().main("NotExistItem");
    });
  }

}
