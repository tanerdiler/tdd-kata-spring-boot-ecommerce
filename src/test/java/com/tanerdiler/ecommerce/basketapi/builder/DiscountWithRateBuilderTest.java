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

public class DiscountWithRateBuilderTest {

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
    public void should_build_limited_disctount_with_rate()
    {
        DiscountWithRate discount = Discount
                .withRate(10d)
                .withLimit(100d)
                .get();

        assertThat(discount.getRate()).isEqualTo(10);
        assertThat(discount.getLimit()).isEqualTo(100);
        assertThat(discount.getType()).isEqualTo(DiscountType.RATE);
    }

    @Test(expected = BeanValidationException.class)
    public void should_throw_BeanValidationException_when_rate_is_negative()
            throws BeanValidationException
    {
        Discount.withRate(-10d).withLimit(100d).validateAndGet(validator);
    }

    @Test(expected = BeanValidationException.class)
    public void should_throw_BeanValidationException_when_rate_is_higher_than_100()
            throws BeanValidationException
    {
        Discount.withRate(200d).withLimit(100d).validateAndGet(validator);
    }
}
