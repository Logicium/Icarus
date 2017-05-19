package Storyline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Confirmation {

	//Confirmations can be configured to be flexible. 
	//They are meant to be Played across the Storyline
	
	public int cycleOrigin=0; //Automated
	public int cyclesRemaining=2; //Manually entered default is two
	public String hookMessage="";
	public ArrayList<String> hookMessages = new ArrayList<String>();
	public Map<String,String> hookOutcomes = new HashMap<String,String>();
	//Example: <{"true","See, I knew all along."},{"false","Well fuck. I thought I was right."}
	
	public Confirmation(int cycleOrigin,int totalCycles, String hook, HashMap<String,String> hookOutcomes){
		this.cycleOrigin = cycleOrigin;
		this.cyclesRemaining = totalCycles;
		this.hookMessage = hook;
		this.hookOutcomes = hookOutcomes;
	}
	
	public String play(int currentCycle){
		if(cycleOrigin==currentCycle){
			cyclesRemaining--;
			return hookMessage;
		}
		else if(currentCycle>cycleOrigin){
			String outcome = Dispatch.getHumanResponse(hookMessage);
		}
		
		return "";
	}
	
}
