import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.*;
//import java.nio.charset;
public class CatalogManager {
    private static String PATH_HAM = "./src/data/ham-and-spam-dataset/ham/";
    private static String PATH_SPAM = "./src/data/ham-and-spam-dataset/spam/";

    private HashMap<String, String> fileDirectories;

    public CatalogManager() {
        this.fileDirectories = new HashMap<String, String>();
        this.addDirectory("ham", PATH_HAM);
        this.addDirectory("spam", PATH_SPAM);
    }

    public void addDirectory(String key, String path) {
        this.fileDirectories.put(key, path);
    }

    public HashMap<String, List<String>> GetContent(String directory) throws IOException{
        String path = this.fileDirectories.get(directory);
        if(path == null)
            throw new IOException("Path does not exist!");

        Path p = Paths.get(path);
        
        HashMap<String, List<String>> contents = new HashMap<String, List<String>>();
//        Files.list(p)
//        		.forEach(System.out::println);
        
        
        ArrayList<Path> paths = Files.list(p)
        		.collect(Collectors.toCollection(ArrayList::new));
        		
        int readFiles = 0;
        int skippedFiles = 0;
        for(Path f : paths) {
        	try {
        		List<String> lines = Files.readAllLines(f, Charset.forName("Cp1252"));
//        		System.out.println("Read file!");
//        		readFiles++;
        		String name = f.getFileName().toString();
        		contents.put(name, lines);
        	}catch(Exception e) {
//        		System.out.println("Skipped file!");
//        		skippedFiles++;
        	}
        	
        	
        	
//        	lines.forEach(System.out::println);
        	
        }
        return contents;

    }

}