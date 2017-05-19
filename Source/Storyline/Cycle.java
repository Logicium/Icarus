package Storyline;

import java.util.ArrayList;

public class Cycle {

	public ArrayList<Instance> instances = new ArrayList<Instance>();
	public String origin;
	public int CycleNumber;
	
	public Cycle(Instance instance) {
		this.origin = instance.origin;
		this.CycleNumber = instance.CycleNumber;
		instances.add(instance);
	}

	public void add(Instance instance) {
		instances.add(instance);
	}
}
