package com.basketapi.service.mapper;

import com.basketapi.domain.model.Category;
import com.basketapi.domain.model.DiscountedProduct;
import com.basketapi.domain.model.IProduct;
import com.basketapi.domain.model.Product;
import com.basketapi.domain.validation.BeanValidator;
import com.basketapi.service.dto.BasketItemDTO;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BasketItemDTOMapperTest
{
    @Test
    public void should_return_string_presentation()
    {
        BasketItemDTO dto = new BasketItemDTO(18, "Kedi Maması", 100d,
                80d, 12);
        assertThat(dto.toString()).isEqualTo("BasketItemDTO(id=18, name=Kedi Maması, price=100.0, discountedPrice=80.0, categoryId=12)");
    }

    @Test
    public void should_convert_dto_to_product()
    {
        BasketItemDTOMapper mapper = new BasketItemDTOMapper(new BeanValidator());

        BasketItemDTO dto = new BasketItemDTO(18, "Kedi Maması", 100d,
                80d, 12);

        IProduct product = mapper.toEntity(dto);
        assertThat(product.getId()).isEqualTo(18);
        assertThat(product.getName()).isEqualTo("Kedi Maması");
        assertThat(product.getOriginalPrice()).isEqualTo(100d);
        assertThat(product.getPrice()).isEqualTo(80d);
    }

    @Test
    public void should_convert_product_to_dto()
    {
        BasketItemDTOMapper mapper = new BasketItemDTOMapper(new BeanValidator());

        Product product = Product.aNew().withId(15).withName("Terlikimsi " +
                "Ayakkabılar").withPrice(100d).relateTo(Category.aNew()
                .withId(15).withName("Ayakkabı").get()).get();

        BasketItemDTO itemDTO = mapper.toDTO(product);
        assertThat(itemDTO.getId()).isEqualTo(15);
        assertThat(itemDTO.getName()).isEqualTo("Terlikimsi " +
                "Ayakkabılar");
        assertThat(itemDTO.getPrice()).isEqualTo(100d);
        assertThat(itemDTO.getDiscountedPrice()).isEqualTo(100d);
    }

    @Test
    public void should_convert_discounted_product_to_dto()
    {
        BasketItemDTOMapper mapper = new BasketItemDTOMapper(new BeanValidator());

        Product product = Product.aNew().withId(15).withName("Terlikimsi " +
                "Ayakkabılar").withPrice(100d).relateTo(Category.aNew()
                .withId(13).withName("Ayakkabı").get())
                .get();
        DiscountedProduct discountedProduct = new DiscountedProduct(product,
            50d);

        BasketItemDTO itemDTO = mapper.toDTO(discountedProduct);
        assertThat(itemDTO.getId()).isEqualTo(15);
        assertThat(itemDTO.getName()).isEqualTo("Terlikimsi " +
                "Ayakkabılar");
        assertThat(itemDTO.getPrice()).isEqualTo(100d);
        assertThat(itemDTO.getDiscountedPrice()).isEqualTo(50d);
    }

    @Test
    public void should_not_validate_product()
    {
        BasketItemDTOMapper mapper = new BasketItemDTOMapper(new BeanValidator());

        BasketItemDTO dto = new BasketItemDTO(-18, "", -100d,
                80d, null);

        IProduct product = mapper.toEntity(dto);
        assertThat(product.getId()).isEqualTo(-18);
        assertThat(product.getName()).isEqualTo("");
        assertThat(product.getOriginalPrice()).isEqualTo(-100d);
        assertThat(product.getPrice()).isEqualTo(80d);
    }
}
