package Algorithm;

import java.util.ArrayList;
import Brain.CentralCorpus;
import Objects.PartOfSpeech;
import Objects.Sentence;

public class Tagger {

	public ArrayList<String> POSVector = new ArrayList<String>();
	public ArrayList<String> RootVector = new ArrayList<String>();
	static boolean firstTime = true;
	
	public void tag(Sentence s) {
	
		String tagged = CentralCorpus.PartsOfSpeechTagger.tagString(s.rawText);
		String taggedArray[] = tagged.split(" ");
		String temp[];
		
		for (int k = 0; k < taggedArray.length; k++) {
			temp = taggedArray[k].toString().split("_");
			RootVector.add(temp[0].toString());
			POSVector.add(temp[1].toString());
			PartOfSpeech pos = new PartOfSpeech(temp[0],temp[1]);
			s.pos.add(pos);
		}
	}	
}
