package com.basketapi.domain.business;

import com.basketapi.domain.model.Basket;
import com.basketapi.domain.model.Campaign;
import org.junit.Test;

import java.util.List;

import static com.basketapi.BeanUtil.*;
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

        List<Campaign> campaigns = createCampaigns();

        Basket discountedBasket = BasketCampaignApplier.apply(campaigns).to
                (basket);

        assertThat(discountedBasket.getTotalPrice()).isEqualTo(690.99);
    }





}
