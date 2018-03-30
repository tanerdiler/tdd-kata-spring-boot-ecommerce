package com.basketapi.domain.builder;

import com.basketapi.domain.model.DiscountWithPrice;

import static com.basketapi.domain.model.DiscountType.PRICE;

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
        return new DiscountWithPrice(price, PRICE);
    }
}
