package com.tanerdiler.ecommerce.basketapi.builder;

import com.tanerdiler.ecommerce.basketapi.model.Category;
import com.tanerdiler.ecommerce.basketapi.model.Product;
import com.tanerdiler.ecommerce.basketapi.validation.BeanValidationException;
import com.tanerdiler.ecommerce.basketapi.validation.BeanValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductBuilderTest
{
    private BeanValidator validator;

    @Before
    public void init()
    {
        validator = new BeanValidator();
    }

    @After
    public void terminate()
    {
        validator.close();
    }

    @Test
    public void should_build_a_product_with_id()
    {
        Product product = Product.aNew().withId(10).get();

        assertThat(product.getId()).isEqualTo(10);
    }

    @Test
    public void should_build_a_product_with_name()
    {
        Product product = Product.aNew().withName("Mavi Tişörtler").get();

        assertThat(product.getName()).isEqualTo("Mavi Tişörtler");
    }

    @Test
    public void should_build_a_product_with_price()
    {
        Product product = Product.aNew().withPrice(100d).get();

        assertThat(product.getPrice()).isEqualTo(100d);
    }

    @Test
    public void should_relate_a_product_to_category()
    {
        Product product = Product.aNew()
                .withName("Mavi Tişörtler")
                .withPrice(100d)
                .relateTo(Category.aNew()
                        .withId(1)
                        .withName("Gömlek")
                        .get())
                .get();

        assertThat(product.getCategory().getId()).isEqualTo(1);
        assertThat(product.getCategory().getName()).isEqualTo("Gömlek");
    }

    @Test(expected = BeanValidationException.class)
    public void should_throw_BeanValidationException_with_blank_name() throws
            BeanValidationException
    {
        Product.aNew()
                .withName("")
                .withPrice(100d)
                .validateAndGet(validator);
    }

    @Test(expected = BeanValidationException.class)
    public void should_throw_BeanValidationException_with_negative_price()
            throws BeanValidationException
    {
        Product.aNew()
                .withName("Mavi Tişörtler")
                .withPrice(-100d)
                .validateAndGet(validator);
    }

    @Test(expected = BeanValidationException.class)
    public void should_throw_BeanValidationException_with_invalid_category()
            throws BeanValidationException
    {
        Product.aNew()
                .withName("Mavi Tişörtler")
                .withPrice(100d)
                .relateTo(Category.aNew()
                        .withId(1)
                        .get())
                .validateAndGet(validator);
    }
}
