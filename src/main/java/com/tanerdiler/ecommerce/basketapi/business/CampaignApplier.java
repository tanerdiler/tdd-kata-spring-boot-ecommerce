package com.tanerdiler.ecommerce.basketapi.business;

import com.tanerdiler.ecommerce.basketapi.model.*;

import java.util.function.Predicate;

import static com.tanerdiler.ecommerce.basketapi.model.DiscountTargetType
        .CATEGORY;

public class CampaignApplier {

    private Campaign campaign;

    public CampaignApplier(Campaign campaign)
    {
        this.campaign = campaign;
    }

    public boolean isTargetable(IProduct discountable)
    {
        return getDetector().test(discountable);
    }

    private Predicate<IProduct> getDetector()
    {
        if(CATEGORY.equals(campaign.getTargetType()))
        {
            return p -> p.getCategory().getId().equals(campaign.getTargetId());
        }
        else
        {
            return p -> p.getId().equals(campaign.getTargetId());
        }
    }

    public DiscountedProduct apply(IProduct discountable)
    {
        Double discountedPrice;
        if(!isTargetable(discountable))
        {
            discountedPrice = discountable.getPrice();
        }
        else
        {
            Discount discount = campaign.getDiscount();
            discountedPrice = discount.applyTo(discountable);
        }

        return new DiscountedProduct(discountable, discountedPrice);
    }

    public Campaign getCampaign() {
        return campaign;
    }
}
