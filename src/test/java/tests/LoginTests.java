package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import models.ErrorResponseModel;
import models.users.login.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testData.TestData;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@Owner("Veronika Stolyarchuk")
@Epic("Api testing")
@Feature("Login tests")
public class LoginTests extends BaseTest{

    @DisplayName("Успешная авторизация пользователя")
    @Test
    public void successfulLoginTest(){
        LoginBodyModel userData = new LoginBodyModel(
                TestData.LOGIN_EMAIL, TestData.LOGIN_PASSWORD);

        SuccessfulLoginBodyModel response = api.loginApi.loginUser(userData);

        step("Проверка получения непустого id", () ->
                assertThat(response.getData().getId()).isNotNull());
        step("Проверка получения непустого токена", () ->
                assertThat(response.getData().getToken()).isNotNull());
        step("Проверка получения сообщения об успешной авторизации", () ->
                assertThat(response.getMessage()).isEqualTo("Login successful"));
    }

    @DisplayName("Проверка авторизации пользователя с пустыми полями")
    @Test
    public void loginWithEmptyFieldsErrorTest(){

        ErrorResponseModel response = api.loginApi.loginWithEmptyFields();

        step("Проверка статуса", () ->
                assertThat(response.isSuccess()).isFalse());
        step("Проверка получения сообщения об ошибке авторизации", () ->
                assertThat(response.getMessage()).isEqualTo("A valid email address is required"));
    }

    @DisplayName("Проверка авторизации пользователя с невалидным паролем")
    @Test
    public void loginWithIncorrectPasswordErrorTest(){
        LoginBodyModel userData = new LoginBodyModel(
                TestData.LOGIN_EMAIL, testData.loginIncorrectPassword);

        ErrorResponseModel response = api.loginApi.loginWithIncorrectPassword(userData);

        step("Проверка статуса", () ->
                assertThat(response.isSuccess()).isFalse());
        step("Проверка получения сообщения о некорректном логине или пароле", () ->
                assertThat(response.getMessage()).isEqualTo("Incorrect email address or password"));
    }
}