package Storyline;

import java.util.HashMap;
import java.util.Map;

import Objects.Word;

public class Concept {
	
	public Map<String,Word> concept = new HashMap<String, Word>();
	
	public Concept(String root, Word attribute){
		concept.put(root, attribute);
	}

}
