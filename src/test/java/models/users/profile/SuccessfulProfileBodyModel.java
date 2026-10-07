package models.users.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuccessfulProfileBodyModel {
    private boolean success;
    private int status;
    private String message;
    private ProfileResponseData data;
}
