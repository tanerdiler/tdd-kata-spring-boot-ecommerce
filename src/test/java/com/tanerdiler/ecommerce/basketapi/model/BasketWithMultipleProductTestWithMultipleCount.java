package com.tanerdiler.ecommerce.basketapi.model;

import com.google.common.collect.Multiset;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class BasketWithMultipleProductTestWithMultipleCount
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
        BasketWithMultipleProduct basket = new BasketWithMultipleProduct();
        basket.put(p1);
        basket.put(p1);
        basket.put(p1);
        basket.put(p2);
        basket.put(p3);
        basket.put(p3);
        basket.put(p3);
        basket.put(p3);
        Map<Category, List<Multiset.Entry<Product>>> groupedProducts = basket
        .groupByCategory();
        assertThat(groupedProducts.size()).isEqualTo(3);
    }

    @Test
    public void should_add_product_with_count()
    {
        Category category = createCategory(1, "Gömlek");
        Product p1 = createProduct(1, "Mavi Gömlek", 100d, category);

        BasketWithMultipleProduct basket = new BasketWithMultipleProduct();
        basket.put(p1);
        basket.put(p1, 3);


        assertThat(basket.getCountOf(Product.aNew().withId(1).get()))
                .isEqualTo(4);
    }

    @Test
    public void should_inc_count_when_adding_same_product()
    {
        Category category = createCategory(1, "Gömlek");
        Product p1 = createProduct(1, "Mavi Gömlek", 100d, category);
        Product p2 = createProduct(2, "Sarı Gömlek", 150d, category);
        Product p3 = createProduct(3, "Yeşil Gömlek", 20d, category);
        BasketWithMultipleProduct basket = new BasketWithMultipleProduct();
        basket.put(p1);
        basket.put(p1);
        basket.put(p1);
        basket.put(p2);
        basket.put(p3);
        basket.put(p3);
        basket.put(p3);
        basket.put(p3);

        assertThat(basket.getCountOf(Product.aNew().withId(1).get()))
                .isEqualTo(3);
        assertThat(basket.getCountOf(Product.aNew().withId(3).get()))
                .isEqualTo(4);
    }

    @Test
    public void should_return_count_of_products()
    {
        Category category = createCategory(1, "Gömlek");
        Product p1 = createProduct(1, "Mavi Gömlek", 100d, category);
        Product p2 = createProduct(2, "Sarı Gömlek", 150d, category);
        Product p3 = createProduct(3, "Yeşil Gömlek", 20d, category);
        BasketWithMultipleProduct basket = new BasketWithMultipleProduct();
        basket.put(p1);
        basket.put(p1);
        basket.put(p1);
        basket.put(p2);
        basket.put(p3);
        basket.put(p3);
        basket.put(p3);
        basket.put(p3);

        assertThat(basket.getSize()).isEqualTo(8);
    }

    @Test
    public void should_remove_product()
    {
        Category category = createCategory(1, "Gömlek");
        Product p1 = createProduct(1, "Mavi Gömlek", 100d, category);
        Product p2 = createProduct(2, "Sarı Gömlek", 150d, category);
        Product p3 = createProduct(3, "Yeşil Gömlek", 20d, category);
        BasketWithMultipleProduct basket = new BasketWithMultipleProduct();
        basket.put(p1);
        basket.put(p1);
        basket.put(p1);
        basket.put(p2);
        basket.put(p3);

        int previousBasketWithMultipleProductSize = basket.getSize();
        basket.remove(Product.aNew().withId(1).get
                ());

        assertThat(basket.getSize()).isEqualTo(previousBasketWithMultipleProductSize - 1);
        assertThat(basket.getCountOf(Product.aNew().withId(1).get
                ())).isEqualTo(2);
    }

    @Test
    public void should_remove_product_with_count()
    {
        Category category = createCategory(1, "Gömlek");
        Product p1 = createProduct(1, "Mavi Gömlek", 100d, category);
        Product p2 = createProduct(2, "Sarı Gömlek", 150d, category);
        Product p3 = createProduct(3, "Yeşil Gömlek", 20d, category);
        BasketWithMultipleProduct basket = new BasketWithMultipleProduct();
        basket.put(p1);
        basket.put(p1);
        basket.put(p1);
        basket.put(p2);
        basket.put(p3);

        int previousBasketWithMultipleProductSize = basket.getSize();
        basket.remove(Product.aNew().withId(1).get
                (), 2);

        assertThat(basket.getSize()).isEqualTo(previousBasketWithMultipleProductSize - 2);
        assertThat(basket.getCountOf(Product.aNew().withId(1).get
                ())).isEqualTo(1);
    }

    @Test
    public void should_remove_all_products()
    {
        Category category = createCategory(1, "Gömlek");
        Product p1 = createProduct(1, "Mavi Gömlek", 100d, category);
        Product p2 = createProduct(2, "Sarı Gömlek", 150d, category);
        Product p3 = createProduct(3, "Yeşil Gömlek", 20d, category);
        BasketWithMultipleProduct basket = new BasketWithMultipleProduct();
        basket.put(p1);
        basket.put(p1);
        basket.put(p2);
        basket.put(p3);

        int previousBasketWithMultipleProductSize = basket.getSize();
        int removedProductCount = basket.removeAll(Product.aNew().withId(1).get
                ());

        assertThat(basket.getSize()).isEqualTo(previousBasketWithMultipleProductSize - removedProductCount);
        assertThat(removedProductCount).isEqualTo(2);
    }

    @Test(expected = IllegalStateException.class)
    public void
    should_throw_IllegalStateException_when_trying_to_remove_more_product_than_basket()
    {
        Category category = createCategory(1, "Gömlek");
        Product p1 = createProduct(1, "Mavi Gömlek", 100d, category);
        Product p2 = createProduct(2, "Sarı Gömlek", 150d, category);
        Product p3 = createProduct(3, "Yeşil Gömlek", 20d, category);
        BasketWithMultipleProduct basket = new BasketWithMultipleProduct();
        basket.put(p1);
        basket.put(p1);
        basket.put(p1);
        basket.put(p2);
        basket.put(p3);

        basket.remove(Product.aNew().withId(1).get
                (), 4);
    }

    private Product createProduct(Integer id, String name, Double price,
                                  Category category)
    {
        return Product.aNew()
                .withId(id)
                .withName(name)
                .withPrice(price)
                .relateTo(category)
                .get();
    }

    private Category createCategory(Integer id, String name)
    {
        return Category.aNew()
                .withId(id)
                .withName(name)
                .get();
    }
}
