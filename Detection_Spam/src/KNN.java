import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class KNN {
	
	HashMap<String[], Double> sortedK;
	
	public KNN() {
		
	}
	
	/**
	 * Calculates the Euclidean distances between two CSV rows.
	 * @param X1
	 * @param X2
	 * @return
	 */
	public double distance(String[] X1, String[] X2) {
		int dims = X1.length;
		
		String[] returnVector = new String[3]; // X1 X2 same class
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
		
//		results.values()
//			.stream()
//			.sorted()
//			.limit(K)
//			.forEach(System.out::println);
//		
//		results.entrySet()
//			.stream()
//			.sorted(Map.Entry.comparingByValue())
//			.forEach(System.out::println);
		
		this.sortedK = results.entrySet()
			.stream()
			.sorted(Map.Entry.comparingByValue())
			.limit(K)
			.collect(
					Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, HashMap::new));
		
		return this.sortedK;
	}
}
