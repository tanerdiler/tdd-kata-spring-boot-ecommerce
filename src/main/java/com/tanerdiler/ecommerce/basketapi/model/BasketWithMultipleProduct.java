package com.tanerdiler.ecommerce.basketapi.model;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

public class BasketWithMultipleProduct
{
    private Multiset<Product> products = HashMultiset.create();

    public int getCountOf(Product product)
    {
        return products.count(product);
    }

    public int getSize() {

        return products.entrySet().stream().mapToInt(e -> e.getCount()).sum();
    }

    public Integer removeAll(Product product)
    {
        int count = products.count(product);
        products.remove(product, count);
        return count;
    }

    public BasketWithMultipleProduct remove(Product product)
    {
        remove(product, 1);
        return this;
    }

    public BasketWithMultipleProduct remove(Product product, int count)
    {
        int productCount = products.count(product);
        if(productCount < count)
        {
            throw new IllegalStateException("BasketWithMultipleProduct doesn't have enough counf" +
                    " of product");
        }
        products.remove(product, count);
        return this;
    }

    public BasketWithMultipleProduct put(Product product)
    {
        put(product, 1);
        return this;
    }

    public BasketWithMultipleProduct put(Product product, int count)
    {
        products.add(product, count);

        return this;
    }

    public Map<Category, List<Multiset.Entry<Product>>> groupByCategory() {
        return products.entrySet().stream().collect(groupingBy(e -> e
                .getElement().getCategory()));
    }
}
