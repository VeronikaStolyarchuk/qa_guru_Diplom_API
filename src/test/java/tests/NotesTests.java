package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import models.ErrorResponseModel;
import models.notes.*;
import models.users.login.*;
import org.junit.jupiter.api.Test;
import testData.TestData;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.BaseSpec.*;
import static specs.notes.NotesSpecifications.*;
import static specs.users.UserLoginSpecifications.*;

@Owner("Veronika Stolyarchuk")
@Epic("Api testing")
@Feature("Notes tests")
public class NotesTests extends BaseTest{

    @Test
    public void createNotesTest(){
        LoginBodyModel userData = new LoginBodyModel(
                TestData.LOGIN_EMAIL, TestData.LOGIN_PASSWORD);
        CreateNotesBodyModel notesData = new CreateNotesBodyModel(
                testData.noteTitle, testData.noteDescription, testData.noteCategory);

        SuccessfulLoginBodyModel response = step("Отправить POST запрос и получить токен", () ->
                given(requestSpec)
                        .body(userData)
                        .when()
                        .post("/users/login")
                        .then()
                        .spec(responseLoginSpec200)
                        .extract().as(SuccessfulLoginBodyModel.class));

        step("Проверка получения непустого токена", () ->
                assertThat(response.getData().getToken()).isNotNull());
        step("Проверка получения непустого id", () ->
                assertThat(response.getData().getId()).isNotNull());

                String token = response.getData().getToken();
                String userId = response.getData().getId();

        SuccessfulCreateNotesBodyModel responseNote = step("Отправить POST запрос на создание заметки", () ->
                given(requestSpecWithToken(token))
                        .body(notesData)
                        .when()
                        .post("/notes")
                        .then()
                        .spec(responseNotesSpec200)
                        .extract().as(SuccessfulCreateNotesBodyModel.class));

        step("Проверка сообщения об успешном создании заметки", () ->
                assertThat(responseNote.getMessage()).isEqualTo("Note successfully created"));
        step("Проверка, что id пользователя, создавшего заметку совпадает с id из логина", () ->
                assertThat(responseNote.getData().getUser_id()).isEqualTo(userId));
        step("Проверка названия заметки", () ->
                assertThat(responseNote.getData().getTitle()).isEqualTo(testData.noteTitle));
        }

    @Test
    public void getNoteByNotExistingNoteIdTest(){
        LoginBodyModel userData = new LoginBodyModel(
                TestData.LOGIN_EMAIL, TestData.LOGIN_PASSWORD);
        String noteId = testData.notExistingNoteId;

        SuccessfulLoginBodyModel response = step("Отправить POST запрос и получить токен", () ->
                given(requestSpec)
                        .body(userData)
                        .when()
                        .post("/users/login")
                        .then()
                        .spec(responseLoginSpec200)
                        .extract().as(SuccessfulLoginBodyModel.class));

        step("Проверка получения непустого токена", () ->
                assertThat(response.getData().getToken()).isNotNull());

        String token = response.getData().getToken();

        ErrorResponseModel responseNote = step("Отправить GET запрос на получение заметки по несуществующему id", () ->
                given(requestSpecWithToken(token))
                        .when()
                        .get("/notes/"+noteId)
                        .then()
                        .spec(responseNotesSpec400)
                        .extract().as(ErrorResponseModel.class));

        step("Проверка статуса", () ->
                assertThat(responseNote.isSuccess()).isFalse());
        step("Проверка сообщения о некорректном id", () ->
                assertThat(responseNote.getMessage()).isEqualTo("Note ID must be a valid ID"));
    }

    @Test
    public void deleteNoteTest(){
        LoginBodyModel userData = new LoginBodyModel(
                TestData.LOGIN_EMAIL, TestData.LOGIN_PASSWORD);
        CreateNotesBodyModel notesData = new CreateNotesBodyModel(
                testData.noteTitle, testData.noteDescription, testData.noteCategory);

        SuccessfulLoginBodyModel response = step("Отправить POST запрос и получить токен", () ->
                given(requestSpec)
                        .body(userData)
                        .when()
                        .post("/users/login")
                        .then()
                        .spec(responseLoginSpec200)
                        .extract().as(SuccessfulLoginBodyModel.class));

        step("Проверка получения непустого токена", () ->
                assertThat(response.getData().getToken()).isNotNull());

        String token = response.getData().getToken();

        SuccessfulCreateNotesBodyModel responseNote = step("Отправить POST запрос на создание заметки", () ->
                given(requestSpecWithToken(token))
                        .body(notesData)
                        .when()
                        .post("/notes")
                        .then()
                        .spec(responseNotesSpec200)
                        .extract().as(SuccessfulCreateNotesBodyModel.class));

        step("Проверка сообщения об успешном создании заметки", () ->
                assertThat(responseNote.getMessage()).isEqualTo("Note successfully created"));
        step("Проверка названия заметки", () ->
                assertThat(responseNote.getData().getTitle()).isEqualTo(testData.noteTitle));

        String noteId = responseNote.getData().getId();

        ErrorResponseModel responseDeleteNote = step("Отправить DELETE запрос на удаление заметки", () ->
                given(requestSpecWithToken(token))
                        .when()
                        .delete("/notes/"+ noteId)
                        .then()
                        .spec(responseDeleteNotesSpec200)
                        .extract().as(ErrorResponseModel.class));

        step("Проверка статуса", () ->
                assertThat(responseDeleteNote.isSuccess()).isTrue());
        step("Проверка сообщения об успешном удалении заметки", () ->
                assertThat(responseDeleteNote.getMessage()).isEqualTo("Note successfully deleted"));
    }
}