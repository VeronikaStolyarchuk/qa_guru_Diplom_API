package models.users.registration;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationBodyModel {
    private String name;
    private String email;
    private String password;
}
