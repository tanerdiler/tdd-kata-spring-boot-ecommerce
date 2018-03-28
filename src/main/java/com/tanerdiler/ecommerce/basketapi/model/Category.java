package com.tanerdiler.ecommerce.basketapi.model;

import com.tanerdiler.ecommerce.basketapi.builder.CategoryBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Category
{
    @Positive
    private Integer id;
    @NotNull @NotBlank
    private String name;

    public static CategoryBuilder aNew()
    {
        return new CategoryBuilder();
    }
}
