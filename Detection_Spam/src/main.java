
import java.util.HashMap; //variables
import java.util.ArrayList; //variables
import java.io.File; //lire fichier externe
import java.util.Scanner; //lire fichier externe

public class main {
	
	/* Dictionnaire
	 * HashMap<mot, ArratList<courriel_ID>>
	 * Ceci va nous retourner tous les mots du courriel recherché */
	protected HashMap<String, ArrayList<String>> dictionnaire_Ham;
	protected HashMap<String, ArrayList<String>> dictionnaire_Spam;

	
	/* Inverted Index 
	 * HashMap<mot, ArratList<courriel_ID>>
	 * Ceci va nous retourner une liste de courriel associé au mot cherché */
	protected HashMap<String, ArrayList<String>> invertedIndex_Ham;
	protected HashMap<String, ArrayList<String>> invertedIndex_Spam;

	
	public static void main(String[] args) {
		
	}
	
	public void traitementDeDonnees(boolean stopword, boolean stemming, boolean normalisation) throws Exception {
		if (stopword) { stopWord(); }
		if (stemming) { stemming(); }
		if (normalisation) { normalisation(); }
		
	}
	
	/*
	 * Inspiré de ce site web pour lire un fichier texte
	 * https://www.geeksforgeeks.org/different-ways-reading-text-file-java/
	 * */
	public void stopWord() throws Exception {
	    // pass the path to the file as a parameter 
	    File file = new File("C:\\Users\\pankaj\\Desktop\\test.txt"); 
	    Scanner sc = new Scanner(file); 
	  
	    while (sc.hasNextLine()) 
	      System.out.println(sc.nextLine());  
	}
	
	public void stemming() {
		
	}
	
	public void normalisation() {
		
	}
}
