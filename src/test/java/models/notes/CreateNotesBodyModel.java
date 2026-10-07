package models.notes;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateNotesBodyModel {
    private String title;
    private String description;
    private String category;
}
