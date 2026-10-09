package api;

import io.qameta.allure.Step;
import models.ErrorResponseModel;
import models.users.registration.RegistrationBodyModel;
import models.users.registration.SuccessfulRegistrationBodyModel;
import static io.restassured.RestAssured.given;
import static specs.BaseSpec.requestSpec;
import static specs.users.UserRegistrationSpecifications.*;

public class RegistrationApiClient {

    @Step("Отправить POST запрос на регистрацию пользователя")
    public SuccessfulRegistrationBodyModel registrationUser(RegistrationBodyModel userData){

        return given(requestSpec)
                .body(userData)
                .when()
                .post("/users/register")
                .then()
                .spec(responseRegistrationSpec201)
                .extract().as(SuccessfulRegistrationBodyModel.class);
    }

    @Step("Отправить POST запрос на регистрацию пользователя с пустыми полями")
    public ErrorResponseModel registrationWithEmptyFields(){

        return given(requestSpec)
                .body("{}")
                .when()
                .post("/users/register")
                .then()
                .spec(responseRegistrationSpec400)
                .extract().as(ErrorResponseModel.class);
    }

    @Step("Отправить POST запрос на регистрацию существующего пользователя")
    public ErrorResponseModel registrationExistingUser(RegistrationBodyModel userData){

        return given(requestSpec)
                .body(userData)
                .when()
                .post("/users/register")
                .then()
                .spec(responseRegistrationSpec409)
                .extract().as(ErrorResponseModel.class);
    }
}
