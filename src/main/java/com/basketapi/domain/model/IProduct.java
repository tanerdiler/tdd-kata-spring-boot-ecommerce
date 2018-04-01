package com.basketapi.domain.model;

public interface IProduct
{
    Integer getId();
    Double getOriginalPrice();
    Double getPrice();
    Category getCategory();

    String getName();
}
