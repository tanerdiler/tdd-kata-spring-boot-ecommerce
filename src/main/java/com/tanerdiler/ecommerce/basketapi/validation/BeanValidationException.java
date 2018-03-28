package com.tanerdiler.ecommerce.basketapi.validation;


import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class BeanValidationException extends Exception
{
    private final List<FieldError> fieldErrors = new ArrayList<>();

    public BeanValidationException add(FieldError error)
    {
        fieldErrors.add(error);
        return this;
    }

    public List<FieldError> getFieldErrors()
    {
        return unmodifiableList(fieldErrors);
    }
}
