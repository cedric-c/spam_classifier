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
    
    private static String PATH_BALANCED = "./src/data/DossierA_Classe_Balancee/";
    private static String PATH_UNDERSHAMPLED = "./src/data/DossierB_Undersampling_Ham/";
    private static String PATH_OVERSHAMPLED = "./src/data/DossierC_Oversampling_Ham/";
    
    private static String PATH_NON_CLASSIFIED = "./src/test_data_CAT/ham-and-spam-dataset/test_set"; //MODIFIER: l'ensemble test qui n'a pas été classifié
    private static String PATH_ENG_STOPWORDS = "./src/data/stopwords/";

    private HashMap<String, String> fileDirectories;

    
    public CatalogManager(int cas) {
    	if (cas == 1) {
    		/*
    		 * Cas 1: DossierA_Classe_Balancee
    		 * 		=> 400 hams, 400 spams, 80  courriels test
    		 * */
            this.fileDirectories = new HashMap<String, String>();
            this.addDirectory("ham", "./src/data/DossierA_Classe_Balancee/ham_2500/");
            this.addDirectory("spam", "./src/data/DossierA_Classe_Balancee/spam_400/");
            this.addDirectory("test", "./src/data/DossierA_Classe_Balancee/test_80/"); 
            this.addDirectory("stopwords", PATH_ENG_STOPWORDS);
            
    	} else if (cas == 2) {
    		/*
    		 * Cas 2: DossierB_Undersampling_Ham
    		 * 		=> 100 hams, 460 spams, 80  courriels test
    		 * */
            this.fileDirectories = new HashMap<String, String>();
            this.addDirectory("ham", "./src/data/DossierB_Undersampling_Ham/ham_100/");
            this.addDirectory("spam", "./src/data/DossierB_Undersampling_Ham/spam_460/");
            this.addDirectory("test", "./src/data/DossierB_Undersampling_Ham/test_80/"); 
            this.addDirectory("stopwords", PATH_ENG_STOPWORDS);
            
    	} else if (cas == 3) {
    		/*
    		 * Cas 3: DossierC_Oversampling_Ham
    		 * 		=> 2 500 hams, 400 spams, 80  courriels test
    		 * */
            this.fileDirectories = new HashMap<String, String>();
            this.addDirectory("ham", "./src/data/DossierC_Oversampling_Ham/ham_2500/");
            this.addDirectory("spam", "./src/data/DossierC_Oversampling_Ham/spam_400/");
            this.addDirectory("test", "./src/data/DossierC_Oversampling_Ham/test_80/"); 
            this.addDirectory("stopwords", PATH_ENG_STOPWORDS);
    		
    	} else {
            this.fileDirectories = new HashMap<String, String>();
            this.addDirectory("ham", PATH_HAM);
            this.addDirectory("spam", PATH_SPAM);
            this.addDirectory("stopwords", PATH_ENG_STOPWORDS);
            this.addDirectory("test", PATH_NON_CLASSIFIED); 
    	}
    }
    
    public CatalogManager() {
        this.fileDirectories = new HashMap<String, String>();
        this.addDirectory("ham", PATH_HAM);
        this.addDirectory("spam", PATH_SPAM);
        this.addDirectory("stopwords", PATH_ENG_STOPWORDS);

        this.addDirectory("test", PATH_NON_CLASSIFIED); 
        this.addDirectory("test", PATH_NON_CLASSIFIED); //MODIFIER: ajouter le chemin pour les données test
        
        // Balanced data
        this.addDirectory("ham_400", PATH_BALANCED + "ham_400");
        this.addDirectory("spam_400_a", PATH_BALANCED + "spam_400");
        this.addDirectory("test_80", PATH_BALANCED + "test_80");
        
        // Undersampled data 
        this.addDirectory("ham_100", PATH_UNDERSHAMPLED + "ham_100");
        this.addDirectory("spam_460", PATH_UNDERSHAMPLED + "spam_460");
        
        // Oversampled data 
        this.addDirectory("ham_2500", PATH_OVERSHAMPLED + "ham_2500");
        this.addDirectory("spam_400_b", PATH_OVERSHAMPLED + "spam_400");
        
        // (ham_400, spam_400_a), (ham_100, spam_460), (ham_2500, spam_400_b)
        
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
            throw new IOException("Path does not exist: " + directory);

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