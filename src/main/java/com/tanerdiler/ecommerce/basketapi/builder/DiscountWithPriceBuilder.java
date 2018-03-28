package com.tanerdiler.ecommerce.basketapi.builder;

import com.tanerdiler.ecommerce.basketapi.model.DiscountWithPrice;

import static com.tanerdiler.ecommerce.basketapi.model.DiscountType.AMOUNT;

public class DiscountWithPriceBuilder implements IValidatableBuilder<DiscountWithPrice>
{
    private double price;

    public DiscountWithPriceBuilder withPrice(double price)
    {
        this.price = price;
        return this;
    }

    @Override
    public DiscountWithPrice get()
    {
        return new DiscountWithPrice(price, AMOUNT);
    }
}
