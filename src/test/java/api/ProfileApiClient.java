package api;

import io.qameta.allure.Step;
import models.ErrorResponseModel;
import models.users.profile.SuccessfulProfileBodyModel;
import models.users.profile.UpdateProfileBodyModel;
import static io.restassured.RestAssured.given;
import static specs.BaseSpec.requestSpec;
import static specs.BaseSpec.requestSpecWithToken;
import static specs.users.UserProfileSpecifications.*;

public class ProfileApiClient {

    @Step("Отправить GET запрос на получение профиля")
    public SuccessfulProfileBodyModel getUserProfile(String token){

        return given(requestSpecWithToken(token))
                .when()
                .get("/users/profile")
                .then()
                .spec(responseProfileSpec200)
                .extract().as(SuccessfulProfileBodyModel.class);
    }

    @Step("Отправить PATCH запрос на обновление профиля")
    public SuccessfulProfileBodyModel updateUserProfile(String token, UpdateProfileBodyModel userUpdateData){

        return given(requestSpecWithToken(token))
                .body(userUpdateData)
                .when()
                .patch("/users/profile")
                .then()
                .spec(responseProfileSpec200)
                .extract().as(SuccessfulProfileBodyModel.class);
    }

    @Step("Отправить PATCH запрос на обновление профиля с пустыми полями")
    public ErrorResponseModel updateProfileWithEmptyRequiredField(String token){

        return given(requestSpecWithToken(token))
                .body("{}")
                .when()
                .patch("/users/profile")
                .then()
                .spec(responseProfileSpec400)
                .extract().as(ErrorResponseModel.class);
    }

    @Step("Отправить PATCH запрос на обновление профиля без токена")
    public ErrorResponseModel updateProfileWithoutToken(UpdateProfileBodyModel userUpdateData){

        return given(requestSpec)
                .body(userUpdateData)
                .when()
                .patch("/users/profile")
                .then()
                .spec(responseProfileSpec401)
                .extract().as(ErrorResponseModel.class);
    }
}
