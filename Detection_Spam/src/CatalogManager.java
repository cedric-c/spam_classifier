import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.StringTokenizer;

public class CatalogManager {
    private static String PATH_HAM = "./src/data/ham-and-spam-dataset/ham/";
    private static String PATH_SPAM = "./src/data/ham-and-spam-dataset/spam/";

    private HashMap<String, String> fileDirectories;

    public CatalogManager() {
        this.fileDirectories = new HashMap<String, String>();
        this.addDirectory("ham", PATH_HAM);
        this.addDirectory("spam", PATH_SPAM);
    }

    /**
     * 
     * @param key
     * @param path
     */
    public void addDirectory(String key, String path) {
        this.fileDirectories.put(key, path);
    }

    /**
     * Returns a hashmap of the filecontents.
     * @param directory
     * @return
     * @throws IOException
     */
    public HashMap<String, List<String>> GetContent(String directory) throws IOException{
        String path = this.fileDirectories.get(directory);
        if(path == null)
            throw new IOException("Path does not exist!");

        Path p = Paths.get(path);
        
        ArrayList<Path> paths = Files.list(p)
        		.collect(Collectors.toCollection(ArrayList::new));
        		
        HashMap<String, List<String>> contents = new HashMap<String, List<String>>();
        for(Path f : paths) {
        	try {
        		List<String> lines = Files.readAllLines(f, Charset.forName("Cp1252"));
        		List<String> allTokens = new ArrayList<String>();
        		for(String line : lines) {
        			StringTokenizer tokens = new StringTokenizer(line);
        			while(tokens.hasMoreTokens()) {
        				allTokens.add(tokens.nextToken());
        			}
        		}
//        		lines.sp
        		String name = f.getFileName().toString();
        		contents.put(name, allTokens);
        	}catch(Exception e) {
        	}
        	
        }
        return contents;

    }
    
}