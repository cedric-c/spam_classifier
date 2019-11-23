import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class SpamDetector {
    
	/**
	 * Run k-NN with Spam and Ham emails
	 * @throws IOException
	 */
	public static void KNNSpam() throws IOException {
        
        CatalogManager manager = new CatalogManager();
        HashMap<String, ArrayList<String>> spam = manager.getMap("spam");
        HashMap<String, ArrayList<String>> ham  = manager.getMap("ham");
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
        csv.load().tabulate(true);
        HashMap<Double, ArrayList<String[]>> data = csv.split(trainingSize, testingSize);
        ArrayList<String[]> training = data.get(trainingSize);
        ArrayList<String[]> testing  = data.get(testingSize);
        
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
	
	public static void main(String[] args) {
		
		System.exit(0);
		
		String link = "./src/data/iris/iris.csv";
		
		Double[] setSize = new Double[] {.1, .2, .3, .4, .5, .6, .7, .8, .9};
		Integer[] KSize = new Integer[] {3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
		

		
		for(int i = 0; i < setSize.length; i++) {
			for(int j = 0; j < 40; j++) {
				for(int o : KSize) {
					Double train = setSize[i];
					Double test = setSize[setSize.length - i -1];
					ArrayList<String[]> result = KNNIris(link, train, test, o);
					String b64 = SimpleIO.toBase64(String.valueOf(System.nanoTime()));
					String filename = b64 + ".csv";
//					writeTest("./src/out/t/knn/" + filename, result);
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
						SimpleIO.appendStringToFile("./src/out/iris_aggregate2.csv", sb.toString());						
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