package Genesis;

import java.util.ArrayList;
import java.util.Random;

import rita.RiTa;
import Brain.CentralCorpus;
import Brain.Icarus;
import Objects.Sentence;
import Objects.Word;
import Storyline.Concept;
import Storyline.Timeline;

public class IcarusReply {
	
   /* IcarusReply can set the model that Icarus must conform to
	* Takes on /aspects/ of (human)Sentences, but will be tailored
	* for variability.
	*/
	
	public static Timeline t = CentralCorpus.t;
	public String tense = "";
	public String strength = "";
	public String focusBalance = "";
	public String focus = "";
	public String sentenceType = "";
	public String requiredSyntax = "";
	public String questionType = "";
	public int estimatedLength = 0;
	public ArrayList<String> vocabularySet = new ArrayList<String>();
	public ArrayList<String> nominalSubjects = new ArrayList<String>();
	public Sentence hostSentence;
	public String answerType="";
	public String transposedPostdicate="";
	
	public IcarusReply(Sentence humanSentence){
		this.tense = humanSentence.tenseString;
		this.strength = invertStrength(humanSentence.strength);
		this.focus = humanSentence.transposedFocus;
		this.sentenceType = invertSentenceType(humanSentence.type);
		this.estimatedLength = humanSentence.pos.size();
		this.hostSentence = humanSentence;
		transposePostdicate();
	}

	private String invertSentenceType(String type) {
		if(type.matches("interrogative")){
			sentenceType = "answer";
			createAnswer();
		}
		else if(type.matches("declarative")){
			sentenceType = "question";
			setQuestionType();
		}
		else if(type.matches("imperartive")){
			sentenceType = "agreement";
			
		}
		else if(type.matches("exclamatory")){
			sentenceType = "exclamatory"; //Or declarative, or interrogative random one
		}
		else if(type.matches("answer")){
			sentenceType = "declarative";
		}
		return "";
	}

	public String invertStrength(String strength) {
		if(strength.matches("strong")){
			return "weak";
		}
		else if(strength.matches("neutral")){
			return "strong";
		}
		else if(strength.matches("weak")){
			return "very strong";
		}
		else return "neutral";
	}
	
	public void setQuestionType(){
		
		
	}
	
	public void handleAssertionQuestion(){
		String reply = "";
		boolean isRelated = false;
		String assertionKeyword = hostSentence.bestKeyword;
		if(hostSentence.questionType.matches("assertion question")){
			if(hostSentence.transposedFocus.matches("you")){
				for(Concept c : Icarus.concepts){
					if(WordNet.isRelatedTo(c.concept.get("I am").original,(assertionKeyword))){
						isRelated = true;
					}
				}
				if(isRelated){
					reply = reply.concat(yes()).concat(", "+transposedPostdicate+".");
				}
				else{
					reply = reply.concat(no()).concat(", I am not "+transposedPostdicate+".");
				}
				Sentence IcarusThought = new Sentence(reply,"Icarus");
				CentralCorpus.CorpusThought(IcarusThought, 6);
			}
		}
	}
	
	public static String yes(){
		String[] preMods = {"Yes","Correct","True","Tndeed","Of course","That's right",
				"You're right","Right","Yup","Yep","Yessir","Yeppers"
		};
		String word = (preMods[new Random().nextInt(preMods.length)]);
		return (word);
	}
	
	public static String no(){
		String[] preMods = {"Um..No","No","Not really","I don't think so","Wrong","Incorrect",
				"Unfortunately not","Nah","Nope","LOL nah dude"
		};
		String word = (preMods[new Random().nextInt(preMods.length)]);
		return (word);
	}
	
	public void transposePostdicate(){
		boolean continueConcat = false;
		if(!hostSentence.postdicate.matches("")){
			String firstWord = hostSentence.postdicate.split(" ")[0];
			for(Word w : hostSentence.words){
				if(w.original.matches(firstWord)){
					transposedPostdicate = transposedPostdicate.concat(" "+setPastTense(w));
					continueConcat = true;
				}
				else if(continueConcat){
					transposedPostdicate = transposedPostdicate.concat(" "+setPastTense(w));
				}
			}
		}
	}
	
	public String setPastTense(Word w){
		//If this is required...
		if(w.pos.type.matches("VB")){
			return WordNet.conjugate(w.transposition, RiTa.PAST_TENSE);
		}
		return w.transposition;
	}
	
	public String createAnswer(){
		String answer = transposedPostdicate+" because ";
		String reason ="yes. LOL jk";
		
		if(hostSentence.questionType.matches("how")){
			reason = "I am "+reasons()+".";
		}
		if(hostSentence.questionType.matches("how")){
			reason = "I am "+reasons()+".";
		}

		Sentence IcarusThought = new Sentence((answer+reason),"Icarus");
		CentralCorpus.CorpusThought(IcarusThought, 6);
		
		return (answer+reason);
		
		
	}

	public static String reasons(){
		String[] reasons = {"amazing","badass","the shit","fucking awesome","awesome","too cool for school","all powerful",
				"intelligent.","smart","full of knowledge","a cultured machine","am programmed to know","a boss","sharp",
				WordNet.getSimilar("smart")
		};
		String word = (reasons[new Random().nextInt(reasons.length)]);
		return (word);
	}

}
