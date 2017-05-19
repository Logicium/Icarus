package Genesis;
import java.util.Random;


public class NERQuestioner {

	
	public static String People(String inName){
		String people[] = { ("So...Who's "+inName+ "?"), ("How long have you known " +inName +"?"), 
				("How is " +inName+" related to you?"), ("Can I add "+inName+" on Facebook?"),
				("How much do you think about "+inName+"?"),
			    ("How much do you like " +inName+"?"), 
				("Do you and " +inName+ " argue?"), ("Do you and " +inName+" have common interests?"),
				("Where did you meet "+inName+"?"), ("Has " +inName+ " done anything important?"), ("What do you think of "+inName+"?")
				,("How has "+inName+" contributed to their community? "),("Is "+inName+" your best friend?")};
		String word = (people[new Random().nextInt(people.length)]);
		return word;
	}
	
	public static String Organizations(String orgName) {
		String orgs[] = { "What is this organization doing? ", "What is your connection between this organization? ", ("Can you tell me more about organization "+orgName+" ? "),
				("How are you involved with the organization "+orgName+"?"), ("What does the "+orgName+"do?"), ("What are your thoughs on the " +orgName+"?")};
		String word = (orgs[new Random().nextInt(orgs.length)]);
		return word;
	}
	
	/*
	 * Read in the POS.
	 * Count each POS.
	 * Isolate the lowest ones, and ask question based on the lacking information.
	 * Few verbs = ask what you were doing.
	 * Few adjectives = ask how it was done.
	 * Few nouns = ask what was being done.
	 * Few pronouns = ask who else was involved.
	 * Few conjunctions = ask for more information.
	 * 
	 */
	
	
	
	
}
