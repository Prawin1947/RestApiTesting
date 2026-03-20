package restAssuredTesting;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MockApi {

    /**
     * Steps to create a mock api in PostMan tool
     *
     *  1. Login to the postman (Create a account)
     *  2. Click on "configure workspace sidebar"
     *  3. Enable "Mock servers"
     *  4. Create a mock server by adding the details.
     *  5. in the collections the postman will create a mock collection
     */

    @Test
    public void createMockApiUsingPostMan(){

       RequestSpecification requestSpecification =  RestAssured.given()
       .baseUri("https://12c225c2-5ceb-4d68-85f8-0fbec6e3203a.mock.pstmn.io")
               .basePath("users");
        requestSpecification.log().all();

        Response response = requestSpecification.get();
        int statusCode =  response.getStatusCode();

        Assert.assertEquals(statusCode,200,"The request is success");

        ResponseBody responseBody = response.body();
        String body = responseBody.asString();

        System.out.println(body);

        JsonObject  jsonObject  = JsonParser.parseString(body).getAsJsonObject();

        JsonArray jsonArray = jsonObject.get("users").getAsJsonArray();

        for(JsonElement element : jsonArray){
           String name =  element.getAsJsonObject().get("name").getAsString();
            System.out.println(name);
        }

    }


    /**
     *  Steps to create a mock api in Json-server--
     *
     * Create a json server using --> https://www.npmjs.com/package/json-server
     *  ---  Open the command prompt in the IntelIj IDE project path ---
     * 1. node and npm should be installed [npm install -g npm@11.12.0]
     * 2. install the json server [npm install json-server]
     * 3. Create a db.json file in the project path and add your json request
     * 4. Start the json server [npx json-server db.json]
     * The server will start in http://localhost:3000
     *
     */
    @Test
    public void mockApiTestingUsingJsonServer(){
        RequestSpecification requestSpecification =  RestAssured.given()
                .baseUri("http://localhost:3000")
                .basePath("posts");
        requestSpecification.log().all();

        Response response = requestSpecification.get();
        int statusCode =  response.getStatusCode();

        Assert.assertEquals(statusCode,200,"The request is success");

        ResponseBody responseBody = response.body();
        String body = responseBody.asString();


        JsonArray  jsonArray  = JsonParser.parseString(body).getAsJsonArray();

        for(JsonElement element : jsonArray){
            String id =  element.getAsJsonObject().get("id").getAsString();
            String title =  element.getAsJsonObject().get("title").getAsString();

            System.out.println(id+" : "+title);
        }
    }
}
