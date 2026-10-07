package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import models.ErrorResponseModel;
import models.users.registration.*;
import org.junit.jupiter.api.Test;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.BaseSpec.requestSpec;
import static specs.users.UserRegistrationSpecifications.*;

@Owner("Veronika Stolyarchuk")
@Epic("Api testing")
@Feature("Registration tests")
public class RegistrationTests extends BaseTest{

    @Test
    public void successfulRegistrationTest(){
        RegistrationBodyModel userData = new RegistrationBodyModel(
                testData.userName, testData.userEmail, testData.password);

        SuccessfulRegistrationBodyModel response = step("Отправить POST запрос на регистрацию", () ->
                given(requestSpec)
                        .body(userData)
                        .when()
                        .post("/users/register")
                        .then()
                        .spec(responseRegistrationSpec201)
                        .extract().as(SuccessfulRegistrationBodyModel.class));

        step("Проверка получения непустого id", () ->
                assertThat(response.getData().getId()).isNotNull());
        step("Проверка получения сообщения об успешной регистрации", () ->
                assertThat(response.getMessage()).isEqualTo("User account created successfully"));
    }

    @Test
    public void registrationWithEmptyFieldsErrorTest(){

        ErrorResponseModel response = step("Отправить POST запрос на регистрацию с пустыми полями", () ->
                given(requestSpec)
                        .body("{}")
                        .when()
                        .post("/users/register")
                        .then()
                        .spec(responseRegistrationSpec400)
                        .extract().as(ErrorResponseModel.class));

        step("Проверка статуса", () ->
                assertThat(response.isSuccess()).isFalse());
        step("Проверка получения сообщения об ошибке регистрации", () ->
                assertThat(response.getMessage()).isEqualTo("User name must be between 4 and 30 characters"));
    }

    @Test
    public void registrationExistingUserErrorTest(){
        RegistrationBodyModel userData = new RegistrationBodyModel(
                testData.userName, testData.userEmail, testData.password);

        SuccessfulRegistrationBodyModel firstResponse = step("Отправить POST запрос на регистрацию", () ->
                given(requestSpec)
                        .body(userData)
                        .when()
                        .post("/users/register")
                        .then()
                        .spec(responseRegistrationSpec201)
                        .extract().as(SuccessfulRegistrationBodyModel.class));

        step("Проверка получения имени пользователя", () ->
                assertThat(firstResponse.getData().getName()).isEqualTo(testData.userName));

        ErrorResponseModel secondResponse = step("Отправить повторный POST запрос на регистрацию", () ->
                given(requestSpec)
                        .body(userData)
                        .when()
                        .post("/users/register")
                        .then()
                        .spec(responseRegistrationSpec409)
                        .extract().as(ErrorResponseModel.class));

        step("Проверка статуса", () ->
                assertThat(secondResponse.isSuccess()).isFalse());
        step("Проверка получения сообщения об ошибке регистрации", () ->
                assertThat(secondResponse.getMessage()).isEqualTo("An account already exists with the same email address"));
    }
}
