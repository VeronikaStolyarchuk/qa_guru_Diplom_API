package tests;

import api.ApiClient;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import testData.TestData;

public class BaseTest {
    protected static final ApiClient api = new ApiClient();
    TestData testData = new TestData();

    @BeforeAll
    public static void setUp(){
        RestAssured.baseURI = "https://practice.expandtesting.com";
        RestAssured.basePath = "/notes/api";
    }
}
