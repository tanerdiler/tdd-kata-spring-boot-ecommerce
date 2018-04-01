package com.basketapi.service.exception;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.basketapi.service.ApplicationConstants.*;
import static com.basketapi.service.exception.ResourceNotFoundException
        .EXCEPTION_KEY;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class ResourceNotFoundExceptionTest
{

    @Test
    public void should_hold_info_about_source_and_exception()
    {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("key", EXCEPTION_KEY);
        parameters.put("entityName", ENTITY_NAME_PRODUCT);
        parameters.put("entityId", 15);


        ResourceNotFoundException exception = new ResourceNotFoundException
            (FAILED_SAVING_CAMPAIGN,
                ENTITY_NAME_PRODUCT, 15);

        assertThat(exception.getEntityId()).isEqualTo(15);
        assertThat(exception.getEntityName()).isEqualTo(ENTITY_NAME_PRODUCT);
        assertThat(exception.getExceptionPage()).isEqualTo(RESOURCE_NOT_FOUND_TYPE);
        assertThat(exception.getDefaultMessage()).isEqualTo(FAILED_SAVING_CAMPAIGN);
        assertThat(exception.getErrorKey()).isEqualTo(EXCEPTION_KEY);
        assertThat(exception.getAlertParameters()).containsAllEntriesOf(parameters);
    }
}
