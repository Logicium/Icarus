package Objects;
import java.util.Vector;

import Brain.CentralCorpus;


public class Conversation {

	public Vector<Response> responses = new Vector<Response>();
	Vector<String> topics = new Vector<String>();
	public Vector<Delta> deltas = new Vector<Delta>();
	public int chatLength = 0; 
	public int chatMood = 0; // 2,1,0,-1,-2
	public String chatFocus = "you"; //you, me, him, her, them, it, that/this. "This conversation is about +chatFocus+."
	public boolean previousSubjectConsistency = false;
	public boolean currentSubjectConsistency = false;
	public String conversationQuadrant = "start";
	
	public void setMood(String sentiment){
		if(sentiment.matches("very positive")){
			chatMood += 2;
		}
		else if(sentiment.matches("positive")){
			chatMood += 1;
		}
		else if(sentiment.matches("neutral")){
			chatMood += 0;
		}
		else if(sentiment.matches("negative")){
			chatMood += -1;
		}
		else if(sentiment.matches("very negative")){
			chatMood += -2;
		}
		CentralCorpus.CorpusPrint("[INFO] This conversation as a whole is: "+chatMood);
	}
	
	public void setConversationQuadrant(){
		if(chatLength>6){
			conversationQuadrant = "middle";
		}
		else if(chatLength>18){
			conversationQuadrant = "end";
		}
	}
	
	public void addDelta(Delta delta){
		deltas.add(delta);
		currentSubjectConsistency = delta.subjectConsistency;
		if(deltas.size()>1){
			if((currentSubjectConsistency == false) && (previousSubjectConsistency == true)){
				CentralCorpus.CorpusPrint("[INFO] Your sentence has NO subject, but the previous two did.");
			}
			else if((currentSubjectConsistency == true) && (previousSubjectConsistency == false)){
				CentralCorpus.CorpusPrint("[INFO] Your sentence has a subject, but the previous two didn't.");
			}
			else if((currentSubjectConsistency == previousSubjectConsistency)){
				if(currentSubjectConsistency == true){
					CentralCorpus.CorpusPrint("[INFO] The last three sentences had a subject present.");
				}
				else if(currentSubjectConsistency == false){
					CentralCorpus.CorpusPrint("[INFO] The last three sentences had NO consistent subject present.");
				}
			}
		}
		previousSubjectConsistency = currentSubjectConsistency;
	}	
}
