package com.basketapi.web.rest;

import com.basketapi.service.CategoryService;
import com.basketapi.service.dto.CategoryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static com.basketapi.service.ApplicationConstants.ENTITY_NAME_CATEGORY;
import static com.basketapi.web.rest.util.HeaderUtil.createEntityCreationAlert;

@Controller
public class CategoryResource {

    private final Logger log = LoggerFactory.getLogger(CategoryResource.class);

    private final CategoryService categoryService;

    public CategoryResource(CategoryService categoryService)
    {
        this.categoryService = categoryService;
    }

    /**
     * POST  /api/v1/categories : Create a new category.
     *
     * @param categoryDTO the categoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new categoryDTO, or with status 400 (Bad Request) if the category has
     * already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/api/v1/categories")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO
                                                              categoryDTO)
            throws URISyntaxException
    {
        log.debug("REST request to save Category : {}",
                categoryDTO);

        CategoryDTO result = categoryService.save(categoryDTO);

        return ResponseEntity
                .created(new URI("/api/v1/categories/"+result.getId()))
                .headers(createEntityCreationAlert(
                        ENTITY_NAME_CATEGORY,
                        result.getId().toString()))
                .body(result);
    }

    /**
     * GET  /api/v1/categories : get all categories.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the
     * categories, or with status 404 (Not Found)
     */
    @GetMapping("/api/v1/categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories()
    {
        return ResponseEntity.ok(categoryService.findAll());
    }

    /**
     * GET  /api/v1/categories : get the "id" category.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the
     * category, or with status 404 (Not Found)
     */
    @GetMapping("/api/v1/categories/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Integer id)
    {
        return ResponseEntity.ok(categoryService.find(id));
    }
}
