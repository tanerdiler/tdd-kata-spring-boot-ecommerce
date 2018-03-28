package com.tanerdiler.ecommerce.basketapi.builder;

import com.tanerdiler.ecommerce.basketapi.model.DiscountWithRate;

import static com.tanerdiler.ecommerce.basketapi.model.DiscountType.RATE;

public class DiscountWithRateBuilder implements IValidatableBuilder
{

    private Double limit;
    private Double rate;

    public DiscountWithRateBuilder(){

    }

    public DiscountWithRateBuilder withLimit(Double limit) {
        this.limit = limit;
        return this;
    }

    public DiscountWithRateBuilder withRate(Double rate) {
        this.rate = rate;
        return this;
    }

    @Override
    public DiscountWithRate get()
    {
        return new DiscountWithRate(rate, limit, RATE);
    }
}
