import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class KNN {
	
	HashMap<String[], Double> sortedK;
	
	public KNN() {
		
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
	 * Finds the K closest neighbors to X1.
	 * @param X1 The candidate that is being compared.
	 * @param dataset The pool of possible neighbors to choose from.
	 * @param K The number of neighbours to choose.
	 * @return
	 */
	public KNN neighbors(String[] X1, ArrayList<String[]> dataset, int K) {
//		System.out.println("Finding best neighbors amongst neighbors of size " + dataset.size());
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
//		System.out.println("Predicting classification");
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
