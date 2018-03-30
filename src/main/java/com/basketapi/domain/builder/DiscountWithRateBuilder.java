package com.basketapi.domain.builder;

import com.basketapi.domain.model.DiscountWithRate;

import static com.basketapi.domain.model.DiscountType.RATE;

public class DiscountWithRateBuilder implements IValidatableBuilder<DiscountWithRate>
{
    private Double limit;
    private Double rate;

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
