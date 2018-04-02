package com.basketapi.service;

import com.basketapi.domain.model.Category;
import com.basketapi.repository.CategoryRepository;
import com.basketapi.service.dto.CategoryDTO;
import com.basketapi.service.exception.EntityWithIdException;
import com.basketapi.service.exception.ResourceNotFoundException;
import com.basketapi.service.mapper.CategoryDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

import static com.basketapi.service.ApplicationConstants.*;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Isolation.READ_UNCOMMITTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.transaction.annotation.Propagation.SUPPORTS;

@Service
@Transactional
public class CategoryService implements IService
{
    @Autowired
    private CategoryDTOMapper dtoMapper;

    @Autowired
    private CategoryRepository repository;

    @Override
    @Transactional(propagation = SUPPORTS, isolation = READ_COMMITTED)
    public boolean checkIfExists(@NotNull Integer id)
    {
        return repository.existsById(id);
    }

    @Transactional(propagation = REQUIRED)
    public CategoryDTO save(CategoryDTO dto)
    {
        if(dto.getId()!=null)
        {
            throw  new
                    EntityWithIdException(FAILED_SAVING_CATEGORY,
                    ENTITY_NAME_CATEGORY, dto
                    .getId());
        }

        Category categoryToPersist = dtoMapper.toEntity(dto);

        return dtoMapper.toDTO(repository.save(categoryToPersist));
    }

    @Transactional(readOnly = true, propagation = SUPPORTS)
    public List<CategoryDTO> findAll()
    {
        return repository.findAll().stream().map(dtoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(propagation = REQUIRED)
    public void delete(@NotNull Integer id)
    {
        if(!checkIfExists(id))
        {
            throw new ResourceNotFoundException(FAILED_DELETING_CATEGORY,
                    ENTITY_NAME_CAMPAIGN, id);
        }
        repository.deleteById(id);
    }

    @Transactional(readOnly = false, propagation = REQUIRED)
    public CategoryDTO update(Integer id, @Valid CategoryDTO dto)
    {
        if(!checkIfExists(id))
        {
            throw new ResourceNotFoundException(FAILED_UPDATING_CATEGORY,
                    ENTITY_NAME_CATEGORY, id);
        }

        dto.setId(id);

        Category categoryWithNewInfo = dtoMapper.toEntity(dto);

        Category updatedCategory = repository.save(categoryWithNewInfo);

        return dtoMapper.toDTO(updatedCategory);
    }

    @Transactional(readOnly = true, propagation = SUPPORTS, isolation = READ_UNCOMMITTED)
    public CategoryDTO find(int id)
    {
        return repository.findById(id)
                .map(dtoMapper::toDTO)
                .orElseThrow(()
                        -> new ResourceNotFoundException(
                        FAILED_FINDING_CATEGORY,
                        ENTITY_NAME_CATEGORY, id));
    }
}
