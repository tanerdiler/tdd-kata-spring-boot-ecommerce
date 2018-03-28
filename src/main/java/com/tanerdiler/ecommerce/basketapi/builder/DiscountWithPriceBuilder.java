package com.tanerdiler.ecommerce.basketapi.builder;

import com.tanerdiler.ecommerce.basketapi.model.Campaign;
import com.tanerdiler.ecommerce.basketapi.model.DiscountType;
import com.tanerdiler.ecommerce.basketapi.model.DiscountWithPrice;
import com.tanerdiler.ecommerce.basketapi.validation.BeanValidationException;
import com.tanerdiler.ecommerce.basketapi.validation.BeanValidator;

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
        DiscountWithPrice discount = new DiscountWithPrice(price, AMOUNT);
        return discount;
    }
}
