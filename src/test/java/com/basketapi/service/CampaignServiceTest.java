package com.basketapi.service;

import com.basketapi.config.BaseMockitoTest;
import com.basketapi.domain.model.Campaign;
import com.basketapi.domain.validation.BeanValidationException;
import com.basketapi.domain.validation.BeanValidator;
import com.basketapi.repository.CampaignRepository;
import com.basketapi.service.dto.CampaignDTO;
import com.basketapi.service.exception.ResourceNotFoundException;
import com.basketapi.service.mapper.CampaignDTOMapper;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.basketapi.CampaignBeanUtil.*;
import static com.basketapi.domain.model.DiscountTargetType.CATEGORY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CampaignServiceTest extends BaseMockitoTest
{
    @InjectMocks
    private CampaignService campaignService;
    @Mock
    private CampaignRepository repo;
    @Mock
    private ProductService productService;
    @Mock
    private CategoryService categoryService;
    @Spy
    private CampaignDTOMapper mapper = new CampaignDTOMapper(new BeanValidator());


    @Test
    public void should_delete_campaign()
            throws BeanValidationException
    {
        when(campaignService.checkIfExists(1)).thenReturn(true);

        campaignService.delete(1);

        verify(repo).deleteById(1);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void should_throw_ResourceNotFoundException_while_deleting_campaign()
            throws BeanValidationException
    {
        when(campaignService.checkIfExists(1)).thenReturn(false);

        campaignService.delete(1);
    }

    @Test
    public void should_save_campaign_with_price_discount()
            throws BeanValidationException
    {
        CampaignDTO dto = createDTOWithPriceDiscount(null);
        Campaign campaign = createCampaign(1, createDTOWithPriceDiscount
                (null));
        CampaignDTO dtoOfPersistedCampaign = createDTOWithPriceDiscount(1);

        when(productService.checkIfExists(any())).thenReturn(true);
        when(categoryService.checkIfExists(any())).thenReturn(true);
        when(repo.save(any(Campaign.class))).thenReturn(campaign);

        CampaignDTO  persistedCampaignDTO = campaignService.save(dto);
        assertThat(persistedCampaignDTO).isEqualToComparingFieldByField(dtoOfPersistedCampaign);
    }

    @Test
    public void should_save_campaign_with_rate_discount()
            throws BeanValidationException
    {
        CampaignDTO dto = createDTOWithRateDiscount(null);
        Campaign campaign = createCampaign(1, createDTOWithRateDiscount
                (null));
        CampaignDTO dtoOfPersistedCampaign = createDTOWithRateDiscount(1);

        when(productService.checkIfExists(any())).thenReturn(true);
        when(categoryService.checkIfExists(any())).thenReturn(true);
        when(repo.save(any(Campaign.class))).thenReturn(campaign);

        CampaignDTO  persistedCampaignDTO = campaignService.save(dto);
        assertThat(persistedCampaignDTO).isEqualToComparingFieldByField(dtoOfPersistedCampaign);
    }

    @Test
    public void should_find_campaign()
            throws BeanValidationException
    {
        Campaign campaign = createCampaign(1, createDTOWithRateDiscount
                (null));
        CampaignDTO dtoOfPersistedCampaign = createDTOWithRateDiscount(1);

        when(repo.findById(1)).thenReturn(Optional.of(campaign));

        CampaignDTO  persistedCampaignDTO = campaignService.find(1);
        assertThat(persistedCampaignDTO).isEqualToComparingFieldByField(dtoOfPersistedCampaign);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void should_throw_ResourceNotFoundException_when_campaign_not_found()
    {
        CampaignDTO dtoOfPersistedCampaign = createDTOWithRateDiscount(1);

        when(repo.findById(1)).thenReturn(Optional.ofNullable(null));

        CampaignDTO  persistedCampaignDTO = campaignService.find(1);
        assertThat(persistedCampaignDTO).isEqualToComparingFieldByField(dtoOfPersistedCampaign);
    }

    @Test
    public void should_return_list_of_campaigns()
    {
        CampaignDTO dto = createDTOWithPriceDiscount(null);
        Campaign campaign1 = createCampaign(1, createDTOWithPriceDiscount
                (null));
        Campaign campaign2 = createCampaign(2, createDTOWithPriceDiscount
                (null));
        Campaign campaign3 = createCampaign(3, createDTOWithPriceDiscount
                (null));
        List<Campaign> campaigns = Arrays.asList(campaign1, campaign2, campaign3);
        when(repo.findAll()).thenReturn(campaigns);

        CampaignDTO dtoOfPersistedCampaign1 = createDTOWithPriceDiscount(1);
        CampaignDTO dtoOfPersistedCampaign2 = createDTOWithPriceDiscount(2);
        CampaignDTO dtoOfPersistedCampaign3 = createDTOWithPriceDiscount(3);
        List<CampaignDTO> dtoListOfPersistedCampaigns = Arrays.asList(dtoOfPersistedCampaign1,
                dtoOfPersistedCampaign2,
                dtoOfPersistedCampaign3);


        List<CampaignDTO>  listOfPersistedCampaignDTO = campaignService
                .findAll();

        for(int i=0; i<listOfPersistedCampaignDTO.size(); i++)
        {
            assertThat(listOfPersistedCampaignDTO.get(i))
                    .isEqualToComparingFieldByField
                            (dtoListOfPersistedCampaigns.get(i));
        }
    }

    @Test(expected = BeanValidationException.class)
    public void
    should_throw_BeanValidationException_while_persisting_invalid_campaign()
            throws BeanValidationException
    {
        CampaignDTO invalidDTO = createDTOWithPriceDiscount(null);
        invalidDTO.setDiscountAmount(-100d);

        when(productService.checkIfExists(any())).thenReturn(true);
        when(categoryService.checkIfExists(any())).thenReturn(true);

        campaignService.save(invalidDTO);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void
    should_throw_ResourceNotFoundException_while_targeted_product_not_exist()
            throws BeanValidationException
    {
        when(productService.checkIfExists(any())).thenReturn(false);

        CampaignDTO invalidDTO = createDTOWithPriceDiscount(null);

        campaignService.save(invalidDTO);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void
    should_throw_ResourceNotFoundException_while_targeted_category_not_exist()
            throws BeanValidationException
    {
        when(categoryService.checkIfExists(any())).thenReturn(false);

        CampaignDTO invalidDTO = createDTOWithPriceDiscount(null);
        invalidDTO.setTargetType(CATEGORY);

        campaignService.save(invalidDTO);
    }
}
