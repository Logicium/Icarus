package Genesis;

import java.util.Random;

import Objects.Conversation;
import Objects.Sentence;

public class Dialogs {
	
	public static String betterGoodbye(Conversation c){
		String bye = "";
		
		if(c.chatLength==0){
			bye = "Dude. You're leaving before we even started! Bye then!";
		}
		else if(c.chatLength==1){
			bye = "Whoa, you're leaving now? We just started.";
		}
		else if(c.chatLength==2){
			bye = "Yeah, I've got things to do too. Later.";
		}
		else if(c.chatLength==3){
			bye = "Okay bye! See ya later!";
		}
		else if(c.chatLength==4){
			bye = "Just when I was starting to know you. Haha! Bye!";
		}
		else if(c.chatLength==5){
			bye = "I hope to hear from you again soon, fella!";
		}
		else if(c.chatLength==6){
			bye = "I'll be thinking about what you said.";
		}
		else if(c.chatLength<6){
			bye = "That was a good chat. See you later!";
		}
		return bye;
	}
	
	
	
	public static String nameComment(String inName) {
		String nameDialogue[] = { ("Hm..." + inName + "...I like it!"),
				(inName + " is a nice name."),
				(inName + " has a nice ring to it."),
				("Interesting name, " + inName + " is!"),
				("I quite like the sound of " + inName + "."),
				("Ah! I wonder where the name " + inName + " comes from."),
				("How exotic a name!") };
		String observation = (nameDialogue[new Random()
				.nextInt(nameDialogue.length)]);
		return ("[ICARUS] "+observation);
	}
	
	public static String Greetings(){
		String[] Greetings = {"Hello! How are you?", "Hi, I'm Icarus, what's your name?",
				"Greetings, human.","Hey, what's up?", "Hi there!","Hello!","Hey, how's it going?",
				"Look, a human! How are you?", "Good day! How's it going?", "How are you doing?",
				"How are you doing today?","Hello. What's been going on with you?","Good day, human.",
				"The name's Icarus. What's yours?", "Hello. My name is Icarus.","I just woke up. Who are you?"				
		};
		String word = (Greetings[new Random().nextInt(Greetings.length)]);
		return word;
	}
	
	public static String Goodbye() {
		String Goodbye[] = { "Goodbye now!", "Talk to you later!", "See ya!",
				"A tout a l'heure!", "Have a good one!", "Later!",
				"Nice talking with you!", "Until we meet again!",
				"Until another day!", "Bye-bye!", "Ciao!", "Farewell.",
				"Later!", "Peace out.", "Take care!", "Bye! Stay inspired!",
				"Bye! Keep your head up!","So long." };
		String word = (Goodbye[new Random().nextInt(Goodbye.length)]);
		return ("[ICARUS] "+word);
	}
	
	public static String skipPhrases() {
		String skipPhrases[] = { "Hmm...I haven't looked at it that way.",
				"Ah...That's interesting.", "Is that so?",
				"I wonder what makes you say that...",
				"I'm going to ask you about this later.",
				"Ah, I'll commit that to memory.", "Hm. Noted.",
				"Alright then.", "If you say so.", "Okie dokie." };
		String word = (skipPhrases[new Random().nextInt(skipPhrases.length)]);
		return ("[ICARUS] "+word);
	}
	
	
	public static String conversationDriver(Conversation c, Sentence s){
		//List of 'things to say next', after one iteration of sentence exchange has completed. 
		String drivers[] = { "Okay, go on...","Tell me more.",("Continue, "+postModifiers()+"."), 
				("This is a "+conversationMood(c)+" conversation!"),"What happened next?", 
				"What *(did you do) after that?","I want to know more.",
				("How is that working out for "+s.transposedFocus+"?"),("Keep talking, "+postModifiers()+"."), "I'm listening.", 
				("I "+getSentimentVerb(c)+" this story."), "Care to divulge?"
		};
		String driver = (drivers[new Random().nextInt(drivers.length)]);
		return ("[ICARUS] "+driver);
	}
	
	public static String postModifiers() {
		String postMods[] = { "if you would","","if you have time",
				"please", "if you can", "continue","I'm listening"};
		String word = (postMods[new Random().nextInt(postMods.length)]);
		return (word);
	}
	
	public static String advancedPostModifiers(){
		return "";
	}
	
	public static String getSentimentVerb(Conversation c){
		
		if(c.chatMood>0){
			return WordNet.getHypernym("appreciate");
		}
		else if(c.chatMood<0){
			return WordNet.getHyponym("hate","v");
		}
		else 
			return WordNet.getSimilar("neutral");
		
	}
	
	
	public static String addPronounClause(Sentence s){
		if(s.transposedFocus.toLowerCase().matches("you")&&!(s.verbs.isEmpty())){
//			for(Word w: s.words){
//				
//			}
		}
		return null;
	}
	
	public static String conversationMood(Conversation c){
		if(c.chatMood>0){
			return WordNet.getSimilar("happy");
		}
		else if(c.chatMood<0){
			return WordNet.getSimilar("sad");
		}
		else return WordNet.getSimilar("neutral");
	}
	
	public static String conversationDialogs(Conversation c){
		String remark="";
		if(c.conversationQuadrant.matches("start")){
			remark = "I *like where this is going...";
			return remark;
		}
		else if(c.conversationQuadrant.matches("middle")){
			remark = "This conversation is really *good...";
			return remark;
		}
		else if(c.conversationQuadrant.matches("end")){
			remark = "We should be wrapping it up by now...";
		}
		return ("[ICARUS-] "+remark); 		
	}
	
	
	
	

}
