package com.tanerdiler.ecommerce.basketapi.model;

public interface IProduct
{
    Integer getId();
    Double getOriginalPrice();
    Double getPrice();
    Category getCategory();
}
