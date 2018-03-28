package com.tanerdiler.ecommerce.basketapi.model;

import java.util.Optional;

public enum DiscountType {
    RATE, AMOUNT;

    public static DiscountType of(String name) {

        return Optional.of(name)
                .map(String::toUpperCase)
                .map(DiscountType::valueOf)
                .orElseThrow(NullPointerException::new);
    }
}
