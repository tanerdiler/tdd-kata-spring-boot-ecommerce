package com.tanerdiler.ecommerce.basketapi.builder;

import com.tanerdiler.ecommerce.basketapi.model.Campaign;
import com.tanerdiler.ecommerce.basketapi.model.Discount;
import com.tanerdiler.ecommerce.basketapi.model.DiscountTargetType;

public class CampaignBuilder implements IValidatableBuilder<Campaign>
{
    private String name;
    private Discount discount;
    private int entityId;
    private DiscountTargetType entityType;
    private Integer id;

    public CampaignBuilder withId(Integer id)
    {
        this.id = id;
        return this;
    }

    public CampaignBuilder withName(String name)
    {
        this.name = name;
        return this;
    }

    public CampaignBuilder target(int entityId, DiscountTargetType entityType)
    {
        this.entityId = entityId;
        this.entityType = entityType;
        return this;
    }

    public CampaignBuilder withDiscount(Discount discount) {

        this.discount = discount;
        return this;
    }

    @Override
    public Campaign get()
    {
        return new Campaign(id, name, discount, entityId, entityType);
    }
}

