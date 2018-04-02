package com.basketapi.service;

import java.net.URI;

public class ApplicationConstants
{
    public static final String APPLICATION_NAME = "basketapi";

    public static final String ERR_CONCURRENCY_FAILURE = "error" +
            ".concurrencyFailure";
    public static final String ERR_VALIDATION = "error.validation";
    public static final String PROBLEM_BASE_URL = "http://wiki.basketapi.com/problem";
    public static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "/problem-with-message");
    public static final URI CONSTRAINT_VIOLATION_TYPE = URI.create(PROBLEM_BASE_URL + "/constraint-violation");
    public static final URI PARAMETERIZED_TYPE = URI.create(PROBLEM_BASE_URL + "/parameterized");
    public static final URI INVALID_PASSWORD_TYPE = URI.create(PROBLEM_BASE_URL + "/invalid-password");
    public static final URI EMAIL_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/email-already-used");
    public static final URI LOGIN_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/login-already-used");
    public static final URI EMAIL_NOT_FOUND_TYPE = URI.create(PROBLEM_BASE_URL + "/email-not-found");
    public static final URI RESOURCE_NOT_FOUND_TYPE = URI.create(PROBLEM_BASE_URL + "/resource-not-found");
    public static final URI RESOURCE_WITH_ID_TYPE = URI.create
            (PROBLEM_BASE_URL +
            "/resource-with-id");

    public static final String ENTITY_NAME_CAMPAIGN = "campaign";
    public static final String ENTITY_NAME_PRODUCT = "product";
    public static final String ENTITY_NAME_CATEGORY = "category";

    public static final String SAVING = "Saving";
    public static final String FAILED_SAVING_PRODUCT = getFailedOperationMessage(SAVING, ENTITY_NAME_PRODUCT);
    public static final String FAILED_SAVING_CAMPAIGN = getFailedOperationMessage(SAVING, ENTITY_NAME_CAMPAIGN);
    public static final String FAILED_SAVING_CATEGORY = getFailedOperationMessage(SAVING, ENTITY_NAME_CATEGORY);

    public static final String DELETING = "Deleting";
    public static final String FAILED_DELETING_CAMPAIGN = getFailedOperationMessage(DELETING, ENTITY_NAME_CAMPAIGN);
    public static final String FAILED_DELETING_CATEGORY = getFailedOperationMessage(DELETING, ENTITY_NAME_CATEGORY);

    public static final String FAILED_FINDING_CAMPAIGN = "Campaign not found";
    public static final String FAILED_FINDING_PRODUCT = "Product not found";
    public static final String FAILED_FINDING_CATEGORY = "Category not found";

    public static final String UPDATING = "Updating";
    public static final String FAILED_UPDATING_CAMPAIGN = getFailedOperationMessage(UPDATING, ENTITY_NAME_CAMPAIGN);
    public static final String FAILED_UPDATING_CATEGORY = getFailedOperationMessage(UPDATING, ENTITY_NAME_CATEGORY);
    public static final String FAILED_UPDATING_PRODUCT = getFailedOperationMessage(UPDATING, ENTITY_NAME_PRODUCT);

    private static final String getFailedOperationMessage(String operation, String entity)
    {
        return String.format("%s %s operation failed", operation, entity);
    }

    private ApplicationConstants()
    {
        // DO NOTHING
    }
}
