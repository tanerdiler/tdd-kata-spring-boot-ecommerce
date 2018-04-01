package com.basketapi.service.mapper;

import com.basketapi.domain.model.Basket;
import com.basketapi.service.dto.BasketDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BasketDTOMapper {

    @Autowired
    private BasketItemDTOMapper itemDTOMapper;

    public  BasketDTOMapper(BasketItemDTOMapper itemDTOMapper)
    {
        this.itemDTOMapper = itemDTOMapper;
    }

    public Basket toEntity(BasketDTO basketDTO)
    {
        Basket basket = new Basket();
        basketDTO.getItems().stream()
                .map(itemDTOMapper::toEntity)
                .forEach(basket::add);
        return basket;
    }

    public BasketDTO toDTO(Basket basket)
    {
        BasketDTO basketDTO = new BasketDTO();
        basket.getProducts()
                .stream()
                .map(itemDTOMapper::toDTO)
                .forEach(basketDTO::add);
        return basketDTO;
    }
}
