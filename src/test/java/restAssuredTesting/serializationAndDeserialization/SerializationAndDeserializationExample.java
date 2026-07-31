package restAssuredTesting.serializationAndDeserialization;

import com.fasterxml.jackson.databind.JsonNode;
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

    @Test
    public void readAsJsonTree(){
        String jsonData = "{\n" +
                "  {\n" +
                "    \"Door Num\": \"#111\",\n" +
                "    \"SName\": \"Maykar\",\n" +
                "    \"Owner\": \"Sushila\",\n" +
                "    \"family members\": [\n" +
                "      {\n" +
                "        \"Member Id\": 1001,\n" +
                "        \"Name\": \"Praveen\",\n" +
                "        \"Gender\": \"Male\",\n" +
                "        \"Age\": 30,\n" +
                "        \"Qualification\": \"BE\",\n" +
                "        \"Siblings\":[\n" +
                "          {\n" +
                "          \"Have a Siblings\": true,\n" +
                "          \"Relation\": \"Sister\",\n" +
                "          \"Age\": 26,\n" +
                "            \"Studying\": true,\n" +
                "            \"Working\": false\n" +
                "          }  ]\n" +
                "      },\n" +
                "\n" +
                "      {\n" +
                "        \"Member Id\": 1002,\n" +
                "        \"Name\": \"Pratibha\",\n" +
                "        \"Gender\": \"Female\",\n" +
                "        \"Age\": 26,\n" +
                "        \"Qualification\": \"MD\",\n" +
                "        \"Siblings\":[\n" +
                "          {\n" +
                "            \"Have a Siblings\": true,\n" +
                "            \"Relation\": \"Brother\",\n" +
                "            \"Age\": 30,\n" +
                "            \"Studying\": false,\n" +
                "            \"Working\": true\n" +
                "          }  ]\n" +
                "      }\n" +
                "]\n" +
                "}";

        // Find the name of the person whose Qualification is BE

        ObjectMapper objectMapper = new ObjectMapper();
        try{
         JsonNode rootNode = objectMapper.readTree(jsonData);
         JsonNode familyNode = rootNode.get("family members");
         for(JsonNode node :familyNode){

             String qualification = node.get("Qualification").asText();
             if(qualification.equals("BE")){
                 int age = node.get("Age").asInt();
                 String name = node.get("name").asText();
             }


         }


        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
