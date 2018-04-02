package com.basketapi.web.rest;


import com.basketapi.BeanUtil;
import com.basketapi.EcommerceBasketApiApplication;
import com.basketapi.domain.model.Category;
import com.basketapi.repository.CategoryRepository;
import com.basketapi.service.CategoryService;
import com.basketapi.service.dto.CategoryDTO;
import com.basketapi.service.exception.ExceptionTranslator;
import com.basketapi.service.mapper.CategoryDTOMapper;
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

import javax.persistence.EntityManager;
import java.util.Random;
import java.util.UUID;

import static com.basketapi.web.rest.TestUtil.APPLICATION_JSON_UTF8;
import static com.basketapi.web.rest.TestUtil.convertObjectToJsonBytes;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= EcommerceBasketApiApplication.class, webEnvironment = SpringBootTest
        .WebEnvironment.RANDOM_PORT)
public class CategoryResourceIT
{
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryDTOMapper categoryMapper;
    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private BeanUtil beanUtil;

    private MockMvc restMockMvc;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
        final CategoryResource categoryResource = new CategoryResource
                (categoryService);
        this.restMockMvc = MockMvcBuilders.standaloneSetup(categoryResource)
                .setControllerAdvice(exceptionTranslator)
                .build();
    }

    @Test
    @Transactional
    public void should_delete_category() throws Exception
    {
        String name = "IT-CATEGORY-"+ UUID.randomUUID();

        Category category = BeanUtil.createRandomCategoryAndSave
                (categoryRepository);

        category = categoryRepository.save(category);

        restMockMvc.perform(delete("/api/v1/categories/"+category.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @Transactional
    public void should_return_400_when_campaign_to_delete_is_not_found() throws
            Exception
    {
        restMockMvc.perform(delete("/api/v1/categories/"+(Integer.MAX_VALUE-new
                Random().nextInt())))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void should_update_category() throws Exception
    {
        //GIVEN
        String name = "IT-CATEGORY-"+ UUID.randomUUID();

        Category category = BeanUtil.createRandomCategoryAndSave
                (categoryRepository);

        CategoryDTO dto = categoryMapper.toDTO(category);
        dto.setName(name+"-UPDATED");

        //WHEN
        restMockMvc.perform(put("/api/v1/categories/"+category.getId())
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(dto)))
                .andExpect(status().isNoContent());

        //THEN
        Category updated = categoryRepository.findById(category.getId()).get();
        assertThat(updated.getName()).isEqualTo(name+"-UPDATED");
    }

    @Test
    @Transactional
    public void should_return_400_when_category_to_update_is_not_found() throws Exception
    {
        //GIVEN
        String name = "IT-CATEGORY-"+ UUID.randomUUID();

        Category category = BeanUtil.createRandomCategory();
        category.setId(Integer.MAX_VALUE);

        CategoryDTO dto = categoryMapper.toDTO(category);
        dto.setName(name+"-UPDATED");

        //WHEN
        ResultActions result = restMockMvc.perform(put("/api/v1/categories/"+category.getId())
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(dto)));

        //THEN
        result.andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void should_get_categories() throws Exception
    {
        beanUtil.deleteAll();

        //GIVEN
        Category category1 = BeanUtil.createRandomCategory();
        Category category2 = BeanUtil.createRandomCategory();

        category1 = categoryRepository.save(category1);
        category2 = categoryRepository.save(category2);

        //WHEN
        ResultActions resultActions = restMockMvc
                .perform(get("/api/v1/categories"));


        //THEN
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[*].id").value(
                        hasItem(category1.getId().intValue())))
                .andExpect(jsonPath("$.[*].id").value(
                        hasItem(category2.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(
                        hasItem(category1.getName())))
                .andExpect(jsonPath("$.[*].name").value(
                        hasItem(category2.getName())));
    }


    @Test
    @Transactional
    public void should_return_category() throws Exception {
        //GIVEN
        Category category = BeanUtil.createRandomCategory();

        category = categoryRepository.save(category);

        //WHEN
        ResultActions result = restMockMvc.perform(
                get("/api/v1/categories/" +
                        category.getId()));

        //THEN
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.name")
                        .value(equalTo(category.getName())));
    }


    @Test
    @Transactional
    public void
    should_return_400_when_creating_category_with_invalid_name() throws
    Exception {

        //GIVEN
        Category category = new Category();
        category.setName("");
        CategoryDTO categoryDTO = categoryMapper.toDTO(category);

        //WHEN
        ResultActions result = restMockMvc.perform(
                post("/api/v1/categories")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(convertObjectToJsonBytes(categoryDTO)));

        //THEN
        result.andExpect(status().isBadRequest());
    }
}
