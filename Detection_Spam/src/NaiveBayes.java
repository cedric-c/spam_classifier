import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NaiveBayes {
	
	/*
	 * Définition des variables publiques:
	 * 
	 * dictionnaire_Ham et dictionnaire_Spam: 
	 * données dans l'ensemble d'entrainement (traning set)
	 * 
	 * invertedIndex_Ham et invertedIndex_Spam:
	 * inverted index basé sur les données d'entrainement
	 * 
	 * classifier_Ham_Test: courriel de l'ensemble test qui a été classifié comme ham
	 * classifier_Spam_Test: courriel de l'ensemble test qui a été classifié comme spam
	 * 
	 * test_set: ensemble de courriels non-classfiés
	 * 
	 * */
	protected static HashMap<String, ArrayList<String>> dictionnaire_Ham; //clé:courriel ham de l'ensemble d'entrainement ; valeur:tokens après le traitement de données
	protected static HashMap<String, ArrayList<String>> dictionnaire_Spam; //clé:courriel spam de l'ensemble d'entrainement ; valeur:tokens après le traitement de données
	
	protected static HashMap<String, ArrayList<String>> invertedIndex_Ham; //inverted index basé sur les courriels ham
	protected static HashMap<String, ArrayList<String>> invertedIndex_Spam; //inverted index basé sur les courriels ham
	
	protected static HashMap<String, ArrayList<String>> classifier_Ham_Test = new HashMap<String, ArrayList<String>> (); //courriel de l'ensemble test à été classifié en tant que ham
	protected static HashMap<String, ArrayList<String>> classifier_Spam_Test = new HashMap<String, ArrayList<String>> (); //courriel de l'ensemble test à été classifié en tant que spam
	
	protected static HashMap<String, ArrayList<String>> test_set; //clé:courriel de l'ensemble test (pas encore classifié); valeur:tokens après le traitement de données
	protected static ArrayList<String[]> statistics = new ArrayList<String[]>();
	
	protected static CatalogManager manager;
	
	protected static double lissage_NB = 0;

	NaiveBayes(HashMap<String, ArrayList<String>> dictionnaire_Ham, HashMap<String, ArrayList<String>> dictionnaire_Spam,
			HashMap<String, ArrayList<String>> invertedIndex_Ham, HashMap<String, ArrayList<String>> invertedIndex_Spam,
			HashMap<String, ArrayList<String>> test_set){
		this.dictionnaire_Ham = dictionnaire_Ham;
		this.dictionnaire_Spam = dictionnaire_Spam;
		this.invertedIndex_Ham = invertedIndex_Ham;
		this.invertedIndex_Spam = invertedIndex_Spam;
		this.test_set = test_set;
	    this.statistics.add(new String[] {"Test_courriel_ID", "Probability", "NB_Prediction", "Type_courriel_test", "Lissage"});
		
	}
	
	NaiveBayes(HashMap<String, ArrayList<String>> dictionnaire_Ham, HashMap<String, ArrayList<String>> dictionnaire_Spam,
			HashMap<String, ArrayList<String>> invertedIndex_Ham, HashMap<String, ArrayList<String>> invertedIndex_Spam,
			HashMap<String, ArrayList<String>> test_set, double lissage_NB, CatalogManager manager, ArrayList<String[]> statistics){
		this.dictionnaire_Ham = dictionnaire_Ham;
		this.dictionnaire_Spam = dictionnaire_Spam;
		this.invertedIndex_Ham = invertedIndex_Ham;
		this.invertedIndex_Spam = invertedIndex_Spam;
		this.test_set = test_set;
		this.lissage_NB = lissage_NB;
		this.statistics = statistics;
		this.manager = manager;
	}
	
	/*
	 * Inspirer de ce site web pour itérer à travers d'un hashmap
	 * https://www.geeksforgeeks.org/traverse-through-a-hashmap-in-java/
	 * */
	private double calculateProbability(ArrayList<String> test_tokens, HashMap<String, ArrayList<String>> invertedIndex, HashMap<String, ArrayList<String>> dictionnaire, boolean lissage) {
		double posterior=0; //probabilité conditionnelle: P([token1 ET token2 ... ET tokenN]| classe)
		int denominateur = dictionnaire.size(); //tous les courriels de cette classe
		String token; //un momt dans l'arrayList test_Tokens
		int nbCourriels;
		double tmp = 0; //var temp pour stocké la prob conditionnelle de P(token i | classe)
		
		for (int i=0; i<test_tokens.size(); i++) {
			token = test_tokens.get(i);
			
			if (invertedIndex.containsKey(token)) {
				nbCourriels = invertedIndex.get(token).size(); //nombre de courriels qui contient ce token d'une classe
				tmp = nbCourriels/denominateur;
				//System.out.print(tmp + " ");
				//System.out.println("nbCourriels/denominateur = " + tmp);
			
			} else { //ensemble d'apprentissage ne contient pas le mot, donc prob conditionnelle est 0
				tmp = 0;
				if (!lissage) { //si il n'y a pas de lissage, on sait que la prob va être 0
					return 0; //car si un mot n'existe pas, alors une des probabiltiés conditionnelles vont être à 0, donc le résultat va être à 0
				}
			}
			
			if (i==0 && lissage && lissage_NB>0) {
				posterior = tmp + lissage_NB; //ajoute 0,1 à la prob conditionnelle pour le lissage
			} else if (i==0 && !lissage) {
				posterior = tmp;
			} else if (lissage && i>0 && lissage_NB>0) {
				posterior = posterior * (tmp + lissage_NB); //ajoute 0,1 à la prob conditionnelle pour le lissage
			} else { //lissage && i>0
				posterior = posterior * tmp;
			}
		} //fin de la boucle for
		
		return posterior;
	} //fin de la fonction calculateProbability
	
	
	/* retourne le priori de la classe choisie */
	public double getPriors(String classe) {
		double count_Ham = dictionnaire_Ham.size();
		double count_Spam = dictionnaire_Spam.size();
		double total_courriel = count_Ham + count_Spam;
		double prior = 0;
		
		if (classe.equals("spam")) { 
			prior = count_Spam/total_courriel;
			return prior; }
		else if (classe.equals("ham")) { 
			prior = count_Ham/total_courriel;
			return prior; }
		else { 
			System.out.println("ELSE prior = 0 ");
			return 0; }	
	} //fin de la fonction getPriors
	
	
	//classifie les données de l'ensemble test avec la méthode Naive Bayes
	/*
	 * Inspirer de ce site web pour itérer à travers d'un hashmap
	 * https://www.geeksforgeeks.org/traverse-through-a-hashmap-in-java/
	 * */
	public void classifierNB(boolean lissage) throws IOException {
			
		double prior_Spam = getPriors("spam"); //priori de la classe spam
		double prior_Ham = getPriors("ham"); //priori de la classe ham
		
		
		double probHam, probSpam;
		String courriel_ID;
		ArrayList<String> tokensDuCourriel = new ArrayList<String>();
		
		//itérer à travers de l'ensemble test
		for (Map.Entry mapElement : test_set.entrySet()) { 
            courriel_ID = (String)mapElement.getKey(); 
            tokensDuCourriel = test_set.get(courriel_ID);

			//appeler calculateProbabilty pour calculer les prob conditionelles
			probSpam = calculateProbability(tokensDuCourriel, invertedIndex_Spam, dictionnaire_Spam, lissage)*prior_Spam;
			
			probHam = calculateProbability(tokensDuCourriel, invertedIndex_Ham, dictionnaire_Ham, lissage)*prior_Ham;
			//System.out.println("(spam,ham):("+probSpam + ","+probHam+")");
		
			
			//compare les probabilités et prendre celui qui est la plus grande lissage_NB
			if (probSpam > probHam) {
				classifier_Spam_Test.put(courriel_ID, tokensDuCourriel);
				
				if(manager.isSpam(courriel_ID)) {
					statistics.add(new String[] { courriel_ID, String.valueOf(probSpam), "spam", "spam", String.valueOf(lissage_NB)});
				} else {
					statistics.add(new String[] { courriel_ID, String.valueOf(probSpam), "spam", "ham", String.valueOf(lissage_NB)});
				}
				
			} else if (probSpam < probHam){
				classifier_Ham_Test.put(courriel_ID, tokensDuCourriel);
				
				if(manager.isSpam(courriel_ID)) {
					statistics.add(new String[] { courriel_ID, String.valueOf(probHam), "ham", "spam", String.valueOf(lissage_NB)});
				} else {
					statistics.add(new String[] { courriel_ID, String.valueOf(probHam), "ham", "ham", String.valueOf(lissage_NB)});
				}
				
			} else {
				classifier_Spam_Test.put(courriel_ID, tokensDuCourriel); //si il n'y a pas de prob max, on va supposé aléatoirement que c'est un spam
				
				if(manager.isSpam(courriel_ID)) {
					statistics.add(new String[] { courriel_ID, String.valueOf(probSpam), "spam", "spam", String.valueOf(lissage_NB)});
				} else {
					statistics.add(new String[] { courriel_ID, String.valueOf(probSpam), "spam", "ham", String.valueOf(lissage_NB)});
				}
			}
		}
		
	} //fin de la fonction classifierNB
	
	public void exportCSV(String path, ArrayList<String[]> statistics_var) {
		try {
			//SimpleIO.writeStringsToFile("./src/out/my.csv", statistics);
			SimpleIO.writeStringsToFile(path, statistics_var);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/* Getters */
	public HashMap<String, ArrayList<String>> getClassifier_Spam_Test() { return classifier_Spam_Test; }
	
	public HashMap<String, ArrayList<String>> getClassifier_Ham_Test() { return classifier_Ham_Test; }

	public ArrayList<String[]> getStatistics() { return statistics; }
}

