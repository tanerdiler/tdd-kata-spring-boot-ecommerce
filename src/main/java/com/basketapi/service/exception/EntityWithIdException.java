package com.basketapi.service.exception;


import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static com.basketapi.service.ApplicationConstants
        .RESOURCE_NOT_FOUND_TYPE;
import static com.basketapi.service.ApplicationConstants.RESOURCE_WITH_ID_TYPE;


public class EntityWithIdException extends RuntimeException
{
    public static final String EXCEPTION_KEY = "error.resource.withid";

    private final Integer entityId;
    private final URI exceptionPage;
    private final String defaultMessage;
    private final String entityName;

    public EntityWithIdException(String defaultMessage, String
            entityName, Integer entityId)
    {
        this(RESOURCE_WITH_ID_TYPE, defaultMessage, entityName, entityId);
    }

    public EntityWithIdException(URI page, String defaultMessage, String
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

    public String getKey()
    {
        return EXCEPTION_KEY;
    }

    public Map<String, Object> getAlertParameters()
    {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("key", getKey());
        parameters.put("entityName", entityName);
        parameters.put("entityId", entityId);
        return parameters;
    }
}
