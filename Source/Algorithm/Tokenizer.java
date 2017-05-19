package Algorithm;

import java.util.ArrayList;
import Brain.CentralCorpus;
import Genesis.NERQuestioner;
import Objects.Sentence;

public class Tokenizer {

	public static ArrayList<String> orgVector = new ArrayList<String>();
	public static ArrayList<String> personVector = new ArrayList<String>();
	public static ArrayList<String> tokenElementsV = new ArrayList<String>();
	public static ArrayList<String> sentenceItems = new ArrayList<String>();
	public static ArrayList<String> tempVector = new ArrayList<String>();
	public static String[] tokenElements;
	public static String orgName = "";
	public static String people = "";
	
	public static void parseTokens(ArrayList<String> tokens, Sentence s) {
		
		for (int i = 0; i < tokens.size(); i++) {

			if (tokens.get(i).contains("[") && tokens.get(i).contains("]")) {
				tokens.get(i).replace("[", "");
				tokens.get(i).replace("]", "");
			}

			tokenElements = tokens.get(i).split(" ");			
			tokenElementsV = new ArrayList<String>();
			for (int k = 0; k < tokenElements.length; k++) {
				tokenElementsV.add(tokenElements[k]);
			}
			NER(tokenElementsV);
		}
		s.tokens = tokenElementsV;
		organizationRemarks(s);
		personRemarks(s);
	}

	public static void NER(ArrayList<String> tokenElementsV2) {
		
		for (int k = 0; k < tokenElementsV2.size(); k++) {
			
			if (tokenElementsV2.get(k).contains("NamedEntityTag=PERSON")) {
				// Search for the name of the person now
				String[] temp = tokenElementsV2.get(0).split("=");
				//For fun, you can do a quick web-search of the name 
				personVector.add(temp[1]);
			}
			if (tokenElementsV2.get(k).contains("NamedEntityTag=ORGANIZATION")) {
				// Search for the name of the organization
				String[] temp = tokenElementsV2.get(0).split("=");
				orgVector.add(temp[1]);
			}
		}
	}

	public static void organizationRemarks(Sentence s) {
		if (!orgVector.isEmpty()) {
			for (int j = 0; j < orgVector.size(); j++) {
				orgName = orgName.concat((String) orgVector.get(j).toString()).concat(" ");
			}
			CentralCorpus.CorpusPrint("[INFO] So, I bet that organization is called: "+ orgName);
			String thought = NERQuestioner.Organizations(orgName);
			Sentence IcarusThought = new Sentence(thought, "Icarus");
			CentralCorpus.CorpusThought(IcarusThought,4);
			s.organizations.add(orgName);
		}
	}

	public static void personRemarks(Sentence s) {
		if (!personVector.isEmpty()) {
			if (personVector.size() == 1) {
				for (int j = 0; j < personVector.size(); j++) {
					people = people.concat((String) personVector.get(j).toString());
				}
				
				CentralCorpus.CorpusPrint("[INFO] The person I found was: " + people + ".");
				String thought = NERQuestioner.People(people);
				Sentence IcarusThought = new Sentence(thought, "Icarus");
				CentralCorpus.CorpusThought(IcarusThought,4);
				
			} else if (personVector.size() > 1) {
				for (int j = 0; j < personVector.size() - 1; j++) {
					people = people.concat((String) personVector.get(j).toString()).concat(", ");
					s.names.add(people);
				}
				people = people.concat("and ").concat((String) personVector.get(personVector.size()).toString());
				CentralCorpus.CorpusPrint("[INFO] The people I found were: " + people + ".");
				String thought = NERQuestioner.People(people);
				Sentence IcarusThought = new Sentence(thought, "Icarus");
				CentralCorpus.CorpusThought(IcarusThought,4);
			}
		}
	}
}
