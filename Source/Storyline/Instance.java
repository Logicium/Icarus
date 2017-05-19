package Storyline;

import Objects.Sentence;

public class Instance {

	public Sentence s;
	public String origin;
	public int instanceNumber;
	public int CycleNumber;
	
	public Instance(Sentence content,int instanceNum, int CycleNum){
		this.s = content;
		this.origin = content.origin;
		this.instanceNumber = instanceNum;
		this.CycleNumber = CycleNum;
	}
}
