package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import models.ErrorResponseModel;
import models.users.registration.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@Owner("Veronika Stolyarchuk")
@Epic("Api testing")
@Feature("Registration tests")
public class RegistrationTests extends BaseTest{

    @DisplayName("Успешная регистрация пользователя")
    @Test
    public void successfulRegistrationTest(){
        RegistrationBodyModel userData = new RegistrationBodyModel(
                testData.userName, testData.userEmail, testData.password);

        SuccessfulRegistrationBodyModel response = api.registrationApi.registrationUser(userData);

        step("Проверка получения непустого id", () ->
                assertThat(response.getData().getId()).isNotNull());
        step("Проверка получения сообщения об успешной регистрации", () ->
                assertThat(response.getMessage()).isEqualTo("User account created successfully"));
    }

    @DisplayName("Проверка регистрации пользователя с пустыми полями")
    @Test
    public void registrationWithEmptyFieldsErrorTest(){

        ErrorResponseModel response = api.registrationApi.registrationWithEmptyFields();

        step("Проверка статуса", () ->
                assertThat(response.isSuccess()).isFalse());
        step("Проверка получения сообщения об ошибке регистрации", () ->
                assertThat(response.getMessage()).isEqualTo("User name must be between 4 and 30 characters"));
    }

    @DisplayName("Проверка регистрации существующего пользователя")
    @Test
    public void registrationExistingUserErrorTest(){
        RegistrationBodyModel userData = new RegistrationBodyModel(
                testData.userName, testData.userEmail, testData.password);

        SuccessfulRegistrationBodyModel firstResponse = api.registrationApi.registrationUser(userData);

        step("Проверка получения имени пользователя", () ->
                assertThat(firstResponse.getData().getName()).isEqualTo(testData.userName));

        ErrorResponseModel secondResponse = api.registrationApi.registrationExistingUser(userData);

        step("Проверка статуса", () ->
                assertThat(secondResponse.isSuccess()).isFalse());
        step("Проверка получения сообщения об ошибке регистрации", () ->
                assertThat(secondResponse.getMessage()).isEqualTo("An account already exists with the same email address"));
    }
}
