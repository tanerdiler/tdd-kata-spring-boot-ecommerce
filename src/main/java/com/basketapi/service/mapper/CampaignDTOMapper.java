package com.basketapi.service.mapper;

import com.basketapi.domain.model.*;
import com.basketapi.domain.validation.BeanValidator;
import com.basketapi.service.dto.CampaignDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.basketapi.domain.model.DiscountType.RATE;

@Component
public class CampaignDTOMapper
{
    @Autowired
    private BeanValidator beanValidator;

    public CampaignDTOMapper(BeanValidator beanValidator)
    {
        this.beanValidator = beanValidator;
    }

    public Campaign toEntity(CampaignDTO dto)
    {
        Discount discount;

        // TODO DISCOUNT TYPE LOGIC
        if(RATE.equals(dto.getDiscountType()))
        {
            discount = Discount.withRate(dto.getDiscountAmount())
                    .withLimit
                    (dto.getDiscountLimit()).validateAndGet(beanValidator);
        }
        else
        {
            discount = Discount.withPrice(dto
                    .getDiscountAmount())
                    .validateAndGet(beanValidator);
        }

        return Campaign.aNew().withId(dto.getId()).withName(dto
                .getName())
                .withDiscount(discount)
                .target(dto.getTargetId(), dto.getTargetType())
                .validateAndGet(beanValidator);
    }

    public CampaignDTO toDTO(Campaign campaign)
    {
        IDiscount discount = campaign.getDiscount();

        CampaignDTO dto = new CampaignDTO();

        dto.setDiscountType(discount.getType());

        // TODO DISCOUNT TYPE LOGIC
        if(campaign.getDiscount().isType(RATE))
        {
            DiscountWithRate rated = discount.castToRate();
            dto.setDiscountAmount(rated.getRate());
            dto.setDiscountLimit(rated.getLimit());
        }
        else
        {
            DiscountWithPrice priced = discount.castToPrice();
            dto.setDiscountAmount(priced.getPrice());
        }

        dto.setTargetId(campaign.getTargetId());
        dto.setTargetType(campaign.getTargetType());

        dto.setId(campaign.getId());
        dto.setName(campaign.getName());

        return dto;
    }
}
