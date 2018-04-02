package com.basketapi.service.exception;

import java.net.URI;

public abstract class RestException extends RuntimeException
{
    private Integer entityId;
    private URI exceptionPage;
    private String defaultMessage;
    private String entityName;

    public RestException(Integer entityId, String entityName, URI page, String defaultMessage)
    {
        this.entityId = entityId;
        this.entityName = entityName;
        this.exceptionPage = page;
        this.defaultMessage = defaultMessage;
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


}
