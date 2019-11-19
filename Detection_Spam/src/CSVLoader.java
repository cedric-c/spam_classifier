import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class CSVLoader {
	
	private String path;
	private ArrayList<String> content;
	private String[][] _data;
	private ArrayList<String[]> data;
	private int columnNum;
	private String[] header;
	
	public CSVLoader(String path) {
		this.path = path;
		this.data = new ArrayList<String[]>();
	}
	
	public CSVLoader load() {
		try {
			this.content = (ArrayList<String>) Files.readAllLines(Paths.get(this.path));
		} catch(Exception e) {
			System.out.println("Couldn't load " + this.path);
		}
		return this;
	}
	
	public CSVLoader load(String path) {
		try {
			this.content = ((ArrayList<String>) Files.readAllLines(Paths.get(path)));
		} catch (Exception e) {
			System.out.println("Couldn't load " + path);
		}
		return this;
	}
	
	public CSVLoader tabulate(boolean ignoreHeaderInData) {
		String header = this.content.get(0);
		String[] tabulatedHeader = header.split(",");
		this.header = tabulatedHeader;
		this.columnNum = tabulatedHeader.length;
		
		int startIndex = ignoreHeaderInData ? 1 : 0;
		
		int createSize = ignoreHeaderInData 
				? this.content.size() - 1 
				: this.content.size();
		
//		this._data = new String[createSize][];
		for(int i = 0; i < this.content.size(); i++) {
			
			if(startIndex == this.content.size())
				break;
			
			String[] data = this.content.get(startIndex).split(",");
//			this.data[i] = data;
			this.data.add(data);
			startIndex++;
		}
		System.out.println("Number of columns is " + this.columnNum);
		return this;
	}
	
	public String[] _get(int index) {
		return this._data[index];
	}
	
	public String[][] _data(){
		return this._data;
	}
	
	public ArrayList<String[]> data(){
		return this.data;
	}
	
	public String[] get(int index) {
		return this.data.get(index);
	}
	
	/**
	 * Returns the data loaded from CSV split into two partitions of % a and % b where a + b = 1.0.
	 * @param a
	 * @param b
	 * @return
	 */
	public HashMap<Double, ArrayList<String[]>> split(double a, double b) {
		
		if(a + b != 1.0)
			throw new IllegalArgumentException("Input doubles must sum to exactly 1.0");
		
		// Randomize our split
		long seed = System.nanoTime();
		Collections.shuffle(this.data, new Random(seed));
		
//		Math.floor(a)
		double min = a * this.data.size();
		double max = b * this.data.size();
		ArrayList<String[]> aList = new ArrayList<String[]> (this.data.subList(0, (int) Math.floor(min)));
		ArrayList<String[]> bList = new ArrayList<String[]> (this.data.subList( (int) Math.ceil(min), this.data.size()));
		
		HashMap<Double, ArrayList<String[]>> r = new HashMap<Double, ArrayList<String[]>>();
		r.put(a, aList);
		r.put(b, bList);
		return r;
		
		
	}
	
	
	
}
