package com.price.basket;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * Entry point to implementation
 */
public class PriceBasket {


  public static void main(String... args) {

    try {

      PriceBasket priceBasket = new PriceBasket();
      // Create shop cart from items
      List<Item> shopCart = priceBasket.createShopCart(Arrays.asList(args));

      // Calc subtotal shop cart price
      System.out.println("Subtotal: £" + priceBasket.countPrice(shopCart));

      // Apply discounts
      shopCart = priceBasket.applyDiscounts(shopCart, new ShopDiscounts().getAllShopDiscounts());

      // Calc total shop cart price
      System.out.println("Total: £" + priceBasket.countPrice(shopCart));

    } catch (ProductNotFoundException e) {
      System.err.println(e.getMessage());
    }
  }

  /**
   * // TODO Refactor to separate class Create shop items from item names
   */
  public List<Item> createShopCart(List<String> itemNames) throws ProductNotFoundException {

    ShopItems shopItems = new ShopItems();
    List<Item> shopCart = new LinkedList<Item>();
    for (String item : itemNames) {
      if (shopItems.existShopItem(item)) {
        shopCart.add(shopItems.getShopItem(item).get());
      } else {
        throw new ProductNotFoundException(
            String.format("There is missing %s in shop", item));
      }
    }
    return shopCart;
  }

  /**
   * // TODO Refactor to separate class Apply applicable discounts for shop cart
   */
  public List<Item> applyDiscounts(List<Item> shopCart, List<Discount> discounts)
      throws ProductNotFoundException {

    List<Item> tempShopCart = new LinkedList<Item>();
    List<Item> updateShopCart = new LinkedList<Item>();
    List<Discount> applicableDiscounts = getApplicableDiscounts(shopCart, discounts);
    List<Discount> appliedDiscounts = new LinkedList<Discount>();

    // There is applicable discount
    if (!applicableDiscounts.isEmpty()) {

      for (Item item : shopCart) {
        tempShopCart.add(item);

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

    Map<String, BigDecimal> discountsMap = new HashMap<String, BigDecimal>();
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

  /**
   * // TODO Refactor to separate class Get applicable discounts for shop cart
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
   * // TODO Refactor to separate class Count price for shop cart
   */
  public BigDecimal countPrice(List<Item> shopCart) {
    return shopCart
        .stream()
        .map(item -> item.getPrice())
        .reduce(BigDecimal.ZERO, BigDecimal::add)
        .setScale(2);
  }
}
