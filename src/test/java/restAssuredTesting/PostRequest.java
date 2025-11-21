package restAssuredTesting;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.io.IOUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PostRequest {

    @Test
    public void makePostCall(){
    RequestSpecification requestSpecification = given();
        requestSpecification.baseUri("https://reqres.in").basePath("api/users")
                .header("content-type", ContentType.JSON)
            .header("x-api-key","reqres-free-v1").body(loadPayload());
        requestSpecification.log().all();
      Response response = requestSpecification.post();
        response.then().log().all();
      int statusCode = response.getStatusCode();
      ResponseBody responseBody =  response.body();
        String responseInString = responseBody.asString();

        System.out.println("STATUS Code is : "+statusCode);
        Assert.assertEquals(statusCode,201);

      JsonObject jsonObject = JsonParser.parseString(responseInString).getAsJsonObject();
      String firstName = jsonObject.get("First_Name").getAsString();
        System.out.println("First_Name "+firstName);
    }

    private String  loadPayload(){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("requestJsons/postRequest.json");
        String payLoad = null;

        try{
            payLoad =  IOUtils.toString(inputStream,"UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
       return payLoad;
    }


    @Test
    public void makePostCallBtReadingHeadersFromConfigFile(){
        RequestSpecification requestSpecification = given();
        requestSpecification.baseUri("https://reqres.in").basePath("api/users")
                .headers(getHeader()).body(loadPayload());
        requestSpecification.log().all();
        ValidatableResponse  validatableResponse = requestSpecification.post().then();
        int statusCode = validatableResponse.extract().statusCode();
        System.out.println("STATUS Code : "+statusCode);
        String body = validatableResponse.extract().body().asString();

        JsonObject jsonObject =  JsonParser.parseString(body).getAsJsonObject();
        String firstName = jsonObject.get("First_Name").getAsString();
        System.out.println("First_Name "+firstName);

        JsonSchemaValidator jsonSchemaValidator = JsonSchemaValidator.matchesJsonSchemaInClasspath("Schema/postResponseSchema.jsd");
        validatableResponse.body(jsonSchemaValidator);
    }

    private Map<String, Object> getHeader(){
        Config config = ConfigFactory.parseFile(new File("src/test/resources/config/application.conf"));
        Config reqResConfig = config.getConfig("api.reqRes");
        Map<String, Object> headers = reqResConfig.getObject("headers").unwrapped();
        return headers;
    }


    @Test
    public void makePostCallByModifyingTheRequest(){
        RequestSpecification requestSpecification = given();

        requestSpecification.baseUri("https://reqres.in").basePath("api/users")
                .headers(getHeader()).body(loadPayload());
        requestSpecification.log().all();

        //Modify the payload
        HashMap<String,Object> dataMap = new HashMap<>();
        dataMap.put("id","25");
        String payLoadPostModify = modifyTheFieldInRequestPayLoad(dataMap);

        requestSpecification.body(payLoadPostModify);
        System.out.println("Payload post modification----------------------");
        requestSpecification.log().body();

        ValidatableResponse  validatableResponse = requestSpecification.post().then();
        int statusCode = validatableResponse.extract().statusCode();
        System.out.println("STATUS Code : "+statusCode);
        String body = validatableResponse.extract().body().asString();

        JsonObject jsonObject =  JsonParser.parseString(body).getAsJsonObject();
        String firstName = jsonObject.get("First_Name").getAsString();
        System.out.println("First_Name "+firstName);

        JsonSchemaValidator jsonSchemaValidator = JsonSchemaValidator.matchesJsonSchemaInClasspath("Schema/postResponseSchema.jsd");
        validatableResponse.body(jsonSchemaValidator);
    }

    private String modifyTheFieldInRequestPayLoad(HashMap<String,Object> dataMap){
       String payLoad = loadPayload();
        DocumentContext context = JsonPath.using(Configuration.defaultConfiguration()).parse(payLoad);
        Set<Map.Entry<String,Object>> entries = dataMap.entrySet();
        for(Map.Entry<String,Object> entry:entries){
            context.set(entry.getKey(),entry.getValue());
        }
       return context.jsonString();
    }
}
