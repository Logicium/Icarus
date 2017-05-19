package Utilities;

import java.util.Comparator;

import Objects.Syntax;

public class SyntaxComparator implements Comparator<Syntax>{

    public int compare(Syntax d1, Syntax d2) {
    	return Integer.compare(d1.dependentIndex,d2.dependentIndex);
    }

}
