package com.basketapi.domain.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@DiscriminatorValue("RATE")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter @Setter
public class DiscountWithRate extends Discount
{
    @DecimalMin("0.0")  @DecimalMax("100.0")
    @Column(name="discount_rate")
    private Double rate;
    @NotNull  @Positive
    @Column(name="discount_limit")
    private Double limit;
    @Enumerated(value = EnumType.STRING)
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

        return productPrice - discountAmount;
    }
}
