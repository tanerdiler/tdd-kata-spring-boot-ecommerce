package com.basketapi.web.rest;

import com.basketapi.service.ProductService;
import com.basketapi.service.dto.ProductDTO;
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
public class ProductResource {

    private final Logger log = LoggerFactory.getLogger(ProductResource.class);

    private final ProductService productService;

    public ProductResource(ProductService productService)
    {
        this.productService = productService;
    }

    /**
     * POST  /api/v1/products : Create a new product.
     *
     * @param productDTO the productDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new productDTO, or with status 400 (Bad Request) if the product has
     * already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/api/v1/products")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO
                                                              productDTO)
            throws URISyntaxException
    {
        log.debug("REST request to save Product : {}",
                productDTO);

        ProductDTO result = productService.save(productDTO);

        return ResponseEntity
                .created(new URI("/api/v1/products/"+result.getId()))
                .headers(createEntityCreationAlert(
                        ENTITY_NAME_CATEGORY,
                        result.getId().toString()))
                .body(result);
    }

    /**
     * GET  /api/v1/products : get all products.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the
     * products, or with status 404 (Not Found)
     */
    @GetMapping("/api/v1/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts()
    {
        return ResponseEntity.ok(productService.findAll());
    }

    /**
     * GET  /api/v1/products : get the "id" product.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the
     * product, or with status 404 (Not Found)
     */
    @GetMapping("/api/v1/products/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Integer id)
    {
        return ResponseEntity.ok(productService.find(id));
    }
}
