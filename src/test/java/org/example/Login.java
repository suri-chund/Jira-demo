package org.example;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;

public class Login {
    static String sessionId(String baseURI) {
        RestAssured.baseURI=baseURI;
        String response= given().log().all().header("Content-type","application/json")
                .body("{ \"username\": \"sunny123suresh\", \"password\": \"Secure@123\" }'")
                .when().post("/rest/auth/1/session")
                .then().log().all().extract().response().asString();
        return("JSESSIONID="+(new JsonPath(response)).getString("session.value"));
    }
}
