package com.basketapi.web.rest;


import com.basketapi.BeanUtil;
import com.basketapi.EcommerceBasketApiApplication;
import com.basketapi.domain.model.Category;
import com.basketapi.domain.model.Product;
import com.basketapi.repository.CampaignRepository;
import com.basketapi.repository.CategoryRepository;
import com.basketapi.repository.ProductRepository;
import com.basketapi.service.ProductService;
import com.basketapi.service.dto.ProductDTO;
import com.basketapi.service.exception.ExceptionTranslator;
import com.basketapi.service.mapper.ProductDTOMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

import static com.basketapi.web.rest.TestUtil.APPLICATION_JSON_UTF8;
import static com.basketapi.web.rest.TestUtil.convertObjectToJsonBytes;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request
        .MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request
        .MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result
        .MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result
        .MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes= EcommerceBasketApiApplication.class, webEnvironment = SpringBootTest
        .WebEnvironment.RANDOM_PORT)
public class ProductResourceIT
{
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CampaignRepository campaignRepository;
    @Autowired
    private ProductDTOMapper productMapper;
    @Autowired
    private ExceptionTranslator exceptionTranslator;
    @Autowired
    private BeanUtil beanUtil;

    private MockMvc restMockMvc;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
        final ProductResource productResource = new ProductResource
                (productService);
        this.restMockMvc = MockMvcBuilders.standaloneSetup(productResource)
                .setControllerAdvice(exceptionTranslator)
                .build();
    }

    @Test
    @Transactional
    public void should_create_product() throws Exception
    {
        //GIVEN

        //WHEN
        Category category = BeanUtil.createRandomCategoryAndSave
                (categoryRepository);

        Product product = BeanUtil.createRandomProduct(category);

        ProductDTO productDTO = productMapper.toDTO(product);

        ResultActions result = restMockMvc.perform(post("/api/v1/products")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(productDTO)));
        //THEN
        result.andExpect(status().isCreated())
            .andExpect(jsonPath("$.name")
                    .value(equalTo(product.getName())))
            .andExpect(jsonPath("$.price")
                    .value(equalTo(100d)))
            .andExpect(jsonPath("$.category.id")
                    .value(equalTo(category.getId())))
            .andExpect(jsonPath("$.category.name")
                    .value(equalTo(category.getName())));
    }


    @Test
    @Transactional
    public void should_return_400_when_creating_product_with_id() throws
            Exception {
        //GIVEN
        Category category = BeanUtil.createRandomCategoryAndSave
                (categoryRepository);

        Product product = BeanUtil.createRandomProduct(category);
        product.setId(new Random().nextInt());

        ProductDTO productDTO = productMapper.toDTO(product);

        //WHEN
        ResultActions result = restMockMvc.perform(post("/api/v1/products")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(productDTO)));
        //THEN
        result.andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void should_get_products() throws Exception {

        productRepository.deleteAll();

        //GIVEN
        Category category1 = BeanUtil.createRandomCategoryAndSave(categoryRepository);
        Category category2 = BeanUtil.createRandomCategoryAndSave(categoryRepository);

        Product product1 = BeanUtil.createRandomProductAndSave
                (category1, productRepository);
        Product product2 = BeanUtil.createRandomProductAndSave
                (category2, productRepository);

        //WHEN
        ResultActions resultActions = restMockMvc
                .perform(get("/api/v1/products"));

        //THEN
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[*].id").value(
                        hasItem(product1.getId().intValue())))
                .andExpect(jsonPath("$.[*].id").value(
                        hasItem(product2.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(
                        hasItem(product1.getName())))
                .andExpect(jsonPath("$.[*].name").value(
                        hasItem(product2.getName())));
    }


    @Test
    @Transactional
    public void should_return_product() throws Exception
    {
        //GIVEN
        Category category1 = categoryRepository.save(BeanUtil.category1);
        Product product = BeanUtil.getBlueShirt();
        product.setCategory(category1);
        product.setId(null);

        product = productRepository.save(product);

        //WHEN
        ResultActions result = restMockMvc.perform(
                get("/api/v1/products/" +
                        product.getId()));

        //THEN
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.name")
                        .value(equalTo("Mavi Gömlek")));
    }

    @Test
    @Transactional
    public void
    should_return_404_for_nonexisting_product()
            throws Exception
    {
        //GIVEN
        Category category1 = BeanUtil.createRandomCategoryAndSave(categoryRepository);
        Product product = BeanUtil.getBlueShirt();
        product.setCategory(category1);
        product.setId(new Random().nextInt());

        //WHEN
        ResultActions result = restMockMvc.perform(
                get("/api/v1/products/" +
                        product.getId()));

        //THEN
        result.andExpect(status().isNotFound());
    }


    @Test
    @Transactional
    public void
    should_return_400_when_creating_product_with_invalid_name() throws
    Exception {

        //GIVEN
        Category category = BeanUtil.createRandomCategoryAndSave
                (categoryRepository);
        Product product = BeanUtil.getBlueShirt();
        product.setCategory(category);
        product.setId(null);
        product.setName("");

        ProductDTO productDTO = productMapper.toDTO(product);

        //WHEN
        ResultActions result = restMockMvc.perform(
                post("/api/v1/products")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(convertObjectToJsonBytes(productDTO)));

        //THEN
        result.andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void
    should_return_400_when_creating_product_with_negative_price() throws
            Exception {

        //GIVEN
        Category category = BeanUtil.createRandomCategoryAndSave(categoryRepository);
        Product product = BeanUtil.getBlueShirt();
        product.setCategory(category);
        product.setId(null);
        product.setPrice(-100d);

        ProductDTO productDTO = productMapper.toDTO(product);

        //WHEN
        ResultActions result = restMockMvc.perform(
                post("/api/v1/products")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(convertObjectToJsonBytes(productDTO)));
        //THEN
        result.andExpect(status().isBadRequest());
    }
}
