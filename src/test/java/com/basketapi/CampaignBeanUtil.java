package com.basketapi;

import com.basketapi.domain.model.Campaign;
import com.basketapi.domain.model.DiscountTargetType;
import com.basketapi.domain.model.DiscountType;
import com.basketapi.domain.validation.BeanValidationException;
import com.basketapi.domain.validation.BeanValidator;
import com.basketapi.service.dto.CampaignDTO;
import com.basketapi.service.mapper.CampaignDTOMapper;

public class CampaignBeanUtil
{
    public static CampaignDTO createDTOWithPriceDiscount(Integer id)
    {
        CampaignDTO campaignDTO = new CampaignDTO();
        campaignDTO.setId(id);
        campaignDTO.setName("Yaz tatili kampanyası");
        campaignDTO.setTargetId(5);
        campaignDTO.setTargetType(DiscountTargetType.PRODUCT);
        campaignDTO.setDiscountAmount(100d);
        campaignDTO.setDiscountType(DiscountType.PRICE);
        return campaignDTO;
    }

    public static CampaignDTO createDTOWithRateDiscount(Integer id)
    {
        CampaignDTO campaignDTO = new CampaignDTO();
        campaignDTO.setId(id);
        campaignDTO.setName("Gömlek İndirimi");
        campaignDTO.setTargetId(5);
        campaignDTO.setTargetType(DiscountTargetType.PRODUCT);
        campaignDTO.setDiscountAmount(10d);
        campaignDTO.setDiscountLimit(30d);
        campaignDTO.setDiscountType(DiscountType.RATE);
        return campaignDTO;
    }

    public static Campaign createCampaign(Integer id, CampaignDTO dto)
    {
        dto.setId(id);
        Campaign campaign = null;
        try {
            campaign = new CampaignDTOMapper(new BeanValidator()).toEntity
                    (dto);
        } catch (BeanValidationException e)
        {
            // DO NOTHING
        }
        return campaign;
    }
}
