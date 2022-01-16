package org.example;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;

public class UpdateIssue {
    UpdateIssue(String BaseURI, String SessionId,String IssueKey, String QACompletion)
    {
        RestAssured.baseURI=BaseURI;
        given().log().all().pathParam("Key",IssueKey).header("Content-type","application/json").header("Cookie",SessionId)
                .body("{\n" +
                        "    \"fields\" : {\n" +
                        "            \"customfield_10200\" : "+QACompletion+"\n" +
                        "    }\n" +
                        "}")
                .when().put("/rest/api/2/issue/{Key}")
                .then().log().all();
    }
}
