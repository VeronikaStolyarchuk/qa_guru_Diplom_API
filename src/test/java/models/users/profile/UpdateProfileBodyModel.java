package models.users.profile;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateProfileBodyModel {
    private String name;
    private String phone;
    private String company;
}
