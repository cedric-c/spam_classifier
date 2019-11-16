
import java.util.*; 
import java.io.File; //lire fichier externe

public class main {
	
	Set<String> uniqueTokens = new HashSet<String>();
	
	/* Dictionnaire
	 * HashMap<mot, ArratList<courriel_ID>>
	 * Ceci va nous retourner tous les mots du courriel recherché */
	protected static HashMap<String, ArrayList<String>> dictionnaire_Ham;
	protected static HashMap<String, ArrayList<String>> dictionnaire_Spam;

	
	/* Inverted Index 
	 * HashMap<mot, ArratList<courriel_ID>>
	 * Ceci va nous retourner une liste de courriel associé au mot cherché */
	protected static HashMap<String, ArrayList<String>> invertedIndex_Ham;
	protected static HashMap<String, ArrayList<String>> invertedIndex_Spam;
	
	
	/*
	 * Inspiré de ce site web pour lire un fichier texte
	 * https://www.geeksforgeeks.org/different-ways-reading-text-file-java/
	 * */
	public boolean stopWord(String mot) throws Exception {
	    File file = new File("test.txt"); //lire le fichier contenant les stopwords
	    Scanner scanner = new Scanner(file); //créer notre scanner
	    
	    while (scanner.hasNextLine()) {
	    	String motDuFichier = scanner.nextLine();
	    	if (motDuFichier.equals(mot)) {
	    		System.out.print("stopword trouvé est: "); 
	    		System.out.println(mot); 
	    		return true;
	    	} 
	    } 
	   
	    return false;
	}
	
	public void stemming() {
		
	}
	
	public void normalisation() {
		
	}
	
	public void construitInvertedIndex() {
		
	}
	
	/*
	 * Inspirer de ce site web pour itérer à travers d'un hashmap
	 * https://www.geeksforgeeks.org/traverse-through-a-hashmap-in-java/
	 * */
	public void traitementDeDonnees(HashMap<String, ArrayList<String>> dictionnaire) throws Exception {
		//itérer à travers de chaque courriel
		for (Map.Entry mapElement : dictionnaire.entrySet()) { 
            String courriel_ID = (String)mapElement.getKey(); //get la clé (ID du courriel)
            ArrayList<String> tokensDuCourriel = dictionnaire.get(courriel_ID); //tokens du courriel
            
            //itérer à travers de chaque tokens dans le courriel
			for (int j=0; j< tokensDuCourriel.size(); j++) {
				
				if(!stopWord(tokensDuCourriel.get(j))) {
					stemming();
					construitInvertedIndex();
				}
			}
        } 
		
	}
	
	/*
	 * for each dictionnaire_Ham 
	 * 		=> (1) if (stopWord) then remove word (--IGNORE stemming et passe au prochain mot)
	 * 		=> (2) stemming
	 * 		=> (3) remet le mot dans le arrayList
	 * 		=> en même temps, commence à construire invertedIndex_Ham
	 * 
	 * 
	 * for each dictionnaire_Ham 
	 * 		=>
	 * */
	
	
	/*---------------------------------------------------------------------------------------*/
	public boolean stopWordTEST(String mot) throws Exception {
	    File file = new File("test.txt"); //lire le fichier contenant les stopwords
	    Scanner scanner = new Scanner(file); //créer notre scanner
	    
	    while (scanner.hasNextLine()) {
	    	String motDuFichier = scanner.nextLine();
	    	if (motDuFichier.equals(mot)) {
	    		System.out.print("stopword trouvé est: "); 
	    		System.out.println(mot); 
	    		return true;
	    	} 
	    } 
	   
	    return false;
	}
	/*---------------------------------------------------------------------------------------*/
	
	public static void main(String[] args) throws Exception {
		main a = new main();
		
		/*TESTER fonction stopWords()
		ArrayList<String> cars = new ArrayList<String>();
		cars.add("Volvo");
	    cars.add("BMW");
	    cars.add("Ford");
	    cars.add("Mazda");
	    dictionnaire_Ham.put("automobile", cars);
	    cars = new ArrayList<String>(); //créer nouvelle liste
		cars.add("cat");
	    cars.add("yo");
	    cars.add("banane");
	    cars.add("amarillo");
	    dictionnaire_Ham.put("random", cars);
	    a.traitementDeDonnees(dictionnaire_Ham); */
	    
	    System.out.println(a.stopWordTEST("cat"));
	}	
}
