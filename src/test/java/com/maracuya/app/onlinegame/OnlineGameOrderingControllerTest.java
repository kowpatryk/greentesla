package com.maracuya.app.onlinegame;

import com.maracuya.app.common.BaseControllerTest;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Test;

import static com.maracuya.app.common.Resources.loadResourceAsString;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.equalTo;

class OnlineGameOrderingControllerTest extends BaseControllerTest {

    @Test
    void shouldCalculateEnteringOrderForGame() {
        // @formatter:off
        given()
            .contentType(JSON)
            .body(loadResourceAsString("onlinegame/example_request.json"))
        .when()
            .post("/onlinegame/calculate")
        .then()
            .statusCode(200)
            .spec(expectedGameOrder());
        // @formatter:on
    }

    private ResponseSpecification expectedGameOrder() {
        return new ResponseSpecBuilder()
            .expectBody("$.size()", equalTo(5))
            .expectBody("[0][0].numberOfPlayers", equalTo(2))
            .expectBody("[0][0].points", equalTo(70))
            .expectBody("[0][1].numberOfPlayers", equalTo(4))
            .expectBody("[0][1].points", equalTo(50))
            .expectBody("[1][0].numberOfPlayers", equalTo(6))
            .expectBody("[1][0].points", equalTo(60))
            .expectBody("[2][0].numberOfPlayers", equalTo(3))
            .expectBody("[2][0].points", equalTo(45))
            .expectBody("[2][1].numberOfPlayers", equalTo(1))
            .expectBody("[2][1].points", equalTo(15))
            .expectBody("[2][2].numberOfPlayers", equalTo(1))
            .expectBody("[2][2].points", equalTo(12))
            .expectBody("[3][0].numberOfPlayers", equalTo(4))
            .expectBody("[3][0].points", equalTo(40))
            .expectBody("[4][0].numberOfPlayers", equalTo(5))
            .expectBody("[4][0].points", equalTo(40))
            .build();
    }
}