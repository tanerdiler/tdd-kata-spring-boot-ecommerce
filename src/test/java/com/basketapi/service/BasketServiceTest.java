package com.basketapi.service;

import com.basketapi.config.BaseMockitoTest;
import com.basketapi.domain.model.Campaign;
import com.basketapi.domain.validation.BeanValidator;
import com.basketapi.repository.CampaignRepository;
import com.basketapi.service.dto.BasketDTO;
import com.basketapi.service.dto.BasketItemDTO;
import com.basketapi.service.mapper.BasketDTOMapper;
import com.basketapi.service.mapper.BasketItemDTOMapper;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.List;

import static com.basketapi.BeanUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class BasketServiceTest extends BaseMockitoTest
{
    @InjectMocks
    private BasketService basketService;

    @Spy
    private BasketItemDTOMapper itemMapper = new BasketItemDTOMapper(new BeanValidator());

    @Spy
    private BasketDTOMapper mapper = new BasketDTOMapper(itemMapper);

    @Mock
    private CampaignRepository campaignRepository;

    @Test
    public void should_apply_campaigns_to_basket()
    {
        BasketDTO basketDTO = fillAndGetBasketDTO();
        List<Campaign> campaigns = createCampaigns();

        when(campaignRepository.findAll()).thenReturn(campaigns);

        BasketDTO discountedBasketDTO = basketService
            .applyCampaigns
            (basketDTO);


        assertThat(basketDTO.getItems())
                .usingElementComparatorOnFields("id","name", "price",
                        "categoryId")
                .containsAll
                        (discountedBasketDTO.getItems());

        Double totalDiscountedPrice = discountedBasketDTO
                .getItems()
                .stream()
                .mapToDouble(BasketItemDTO::getDiscountedPrice)
                .sum();

        assertThat(totalDiscountedPrice).isEqualTo(690.99);
    }

    @Test
    public void should_apply_product_targeted_campaigns_to_all_products()
    {
        BasketDTO basketDTO = fillAndGetBasketDTO();

        List<Campaign> productCampaigns = createProductCampaigns();
        when(campaignRepository.findAll()).thenReturn(productCampaigns);

        BasketDTO discountedBasketDTO = basketService
                .applyCampaigns
                        (basketDTO);

        Double totalDiscountedPrice = discountedBasketDTO
                .getItems()
                .stream()
                .mapToDouble(BasketItemDTO::getDiscountedPrice)
                .sum();

        assertThat(totalDiscountedPrice).isEqualTo(740.99);
    }

    @Test
    public void
    should_apply_category_targeted_campaigns_to_a_product_with_high_price()
    {
        BasketDTO basketDTO = fillAndGetBasketDTO();

        List<Campaign> productCampaigns = createCategoryCampaigns();
        when(campaignRepository.findAll()).thenReturn(productCampaigns);

        BasketDTO discountedBasketDTO = basketService
                .applyCampaigns
                        (basketDTO);

        Double totalDiscountedPrice = discountedBasketDTO
                .getItems()
                .stream()
                .mapToDouble(BasketItemDTO::getDiscountedPrice)
                .sum();

        assertThat(totalDiscountedPrice).isEqualTo(750.99);
    }

}
