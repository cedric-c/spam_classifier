import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class SpamDetector {
    
	public static void main(String[] args) throws IOException{
        CatalogManager manager = new CatalogManager();
        HashMap<String, ArrayList<String>> spam = manager.getMap("spam");
        HashMap<String, ArrayList<String>> ham  = manager.getMap("ham");
        //HashMap<String, ArrayList<String>> test  = manager.getMap("test"); //MODIFIER: ajouter cette ligne
        HashMap<String, ArrayList<String>> stops = manager.getMap("stopwords");
        List<String> englishStopwords = stops.get("english.txt");
        List<String> emailStopwords = stops.get("email.txt");
        List<String> sample_ham = ham.get("2186.9dc59321a95e53d5e0ebaf3524858913");
        
        PorterStemmer stemmer = new PorterStemmer();

//        for(String s : sample_ham) {
//        	String stemmed = stemmer.stemWord(s);
//        	System.out.println(s + " " +stemmed + " ");
//        }

        CSVLoader csv = new CSVLoader("./src/data/iris/iris.csv");
        csv.load().tabulate(true);
        String[] row1 = csv.get(0);
        
        KNN knn = new KNN();
        
//        for(String[] xi : csv.data()) {
//        	double d = knn.distance(row1, xi);
//        	System.out.println("distance is " + d);
//        }
        
        // Note that nearestNeighbors are not sorted
        HashMap<String[], Double> nearestNeighbors = knn.getNeighbors(row1, csv.data(), 5);
        knn.predict(row1);
        System.out.println("hello");
        System.exit(0);
        
        HashMap<String, Integer> occurences = manager.getOccurences(spam, false);
		OutputOccurencesCSV("./src/out/spam_occurences.csv", occurences);
		
        HashMap<String, Integer> occurences_stemmed = manager.getOccurences(spam, true);
		OutputOccurencesCSV("./src/out/spam_occurences_s.csv", occurences);

		// Imprimer les occurences (value) qui apparaissent plus de 3000 fois
		System.out.println("\nOccurences >= 3000 :");
        occurences.values()								// l'objet est le Integer
        	.stream()
        	.sorted(Comparator.reverseOrder())
        	.filter(count -> count.intValue() >= 3000)
        	.forEach(System.out::println);
		
		// Imprimer les occurences (key=value) qui contiennent 'ing' et qui apparaissent plus de 100 fois
		System.out.println("\nOccurences >= 100 AND contains 'ing' :");
		occurences.entrySet()
			.stream()
			.filter(item -> item.getKey().contains("ing") && item.getValue() >= 100)
			.forEach(System.out::println);
        
        // Imprimer les occurences (key=value) qui apparaissent plus de 3000 fois
		System.out.println("\nOccurences >= 3000 :");
		occurences_stemmed.entrySet()					// l'objet est entrySet
			.stream()
			.filter(item -> item.getValue() >= 3000)
			.forEach(System.out::println);
		

		// Stopword filters
		System.out.println("\nOccurences >= 3000 AND does NOT contain stopwords from 'english.txt':");
		occurences.entrySet()
		 	.stream()
		 	.filter(item -> !englishStopwords.contains(item.getKey())) // see stopwords above
		 	.filter(item -> item.getValue() > 3000)
		    .forEach(System.out::println);
		
		System.out.println("\nOccurences >= 200 AND no stopwords from 'english.txt' or 'email.txt'");
		occurences.entrySet()
	 		.stream()
	 		.filter(item -> !englishStopwords.contains(item.getKey())) // see stopwords above
	 		.filter(item -> !emailStopwords.contains(item.getKey())) // see stopwords above
	 		.filter(item -> item.getValue() > 250)
	 		.filter(item -> item.getValue() < 350)
	 		.forEach(System.out::println);
    }
	
	/**	
	 * Save occurences of words and the number of times they appear to a file.
	 * @param filename
	 * @param map
	 * @throws IOException
	 * @example OutputOccurencesCSV("./src/out/spam.csv", map)
	 */
	public static void OutputOccurencesCSV(String filename, HashMap<String, Integer> map) throws IOException{
        System.out.println("Saving data to " + filename);
        SimpleIO.clear(filename);
        SimpleIO.appendStringToFile(filename, "word|occurences\n");
        map.entrySet()
    		.stream()
    		.forEach(set -> {
    			String word = set.getKey();
    			Integer numOccurences = set.getValue();
    			try {
    				
    				if(word.contains("|")) {
    					word = "\"" + word + "\""; 
    				}
    				
    				SimpleIO.appendStringToFile(filename, word +" | "+numOccurences + "\n");
    			} catch(Exception e) {
    			}
    		});
        System.out.println("Data saved.");
	}
}