package specs.users;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class UserRegistrationSpecifications {

    public static ResponseSpecification responseRegistrationSpec201 = new ResponseSpecBuilder()
            .log(LogDetail.BODY)
            .expectStatusCode(201)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/registration/registration_user_schema.json"))
            .build();

    public static ResponseSpecification responseRegistrationSpec400 = new ResponseSpecBuilder()
            .log(LogDetail.BODY)
            .expectStatusCode(400)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/response_user_schema.json"))
            .build();

    public static ResponseSpecification responseRegistrationSpec409 = new ResponseSpecBuilder()
            .log(LogDetail.BODY)
            .expectStatusCode(409)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/response_user_schema.json"))
            .build();
}
