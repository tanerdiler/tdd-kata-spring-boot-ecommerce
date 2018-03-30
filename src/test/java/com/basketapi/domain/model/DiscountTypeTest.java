package com.basketapi.domain.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DiscountTypeTest {

    @Test
    public void should_return_RATE () {

        DiscountType targetType = DiscountType.of("RATE");
        assertThat(targetType).isEqualTo(DiscountType.RATE);
    }

    @Test
    public void should_return_AMOUNT () {

        DiscountType targetType = DiscountType.of("PRICE");
        assertThat(targetType).isEqualTo(DiscountType.PRICE);
    }

    @Test
    public void should_return_type_for_uppercased_string () {

        DiscountType targetType = DiscountType.of("RATE");
        assertThat(targetType).isEqualTo(DiscountType.RATE);
    }

    @Test
    public void should_return_type_for_lowercased_string () {

        DiscountType targetType = DiscountType.of("rate");
        assertThat(targetType).isEqualTo(DiscountType.RATE);
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_NullPointerException_on_string_is_null () {

        DiscountType.of(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_IllegalParameterException_on_string_is_not_known () {

        DiscountType.of("hello");
    }
}
