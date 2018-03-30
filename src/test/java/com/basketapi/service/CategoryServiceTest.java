package com.basketapi.service;

import com.basketapi.config.BaseMockitoTest;
import com.basketapi.domain.model.Category;
import com.basketapi.domain.validation.BeanValidationException;
import com.basketapi.domain.validation.BeanValidator;
import com.basketapi.repository.CategoryRepository;
import com.basketapi.service.dto.CategoryDTO;
import com.basketapi.service.exception.EntityWithIdException;
import com.basketapi.service.exception.ResourceNotFoundException;
import com.basketapi.service.mapper.CategoryDTOMapper;
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

public class CategoryServiceTest extends BaseMockitoTest
{
    @InjectMocks
    private CategoryService service;
    @Mock
    private CategoryRepository repo;

    @Spy
    private CategoryDTOMapper mapper = new CategoryDTOMapper(new BeanValidator
            ());


    @Test
    public void should_delete_category()
            throws BeanValidationException
    {
        when(service.checkIfExists(1)).thenReturn(true);

        service.delete(1);

        verify(repo).deleteById(1);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void should_throw_ResourceNotFoundException_while_deleting_category()
            throws BeanValidationException
    {
        when(service.checkIfExists(1)).thenReturn(false);

        service.delete(1);
    }

    @Test
    public void should_save_category()
            throws BeanValidationException
    {
        CategoryDTO dto = createDTO(null);
        Category category = createEntity(1);
        CategoryDTO dtoOfPersisted = createDTO(1);

        when(repo.save(any(Category.class))).thenReturn(category);

        CategoryDTO  persistedCategoryDTO = service.save(dto);
        assertThat(persistedCategoryDTO).isEqualToComparingFieldByField(dtoOfPersisted);
    }

    @Test(expected = EntityWithIdException.class)
    public void
    should_throw_EntityWithIdException_while_saving_category_with_id()
            throws BeanValidationException
    {
        CategoryDTO dto = createDTO(1);

        service.save(dto);
    }

    @Test
    public void should_find_category()
    {
        Category category = createEntity(1);
        CategoryDTO dtoOfPersisted = createDTO(1);

        when(repo.findById(1)).thenReturn(Optional.of(category));

        CategoryDTO  persistedCategoryDTO = service.find(1);
        assertThat(persistedCategoryDTO).isEqualToComparingFieldByField(dtoOfPersisted);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void should_throw_ResourceNotFoundException_when_category_not_found()
    {
        CategoryDTO dtoOfPersisted = createDTO(1);


        when(repo.findById(1)).thenReturn(Optional.ofNullable(null));

        CategoryDTO  persistedCategoryDTO = service.find(1);
        assertThat(persistedCategoryDTO).isEqualToComparingFieldByField(dtoOfPersisted);
    }

    @Test
    public void should_return_list_of_campaigns()
            throws BeanValidationException
    {
        Category category1 = createEntity(1);
        Category category2 = createEntity(2);
        Category category3 = createEntity(3);
        List<Category> categoies = Arrays.asList(category1, category2,
                category3);
        when(repo.findAll()).thenReturn(categoies);

        CategoryDTO dtoOfPersistedCategory1 = createDTO(1);
        CategoryDTO dtoOfPersistedCategory2 = createDTO(2);
        CategoryDTO dtoOfPersistedCategory3 = createDTO(3);
        List<CategoryDTO> dtoListOfPersistedCategories = Arrays.asList
                (dtoOfPersistedCategory1,
                        dtoOfPersistedCategory2,
                        dtoOfPersistedCategory3);


        List<CategoryDTO>  listOfPersistedCategoryDTO = service
                .findAll();

        for(int i=0; i<listOfPersistedCategoryDTO.size(); i++)
        {
            assertThat(listOfPersistedCategoryDTO.get(i))
                    .isEqualToComparingFieldByField
                            (dtoListOfPersistedCategories.get(i));
        }
    }

    @Test(expected = BeanValidationException.class)
    public void
    should_throw_BeanValidationException_while_persisting_invalid_campaign()
            throws BeanValidationException
    {
        CategoryDTO invalidDTO = createDTO(null);
        invalidDTO.setName("");

        service.save(invalidDTO);
    }

    private CategoryDTO createDTO(Integer id)
    {
        CategoryDTO category = new CategoryDTO();
        category.setId(id);
        category.setName("Gömlek");
        return category;
    }

    private Category createEntity(Integer id)
    {
        CategoryDTO dto = createDTO(id);
        Category category = null;
        try {
            category = new CategoryDTOMapper(new BeanValidator()).toEntity
                (dto);
        } catch (BeanValidationException e)
        {
            // DO NOTHING
        }
        return category;
    }
}
