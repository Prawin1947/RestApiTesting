import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class ReadArgusJsonFile {

// In some cases the data will be stored in the form of Json So at that time to find the count of few elements from that json
//    Need to iterate each object and get the specified field value

   // JsonFactory::
//The main factory class of Jackson package, used to configure
//and construct reader (aka parser, JsonParser) and writer (aka generator, JsonGenerator) instances.

    // MappingJsonFactory::
// Sub-class of JsonFactory that will create a
//proper ObjectCodec to allow seam-less conversions between JSON content and Java objects (POJOs).


 @Test
    public static void runner(){
     String filePath = "src/test/resources/Argus/argusFile.json";
        readTheDataFromArrayOfJson(filePath,"family members","Name","Indrajit");
    }



public static void readTheDataFromArrayOfJson(String file,String mainNode,String nameField,String fmName){
try{
    JsonFactory jsonFactory = new MappingJsonFactory();
   JsonParser jsonParser = jsonFactory.createParser(new File(file));
    JsonToken jsonToken = jsonParser.nextToken();

    if(jsonToken == JsonToken.START_ARRAY){
        while(jsonParser.nextToken() !=JsonToken.END_ARRAY ){

          JsonNode node = jsonParser.readValueAsTree();
        JsonNode familyMemberNode =  node.get(mainNode);
        int fmNode = 0;
        while(fmNode<familyMemberNode.size()){
            JsonNode fmNodeIndexNode =  familyMemberNode.get(fmNode);
            String mamberName = fmNodeIndexNode.get(nameField).toString().replace("\"","");
            if(mamberName.equals(fmName)){
             int  age = Integer.parseInt(fmNodeIndexNode.get("Age").toString().replace("\"",""));
             String qualification = fmNodeIndexNode.get("Qualification").toString().replace("\"","");;
                System.out.println("Mamber Name :"+ mamberName);
                System.out.println("Age :"+ age);
                System.out.println("Qualification :"+ qualification);
            JsonNode siblingNode =   fmNodeIndexNode.get("Siblings");

            for(JsonNode jsonNode: siblingNode){
            boolean studying =  Boolean.parseBoolean(jsonNode.get("Studying").toString().replace("\"",""));
            boolean working =  Boolean.parseBoolean(jsonNode.get("Working").toString().replace("\"",""));
            if((studying & working) == true){
                String relation = jsonNode.get("Relation").toString().replace("\"","");
                System.out.println("The sibling relation who is working and Studying is "+ relation);
            }
            }

            }
            fmNode++;
        }

        }
    }

}catch (IOException io){

}

}

}
