package com.basketapi.domain.business;

import com.basketapi.domain.model.Basket;
import com.basketapi.domain.model.Campaign;
import com.basketapi.domain.model.IProduct;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.basketapi.domain.model.DiscountTargetType.CATEGORY;
import static com.basketapi.domain.model.DiscountTargetType.PRODUCT;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class BasketCampaignApplier
{
    private List<Campaign> campaigns;

    public BasketCampaignApplier(List<Campaign> campaigns)
    {
        this.campaigns = campaigns;
    }

    public static BasketCampaignApplier apply(List<Campaign> campaigns)
    {
        return new BasketCampaignApplier(campaigns);
    }

    public Basket to(Basket basket)
    {

        Set<IProduct> productsWithDiscount = basket.getProducts();

        applyDiscountOfProductTargetedCampaigns(basket, productsWithDiscount);

        applyDiscountOfCategoryTargetedCampaigns(productsWithDiscount);

        Basket basketWithDiscount = new Basket();
        productsWithDiscount.stream().forEach(basketWithDiscount::add);

        return basketWithDiscount;
    }

    private void applyDiscountOfCategoryTargetedCampaigns(Set<IProduct>
                                                                  productsWithDiscount) {
        List<CampaignApplier> categoryTargetedCampaigns = this.campaigns
                .stream()
                .filter(c -> CATEGORY.equals(c
                        .getTargetType())
                ).map(CampaignApplier::new).collect(toList());

        if(!categoryTargetedCampaigns.isEmpty())
        {
            for(CampaignApplier applier : categoryTargetedCampaigns)
            {
                Optional<IProduct> productWithMaxPrice = productsWithDiscount
                        .stream()
                        .filter(applier::isTargetable)
                        .max(comparing(IProduct::getPrice));

                productWithMaxPrice
                        .map(applier::apply)
                        .ifPresent(p ->
                                {
                                    productsWithDiscount.remove(p);
                                    productsWithDiscount.add(p);
                                });

            }
        }
    }

    private void applyDiscountOfProductTargetedCampaigns(Basket basket, Set<IProduct> productsWithDiscount) {
        List<CampaignApplier> productTargetedCampaigns = this.campaigns.stream()
                .filter(c -> PRODUCT.equals(c
                .getTargetType())
        ).map(CampaignApplier::new).collect(toList());

        if(!productTargetedCampaigns.isEmpty())
        {
            for(IProduct product : basket.getProducts())
            {
                for(CampaignApplier applier : productTargetedCampaigns)
                {
                    product = applier.apply(product);
                }

                productsWithDiscount.remove(product);
                productsWithDiscount.add(product);
            }
        }
    }
}
