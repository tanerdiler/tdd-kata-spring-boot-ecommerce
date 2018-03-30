package com.basketapi.domain.builder;

import com.basketapi.domain.model.Discount;
import com.basketapi.domain.model.DiscountType;
import com.basketapi.domain.model.DiscountWithPrice;
import com.basketapi.domain.validation.BeanValidationException;
import com.basketapi.domain.validation.BeanValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.basketapi.domain.model.DiscountType.PRICE;
import static org.assertj.core.api.Assertions.assertThat;

public class DiscountWithPriceBuilderTest {

    private BeanValidator validator;

    @Before
    public void init()
    {
        validator = new BeanValidator();
    }

    @After
    public void terminate()
    {
        validator.close();
    }

    @Test
    public void should_create_by_using_constructor_with_all_args()
    {
        DiscountWithPrice discount = new DiscountWithPrice(10d, PRICE);

        assertThat(discount.getId()).isNull();
        assertThat(discount.getPrice()).isEqualTo(10d);
        assertThat(discount.getType()).isEqualTo(PRICE);
    }

    @Test
    public void should_create_by_using_constructor_with_no_args()
    {
        DiscountWithPrice discount = new DiscountWithPrice();
        discount.setId(5);
        discount.setPrice(10d);
        discount.setType(PRICE);

        assertThat(discount.getId()).isEqualTo(5);
        assertThat(discount.getPrice()).isEqualTo(10d);
        assertThat(discount.getType()).isEqualTo(PRICE);
    }

    @Test
    public void should_build_discount_with_price()
    {
        DiscountWithPrice discount = Discount.withPrice(100d).get();
        assertThat(discount.getPrice()).isEqualTo(100);
        assertThat(discount.getType()).isEqualTo(DiscountType.PRICE);
    }

    @Test(expected = BeanValidationException.class)
    public void should_throw_BeanValidationException_when_price_is_negative()
            throws BeanValidationException
    {
        Discount.withPrice(-100d).validateAndGet(validator);
    }

}
