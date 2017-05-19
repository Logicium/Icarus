package Utilities;

import java.util.Vector;

import Brain.CentralCorpus;

public class Extraneous {
	
	public static void ReCognizer(String[] taggedArray) {
		CentralCorpus.CorpusPrint("[LAUNCH] POSAnalysis.ReCognizer(String[] taggedArray)");
		Vector<String> POSVector = new Vector<String>();
		Vector<String> RootVector = new Vector<String>();
		Vector<String> POSVectorVector = new Vector<String>();
		Vector<String> RootVectorVector = new Vector<String>();

		for (int k = 0; k < taggedArray.length; k++) {
			if (taggedArray[k].contains(partsOfSpeech(taggedArray[k].toString()))) {
				Analysis(taggedArray[k]);
			} else {
				break;
			}
		}
		CentralCorpus.CorpusPrint("[END] POSAnalysis.ReCognizer(String[] taggedArray)");
	}
	
	public String Dependencies(String depen) {
		String[] dependencyList = { "acomp", "advcl", "advmod", "agent",
				"amod", "appos", "aux", "auxpass", "cc", "ccomp", "conj",
				"cop", "csubj", "csubjpass", "dep", "det", "discourse", "dobj",
				"expl", "goeswith", "iobj", "mark", "mwe", "neg", "nn",
				"npadvmod", "nsubj", "nsubjpass", "num", "number", "parataxis",
				"pcomp", "pobj", "poss", "possessive", "preconj", "predet",
				"prep", "prepc", "prt", "punct", "quantmod", "referent",
				"root", "tmod", "vmod", "xcomp", "xsubj" };
		boolean flag = true;

		while (flag == true) {
			//CentralCorpus.CorpusPrint(depen);

			for (int i = 0; i < dependencyList.length; i++) {
				if (dependencyList[i].matches(depen)) {
					return dependencyList[i];
				} else {

				}
			}
			flag = false;
		}
		return depen;
	}


	public static String partsOfSpeech(String POS) {
		CentralCorpus.CorpusPrint("[LAUNCH] POSAnalysis.partsOfSpeech(String POS)");
		
		String[] POSArray = { "CC", "CD", "DT", "EX", "FW", "IN", "JJ", "JJR",
				"JJS", "LS", "MD", "NN", "NNP", "NNPS", "NNS", "PDT", "POS",
				"PRP", "PRP$", "RB", "RBR", "RBS", "RP", "SYM", "TO", "UH",
				"VB", "VBD", "VBG", "VBN", "VBP", "VBZ", "WDT", "WP", "WP$",
				"WRB" };
		boolean flag = true;

		while (flag = true) {
			CentralCorpus.CorpusPrint(POS);

			if (POS.contains("_")) {
				String[] temp = POS.split("_");
				POS = temp[1].toString();

				for (int i = 0; i < POSArray.length; i++) {
					if (POSArray[i].matches(POS)) {
						return POSArray[i];
					} else {
					}
				}flag = false; 
			}
			else break;
		}
		CentralCorpus.CorpusPrint("[END] POSAnalysis.partsOfSpeech(String POS)");
		return POS;
	}
	
