package com.basketapi.domain.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductTest
{
    @Test
    public void should_create_by_using_constructor_with_all_args()
    {
        Product product = new Product(10,"Kırmızı Ayakkabı", 100d, Category.aNew()
                .withId(1)
                .get());

        assertThat(product.getId()).isEqualTo(10);
        assertThat(product.getName()).isEqualTo("Kırmızı Ayakkabı");
        assertThat(product.getPrice()).isEqualTo(100d);
        assertThat(product.getCategory().getId()).isEqualTo(1);
    }

    @Test
    public void should_create_by_using_constructor_with_no_args()
    {
        Product product = new Product();
        product.setId(10);
        product.setPrice(100d);
        product.setName("Kırmızı Ayakkabı");
        product.setCategory(Category.aNew()
                .withId(1)
                .get());

        assertThat(product.getId()).isEqualTo(10);
        assertThat(product.getName()).isEqualTo("Kırmızı Ayakkabı");
        assertThat(product.getPrice()).isEqualTo(100d);
        assertThat(product.getCategory().getId()).isEqualTo(1);
    }

    @Test
    public void should_equal_with_discounted_product()
    {
        Product product = new Product();
        product.setId(10);
        product.setPrice(100d);
        product.setName("Kırmızı Ayakkabı");
        product.setCategory(Category.aNew()
                .withId(1)
                .get());

        DiscountedProduct discountedProduct = new DiscountedProduct(product,
                80d);

        assertThat(discountedProduct).isEqualTo(product);
    }

    @Test
    public void should_not_equal_with_different_class()
    {
        Product product = new Product();
        product.setId(10);
        product.setPrice(100d);
        product.setName("Kırmızı Ayakkabı");
        product.setCategory(Category.aNew()
                .withId(1)
                .get());

        assertThat(product).isNotEqualTo(Category.aNew()
                .withId(1)
                .get());
    }
}
