package Models;

import Brain.CentralCorpus;
import Objects.Sentence;

public class Exclamatory {

	public static void simpleReply(Sentence s){
		if(s.type.matches("exclamatory")){
			if(s.numericalSentiment()>0){
				String thought = ("Yay! I am really excited for "+s.transposedFocus+"!");
				Sentence IcarusThought = new Sentence(thought, "Icarus");
				CentralCorpus.CorpusThought(IcarusThought,3);
			}
			else if(s.numericalSentiment()<0){
				String thought = ("Calm down buddy, I'm not your enemy.");
				Sentence IcarusThought = new Sentence(thought, "Icarus");
				CentralCorpus.CorpusThought(IcarusThought,3);
			}			
		}
	}
}
