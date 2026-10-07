package specs.users;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class UserLoginSpecifications {

    public static ResponseSpecification responseLoginSpec200 = new ResponseSpecBuilder()
            .log(LogDetail.BODY)
            .expectStatusCode(200)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/login/login_user_schema.json"))
            .build();

    public static ResponseSpecification responseLoginSpec400 = new ResponseSpecBuilder()
            .log(LogDetail.BODY)
            .expectStatusCode(400)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/response_user_schema.json"))
            .build();

    public static ResponseSpecification responseLoginSpec401 = new ResponseSpecBuilder()
            .log(LogDetail.BODY)
            .expectStatusCode(401)
            .expectBody(matchesJsonSchemaInClasspath(
                    "schemas/response_user_schema.json"))
            .build();
}
