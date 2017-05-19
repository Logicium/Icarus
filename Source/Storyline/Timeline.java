package Storyline;

import java.util.ArrayList;

import Brain.CentralCorpus;

public class Timeline {
	
	public ArrayList<Cycle> cycles = new ArrayList<Cycle>();
	public ArrayList<String> humanInstances = new ArrayList<String>();
	public ArrayList<String> IcarusInstances = new ArrayList<String>();
	public Cycle currentCycle;
	public int currentCycleIndex = 0;
	
	public void addInstance(Instance instance) {
		
		if(instance.CycleNumber==1 && instance.instanceNumber==1){
			currentCycle = new Cycle(instance);
			cycles.add(currentCycle);
			currentCycleIndex = 1;
		}	
		else if(instance.instanceNumber>1){
			if(instance.CycleNumber==currentCycleIndex){
				currentCycle.add(instance);
				currentCycleIndex = instance.CycleNumber;
			}
			else if(instance.CycleNumber>currentCycleIndex){
				currentCycle = new Cycle(instance);
				cycles.add(currentCycle);
				currentCycleIndex = instance.CycleNumber;
			}
		}
		
		if(instance.origin.matches("Icarus")){
			IcarusInstances.add(instance.s.rawText);
			CentralCorpus.sortList.add(instance.s.rawText);
		}
		else if(instance.origin.matches("human")){
			humanInstances.add(instance.s.rawText);
		}	
	}
	
	public void printTimeline(){
		System.out.println("\n[TIMELINE] CycleCount:"+currentCycleIndex);
		for(Cycle c : cycles){
			System.out.println("[TIMELINE] Cycle number: "+c.CycleNumber);
			System.out.println("[TIMELINE] Cycle size: "+c.instances.size());
			for(Instance i : c.instances){
				System.out.println("[TIMELINE] Instance number: "+i.instanceNumber);
				System.out.println("[TIMELINE] Instance sentence: "+i.s.rawText);
			}
		}
	}
	
	public void separateInstanceOrigins(){
		IcarusInstances.clear();
		humanInstances.clear();		
		for(Cycle c: cycles){
			for(Instance i : c.instances){
				if(i.origin.matches("Icarus")){
					IcarusInstances.add(i.s.rawText);
					CentralCorpus.sortList.add(i.s.rawText);
				}
				else if(i.origin.matches("human")){
					humanInstances.add(i.s.rawText);
				}	
			}
		}	
	}
}
