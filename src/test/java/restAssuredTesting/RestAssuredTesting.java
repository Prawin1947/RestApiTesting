package restAssuredTesting;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.testng.annotations.Test;

import java.awt.event.ActionListener;

import static io.restassured.RestAssured.*;

public class RestAssuredTesting {
    //"https://reqres.in/api/users?page=2"
   //https://reqres.in/api/users?page=2
    @Test
    private void getResource(){
      RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.baseUri("https://reqres.in").basePath("api/users").queryParams("page","2")
                .header("Content-Type", ContentType.JSON).header("x-api-key","reqres-free-v1");
        requestSpecification.when().log().all();
        Response response = requestSpecification.get();
        ValidatableResponse validatableResponse = requestSpecification.get().then();
        response.then().log().all();
        validatableResponse.log().all();

    //    String responseBody = validatableResponse.extract().response().body().asString();
       //    String v =        validatableResponse.extract().jsonPath().get("data").toString();
       // JsonPath jsonPath = JsonPath.from(v);
    //    String   v1 =  jsonPath.getJsonObject("data").toString();
  //      System.out.println( v );
        //System.out.println( v1 );
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        //System.out.println(responseBody);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        //status
        int statusCode = response.getStatusCode();
        response.body().asString();
        //value
       String value =  response.jsonPath().get("data[0].email");
        value =   String.format("The value is $ "+value);
        System.out.println(value);

        String page =  response.jsonPath().get("page").toString();
        System.out.println(page);
    }

    @Test
    private void getResourceWithResponse(){
        RequestSpecification requestSpecification = given();
        requestSpecification.baseUri("https://reqres.in").basePath("api/users").queryParams("page","2")
                .header("Content-Type", ContentType.JSON).header("x-api-key","reqres-free-v1");
        requestSpecification.log().all();

        Response response = requestSpecification.get();
        response.then().log().all();
        //Status Code
       int code =  response.getStatusCode();
       String statusCode = String.format("The status code is "+code);
        System.out.println(statusCode);
        //Response Body
      // String bd =  response.body().prettyPrint();
      // String bd =  response.body().asString();
        ResponseBody body = response.body();
        String responseBody = String.format("Response Body is "+body.asString());
        System.out.println(responseBody);
        //get the details
        String email = response.jsonPath().get("data[0].email");
        System.out.println(email);
        int page = response.body().jsonPath().get("page");
        System.out.println(page);

    }

    @Test
    private String getResourceWithValidatableResponse(){
        RequestSpecification requestSpecification = given();
        requestSpecification.baseUri("https://reqres.in").basePath("api/users").queryParams("page","2")
                .header("Content-Type", ContentType.JSON).header("x-api-key","reqres-free-v1");
        requestSpecification.when().log().all();

      ValidatableResponse validatableResponse =  requestSpecification.get().then();

        validatableResponse.log().all();
        //Status Code
       int statusCode = validatableResponse.extract().statusCode();
        System.out.println("Status code is "+statusCode);
        //Response Body
        String responseBody = validatableResponse.extract().body().asPrettyString();
        System.out.println(responseBody);

        String value = validatableResponse.extract().jsonPath().get("data[0].email");
        System.out.println(value);
        return responseBody;
    }

    @Test
    private void validateGetResponse(){
        String responseBody = getResourceWithValidatableResponse();

        JsonObject jsonObject =  JsonParser.parseString(responseBody).getAsJsonObject();

      String s  =  jsonObject.get("total_pages").getAsString();

        System.out.println(s);

       JsonArray jsonArray =   jsonObject.getAsJsonArray("data");

       for (JsonElement jsonElement:jsonArray){
         String  id =  jsonElement.getAsJsonObject().get("id").getAsString();
      }

    }
}