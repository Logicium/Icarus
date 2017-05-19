package Objects;

public class Syntax {
	
	public String type;
	public String governor;
	public String dependent;
	public int dependentIndex=0;
	
	public Syntax(String type, String governor,String dependent, int index) {
		this.type = type.toLowerCase();
		this.governor = governor.toLowerCase();
		this.dependent = dependent.toLowerCase();
		this.dependentIndex = index;
	}
	
	public Syntax(String type, String governor,String dependent) {
		this.type = type.toLowerCase();
		this.governor = governor.toLowerCase();
		this.dependent = dependent.toLowerCase();
	}
	
	public String toString(){
		return ("[Type: "+type+"] [Governor: "+governor+"] [Dependent: "+dependent+"]");
	}
	
	public boolean equals(Object object2) {
	    return object2 instanceof Syntax 
	    	&& type.equals(((Syntax)object2).type)
	    	&& governor.equals(((Syntax)object2).governor)
	    	&& dependent.equals(((Syntax)object2).dependent);
	}
}
