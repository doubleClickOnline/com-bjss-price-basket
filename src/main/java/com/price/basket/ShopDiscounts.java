package com.price.basket;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

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
    applesDiscount.addDiscountCaseItem(new Item("Apples", "Bag", BigDecimal.valueOf(1.00)));
    applesDiscount.addDiscountedItem(new Item("Apples", "Bag", BigDecimal.valueOf(0.90)));

    // Bread discount
    Discount breadDiscount = new Discount(BREAD_DISCOUNT_TEXT, BREAD_DISCOUNT_AMOUNT);
    breadDiscount.addDiscountCaseItem(new Item("Soup", "Tin", BigDecimal.valueOf(0.65)));
    breadDiscount.addDiscountCaseItem(new Item("Soup", "Tin", BigDecimal.valueOf(0.65)));
    breadDiscount.addDiscountedItem(new Item("Bread", "Loaf", BigDecimal.valueOf(0.40)));

    // Add to all shops discounts
    allShopDiscounts.add(applesDiscount);
    allShopDiscounts.add(breadDiscount);

    return allShopDiscounts;
  }

  /**
   * Get applicable discounts for shop cart
   */
  public List<Discount> getApplicableDiscounts(List<Item> shopCart, List<Discount> shopDiscounts) {

    return shopDiscounts
        .stream()
        .filter(discount ->
            shopCart.containsAll(discount.getDiscountCaseItems()) &&
                shopCart.containsAll(discount.getDiscountedItems()))
        .collect(Collectors.toList());
  }

  /**
   * Apply applicable discounts to shop cart
   */
  public List<Item> applyDiscounts(List<Item> shopCart, List<Discount> discounts)
      throws ProductNotFoundException {

    List<Item> tempShopCart = new LinkedList<>();
    List<Item> updateShopCart = new LinkedList<>();
    List<Discount> appliedDiscounts = new LinkedList<>();
    List<Discount> applicableDiscounts = new ShopDiscounts()
        .getApplicableDiscounts(shopCart, discounts);

    // There is applicable discount
    if (!applicableDiscounts.isEmpty()) {

      for (Item item : shopCart) {
        tempShopCart.add(item);

        // iterate through all applicable discounts
        for (Discount discount : discounts) {

          // possible apply discount
          if (tempShopCart.containsAll(discount.getDiscountCaseItems()) &&
              tempShopCart.containsAll(discount.getDiscountedItems())) {

            appliedDiscounts.add(discount);

            // update temp shop cart
            tempShopCart.removeAll(discount.getDiscountedItems());
            tempShopCart.addAll(discount.getDiscountedItems());

            // update final shop cart
            updateShopCart.addAll(tempShopCart);
            tempShopCart.clear();
          }
        }
      }

      // not to leave part of cart left
      if (!tempShopCart.isEmpty()) {
        updateShopCart.addAll(tempShopCart);
      }

      // print out applied discounts
      calcAppliedDiscounts(appliedDiscounts);
    } else {
      System.out.println(ShopDiscounts.NO_DISCOUNTS);
    }

    return updateShopCart;
  }

  public void calcAppliedDiscounts(List<Discount> appliedDiscounts) {

    Map<String, BigDecimal> discountsMap = new HashMap<>();
    appliedDiscounts.stream().forEach(discount -> {

      String discountText = discount.getDiscountText();
      BigDecimal discountAmount = discount.getDiscountAmount();
      if (discountsMap.keySet().contains(discountText)) {
        discountAmount = discountsMap.get(discountText).add(discountAmount);
      }
      discountsMap.put(discount.getDiscountText(), discountAmount);
    });

    for (Entry<String, BigDecimal> discountCase : discountsMap.entrySet()) {
      System.out.println(
          String.format("%s %s", discountCase.getKey(), discountCase.getValue().toString()));
    }
  }
}
