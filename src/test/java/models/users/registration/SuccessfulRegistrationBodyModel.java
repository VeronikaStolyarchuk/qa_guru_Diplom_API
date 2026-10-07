package models.users.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuccessfulRegistrationBodyModel {
    private boolean success;
    private int status;
    private String message;
    private RegistrationResponseData data;
}
