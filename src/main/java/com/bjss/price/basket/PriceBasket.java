package com.bjss.price.basket;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
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
     * // TODO Refactor to separate class
     * Create shop items from item names
     *
     * @param itemNames
     * @return
     * @throws ProductNotFoundException
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
     *
     * // TOOD Refactor to separate class
     * Apply applicable discounts for shop cart
     *
     * @param shopCart
     * @param discounts
     * @return
     * @throws ProductNotFoundException
     */
    public List<Item> applyDiscounts(List<Item> shopCart, List<Discount> discounts) throws ProductNotFoundException {

        List<Discount> applicableDiscounts = getApplicableDiscounts(shopCart, discounts);
        if (!applicableDiscounts.isEmpty()) {
            for (Discount discount : applicableDiscounts) {

                System.out.println(discount.getDiscountText());
                shopCart.removeAll(discount.getDiscountedItems());
                shopCart.addAll(discount.getDiscountedItems());
            }
        } else {
            System.out.println(ShopDiscounts.NO_DISCOUNTS);
        }

        return shopCart;
    }

    /**
     *
     * // TODO Refactor to separate class
     * Get applicable discounts for shop cart
     *
     * @param shopCart
     * @param shopDiscounts
     * @return
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
     *
     * // TODO Refactor to separate class
     * Count price for shop cart
     *
     * @param shopCart
     * @return
     */
    public BigDecimal countPrice(List<Item> shopCart) {
        return shopCart
                .stream()
                .map(item -> item.getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2);
    }
}
