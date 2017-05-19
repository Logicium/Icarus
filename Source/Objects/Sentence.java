package Objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Storyline.Concept;
import Utilities.SyntaxComparator;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import Algorithm.Converter;
import Algorithm.SyntaxParser;
import Algorithm.Tagger;
import Brain.CentralCorpus;
import Brain.SelfKnowledge;
import Genesis.WordNet;

public class Sentence {

	public String rawText="";
	public String origin="";
	public String transposition = "";
	public String transposedFocus = "you";
	public String sentiment="";
	public String strength = ""; //Find a way to calculate this. Potentially machine trainable. 	
	public String posPattern="";
	public String subject="";
	public String verb="";
	public String object="";
	public String adj="";
	public String type=""; //Declarative, Interrogative, Exclamatory, Imperative
	public String questionType="";	
	public String pronounFocus=""; //You, him, her, me, I, them, they, one, we, things, it, that. 
	public String tenseString="";
	public String syntaxPattern="";
	public String predicate ="";
	public String postdicate="";
	public ArrayList<String> regions = new ArrayList<String>();
	public ArrayList<Word> words = new ArrayList<Word>();
	public ArrayList<String> wordQuestions = new ArrayList<String>();
	public ArrayList<String> tokens = new ArrayList<String>();
	public ArrayList<String> organizations = new ArrayList<String>();
	public ArrayList<String> names = new ArrayList<String>();
	public ArrayList<PartOfSpeech> pos = new ArrayList<PartOfSpeech>();
	public ArrayList<Syntax> syntax = new ArrayList<Syntax>();
	public ArrayList<PartOfSpeech> verbs = new ArrayList<PartOfSpeech>();
	public ArrayList<PartOfSpeech> nouns = new ArrayList<PartOfSpeech>();
	public ArrayList<PartOfSpeech> adjectives = new ArrayList<PartOfSpeech>();
	public ArrayList<PartOfSpeech> pronouns = new ArrayList<PartOfSpeech>();
	public ArrayList<Sentence> syntaxExpansions = new ArrayList<Sentence>();
	public ArrayList<Word> keywords = new ArrayList<Word>();
	public String[] elements;
	public Syntax negation;
	public Syntax possession;
	public Syntax nominalSubject;
	public boolean isAssertion = false;
	public Tense tense = Tense.BASE_FORM;
	public Annotation annotation;
	public int presidence=0;
	public String bestKeyword;
	
	public enum Tense {
		BASE_FORM,PAST_TENSE,PRESENT_PARTICIPLE,
		PAST_PARTICIPLE,PRESENT_TENSE,PT3PS
	}
	
	public Sentence(String response, String origin) {
		this.rawText = response;
		this.origin = origin;
		this.elements = response.split(" ");
		for(String e: elements){
			Word w = new Word(e.toLowerCase().replace(",", "").replace(".", ""));
			this.words.add(w);
		}
		createNLPCore(CentralCorpus.nlpCore);
		callInternalAnalysis();
	}
	
	public Sentence() {
		//For silly initilization purposes only.
	}

	public void createNLPCore(StanfordCoreNLP nlpCore){
		Annotation newAnno = nlpCore.process(rawText);
		nlpCore.annotate(newAnno);
		CoreMap cm = newAnno.get(CoreAnnotations.SentencesAnnotation.class).get(0);
		//Parts of Speech
		Tagger t = new Tagger();
		t.tag(this);
		//Syntax
		generateSyntax(cm);
		//Tokens
		CentralCorpus.Tokenize(cm, this);
		//Sentiment
		sentiment = cm.get(SentimentCoreAnnotations.SentimentClass.class);
	}

	public void generateSyntax(CoreMap cm){
		SemanticGraph graph = cm.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
		SyntaxParser syn = new SyntaxParser();
		syn.syntaxAnalysis(graph.toString(SemanticGraph.OutputFormat.LIST),this);
	}
	
	
	public void callInternalAnalysis(){
		sortSyntax();
		setType(this);
		setPronounFocus();
		transpose();
		detectNegation();
		detectPossession();
		detectSubject();
		populateWords();
		syntaxPatternToString();
		extractTense();
		extractDicates();
	}

