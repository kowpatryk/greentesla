package com.maracuya.app.transactions;

import com.maracuya.app.common.BaseControllerTest;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static com.maracuya.app.common.ErrorResponseSpecifications.expectDetailedValidationError;
import static com.maracuya.app.common.Resources.loadResourceAsString;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.equalTo;

class TransactionsControllerTest extends BaseControllerTest {

    @Test
    void shouldCalculateReportForTransactions() {
        // @formatter:off
        given()
            .contentType(JSON)
            .body(loadResourceAsString("transactions/example_request.json"))
        .when()
            .post("/transactions/report")
        .then()
            .statusCode(200)
            .spec(expectedTransactionsReport());
        // @formatter:on
    }

    private ResponseSpecification expectedTransactionsReport() {
        return new ResponseSpecBuilder()
            .expectBody("$.size()", equalTo(4))
            .expectBody("[0].account", equalTo("06105023389842834748547303"))
            .expectBody("[0].debitCount", equalTo(0))
            .expectBody("[0].creditCount", equalTo(1))
            .expectBody("[0].balance", equalTo(10.9F))
            .expectBody("[1].account", equalTo("31074318698137062235845814"))
            .expectBody("[1].debitCount", equalTo(1))
            .expectBody("[1].creditCount", equalTo(0))
            .expectBody("[1].balance", equalTo(-200.9F))
            .expectBody("[2].account", equalTo("32309111922661937852684864"))
            .expectBody("[2].debitCount", equalTo(1))
            .expectBody("[2].creditCount", equalTo(1))
            .expectBody("[2].balance", equalTo(39.2F))
            .expectBody("[3].account", equalTo("66105036543749403346524547"))
            .expectBody("[3].debitCount", equalTo(1))
            .expectBody("[3].creditCount", equalTo(1))
            .expectBody("[3].balance", equalTo(150.8F))
            .build();
    }

    @Test
    void shouldReturnBadRequestForInvalidPayload() {
        shouldReturnBadRequest(
            singletonList(new Transaction(
                "123456789",
                "31074318698137062235845814",
                100
            )),
            "debitAccount",
            "size must be exactly 26"
        );

        shouldReturnBadRequest(
            singletonList(new Transaction(
                "31074318698137062235845814",
                "123456789",
                100
            )),
            "creditAccount",
            "size must be exactly 26"
        );

        shouldReturnBadRequest(
            singletonList(new Transaction(
                "31074318698137062235845814",
                "11074318698137062235845814",
                -10.0
            )),
            "amount",
            "must be greater than 0"
        );

        shouldReturnBadRequest(
            singletonList(new Transaction(
                "31074318698137062235845814",
                "31074318698137062235845814",
                10.0
            )),
            "",
            "Credit and debit accounts must be different"
        );

        shouldReturnBadRequest(
            tooManyTransactions(),
            "transactions",
            "size must be between 0 and 100000"
        );
    }

    private List<Transaction> tooManyTransactions() {
        return IntStream.rangeClosed(0, 100001)
            .mapToObj(idx ->
                new Transaction(
                    "31074318698137062235845814",
                    "11234418698137062235845999",
                    1.0
                ))
            .toList();
    }

    private void shouldReturnBadRequest(List<Transaction> transactions, String field, String errorMessage) {
        // @formatter:off
        given()
            .contentType(JSON)
            .body(transactions)
        .when()
            .post("/transactions/report")
        .then()
            .statusCode(400)
            .spec(expectDetailedValidationError(field, errorMessage));
        // @formatter:on
    }
}
