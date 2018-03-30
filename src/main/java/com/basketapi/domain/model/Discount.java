package com.basketapi.domain.model;

import com.basketapi.domain.builder.DiscountWithPriceBuilder;
import com.basketapi.domain.builder.DiscountWithRateBuilder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "discounts")
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="discount_type",
        discriminatorType = DiscriminatorType.STRING)
@Getter @Setter
public abstract class Discount implements IDiscount
{
    @Positive
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public static DiscountWithRateBuilder withRate(Double rate)
    {
        return new DiscountWithRateBuilder().withRate(rate);
    }

    public static DiscountWithPriceBuilder withPrice(Double price)
    {
        return new DiscountWithPriceBuilder().withPrice(price);
    }

    public abstract Double applyTo(IProduct discountable);
}
