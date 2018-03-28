package com.tanerdiler.ecommerce.basketapi.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryTest
{
    @Test
    public void should_create_by_using_constructor_with_all_args()
    {
        Category category = new Category(10, "Gomlek");

        assertThat(category.getId()).isEqualTo(10);
        assertThat(category.getName()).isEqualTo("Gomlek");
    }

    @Test
    public void should_create_by_using_constructor_with_no_args()
    {
        Category category = new Category();
        category.setId(10);
        category.setName("Gomlek");

        assertThat(category.getId()).isEqualTo(10);
        assertThat(category.getName()).isEqualTo("Gomlek");
    }
}
