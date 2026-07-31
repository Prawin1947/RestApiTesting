package restAssuredTesting;


//What is RequestSpecification and ResponseSpecification ?

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;

import java.io.InputStream;

import static io.restassured.RestAssured.given;

/**
 * RequestSpecification:
 * RequestSpecification is a reusable object in Rest Assured that stores common request details such as
 * the base URI, headers, authentication, content type, query parameters, and logging.
 * It helps eliminate duplicate code and makes API tests easier to maintain.
 * ----------------------------------------------------------------
 *
 * ResponseSpecification:
 * ResponseSpecification is a reusable object that stores common response validations, such as
 * expected status code, content type, response time, and headers. It allows consistent validation across multiple API tests.
 *
 */
public class RequestSpecAndResponseSpec {


    @Test
    public void makeRequest(){
        RequestSpecification requestSpecification = getRequestSpecification();
        ResponseSpecification responseSpecification = getResponseSpecification();

        given().spec(requestSpecification).body(loadPayload())
                .when().post()
                .then().spec(responseSpecification);
    }






    public RequestSpecification getRequestSpecification(){

        RequestSpecification requestSpecification =  new RequestSpecBuilder()
                .setBaseUri("https://reqres.in")
                .setBasePath("api/users")
                .setContentType(ContentType.JSON)
                .addHeader("x-api-key","reqres-free-v1")
                //.addHeader("Authorization","Bearer token")
                .log(LogDetail.ALL)
                .build();
        return requestSpecification;
    }


    public ResponseSpecification getResponseSpecification(){
      ResponseSpecification responseSpecification =   new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                //.expectHeader()
                .build();
        return responseSpecification;
    }





    private String loadPayload() {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("requestJsons/postRequest.json");
        String payLoad = null;

        try {
            payLoad = IOUtils.toString(inputStream, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return payLoad;
    }




}
