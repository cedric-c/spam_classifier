import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SpamDetector {
    
	/**
	 * Run k-NN with Spam and Ham emails
	 * @throws IOException
	 */
	public static void KNNSpam() throws IOException{
        
        CatalogManager manager = new CatalogManager();
        HashMap<String, ArrayList<String>> spam = manager.getMap("spam", 300);
        HashMap<String, ArrayList<String>> ham  = manager.getMap("ham", 300);
        HashMap<String, ArrayList<String>> all  = manager.getMap("all");
        HashMap<String, ArrayList<String>> test  = manager.getMap("test"); //MODIFIER: ajouter cette ligne
        HashMap<String, ArrayList<String>> stops = manager.getMap("stopwords");
        List<String> englishStopwords = stops.get("english.txt");
        List<String> emailStopwords = stops.get("email.txt");
        List<String> sample_ham = ham.get("2186.9dc59321a95e53d5e0ebaf3524858913");
        
        PorterStemmer stemmer = new PorterStemmer();
        
//        for(String s : sample_ham) {
//        	String stemmed = stemmer.stemWord(s);
//        	System.out.println(s + " " +stemmed + " ");
//        }
        
        HashMap<String, Integer> occurences = manager.getOccurences(spam, false);
//		OutputOccurencesCSV("./src/out/spam_occurences.csv", occurences);
		
        HashMap<String, Integer> occurences_stemmed = manager.getOccurences(spam, true);
        HashMap<String, Integer> o_spam = manager.getOccurences(spam, true);
        HashMap<String, Integer> o_ham = manager.getOccurences(ham, true);
        
        Set<String> spamWords = o_spam.keySet();
        Set<String> hamWords  = o_ham.keySet();
        ArrayList<String> allWords = new ArrayList<String>();
        allWords.addAll(spamWords);
        allWords.addAll(hamWords);
        Collections.shuffle(allWords);
        
        HashMap<String, ArrayList<String>> smallAll = new HashMap<String, ArrayList<String>>();
        smallAll.putAll(spam);
        smallAll.putAll(ham);
//        smallAll.addAll(hamWords);
        
        HashMap<String, String[]> index = new HashMap<String, String[]>();
//        for(Map.Entry<String, ArrayList<String>> email : all.entrySet()) {
        for(Map.Entry<String, ArrayList<String>> email : smallAll.entrySet()) {
        	
        	String key = email.getKey();
        	System.out.println("Processing email " + key);
        	ArrayList<String> content = email.getValue();
        	
        	// Check if spam or ham
        	boolean isHam = ham.containsKey(key) ? true : false;
        	index.put(key, GetOccurences(content, allWords, isHam));
        }
        
        HashMap<String, ArrayList<String>> invertedIndex;
        
        try {
        	main a = new main();
        	
        	invertedIndex = a.ConstructInvertedIndex(spam);
        	System.out.println("");
        } catch(Exception e) {
        	e.printStackTrace();
        }
//        ArrayList<String[]> v = index.entrySet().stream().collect(collector)
//        writeTest("./src/out/major.csv", index);
        
        System.out.println("All emails scrubbed!");
        
        
        for(Map.Entry<String, String[]> records : index.entrySet()) {
        	String key = records.getKey();
        	String[] data = records.getValue();
        	SimpleIO.appendStringToFile("./src/out/major.csv", data);
        }
        
//        spamWords.
        
//		OutputOccurencesCSV("./src/out/spam_occurences_s.csv", occurences);

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
	
	public static String[] GetOccurences(ArrayList<String> email, ArrayList<String> words, boolean isHam) {
		ArrayList<String> r = new ArrayList<String>();
		
		for(String s : words) {
			String v = email.contains(s) ? "1" : "0";
			r.add(v);
		}
		
		String cls = isHam? "ham" : "spam";
		r.add(cls);
		
		return r.toArray(new String[r.size()]);
	}
	
	/**	
	 * Save occurences of words and the number of times they appear to a file.
	 * @param filename
	 * @param map
	 * @throws IOException
	 * @example OutputOccurencesCSV("./src/out/spam.csv", map)
	 */
	private static void OutputOccurencesCSV(String filename, HashMap<String, Integer> map) throws IOException{
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
	
	/**
	 * Run k-NN with the Iris flower data.
	 */
	public static ArrayList<String[]> KNNIris(String linkToFeaturedData, double trainingSize, double testingSize, int K) {
        
		System.out.printf("Starting k-NN trainingSize %f, testingSize %f, K=%d\n",
				trainingSize,
				testingSize,
				K);
		
		// Load prepared data, split into test set and train set
        CSVLoader csv = new CSVLoader(linkToFeaturedData);
        csv.load().tabulate(false); // should be moved to param, ignore header in file
        // need to parameterize wordlists
        HashMap<Double, ArrayList<String[]>> data = csv.split(trainingSize, testingSize);
        ArrayList<String[]> training = data.get(trainingSize);
        ArrayList<String[]> testing  = data.get(testingSize);
        System.out.println("Training size is " +training.size() + "\nTesting size is "+testing.size());
        
        ArrayList<String[]> statistics = new ArrayList<String[]>();
        statistics.add(new String[] {"testno",
        		"time",
        		"prediction",
        		"actual",
        		"K",
        		"trainingSize",
        		"testingSize",
        		"correct"
        		});
        
        // Run k-NN
        KNN knn = new KNN();
        int testno = 0;
        
        
        System.out.println("Making predictions");
        for(String[] sample : testing) {
        	String prediction = knn.neighbors(sample, training, K)
        		.predict(sample);
        	statistics.add(new String[] {
            		String.valueOf(testno),
            		String.valueOf(System.currentTimeMillis()),
            		prediction,
            		sample[sample.length - 1 ],
            		String.valueOf(K),
            		String.valueOf(trainingSize),
            		String.valueOf(testingSize),
            		prediction.equals(sample[sample.length -1]) ? "1" : "0"
            });
        	testno++;
        }
        System.out.println("k-NN Done!");
        return statistics;
	}
	
	public static void Catherine() {
		try {
			main a = new main();
			HashMap<String, HashMap<String, ArrayList<String>>> maps = a.Cedric();
			HashMap<String, ArrayList<String>> iSpam = maps.get("iSpam");
			HashMap<String, ArrayList<String>> iHam = maps.get("iHam");
			System.out.println("Hello World!");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
//	public static void 
	
	public static void main(String[] args) throws Exception{

//		Catherine();
//		KNNSpam();
//		System.exit(0);
		
		String link = "./src/out/major.csv";
//		String link = "./src/data/iris/iris.csv";
		
		Double[] setSize = new Double[] {.1, .2, .3, .4, .5, .6, .7, .8, .9};
		Integer[] KSize = new Integer[] {3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
		

		
		for(int i = 0; i < setSize.length; i++) {
			for(int j = 0; j < 10; j++) { // number if tries
				for(int o : KSize) {
					System.out.println("Running iteration " + j);
					Double train = setSize[i];
					Double test = setSize[setSize.length - i -1];
					ArrayList<String[]> result = KNNIris(link, train, test, o);
					String b64 = SimpleIO.toBase64(String.valueOf(System.nanoTime()));
					String filename = b64 + ".csv";
					writeTest("./src/out/t/knn/spamnham/" + filename, result);
					int sum = result.stream()
						.filter(s -> !s[0].equals("testno")) // remove the first row
						.mapToInt(s -> Integer.parseInt(s[s.length-1])).sum();
					StringBuilder sb = new StringBuilder();
					sb.append(o + ",");
					sb.append(train + ",");
					sb.append(test + ",");
					double v = ((double) sum / (double) result.size());
					sb.append(String.format("%.2f", v));
					sb.append("\n");
					System.out.println(sum + " " + result.size());
					try {
						SimpleIO.appendStringToFile("./src/out/spamHam.csv", sb.toString());
//						SimpleIO.appendStringToFile("./src/out/iris_aggregate2.csv", sb.toString());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}


	}
	
	public static void writeTest(String name, ArrayList<String[]> result) {
		try {
			SimpleIO.writeStringsToFile(name, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}