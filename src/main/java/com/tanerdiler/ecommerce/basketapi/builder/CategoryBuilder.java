package com.tanerdiler.ecommerce.basketapi.builder;

import com.tanerdiler.ecommerce.basketapi.model.Category;

public class CategoryBuilder implements IValidatableBuilder<Category>
{
    private Integer id;
    private String name;

    public CategoryBuilder withId(Integer id)
    {
        this.id = id;
        return this;
    }

    public CategoryBuilder withName(String name)
    {
        this.name = name;
        return this;
    }

    @Override
    public Category get()
    {
        return new Category(id, name);
    }
}
