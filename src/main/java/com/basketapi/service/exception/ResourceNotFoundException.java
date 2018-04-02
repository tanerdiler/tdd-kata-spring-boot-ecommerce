package com.basketapi.service.exception;


import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static com.basketapi.service.ApplicationConstants.RESOURCE_NOT_FOUND_TYPE;


public class ResourceNotFoundException extends RestException
{
    public static final String EXCEPTION_KEY = "error.resource.notfound";

    public ResourceNotFoundException(String defaultMessage, String
            entityName, Integer entityId)
    {
        this(RESOURCE_NOT_FOUND_TYPE, defaultMessage, entityName, entityId);
    }

    public ResourceNotFoundException(URI page, String defaultMessage, String
            entityName, Integer entityId)
    {
        super(entityId, entityName, page, defaultMessage);
    }



    public String getErrorKey()
    {
        return EXCEPTION_KEY;
    }

    public Map<String, Object> getAlertParameters()
    {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("key", getErrorKey());
        parameters.put("entityName", getEntityName());
        parameters.put("entityId", getEntityId());
        return parameters;
    }
}
