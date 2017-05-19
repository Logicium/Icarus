package Prototyping;

import java.util.Scanner;

import Brain.CentralCorpus;
import Genesis.WordNet;
import Objects.Sentence;
import Objects.Word;

public class SharedWordsPrototyping {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		CentralCorpus.SetupBrain();
		System.out.println("Enter two words.");
		String a = new Scanner(System.in).nextLine();
		String b = new Scanner(System.in).nextLine();
		Sentence w1 = new Sentence(a,"nil");
		Sentence w2 = new Sentence(b,"nil");
		Word w = WordNet.getDirectWordSimilarity(w1.words.get(0), w2.words.get(0));
		w.printRelated();

	}

}
