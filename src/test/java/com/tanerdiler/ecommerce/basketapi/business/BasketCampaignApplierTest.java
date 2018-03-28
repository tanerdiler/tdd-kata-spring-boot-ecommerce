package com.tanerdiler.ecommerce.basketapi.business;

import com.google.common.collect.Lists;
import com.tanerdiler.ecommerce.basketapi.model.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.tanerdiler.ecommerce.basketapi.TestUtil.createCategory;
import static com.tanerdiler.ecommerce.basketapi.TestUtil.createProduct;
import static com.tanerdiler.ecommerce.basketapi.model.DiscountTargetType
        .CATEGORY;
import static com.tanerdiler.ecommerce.basketapi.model.DiscountTargetType
        .PRODUCT;
import static org.assertj.core.api.Assertions.assertThat;

public class BasketCampaignApplierTest
{
    @Test
    public void should_apply_product_targeted_campaigns_to_all_products()
    {
        Basket basket = fillAndGetBasket();
        List<Campaign> campaigns = createProductCampaigns();
        Basket discountedBasket = BasketCampaignApplier.apply(campaigns).to
                (basket);
        assertThat(discountedBasket.getTotalPrice()).isEqualTo(740.99);
    }

    @Test
    public void
    should_apply_category_targeted_campaigns_to_a_product_with_high_price()
    {
        Basket basket = fillAndGetBasket();
        List<Campaign> campaigns = createCategoryCampaigns();
        Basket discountedBasket = BasketCampaignApplier.apply(campaigns).to
                (basket);
        assertThat(discountedBasket.getTotalPrice()).isEqualTo(750.99);
    }

    @Test
    public void
    should_apply_all_campaigns()
    {
        Basket basket = fillAndGetBasket();

        List<Campaign> campaigns = new ArrayList<>();
        campaigns.addAll(createProductCampaigns());
        campaigns.addAll(createCategoryCampaigns());


        Basket discountedBasket = BasketCampaignApplier.apply(campaigns).to
                (basket);

        assertThat(discountedBasket.getTotalPrice()).isEqualTo(690.99);
    }

    private List<Campaign> createProductCampaigns() {
        Campaign c1 = Campaign.aNew()
                .withName("Campaign 1")
                .withDiscount(Discount.withRate(5d).withLimit(100d).get())
                .target(5, PRODUCT)
                .get();
        Campaign c2 = Campaign.aNew()
                .withName("Campaign 2")
                .withDiscount(Discount.withPrice(50d).get())
                .target(10, PRODUCT)
                .get();
        List<Campaign> campaigns = Arrays.asList(c1, c2);
        return campaigns;
    }

    private List<Campaign> createCategoryCampaigns() {
        Campaign c3 = Campaign.aNew()
                .withName("Campaign 3")
                .withDiscount(Discount.withRate(10d).withLimit(30d).get())
                .target(100, CATEGORY)
                .get();
        Campaign c4 = Campaign.aNew()
                .withName("Campaign 3")
                .withDiscount(Discount.withPrice(20d).get())
                .target(200, CATEGORY)
                .get();
        List<Campaign> campaigns = Arrays.asList(c3, c4);
        return campaigns;
    }


    private Basket fillAndGetBasket() {
        Category category1 = createCategory(200, "Gömlek");
        Category category2 = createCategory(100, "Pantalon");

        Product p1 = createProduct(10, "Mavi Gömlek", 100.99d, category1);
        Product p2 = createProduct(5, "Pantul", 200d, category2);
        Product p3 = createProduct(20, "Rugan", 500d, category2);

        Basket basket = new Basket();
        basket.add(p1);
        basket.add(p2);
        basket.add(p3);
        return basket;
    }
}
