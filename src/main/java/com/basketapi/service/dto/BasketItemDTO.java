package com.basketapi.service.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
public class BasketItemDTO {
    @Positive
    private Integer id;
    @NotNull @NotBlank
    private String name;
    @Positive @NotNull
    private Double price;
    @Positive
    private Double discountedPrice;
    @Positive @NotNull
    private Integer categoryId;
}
