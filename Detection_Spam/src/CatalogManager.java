import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.StringTokenizer;

public class CatalogManager {
    private static String PATH_HAM = "./src/data/ham-and-spam-dataset/ham/";
    private static String PATH_SPAM = "./src/data/ham-and-spam-dataset/spam/";
    private static String PATH_NON_CLASSIFIED = "./src/test_data_CAT/ham-and-spam-dataset/test_set"; //MODIFIER: l'ensemble test qui n'a pas été classifié
    private static String PATH_ENG_STOPWORDS = "./src/data/stopwords/";

    private HashMap<String, String> fileDirectories;

    public CatalogManager() {
        this.fileDirectories = new HashMap<String, String>();
        this.addDirectory("ham", PATH_HAM);
        this.addDirectory("spam", PATH_SPAM);
        this.addDirectory("stopwords", PATH_ENG_STOPWORDS);
        this.addDirectory("test", PATH_NON_CLASSIFIED); //MODIFIER: ajouter le chemin pour les données test
    }

    /**
     * 
     * @param key
     * @param path
     */
    public void addDirectory(String key, String path) {
        this.fileDirectories.put(key, path);
    }

    /**
     * Returns a hashmap of the filecontents.
     * @param directory
     * @return
     * @throws IOException
     */
    public HashMap<String, ArrayList<String>> getMap(String directory) throws IOException{
        String path = this.fileDirectories.get(directory);
        if(path == null)
            throw new IOException("Path does not exist!");

        Path p = Paths.get(path);
        
        ArrayList<Path> paths = Files.list(p)
        		.collect(Collectors.toCollection(ArrayList::new));
        		
        HashMap<String, ArrayList<String>> contents = new HashMap<String, ArrayList<String>>();
        for(Path f : paths) {
        	try {
        		ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(f, Charset.forName("Cp1252"));
        		ArrayList<String> allTokens = new ArrayList<String>();
        		for(String line : lines) {
        			StringTokenizer tokens = new StringTokenizer(line);
        			while(tokens.hasMoreTokens()) {
        				allTokens.add(tokens.nextToken().toLowerCase().trim());
        			}
        		}
        		String name = f.getFileName().toString();
        		contents.put(name, allTokens);
        	}catch(Exception e) {
        	}
        	
        }
        return contents;

    }
    
    /**
     * Returns records, up to a maximum of limit
     * @param directory
     * @param limit
     * @return
     * @throws IOException
     */
    public HashMap<String, ArrayList<String>> getMap(String directory, int limit) throws IOException{
        return this.getMap(directory)
        	.entrySet()
        	.stream()
        	.limit(limit)
        	.collect(
    				Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, 
    						(e1, e2) -> e2, HashMap::new)
    		);

    }
    
    /**
     * Return a hash map of words and the number of times they appear in map.
     * @param map
     * @param stemmed
     * @return
     */
    public HashMap<String, Integer> getOccurences(HashMap<String, ArrayList<String>> map, boolean stemmed) {
        HashMap<String, Integer> occurences = new HashMap<String, Integer>();
        
        PorterStemmer stemmer = new PorterStemmer();
        
        for(Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
        	String filename = entry.getKey();
        	ArrayList<String> words = entry.getValue();
        	words.forEach(word -> {
        		String stemmedWord = stemmer.stemWord(word);
        		String w = stemmed ? stemmedWord : word; 
        		if(occurences.containsKey(w)) {
        			occurences.put(w, occurences.get(w) + 1);
        		} else {
        			occurences.put(w, 1);
        		}
        	});
        }
        return occurences;
        
    }
    
}