import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class SpamDetector {
    public static void main(String[] args) throws IOException{
        ListFiles();
    }
    
    public static void ListFiles() throws IOException{
        System.out.println("Hello World!");
        Files.list(Paths.get("../../data"))
            .forEach(System.out::println);
        
    }



}