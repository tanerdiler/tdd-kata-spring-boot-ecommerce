package com.basketapi.domain.validation;

import org.springframework.stereotype.Component;

import javax.validation.*;
import java.util.Set;

@Component
public class BeanValidator
{
    private ValidatorFactory factory;

    public BeanValidator ()
    {
        Configuration<?> config = Validation.byDefaultProvider()
                .configure();
        factory = config.buildValidatorFactory();
    }

    public Validator get()
    {
        return factory.getValidator();
    }

    public <T extends Object> T validate(T object)
    {
        Set<ConstraintViolation<T>> violations = get().validate(object);
        if(!violations.isEmpty())
        {
            BeanValidationException ex = new BeanValidationException();
            violations
                    .stream()
                    .map( v ->
                            new FieldError(v.getRootBeanClass(),
                                    v.getPropertyPath().toString(),
                                    v.getMessage(), null))
                    .forEach(ex::add);
            throw ex;
        }
        return object;
    }

    public void close()
    {
        factory.close();
    }
}
