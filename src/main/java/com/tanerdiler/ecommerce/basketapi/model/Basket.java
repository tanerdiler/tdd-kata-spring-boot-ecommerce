package com.tanerdiler.ecommerce.basketapi.model;

import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.groupingBy;

public class Basket
{
    private Set<IProduct> products = new HashSet<>();

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
        return products.stream().map(IProduct::getPrice).mapToDouble
                (Double::doubleValue).sum();
    }

    public Set<IProduct> getProducts()
    {
        return Sets.newHashSet(products);
    }
}
