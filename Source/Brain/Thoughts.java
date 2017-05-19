package Brain;

import java.util.ArrayList;
import java.util.Collections;

import Objects.Sentence;
import Utilities.PresidenceComparator;

public class Thoughts {
	
	public static ArrayList<Sentence> thoughts = new ArrayList<Sentence>();
	
	public static void addThought(Sentence s){
		thoughts.add(s);
	}
	
	public static void printBestThoughts(){
		sortThoughts();
		int top25Percent = (int) Math.ceil(thoughts.size()*0.25);
		for(Sentence s:thoughts){
			if(!(top25Percent==0)){
				CentralCorpus.CorpusPrint("[ICARUS] "+s.rawText);
			}
			else{
				break;
			}
		}
		thoughts.clear();
	}

	private static void sortThoughts() {
		PresidenceComparator pc = new PresidenceComparator();
		Collections.sort(thoughts,pc);
	}

}
