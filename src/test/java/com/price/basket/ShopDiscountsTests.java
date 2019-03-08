package com.price.basket;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ShopDiscountsTests {

  private static Discount appleDiscount;
  private static Discount breadDiscount;
  private static ShopCart storeShopCart;
  private static ShopDiscounts shopDiscounts;
  private static List<Discount> allShopDiscounts;

  @BeforeAll
  private static void setup() {

    storeShopCart = new ShopCart(new ShopItems());
    shopDiscounts = new ShopDiscounts();
    allShopDiscounts = shopDiscounts.getAllShopDiscounts();

    // apples discount
    appleDiscount = new Discount(ShopDiscounts.APPLES_DISCOUNT_TEXT,
        ShopDiscounts.APPLES_DISCOUNT_AMOUNT);
    appleDiscount.addDiscountCaseItem(new Item("Apples", "Bag", BigDecimal.valueOf(1.00)));
    appleDiscount.addDiscountedItem(new Item("Apples", "Bag", BigDecimal.valueOf(0.90)));

    // bread discount
    breadDiscount = new Discount(ShopDiscounts.BREAD_DISCOUNT_TEXT,
        ShopDiscounts.BREAD_DISCOUNT_AMOUNT);
    breadDiscount.addDiscountCaseItem(new Item("Soup", "Tin", BigDecimal.valueOf(0.65)));
    breadDiscount.addDiscountCaseItem(new Item("Soup", "Tin", BigDecimal.valueOf(0.65)));
    breadDiscount.addDiscountedItem(new Item("Bread", "Loaf", BigDecimal.valueOf(0.40)));
  }

  @Test
  public void applyDiscountsTestSuccess() {

    Item expectedBread = new Item("Bread", "Loaf", BigDecimal.valueOf(0.40));

    List<Item> shopCart = storeShopCart.createShopCart(
        Arrays.asList("Bread", "Soup", "Soup"));
    List<Item> updatedShopCart = shopDiscounts
        .applyDiscounts(shopCart, allShopDiscounts);

    // Find bread
    Optional<Item> updatedBread = Utils.findItem("Bread", updatedShopCart);

    Assertions.assertEquals(3, updatedShopCart.size());
    Assertions.assertEquals(expectedBread, updatedBread.get());
    Assertions.assertEquals(expectedBread.getPrice(), updatedBread.get().getPrice());
  }

  @Test
  public void applyDiscountsTest2Success() {

    Item expectedBread = new Item("Bread", "Loaf", BigDecimal.valueOf(0.40));
    Item expectedApples = new Item("Apples", "Bag", BigDecimal.valueOf(0.90));

    List<Item> shopCart = storeShopCart.createShopCart(
        Arrays.asList("Bread", "Soup", "Soup", "Apples", "Milk"));
    List<Item> updatedShopCart = shopDiscounts
        .applyDiscounts(shopCart, allShopDiscounts);

    // Find bread
    Optional<Item> updatedBread = Utils.findItem("Bread", updatedShopCart);

    // Find apples
    Optional<Item> updatedApples = Utils.findItem("Apples", updatedShopCart);

    Assertions.assertEquals(5, updatedShopCart.size());
    Assertions.assertEquals(expectedBread, updatedBread.get());
    Assertions.assertEquals(expectedBread.getPrice(), updatedBread.get().getPrice());
    Assertions.assertEquals(expectedApples.getPrice(), updatedApples.get().getPrice());
  }

  @Test
  public void applyDiscountsTestFail() {

    Item expectedBread = new Item("Bread", "Loaf", BigDecimal.valueOf(0.80));

    List<Item> shopCart = storeShopCart.createShopCart(
        Arrays.asList("Bread", "Soup", "Soup"));
    List<Item> updatedShopCart = shopDiscounts
        .applyDiscounts(shopCart, allShopDiscounts);

    Optional<Item> updatedBread = Utils.findItem("Bread", updatedShopCart);

    Assertions.assertEquals(3, updatedShopCart.size());
    Assertions.assertEquals(expectedBread, updatedBread.get());
    Assertions.assertNotEquals(expectedBread.getPrice(), updatedBread.get().getPrice());
  }

  @Test
  public void getApplicableDiscountsApplesTestSuccess() {

    List<Discount> expectedApplicableDiscounts = new LinkedList<>();
    expectedApplicableDiscounts.add(appleDiscount);
    expectedApplicableDiscounts.add(breadDiscount);

    List<Item> shopCart = storeShopCart.createShopCart(
        Arrays.asList("Apples", "Soup", "Soup", "Bread", "Milk"));
    List<Discount> actualApplicableDiscounts =
        shopDiscounts.getApplicableDiscounts(shopCart, allShopDiscounts);

    Assertions.assertEquals(expectedApplicableDiscounts, actualApplicableDiscounts);
  }

  @Test
  public void getNoApplicableDiscountsTestSuccess() {

    List<Item> shopCart = storeShopCart.createShopCart(
        Arrays.asList("Soup", "Soup"));
    List<Discount> actualApplicableDiscounts =
        shopDiscounts.getApplicableDiscounts(shopCart, allShopDiscounts);

    Assertions.assertEquals(Collections.emptyList(), actualApplicableDiscounts);
  }

  @Test
  public void getApplicableDiscountsTestFail() {

    List<Discount> expectedApplicableDiscounts = new LinkedList<>();
    expectedApplicableDiscounts.add(breadDiscount);

    List<Item> shopCart = storeShopCart.createShopCart(
        Arrays.asList("Soup", "Soup"));
    List<Discount> actualApplicableDiscounts =
        shopDiscounts.getApplicableDiscounts(shopCart, allShopDiscounts);

    Assertions.assertNotEquals(expectedApplicableDiscounts, actualApplicableDiscounts);
  }

  @Test
  public void getApplicableDiscountsBreadTestSuccess() {

    List<Discount> expectedApplicableDiscounts = new LinkedList<>();
    expectedApplicableDiscounts.add(breadDiscount);

    List<Item> shopCart = storeShopCart.createShopCart(
        Arrays.asList("Bread", "Soup", "Soup"));
    List<Discount> actualApplicableDiscounts =
        shopDiscounts.getApplicableDiscounts(shopCart, allShopDiscounts);

    Assertions.assertEquals(expectedApplicableDiscounts, actualApplicableDiscounts);
  }
}
