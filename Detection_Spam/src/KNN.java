import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.*;


public class KNN {
	
	
	HashMap<String[], Double> sortedK;
	protected static HashMap<String, ArrayList<String>> invertedIndex_Ham; //inverted index basé sur les courriels ham
	protected static HashMap<String, ArrayList<String>> invertedIndex_Spam; //inverted index basé sur les courriels ham
	
	protected static HashMap<Set, Double> distance = new HashMap<Set, Double>(); 
	
	protected static ArrayList<String[]> statistics = new ArrayList<String[]>();
    
	
	public KNN() { }
	
	public KNN(HashMap<String, ArrayList<String>> invertedIndex_Ham, HashMap<String, ArrayList<String>> invertedIndex_Spam) {
		this.invertedIndex_Ham = invertedIndex_Ham;
		this.invertedIndex_Spam = invertedIndex_Spam;
	    this.statistics.add(new String[] {"test_courriel_ID",
	    		"training_courriel_ID",
	    		"distance",
	    		"type"});
	}
	
	
	
	public HashMap<Set, Double> getDistance(){
		return distance;
	} 
	
	/*
	 * courriel1_ID: test
	 * courriel2_ID: training
	 * */
	public void getTable(String type, String courriel1_ID, String courriel2_ID, ArrayList<String> email1, ArrayList<String> email2) {

		Set<String> uniquewords = new HashSet<String>();
		Set<String> tokens_courriel1 = new HashSet<String>();
		Set<String> tokens_courriel2 = new HashSet<String>();
		
		int nb_tokens_courriel1, nb_tokens_courriel2, nb_intersection;
		
		
		for (int i=0; i<email1.size(); i++) {
			uniquewords.add(email1.get(i)); //ajoute le token dans l'ensemble (set) du courriel 1
			tokens_courriel1.add(email1.get(i));
		}
		
		for (int i=0; i<email2.size(); i++) {
			uniquewords.add(email2.get(i)); //ajoute le token dans l'ensemble (set) du courriel 2
			tokens_courriel2.add(email2.get(i));
		}
		
		nb_tokens_courriel1 = tokens_courriel1.size(); //nombre total de tokens dans courriel 1
		nb_tokens_courriel2 = tokens_courriel2.size(); //nombre total de tokens dans courriel 2
		
		tokens_courriel1.retainAll(tokens_courriel2); //tokens_courriel1: contient l'INTERSECTION des 2 ensembles
		nb_intersection = tokens_courriel1.size();
		
		int[] email1_10 = new int[uniquewords.size()+1];
		int[] email2_10 = new int[uniquewords.size()+1];
		
		/*
		 * On sait qu'il y aura des "1" dans les tableaux pour les mots pareils
		 * */
		int index = 0;
		for (int i=0; i< nb_intersection; i++) {
			email1_10[i] = 1;
			email2_10[i] = 1;
			index = i;
		}
		
		//courriel 1
		for (int i=0; i<(nb_tokens_courriel1 - nb_intersection); i++) {
			email1_10[i+index+1] = 1;
			email2_10[i+index+1] = 0;
		}
		
		//courriel 2
		index = index + nb_tokens_courriel1 - nb_intersection;
		for (int i=0; i<(nb_tokens_courriel2 - nb_intersection); i++) {
			email1_10[i+index + 1] = 0;
			email2_10[i+index + 1] = 1;
		}
		
		Set<String> test_train_emailID = new HashSet<String>();
		test_train_emailID.add(courriel1_ID);
		test_train_emailID.add(courriel2_ID);
		distance.put(test_train_emailID, distance(email1_10, email2_10));
	
		
		statistics.add(new String[] { courriel1_ID, courriel2_ID, String.valueOf(distance(email1_10, email2_10)), type });
	
	}
	
