package com.basketapi.web.rest;

import com.basketapi.BeanUtil;
import com.basketapi.EcommerceBasketApiApplication;
import com.basketapi.domain.model.Campaign;
import com.basketapi.repository.CampaignRepository;
import com.basketapi.service.BasketService;
import com.basketapi.service.dto.BasketDTO;
import com.basketapi.service.dto.BasketItemDTO;
import com.basketapi.service.exception.ExceptionTranslator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.basketapi.BeanUtil.*;
import static com.basketapi.web.rest.TestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request
        .MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result
        .MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= EcommerceBasketApiApplication.class, webEnvironment = SpringBootTest
        .WebEnvironment.RANDOM_PORT)
public class BasketResourceIT {

    @Autowired
    private BasketService basketService;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restMockMvc;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private BeanUtil beanUtil;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
        final BasketResource basketResource = new BasketResource
                (basketService);
        this.restMockMvc = MockMvcBuilders.standaloneSetup(basketResource)
                .setControllerAdvice(exceptionTranslator)
                .build();
    }

    @Test
    public void should_apply_campaign_discounts() throws Exception
    {
        beanUtil.deleteAll();

        BasketDTO basketDTO = fillAndGetBasketDTO();
        List<Campaign> campaigns = createCampaigns();
        campaignRepository.saveAll(campaigns);

        MvcResult result = restMockMvc.perform(post("/api/v1/campaigns/apply")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(basketDTO)))
                .andExpect(status().isOk())
                .andReturn();

        BasketDTO sfd = convertJsonBytesToObject(BasketDTO.class,
                result
                .getResponse
                        ().getContentAsByteArray());

        Double totalDiscountedPrice = sfd
                .getItems()
                .stream()
                .mapToDouble(BasketItemDTO::getDiscountedPrice)
                .sum();

        assertThat(totalDiscountedPrice).isEqualTo(690.99);
    }

    @Test
    public void should_apply_product_targeted_campaigns_to_all_products() throws Exception {
        beanUtil.deleteAll();

        BasketDTO basketDTO = fillAndGetBasketDTO();

        List<Campaign> productCampaigns = createProductCampaigns();
        campaignRepository.saveAll(productCampaigns);

        MvcResult result = restMockMvc.perform(post("/api/v1/campaigns/apply")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(basketDTO)))
                .andExpect(status().isOk())
                .andReturn();

        BasketDTO sfd = convertJsonBytesToObject(BasketDTO.class,
                result
                        .getResponse
                                ().getContentAsByteArray());

        Double totalDiscountedPrice = sfd
                .getItems()
                .stream()
                .mapToDouble(BasketItemDTO::getDiscountedPrice)
                .sum();

        assertThat(totalDiscountedPrice).isEqualTo(740.99);
    }

    @Test
    public void
    should_apply_category_targeted_campaigns_to_a_product_with_high_price() throws Exception {
        beanUtil.deleteAll();

        BasketDTO basketDTO = fillAndGetBasketDTO();

        List<Campaign> productCampaigns = createCategoryCampaigns();
        campaignRepository.saveAll(productCampaigns);

        MvcResult result = restMockMvc.perform(post("/api/v1/campaigns/apply")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(basketDTO)))
                .andExpect(status().isOk())
                .andReturn();

        BasketDTO sfd = convertJsonBytesToObject(BasketDTO.class,
                result
                        .getResponse
                                ().getContentAsByteArray());

        Double totalDiscountedPrice = sfd
                .getItems()
                .stream()
                .mapToDouble(BasketItemDTO::getDiscountedPrice)
                .sum();

        assertThat(totalDiscountedPrice).isEqualTo(750.99);
    }
}