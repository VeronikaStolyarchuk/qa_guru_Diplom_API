package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import models.ErrorResponseModel;
import models.users.login.*;
import org.junit.jupiter.api.Test;
import testData.TestData;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.BaseSpec.requestSpec;
import static specs.users.UserLoginSpecifications.*;

@Owner("Veronika Stolyarchuk")
@Epic("Api testing")
@Feature("Login tests")
public class LoginTests extends BaseTest{

    @Test
    public void successfulLoginTest(){
        LoginBodyModel userData = new LoginBodyModel(
                TestData.LOGIN_EMAIL, TestData.LOGIN_PASSWORD);

        SuccessfulLoginBodyModel response = step("Отправить POST запрос на авторизацию", () ->
                given(requestSpec)
                        .body(userData)
                        .when()
                        .post("/users/login")
                        .then()
                        .spec(responseLoginSpec200)
                        .extract().as(SuccessfulLoginBodyModel.class));

        step("Проверка получения непустого id", () ->
                assertThat(response.getData().getId()).isNotNull());
        step("Проверка получения непустого токена", () ->
                assertThat(response.getData().getToken()).isNotNull());
        step("Проверка получения сообщения об успешной авторизации", () ->
                assertThat(response.getMessage()).isEqualTo("Login successful"));
    }

    @Test
    public void loginWithEmptyFieldsErrorTest(){

        ErrorResponseModel response = step("Отправить POST запрос на авторизацию с пустыми полями", () ->
                given(requestSpec)
                        .body("{}")
                        .when()
                        .post("/users/login")
                        .then()
                        .spec(responseLoginSpec400)
                        .extract().as(ErrorResponseModel.class));

        step("Проверка статуса", () ->
                assertThat(response.isSuccess()).isFalse());
        step("Проверка получения сообщения об ошибке авторизации", () ->
                assertThat(response.getMessage()).isEqualTo("A valid email address is required"));
    }

    @Test
    public void loginWithIncorrectPasswordErrorTest(){
        LoginBodyModel userData = new LoginBodyModel(
                TestData.LOGIN_EMAIL, testData.loginIncorrectPassword);

        ErrorResponseModel response = step("Отправить POST запрос на авторизацию с некорректным паролем", () ->
                given(requestSpec)
                        .body(userData)
                        .when()
                        .post("/users/login")
                        .then()
                        .spec(responseLoginSpec401)
                        .extract().as(ErrorResponseModel.class));

        step("Проверка статуса", () ->
                assertThat(response.isSuccess()).isFalse());
        step("Проверка получения сообщения о некорректном логине или пароле", () ->
                assertThat(response.getMessage()).isEqualTo("Incorrect email address or password"));
    }
}