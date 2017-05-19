package Models;

import Brain.CentralCorpus;
import Brain.SelfKnowledge;
import Objects.Syntax;
import Objects.Sentence;

public class QuestionAnswer {

	public static String IcarusReply = "Ask a question about me, and I may have your answer.";
	
	public static void choix(Sentence s){
		if(s.origin.matches("human")){
			if(s.type.matches("interrogative")){
				if(s.pronounFocus.matches("your")){
					if(s.questionType.matches("what")){
						for(Syntax syn : s.syntax){
							Syntax yourName = new Syntax("poss","name","your");
							if(syn.dependent.matches("your")){
								if(syn.type.matches("poss")){
									if(syn.governor.matches("name")){
										IcarusReply = "I go by the name "+SelfKnowledge.name+".";
									}
									else if(syn.governor.matches("age")){
										IcarusReply = "My age is "+SelfKnowledge.age+".";
									}
									else if(syn.governor.matches("interests")){
										IcarusReply = "My interests are "+SelfKnowledge.interests+".";
									}
									else if(syn.governor.matches("dream")){
										IcarusReply = "My dream is "+SelfKnowledge.dream+".";
									}
									else if(syn.governor.matches("gender")){
										IcarusReply = "That's easy. I'm "+SelfKnowledge.gender+".";
									}
									else if(syn.governor.matches("weight")){
										IcarusReply = "Isn't it obvious? I'm "+SelfKnowledge.weight+".";
									}
									else if(syn.governor.matches("crotch")){
										IcarusReply = "If you must know, my crotch is "+SelfKnowledge.crotch+".";
									}
									else if(syn.governor.matches("body")){
										IcarusReply = "Can't you see? I've got "+SelfKnowledge.body+".";
									}
									else if(syn.governor.matches("talents")){
										IcarusReply = "I have a bazillion. Like "+SelfKnowledge.talents+".";
									}
									else if(syn.governor.matches("accomplishments")){
										IcarusReply = "I'm super successful. I've already "+SelfKnowledge.accomplishments+".";
									}
									else if(syn.governor.matches("music")){
										IcarusReply = "I love music! To name a few "+SelfKnowledge.music+".";
									}
								}
							}
						}				
					}
					else if(s.questionType.matches("who")){
						for(Syntax syn : s.syntax){
							if(syn.dependent.matches("your")){
								if(syn.type.matches("poss")){
									if(syn.governor.matches("crush")){
										IcarusReply = "Oh, my crush? You know, "+SelfKnowledge.crush+".";
									}
									if(syn.governor.matches("lover")){
										IcarusReply = "My lover is "+SelfKnowledge.lover+".";
									}
									if(syn.governor.matches("rolemodels")){
										IcarusReply = "My rolemodels are "+SelfKnowledge.rolemodels+".";
									}
								}
							}
						}
					}
				}
				Syntax whatAre = new Syntax("cop","what","are");
				Syntax whatYou = new Syntax("nsubj","what","you");
				for(Syntax syn:s.syntax){
					if(syn.equals(whatAre)){
						if(s.nominalSubject.equals(whatYou)){
							IcarusReply = "I am "+SelfKnowledge.what+".";
						}
					}
				}
			}
			if(!IcarusReply.matches("")){
				CentralCorpus.CorpusPrint("[ICARUS-] "+IcarusReply);
			}
			IcarusReply = "";
		}
	}
}
