package com.tanerdiler.ecommerce.basketapi.model;

import lombok.*;

import javax.validation.constraints.Positive;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter @Setter
public class DiscountWithPrice implements Discount
{
    @Positive
    private double price;
    private DiscountType type;

    @Override
    public Double applyTo(IProduct discountable)
    {
        Double discountAmount = price;
        Double productPrice = discountable.getPrice();
        return productPrice - discountAmount;
    }
}
