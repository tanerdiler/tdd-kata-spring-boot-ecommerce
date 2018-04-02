package com.basketapi.service;

import com.basketapi.domain.model.Campaign;
import com.basketapi.repository.CampaignRepository;
import com.basketapi.service.dto.CampaignDTO;
import com.basketapi.service.exception.EntityWithIdException;
import com.basketapi.service.exception.ResourceNotFoundException;
import com.basketapi.service.mapper.CampaignDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

import static com.basketapi.domain.model.DiscountTargetType.PRODUCT;
import static com.basketapi.service.ApplicationConstants.*;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.transaction.annotation.Propagation.SUPPORTS;

@Service
@Transactional
public class CampaignService implements IService
{
    @Autowired
    private CampaignDTOMapper dtoMapper;

    @Autowired
    private CampaignRepository repository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Override
    @Transactional(readOnly = true, propagation = SUPPORTS, isolation = READ_COMMITTED)
    public boolean checkIfExists(@NotNull Integer id)
    {
        return repository.existsById(id);
    }

    @Transactional(propagation = REQUIRED)
    public CampaignDTO save(CampaignDTO dto)
    {
        if(dto.getId()!=null)
        {
            throw  new
                    EntityWithIdException(FAILED_SAVING_CAMPAIGN,
                    ENTITY_NAME_CAMPAIGN, dto
                    .getId());
        }

        checkTargetExistenceIfNotThrowException(dto);

        Campaign campaignToPersist = dtoMapper.toEntity(dto);

        return dtoMapper.toDTO(repository.save(campaignToPersist));
    }

    private void checkTargetExistenceIfNotThrowException(CampaignDTO dto)
    {
        if(PRODUCT.equals(dto.getTargetType()))
        {
            if(!productService.checkIfExists(dto.getTargetId()))
            {
                throw new ResourceNotFoundException(FAILED_SAVING_CAMPAIGN,
                        ENTITY_NAME_PRODUCT, dto.getTargetId());
            }
        }
        else
        {
            if(!categoryService.checkIfExists(dto.getTargetId()))
            {
                throw new ResourceNotFoundException(FAILED_SAVING_CAMPAIGN,
                        ENTITY_NAME_CATEGORY, dto.getTargetId());
            }
        }
    }

    @Transactional(readOnly = true, propagation = SUPPORTS)
    public List<CampaignDTO> findAll()
    {
        return repository.findAll().stream().map(dtoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(propagation = REQUIRED)
    public void delete(@NotNull Integer id)
    {
        if(!checkIfExists(id))
        {
            throw new ResourceNotFoundException(FAILED_DELETING_CAMPAIGN,
                    ENTITY_NAME_CAMPAIGN, id);
        }
        repository.deleteById(id);
    }

    @Transactional(readOnly = true, propagation = SUPPORTS)
    public CampaignDTO find(int id)
    {
        return repository.findById(id)
                .map(dtoMapper::toDTO)
                .orElseThrow(()
                -> new ResourceNotFoundException(
                        FAILED_FINDING_CAMPAIGN,
                        ENTITY_NAME_CAMPAIGN, id));
    }

    @Transactional(readOnly = false, propagation = REQUIRED)
    public CampaignDTO update(Integer id, @Valid CampaignDTO dto)
    {
        if(!checkIfExists(id))
        {
            throw new ResourceNotFoundException(FAILED_UPDATING_CAMPAIGN,
                    ENTITY_NAME_CAMPAIGN, id);
        }

        checkTargetExistenceIfNotThrowException(dto);

        dto.setId(id);

        Campaign campaignWithNewInfo = dtoMapper.toEntity(dto);

        Campaign updatedCampaign = repository.save(campaignWithNewInfo);

        return dtoMapper.toDTO(updatedCampaign);
    }
}
