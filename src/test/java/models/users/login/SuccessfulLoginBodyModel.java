package models.users.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuccessfulLoginBodyModel {
    private boolean success;
    private int status;
    private String message;
    private LoginResponseData data;
}
