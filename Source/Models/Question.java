package Models;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Brain.CentralCorpus;
import Genesis.WordNet;
import Objects.Sentence;
import Objects.Word;

public class Question{
	
	//How to handle questions directed at the computer
	
	/* [BASES]
	 * Who ---> Do I look like Facebook to you?
	 * What --> Ask an encyclopedia.
	 * When --> Time is really just an illusion. 
	 * Where --> There's this really cool site called GOOGLE MAPS.
	 * Why --> Hold on, I'm putting on my philosopher's hat for this. 
	 * How --> It is done with <ADV>.
	 * Which --> Pick a number one through ten. There's your answer. 
	 * 
	 * [MODIFIERS]
	 * Who<WP>: was<VBD>/is<VBZ> that<DT>/this<DT>, were<VBD> you<PRP> <VBP>, am<VBP> I<PRP>, are<VBP> you<PRP>, are<VBP> they<PRP>/those<DT>? 
	 * What<WP>: was/is that/this, were you <VBP>, am I, are you, are they? 
	 * When<WRB>:  was/is that/this, were you <VBP>, am I, are you, are they? 
	 * Where<WRB>: was/is that/this, were you <VBP>, am I, are you, are they? 
	 * Why<WRB>: was/is that/this, were you <VBP>, am I, are you, are they? 
	 * How<WRB>: is that <VBD/VBN/NN>
	 * Which<WP>: was/is that/this, were you <VBP>, am I, are you, are they? 
	 * 
	 * [ALTERNATES]
	 * Are<VBP> <PN> <VB>
	 * Do<VBP> <PN> <VB>
	 * Does<NNP> <PN> <VB> -->
	 * Have<VBP> <PN> <VB> --> I can't say right away. *Backwards reference
	 * Has<VBZ> <PRP> <VBN> --> 
	 * May<NNP> <PN> <VB> --> I give you my permission.
	 * Would<MD> <PN>  <VB> 
	 * Can<MD> <PN> <VB>
	 * Could<MD> <PRP> <VB>
	 * Should<MD> <PRP> <VB>
	 * */
	
	public static void setQuestionType(Sentence s){
		
			//Improve with regex
			if(s.elements[0].toLowerCase().matches("who")){s.questionType = ("who");}
			else if(s.elements[0].toLowerCase().matches("what")){s.questionType = ("what");} 
			else if(s.elements[0].toLowerCase().matches("when")){s.questionType = ("when");} 
			else if(s.elements[0].toLowerCase().matches("where")){s.questionType = ("where");} 
			else if(s.elements[0].toLowerCase().matches("why")){s.questionType = ("why");} 
			else if(s.elements[0].toLowerCase().matches("how")){s.questionType = ("how");} 
			else if(s.elements[0].toLowerCase().matches("which")){s.questionType = ("which");}
			else if(s.isAssertion&&(s.type.matches("question")||s.type.matches("interrogative"))){
				s.questionType= "assertion question";
			}
			else { 
				detectAlternativeQuestions(s,formatQuestionPOS(s)); 
			}
			CentralCorpus.CorpusPrint("[INFO] Question Type: " +s.questionType);
	}
	
	public static void generateQuestions(Sentence s){
		
		for(Word w : s.words){
			if(w.questionType.matches("what")){
				s.wordQuestions.add("What is a "+WordNet.conjugate(w.original)+"?");
			}
			else if(w.questionType.matches("how")){
				s.wordQuestions.add("So, it was done "+w.original+"?");
			}
			else if(w.questionType.matches("who")){
				s.wordQuestions.add("Who is "+WordNet.conjugate(w.original)+"?");
			}
			else if(w.questionType.matches("whose")){
				s.wordQuestions.add("So, it was "+w.original+"?");
			}
			else if(w.questionType.matches("which")){
				s.wordQuestions.add("So, we will use "+w.original+" of them?");
			}
			else if(w.questionType.matches("compared to what")){
				s.wordQuestions.add(w.original+", compared to what?");
			}
			else if(w.questionType.matches("where")){
				s.wordQuestions.add("Where is "+w.original+" on?");
			}
			else if(w.questionType.matches("which language")){
				s.wordQuestions.add("Which language is "+w.original+" in?");
			}
		}
	}
	
	public static String formatQuestionPOS(Sentence s){
		//Grab first word, concatenate with pos string
		Pattern alternateQuestion = Pattern.compile("(are)|(do)|(does)|(have)|(has)|(may)|(would)|(can)|(could)(should)");
		Matcher m = alternateQuestion.matcher(s.rawText.toLowerCase());
		String questionKeyword = "";
		if(m.find()){
			questionKeyword = m.group();
			questionKeyword.concat(" "+s.posPattern);
			return questionKeyword;
		}
		return "";
	}
	
	public static void detectAlternativeQuestions(Sentence s,String formattedTest){
				
		Pattern areQuestion = Pattern.compile("are (vbp) (pn) (vb)");
		Pattern doQuestion = Pattern.compile("do (vbp) (pn) (vb)");
		Pattern doesQuestion = Pattern.compile("does (nnp) (pn) (vb)");
		Pattern haveQuestion = Pattern.compile("have (vbn) (pn) (vb)");
		Pattern hasQuestion = Pattern.compile("has (vbz) (prp) (vbn)");
		Pattern mayQuestion = Pattern.compile("may (nnp) (pn) (vb)");
		Pattern wouldQuestion = Pattern.compile("would (md) (pn) (vb)");
		Pattern canQuestion = Pattern.compile("can (md) (pn) (vb)");
		Pattern couldQuestion = Pattern.compile("could (md) (prp) (vb)");
		Pattern shouldQuestion = Pattern.compile("should (md) (prp) (vb)");
		
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		
		patterns.add(areQuestion);
		patterns.add(doQuestion);
		patterns.add(doesQuestion);
		patterns.add(haveQuestion);
		patterns.add(hasQuestion);
		patterns.add(mayQuestion);
		patterns.add(wouldQuestion);
		patterns.add(canQuestion);
		patterns.add(couldQuestion);
		patterns.add(shouldQuestion);
		
		for(Pattern p : patterns){
			Matcher m = p.matcher(formattedTest);
			if(m.find()){
				s.questionType = m.group().split(" ")[0];
			}
		}

	}
	

}
