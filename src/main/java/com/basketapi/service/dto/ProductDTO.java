package com.basketapi.service.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter @Setter
public class ProductDTO
{
    @Positive
    private Integer id;
    @Positive @NotNull
    private Double price;
    @NotNull @NotBlank
    private String name;
    @NotNull @Valid
    private CategoryDTO category;
}
