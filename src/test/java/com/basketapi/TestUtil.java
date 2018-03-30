package com.basketapi;

import com.basketapi.domain.model.Category;
import com.basketapi.domain.model.Product;

public class TestUtil
{
    public static Product createProduct(Integer id, String name, Double price,
                                  Category category)
    {
        return Product.aNew()
                .withId(id)
                .withName(name)
                .withPrice(price)
                .relateTo(category)
                .get();
    }

    public static Category createCategory(Integer id, String name)
    {
        return Category.aNew()
                .withId(id)
                .withName(name)
                .get();
    }
}
