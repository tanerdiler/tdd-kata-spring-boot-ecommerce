package com.tanerdiler.ecommerce.basketapi.model;

import org.junit.Test;

import static com.tanerdiler.ecommerce.basketapi.model.DiscountTargetType
        .CATEGORY;
import static org.assertj.core.api.Assertions.assertThat;

public class CampaignTest
{
    @Test
    public void should_create_by_using_constructor_with_all_args()
    {
        Campaign campaign = new Campaign(12, "Gomleklere Indirim", Discount
                .withPrice(100d).get(), 15, CATEGORY);

        assertThat(campaign.getId()).isEqualTo(12);
        assertThat(campaign.getName()).isEqualTo("Gomleklere Indirim");
        assertThat(campaign.getTargetId()).isEqualTo(15);
        assertThat(campaign.getTargetType()).isEqualTo(CATEGORY);
    }

    @Test
    public void should_create_by_using_constructor_with_no_args()
    {
        Campaign campaign = new Campaign();
        campaign.setId(10);
        campaign.setName("Gomlek");
        campaign.setTargetId(16);
        campaign.setTargetType(CATEGORY);

        assertThat(campaign.getId()).isEqualTo(10);
        assertThat(campaign.getName()).isEqualTo("Gomlek");
        assertThat(campaign.getTargetId()).isEqualTo(16);
        assertThat(campaign.getTargetType()).isEqualTo(CATEGORY);
    }
}
