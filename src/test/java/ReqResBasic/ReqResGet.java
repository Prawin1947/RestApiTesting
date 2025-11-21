package ReqResBasic;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.*;
public class ReqResGet {

    public static void main(String[] args) {
       getCall();
        readAllHeaders();
    }

    @Test
    private static void getCall(){
      RequestSpecification requestSpecification =given();
     Response response =  requestSpecification.get("https://reqres.in/api/users?page=2");

        System.out.println(response.getContentType());
        System.out.println(response.prettyPrint());
        System.out.println(response.statusCode());
        System.out.println(response.statusLine());
        System.out.println(response.getHeader("content-type"));
    }

    @Test
    private static void readAllHeaders(){
        Response response =  given().get("https://reqres.in/api/users?page=2");
       Headers headers = response.getHeaders();
       for(Header header: headers){
           System.out.println(header.getName()+" = "+header.getValue());
       }
    }


    @Test
    private static void validateTheResponse(){
        Response response =  given().header("Content-Type", ContentType.JSON).log().all().
        get("https://reqres.in/api/users?page=2");
        System.out.println("Printing the Response");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        response.then().log().all();

       String fname =   response.jsonPath().get("data[2].first_name").toString();
        Assert.assertEquals(fname,"Tobias");
        // Schema Validation
        response.then().body(JsonSchemaValidator.matchesJsonSchema(new File("D:\\IDEProjects\\RestAssuredAPI\\src\\test\\resources\\Schema\\getReqSchema.json")));

    }
}
