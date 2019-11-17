import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class CSVLoader {
	
	private String path;
	private ArrayList<String> content;
	private String[][] data;
	private int columnNum;
	private String[] header;
	
	public CSVLoader(String path) {
		this.path = path;
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
		
		this.data = new String[createSize][];
		for(int i = 0; i < this.content.size(); i++) {
			
			if(startIndex == this.content.size())
				break;
			
			String[] data = this.content.get(startIndex).split(",");
			this.data[i] = data;
			startIndex++;
		}
		System.out.println("Number of columns is " + this.columnNum);
		return this;
	}
	
	public String[] get(int index) {
		return this.data[index];
	}
	
	public String[][] data(){
		return this.data;
	}
	
	
	
}
