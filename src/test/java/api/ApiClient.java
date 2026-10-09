package api;

public class ApiClient {
    public final RegistrationApiClient registrationApi = new RegistrationApiClient();
    public final LoginApiClient loginApi = new LoginApiClient();
    public final ProfileApiClient profileApi = new ProfileApiClient();
    public final NotesApiClient notesApi = new NotesApiClient();
}
