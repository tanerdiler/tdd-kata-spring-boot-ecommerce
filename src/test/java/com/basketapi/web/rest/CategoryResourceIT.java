package com.basketapi.web.rest;


import com.basketapi.EcommerceBasketApiApplication;
import com.basketapi.TestUtil;
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

import java.util.List;
import java.util.Random;

import static com.basketapi.web.rest.TestUtil.APPLICATION_JSON_UTF8;
import static com.basketapi.web.rest.TestUtil.convertObjectToJsonBytes;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request
        .MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    public void should_create_category() throws Exception {
        //GIVEN
        categoryRepository.deleteAll();
        int sizeBeforeCreate = categoryRepository.findAll().size();
        String name = "IT-CATEGORY-1";

        //WHEN
        Category category = Category.aNew().withName(name).get();
        CategoryDTO categoryDTO = categoryMapper.toDTO(category);

        restMockMvc.perform(post("/api/v1/categories")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(categoryDTO)))
                .andExpect(status().isCreated());
        //THEN

        // Validate the Coop in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(sizeBeforeCreate + 1);

        Category persitedCategory = categoryList.get(categoryList.size() - 1);
        assertThat(persitedCategory.getName()).isEqualTo(name);
    }


    @Test
    @Transactional
    public void should_throw_badrequest_when_creating_category_with_id() throws Exception {
        //GIVEN
        int sizeBeforeCreate = categoryRepository.findAll().size();

        String categoryName = "IT-CATEGORY-1";

        Category categoryWithId = new Category();
        categoryWithId.setId(1);
        categoryWithId.setName(categoryName);

        //WHEN
        CategoryDTO categoryDTO = categoryMapper.toDTO(categoryWithId);
        restMockMvc.perform(post("/api/v1/categories")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(categoryDTO)))
                .andExpect(status().isBadRequest());
        //THEN
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(sizeBeforeCreate);
    }

    @Test
    @Transactional
    public void should_get_categories() throws Exception {
        //GIVEN
        categoryRepository.deleteAll();

        String categoryNamePrefix = "IT-CATEGORY-";
        Category categoryWithId = new Category();
        categoryWithId.setName(categoryNamePrefix + "1");


        Category categoryWithId2 = new Category();
        categoryWithId2.setName(categoryNamePrefix + "2");

        categoryRepository.save(categoryWithId);
        categoryRepository.save(categoryWithId2);

        //WHEN
        ResultActions resultActions = restMockMvc
                .perform(get("/api/v1/categories"));


        //THEN

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[*].id").value(
                        hasItem(categoryWithId.getId().intValue())))
                .andExpect(jsonPath("$.[*].id").value(
                        hasItem(categoryWithId2.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(
                        hasItem("IT-CATEGORY-" + 1)))
                .andExpect(jsonPath("$.[*].name").value(
                        hasItem("IT-CATEGORY-" + 2)));
    }


    @Test
    @Transactional
    public void should_return_category() throws Exception {
        //GIVEN
        String categoryNamePrefix = "IT-CATEGORY-";
        Category category = new Category();
        category.setName(categoryNamePrefix + "1");

        category = categoryRepository.save(category);

        //WHEN
        restMockMvc.perform(
                get("/api/v1/categories/" +
                        category.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name")
                        .value(equalTo("IT-CATEGORY-1")));

        //THEN
    }


    @Test
    @Transactional
    public void
    should_throw_badrequest_when_creating_category_with_invalid_name() throws
    Exception {

        //GIVEN
        Category category = new Category();
        category.setName("");

        CategoryDTO categoryDTO = categoryMapper.toDTO(category);
        //WHEN
        restMockMvc.perform(
                post("/api/v1/categories")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(convertObjectToJsonBytes(categoryDTO)))
                .andExpect(status().isBadRequest());
        //THEN
    }
}
