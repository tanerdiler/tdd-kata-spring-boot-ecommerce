package com.tanerdiler.ecommerce.basketapi.builder;

import com.tanerdiler.ecommerce.basketapi.model.Category;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryBuilderTest
{
    @Test
    public void should_create_category_with_id()
    {
        Category category = Category.aNew()
                .withId(1)
                .get();

        assertThat(category.getId()).isEqualTo(1);
    }

    @Test
    public void should_create_category_with_name()
    {
        Category category = Category.aNew()
                .withName("Gömlek")
                .get();

        assertThat(category.getName()).isEqualTo("Gömlek");
    }

}
