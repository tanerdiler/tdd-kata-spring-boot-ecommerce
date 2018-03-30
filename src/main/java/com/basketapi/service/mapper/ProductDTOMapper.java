package com.basketapi.service.mapper;

import com.basketapi.domain.model.Product;
import com.basketapi.domain.validation.BeanValidator;
import com.basketapi.service.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductDTOMapper
{
    @Autowired
    private BeanValidator beanValidator;

    @Autowired
    private CategoryDTOMapper categoryDTOMapper;

    public ProductDTOMapper(CategoryDTOMapper categoryDTOMapper, BeanValidator
            beanValidator)
    {
        this.beanValidator = beanValidator;
        this.categoryDTOMapper = categoryDTOMapper;
    }

    public Product toEntity(ProductDTO dto)
    {
        return Product.aNew()
                .withId(dto.getId())
                .withName(dto.getName())
                .withPrice(dto.getPrice())
                .relateTo(categoryDTOMapper.toEntity(dto.getCategory()))
                .validateAndGet(beanValidator);
    }

    public ProductDTO toDTO(Product product)
    {
        ProductDTO dto = new ProductDTO();

        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setCategory(categoryDTOMapper.toDTO(product.getCategory()));

        return dto;
    }
}
