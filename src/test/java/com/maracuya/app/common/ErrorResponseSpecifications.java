package com.maracuya.app.common;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class ErrorResponseSpecifications {

    public static ResponseSpecification expectSimpleValidationError(String message) {
        return new ResponseSpecBuilder()
            .expectStatusCode(400)
            .expectContentType(APPLICATION_JSON_VALUE)
            .expectBody("message", equalTo(message))
            .build();
    }

    public static ResponseSpecification expectDetailedValidationError(String field, String message) {
        return new ResponseSpecBuilder()
            .expectStatusCode(400)
            .expectContentType(APPLICATION_JSON_VALUE)
            .expectBody("message", equalTo("Validation failed"))
            .expectBody("errors.size()", equalTo(1))
            .expectBody("errors[0].field", equalTo(field))
            .expectBody("errors[0].message", equalTo(message))
            .build();
    }
}
