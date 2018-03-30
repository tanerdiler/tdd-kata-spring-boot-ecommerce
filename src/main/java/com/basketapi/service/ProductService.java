package com.basketapi.service;

import com.basketapi.domain.model.Product;
import com.basketapi.repository.ProductRepository;
import com.basketapi.service.dto.ProductDTO;
import com.basketapi.service.exception.ResourceNotFoundException;
import com.basketapi.service.mapper.ProductDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

import static com.basketapi.service.ApplicationConstants.*;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;
import static org.springframework.transaction.annotation.Propagation.SUPPORTS;

@Service
@Transactional
public class ProductService implements IService
{
    @Autowired
    private ProductRepository repository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductDTOMapper dtoMapper;

    @Override
    @Transactional(propagation = SUPPORTS, isolation = Isolation
            .READ_UNCOMMITTED)
    public boolean checkIfExists(@NotNull Integer id)
    {
        return repository.existsById(id);
    }

    @Transactional(propagation = REQUIRED)
    public ProductDTO save(ProductDTO dto)
    {
        if(!categoryService.checkIfExists(dto.getCategory().getId()))
        {
            throw new ResourceNotFoundException("Saving product operation " +
                    "failed",
                    ENTITY_NAME_CATEGORY, dto.getCategory().getId());
        }

        Product productToPersist = dtoMapper.toEntity(dto);

        return dtoMapper.toDTO(repository.save(productToPersist));
    }

    @Transactional(readOnly = true, propagation = SUPPORTS)
    public List<ProductDTO> findAll()
    {
        return repository.findAll().stream().map(dtoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(propagation = REQUIRED)
    public void delete(@NotNull Integer id)
    {
        if(!checkIfExists(id))
        {
            throw new ResourceNotFoundException("Deleting product operation " +
                    "failed",
                    ENTITY_NAME_CAMPAIGN, id);
        }
        repository.deleteById(id);
    }

    public ProductDTO find(int id) {
        return repository.findById(id)
                .map(dtoMapper::toDTO)
                .orElseThrow(()
                        -> new ResourceNotFoundException(
                        FAILED_FINDING_PRODUCT,
                        ENTITY_NAME_PRODUCT, id));
    }
}