	public void setRegions(){

		regions = (ArrayList<String>) Arrays.asList(rawText.split(","));
		//Detect words at start of sentence, like interjections
		//Also, check for listing usage of commas.
		//Print regions for INFO
	}
	
	public void setKeywords(){
		for(int i =0;i<syntax.size();i++){
			Syntax d = syntax.get(i);
			if(d.type.matches("like")){
				keywords.add(words.get(i));
				CentralCorpus.CorpusPrint("[INFO] Keywords:"+d.dependent);
			}
			else if(d.type.matches("amod")){
				keywords.add(words.get(i));
				CentralCorpus.CorpusPrint("[INFO] Keywords:"+d.dependent);
			}
			else if(d.type.matches("ccomp")){
				keywords.add(words.get(i));
				CentralCorpus.CorpusPrint("[INFO] Keywords:"+d.dependent);
			}
			
			
		}
	}	
	
	
	public void populateWords(){
		for(int i =0;i<words.size();i++){
			Word w = words.get(i);
			w.pos = pos.get(i);
			//w.syntax = syntax.get(i); //DEBUG THIS. The roots have a tendency to come first. :/
			w.sentiment = w.getSentiment(); //Call from CentralCorpus
			w.hypernyms = w.getHypernyms();
			w.holonyms = w.getHolonyms();
			w.synonyms = w.getSynonyms();
			w.antonyms = w.getAntonyms();
			w.similar = w.getSimilar();
			w.examples = w.getExamples();
			w.setQuestionAnswerType();
		}
	}
	
	public void sortSyntax() {
		  SyntaxComparator comparator = new SyntaxComparator();
		  Collections.sort(syntax, comparator);
	}
	
	public void generatePOSPattern(){
		for(PartOfSpeech p: pos){
			posPattern.concat(p.type).concat(" ");
		}
		CentralCorpus.CorpusPrint("[INFO] The sentence's POS Pattern is: "+posPattern);
	}
	
	public void addSyntaxExpansion(String s) throws Throwable{
		Sentence r = new Sentence(s,"Icarus");
		Tagger t = new Tagger();
		t.tag(this);
		Converter.generatePOS(r);
		syntaxExpansions.add(r);
	}
	
	@SuppressWarnings("unused")
	private void printData() {
		CentralCorpus.CorpusPrint("Dependencies :"+syntax.size());
		CentralCorpus.CorpusPrint("Verbs "+verbs.size());
		CentralCorpus.CorpusPrint("Nouns "+nouns.size());
		CentralCorpus.CorpusPrint("Tokens "+tokens.size());		
	}
	
	public void extractTense(){		
		
		for(Syntax d : syntax){
			if(d.type.matches("vb")){
				tense = Tense.BASE_FORM;
				tenseString = "baseForm";
			}
			else if(d.type.matches("vbd")){
				tense = Tense.PAST_TENSE;
				tenseString = "pastTense";
			}
			else if(d.type.matches("vbg")){
				tense = Tense.PRESENT_PARTICIPLE;
				tenseString = "presentParticiple";
			}
			else if(d.type.matches("vbn")){
				tense = Tense.PAST_PARTICIPLE;
				tenseString = "partParticiple";
			}
			else if(d.type.matches("vbp")){
				tense = Tense.PRESENT_TENSE;
				tenseString = "presentTense";
			}
			else if(d.type.matches("vbz")){
				tense = Tense.PT3PS;
				tenseString = "presentTense3rdPersonSingular";
			}	
		}
		CentralCorpus.CorpusPrint("[INFO] This sentence is in "+tense);
	}
	
	public void syntaxPatternToString(){
		for(Syntax d:syntax){
			syntaxPattern = syntaxPattern.concat(d.type).concat(" ");
		}
	};
	
	public void setType(Sentence s){
		
		if(isImperative(s)){
			s.type = "imperative";
		}
		else if(isExclamatory(s)){
			s.type = "exclamatory";
		}
		else if(isQuestion(s)){
			s.type = "interrogative";
		}
		else{
			s.type = "declarative";
		}
		CentralCorpus.CorpusPrint("[INFO] This sentence is type: "+s.type+".");
	}

	private boolean isQuestion(Sentence s) {
		//If the final punctuation is a question mark
		//CentralCorpus.CorpusPrint(s.pos.get(s.pos.size()-1).root);
		if(s.pos.get(s.pos.size()-1).root.matches("\\?")) return true;
		else return false;
	}