	public static void Analysis(String POS) {
		CentralCorpus.CorpusPrint("[LAUNCH] POSAnalysis.Analysis(String POS)");
		String[] temp = POS.split("_");
		if(temp[1].matches("CC")){
			CentralCorpus.CorpusPrint("Coordinating conjuntion.");
			CentralCorpus.CorpusPrint("The word is: " + temp[0]);
		}
		if(temp[1].matches("CD")){
			CentralCorpus.CorpusPrint("Cardinal number.");
			CentralCorpus.CorpusPrint("The word is: " + temp[0]);
		}	
		if(temp[1].matches("DT")){
			CentralCorpus.CorpusPrint("Determiner.");
			CentralCorpus.CorpusPrint("The word is: " + temp[0]);
		}
		if(temp[1].matches("EX")){
			CentralCorpus.CorpusPrint("Existential /there/.");
			CentralCorpus.CorpusPrint("The word is: " + temp[0]);
		}
		if(temp[1].matches("FW")){
			CentralCorpus.CorpusPrint("Foreign word.");
			CentralCorpus.CorpusPrint("The word is: " + temp[0]);
		}
		if(temp[1].matches("IN")){
			CentralCorpus.CorpusPrint("Preoposition or subordaning conjunction.");
			CentralCorpus.CorpusPrint("The word is: " + temp[0]);
		}
		if(temp[1].matches("JJ")){
			CentralCorpus.CorpusPrint("Adjective.");
			CentralCorpus.CorpusPrint("The word is: " + temp[0]);
		}
		if(temp[1].matches("JJR")){
			CentralCorpus.CorpusPrint("Adjective, Comparitive.");
			CentralCorpus.CorpusPrint("The word is: " + temp[0]);
		}
		if(temp[1].matches("JJS")){
			CentralCorpus.CorpusPrint("Adjective, Superlative.");
			CentralCorpus.CorpusPrint("The word is: " + temp[0]);
		}
		if(temp[1].matches("LS")){
			CentralCorpus.CorpusPrint("List Item Marker.");
			CentralCorpus.CorpusPrint("The word is: " + temp[0]);
		}
		if(temp[1].matches("MD")){
			CentralCorpus.CorpusPrint("Modal.");
			CentralCorpus.CorpusPrint("The word is: " + temp[0]);
		}
		if(temp[1].matches("NN")){
			CentralCorpus.CorpusPrint("Noun.");
			CentralCorpus.CorpusPrint("The word is: " + temp[0]);
		}
		if(temp[1].matches("NNP")){
			CentralCorpus.CorpusPrint("Proper Noun, Singular.");
			CentralCorpus.CorpusPrint("The word is: " + temp[0]);
		}
		if(temp[1].matches("NNPS")){
			CentralCorpus.CorpusPrint("Proper Noun, Plural.");
			CentralCorpus.CorpusPrint("The word is: " + temp[0]);
		}
		if(temp[1].matches("NNS")){
			CentralCorpus.CorpusPrint("Noun, plural");
			CentralCorpus.CorpusPrint("The word is: " + temp[0]);
		}
		if(temp[1].matches("PDT")){
			CentralCorpus.CorpusPrint("Predeterminer.");
			CentralCorpus.CorpusPrint("The word is: " + temp[0]);
		}
		if(temp[1].matches("POS")){
			CentralCorpus.CorpusPrint("Possessive Ending Noun.");
			CentralCorpus.CorpusPrint("The word is: " + temp[0]);
		}
		if(temp[1].matches("PRP")){
			CentralCorpus.CorpusPrint("Pesonal Pronoun.");
			CentralCorpus.CorpusPrint("The word is: " + temp[0]);
		}
		if(temp[1].matches("PRP$")){
			CentralCorpus.CorpusPrint("Possessive Pronoun.");
			CentralCorpus.CorpusPrint("The word is: " + temp[0]);
		}
		if(temp[1].matches("RB")){
			CentralCorpus.CorpusPrint("Adverb.");
			CentralCorpus.CorpusPrint("The word is: " + temp[0]);
		}
		if(temp[1].matches("RBR")){
			CentralCorpus.CorpusPrint("Adverb, Comparative.");
			CentralCorpus.CorpusPrint("The word is: " + temp[0]);
		}
		if(temp[1].matches("RBS")){
			CentralCorpus.CorpusPrint("Adverb, Superlative.");
			CentralCorpus.CorpusPrint("The word is: " + temp[0]);
		}
		if(temp[1].matches("RP")){
			CentralCorpus.CorpusPrint("Particle.");
			CentralCorpus.CorpusPrint("The word is: " + temp[0]);
		}
		if(temp[1].matches("SYM")){
			CentralCorpus.CorpusPrint("Symbol.");
			CentralCorpus.CorpusPrint("The word is: " + temp[0]);
		}
		if(temp[1].matches("TO")){
			CentralCorpus.CorpusPrint("/To/.");
			CentralCorpus.CorpusPrint("The word is: " + temp[0]);
		}
		if(temp[1].matches("UH")){
			CentralCorpus.CorpusPrint("Interjection.");
			CentralCorpus.CorpusPrint("The word is: " + temp[0]);
		}
		if(temp[1].matches("VB")){
			CentralCorpus.CorpusPrint("Verb, Base Form.");
			CentralCorpus.CorpusPrint("The word is: " + temp[0]);
		}
		if(temp[1].matches("VBD")){
			CentralCorpus.CorpusPrint("Verb, Past Tense.");
			CentralCorpus.CorpusPrint("The word is: " + temp[0]);
		}
		if(temp[1].matches("VBG")){
			CentralCorpus.CorpusPrint("Verb, Gerund or Present Participle.");
			CentralCorpus.CorpusPrint("The word is: " + temp[0]);
		}
		if(temp[1].matches("VBN")){
			CentralCorpus.CorpusPrint("Verb, past participle.");
			CentralCorpus.CorpusPrint("The word is: " + temp[0]);
		}
		if(temp[1].matches("VBP")){
			CentralCorpus.CorpusPrint("Verb, non-3rd person singular present.");
			CentralCorpus.CorpusPrint("The word is: " + temp[0]);
		}
		if(temp[1].matches("VBZ")){
			CentralCorpus.CorpusPrint("Verb, 3rd person, singular present.");
			CentralCorpus.CorpusPrint("The word is: " + temp[0]);
		}
		if(temp[1].matches("WDT")){
			CentralCorpus.CorpusPrint("Wh-Determiner.");
			CentralCorpus.CorpusPrint("The word is: " + temp[0]);
		}
		if(temp[1].matches("WP")){
			CentralCorpus.CorpusPrint("Wh-Pronoun.");
			CentralCorpus.CorpusPrint("The word is: " + temp[0]);
		}
		if(temp[1].matches("WP$")){
			CentralCorpus.CorpusPrint("Possessive wh-pronoun.");
			CentralCorpus.CorpusPrint("The word is: " + temp[0]);
		}
		if(temp[1].matches("WRB")){
			CentralCorpus.CorpusPrint("Wh-adverb.");
			CentralCorpus.CorpusPrint("The word is: " + temp[0]);
		}
		CentralCorpus.CorpusPrint("[END] POSAnalysis.Analysis(String POS)");
	}

}
