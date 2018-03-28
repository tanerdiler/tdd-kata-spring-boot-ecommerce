package com.tanerdiler.ecommerce.basketapi.model;

import lombok.EqualsAndHashCode;

public class DiscountedProduct implements IProduct
{
    private final Double discountedPrice;
    private IProduct origin;

    public DiscountedProduct(IProduct discountable, Double discountedPrice)
    {
        this.origin = discountable;
        this.discountedPrice = discountedPrice;
    }

    @Override
    public Integer getId() {
        return origin.getId();
    }

    @Override
    public Double getOriginalPrice() {
        return origin.getOriginalPrice();
    }

    @Override
    public Double getPrice() {
        return discountedPrice;
    }

    @Override
    public Category getCategory() {
        return origin.getCategory();
    }

    @Override
    public boolean equals(Object o) {
        return origin.equals(o);
    }

    @Override
    public int hashCode() {
        return origin.hashCode();
    }
}
