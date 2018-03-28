package com.tanerdiler.ecommerce.basketapi.model;

import lombok.*;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter @Setter
public class DiscountWithRate extends Discount
{
    @DecimalMin("0.0")  @DecimalMax("100.0")
    private Double rate;
    @NotNull  @Positive
    private Double limit;
    @NotNull
    private DiscountType type;

    @Override
    public Double applyTo(IProduct discountable)
    {
        Double productPrice = discountable.getPrice();

        double discountAmount = productPrice * (rate/100);
        if(discountAmount > limit)
        {
            discountAmount = limit;
        }

        Double discountedPrice = productPrice - discountAmount;

        return discountedPrice;
    }
}
