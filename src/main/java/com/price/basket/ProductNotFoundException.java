package com.price.basket;

/**
 *
 * Product not found in shop exception
 */
public class ProductNotFoundException extends IllegalArgumentException {

    public ProductNotFoundException(String message) {
        super(message);
    }
}
