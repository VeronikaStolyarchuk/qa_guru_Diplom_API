package models.users.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponseData {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String company;
}