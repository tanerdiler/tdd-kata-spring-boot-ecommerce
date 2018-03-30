package com.basketapi.service.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter @Setter
public class CategoryDTO
{
    @Positive
    private Integer id;
    @NotNull @NotBlank
    private String name;
}
