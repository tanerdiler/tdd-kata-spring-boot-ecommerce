package com.tanerdiler.ecommerce.basketapi.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BeanValidatorTest
{
    @AllArgsConstructor
    @Getter @Setter
    public static class BeanToValidate
    {
        @NotNull
        @Size(min=5, max=50)
        private String name;
        @Min(18) @Max(90)
        private int age;
    }

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

    @Test(expected = BeanValidationException.class)
    public void should_validate_bean_with_short_name() throws BeanValidationException {
        BeanToValidate invalidBean = new BeanToValidate("test", 45);
        validator.validate(invalidBean);
    }

    @Test(expected = BeanValidationException.class)
    public void should_validate_bean_with_invalid_age() throws BeanValidationException {
        BeanToValidate invalidBean = new BeanToValidate("Jhon Doe", 5);
        validator.validate(invalidBean);
    }

    @Test
    public void should_attach_invalid_field_details()
    {
        BeanToValidate invalidBean = new BeanToValidate("ttt", 5);
        List<FieldError> fieldErrors = null;
        try {
            validator.validate(invalidBean);
        }
        catch (BeanValidationException e)
        {
            fieldErrors = e.getFieldErrors();
        }
        assertThat(fieldErrors).isNotNull();
        assertThat(fieldErrors).hasSize(2);
        assertThat(fieldErrors).contains(new FieldError(BeanToValidate.class, "name", "size must be between 5 and 50", null));
        assertThat(fieldErrors).contains(new FieldError(BeanToValidate.class, "age", "must be greater than or equal to 18", null));
    }
}
