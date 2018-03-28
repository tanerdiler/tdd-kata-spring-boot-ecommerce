package com.tanerdiler.ecommerce.basketapi.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DiscountTargetTypeTest {

    @Test
    public void should_return_CATEGORY () {

        DiscountTargetType targetType = DiscountTargetType.of("CATEGORY");
        assertThat(targetType).isEqualTo(DiscountTargetType.CATEGORY);
    }

    @Test
    public void should_return_PRODUCT () {

        DiscountTargetType targetType = DiscountTargetType.of("PRODUCT");
        assertThat(targetType).isEqualTo(DiscountTargetType.PRODUCT);
    }

    @Test
    public void should_return_type_for_uppercased_string () {

        DiscountTargetType targetType = DiscountTargetType.of("CATEGORY");
        assertThat(targetType).isEqualTo(DiscountTargetType.CATEGORY);
    }

    @Test
    public void should_return_type_for_lowercased_string () {

        DiscountTargetType targetType = DiscountTargetType.of("category");
        assertThat(targetType).isEqualTo(DiscountTargetType.CATEGORY);
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_NullPointerException_on_string_is_null () {

        DiscountTargetType.of(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_IllegalParameterException_on_string_is_not_known () {

        DiscountTargetType.of("hello");
    }
}
