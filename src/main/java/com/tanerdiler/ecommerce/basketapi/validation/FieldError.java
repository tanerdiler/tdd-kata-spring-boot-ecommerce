package com.tanerdiler.ecommerce.basketapi.validation;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class FieldError {

    private Class objectName;
    private String field;
    private String message;
    private String key;
}
