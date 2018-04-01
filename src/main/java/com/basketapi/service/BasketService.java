package com.basketapi.service;

import com.basketapi.domain.business.BasketCampaignApplier;
import com.basketapi.domain.model.Basket;
import com.basketapi.domain.model.Campaign;
import com.basketapi.repository.CampaignRepository;
import com.basketapi.service.dto.BasketDTO;
import com.basketapi.service.mapper.BasketDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BasketService
{
    @Autowired
    private BasketDTOMapper mapper;

    @Autowired
    private CampaignRepository campaignRepository;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public BasketDTO applyCampaigns(BasketDTO basketDTO)
    {
        Basket basket = mapper.toEntity(basketDTO);
        List<Campaign> campaigns = campaignRepository.findAll();
        Basket discountedBasket = BasketCampaignApplier.apply(campaigns).to
            (basket);
        return mapper.toDTO(discountedBasket);
    }
}
