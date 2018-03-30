package com.basketapi.domain.model;

import com.basketapi.domain.builder.ProductBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table(name="products")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Product implements IProduct
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Positive
    private Integer id;
    @NotNull @NotBlank
    private String name;
    @Positive @NotNull
    private Double price;
    @NotNull @Valid
    @ManyToOne
    private Category category;

    public static ProductBuilder aNew() {
        return new ProductBuilder();
    }

    @Override
    @Transient
    public Double getOriginalPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {

        if(!(o instanceof IProduct))
        {
            return false;
        }

        IProduct product = (IProduct) o;

        return id != null ? id.equals(product.getId()) : product.getId() == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
