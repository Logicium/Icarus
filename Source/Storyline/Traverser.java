package Storyline;

import java.util.ArrayList;

import Brain.CentralCorpus;
import Brain.Thoughts;
import Objects.Sentence;

public class Traverser {
	
	public Timeline t;
	
	
	public Traverser(Timeline t){
		this.t = t;
	}
	
	public int getFirstCycle(){
		return 0;
	}
	
	public Sentence getLastSentence(){
		return t.currentCycle.instances.get(0).s;
	}
	
	public void advance(){
		
		//[EXPECTATIONS]
		ArrayList<Expectation> toRemove = new ArrayList<Expectation>();
		System.out.println("\nAdvancing...");
		System.out.println("Size:"+Dispatch.expectations.size());
		for(Expectation e : Dispatch.expectations){
			if(e.exists){
				Sentence s = getLastSentence();
				if(e.meetExpectation(s)){
					String thought = "That's what I expected.";
					CentralCorpus.CorpusPrint("[INFO] "+thought);
					toRemove.add(e);
				}
				else{
					String thought = "That's NOT what I expected.";
					CentralCorpus.CorpusPrint("[INFO] "+thought);
					toRemove.add(e);
				}
			}
			else{
				//System.out.println("Expectation was empty...");
				toRemove.add(e);
			}
		}
		for(Expectation e : toRemove){ Dispatch.expectations.remove(e); }
		Thoughts.printBestThoughts();
	}
	
	public int getCurrentCycle(){
		return t.cycles.size()-1;
	}
	
	
	public Cycle getCycle(int n){
		return t.cycles.get(n);
	}
	
	public ArrayList<Cycle> getLastNCycles(int n){
		ArrayList<Cycle> nCycles = new ArrayList<Cycle>();
		
		for(int i = getCurrentCycle();i>0;i--,n--){
			nCycles.add(t.cycles.get(i));
			if(n==0){
				return nCycles;
			}
		}
		return nCycles;
	}

}
