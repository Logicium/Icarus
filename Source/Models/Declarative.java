package Models;

import java.util.Random;

import Brain.CentralCorpus;
import Brain.Human;
import Brain.Icarus;
import Genesis.WordNet;
import Objects.Sentence;
import Objects.Word;
import Storyline.Concept;
import Storyline.Dispatch;
import Storyline.Expectation;
import Storyline.Timeline;

public class Declarative {

	/*I suppose; I think; I presume; Considering; All things considered; If you think about it, it seems; It seems;
	 * Hmm...; I guess; Looks like; Perhaps; Conversely; 
	 * HUMAN: I think...; COMP: I agree; That's understandable; That makes sense; I disagree, but; 
	 * HUMAN: I feel...; COMP: I feel the same; Lots of people feel that way; That's true, but; That's tough;
	 * HUMAN: I like...; COMP: 
	 * HUMAN: I was 
	 * 
	 * */
	
	//IMPROVE: use similar words to 'good', and 'dissappointing'
	Timeline t = CentralCorpus.t;
	public static void simpleReply(Sentence s){
		if(s.type.matches("declarative")){
			if(s.numericalSentiment()>0){
				String thought = ("That's "+WordNet.getSimilar("good")+postModifiersA()+"to hear.");
				Sentence IcarusThought = new Sentence(thought, "Icarus");
				CentralCorpus.CorpusThought(IcarusThought, 1);
			}
			else if(s.numericalSentiment()<0){
				String thought = ("That's "+WordNet.getSimilar("bad")+postModifiersA()+"to hear.");
				Sentence IcarusThought = new Sentence(thought, "Icarus");
				CentralCorpus.CorpusThought(IcarusThought, 1);
			}
		}
	}
	
	public static void invertGoodFragment(){
		//That is good --> That is *not very good. |*not |*definitely not |etc
	}
	

	public static void detectHumanAssertion(Sentence s){
		if(s.origin.matches("human")){
			if(!s.predicate.matches("")&&!s.postdicate.matches("")){
				if(s.transposedFocus.toLowerCase().matches("me")){
					if(s.numericalSentiment()>0){
						CentralCorpus.CorpusThought(new Sentence("I am "+WordNet.getSynonym("happy")
						+" to be "+s.postdicate+"!","Icarus"),4);//Improve with array of assertion responses 
						for(Word kw : s.keywords){
							Concept c  = new Concept("I am",kw); 
							Icarus.addConcept(c);
						}						
					}
					else if(s.numericalSentiment()==0){
						String reply = ("Should I be "+s.postdicate+"?");
						Sentence IcarusSentence = new Sentence(reply,"Icarus");	
						CentralCorpus.CorpusThought(IcarusSentence,4);				
						Expectation e = new Expectation(IcarusSentence);
						Dispatch.addExpectation(e);
						for(Word kw : s.keywords){
							Concept c  = new Concept("I may be",kw); 
							Icarus.addConcept(c);
						}
						
					}
					else if(s.numericalSentiment()<0){
						String reply = "I don't think I want to be "+s.postdicate+".";
						Sentence IcarusSentence = new Sentence(reply,"Icarus");
						CentralCorpus.CorpusThought(IcarusSentence,4);
						for(Word kw : s.keywords){
							Concept c  = new Concept("I am not",kw); 
							Icarus.addConcept(c);
						}
					}
				}
				else if(s.transposedFocus.toLowerCase().matches("you")){
					if(s.numericalSentiment()>0){
						String thought = ("You seem "+WordNet.getSynonym("happy")+" to be "+s.postdicate+".");
						Sentence IcarusThought = new Sentence(thought, "Icarus");
						CentralCorpus.CorpusThought(IcarusThought,3);

						for(Word kw :s.keywords){
							Concept c = new Concept("You are",kw);
							Human.addConcept(c);
						}

					}
					else if(s.numericalSentiment()<0){
						String thought = ("You seem "+WordNet.getSynonym("sad")+" to be "+s.postdicate+".");
						Sentence IcarusThought = new Sentence(thought, "Icarus");
						CentralCorpus.CorpusThought(IcarusThought,3);

						for(Word kw :s.keywords){
							Concept c = new Concept("You are",kw);
							Human.addConcept(c);
						}
					}
				}
			}
		}
	}

	public static String postModifiersA(){
		String[] postMods = {" to hear"," to read"," to know"," to understand"," to be told"," to be aware of."};
		String word = (postMods[new Random().nextInt(postMods.length)]);
		return (word);
	}
	
	public static String preModifiersA(){
		String[] preMods = {"I agree","That's understandable","That makes sense","I disagree, but"
		};
		String word = (preMods[new Random().nextInt(preMods.length)]);
		return (word);
	}
	
	public static String preModifiersB(){
		String[] preMods = {"I suppose","I think","I presume","Considering","All things considered",
				"If you think about it, it seems", "It seems", "I imagine"};
		String word = (preMods[new Random().nextInt(preMods.length)]);
		return (word);
	}
	
	public static String preModifiersC(){
		String[] preMods = {"Hmm...","I guess","Looks like","Perhaps","Conversely"
		};
		String word = (preMods[new Random().nextInt(preMods.length)]);
		return (word);
	}
	
	//I feel 
	public static String preModifiersD(){
		String[] preMods = {"I feel the same","Lots of people feel that way","That's true, but","That's tough, but",
		};
		String word = (preMods[new Random().nextInt(preMods.length)]);
		return (word);
	}

	public static void listSimilarities(Word w){
		String similarities="";
		for(int i=0;i<w.similar.length;i++){
			similarities = similarities.concat(w.similar[i]+", ");
		}
	}
	
	public static String captializeFirstLetter(String input){
		return input.substring(0, 1).toUpperCase() + input.substring(1);
	}
	
	
	
}
