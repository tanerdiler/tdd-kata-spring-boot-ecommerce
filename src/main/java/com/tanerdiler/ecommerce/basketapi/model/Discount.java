package com.tanerdiler.ecommerce.basketapi.model;

import com.tanerdiler.ecommerce.basketapi.builder.DiscountWithPriceBuilder;
import com.tanerdiler.ecommerce.basketapi.builder.DiscountWithRateBuilder;

public abstract class Discount {

    public static DiscountWithRateBuilder withRate(Double rate) {
        return new DiscountWithRateBuilder().withRate(rate);
    }

    public static DiscountWithPriceBuilder withPrice(Double price) {
        return new DiscountWithPriceBuilder().withPrice(price);
    }

    public abstract Double applyTo(IProduct discountable);
}
