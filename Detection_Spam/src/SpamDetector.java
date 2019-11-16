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
        List<String> sample_ham = ham.get("2186.9dc59321a95e53d5e0ebaf3524858913");
        
        PorterStemmer stemmer = new PorterStemmer();
        for(String s : sample_ham) {
        	String stemmed = stemmer.stemWord(s);
        	System.out.println(s + " " +stemmed + " ");
        }
        
        System.out.println("Hello World");
    }
}