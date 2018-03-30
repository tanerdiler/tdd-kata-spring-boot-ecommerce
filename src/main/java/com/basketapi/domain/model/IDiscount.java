package com.basketapi.domain.model;

public interface IDiscount
{
    Integer getId();
    DiscountType getType();

    default boolean isType(DiscountType type)
    {
        return getType().equals(type);
    }

    default DiscountWithPrice castToPrice()
    {
        return (DiscountWithPrice) this;
    }

    default DiscountWithRate castToRate()
    {
        return (DiscountWithRate) this;
    }
}