	private boolean isImperative(Sentence s) {
		//If the first word is a Verb base form or VB Present tense
		if(s.pos.get(0).type.matches("VB") || s.pos.get(0).type.matches("VBP") ) return true;
		else return false;
	}

	private boolean isExclamatory(Sentence s) {
		// If the final punctuation is an exclamation mark
		//CentralCorpus.CorpusPrint(s.pos.get(s.pos.size()-1).root);
		if(s.pos.get(s.pos.size()-1).root.matches("\\!")) return true;
		else return false;
	}
	
	public void extractDicates(){
		
		System.out.println("[DEBUG "+origin.toUpperCase()+"] SyntaxPattern: "+syntaxPattern);
		System.out.println("[RAW] "+rawText);
		Pattern predicatePattern = Pattern.compile("(discourse )?(nsubj )(aux )?(cop)");
		Matcher m = predicatePattern.matcher(syntaxPattern);
		String matchedElements;
		if(m.find()){
			matchedElements = m.group();
			System.out.println(matchedElements);
			boolean predicateFlag=true;
			for(Syntax d:syntax){
				
				if(predicateFlag){
					if(!d.type.matches("discourse")){
					predicate = predicate.concat(d.dependent+" ");
					}
				}
				if(!predicateFlag){
					postdicate = postdicate.concat(d.dependent+" ");
				}
				if(d.type.matches("cop")){
					predicateFlag = false;
				}
			}
			isAssertion = true; 
			CentralCorpus.CorpusPrint("[INFO] Predicate is: "+predicate);
			CentralCorpus.CorpusPrint("[INFO] Postdicate is: "+postdicate);	
		}		
	}
	
	public void setStrength(){
		
		//Check for weak words
		//[WEAK] maybe, I suppose, I guess, perhaps, 
		//Check for strong words
		//[STRONG] 
		
	}
	
	public void setPrecedence(int p){
		presidence = p;
	}
	
	public void setPronounFocus(){

		//Improve by creating an ArrayList of pronoun foci. 
		Pattern verbPattern = Pattern.compile("(prp)|(prp\\$)|(dt)");
		for(PartOfSpeech pos:pos){
			//CentralCorpus.CorpusPrint("[Debug] Root: "+pos.root +" and Part: "+ pos.pos);
			Matcher pronounMatcher = verbPattern.matcher(pos.type.toLowerCase());
			if(pronounMatcher.matches()){
				pronouns.add(pos);
				pronounFocus = pos.root;
				CentralCorpus.CorpusPrint("[INFO] Detected the pronoun focus: "+pronounFocus);
				if(pronounFocus.matches("I") || pronounFocus.matches("me") || pronounFocus.matches("my")){
					if(origin.matches("human")){ 
						transposedFocus = "you"; 
						CentralCorpus.CorpusPrint("[INFO] So this is all about "+transposedFocus+"?");
					}
				}
				else if(pronounFocus.toLowerCase().matches("your") || pronounFocus.toLowerCase().matches("you")){
					if(origin.matches("human")){ 
						transposedFocus = "me"; 
						CentralCorpus.CorpusPrint("[INFO] So this is all about "+transposedFocus+"?");
					}
				}
				else if(pronounFocus.toLowerCase().matches("him") || pronounFocus.toLowerCase().matches("his")){
					CentralCorpus.CorpusPrint("[INFO] So this is all about a GUY?");
				}
				else if(pronounFocus.toLowerCase().matches("her") || pronounFocus.toLowerCase().matches("hers")||pronounFocus.toLowerCase().matches("she")){
					CentralCorpus.CorpusPrint("[INFO] So this is all about a GIRL?");
				}
				else if(pronounFocus.toLowerCase().matches("this") || pronounFocus.toLowerCase().matches("that") || pronounFocus.toLowerCase().matches("it")){
					CentralCorpus.CorpusPrint("[INFO] So this is all about IT/THIS/THAT?");
				}
				else if(pronounFocus.toLowerCase().matches("them") || pronounFocus.toLowerCase().matches("they")){
					CentralCorpus.CorpusPrint("[INFO] So this is all about OTHERS?");
				}
				else if(pronounFocus.toLowerCase().matches("we") || pronounFocus.toLowerCase().matches("us")){
					CentralCorpus.CorpusPrint("[INFO] So this is all about the GROUP?");
				}
			}
		}
	}

