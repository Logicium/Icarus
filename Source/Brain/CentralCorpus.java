package Brain;

import java.util.concurrent.TimeUnit;
import java.util.*;
import Algorithm.Converse;
import Algorithm.Converter;
import Algorithm.Tokenizer;
import Genesis.Dialogs;
import Models.Declarative;
import Models.Exclamatory;
import Models.Imperative;
import Models.Question;
import Models.QuestionAnswer;
import Objects.Conversation;
import Objects.Delta;
import Objects.Response;
import Objects.Sentence;
import Storyline.Instance;
import Storyline.Timeline;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.util.ArrayCoreMap;
import edu.stanford.nlp.util.CoreMap;


public class CentralCorpus {

	/* From this point on, the Central Corpus is responsible for taking in all of Icarus's replies
	 * For further analyis. Considering the machine needs to select one or two as a reply. 
	 * */
	
	public static boolean printIcarus = true;
	public static boolean printInfo = true;
	public static boolean printDebug = false;
	public static long printSpeed=25;
	public static Timeline t = new Timeline();
	public static Properties props = new Properties();
	public static StanfordCoreNLP nlpCore;
	public static Annotation humanAnnotation;
	public static Annotation IcarusAnnotation;
	public static MaxentTagger PartsOfSpeechTagger;
	public static Conversation c = new Conversation();
	public static Response previousResponse = null;
	public static Response currentResponse = null;
	public static int CycleCount = 1;
	public static int InstanceCount = 1;
	
	public static void SetupBrain(){
		props.setProperty("annotators" , "tokenize, ssplit, pos, lemma, parse, sentiment, ner");
		nlpCore = new StanfordCoreNLP(props);
		PartsOfSpeechTagger = new MaxentTagger("Datasets\\taggers\\english-left3words-distsim.tagger");
	}
	
	public static void CorpusPrint(String toConsider){
		String firstWord = toConsider.split(" ")[0];
		
		if(firstWord.matches("\\[ICARUS\\]") && printIcarus){
			seriouslyConsider(toConsider);
		}
		else if(firstWord.matches("\\[ICARUS-\\]")){
			System.out.print("\n"+toConsider);
		}
		else if(firstWord.matches("\\[HUMAN\\]")){
			print(toConsider);
		}
		else if(firstWord.matches("\\[Icarus")){
			System.out.println(toConsider);
		}
		else if(firstWord.matches("\\[INFO\\]")&& printInfo){
			print(toConsider);
		}
		else if(firstWord.matches("\\[DEBUG\\]")&& printDebug){
			System.out.println("\n"+toConsider);
		}
	}
	
	public static void seriouslyConsider(String considering){
		String name = "[ICARUS] "; 
		considering = considering.replace(name, "");

		//DELETE THIS CRAP. Sentences now generate their own metadata.
		
		Sentence s = new Sentence(considering,"Icarus");
		callStaticClasses(s);
		t.addInstance(new Instance(s,InstanceCount,CycleCount));
		InstanceCount++;
		
		//[CONVERSATION]
		if(!(previousResponse==null)){
			//CentralCorpus.c.addDelta(new Delta(previousResponse,currentResponse)); <-- Must create a Cross-Compatible Delta object
		}
		CentralCorpus.c.responses.add(currentResponse);
		previousResponse = currentResponse;
		
		if(redundancyAnalysis()){
			print(name.concat(considering));
		}
		else if(!redundancyAnalysis()){
			CorpusPrint(Dialogs.conversationDriver(c, 
				t.currentCycle.instances.get(
				t.currentCycle.instances.size()-1).s));
		} //Perform other analysis.
	}

	public static void print(String data) {
		String newLine = "\n";
		newLine = newLine.concat(data);
		data = newLine;
		TimeUnit unit = TimeUnit.MILLISECONDS;
		long delay = printSpeed;
		for (char ch : data.toCharArray()) {
			System.out.print(ch);
			try {
				unit.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void CorpusThought(Sentence IcarusThought, int priority){
		IcarusThought.setPrecedence(priority);
		Thoughts.addThought(IcarusThought);
	}
	
	public static void Tokenize(CoreMap sentence, Sentence s){
		for (CoreMap token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
			ArrayCoreMap aToken = (ArrayCoreMap) token;
			s.tokens.add(aToken.toShorterString());
		}
		Tokenizer.parseTokens(s.tokens,s);
	}
	
	public static void callSentenceFunctions(Sentence s){
		s.sortSyntax();
		s.setPronounFocus();
		s.transpose();
		s.detectNegation();
		s.detectPossession();
		s.detectSubject();
		s.populateWords();
		s.syntaxPatternToString();
		s.extractTense();
		s.extractDicates();
	}
	
	public static void callStaticClasses(Sentence s){
		Converter.generatePOS(s);
		Question.setQuestionType(s);
		Exclamatory.simpleReply(s);
		Declarative.simpleReply(s);
		Declarative.detectHumanAssertion(s);
		Imperative.simpleReply(s);
		QuestionAnswer.choix(s);
		//SyntaxExpasion.transformAll(s);
	}
	
	
	public static void callConversationFunctions(Sentence s){
		if(!(Converse.previousResponse==null)){
			CentralCorpus.CorpusPrint("[INFO] Delta previous: "+Converse.previousResponse.sentences.get(0).rawText);
			CentralCorpus.CorpusPrint("[INFO] Delta current: "+Converse.currentResponse.sentences.get(0).rawText); 
			CentralCorpus.c.addDelta(new Delta(Converse.previousResponse,Converse.currentResponse));
		}
		CentralCorpus.c.responses.add(Converse.currentResponse);
		CentralCorpus.c.chatLength++;
		CentralCorpus.c.setConversationQuadrant();
		Converse.previousResponse = Converse.currentResponse;
		CentralCorpus.CorpusPrint(Dialogs.conversationDriver(CentralCorpus.c,s));
	}
	
	public static List<String> sortList = new ArrayList<String>();
	
	public static boolean redundancyAnalysis(){
		
		@SuppressWarnings("unused")
		boolean noRedundancies = true;
		Collections.sort(sortList);
		String previous = "";
		String current = "";
		ArrayList<Integer> toRemove = new ArrayList<Integer>();
		for(int i =0;i<sortList.size();i++){
			String s = sortList.get(i);
			current = s;
			if(current.matches(previous)){
				System.out.println("[INFO] Stopping myself from repeating myself.");
				System.out.println("[INFO] Removing: "+s);
				noRedundancies = false;
				toRemove.add(i);
				break;
			}
			previous = current;
		}
		for(int index = toRemove.size()-1;index>=0;index--){
			sortList.remove(index);
		}
		
		return true; ///change to the flag for the true behaviour
	}

	
}
