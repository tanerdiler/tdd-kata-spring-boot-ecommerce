package com.basketapi.web.rest;

import com.basketapi.service.CategoryService;
import com.basketapi.service.dto.CategoryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static com.basketapi.service.ApplicationConstants.ENTITY_NAME_CAMPAIGN;
import static com.basketapi.service.ApplicationConstants.ENTITY_NAME_CATEGORY;
import static com.basketapi.web.rest.util.HeaderUtil.createEntityCreationAlert;
import static com.basketapi.web.rest.util.HeaderUtil.createEntityDeletionAlert;
import static com.basketapi.web.rest.util.HeaderUtil.createEntityUpdateAlert;

@Controller
public class CategoryResource {

    private final Logger log = LoggerFactory.getLogger(CategoryResource.class);

    private final CategoryService categoryService;

    public CategoryResource(CategoryService categoryService)
    {
        this.categoryService = categoryService;
    }

    /**
     * DELETE  /api/v1/categories : Delete the specified Category.
     *
     * @param id the category identifier
     * @return the ResponseEntity with status 204 (NoContent),
     * or with status 404 (Not Fount) if the category has
     * not been found
     */
    @DeleteMapping("/api/v1/categories/{id}")
    public ResponseEntity deleteCategories(@PathVariable("id") Integer id)
    {
        log.debug("REST request to delete Category : {}",
                id);

        categoryService.delete(id);

        return ResponseEntity
                .noContent()
                .headers(createEntityDeletionAlert(ENTITY_NAME_CATEGORY, id.toString()))
                .build();
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
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO)
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
     * PUT  /api/v1/categories Updates an existing category.
     *
     * @param id the identifier of category to update
     * @param categoryDTO the categoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated categoryDTO,
     * or with status 400 (Bad Request) if the categoryDTO is not valid,
     * or with status 405 (Not Fount) if the category is not existed,
     * or with status 500 (Internal Server Error) if the categoryDTO couldn't be updated
     */
    @PutMapping("/api/v1/categories/{id}")
    public ResponseEntity updateCampaign(@PathVariable("id") Integer id,
                                         @RequestBody @Valid CategoryDTO categoryDTO)
    {
        log.debug("REST request to update Category : {}", categoryDTO);

        categoryService.update(id, categoryDTO);

        return ResponseEntity
                .noContent()
                .headers(createEntityUpdateAlert(ENTITY_NAME_CAMPAIGN, id.toString()))
                .build();
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
