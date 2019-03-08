package com.price.basket;

import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ItemTests {

  @Test
  public void hashCodeTestSuccess() {

    Item expectedApples = new Item("Apples", "Bag", BigDecimal.valueOf(1.00));
    Item actualApples = new Item("Apples", "Bag", BigDecimal.valueOf(0.90));

    Assertions.assertEquals(expectedApples.hashCode(), actualApples.hashCode());
  }

  @Test
  public void hashCodeTestFail() {

    Item expectedApples = new Item("Apples", "Bag", BigDecimal.valueOf(1.00));
    Item actualApples = new Item("Apples", "Tin", BigDecimal.valueOf(0.90));

    Assertions.assertNotEquals(expectedApples.hashCode(), actualApples.hashCode());
  }

  @Test
  public void equalsTestSuccess() {

    Item expectedApples = new Item("Apples", "Bag", BigDecimal.valueOf(1.00));
    Item actualApples = new Item("Apples", "Bag", BigDecimal.valueOf(0.90));

    Assertions.assertEquals(expectedApples, actualApples);
  }

  @Test
  public void equalsTest2Success() {

    Item expectedApples = new Item("Apples", "Bag", BigDecimal.valueOf(1.00));
    Item actualApples = new Item("Apples", "Bag", BigDecimal.valueOf(1.00));

    Assertions.assertEquals(expectedApples.getPrice(), actualApples.getPrice());
    Assertions.assertEquals(expectedApples, actualApples);
  }

  @Test
  public void equalsTestFail() {

    Item expectedApples = new Item("Apples", "Bag", BigDecimal.valueOf(1.00));
    Item actualApples = new Item("Apples", "Bag", BigDecimal.valueOf(0.90));

    Assertions.assertEquals(expectedApples, actualApples);
    Assertions.assertNotEquals(expectedApples.getPrice(), actualApples.getPrice());
  }

  @Test
  public void equalsTest2Fail() {

    Item expectedApples = new Item("Apples", "Bag", BigDecimal.valueOf(1.00));
    Item actualApples = new Item("Apples", "Tin", BigDecimal.valueOf(0.90));

    Assertions.assertNotEquals(expectedApples, actualApples);
  }

}
