package com.price.basket;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ShopCartTests {

  private static ShopCart storeShopCart;

  @BeforeAll
  public static void setup() {
    storeShopCart = new ShopCart(new ShopItems());
  }

  @Test
  public void createShopCartTestSuccess() {

    List<String> itemNames = Arrays.asList(new String[]{"Apples", "Soup"});
    List<Item> actualResult = storeShopCart.createShopCart(itemNames);

    List<Item> expectedResult = new LinkedList<>();
    expectedResult.add(new Item("Apples", "Bag", BigDecimal.valueOf(1.00)));
    expectedResult.add(new Item("Soup", "Tin", BigDecimal.valueOf(0.65)));

    Assertions.assertEquals(expectedResult, actualResult);
  }

  @Test
  public void createShopCartTestFail() {

    List<String> itemNames = Arrays.asList(new String[]{"NotExistItem"});
    // thrown ProductNotFoundException exception
    assertThrows(ProductNotFoundException.class, () -> {
      storeShopCart.createShopCart(itemNames);
    });
  }

  @Test
  public void countPriceTestSuccess() {

    BigDecimal expectedSum = BigDecimal.valueOf(1.45);
    List<Item> shopCart = storeShopCart.createShopCart(
        Arrays.asList("Bread", "Soup"));

    BigDecimal actualSum = storeShopCart.countPrice(shopCart);
    Assertions.assertEquals(expectedSum, actualSum);
  }

  @Test
  public void countPriceTest2Success() {

    BigDecimal expectedSum = BigDecimal.valueOf(3.40).setScale(2);
    List<Item> shopCart = storeShopCart.createShopCart(
        Arrays.asList("Bread", "Soup", "Soup", "Milk"));

    BigDecimal actualSum = storeShopCart.countPrice(shopCart);
    Assertions.assertEquals(expectedSum, actualSum);
  }
}
