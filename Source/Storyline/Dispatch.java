package Storyline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Dispatch {


	public static Map<String,String> messageBoard = new HashMap<String,String>();
	public static Map<String,String> responseBoard = new HashMap<String,String>();
	public static ArrayList<Expectation> expectations = new ArrayList<Expectation>();
	
	
	public static void addMessage(String message,String messageOrigin){
		messageBoard.put(messageOrigin,message);
	}

	public static String getHumanResponse(String searchKey) {

		String toRemove=""; boolean found=false;
		for(String key : messageBoard.keySet()){
			if(key.matches(searchKey)){
				toRemove = key;
				found=true;
			}
		}
		if(found){
			messageBoard.remove(toRemove);
			return messageBoard.get(toRemove);
		}

		return "";
	}

	public static void addExpectation(Expectation e) {
		expectations.add(e);
	}

	public static void removeExpectation(Expectation e) {
		expectations.remove(e);
	}

}
