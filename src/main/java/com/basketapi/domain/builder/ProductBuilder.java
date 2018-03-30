package com.basketapi.domain.builder;

import com.basketapi.domain.model.Category;
import com.basketapi.domain.model.Product;

public class ProductBuilder implements IValidatableBuilder<Product>
{
    private String name;
    private Integer id;
    private Double price;
    private Category category;

    public ProductBuilder withId(Integer id)
    {
        this.id = id;
        return this;
    }

    public ProductBuilder withName(String name)
    {
        this.name = name;
        return this;
    }

    public ProductBuilder withPrice(Double price)
    {
        this.price = price;
        return this;
    }

    public ProductBuilder relateTo(Category category)
    {
        this.category = category;
        return this;
    }

    @Override
    public Product get()
    {
        return new Product(id, name, price, category);
    }
}
