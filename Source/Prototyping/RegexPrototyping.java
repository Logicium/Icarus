package Prototyping;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexPrototyping {

	public static void main(String[] args) {
		String test1 = "nsubj cop advmod root ";
		String test2 = "nsubj cop advmod det root";
		String test3 = "Icarus, are you self aware?";
		
		//"(discourse)? nsubj (aux)? cop advmod \\.*"
		Pattern humanAssertionSyntaxPattern = Pattern.compile("(are)|(do)|(does)|(have)|(has)|(may)|(would)|(can)|(could)(should)");
		Matcher m = humanAssertionSyntaxPattern.matcher(test3);
		
		if(m.find()){
			System.out.println("Success.");
			System.out.println(m.start());
			System.out.println(m.end());
			System.out.println(m.group());
		}

	}

}