	public void exportCSV() {
		try {
			SimpleIO.writeStringsToFile("./src/out/my.csv", statistics);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public double distance(int[] X1, int[] X2){
		int dims = X1.length;
		// String[] returnVector = new String[3]; // X1 X2 same class
		double distance = 0.0;
		
		for(int i = 0; i < dims; i++) {
			double a = X1[i];
			double b = X2[i];
			
			distance += (a - b) * (a - b);
		}
		
		return Math.sqrt(distance);
	}
	

	/**
	 * Calculates the Euclidean distances between two CSV rows, X1, X2.
	 * @param X1 sample1
	 * @param X2 sample2
	 * @return
	 */
	public double distance(String[] X1, String[] X2){
		int dims = X1.length;
		// String[] returnVector = new String[3]; // X1 X2 same class
		double distance = 0.0;
		
		for(int i = 0; i < dims; i++) {
			String a1 = X1[i];
			String b1 = X2[i];
			
			// skip last col since it is label
			if(i != dims - 1) {
				double a = Double.parseDouble(a1);
				double b = Double.parseDouble(b1);
				distance += (a - b) * (a - b);
			}
		}
		
		return Math.sqrt(distance);
	}
	
	/**
	 * Returns the K nearest neighbours to X1 in dataset.
	 * @param X1
	 * @param dataset
	 * @param K
	 * @return
	 */
	public HashMap<String[], Double> getNeighbors(String[] X1, String[][] dataset, int K) {
        ArrayList<Double> distances = new ArrayList<Double>();
        HashMap<String[], Double> results = new HashMap<String[], Double>();
		for(String[] xi : dataset) {
        	double d = this.distance(X1, xi);
        	results.put(xi, d);
        	distances.add(d);
        }
		
		this.sortedK = results.entrySet()
			.stream()
			.sorted(Map.Entry.comparingByValue())
			.limit(K)
			.collect(
					Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, HashMap::new));
		
		return this.sortedK;
	}
	
	/**
	 * 
	 * @param X1
	 * @param dataset
	 * @param K
	 * @return
	 */
	public HashMap<String[], Double> getNeighbors(String[] X1, ArrayList<String[]> dataset, int K) {
		ArrayList<Double> distances = new ArrayList<Double>();
		HashMap<String[], Double> results = new HashMap<String[], Double>();
		for(String[] xi : dataset) {
			double d = this.distance(X1, xi);
			results.put(xi, d);
			distances.add(d);
		}
		
		return this.sortedK = results.entrySet()
				.stream()
				.sorted(Map.Entry.comparingByValue())
				.limit(K)
				.collect(Collectors.toMap(Map.Entry::getKey,  Map.Entry::getValue,(e1, e2) -> e2, HashMap::new));
		
	}
	
	/**
	 * 
	 * @param X1
	 * @param dataset
	 * @param K
	 * @return
	 */
	public KNN neighbors(String[] X1, ArrayList<String[]> dataset, int K) {
		ArrayList<Double> distances = new ArrayList<Double>();
		HashMap<String[], Double> results = new HashMap<String[], Double>();
		for(String[] xi : dataset) {
			double d = this.distance(X1, xi);
			results.put(xi, d);
			distances.add(d);
		}
		
		this.sortedK = results.entrySet()
				.stream()
				.sorted(Map.Entry.comparingByValue())
				.limit(K)
				.collect(Collectors.toMap(Map.Entry::getKey,  Map.Entry::getValue,(e1, e2) -> e2, HashMap::new));
		
		return this;
		
	}
	
	/**
	 * Predicts the class that input record belongs to.
	 * @param X1
	 * @return
	 */
	public String predict(String[] X1) {
		int same = 0, different = 0;

		int labelIndex = X1.length -1;
		String label = X1[labelIndex];
		
		for(Map.Entry<String[], Double> entry : this.sortedK.entrySet()) {
			String[] row = entry.getKey();
			Double value = entry.getValue();
			if(row[labelIndex].equals(label)) {
				same++;
			} else {
				different++;
			}
			
		}
		
		// Gather collection of label counts
		Map<Object, Long> labelCounts = this.sortedK.keySet()
				.stream()
				.collect(Collectors.groupingBy(e->e[labelIndex], Collectors.counting()));
		
		// The actual prediction, simply the label that was used most often
		String actualPrediction = (String) Collections.max(labelCounts.entrySet(), 
									Comparator.comparingLong(Map.Entry::getValue)).getKey();
		
		String predictedLabel = same > different ? label : actualPrediction; 
		return predictedLabel;
	}

}
