import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.util.Comparator;

public class SpamDetector {
    
	public static void main(String[] args) throws IOException{
        CatalogManager manager = new CatalogManager();
        HashMap<String, List<String>> spam = manager.GetContent("spam");
        HashMap<String, List<String>> ham  = manager.GetContent("ham");
        HashMap<String, List<String>> stops = manager.GetContent("stopwords");
        List<String> stopwords = stops.get("english.txt");
        List<String> sample_ham = ham.get("2186.9dc59321a95e53d5e0ebaf3524858913");
        
        PorterStemmer stemmer = new PorterStemmer();
        for(String s : sample_ham) {
        	String stemmed = stemmer.stemWord(s);
        	System.out.println(s + " " +stemmed + " ");
        }

        
        HashMap<String, Integer> occurences = manager.getOccurences(spam, false);
        
        occurences.values()
        	.stream()
        	.sorted(Comparator.reverseOrder())
        	.forEach(System.out::println);
        
        occurences.entrySet()
    		.stream()
    		.forEach(set -> {
    			String word = set.getKey();
    			Integer numOccurences = set.getValue();
    			String out = word + ", " + numOccurences;
    			System.out.println(out);
    		});
        
        
    }
}