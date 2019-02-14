package com.bjss.price.basket;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;

// TODO Refactor code to increase DRY principle
public class PriceBasketTests {

    private static PriceBasket priceBasket;

    @BeforeAll
    public static void setup() {
        priceBasket = new PriceBasket();
    }

    @Test
    public void createShopCartTestSuccess() {
        List<String> itemNames = Arrays.asList(new String[] {"Apples", "Soup"});
        List<Item> actualResult = priceBasket.createShopCart(itemNames);

        List<Item> expectedResult = new LinkedList<>();
        expectedResult.add(new Item("Apples", "Bag"));
        expectedResult.add(new Item("Soup", "Tin"));

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void createShopCartTestFail() {

        List<String> itemNames = Arrays.asList(new String[] {"NotExistItem"});
        // thrown ProductNotFoundException exception
        assertThrows(ProductNotFoundException.class, () -> {
            priceBasket.createShopCart(itemNames);
        });
    }

    // TODO Write more success and fail test cases
    @Test
    public void getApplicableDiscountsApplesTestSuccess() {

        // apples discount
        Discount appleDiscount = new Discount(ShopDiscounts.APPLES_DISCOUNT);
        appleDiscount.addDiscountCaseItem(new Item("Apples", "Bag"));
        appleDiscount.addDiscountedItem(new Item("Apples", "Bag"));

        List<Discount> expectedApplicableDiscounts = new LinkedList<>();
        expectedApplicableDiscounts.add(appleDiscount);

        List<Item> shopCart = priceBasket.createShopCart(
                                Arrays.asList(new String[] {"Apples", "Soup"}));
        List<Discount> actualApplicableDiscounts =
            priceBasket.getApplicableDiscounts(shopCart, new ShopDiscounts().getAllShopDiscounts());

        Assertions.assertEquals(expectedApplicableDiscounts, actualApplicableDiscounts);
    }

    // TODO Write more success and fail test cases
    @Test
    public void getApplicableDiscountsBreadTestSuccess() {

        // bread discount
        Discount breadDiscount = new Discount(ShopDiscounts.BREAD_DISCOUNT);
        breadDiscount.addDiscountCaseItem(new Item("Soup", "Tin"));
        breadDiscount.addDiscountCaseItem(new Item("Soup", "Tin"));
        breadDiscount.addDiscountedItem(new Item("Bread", "Loaf", BigDecimal.valueOf(0.40)));

        List<Discount> expectedApplicableDiscounts = new LinkedList<>();
        expectedApplicableDiscounts.add(breadDiscount);

        List<Item> shopCart = priceBasket.createShopCart(
            Arrays.asList(new String[] {"Bread", "Soup", "Soup"}));
        List<Discount> actualApplicableDiscounts =
            priceBasket.getApplicableDiscounts(shopCart, new ShopDiscounts().getAllShopDiscounts());

        Assertions.assertEquals(expectedApplicableDiscounts, actualApplicableDiscounts);
    }

    // TODO Write more success and fail test cases
    @Test
    public void applyDiscountsTestSuccess() {

        List<Item> expectedBread = new LinkedList<>();
        expectedBread.add(new Item("Bread", "Loaf", BigDecimal.valueOf(0.40)));

        List<Item> shopCart = priceBasket.createShopCart(
            Arrays.asList(new String[] {"Bread", "Soup", "Soup"}));
        List<Item> updatedShopCart = priceBasket.applyDiscounts(shopCart, new ShopDiscounts().getAllShopDiscounts());

        // Find bread
        List<Item> updatedBread = updatedShopCart
                                            .stream()
                                            .filter(item -> item.getName().equalsIgnoreCase("Bread"))
                                            .collect(Collectors.toList());

        Assertions.assertEquals(3, updatedShopCart.size());
        Assertions.assertEquals(expectedBread, updatedBread);
        Assertions.assertEquals(expectedBread.get(0).getPrice(), updatedBread.get(0).getPrice());
    }

    // TODO Write more success and fail test cases
    @Test
    public void countPriceTestSuccess() {

        BigDecimal expectedSum = BigDecimal.valueOf(1.45);
        List<Item> shopCart = priceBasket.createShopCart(
            Arrays.asList(new String[] {"Bread", "Soup"}));

        BigDecimal actualSum = priceBasket.countPrice(shopCart);
        Assertions.assertEquals(expectedSum, actualSum);
    }

}
