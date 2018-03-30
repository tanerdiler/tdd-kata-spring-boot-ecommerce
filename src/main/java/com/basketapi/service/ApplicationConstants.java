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

    public static final String FAILED_SAVING_CAMPAIGN = "Saving campaign operation failed";
    public static final String FAILED_SAVING_CATEGORY = "Saving category " +
            "operation failed";
    public static final String FAILED_DELETING_CAMPAIGN = "Deleting campaign operation failed";
    public static final String FAILED_DELETING_CATEGORY = "Deleting category " +
            "operation failed";
    public static final String FAILED_FINDING_CAMPAIGN = "Campaign not found";
    public static final String FAILED_FINDING_PRODUCT = "Product not found";
    public static final String FAILED_FINDING_CATEGORY = "Category not found";

    private ApplicationConstants()
    {
        // DO NOTHING
    }
}
