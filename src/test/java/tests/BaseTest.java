package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import testData.TestData;

public class BaseTest {
    TestData testData = new TestData();

    @BeforeAll
    public static void setUp(){
        RestAssured.baseURI = "https://practice.expandtesting.com";
        RestAssured.basePath = "/notes/api";
    }
}
