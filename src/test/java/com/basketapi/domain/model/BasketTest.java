package com.basketapi.domain.model;

import org.junit.Test;

import java.util.List;
import java.util.Map;

import static com.basketapi.BeanUtil.createCategory;
import static com.basketapi.BeanUtil.createProduct;
import static org.assertj.core.api.Assertions.assertThat;

public class BasketTest
{

    @Test
    public void should_group_products_by_category()
    {
        Category category1 = createCategory(1, "Gömlek");
        Category category2 = createCategory(1, "Pantalon");
        Category category3 = createCategory(1, "Ayakkabı");
        Product p1 = createProduct(1, "Mavi Gömlek", 100d, category1);
        Product p2 = createProduct(2, "Pantul", 150d, category2);
        Product p3 = createProduct(3, "Rugan", 20d, category3);
        Basket basket = new Basket();
        basket.add(p1);
        basket.add(p2);
        basket.add(p3);
        Map<Category, List<IProduct>> groupedProducts = basket
        .groupByCategory();
        assertThat(groupedProducts.size()).isEqualTo(3);
    }


    @Test
    public void should_return_count_of_products()
    {
        Category category = createCategory(1, "Gömlek");
        Product p1 = createProduct(1, "Mavi Gömlek", 100d, category);
        Product p2 = createProduct(2, "Sarı Gömlek", 150d, category);
        Product p3 = createProduct(3, "Yeşil Gömlek", 20d, category);
        Basket basket = new Basket();
        basket.add(p1);
        basket.add(p1);
        basket.add(p1);
        basket.add(p2);
        basket.add(p3);
        basket.add(p3);
        basket.add(p3);
        basket.add(p3);

        assertThat(basket.getSize()).isEqualTo(3);
    }

    @Test
    public void should_remove_product()
    {
        Category category = createCategory(1, "Gömlek");
        Product p1 = createProduct(1, "Mavi Gömlek", 100d, category);
        Product p2 = createProduct(2, "Sarı Gömlek", 150d, category);
        Product p3 = createProduct(3, "Yeşil Gömlek", 20d, category);
        Basket basket = new Basket();
        basket.add(p1);
        basket.add(p1);
        basket.add(p1);
        basket.add(p2);
        basket.add(p3);

        int previousBasketSize = basket.getSize();
        basket.remove(Product.aNew().withId(1).get
                ());

        assertThat(basket.getSize()).isEqualTo(previousBasketSize - 1);
    }

}
