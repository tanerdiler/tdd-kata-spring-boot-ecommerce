package com.basketapi.domain.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Entity
@DiscriminatorValue("PRICE")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter @Setter
public class DiscountWithPrice extends Discount
{
    @Positive
    @Column(name="discount_price")
    private Double price;
    @Enumerated(value = EnumType.STRING)
    private DiscountType type;

    @Override
    public Double applyTo(IProduct discountable)
    {
        Double discountAmount = price;
        Double productPrice = discountable.getPrice();
        return productPrice - discountAmount;
    }
}
