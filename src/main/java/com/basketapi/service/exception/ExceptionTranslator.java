package com.basketapi.service.exception;


import com.basketapi.domain.validation.BeanValidationException;
import com.basketapi.service.ApplicationConstants;
import com.basketapi.web.rest.util.HeaderUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.DefaultProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.validation.ConstraintViolationProblem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;

/**
 * Controller advice to translate the server side exceptions to client-friendly json structures.
 * The error response follows RFC7807 - Problem Details for HTTP APIs (https://tools.ietf.org/html/rfc7807)
 */
@ControllerAdvice
public class ExceptionTranslator implements ProblemHandling {

    public static final String ENTITY_ID = "entityId";
    public static final String ENTITY_NAME = "entityName";
    public static final String MESSAGE = "message";
    public static final String FIELD_ERRORS = "fieldErrors";

    /**
     * Post-process Problem payload to add the message key for front-end if needed
     */
    @Override
    public ResponseEntity<Problem> process(@Nullable ResponseEntity<Problem> entity, NativeWebRequest request) {
        if (entity == null || entity.getBody() == null) {
            return entity;
        }
        Problem problem = entity.getBody();
        if (!(problem instanceof ConstraintViolationProblem || problem instanceof DefaultProblem)) {
            return entity;
        }
        ProblemBuilder builder = Problem.builder()
                .withType(Problem.DEFAULT_TYPE.equals(problem.getType()) ? ApplicationConstants
                        .DEFAULT_TYPE : problem.getType())
                .withStatus(problem.getStatus())
                .withTitle(problem.getTitle())
                .with("path", request.getNativeRequest(HttpServletRequest.class).getRequestURI());

        if (problem instanceof ConstraintViolationProblem) {
            builder
                    .with("violations", ((ConstraintViolationProblem) problem).getViolations())
                    .with("message", ApplicationConstants.ERR_VALIDATION);
            return new ResponseEntity<>(builder.build(), entity.getHeaders(), entity.getStatusCode());
        } else {
            builder
                    .withCause(((DefaultProblem) problem).getCause())
                    .withDetail(problem.getDetail())
                    .withInstance(problem.getInstance());
            problem.getParameters().forEach(builder::with);
            if (!problem.getParameters().containsKey("message") && problem.getStatus() != null) {
                builder.with("message", "error.http." + problem.getStatus().getStatusCode());
            }
            return new ResponseEntity<>(builder.build(), entity.getHeaders(), entity.getStatusCode());
        }
    }

    @ExceptionHandler(EntityWithIdException.class)
    public ResponseEntity<Problem> handleMethodArgumentNotValid(EntityWithIdException ex, @Nonnull NativeWebRequest request) {
        Problem problem = Problem.builder()
                .withType(ApplicationConstants.RESOURCE_WITH_ID_TYPE)
                .withTitle("Resource not saved.")
                .withStatus(defaultConstraintViolationStatus())
                .with(MESSAGE, ApplicationConstants.ERR_VALIDATION)
                .withDetail("Resource with id is not permitted to be saved.")
                .build();
        return create(ex, problem, request, HeaderUtil.createFailureAlert(ex.getEntityName(), ex.getErrorKey(), ex.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Problem> handleMethodArgumentNotValid(ResourceNotFoundException ex,
                                                                @Nonnull NativeWebRequest request) {
        Problem problem = Problem.builder()
                .withType(ApplicationConstants.RESOURCE_NOT_FOUND_TYPE)
                .withTitle("Resource not found.")
                .withStatus(Status.NOT_FOUND)
                .with(MESSAGE, ex.getErrorKey())
                .with(ENTITY_ID, ex.getEntityId())
                .with(ENTITY_NAME, ex.getEntityName())
                .build();
        return create(ex, problem, request, HeaderUtil.createFailureAlert(ex.getEntityName(), ex.getErrorKey(), ex.getMessage()));
    }

    @ExceptionHandler(BeanValidationException.class)
    public ResponseEntity<Problem> handleMethodArgumentNotValid(BeanValidationException ex, @Nonnull NativeWebRequest request) {
        Problem problem = Problem.builder()
                .withType(ApplicationConstants.CONSTRAINT_VIOLATION_TYPE)
                .withTitle("Method argument not valid")
                .withStatus(defaultConstraintViolationStatus())
                .with(MESSAGE, ApplicationConstants.ERR_VALIDATION)
                .with(FIELD_ERRORS, ex.getFieldErrors())
                .build();
        return create(ex, problem, request);
    }


}
