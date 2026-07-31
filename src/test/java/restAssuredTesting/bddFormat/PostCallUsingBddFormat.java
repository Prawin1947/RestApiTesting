package restAssuredTesting.bddFormat;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;

import java.io.InputStream;

import static io.restassured.RestAssured.given;
public class PostCallUsingBddFormat {

    @Test
    public void makePostCallUsingBddFormat(){

        RequestSpecification requestSpecification = given().contentType(ContentType.JSON)
            .header("x-api-key", "reqres-free-v1")
                .body(loadPayload());

        requestSpecification.log().all();

       Response response =  requestSpecification.post("https://reqres.in/api/users");

       int statusCode =  response.getStatusCode();


       String first_name =  response.jsonPath().getString("First_Name");
        System.out.println(first_name);

        //or
        String name = response.jsonPath().get("First_Name").toString();
        System.out.println(first_name);

       int page =  response.jsonPath().getInt("page");
        System.out.println(page);
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
