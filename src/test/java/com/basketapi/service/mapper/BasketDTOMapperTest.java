package com.basketapi.service.mapper;

import com.basketapi.domain.model.Basket;
import com.basketapi.domain.validation.BeanValidator;
import com.basketapi.service.dto.BasketDTO;
import org.junit.Test;

import static com.basketapi.BeanUtil.fillAndGetBasket;
import static com.basketapi.BeanUtil.fillAndGetBasketDTO;
import static org.assertj.core.api.Assertions.assertThat;

public class BasketDTOMapperTest
{
    @Test
    public void should_convert_dto_to_entity()
    {
        BasketItemDTOMapper itemDTOMapper = new BasketItemDTOMapper(new BeanValidator());

        BasketDTO basketDTO = fillAndGetBasketDTO();

        BasketDTOMapper mapper = new BasketDTOMapper(itemDTOMapper);
        Basket basket = mapper.toEntity(basketDTO);

        assertThat(basket.getSize()).isEqualTo(3);
        assertThat(basket.getTotalPrice()).isEqualTo(800.99);
    }

    @Test
    public void should_convert_entity_to_dto()
    {
        BasketItemDTOMapper itemDTOMapper = new BasketItemDTOMapper(new BeanValidator());

        Basket basket = fillAndGetBasket();

        BasketDTO basketDTOToTest = fillAndGetBasketDTO();

        BasketDTOMapper mapper = new BasketDTOMapper(itemDTOMapper);
        BasketDTO basketDTO = mapper.toDTO(basket);

        assertThat(basketDTO.getItems())
                .usingElementComparatorOnFields("id","name", "price",
                        "categoryId")
                .containsAll
                (basketDTOToTest.getItems());

    }
}
