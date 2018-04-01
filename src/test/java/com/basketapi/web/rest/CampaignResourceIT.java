package com.basketapi.web.rest;


import com.basketapi.BeanUtil;
import com.basketapi.EcommerceBasketApiApplication;
import com.basketapi.domain.model.*;
import com.basketapi.repository.CampaignRepository;
import com.basketapi.repository.CategoryRepository;
import com.basketapi.repository.ProductRepository;
import com.basketapi.service.CampaignService;
import com.basketapi.service.dto.CampaignDTO;
import com.basketapi.service.exception.ExceptionTranslator;
import com.basketapi.service.mapper.CampaignDTOMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.basketapi.domain.model.DiscountTargetType.PRODUCT;
import static com.basketapi.web.rest.TestUtil.APPLICATION_JSON_UTF8;
import static com.basketapi.web.rest.TestUtil.convertObjectToJsonBytes;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request
        .MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request
        .MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result
        .MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result
        .MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes= EcommerceBasketApiApplication.class, webEnvironment = SpringBootTest
        .WebEnvironment.RANDOM_PORT)
public class CampaignResourceIT
{
    @Autowired
    private CampaignService campaignService;
    @Autowired
    private CampaignRepository campaignRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CampaignDTOMapper campaignMapper;
    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restMockMvc;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
        final CampaignResource campaignResource = new CampaignResource
                (campaignService);
        this.restMockMvc = MockMvcBuilders.standaloneSetup(campaignResource)
                .setControllerAdvice(exceptionTranslator)
                .build();
    }

    @Test
    @Transactional
    public void should_create_campaign() throws Exception {
        //GIVEN
        campaignRepository.deleteAll();

        int sizeBeforeCreate = campaignRepository.findAll().size();
        String name = "IT-CAMPAİGN-"+ UUID.randomUUID();
        Category category = BeanUtil.createRandomCategoryAndSave
                (categoryRepository);

        Product product = BeanUtil.createRandomProductAndSave
                (category, productRepository);

        Campaign campaign = Campaign.aNew()
                .withName(name)
                .withDiscount(Discount.withRate(5d).withLimit(100d).get())
                .target(product.getId(), PRODUCT)
                .get();

        CampaignDTO campaignDTO = campaignMapper.toDTO(campaign);

        //WHEN
        restMockMvc.perform(post("/api/v1/campaigns")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(campaignDTO)))
                .andExpect(status().isCreated());
        //THEN
        List<Campaign> campaignList = campaignRepository.findAll();
        assertThat(campaignList).hasSize(sizeBeforeCreate + 1);

        Campaign persitedCampaign = campaignList.get(campaignList.size() - 1);
        assertThat(persitedCampaign.getName()).isEqualTo(name);
    }


    @Test
    @Transactional
    public void should_return_400_when_creating_campaign_with_id() throws
            Exception {

        //GIVEN
        int sizeBeforeCreate = campaignRepository.findAll().size();

        String name = "IT-CATEGORY-"+UUID.randomUUID();

        Category category = BeanUtil.createRandomCategoryAndSave
                (categoryRepository);

        Product product = BeanUtil.createRandomProductAndSave
                (category, productRepository);

        Campaign campaignWithId = Campaign.aNew()
                .withId(1)
                .withName(name)
                .withDiscount(Discount.withRate(5d).withLimit(100d).get())
                .target(product.getId(), PRODUCT)
                .get();

        //WHEN
        CampaignDTO campaignDTO = campaignMapper.toDTO(campaignWithId);
        restMockMvc.perform(post("/api/v1/campaigns")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(campaignDTO)))
                .andExpect(status().isBadRequest());

        //THEN
        List<Campaign> campaignList = campaignRepository.findAll();
        assertThat(campaignList).hasSize(sizeBeforeCreate);
    }

    @Test
    @Transactional
    public void should_get_campaigns() throws Exception {
        //GIVEN
        campaignRepository.deleteAll();

        String campaignNamePrefix = "IT-CATEGORY-";
        Campaign campaignWithId = new Campaign();
        campaignWithId.setName(campaignNamePrefix + "1");


        Campaign campaignWithId2 = new Campaign();
        campaignWithId2.setName(campaignNamePrefix + "2");

        Category category = BeanUtil.createRandomCategoryAndSave
                (categoryRepository);

        Product product = BeanUtil.createRandomProductAndSave
                (category, productRepository);

        campaignWithId.setTargetId(category.getId());
        campaignWithId.setTargetType(DiscountTargetType.CATEGORY);
        campaignWithId.setDiscount(Discount.withPrice(100d).get());

        campaignWithId2.setTargetId(product.getId());
        campaignWithId2.setTargetType(DiscountTargetType.PRODUCT);
        campaignWithId2.setDiscount(Discount.withRate(10d).withLimit(30d).get());

        campaignRepository.save(campaignWithId);
        campaignRepository.save(campaignWithId2);

        //WHEN
        ResultActions resultActions = restMockMvc
                .perform(get("/api/v1/campaigns"));


        //THEN
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[*].id").value(
                        hasItem(campaignWithId.getId().intValue())))
                .andExpect(jsonPath("$.[*].id").value(
                        hasItem(campaignWithId2.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(
                        hasItem("IT-CATEGORY-" + 1)))
                .andExpect(jsonPath("$.[*].name").value(
                        hasItem("IT-CATEGORY-" + 2)));
    }


    @Test
    @Transactional
    public void should_return_campaign() throws Exception {
        //GIVEN
        Category category = BeanUtil.createRandomCategoryAndSave
                (categoryRepository);

        Product product = BeanUtil.createRandomProductAndSave
                (category, productRepository);

        Campaign campaignWithId = new Campaign();
        campaignWithId.setName("IT-CAMPAIGN-" + UUID.randomUUID());
        campaignWithId.setTargetId(category.getId());
        campaignWithId.setTargetType(DiscountTargetType.CATEGORY);
        campaignWithId.setDiscount(Discount.withPrice(100d).get());

        Campaign campaignPeristed = campaignRepository.save(campaignWithId);


        //WHEN
        ResultActions result = restMockMvc.perform(
                get("/api/v1/campaigns/" +
                        campaignPeristed.getId()));

        //THEN
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name")
                        .value(equalTo(campaignWithId.getName())))
                .andExpect(jsonPath("$.targetId")
                        .value(equalTo(product.getId())))
                .andExpect(jsonPath("$.targetType")
                        .value(equalTo("CATEGORY")))
                .andExpect(jsonPath("$.discountAmount")
                        .value(equalTo(100d)))
                .andExpect(jsonPath("$.discountLimit")
                        .value(equalTo(null)))
                .andExpect(jsonPath("$.discountType")
                        .value(equalTo("PRICE")));
    }


    @Test
    @Transactional
    public void
    should_return_400_when_creating_campaign_with_invalid_name() throws
            Exception {

        //GIVEN
        Category category = BeanUtil.createRandomCategoryAndSave
                (categoryRepository);

        Product product = BeanUtil.createRandomProductAndSave
                (category, productRepository);

        Campaign campaign = new Campaign();
        campaign.setName("");
        campaign.setTargetId(category.getId());
        campaign.setTargetType(DiscountTargetType.CATEGORY);
        campaign.setDiscount(Discount.withPrice(100d).get());

        CampaignDTO campaignDTO = campaignMapper.toDTO(campaign);

        //WHEN
        ResultActions result = restMockMvc.perform(
                post("/api/v1/campaigns")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(convertObjectToJsonBytes(campaignDTO)));

        //THEN
        result.andExpect(status().isBadRequest());
    }
    
}
