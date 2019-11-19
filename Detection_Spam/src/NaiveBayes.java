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
	

	NaiveBayes(HashMap<String, ArrayList<String>> dictionnaire_Ham, HashMap<String, ArrayList<String>> dictionnaire_Spam,
			HashMap<String, ArrayList<String>> invertedIndex_Ham, HashMap<String, ArrayList<String>> invertedIndex_Spam,
			HashMap<String, ArrayList<String>> test_set){
		this.dictionnaire_Ham = dictionnaire_Ham;
		this.dictionnaire_Spam = dictionnaire_Spam;
		this.invertedIndex_Ham = invertedIndex_Ham;
		this.invertedIndex_Spam = invertedIndex_Spam;
		this.test_set = test_set;
		
	}
	
	private double calculateProbability(ArrayList<String> test_tokens, HashMap<String, ArrayList<String>> invertedIndex, HashMap<String, ArrayList<String>> dictionnaire, boolean lissage) {
		double posterior = 0; //probabilité conditionnelle: P([token1 ET token2 ... ET tokenN]| classe)
		int denominateur = dictionnaire.size(); //tous les courriels de cette classe
		String token; //un momt dans l'arrayList test_Tokens
		int nbCourriels;
		double tmp = 0; //var temp pour stocké la prob conditionnelle de P(token i | classe)
		
		for (int i=0; i<test_tokens.size(); i++) {
			token = test_tokens.get(i);
			
			if (invertedIndex.containsKey(token)) {
				nbCourriels = invertedIndex.get(token).size(); //nombre de courriels qui contient ce token d'une classe
				tmp = nbCourriels/denominateur;
			
			} else { //ensemble d'apprentissage ne contient pas le mot, donc prob conditionnelle est 0
				tmp = 0;
				if (!lissage) {
					return 0; //car si un mot n'existe pas, alors une des probabiltiés conditionnelles vont être à 0, donc le résultat va être à 0
				}
			}
			
			if (lissage) {
				posterior = posterior * (tmp + 0.1); //ajoute 0,1 à la prob conditionnelle pour le lissage
			} else {
				posterior = posterior * tmp;
			}
		} //fin de la boucle for
		
		return posterior;
	} //fin de la fonction calculateProbability
	
	
	/* retourne le priori de la classe choisie */
	private double getPriors(String classe) {
		int count_Ham = dictionnaire_Ham.size();
		int count_Spam = dictionnaire_Spam.size();
		int total_courriel = count_Ham + count_Spam;
		
		if (classe.equals("spam")) { return count_Spam/total_courriel; }
		else if (classe.equals("ham")) { return count_Ham/total_courriel; }
		else { return 0; }	
	} //fin de la fonction getPriors
	
	
	//classifie les données de l'ensemble test avec la méthode Naive Bayes
	public void classifierNB(boolean lissage) {
			
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
			probSpam = calculateProbability(tokensDuCourriel, invertedIndex_Spam, dictionnaire_Spam, lissage);
			probHam = calculateProbability(tokensDuCourriel, invertedIndex_Ham, dictionnaire_Ham, lissage);
			System.out.println("(spam,ham):("+probSpam + ","+probHam+")");
			//compare les probabilités et prendre celui qui est la plus grande
			if (probSpam > probHam) {
				classifier_Spam_Test.put(courriel_ID, tokensDuCourriel);
			} else if (probSpam < probHam){
				classifier_Ham_Test.put(courriel_ID, tokensDuCourriel);
			} else {
				System.out.println("probSpam == probHam: " + courriel_ID);
				classifier_Spam_Test.put(courriel_ID, tokensDuCourriel); //si il n'y a pas de prob max, on va supposé aléatoirement que c'est un spam
			}
		}
		
	} //fin de la fonction classifierNB
	
	
	/* Getters */
	public HashMap<String, ArrayList<String>> getClassifier_Spam_Test() { return classifier_Spam_Test; }
	
	public HashMap<String, ArrayList<String>> getClassifier_Ham_Test() { return classifier_Ham_Test; }
	
}
