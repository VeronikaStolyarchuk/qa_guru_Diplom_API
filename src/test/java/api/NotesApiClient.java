package api;

import io.qameta.allure.Step;
import models.ErrorResponseModel;
import models.notes.CreateNotesBodyModel;
import models.notes.SuccessfulCreateNotesBodyModel;
import static io.restassured.RestAssured.given;
import static specs.BaseSpec.requestSpecWithToken;
import static specs.notes.NotesSpecifications.*;

public class NotesApiClient {

    @Step("Отправить POST запрос на создание заметки")
    public SuccessfulCreateNotesBodyModel createNotes(String token, CreateNotesBodyModel notesData){

        return given(requestSpecWithToken(token))
                .body(notesData)
                .when()
                .post("/notes")
                .then()
                .spec(responseNotesSpec200)
                .extract().as(SuccessfulCreateNotesBodyModel.class);
    }

    @Step("Отправить GET запрос на получение заметки по несуществующему id")
    public ErrorResponseModel getNoteByNotExistingNoteId(String token, String noteId){

        return given(requestSpecWithToken(token))
                .when()
                .get("/notes/"+ noteId)
                .then()
                .spec(responseNotesSpec400)
                .extract().as(ErrorResponseModel.class);
    }

    @Step("Отправить DELETE запрос на удаление заметки")
    public ErrorResponseModel deleteNote(String token, String noteId){

        return given(requestSpecWithToken(token))
                .when()
                .delete("/notes/"+ noteId)
                .then()
                .spec(responseDeleteNotesSpec200)
                .extract().as(ErrorResponseModel.class);
    }
}