package Prototyping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import rita.RiTa;

public class ConjugationPrototyping {

	public static void main(String[] args) {
		System.out.println("Enter a word to conjugate:");
		String word = new Scanner(System.in).nextLine();
		ArrayList<Integer> tenses = new ArrayList<Integer>();
		tenses.add(RiTa.PRESENT_TENSE);
		tenses.add(RiTa.PAST_TENSE);
		tenses.add(RiTa.FUTURE_TENSE);
		
		for(Integer tense: tenses){
			System.out.println(conjugate(word,tense));
		}
	}
	
	public static String conjugate(String dependent, Integer tense){
		@SuppressWarnings("rawtypes")
		Map args = new HashMap();
		args.put("tense", tense);
		args.put("number", RiTa.SINGULAR);
		args.put("person", RiTa.FIRST_PERSON);
		return RiTa.conjugate(dependent, args);
	}

}
