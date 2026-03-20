package restAssuredTesting.serializationAndDeserialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.Test;


//Serialization : Converting a Java object → JSON / XML / bytes
//DeSerialization : Converting JSON / bytes → Java object
public class SerializationAndDeserializationExample {


    @Test
    private static void serializationDemo(){

        //Create a java objet
        Person person = new Person("praveen","M",33);

        //Create a objectmapper instance
        ObjectMapper objectMapper = new ObjectMapper();



        try{
            String jsonString = objectMapper.writeValueAsString(person);

            System.out.println("serialization -----");
            System.out.println(jsonString);
            System.out.println("                                         ");

            //Deserialization
            Person deserializedPersonObject = objectMapper.readValue(jsonString,Person.class);
            System.out.println("deserialization -----");
            System.out.println("FirstName : "+deserializedPersonObject.getFirstName());
            System.out.println("LastName : "+deserializedPersonObject.getLastName());
            System.out.println("Age : "+deserializedPersonObject.getAge());
        }catch (Exception e){

        }




    }
}
