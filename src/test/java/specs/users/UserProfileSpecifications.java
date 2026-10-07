package specs.users;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class UserProfileSpecifications {

    public static ResponseSpecification responseProfileSpec200 = new ResponseSpecBuilder()
            .log(LogDetail.BODY)
            .expectStatusCode(200)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/profile/profile_user_schema.json"))
            .build();

    public static ResponseSpecification responseProfileSpec400 = new ResponseSpecBuilder()
            .log(LogDetail.BODY)
            .expectStatusCode(400)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/response_user_schema.json"))
            .build();

    public static ResponseSpecification responseProfileSpec401 = new ResponseSpecBuilder()
            .log(LogDetail.BODY)
            .expectStatusCode(401)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/response_user_schema.json"))
            .build();
}
