package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import models.ErrorResponseModel;
import models.users.login.*;
import models.users.profile.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testData.TestData;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@Owner("Veronika Stolyarchuk")
@Epic("Api testing")
@Feature("UserProfile tests")
public class UserProfileTests extends BaseTest{

    @DisplayName("Получение профиля пользователя")
    @Test
    public void getUserProfileByTokenTest(){
        LoginBodyModel userData = new LoginBodyModel(
                TestData.LOGIN_EMAIL, TestData.LOGIN_PASSWORD);

        SuccessfulLoginBodyModel response = api.loginApi.loginUser(userData);

        step("Проверка получения непустого id", () ->
                assertThat(response.getData().getId()).isNotNull());
        step("Проверка получения непустого токена", () ->
                assertThat(response.getData().getToken()).isNotNull());

                String token = response.getData().getToken();
                String userId = response.getData().getId();

        SuccessfulProfileBodyModel responseProfile = api.profileApi.getUserProfile(token);

        step("Проверка сообщения об успешном получении профиля", () ->
                assertThat(responseProfile.getMessage()).isEqualTo("Profile successful"));
        step("Проверка, что id профиля совпадает с id из логина", () ->
                assertThat(responseProfile.getData().getId()).isEqualTo(userId));
        step("Проверка email в профиле", () ->
                assertThat(responseProfile.getData().getEmail()).isEqualTo(TestData.LOGIN_EMAIL));
        }

    @DisplayName("Обновление профиля пользователя")
    @Test
    public void updateUserProfileTest(){
        LoginBodyModel userData = new LoginBodyModel(
                TestData.LOGIN_EMAIL, TestData.LOGIN_PASSWORD);
        UpdateProfileBodyModel userUpdateData = new UpdateProfileBodyModel(
                testData.userName, testData.userPhone, testData.userCompany);

        SuccessfulLoginBodyModel response = api.loginApi.loginUser(userData);

        step("Проверка получения непустого id", () ->
                assertThat(response.getData().getId()).isNotNull());
        step("Проверка получения непустого токена", () ->
                assertThat(response.getData().getToken()).isNotNull());

        String token = response.getData().getToken();

        SuccessfulProfileBodyModel responseProfile = api.profileApi.updateUserProfile(token, userUpdateData);

        step("Проверка сообщения об успешном обновлении профиля", () ->
                assertThat(responseProfile.getMessage()).isEqualTo("Profile updated successful"));
        step("Проверка обновления имени пользователя", () ->
                assertThat(responseProfile.getData().getName()).isEqualTo(testData.userName));
        step("Проверка обновления поля phone", () ->
                assertThat(responseProfile.getData().getPhone()).isEqualTo(testData.userPhone));
        step("Проверка обновления поля company", () ->
                assertThat(responseProfile.getData().getCompany()).isEqualTo(testData.userCompany));
    }

    @DisplayName("Проверка обновления профиля пользователя с пустыми полями")
    @Test
    public void updateProfileWithEmptyRequiredFieldWrongTest(){
        LoginBodyModel userData = new LoginBodyModel(
                TestData.LOGIN_EMAIL, TestData.LOGIN_PASSWORD);

        SuccessfulLoginBodyModel response = api.loginApi.loginUser(userData);

        step("Проверка получения непустого токена", () ->
                assertThat(response.getData().getToken()).isNotNull());

        String token = response.getData().getToken();

        ErrorResponseModel responseProfile = api.profileApi.updateProfileWithEmptyRequiredField(token);

        step("Проверка статуса", () ->
                assertThat(responseProfile.isSuccess()).isFalse());
        step("Проверка получения сообщения об ошибке регистрации", () ->
                assertThat(responseProfile.getMessage()).isEqualTo("User name must be between 4 and 30 characters"));
    }

    @DisplayName("Проверка обновления профиля пользователя без токена")
    @Test
    public void updateProfileWithoutTokenWrongTest(){
        UpdateProfileBodyModel userUpdateData = new UpdateProfileBodyModel(
                testData.userName, testData.userPhone, testData.userCompany);

        ErrorResponseModel response = api.profileApi.updateProfileWithoutToken(userUpdateData);

        step("Проверка статуса", () ->
                assertThat(response.isSuccess()).isFalse());
        step("Проверка получения сообщения об ошибке обновления профиля", () ->
                assertThat(response.getMessage()).isEqualTo("No authentication token specified in x-auth-token header"));
    }
}