package ru.yandex.practicum.api.user;

import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static ru.yandex.practicum.config.RestConfig.*;

public class UserSteps {

    @Step("Создание пользователя")
    public ValidatableResponse createUser(User user) {
        return given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(POST_REGISTER)
                .then();
    }

    @Step("Логин пользователя")
    public ValidatableResponse loginUser(User user) {
        return given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(POST_LOGIN)
                .then();
    }


    @Step("Удаление пользователя по token")
    public ValidatableResponse deleteUser(String token) {
        String headerValue = token != null && token.startsWith("Bearer ") ? token : "Bearer " + token;

        return given()
                .header("Authorization", headerValue)
                .when()
                .delete(DELETE_USER)
                .then();
    }

    @Step("Извлечение accessToken из ответа")
    public String extractAccessToken(ValidatableResponse response) {
        return response
                .extract()
                .body()
                .jsonPath()
                .getString("accessToken");
    }

    protected static RequestSpecification getSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(HOST)
                .build();
    }

    protected static RequestSpecification getSpec(String bearerToken) {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", bearerToken)
                .setBaseUri(HOST)
                .build();
    }
}