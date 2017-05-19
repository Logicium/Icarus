package Genesis;

import java.util.ArrayList;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;

import Brain.CentralCorpus;
import Objects.Sentence;
import Objects.Word;
import Prototyping.WordNetPrototyping;
import rita.wordnet.RiWordnet;
import rita.RiTa;
import edu.cmu.lti.lexical_db.ILexicalDatabase;
import edu.cmu.lti.lexical_db.NictWordNet;
import edu.cmu.lti.ws4j.RelatednessCalculator;
import edu.cmu.lti.ws4j.impl.HirstStOnge;
import edu.cmu.lti.ws4j.impl.JiangConrath;
import edu.cmu.lti.ws4j.impl.LeacockChodorow;
import edu.cmu.lti.ws4j.impl.Lesk;
import edu.cmu.lti.ws4j.impl.Lin;
import edu.cmu.lti.ws4j.impl.Path;
import edu.cmu.lti.ws4j.impl.Resnik;
import edu.cmu.lti.ws4j.impl.WuPalmer;
import edu.cmu.lti.ws4j.util.WS4JConfiguration;


public class WordNet {

	private static ILexicalDatabase db = new NictWordNet();
	public static RiWordnet wordnet = new RiWordnet(null);
	public static String automaticTense = "";
	
	public static String pickReplacement(String word) {
		if (word != null) {

			String pos = wordnet.getBestPos(word);
			if (pos != null) {
				// First check and see if there are any antonyms
				// If so, pick a random one and return
				String[] antonyms = wordnet.getAllAntonyms(word, pos);
				if (antonyms != null) {
					String replacement = antonyms[(int) Math.random() * antonyms.length];
					CentralCorpus.CorpusPrint(word + " ==> " + replacement + "  (antonym " + pos + ")");
					return replacement;
				}
				// Then check and see if there are any hyponyms
				// If so, pick a random one and return
				String[] hyponyms = wordnet.getAllHyponyms(word, pos);
				if (hyponyms != null) {
					String replacement = hyponyms[(int) Math.random() * hyponyms.length];
					CentralCorpus.CorpusPrint(word + " ==> " + replacement + "  (hyponym " + pos + ")");
					return replacement;
				}
				// Same thing for synonyms
				String[] synonyms = wordnet.getAllSynonyms(word, pos);
				if (synonyms != null) {
					String replacement = synonyms[(int) Math.random() * synonyms.length];
					CentralCorpus.CorpusPrint(word + " ==> " + replacement + "  (synonym " + pos + ")");
					return replacement;
				}
			}
		}
		return word;
	}
	
