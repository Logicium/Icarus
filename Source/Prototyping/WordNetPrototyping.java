package Prototyping;

import java.util.Scanner;

import Genesis.WordNet;

public class WordNetPrototyping {

	public static String wnPOS = "";
	public static String word = "";
	public static String[] empty = new String[1];

	public static void main(String[] args) throws Throwable {
		Scanner in = new Scanner(System.in);
		System.out.println("Hello! We're going to test WordNet functions here.");
		System.out.println("Enter a word to lookup.");
		word = in.nextLine();
		wnPOS = WordNet.wordnet.getBestPos(word);
		System.out.println("POS: " + wnPOS);
		

		String[] hyper = getHypernyms(word);
		if (!(hyper == null)) {
			System.out.println("HYPER: " + hyper.length);
			for(String h:hyper){System.out.println(h);}
		} else {
			System.out.println("NO hypernyms.");
		}
		String[] hypo = getHyponyms(word);
		if (!(hypo == null)) {
			System.out.println("HYPO: " + hypo.length);
			for(String h:hypo){System.out.println(h);}
		} else {
			System.out.println("NO hyponyms.");
		}
		String[] holo = getHolonyms(word);
		if (!(holo == null)) {
			System.out.println("HOLO: " + holo.length);
			for(String h:holo){System.out.println(h);}
		} else {
			System.out.println("NO holonyms.");
		}
		String[] syno = getSynonyms(word);
		if (!(syno == null)) {
			System.out.println("SYNO: " + syno.length);
			for(String h:syno){System.out.println(h);}
		} else {
			System.out.println("NO synonyms.");
		}
		String[] anto = getAntonyms(word);
		if (!(anto == null)) {
			System.out.println("ANTO: " + anto.length);
			for(String h:anto){System.out.println(h);}
		} else {
			System.out.println("NO antonyms.");
		}

		String[] simi = getSimilar(word);
		if (!(simi == null)) {
			System.out.println("SIMI: " + simi.length);
			for(String h:simi){System.out.println(h);}
		} else {
			System.out.println("NO similar.");
		}
		String[] exa = getExamples(word);
		if (!(exa == null)) {
			System.out.println("EXA: " + exa.length);
			for(String h:exa){System.out.println(h);}
		} else {
			System.out.println("NO examples.");
		}
		in.close();
	}
	
	public static String[] getHypernyms(String word) throws Throwable{
		String[] hypo;
		if(!(wnPOS==null)){
			try{
				hypo = WordNet.wordnet.getAllHypernyms(word, wnPOS);
			return hypo;
			}
			catch(Exception e){
				return empty;
			}
		}
			return empty;
	}
	
	public static String[] getHyponyms(String word) {
		if(!(wnPOS==null)){
			try{
			return WordNet.wordnet.getAllHyponyms(word, wnPOS);
			}
			catch(Exception e){
				return empty;
			}
		}
			return empty;
	}
	
	public static String[] getHyponyms(String word,String wnPOS) {
		if(!(wnPOS==null)){
			try{
			return WordNet.wordnet.getAllHyponyms(word, wnPOS);
			}
			catch(Exception e){
				return empty;
			}
		}
			return empty;
	}

	public static String[] getHolonyms(String word) {
		if(!(wnPOS==null)){
			try{
			return WordNet.wordnet.getAllHolonyms(word, wnPOS);
			}
			catch(Exception e){
				return empty;
			}
		}
			return empty;
	}

	public static String[] getSynonyms(String word) {
		if(!(wnPOS==null)){
			try{
			return WordNet.wordnet.getAllSynonyms(word, wnPOS);
			}
			catch(Exception e){
				return empty;
			}
		}
			return empty;
	}

	public static String[] getAntonyms(String word) {
		if(!(wnPOS==null)){
			try{
			return WordNet.wordnet.getAllAntonyms(word, wnPOS);
			}
			catch(Exception e){
				return empty;
			}
		}
			return empty;
	}
	
	public static String[] getExamples(String word) {
		if(!(wnPOS==null)){
			try{
			return WordNet.wordnet.getAllExamples(word, wnPOS);
			}
			catch(Exception e){
				return empty;
			}
		}
			return empty;
	}
	
	public static String[] getSimilar(String word) {
		if(!(wnPOS==null)){
			try{
			return WordNet.wordnet.getAllSimilar(word, wnPOS);
			}
			catch(Exception e){
				return empty;
			}
		}
			return empty;
	}

}
