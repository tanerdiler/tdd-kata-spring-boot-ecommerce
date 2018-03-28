package com.tanerdiler.ecommerce.basketapi.model;

import com.tanerdiler.ecommerce.basketapi.builder.CampaignBuilder;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter @Setter
public class Campaign {

    private Integer id;
    @NotNull
    private String name;
    @NotNull
    @Valid
    private Discount discount;
    @NotNull
    private Integer targetId;
    @NotNull
    private DiscountTargetType targetType;

    public static CampaignBuilder aNew() {
        return new CampaignBuilder();
    }

}