	public static String getHypernym(String word){
		String[] hypernyms;
		try {
			hypernyms = WordNetPrototyping.getHypernyms(word);
			String hypernym = (hypernyms[new Random().nextInt(hypernyms.length)]);
			return hypernym;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "";
		
	}
	
	public static void setAutoTense(Sentence s){
		automaticTense = s.tenseString;	
	}
	
	public static Word getDirectWordSimilarity(Word w1, Word w2){
		Word shared = new Word("");
		ArrayList<String> sharedAnto = new ArrayList<String>();
		ArrayList<String> sharedSyno = new ArrayList<String>();
		ArrayList<String> sharedHolo = new ArrayList<String>();
		ArrayList<String> sharedHypo = new ArrayList<String>();
		ArrayList<String> sharedHyper = new ArrayList<String>();
		
		if(!(w1.antonyms==null) && !(w2.antonyms==null)){
			for(int i =0;i<w1.antonyms.length;i++){
				if(contains(w2.antonyms, w1.antonyms[i])){
					sharedAnto.add(w1.antonyms[i]);
				}
			}
			if(!sharedAnto.isEmpty()){
				shared.antonyms = (String[]) sharedAnto.toArray();
				shared.antonyms = sharedAnto.toArray(shared.antonyms);
			}
		}
		
		if(!(w1.synonyms==null) && !(w2.synonyms==null)){
			for(int i =0;i<w1.synonyms.length;i++){
				if(contains(w2.synonyms, w1.synonyms[i])){
					sharedSyno.add(w1.synonyms[i]);
				}
			}
			if(!sharedSyno.isEmpty()){
				shared.synonyms = new String[sharedSyno.size()];
				shared.synonyms = sharedSyno.toArray(shared.synonyms);
			}
		}
		if(!(w1.holonyms==null) && !(w2.holonyms==null)){
			for(int i =0;i<w1.holonyms.length;i++){
				if(contains(w2.holonyms, w1.holonyms[i])){
					sharedHolo.add(w1.holonyms[i]);
				}
			}
			if(!sharedHolo.isEmpty()){
				
				shared.holonyms = new String[sharedHolo.size()];
				shared.holonyms = sharedHolo.toArray(shared.holonyms);
			}
		}
		if(!(w1.hyponyms==null) && !(w2.hyponyms==null)){
			for(int i =0;i<w1.hyponyms.length;i++){
				if(contains(w2.hyponyms, w1.hyponyms[i])){
					sharedHypo.add(w1.hyponyms[i]);
				}
			}
			if(!sharedHypo.isEmpty()){
				shared.hyponyms = new String[sharedHypo.size()];
				shared.hyponyms = sharedHypo.toArray(shared.hyponyms);
			}
		}
		if(!(w1.hypernyms==null) && !(w2.hypernyms==null)){
			for(int i =0;i<w1.hypernyms.length;i++){
				if(contains(w2.hypernyms, w1.hypernyms[i])){
					sharedHyper.add(w1.hypernyms[i]);
				}
			}
			if(!sharedHyper.isEmpty()){
				shared.hypernyms = new String[sharedHyper.size()]; 
				shared.hypernyms = sharedHyper.toArray(shared.hypernyms);
			}
		}
		return shared;
		
	}
	
	public static boolean contains(String[] a,String s){
		for(int i =0;i<a.length;i++){
			if(a[i].matches(s)){
				return true;
			}
		}
		return false;
	}
	
	public static boolean isRelatedTo(String word1, String word2){
		double LCRelation = similarityCalculateLC(word1,word2);
		double ResnikRelation = similarityCalculateResnik(word1,word2);
		if(LCRelation>=1.0||ResnikRelation>=1.0){
			return true;
		}
		return false;
	}
	
	public static double similarityCalculateLC(String word1, String word2){
		 WS4JConfiguration.getInstance().setMFS(true);
         return new LeacockChodorow(db).calcRelatednessOfWords(word1, word2);
	}
	public static double similarityCalculateResnik(String word1, String word2){
		 WS4JConfiguration.getInstance().setMFS(true);
        return new Resnik(db).calcRelatednessOfWords(word1, word2);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String autoConjugate(String word){
		
		Map args = new HashMap();
		if(automaticTense.matches("presentTense")||automaticTense.matches("presentParticiple")){
			args.put("tense", RiTa.PRESENT_TENSE);
		}
		else if(automaticTense.matches("pastTense")||automaticTense.matches("pastParticiple")){
			args.put("tense", RiTa.PAST_TENSE);
		}
		else{
			args.put("tense", RiTa.PRESENT_TENSE);
		}
		args.put("number", RiTa.SINGULAR); //Consider Auto Number Detection for the word
		args.put("person", RiTa.FIRST_PERSON); //Find a way to detect the POV
		
		return RiTa.conjugate(word, args);
		
	}
	
	@SuppressWarnings("unchecked")
	public static String conjugate(String dependent){
		@SuppressWarnings("rawtypes")
		Map args = new HashMap();
		args.put("tense", RiTa.PRESENT_TENSE);
		args.put("number", RiTa.SINGULAR);
		args.put("person", RiTa.FIRST_PERSON);
		return RiTa.conjugate(dependent, args);
	}
	
	public static String conjugate(String dependent, int tense){
		@SuppressWarnings("rawtypes")
		Map args = new HashMap();
		args.put("tense", tense);
		args.put("number", RiTa.SINGULAR);
		args.put("person", RiTa.FIRST_PERSON);
		return RiTa.conjugate(dependent, args);
	}
	
	
	public static String getSimilar(String word) {
		String pos = wordnet.getBestPos(word);
		if(!(pos==null)){
			String similarSet[] = wordnet.getAllSimilar(word, pos);
			String choice = (similarSet[new Random().nextInt(similarSet.length)]);
			return choice;
		}
		return "null";
	}
	
	public static String getSynonym(String word) {
		String pos = wordnet.getBestPos(word);
		if(!(pos==null)){
			String similarSet[] = wordnet.getAllSynonyms(word, pos);
			String choice = (similarSet[new Random().nextInt(similarSet.length)]);
			return choice;
		}
		return "null";
	}

	public static String getHyponym(String word) {
		String[] hyponyms;
		try {
			hyponyms = WordNetPrototyping.getHyponyms(word);
			String hyponym = (hyponyms[new Random().nextInt(hyponyms.length)]);
			return hyponym;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "";
		
	}

	public static String getHyponym(String word, String wnPOS) {
		String[] hyponyms;
		try {
			hyponyms = WordNetPrototyping.getHyponyms(word,wnPOS);
			String hyponym = (hyponyms[new Random().nextInt(hyponyms.length)]);
			return hyponym;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return "";
	}
}
