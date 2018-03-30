package com.basketapi.domain.model;

import com.basketapi.domain.builder.CampaignBuilder;
import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
@Table(name="campaigns")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter @Setter
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Positive
    private Integer id;
    @NotNull @NotBlank
    @Size(max = 100)
    private String name;
    @NotNull @Valid
    @OneToOne(cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Discount discount;
    @NotNull @Positive
    private Integer targetId;
    @NotNull
    @Enumerated(EnumType.STRING)
    private DiscountTargetType targetType;

    public static CampaignBuilder aNew() {
        return new CampaignBuilder();
    }

}
