/*
 * @author <cclem054@uottawa.ca>
 * @version 1.0
 * (c) Copyright 2017 Cédric Clément.
 */
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.xml.bind.DatatypeConverter;
import java.util.Base64;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class SimpleIO{
    
    public static void main(String[] args) throws IOException{

    }

    /**
     * Encodes a string into base64 encoding.
     * @param string content The string to encode.
     * @return string The base64 encoded string.
     */
    public static String toBase64(String content){
        byte[] encoded = Base64.getEncoder().encode(content.getBytes());
        return new String(encoded);
    }
    
    /**
     * Decodes a base64 encoded string.
     * @param string content The base64 encoded string to decode.
     * @return string Decoded base64 string.
     */
    public static String fromBase64(String content){
        byte[] decoded = Base64.getDecoder().decode(content);
        return new String(decoded);
    }

    /**
     * Gets the content of a file.
     * @param string path The path of the file to read the content from.
     * @return string The content read from the file.
     */
    public static String readStringFromFile(String path) throws IOException, FileNotFoundException{
        List<String> content = Files.readAllLines(Paths.get(path));
        String ret           = String.join("\n", content);
        return ret;
    }
    
    public static ArrayList<String> readStringsFromFile(String path) throws IOException, FileNotFoundException {
    	return (ArrayList<String>) Files.readAllLines(Paths.get(path));
    }
    
    /**
     * Writes content to a file.
     * @param string path The path of the file to write to.
     * @param string content The content to write to the file.
     * @return void
     */
    public static void writeStringToFile(String path, String content) throws IOException{
        FileOutputStream fos = new FileOutputStream(path);
        fos.write(content.getBytes());
        fos.close();
    }
    
    public static void writeStringsToFile(String path, List<String> content) throws IOException{
    	FileWriter writer = new FileWriter(path);
    	for(String s : content) {
    		writer.write(s + System.lineSeparator());
    	}
    	writer.close();
    }
    
    public static void appendStringToFile(String path, String content) throws IOException{
    	File f = new File(path);
    	
    	if(!f.exists()) 
    		f.createNewFile();
    	
    	FileWriter fw = new FileWriter(path, true);
    	BufferedWriter bw = new BufferedWriter(fw);
    	bw.write(content);
    	bw.close();
    }
    
   
    
    public static void clear(String path) throws IOException {
    	File f = new File(path);
    	PrintWriter writer = new PrintWriter(f);
    	writer.print("");
    	writer.close();
    }
    
    /**
     * Writes content to a file
     * @param String path
     * @param byte[] content
     * @return void
     */
    public static void writeBytesToFile(String path, byte[] content) throws IOException{
        FileOutputStream fos = new FileOutputStream(path);
        fos.write(content);
        fos.close();
    }

    /**
     * Gets the content of a file.
     * @param string path The path of the file to read the content from.
     * @return byte[] The content read from the file.
     */
    public static byte[] readBytesFromFile(String path) throws IOException {
        byte[] content = Files.readAllBytes(Paths.get(path));
        return content;
    }    






    public static void writeBytes(File output, byte[] toWrite) throws IOException{
        System.out.println("Writting "+ toWrite.length +" bytes to file " + output.getName() );
        FileOutputStream fos = new FileOutputStream(output);
        fos.write(toWrite);
        fos.flush();
        fos.close();
    }
    
    
    public static byte[] readBytes(File f) throws IOException{
        FileInputStream fis = new FileInputStream(f);
        byte[] fbytes = new byte[(int) f.length()];
        fis.read(fbytes);
        fis.close();
        return fbytes;
    }
    
    public static void writeBytes(String path, byte[] toWrite) throws IOException {
        File file = new File(path);
        SimpleIO.writeBytes(file, toWrite);
        // FileOutputStream fos = new FileOutputStream(file);
        // fos.write(toWrite);
        // fos.flush();
        // fos.close();
    }
    
    public static void writeContent(String path, String content) throws IOException{
        File file = new File(path);
        SimpleIO.writeBytes(file, content.getBytes());
    }
    
    public static byte[] readBytes(String path) throws IOException{
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        byte[] fbytes = new byte[(int) file.length()];
        fis.read(fbytes);
        fis.close();
        return fbytes;
    }
    
    public static void writeContent(String path, byte[] toWrite) throws IOException{
        File file = new File(path);
        SimpleIO.writeBytes(file, toWrite);
    }
    
    public static String readContent(String path) throws IOException{
        File file = new File(path);
        byte[] contents = SimpleIO.readBytes(file);
        return new String(contents);
    }




    
}