package com.tanerdiler.ecommerce.basketapi.builder;

import com.tanerdiler.ecommerce.basketapi.model.Discount;
import com.tanerdiler.ecommerce.basketapi.model.DiscountType;
import com.tanerdiler.ecommerce.basketapi.model.DiscountWithPrice;
import com.tanerdiler.ecommerce.basketapi.model.DiscountWithRate;
import com.tanerdiler.ecommerce.basketapi.validation.BeanValidationException;
import com.tanerdiler.ecommerce.basketapi.validation.BeanValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
    public void should_build_discount_with_price()
    {
        DiscountWithPrice discount = Discount.withPrice(100d).get();
        assertThat(discount.getPrice()).isEqualTo(100);
        assertThat(discount.getType()).isEqualTo(DiscountType.AMOUNT);
    }

    @Test(expected = BeanValidationException.class)
    public void should_throw_BeanValidationException_when_price_is_negative()
            throws BeanValidationException
    {
        Discount.withPrice(-100d).validateAndGet(validator);
    }

}
