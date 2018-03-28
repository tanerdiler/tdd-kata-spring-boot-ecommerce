package com.tanerdiler.ecommerce.basketapi.model;

import lombok.*;

import javax.validation.constraints.Positive;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter @Setter
public class DiscountWithPrice extends Discount
{
    @Positive
    private double price;
    private DiscountType type;

    @Override
    public Double applyTo(IProduct discountable)
    {
        Double discountAmount = price;
        Double productPrice = discountable.getPrice();
        Double discountedPrice = productPrice - discountAmount;
        return discountedPrice;
    }
}
