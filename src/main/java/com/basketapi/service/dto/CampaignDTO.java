package com.basketapi.service.dto;

import com.basketapi.domain.model.DiscountTargetType;
import com.basketapi.domain.model.DiscountType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter @Setter
public class CampaignDTO
{
    @Positive
    private Integer id;
    @NotNull @NotBlank
    @Size(max = 100)
    private String name;
    @Positive @NotNull
    private Integer targetId;
    @NotNull
    private DiscountTargetType targetType;
    @Positive @NotNull @DecimalMax("100.0")
    private Double discountAmount;
    @Positive
    private Double discountLimit;
    @NotNull
    private DiscountType discountType;
}
