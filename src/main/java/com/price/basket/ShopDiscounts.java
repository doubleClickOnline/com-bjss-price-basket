package com.price.basket;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * //TODO Extract to interface All shops discounts
 */
public class ShopDiscounts {

  public static final String NO_DISCOUNTS = "(no offers available)";
  public static final String APPLES_DISCOUNT_TEXT = "Apples 10% off:";
  public static final BigDecimal APPLES_DISCOUNT_AMOUNT = BigDecimal.valueOf(0.10);
  public static final String BREAD_DISCOUNT_TEXT = "Bread 50% off:";
  public static final BigDecimal BREAD_DISCOUNT_AMOUNT = BigDecimal.valueOf(0.40);

  private List<Discount> allShopDiscounts;

  /**
   * Return all shop discounts
   */
  public List<Discount> getAllShopDiscounts() {

    if (null == allShopDiscounts) {
      allShopDiscounts = createAllShopDiscounts();
    }
    return allShopDiscounts;
  }

  private List<Discount> createAllShopDiscounts() {

    List<Discount> allShopDiscounts = new LinkedList<Discount>();

    // Apples discount
    Discount applesDiscount = new Discount(APPLES_DISCOUNT_TEXT, APPLES_DISCOUNT_AMOUNT);
    applesDiscount.addDiscountCaseItem(new Item("Apples", "Bag"));
    applesDiscount.addDiscountedItem(new Item("Apples", "Bag", BigDecimal.valueOf(0.90)));

    // Bread discount
    Discount breadDiscount = new Discount(BREAD_DISCOUNT_TEXT, BREAD_DISCOUNT_AMOUNT);
    breadDiscount.addDiscountCaseItem(new Item("Soup", "Tin"));
    breadDiscount.addDiscountCaseItem(new Item("Soup", "Tin"));
    breadDiscount.addDiscountedItem(new Item("Bread", "Loaf", BigDecimal.valueOf(0.40)));

    // Add to all shops discounts
    allShopDiscounts.add(applesDiscount);
    allShopDiscounts.add(breadDiscount);

    return allShopDiscounts;
  }
}
