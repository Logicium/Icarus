package Storyline;

import Objects.Sentence;

public class Expectation {

	
	//Expectations can be focused or broad, fulfilling what a Icarus expects
	//Based on the conversation history, his last statement, etc
	//Expectations are passed to the Dispatch for Confirmation
	//Expectations are formed after Icarus makes a statement.
	//Expectations are placed one cycle ahead of the currentCycle
	
	
	public String expectedFocus = "";
	public String expectedTense = "";
	public boolean metExpectation = false;
	public boolean exists = true;
	
	public Expectation(Sentence IcarusSentence){
		if(IcarusSentence.pronounFocus.matches("")){
			System.out.println("NO FOCUS! :C");
			
			exists = false;
			return;
		}
		
		if(IcarusSentence.pronounFocus.matches("you")){
			expectedFocus = "I";
		}
		else if(IcarusSentence.pronounFocus.matches("I")){
			expectedFocus = "you";
		}
		expectedTense = IcarusSentence.tenseString;	
	}
	
	public boolean meetExpectation(Sentence humanReply){
		String resultFocus = humanReply.pronounFocus; 
		String resultTense = humanReply.tenseString;	
		if(resultFocus.matches(expectedFocus) && resultTense.matches(expectedTense)){
			metExpectation = true;
			return true;
		}
		return false;
	}
	
}
