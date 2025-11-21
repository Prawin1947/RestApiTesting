package ReqResBasic;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.restassured.RestAssured.*;
public class ReqResPost {

    //Pass a request json body in string
    @Test
    private static void reqResPost1(){
//        https://reqres.in/api/register
      Response response =  given().header("content-type", ContentType.JSON)
                .body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"pistol\"\n" +
                        "}").log().all().post(" https://reqres.in/api/register");

        System.out.println("-----------------------------------------");
        response.prettyPrint();

    }

//    Passing a request body in File
//
    @Test
    private static void reqReaPost2(){
       Response response =  given().header("Content-type",ContentType.JSON)
                .body(new File("src/test/resources/requestBody.json")).
                log().all().post(" https://reqres.in/api/register");

        response.prettyPrint();
    }

//    Modify the content in the request body
    @Test
    private static void reqRePost3() throws IOException {
     byte[] bytear  =   Files.readAllBytes(Paths.get("src/test/resources/requestBody.json"));
       String body =     new String(bytear);
       body =  body.replace("Engineer","Automation Test Engineer");
      Response response =   given().header("Content-type",ContentType.JSON)
                .body(body).log().all()
                .post(" https://reqres.in/api/register");

        System.out.println("Ststus Code: - "+    response.statusCode());
        response.prettyPrint();
    }

    @Test
    private void reqRes4(){
    RequestSpecification requestSpecification =  given().header("Content-type",ContentType.JSON);
   Response response =   requestSpecification.body(createBody()).log().all()
             .post(" https://reqres.in/api/register");
        Assert.assertEquals(response.getStatusCode(),200,"STATUS Code :"+response.getStatusCode());


    }

    private String createBody(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("FirstName","Praveen");
        jsonObject.put("LastName","Mayakar");
        jsonObject.put("email", "eve.holt@reqres.in");
        jsonObject.put( "password","265166");
        jsonObject.put("Occupation","Engineer");
        jsonObject.put("age",31);
        //Education
        JSONArray educationArray = new JSONArray();
        JSONObject eduObj = new JSONObject();
        eduObj.put("Primary(1-7)","GARAG");
        eduObj.put("HighSchool(8-10)","DWD");
        eduObj.put("Diploma","Hulkoti");
        eduObj.put("Engineering","Ujire");
        educationArray.put(eduObj);
        jsonObject.put("EDUCATION",educationArray);
        //Family
        JSONArray family = new JSONArray();
        JSONObject fmembers = new JSONObject();
        JSONObject member1 = new JSONObject();
        member1.put("Name","Sushila");
//        member1.put("Relation Type","Mother");
        member1.put("Age",59);
        member1.put("Occupation","Teacher");

        JSONObject member2 = new JSONObject();
        member2.put("Name","Pratibha");
//        member1.put("Relation Type","Sister");
        member2.put("Age",27);
        member2.put("Occupation","Doctor");

        JSONObject member3 = new JSONObject();
        member3.put("Name","Bharati");
//        member3.put("Relation Type","Wife");
        member3.put("Age",27);
        member3.put("Occupation","Engineer");

        fmembers.put("Mother",member1);
        fmembers.put("Sister",member2);
        fmembers.put("Wife",member3);
        family.put(fmembers);

        jsonObject.put("Family Details",family);

       return jsonObject.toString();
    }
}
