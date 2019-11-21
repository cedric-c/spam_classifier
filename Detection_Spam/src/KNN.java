import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.*;

public class KNN {
	
	HashMap<String[], Double> sortedK;
	
	public KNN() {
		
	}
	
	/*
	 * email1: liste de tokens du courriel 1
	 * email2: liste de tokens du courriel 2
	 * INCOMPLET
	 * */
	public void getTable(String courriel1D, String courriel2D, ArrayList<String> email1, ArrayList<String> email2, HashMap<String, ArrayList<String>> invertedIndex) {

		Set<String> uniquewords = new HashSet<String>();
		
		for (int i=0; i<email1.size(); i++) {
			uniquewords.add(email1.get(i)); //ajoute le token dans l'ensemble (set) du courriel 2
		}
		
		for (int i=0; i<email2.size(); i++) {
			uniquewords.add(email2.get(i)); //ajoute le token dans l'ensemble (set) du courriel 2
		}
		
		String[] email1_10 = new String[uniquewords.size()];
		String[] email2_10 = new String[uniquewords.size()];; //1 0 1 0 1
		
		int i=0;
		for(Object object : uniquewords) {
		    String token = (String) object;
		    if(invertedIndex.get(token).contains(courriel1D)) {
		    	email1_10 [i] = "1";
		    }
		    
		    if(invertedIndex.get(token).contains(courriel2D)) {
		    	email1_10 [i] = "1";
		    }
		    
		    i++;
		}
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
