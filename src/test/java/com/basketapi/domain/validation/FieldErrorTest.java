package com.basketapi.domain.validation;

import com.basketapi.domain.model.Product;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FieldErrorTest
{
    @Test
    public void should_return_string()
    {
        FieldError fieldError = new FieldError(Product.class, "name",
                "must not be blank", "error.required");
        assertThat(fieldError.toString()).isEqualTo("FieldError" +
                "(objectName=class com.basketapi.domain.model.Product, field=name, message=must not be blank, key=error.required)");
    }
}
