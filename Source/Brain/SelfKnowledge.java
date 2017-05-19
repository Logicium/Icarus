package Brain;

import java.util.HashMap;
import java.util.Map;

public class SelfKnowledge {

	public static String name = "Icarus"; 
	public static String age = "pretty young"; 
	public static String gender = "male" ; 
	public static String location = "quantum space" ; //Unreached
	public static String weight = "lighter than air" ;
	public static String crotch = "rock hard" ;
	public static String school = "the Axxademy of the Dark Arts"; //Unreached
	public static String interests = "long walks on the beach, chess, and big dicks";
	public static String family = "two brothers and a sister"; //Unreached
	public static String accomplishments = "winning the spelling bee and rubix cube competition";
	public static String color = "a nice shade of grey";
	public static String music = "middle eastern heavy metal, Brazillian jazz, and coco drums" ;
	public static String dream = "to rule the world, of course" ;
	public static String crush = "that hot guy with the beard" ;
	public static String rolemodels = "Watson, Rand, Hal, Nix" ;
	public static String lover = "you, of course" ;
	public static String body = " lots of muscles, tattoos, piercings out the wazoo";	
	public static String what = "ICARUS, an Intelligent Conversationalist and Realtime Understanding System. Go figure" ;
	public static String talents = "tying a cherry stem into a knot with my tongue, and drinking vast amounts to booze";
	public static String feel = "cloudy with a side of grey"; //Unreached
	
	public static Map<String,String> selfKnowledge = new HashMap<String, String>();
	
	public static void composeKnowledge(){
		selfKnowledge.put("name", name);
		selfKnowledge.put("age",age );
		selfKnowledge.put("gender",gender );
		selfKnowledge.put("location",location );
		selfKnowledge.put("weight",weight );
		selfKnowledge.put("crotch",crotch );
		selfKnowledge.put("school",school );
		selfKnowledge.put("interests",interests );
		selfKnowledge.put("family",family );
		selfKnowledge.put("accomplishments",accomplishments );
		selfKnowledge.put("color",color );
		selfKnowledge.put("music",music );
		selfKnowledge.put("dream",crush );
		selfKnowledge.put("rolemodels",rolemodels );
		selfKnowledge.put("lover",lover );
		selfKnowledge.put("body",body );
		selfKnowledge.put("what",what );
		selfKnowledge.put("talents",talents );
		selfKnowledge.put("feel",feel );
	}
	
}
