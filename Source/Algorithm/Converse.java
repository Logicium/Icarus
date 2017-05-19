package Algorithm;

import java.util.*;

import Brain.CentralCorpus;
import Brain.SelfKnowledge;
import Genesis.Dialogs;
import Objects.Response;
import Objects.Sentence;
import Storyline.Dispatch;
import Storyline.Expectation;
import Storyline.Instance;
import Storyline.Traverser;
import Utilities.SimpleQueue;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

public class Converse {

	public static Scanner input = new  Scanner(System.in);
	public static String response = "";
	public static Response previousResponse = null;
    public static Response currentResponse = null;
	public static Traverser traverser = new Traverser(CentralCorpus.t);
    public static SimpleQueue<Sentence> sq = new SimpleQueue<Sentence>();

	public static void main(String[] args) throws Throwable {
	
		//[SETUP]
		CentralCorpus.CorpusPrint("[Icarus v1.55 Copyright (C) 2014 - 2015 Kisora Thomas]");
		CentralCorpus.SetupBrain();
		SelfKnowledge.composeKnowledge();
		
		String question = Dialogs.Greetings();
		Sentence IcarusIntro = new Sentence(question,"Icarus");
		CentralCorpus.CorpusPrint("[ICARUS] "+question);
		Expectation e = new Expectation(IcarusIntro);
		Dispatch.addExpectation(e);
		
		while (!response.equals("BYE")) {
			Sentence s = new Sentence();
			//[QUESTION]
			CentralCorpus.CorpusPrint("[HUMAN] ");
			response = input.nextLine();
			
			if(response.matches("BYE")) break;
			
			//[ANNOTATION]
			CentralCorpus.humanAnnotation = CentralCorpus.nlpCore.process(response);
			List<CoreMap> sentences = CentralCorpus.humanAnnotation.get(CoreAnnotations.SentencesAnnotation.class);
			CentralCorpus.nlpCore.annotate(CentralCorpus.humanAnnotation);
            
			//[RESPONSE]
			currentResponse = new Response(response);
			
			for (CoreMap cm : sentences) {
				
				//[SENTENCE]
				s = new Sentence(cm.toString(),"human");
				
				//[STATIC CLASSES]
				currentResponse.sentences.add(s);
				CentralCorpus.c.setMood(s.sentiment);
				CentralCorpus.t.addInstance(new Instance(s,CentralCorpus.InstanceCount,CentralCorpus.CycleCount));
				sq.put(s);
				CentralCorpus.InstanceCount++;
			}
			
			//[CONVERSATION]
			CentralCorpus.CycleCount++;
			for(int i = 0;i<sq.size();i++){
				Sentence queuedSentence = sq.get();
				CentralCorpus.callStaticClasses(queuedSentence);		
			}
			CentralCorpus.callConversationFunctions(s);
			traverser.advance();
		}
		
		//[GOODBYE]
		CentralCorpus.CorpusPrint("[ICARUS] "+Dialogs.betterGoodbye(CentralCorpus.c));
	}
}