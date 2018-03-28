package com.tanerdiler.ecommerce.basketapi.business;

import com.tanerdiler.ecommerce.basketapi.model.*;
import org.junit.Test;

import static com.tanerdiler.ecommerce.basketapi.TestUtil.createCategory;
import static com.tanerdiler.ecommerce.basketapi.TestUtil.createProduct;
import static com.tanerdiler.ecommerce.basketapi.model.DiscountTargetType
        .CATEGORY;
import static com.tanerdiler.ecommerce.basketapi.model.DiscountTargetType
        .PRODUCT;
import static org.assertj.core.api.Assertions.assertThat;

public class CampaignApplierTest {

    @Test
    public void
    should_detect_product_is_targetable_for_product_targeted_campaign()
    {
        Category category = createCategory(1, "Gömlek");
        Product p1 = createProduct(1, "Mavi Gömlek", 100d, category);
        Campaign campaign = getCampaign(Discount.withPrice(10d).get(), 1,
                CATEGORY);

        assertThat(new CampaignApplier(campaign).isTargetable(p1))
                .isTrue();
    }

    @Test
    public void
    should_detect_product_is_targetable_for_category_targeted_campaign()
    {
        Discount discount = Discount.withPrice(10d).get();
        Product product = getProduct();
        Campaign campaign = getCampaign(discount, 5, CATEGORY);

        assertThat(new CampaignApplier(campaign).isTargetable(product))
                .isTrue();
    }

    @Test
    public void
    should_detect_product_is_not_targetable_for_product_targeted_campaign()
    {
        Discount discount = Discount.withPrice(10d).get();
        Product produt = getProduct();
        Campaign campaign = getCampaign(discount, 10,
                PRODUCT);

        assertThat(new CampaignApplier(campaign).isTargetable(produt))
                .isFalse();
    }

    @Test
    public void
    should_detect_product_is_not_targetable_for_category_targeted_campaign()
    {
        Discount discount = Discount.withPrice(10d).get();
        Product product = getProduct();
        Campaign campaign = getCampaign(discount, 99, CATEGORY);

        assertThat(new CampaignApplier(campaign).isTargetable(product))
                .isFalse();
    }

    @Test
    public void should_apply_discount_of_campaign_with_price()
    {
        Product product = getProduct();
        Campaign campaign = getCampaign(Discount.withPrice(10d).get(), 5,
                CATEGORY);
        DiscountedProduct discountedProduct = new CampaignApplier(campaign)
                .apply(product);

        assertThat(discountedProduct.getOriginalPrice())
                .isEqualTo(100);
        assertThat(discountedProduct.getPrice())
                .isEqualTo(90);
    }

    @Test
    public void should_apply_discount_of_campaign_with_rate()
    {
        Product product = getProduct();
        Campaign campaign = getCampaign(Discount.withRate(20d).withLimit(30d)
                .get(), 1, PRODUCT);
        DiscountedProduct discountedProduct = new CampaignApplier(campaign)
                .apply(product);

        assertThat(discountedProduct.getOriginalPrice())
                .isEqualTo(100);
        assertThat(discountedProduct.getPrice())
                .isEqualTo(80);
    }

    @Test
    public void should_apply_limit_on_discounting_of_campaign_with_rate()
    {
        Discount discount = Discount.withRate(40d).withLimit(20d).get();
        Product product = getProduct();
        Campaign campaign = getCampaign(discount, 5, CATEGORY);
        DiscountedProduct discountedProduct = new CampaignApplier(campaign)
                .apply(product);

        assertThat(discountedProduct.getOriginalPrice())
                .isEqualTo(100);
        assertThat(discountedProduct.getPrice())
                .isEqualTo(80);
    }

    @Test
    public void
            should_not_calc_discount_when_product_is_not_been_target_by_campaign()
    {
        Discount discount = Discount.withRate(40d).withLimit(20d).get();
        Product product = getProduct();
        Campaign campaign = getCampaign(discount, 15, CATEGORY);
        DiscountedProduct discountedProduct = new CampaignApplier(campaign)
                .apply(product);

        assertThat(discountedProduct.getOriginalPrice())
                .isEqualTo(100);
        assertThat(discountedProduct.getPrice())
                .isEqualTo(100);
    }

    private Campaign getCampaign(Discount discount, int targetId,
                                 DiscountTargetType targetType) {
        return Campaign.aNew()
                    .withName("Mavi Gomleklerde 1OTL eksik öde")
                    .withDiscount(discount)
                    .target(targetId, targetType)
                    .get();
    }

    private Product getProduct() {
        Category category = createCategory(5, "Gömlek");
        return createProduct(1, "Mavi Gömlek", 100d, category);
    }
}
