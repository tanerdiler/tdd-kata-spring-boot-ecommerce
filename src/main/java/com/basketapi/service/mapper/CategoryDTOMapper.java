package com.basketapi.service.mapper;

import com.basketapi.domain.model.Category;
import com.basketapi.domain.validation.BeanValidator;
import com.basketapi.service.dto.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryDTOMapper
{
    @Autowired
    private BeanValidator beanValidator;

    public CategoryDTOMapper(BeanValidator beanValidator)
    {
        this.beanValidator = beanValidator;
    }

    public Category toEntity(CategoryDTO dto)
    {
        return Category.aNew().withId(dto.getId()).withName(dto
                .getName())
                .validateAndGet(beanValidator);
    }

    public CategoryDTO toDTO(Category category)
    {
        CategoryDTO dto = new CategoryDTO();

        dto.setId(category.getId());
        dto.setName(category.getName());

        return dto;
    }
}
