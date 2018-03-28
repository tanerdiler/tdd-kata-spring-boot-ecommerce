package com.tanerdiler.ecommerce.basketapi.builder;

import com.tanerdiler.ecommerce.basketapi.model.*;

public class CampaignBuilder implements IValidatableBuilder<Campaign>
{
    private String name;
    private Discount discount;
    private int entityId;
    private DiscountTargetType entityType;

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
        Campaign campaign = new Campaign(name, discount, entityId, entityType);
        return campaign;
    }
}

