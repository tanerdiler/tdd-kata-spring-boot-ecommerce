package com.basketapi.domain.builder;

import com.basketapi.domain.validation.BeanValidator;

public interface IValidatableBuilder<T> extends IBuilder<T>
{
    default T validateAndGet(BeanValidator validator)
    {
        return validator.validate(get());
    }
}
