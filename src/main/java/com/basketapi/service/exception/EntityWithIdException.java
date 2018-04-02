package com.basketapi.service.exception;


import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static com.basketapi.service.ApplicationConstants.RESOURCE_WITH_ID_TYPE;


public class EntityWithIdException extends RestException
{
    public static final String EXCEPTION_KEY = "error.resource.withid";

    public EntityWithIdException(String defaultMessage, String
            entityName, Integer entityId)
    {
        this(RESOURCE_WITH_ID_TYPE, defaultMessage, entityName, entityId);
    }

    public EntityWithIdException(URI page, String defaultMessage, String
            entityName, Integer entityId) {
        super(entityId, entityName, page, defaultMessage);
    }

    public String getErrorKey()
    {
        return EXCEPTION_KEY;
    }
}
