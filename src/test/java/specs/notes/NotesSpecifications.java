package specs.notes;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class NotesSpecifications {

    public static ResponseSpecification responseNotesSpec200 = new ResponseSpecBuilder()
            .log(LogDetail.BODY)
            .expectStatusCode(200)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/notes/create_notes_schema.json"))
            .build();

    public static ResponseSpecification responseNotesSpec400 = new ResponseSpecBuilder()
            .log(LogDetail.BODY)
            .expectStatusCode(400)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/response_user_schema.json"))
            .build();

    public static ResponseSpecification responseDeleteNotesSpec200 = new ResponseSpecBuilder()
            .log(LogDetail.BODY)
            .expectStatusCode(200)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/response_user_schema.json"))
            .build();
}
