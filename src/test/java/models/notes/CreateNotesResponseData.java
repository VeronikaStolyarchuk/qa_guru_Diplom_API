package models.notes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateNotesResponseData {
    private String id;
    private String title;
    private String description;
    private String completed;
    private String created_at;
    private String updated_at;
    private String category;
    private String user_id;

}