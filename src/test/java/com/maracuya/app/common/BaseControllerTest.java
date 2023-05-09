package com.maracuya.app.common;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.config.LogConfig.logConfig;
import static io.restassured.config.RestAssuredConfig.config;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public abstract class BaseControllerTest {

    @LocalServerPort
    private int applicationPort;

    @BeforeEach
    void setRestAssuredPort() {
        RestAssured.port = applicationPort;
    }

    @BeforeEach
    void configureRestAssuredLoggingConfig() {
        RestAssured.config = config().logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
    }
}
