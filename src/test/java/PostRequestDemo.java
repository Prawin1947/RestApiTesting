import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.swagger.models.Path;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class PostRequestDemo {

    //    Pass the request body as a String to the request
    @Test
    private void readRequestBodyAsString() {
        Response response = given().header("Content-Type", "application/json").body("{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"\n" +
                "}").log().all().post("https://reqres.in/api/users");
        response.prettyPeek();

    }


//    Store a request body in the json file And send the file path in the request.
//    The content of the request body is not printing in the console, Instead it will print only the file name.
    @Test
    private void readRequestBodyFromJsonFile() {
        String path = System.getProperty("user.dir")+"/src/test/resources/requestJsons/postRequest.json";
        System.out.println("The request Body file is located at :~"+ path);
        Response response = given().header("Content-Type", "application/json").body(
                new File(path))
                .log().all().post("https://reqres.in/api/users");
        response.prettyPeek();

    }

    /**
     * Read the json request body from the file and Convert it into String
     * So that we can print in the console and also we can modify.
     */
    @Test
    private static void readRequestBodyFromFileAndConvertToString()throws Exception {

        String path = System.getProperty("user.dir")+"/src/test/resources/requestJsons/postRequest.json";
        byte[] bytes =  Files.readAllBytes(Paths.get(path));
        System.out.println("The request Body file is located at :~"+ path);
       String body = new String(bytes);
        body =  body.replace("12",String.valueOf(new Faker().number().numberBetween(1,15)));
      Response response =   given().header("Content-Type",ContentType.JSON).body(body).log().all()
                .post("https://reqres.in/api/users");
      response.prettyPeek();
    }

    /**
     * Constructing the request body using a Java Map and List.
     * {} --> use a Map
     * [] --> List
     * Since our request body is in the form of java object, Rest Won't understand and it will throw below exception
     * java.lang.IllegalStateException:Cannot serialize object because no JSON serializer found in classpath
     *
     * To Resolve the above exception we need to use "Jakson data bind maven dependency"
     * Serialization means to convert an object into that string
     * it converts "Language Specific objects" --> byte stream --> json
     *
     * DisAdvantages:~
     * It's a verbose and not suitable for long request body
     * Since java is type specific, we need to specify the generics
     * If we wan't to add a additional value the map will replace the old value with a new value
     * eg: line:93 I need to add both the emails
     *
     */
    @Test
    private static void constructRequestBodyFromMap(){

        Map<String,Object> bodyMap = new LinkedHashMap();
        bodyMap.put("First_Name","praveen");
        bodyMap.put("Last_Name","Mayakar");
        bodyMap.put("email", "test@gmail.com");
        bodyMap.put("age",29);
        bodyMap.put("id", 12);
//        bodyMap.put("email", "test1@gmail.com");
        List<String> jobList = Arrays.asList("Tester","Dev","DevOpsEng");
        bodyMap.put("jobs",jobList);
        Map<String,Object> foodMap = new LinkedHashMap();
        foodMap.put("breakfast","idly");
        foodMap.put("lunch","rice");
        List<String> dinnerList = Arrays.asList("Chapati", "Rice","milk");
        foodMap.put("Dinner",dinnerList);
        bodyMap.put("favFoods",foodMap);

       Response response =  given().header("content-Type",ContentType.JSON).
                body(bodyMap).log().all()
                .post("https://reqres.in/api/users");
       response.prettyPrint();
    }

    /**
     * Using the "Json" library we can resolve above disadvantage
     * {} --> JSONObject
     * [] --> JSONArray
     * if we use a "put" method to insert the multiple value to same key, the latest value will replace the older value.
     * If we use "accumulate" method to insert the multiple value to same key, it will convert that into json array and put it in the form of array
     *
     * Additional:-- Instead of printing the log in console storing it in a file
     */
    @Test
    private static void constructBodyUsingJsonLibrary() throws Exception{
        JSONObject bodyObj =  new JSONObject();
        bodyObj.put("First_Name","praveen");
        bodyObj.put("Last_Name","Mayakar");
        bodyObj.put("email", "test1@gmail.com");
        bodyObj.accumulate("email", "test2@gmail.com");
//        bodyObj.put("email", "test2@gmail.com");
        bodyObj.put("age",29);
        bodyObj.put("id", 12);
//        Define a list ob jobs for that use JSONArray
        JSONArray jobsList =  new JSONArray();
        jobsList.put("tester");
        jobsList.put("DEV");
        bodyObj.put("jobs",jobsList);
//        Define a jsonObject for food
        JSONObject foodObj =  new JSONObject();
        foodObj.put("breakfast","Idly");
        foodObj.put("lunch","Rice");
//        define a jsonArray to create a list of dinner items
        JSONArray dinnerFoods =  new JSONArray();
        dinnerFoods.put("Curry");
        dinnerFoods.put("Chapati");
        dinnerFoods.put("milk");
//        Add a dinner jsonArray into foodObj JsonObject
       foodObj.put("Dinner",dinnerFoods);
//       Finally add a foodObj JsonObject to bodyObj JsonObject
       bodyObj.put("Food",foodObj);

     Response response =  given().header("content-Type",ContentType.JSON)
               .body(bodyObj.toMap()).log().all()
               .post("https://reqres.in/api/users");
        response.prettyPrint();
        File testLogs = new File(System.getProperty("user.dir")+"/testLogs");
        if(!testLogs.exists()){
            testLogs.mkdir();
        }
        Files.write(Paths.get(System.getProperty("user.dir")+"/testLogs/test.json"),response.asByteArray());

    }
}











