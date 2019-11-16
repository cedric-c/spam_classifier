import java.util.HashMap;
import java.util.List;
import java.io.IOException;

public class SpamDetector {
    
	public static void main(String[] args) throws IOException{
        CatalogManager manager = new CatalogManager();
        HashMap<String, List<String>> spam = manager.GetContent("spam");
        HashMap<String, List<String>> ham  = manager.GetContent("ham");
        HashMap<String, List<String>> stopwords = manager.GetContent("stopwords");
        List<String> words = stopwords.get("english.txt");
        System.out.println("Hello World");
    }
}