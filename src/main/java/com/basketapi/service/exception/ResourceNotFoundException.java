package com.basketapi.service.exception;


import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static com.basketapi.service.ApplicationConstants
        .RESOURCE_NOT_FOUND_TYPE;


public class ResourceNotFoundException extends RuntimeException
{
    public static final String EXCEPTION_KEY = "error.resource.notfound";

    private final Integer entityId;
    private final URI exceptionPage;
    private final String defaultMessage;
    private final String entityName;

    public ResourceNotFoundException(String defaultMessage, String
            entityName, Integer entityId)
    {
        this(RESOURCE_NOT_FOUND_TYPE, defaultMessage, entityName, entityId);
    }

    public ResourceNotFoundException(URI page, String defaultMessage, String
            entityName, Integer entityId)
    {
        this.exceptionPage = page;
        this.defaultMessage = defaultMessage;
        this.entityName = entityName;
        this.entityId = entityId;
    }

    public Integer getEntityId(){
        return entityId;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public URI getExceptionPage() {
        return exceptionPage;
    }

    public String getErrorKey()
    {
        return EXCEPTION_KEY;
    }

    public Map<String, Object> getAlertParameters()
    {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("key", getErrorKey());
        parameters.put("entityName", entityName);
        parameters.put("entityId", entityId);
        return parameters;
    }
}
