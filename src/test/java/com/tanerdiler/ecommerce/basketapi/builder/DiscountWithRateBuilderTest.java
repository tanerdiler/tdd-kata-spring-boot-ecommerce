package com.tanerdiler.ecommerce.basketapi.builder;

import com.tanerdiler.ecommerce.basketapi.model.Discount;
import com.tanerdiler.ecommerce.basketapi.model.DiscountWithRate;
import com.tanerdiler.ecommerce.basketapi.validation.BeanValidationException;
import com.tanerdiler.ecommerce.basketapi.validation.BeanValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.tanerdiler.ecommerce.basketapi.model.DiscountType.RATE;
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
    public void should_create_by_using_constructor_with_all_args()
    {
        DiscountWithRate discount = new DiscountWithRate(10d, 20d, RATE);
        assertThat(discount.getRate()).isEqualTo(10d);
        assertThat(discount.getLimit()).isEqualTo(20d);
        assertThat(discount.getType()).isEqualTo(RATE);
    }

    @Test
    public void should_create_by_using_constructor_with_no_args()
    {
        DiscountWithRate discount = new DiscountWithRate();
        discount.setRate(10d);
        discount.setLimit(30d);
        discount.setType(RATE);

        assertThat(discount.getRate()).isEqualTo(10d);
        assertThat(discount.getLimit()).isEqualTo(30d);
        assertThat(discount.getType()).isEqualTo(RATE);
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
        assertThat(discount.getType()).isEqualTo(RATE);
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
