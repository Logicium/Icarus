package Objects;

import java.util.ArrayList;
import java.util.Arrays;

import Brain.CentralCorpus;
import Genesis.WordNet;

public class Word {
	
	public String original;
	public String transposition;
	public String wnPOS;	
	public String sentiment;
	public String questionType;
	public Syntax syntax;
	public PartOfSpeech pos;
	public String[] hypernyms;
	public String[] holonyms;
	public String[] hyponyms;
	public String[] synonyms;
	public String[] antonyms;
	public String[] similar;
	public String[] examples;
	
	public Word(String w){
		this.original = w;
		wnPOS = WordNet.wordnet.getBestPos(w);
		//CentralCorpus.CorpusPrint("[DEBUG] "+wnPOS);
	}

	public String getSentiment() {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] getHypernyms() {
		if(!(wnPOS==null)){
			//System.out.println("TRUE hyper");
			return WordNet.wordnet.getAllHypernyms(original, wnPOS);
			
		}
		else{
			return null;
		}
	}

	public String[] getHolonyms() {
		if(!(wnPOS==null)){
			//System.out.println("TRUE holo");
			return WordNet.wordnet.getAllHolonyms(original, wnPOS);
		}
		else{
			return null;
		}
	}

	public String[] getHyponyms() {
		if(!(wnPOS==null)){
			//System.out.println("TRUE hypo");
			return WordNet.wordnet.getAllHyponyms(original, wnPOS);
		}
		else{
			return null;
		}
	}
	
	public String[] getSynonyms() {
		if(!(wnPOS==null)){
			//System.out.println("TRUE syno");
			return WordNet.wordnet.getAllSynonyms(original, wnPOS);
		}
		else{
			return null;
		}
	}


	public String[] getAntonyms() {
		if(!(wnPOS==null)){
			//System.out.println("TRUE anto");
			return WordNet.wordnet.getAllAntonyms(original, wnPOS);
		}
		else{
			return null;
		}
	}
	
	public String[] getExamples() {
		if(!(wnPOS==null)){
			//System.out.println("TRUE exa");
			return WordNet.wordnet.getAllExamples(original, wnPOS);
		}
		else{
			return null;
		}
	}
	
	public String[] getSimilar() {
		if(!(wnPOS==null)){
			//System.out.println("TRUE simi");
			return WordNet.wordnet.getAllSimilar(original, wnPOS);
		}
		else{
			return null;
		}
	}
	
	public void capitalizeProperNoun(){
		
	}
	
	public void printRelated(){
		
		if(!(hypernyms==null)){
			System.out.println("[PRINT] hyper "+Arrays.toString(hypernyms));
		}
		if(!(hyponyms==null)){
			System.out.println("[PRINT] hypo "+Arrays.toString(hyponyms));
		}
		if(!(holonyms==null)){
			System.out.println("[PRINT] holo "+Arrays.toString(holonyms));
		}
		if(!(synonyms==null)){
			System.out.println("[PRINT] syno "+Arrays.toString(synonyms));
		}
		if(!(antonyms==null)){
			System.out.println("[PRINT] anto "+Arrays.toString(antonyms));
		}
		
	}
	
	
	public void setQuestionAnswerType() {
		if(pos.type.matches("NN")||pos.type.matches("NNS")||pos.type.matches("VBD")||pos.type.matches("JJ")){
			questionType = "what";
		}
		else if(pos.type.matches("RB")){
			questionType = "how";
		}
		else if(pos.type.matches("NNP") || pos.type.matches("PRP")){
			questionType = "who";
		}
		else if(pos.type.matches("PRP\\$")){
			questionType = "whose";
		}
		else if(pos.type.matches("PDT")||pos.type.matches("DT")){
			questionType = "which";
		}
		else if(pos.type.matches("RBR") ||pos.type.matches("RBS")||pos.type.matches("JJR")){
			questionType = "compared to what";
		}
		else if(pos.type.matches("EX")||pos.type.matches("IN")){
			questionType = "where";
		}
		else if(pos.type.matches("FW")){
			questionType = "which language";
		}
	}

}
