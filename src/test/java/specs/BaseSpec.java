package specs;

import io.restassured.specification.RequestSpecification;
import static helpers.CustomAllureFilter.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;

public class BaseSpec {

    public static RequestSpecification requestSpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().body()
            .log().headers()
            .contentType(JSON);

    public static RequestSpecification requestSpecWithToken(String token){
        return with()
                .spec(requestSpec)
                .header("X-Auth-Token", token);
    }
}
