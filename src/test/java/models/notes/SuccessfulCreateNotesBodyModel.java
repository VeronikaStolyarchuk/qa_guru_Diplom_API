package models.notes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuccessfulCreateNotesBodyModel {
    private boolean success;
    private int status;
    private String message;
    private CreateNotesResponseData data;
}
