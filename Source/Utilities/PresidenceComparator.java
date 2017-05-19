package Utilities;

import java.util.Comparator;
import Objects.Sentence;

public class PresidenceComparator implements Comparator<Sentence>{
	
    public int compare(Sentence s1, Sentence s2) {
    	return Integer.compare(s1.presidence,s2.presidence);
    }


}
