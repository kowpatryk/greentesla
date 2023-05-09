package com.maracuya.app.atmservice;

import com.maracuya.app.common.BaseControllerTest;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.maracuya.app.common.Resources.loadResourceAsString;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.equalTo;

class AtmOrderingControllerTest extends BaseControllerTest {

    @Test
    void shouldCalculateOrderForTheFirstExample() {
        shouldCalculateOrder(
            "example_1_request",
            expectAtmDetails(0, 1, 1),
            expectAtmDetails(1, 2, 1),
            expectAtmDetails(2, 3, 2),
            expectAtmDetails(3, 3, 1),
            expectAtmDetails(4, 4, 1),
            expectAtmDetails(5, 5, 1),
            expectAtmDetails(6, 5, 2)
        );
    }

    @Test
    void shouldCalculateOrderForTheSecondExample() {
        shouldCalculateOrder(
            "example_2_request",
            expectAtmDetails(0, 1, 2),
            expectAtmDetails(1, 1, 1),
            expectAtmDetails(2, 2, 3),
            expectAtmDetails(3, 2, 1),
            expectAtmDetails(4, 3, 1),
            expectAtmDetails(5, 3, 2),
            expectAtmDetails(6, 3, 4),
            expectAtmDetails(7, 4, 5),
            expectAtmDetails(8, 5, 2),
            expectAtmDetails(9, 5, 1)
        );
    }

    private void shouldCalculateOrder(
        String requestFileName,
        ResponseSpecification... expectedAtmDetailsSpecs
    ) {
        String requestFilePath = String.format("atmservice/%s.json", requestFileName);

        // @formatter:off
        given()
            .contentType(JSON)
            .body(loadResourceAsString(requestFilePath))
        .when()
            .post("/atms/calculateOrder")
        .then()
            .statusCode(200)
            .spec(expectedResponseSpec(expectedAtmDetailsSpecs));
        // @formatter:on
    }

    private ResponseSpecification expectedResponseSpec(ResponseSpecification... atmDetailsSpecs) {
        ResponseSpecBuilder specBuilder = new ResponseSpecBuilder()
            .expectBody("$.size()", equalTo(atmDetailsSpecs.length));
        Arrays.stream(atmDetailsSpecs).forEach(specBuilder::addResponseSpecification);
        return specBuilder.build();
    }

    private ResponseSpecification expectAtmDetails(int index, int expectedRegion, int expectedAtmId) {
        return new ResponseSpecBuilder()
            .expectBody(String.format("[%d].region", index), equalTo(expectedRegion))
            .expectBody(String.format("[%d].atmId", index), equalTo(expectedAtmId))
            .build();
    }

    @Test
    void shouldReturnBadRequestForInvalidPayload() {
        shouldReturnBadRequest(1, "NON-TRIVIAL", 2);
        shouldReturnBadRequest(-1, "STANDARD", 2);
        shouldReturnBadRequest(10000, "STANDARD", 2);
        shouldReturnBadRequest(1, "STANDARD", -1);
        shouldReturnBadRequest(1, "STANDARD", 10000);
    }

    private void shouldReturnBadRequest(int region, String requestType, int atmId) {
        // @formatter:off
        given()
            .contentType(JSON)
            .body(requestBodyWithSingleTask(region, requestType, atmId))
        .when()
            .post("/atms/calculateOrder")
        .then()
            .statusCode(400);
        // @formatter:on
    }

    private String requestBodyWithSingleTask(int region, String requestType, int atmId) {
        return String.format("""
            [
                {
                    "region": %d,
                    "requestType": "%s",
                    "atmId": %d
                }
            ]
            """, region, requestType, atmId);
    }
}