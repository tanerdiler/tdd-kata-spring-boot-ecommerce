package com.basketapi.domain.model;

import java.util.Optional;

public enum DiscountTargetType {
    CATEGORY,PRODUCT;

    public static DiscountTargetType of(String name) {

        return Optional.of(name)
                .map(String::toUpperCase)
                .map(DiscountTargetType::valueOf)
                .orElseThrow(NullPointerException::new);
    }
}
