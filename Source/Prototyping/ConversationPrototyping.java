package Prototyping;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import Brain.CentralCorpus;

public class ConversationPrototyping {
	
	/* Use the Prototyping class to script plausible conversation by the machine. 
	 * When the script is over, open the text file to add comments.
	 * Comments reflect the specific processing done on text to produce Icarus's replies
	 * The Prototyping helps clarify the next mechanisms needed to be developed. 
	 * */	

	public static void main(String[] args) throws IOException {
		CentralCorpus.CorpusPrint("Commencing conversation prototyping...");
		String humanInput ="";
		String IcarusInput="";
		Scanner in = new Scanner(System.in);
		FileWriter fw = new FileWriter("prototype.txt");
		while(!humanInput.matches("Bye")){
			System.out.print("[ICARUS] ");
			IcarusInput = in.nextLine();
			fw.write("[ICARUS] "+IcarusInput+"\n");
			System.out.print("[HUMAN] ");
			humanInput = in.nextLine();
			fw.write("[HUMAN] "+humanInput+"\n");
		}
		fw.close();
	}

}
