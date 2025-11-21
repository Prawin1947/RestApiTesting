import io.restassured.http.Header;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class BasicTestClass {


    public void getCall(){
      Response response =  given().get("https://reqres.in/api/users");
       response.prettyPeek();
        System.out.println(response.header("content-Type"));
        System.out.println("222222222222222222222222222222");
        for( Header header : response.getHeaders()){
            System.out.println(header.getName() +"--------> "+ header.getValue());
        }
    }

    @Test
    public void postCall(){
        Response response = given()
                .header("Content-Type","application/json").body("{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"\n" +
                "}").post("https://reqres.in/api/users");
        response.prettyPeek();
    }

}