	public void transpose(){
		if(origin.matches("human")){
			for(int i = 0; i<elements.length;i++){
				if(elements[i].matches("I")){
					transposition = transposition.concat("you ");
					words.get(i).transposition = "you";
				}
				else if(elements[i].toLowerCase().matches("my")){
					transposition = transposition.concat("your ");
					words.get(i).transposition = "your";
				}
				else if(elements[i].toLowerCase().matches("your")){
					transposition = transposition.concat("my ");
					words.get(i).transposition = "my";
				}
				else if(elements[i].toLowerCase().matches("you")){
					transposition = transposition.concat("I ");
					words.get(i).transposition = "I";
				}
				else if(elements[i].toLowerCase().matches("was") && pronounFocus.matches("I")){
					transposition = transposition.concat("were ");
					words.get(i).transposition = "were";
				}
				else if(elements[i].toLowerCase().matches("were") && pronounFocus.matches("you")){
					transposition = transposition.concat("was ");
					words.get(i).transposition = "was";
				}
				else if(elements[i].toLowerCase().matches("am")){
					transposition = transposition.concat("are ");
					words.get(i).transposition = "are";
				}
				else if(elements[i].toLowerCase().matches("are") && !(isCopula("are"))){
					transposition = transposition.concat("am ");
					words.get(i).transposition = "am";
				}
				else{
					transposition = transposition.concat(elements[i].toLowerCase()+" ");
				}
			}
			CentralCorpus.CorpusPrint("[INFO] Transposition: So, "+transposition);
		}
	}
	
	public boolean isCopula(String dep){
		for(Syntax d : syntax){
			if(d.dependent.matches(dep) && (d.type.matches("cop"))){
				return true;
			}
		}
		return false;
	}
	
	public void detectNegation(){
		for(Syntax d : syntax){
			if(d.type.matches("neg")){
				negation = d;
				CentralCorpus.CorpusPrint("[INFO] Negation detected: "+negation.dependent);
			}
		}
	}
	
	public void detectPossession(){
		for(Syntax d : syntax){
			if(d.type.matches("poss")){
				possession = d;
				for(Syntax d2:syntax){
					if(d2.type.matches("of") && d2.governor.matches(possession.governor)){
						possession.governor = d2.dependent;
					}
				}
				CentralCorpus.CorpusPrint("[INFO] Possession detected: "+possession.governor);	
				CentralCorpus.CorpusPrint("[INFO] So, the "+possession.governor+" belongs to "+transposedFocus+"?");
				//Concept c = new Concept(); //Create this concept to register the posession. 
				if(transposedFocus.matches("me")){
					for(String key : SelfKnowledge.selfKnowledge.keySet()){
						if(key.matches(possession.governor)){
							String thought = ("I know something about that, my "
									+WordNet.conjugate(possession.governor)
									+" is "+SelfKnowledge.selfKnowledge.get(key)+".");
							
							Sentence IcarusSentence = new Sentence(thought,"Icarus");
							IcarusSentence.createNLPCore(CentralCorpus.nlpCore);
							CentralCorpus.CorpusThought(IcarusSentence,5);
						}
					}
				}
			}
		}		
	}
	
	public void detectSubject(){
		for(Syntax d : syntax){
			if(d.type.matches("nsubj")){
				nominalSubject = d;
				CentralCorpus.CorpusPrint("[INFO] Subject detected: "+nominalSubject.dependent);
			}
		}		
	}
	
	public int numericalSentiment(){
		int sentimentInt = 0;
		if(sentiment.matches("very positive")){
			sentimentInt += 2;
		}
		else if(sentiment.matches("positive")){
			sentimentInt += 1;
		}
		else if(sentiment.matches("neutral")){
			sentimentInt += 0;
		}
		else if(sentiment.matches("negative")){
			sentimentInt += -1;
		}
		else if(sentiment.matches("very negative")){
			sentimentInt += -2;
		}
		CentralCorpus.CorpusPrint("[INFO] Sentiment: "+sentimentInt);
		return sentimentInt;
	}
	
	public void printDependencies(){
		for(Syntax d1: syntax){
			CentralCorpus.CorpusPrint(d1.toString());
		}
	}
}


