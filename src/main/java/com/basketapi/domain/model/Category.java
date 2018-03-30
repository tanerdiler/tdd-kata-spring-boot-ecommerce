package com.basketapi.domain.model;

import com.basketapi.domain.builder.CategoryBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table(name="categories")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Category
{
    @Positive
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull @NotBlank
    private String name;

    public static CategoryBuilder aNew()
    {
        return new CategoryBuilder();
    }
}
