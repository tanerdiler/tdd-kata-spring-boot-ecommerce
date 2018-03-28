package com.tanerdiler.ecommerce.basketapi.model;

import com.tanerdiler.ecommerce.basketapi.builder.DiscountWithPriceBuilder;
import com.tanerdiler.ecommerce.basketapi.builder.DiscountWithRateBuilder;

public interface Discount {

    static DiscountWithRateBuilder withRate(Double rate)
    {
        return new DiscountWithRateBuilder().withRate(rate);
    }

    static DiscountWithPriceBuilder withPrice(Double price)
    {
        return new DiscountWithPriceBuilder().withPrice(price);
    }

    Double applyTo(IProduct discountable);
}
