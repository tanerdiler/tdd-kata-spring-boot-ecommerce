package com.basketapi.service.mapper;

import com.basketapi.domain.model.Category;
import com.basketapi.domain.model.DiscountedProduct;
import com.basketapi.domain.model.IProduct;
import com.basketapi.domain.model.Product;
import com.basketapi.domain.validation.BeanValidator;
import com.basketapi.service.dto.BasketItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BasketItemDTOMapper
{
    @Autowired
    private BeanValidator beanValidator;

    public BasketItemDTOMapper(BeanValidator beanValidator)
    {
        this.beanValidator = beanValidator;
    }

    public BasketItemDTO toDTO(IProduct product)
    {
        BasketItemDTO dto = new BasketItemDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getOriginalPrice());
        dto.setDiscountedPrice(product.getPrice());
        dto.setCategoryId(product.getCategory().getId());
        return dto;
    }

    public IProduct toEntity(BasketItemDTO dto)
    {
        Product product =
                Product.aNew().withId(dto.getId())
                        .withName(dto.getName())
                        .withPrice(dto.getPrice())
                        .relateTo(
                                Category.aNew()
                                        .withId(dto.getCategoryId())
                                        .get())
                        .get();

        return new DiscountedProduct(product,
                dto.getDiscountedPrice());
    }
}
