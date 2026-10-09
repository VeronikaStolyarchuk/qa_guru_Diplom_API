package api;

import io.qameta.allure.Step;
import models.ErrorResponseModel;
import models.users.login.LoginBodyModel;
import models.users.login.SuccessfulLoginBodyModel;
import static io.restassured.RestAssured.given;
import static specs.BaseSpec.requestSpec;
import static specs.users.UserLoginSpecifications.*;

public class LoginApiClient {

    @Step("Отправить POST запрос на авторизацию")
    public SuccessfulLoginBodyModel loginUser(LoginBodyModel userData){

        return given(requestSpec)
                .body(userData)
                .when()
                .post("/users/login")
                .then()
                .spec(responseLoginSpec200)
                .extract().as(SuccessfulLoginBodyModel.class);
    }

    @Step("Отправить POST запрос на авторизацию с пустыми полями")
    public ErrorResponseModel loginWithEmptyFields(){

        return given(requestSpec)
                .body("{}")
                .when()
                .post("/users/login")
                .then()
                .spec(responseLoginSpec400)
                .extract().as(ErrorResponseModel.class);
    }

    @Step("Отправить POST запрос на авторизацию с некорректным паролем")
    public ErrorResponseModel loginWithIncorrectPassword(LoginBodyModel userData){

        return given(requestSpec)
                .body(userData)
                .when()
                .post("/users/login")
                .then()
                .spec(responseLoginSpec401)
                .extract().as(ErrorResponseModel.class);
    }
}
