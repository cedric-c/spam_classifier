import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.io.IOException;

public class SpamDetector {
    public static void main(String[] args) throws IOException{
        ListFiles();
    }
    
    public static void ListFiles() throws IOException{
        CatalogManager manager = new CatalogManager();
        HashMap<String, List<String>> spam = manager.GetContent("spam");
        HashMap<String, List<String>> ham  = manager.GetContent("ham");
        System.out.println("Hello World");
        
        
    }



}