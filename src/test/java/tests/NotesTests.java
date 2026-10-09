package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import models.ErrorResponseModel;
import models.notes.*;
import models.users.login.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testData.TestData;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@Owner("Veronika Stolyarchuk")
@Epic("Api testing")
@Feature("Notes tests")
public class NotesTests extends BaseTest{

    @DisplayName("Создание заметки")
    @Test
    public void createNotesTest(){
        LoginBodyModel userData = new LoginBodyModel(
                TestData.LOGIN_EMAIL, TestData.LOGIN_PASSWORD);
        CreateNotesBodyModel notesData = new CreateNotesBodyModel(
                testData.noteTitle, testData.noteDescription, testData.noteCategory);

        SuccessfulLoginBodyModel response = api.loginApi.loginUser(userData);

        step("Проверка получения непустого токена", () ->
                assertThat(response.getData().getToken()).isNotNull());
        step("Проверка получения непустого id", () ->
                assertThat(response.getData().getId()).isNotNull());

                String token = response.getData().getToken();
                String userId = response.getData().getId();

        SuccessfulCreateNotesBodyModel responseNote = api.notesApi.createNotes(token, notesData);

        step("Проверка сообщения об успешном создании заметки", () ->
                assertThat(responseNote.getMessage()).isEqualTo("Note successfully created"));
        step("Проверка, что id пользователя, создавшего заметку совпадает с id из логина", () ->
                assertThat(responseNote.getData().getUser_id()).isEqualTo(userId));
        step("Проверка названия заметки", () ->
                assertThat(responseNote.getData().getTitle()).isEqualTo(testData.noteTitle));
        }

    @DisplayName("Получение заметки по несуществующему id")
    @Test
    public void getNoteByNotExistingNoteIdTest(){
        LoginBodyModel userData = new LoginBodyModel(
                TestData.LOGIN_EMAIL, TestData.LOGIN_PASSWORD);
        String noteId = testData.notExistingNoteId;

        SuccessfulLoginBodyModel response = api.loginApi.loginUser(userData);

        step("Проверка получения непустого токена", () ->
                assertThat(response.getData().getToken()).isNotNull());

        String token = response.getData().getToken();

        ErrorResponseModel responseNote = api.notesApi.getNoteByNotExistingNoteId(token, noteId);

        step("Проверка статуса", () ->
                assertThat(responseNote.isSuccess()).isFalse());
        step("Проверка сообщения о некорректном id", () ->
                assertThat(responseNote.getMessage()).isEqualTo("Note ID must be a valid ID"));
    }

    @DisplayName("Удаление заметки")
    @Test
    public void deleteNoteTest(){
        LoginBodyModel userData = new LoginBodyModel(
                TestData.LOGIN_EMAIL, TestData.LOGIN_PASSWORD);
        CreateNotesBodyModel notesData = new CreateNotesBodyModel(
                testData.noteTitle, testData.noteDescription, testData.noteCategory);

        SuccessfulLoginBodyModel response = api.loginApi.loginUser(userData);

        step("Проверка получения непустого токена", () ->
                assertThat(response.getData().getToken()).isNotNull());

        String token = response.getData().getToken();

        SuccessfulCreateNotesBodyModel responseNote = api.notesApi.createNotes(token, notesData);

        step("Проверка сообщения об успешном создании заметки", () ->
                assertThat(responseNote.getMessage()).isEqualTo("Note successfully created"));
        step("Проверка названия заметки", () ->
                assertThat(responseNote.getData().getTitle()).isEqualTo(testData.noteTitle));

        String noteId = responseNote.getData().getId();

        ErrorResponseModel responseDeleteNote = api.notesApi.deleteNote(token, noteId);

        step("Проверка статуса", () ->
                assertThat(responseDeleteNote.isSuccess()).isTrue());
        step("Проверка сообщения об успешном удалении заметки", () ->
                assertThat(responseDeleteNote.getMessage()).isEqualTo("Note successfully deleted"));
    }
}