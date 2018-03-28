package com.tanerdiler.ecommerce.basketapi.model;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Sets;

import java.util.*;

import static java.util.stream.Collectors.groupingBy;

public class Basket
{
    private Set<IProduct> products = new HashSet<>();
    private Double totalPrice;


    public int getSize() {

        return products.size();
    }

    public Basket remove(IProduct product)
    {
        products.remove(product);
        return this;
    }

    public Basket add(IProduct product)
    {
        products.add(product);
        return this;
    }

    public Map<Category, List<IProduct>> groupByCategory()
    {
        return products.stream().collect(groupingBy(IProduct::getCategory));
    }

    public Double getTotalPrice()
    {
        return products.stream().map(p -> p.getPrice()).mapToDouble
                (Double::doubleValue).sum();
    }

    public Set<IProduct> getProducts()
    {
        return Sets.newHashSet(products);
    }
}
