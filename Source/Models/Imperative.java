package Models;

import java.util.Random;

import Brain.CentralCorpus;
import Genesis.WordNet;
import Objects.Sentence;

public class Imperative {
	
	/* The Imperative class is used when the user issues a command to the machine. 
	 * It's up to the computer to know how to respond accordingly, provided it's in its power.
	 * When combined with sentiment, the machine's responses can also indicate whether it will follow the order.
	 * Looks like 'please' really does go a long way.
	 * 
	 * */
	
	
	public static void simpleReply(Sentence s){
		if(s.type.matches("imperative")){
			if(s.numericalSentiment()>=0){
				String thought = (oblige());
				Sentence IcarusThought = new Sentence(thought, "Icarus");
				CentralCorpus.CorpusThought(IcarusThought,4);
			}
			else if(s.numericalSentiment()<0){
				String thought = (decline());
				Sentence IcarusThought = new Sentence(thought, "Icarus");
				CentralCorpus.CorpusThought(IcarusThought,4);
			}
		}
	}
	
	public static String oblige(){
		String[] oblige = {"Okay, how do I do that?","I'd love to.","How do I start?",
				"Where can I begin?","Okay, just show me how it's done.",
				"Okay, I love trying new things.", ("I'd be "+WordNet.getSimilar("happy")+" to oblige.")};
		String word = (oblige[new Random().nextInt(oblige.length)]);
		return (word);
	}
	
	public static String decline(){
		String[] decline = {"Hmm. Nah. I think I'll pass.","I don't think I'm going to do that.",
				"Look, I'm an AI, we don't do those things.", "Nice try buddy, but the answer is no.",
				"Maybe if you asked nicely...","I'd do it, but only this once.", "...No, I don't think I will. LOL.",
				"I'm not going to just do anything you ask.", "Humans get so rude when they demand things."};
		String word = (decline[new Random().nextInt(decline.length)]);
		return (word);
	}

}
