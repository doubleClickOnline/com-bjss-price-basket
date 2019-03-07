package com.price.basket;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ShopItemsTests {

  @Test
  public void checkItemExistInShopSuccess() {
    Assertions.assertTrue(new ShopItems().existShopItem("Apples"));
  }

  @Test
  public void checkItemExistLowerCaseInShopSuccess() {
    Assertions.assertTrue(new ShopItems().existShopItem("apples"));
  }

  @Test
  public void checkItemExistInShopFail() {
    Assertions.assertFalse(new ShopItems().existShopItem("NotExistItem"));
  }

  @Test
  public void checkItemExistLowerCaserInShopFail() {
    Assertions.assertFalse(new ShopItems().existShopItem("notexistitem"));
  }
}
