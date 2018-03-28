package com.tanerdiler.ecommerce.basketapi.builder;

import com.tanerdiler.ecommerce.basketapi.validation.BeanValidationException;
import com.tanerdiler.ecommerce.basketapi.validation.BeanValidator;

public interface IValidatableBuilder<T> extends IBuilder<T>
{
    default T validateAndGet(BeanValidator validator) throws BeanValidationException
    {
        return validator.validate(get());
    }
}
