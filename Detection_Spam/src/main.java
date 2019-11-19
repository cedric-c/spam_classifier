
import java.util.*; 
import java.io.File; //lire fichier externe

public class main {
	
	Set<String> uniqueTokens = new HashSet<String>();
	
	/* Dictionnaire
	 * HashMap<mot, ArratList<courriel_ID>>
	 * Ceci va nous retourner tous les mots du courriel recherché */
	protected static HashMap<String, ArrayList<String>> dictionnaire_Ham = new HashMap<String, ArrayList<String>>();
	protected static HashMap<String, ArrayList<String>> dictionnaire_Spam = new HashMap<String, ArrayList<String>>();
	
	protected static HashMap<String, ArrayList<String>> test_set; //clé:courriel de l'ensemble test (pas encore classifié); valeur:tokens après le traitement de données
	
	/* Inverted Index 
	 * HashMap<mot, ArratList<courriel_ID>>
	 * Ceci va nous retourner une liste de courriel associé au mot cherché */
	protected static HashMap<String, ArrayList<String>> invertedIndex_Ham = new HashMap<String, ArrayList<String>>();
	protected static HashMap<String, ArrayList<String>> invertedIndex_Spam = new HashMap<String, ArrayList<String>>();
	
	
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
	    		//System.out.println("stopword trouvé est: " + mot); 
	    		return true;
	    	} 
	    } 
	   
	    return false;
	}
	
	/*
	 * Appel la fonction stemWord de la classe PorterStemmer
	 * */
	public String stemming(String mot) {
		PorterStemmer stemmer = new PorterStemmer();
		return stemmer.stemWord(mot);
	}
	
	public void normalisation() {
		
	}
	
	/*
	 * String key: clé du hashMap invertedIndex (la clé est un mot/token)
	 * HashMap<String, ArrayList<String>> invertedIndex: inverted index sous forme d'un hashMap. Variable globale statique défini dans cette classe
	 * String courriel_ID: courriel qu'on veut associé à la clé (arg key) dans le hashMap invertedIndex
	 * */
	public void construitInvertedIndex(String key, HashMap<String, ArrayList<String>> invertedIndex, String courriel_ID) {
		
		ArrayList<String> listeDeCourrielID = new ArrayList<String>();
		
		if (invertedIndex.containsKey(key)) {
			listeDeCourrielID = invertedIndex.get(key);
			listeDeCourrielID.add(courriel_ID);
			invertedIndex.put(courriel_ID, listeDeCourrielID);
			
		} else { //ajoute un nouveau mot comme clé
			listeDeCourrielID.add(courriel_ID);
			invertedIndex.put(key, listeDeCourrielID);
		}
	}
	
	/*
	 * Inspirer de ce site web pour itérer à travers d'un hashmap
	 * https://www.geeksforgeeks.org/traverse-through-a-hashmap-in-java/
	 * */
	public void traitementDeDonnees(HashMap<String, ArrayList<String>> dictionnaire, HashMap<String, ArrayList<String>> invertedIndex) throws Exception {
		
		String courriel_ID; //get la clé (ID du courriel)
		ArrayList<String> tokensDuCourriel; //tokens du courriel
		String token, stemToken;
		
		//itérer à travers de chaque courriel
		for (Map.Entry mapElement : dictionnaire.entrySet()) { 
            courriel_ID = (String)mapElement.getKey(); 
            tokensDuCourriel = dictionnaire.get(courriel_ID); 
            
            //itérer à travers de chaque tokens dans le courriel
			for (int j=0; j< tokensDuCourriel.size(); j++) {
				
				token = tokensDuCourriel.get(j);
				if(!stopWord(token)) {
					stemToken = stemming(token);
					tokensDuCourriel.set(j, stemToken); //stem le mot
					construitInvertedIndex(stemToken, invertedIndex, courriel_ID); //ajoute le mot et courriel_ID dans invertedIndex
				} else {
					tokensDuCourriel.remove(j); //enlève ce token car c'est un stopWord
				}
			}
        } 
		
	}
	
	
	/*---------------------------------------------------------------------------------------*/
	public void TEST(String mot) throws Exception {

	}
	/*---------------------------------------------------------------------------------------*/
	
	public static void main(String[] args) throws Exception {
		
		main a = new main();
		CatalogManager manager = new CatalogManager();
		dictionnaire_Spam = manager.getMap("spam");
        dictionnaire_Ham  = manager.getMap("ham");
        test_set  = manager.getMap("test"); //MODIFIER: ajouter cette ligne
        
        a.traitementDeDonnees(dictionnaire_Ham, invertedIndex_Ham);
        a.traitementDeDonnees(dictionnaire_Spam, invertedIndex_Spam);
        
        NaiveBayes nb = new NaiveBayes(dictionnaire_Ham, dictionnaire_Spam, invertedIndex_Ham, invertedIndex_Spam, test_set);
        nb.classifierNB(false); //pas de lissage
        
        HashMap<String, ArrayList<String>> classifier_Ham_Test = nb.getClassifier_Ham_Test();
        HashMap<String, ArrayList<String>> classifier_Spam_Test = nb.getClassifier_Spam_Test();
        
        System.out.print("classifier_Ham_Test: " + classifier_Ham_Test.size());
        System.out.print("classifier_Spam_Test: " + classifier_Spam_Test.size());
        
        
		/*TESTER fonction stopWords() 
		ArrayList<String> cars = new ArrayList<String>();
	    cars.add("Ford");
	    cars.add("started");
	    cars.add("day");
	    dictionnaire_Ham.put("automobile", cars);
	    
	    cars = new ArrayList<String>(); //créer nouvelle liste
		cars.add("September");
	    cars.add("yoo");
	    cars.add("started");
	    cars.add("course");
	    dictionnaire_Ham.put("random", cars);
	    
	    System.out.println("Dictionnaire avant: " + dictionnaire_Ham); //avant
	    a.traitementDeDonnees(dictionnaire_Ham, invertedIndex_Ham); 
	    System.out.println("Dictionnaire stemmed: " + dictionnaire_Ham); //après
	    
	    System.out.println("");
	    System.out.println("Inverted Index: " + invertedIndex_Ham);
	    */
	    	
	}	
}
