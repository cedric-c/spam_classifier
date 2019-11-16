import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.HashMap;
import java.nio.file.Path;

public class CatalogManager {
    private static String PATH_HAM = "../../data/ham-and-spam-dataset/ham/";
    private static String PATH_SPAM = "../../data/ham-and-spam-dataset/spam/";
    private HashMap<String, String> fileDirectories;

    public CatalogManager() {
        this.fileDirectories = new HashMap<String, String>();
        this.addDirectory("ham", PATH_HAM);
        this.addDirectory("spam", PATH_SPAM);
    }

    public void addDirectory(String key, String path) {
        this.fileDirectories.put(key, path);
    }

    public void ListFiles(String directory) throws IOException{
        String path = this.fileDirectories.get(directory);
        if(path == null)
            throw new IOException("Path does not exist!");

        Path p = Paths.get(path);

        Files.list(p)
            .forEach(System.out::println);
        
    }

}