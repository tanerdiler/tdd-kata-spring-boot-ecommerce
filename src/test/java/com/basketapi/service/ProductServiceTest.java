package com.basketapi.service;

import com.basketapi.config.BaseMockitoTest;
import com.basketapi.domain.model.Product;
import com.basketapi.domain.validation.BeanValidationException;
import com.basketapi.domain.validation.BeanValidator;
import com.basketapi.repository.ProductRepository;
import com.basketapi.service.dto.CategoryDTO;
import com.basketapi.service.dto.ProductDTO;
import com.basketapi.service.exception.EntityWithIdException;
import com.basketapi.service.exception.ResourceNotFoundException;
import com.basketapi.service.mapper.CategoryDTOMapper;
import com.basketapi.service.mapper.ProductDTOMapper;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductServiceTest extends BaseMockitoTest
{
    @InjectMocks
    private ProductService service;
    @Mock
    private ProductRepository repo;
    @Mock
    private CategoryService categoryService;

    @Spy
    private ProductDTOMapper mapper = new ProductDTOMapper(new CategoryDTOMapper(new
            BeanValidator
            ()), new BeanValidator
            ());

    @Test(expected = EntityWithIdException.class)
    public void
    should_throw_EntityWithIdException_while_saving_product_with_id()
            throws BeanValidationException
    {
        when(categoryService.checkIfExists(5)).thenReturn(true);

        ProductDTO dto = createDTO(1);
        dto.getCategory().setId(5);

        service.save(dto);
    }

    @Test
    public void should_delete_product()
            throws BeanValidationException
    {
        when(service.checkIfExists(1)).thenReturn(true);

        service.delete(1);

        verify(repo).deleteById(1);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void should_throw_ResourceNotFoundException_while_deleting_product()
            throws BeanValidationException
    {
        when(service.checkIfExists(1)).thenReturn(false);

        service.delete(1);
    }

    @Test
    public void should_save_product()
            throws BeanValidationException
    {
        ProductDTO dto = createDTO(null);
        dto.getCategory().setId(5);

        Product product = createEntity(1);
        product.getCategory().setId(5);

        ProductDTO dtoOfPersisted = createDTO(1);
        dtoOfPersisted.getCategory().setId(5);

        when(categoryService.checkIfExists(5)).thenReturn(true);

        when(repo.save(any(Product.class))).thenReturn(product);

        ProductDTO  persistedProductDTO = service.save(dto);

        assertThat(persistedProductDTO)
                .isEqualToComparingOnlyGivenFields
                        (dtoOfPersisted, "id",
                                "name","price");

        assertThat(persistedProductDTO.getCategory())
                .isEqualToComparingFieldByField
                        (dtoOfPersisted.getCategory());
    }


    @Test(expected = ResourceNotFoundException.class)
    public void
    should_thrown_ResourceNotFoundException_campaign_not_persisted_yet()
    {
        ProductDTO dto = createDTO(null);
        dto.getCategory().setId(5);

        Product product = createEntity(1);
        product.getCategory().setId(5);

        ProductDTO dtoOfPersisted = createDTO(1);
        dtoOfPersisted.getCategory().setId(5);

        when(categoryService.checkIfExists(5)).thenReturn(false);

        ProductDTO  persistedProductDTO = service.save(dto);

        assertThat(persistedProductDTO)
                .isEqualToComparingOnlyGivenFields
                        (dtoOfPersisted, "id",
                                "name","price");

        assertThat(persistedProductDTO.getCategory())
                .isEqualToComparingFieldByField
                        (dtoOfPersisted.getCategory());
    }

    @Test
    public void should_find_product()
            throws BeanValidationException {
        Product product = createEntity(1);
        ProductDTO dtoOfPersisted = createDTO(1);

        when(repo.findById(1)).thenReturn(Optional.of(product));

        ProductDTO persistedProductDTO = service.find(1);
        assertThat(persistedProductDTO).isEqualToComparingOnlyGivenFields
                (dtoOfPersisted, "id",
                        "name", "price");
    }

    @Test(expected = ResourceNotFoundException.class)
    public void should_throw_ResourceNotFoundException_when_product_not_found()
    {
        ProductDTO dtoOfPersisted = createDTO(1);

        when(repo.findById(1)).thenReturn(Optional.ofNullable(null));

        ProductDTO  persistedCategoryDTO = service.find(1);
        assertThat(persistedCategoryDTO).isEqualToComparingOnlyGivenFields
            (dtoOfPersisted, "id",
                    "name","price");
    }

    @Test(expected = ResourceNotFoundException.class)
    public void
    should_throw_ResourceNotFoundException_while_saving_if_category_not_exists()
            throws BeanValidationException
    {
        ProductDTO dto = createDTO(null);
        dto.getCategory().setId(6);

        Product product = createEntity(1);
        product.getCategory().setId(6);

        ProductDTO dtoOfPersisted = createDTO(1);
        dtoOfPersisted.getCategory().setId(6);

        when(categoryService.checkIfExists(6)).thenReturn(false);

        ProductDTO  persistedProductDTO = service.save(dto);

        assertThat(persistedProductDTO)
                .isEqualToComparingOnlyGivenFields
                        (dtoOfPersisted, "id",
                                "name","price");

        assertThat(persistedProductDTO.getCategory())
                .isEqualToComparingFieldByField
                        (dtoOfPersisted.getCategory());
    }

    @Test
    public void should_return_list_of_campaigns()
            throws BeanValidationException
    {
        ProductDTO dto = createDTO(null);
        Product product1 = createEntity(1);
        Product product2 = createEntity(2);
        Product product3 = createEntity(3);
        List<Product> categoies = Arrays.asList(product1, product2,
                product3);
        when(repo.findAll()).thenReturn(categoies);

        ProductDTO dtoOfPersistedProduct1 = createDTO(1);
        ProductDTO dtoOfPersistedProduct2 = createDTO(2);
        ProductDTO dtoOfPersistedProduct3 = createDTO(3);
        List<ProductDTO> dtoListOfPersistedCategories = Arrays.asList
                (dtoOfPersistedProduct1,
                        dtoOfPersistedProduct2,
                        dtoOfPersistedProduct3);


        List<ProductDTO>  listOfPersistedProductDTO = service
                .findAll();

        for(int i=0; i<listOfPersistedProductDTO.size(); i++)
        {
            assertThat(listOfPersistedProductDTO.get(i))
                    .isEqualToComparingOnlyGivenFields
                            (dtoListOfPersistedCategories.get(i), "id",
                    "name","price");

            assertThat(listOfPersistedProductDTO.get(i).getCategory())
                    .isEqualToComparingFieldByField
                            (dtoListOfPersistedCategories.get(i).getCategory());
        }
    }

    @Test(expected = BeanValidationException.class)
    public void
    should_throw_BeanValidationException_while_persisting_invalid_product()
            throws BeanValidationException
    {
        ProductDTO invalidDTO = createDTO(null);
        invalidDTO.setPrice(-100d);

        when(categoryService.checkIfExists(any())).thenReturn(true);

        service.save(invalidDTO);
    }

    private ProductDTO createDTO(Integer id)
    {
        CategoryDTO category = new CategoryDTO();
        category.setId(id);
        category.setName("Gömlek");

        ProductDTO product = new ProductDTO();
        product.setId(id);
        product.setName("Gömlek");
        product.setPrice(100d);
        product.setCategory(category);
        return product;
    }

    private Product createEntity(Integer id)
    {
        ProductDTO dto = createDTO(id);
        Product product = null;
        try {
            product = new ProductDTOMapper(new CategoryDTOMapper(new
                    BeanValidator()),new BeanValidator()).toEntity
                (dto);
        } catch (BeanValidationException e)
        {
            // DO NOTHING
        }
        return product;
    }
}
