package com.basketapi.domain.validation;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class FieldError implements Serializable
{
    private Class objectName;
    private String field;
    private String message;
    private String key;
}
