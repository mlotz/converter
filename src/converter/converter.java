package converter;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class converter {
    public static int PRETTY_PRINT_INDENT_FACTOR = 4;
    public static String INPUT_PATH = "input.xml";
    public static String OUTPUT_PATH = "output.json";
    public static Boolean DEBUG = true;

    static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }


    public static void main(String[] args){
        if (DEBUG) System.out.println("Accessing XML file: "+INPUT_PATH);

        String content = "<?xml version=\"1.0\" ?><test attrib=\"moretest\">Empty. Something went wrong on read.</test>";
        String jsonConvertedString = "Empty. Something went wrong on conversion.";
        try {
            content = readFile(INPUT_PATH, StandardCharsets.UTF_8);
        }
        catch (IOException ex){
            System.out.println (ex.toString());
            System.out.println("Could not find file: "+INPUT_PATH);
        }
        if (DEBUG) System.out.println("Converting to JSON...");
        //CONVERTER PART\/
        try {
            JSONObject xmlJSONObj = XML.toJSONObject(content);
            jsonConvertedString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
            //System.out.println(jsonPrettyPrintString);
        } catch (JSONException je) {
            System.out.println(je.toString());
        }
        //CONVERTER PART^^^
        if (DEBUG) System.out.println("Writing to file: "+OUTPUT_PATH);
        try(  PrintWriter outWriter = new PrintWriter( OUTPUT_PATH )  ){
            outWriter.println( jsonConvertedString );
        } catch (FileNotFoundException ex) {
            System.out.println("Write file not found: "+OUTPUT_PATH);
        }
        if (DEBUG) System.out.println("Done.");
    }


}
