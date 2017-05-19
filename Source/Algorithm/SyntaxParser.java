package Algorithm;
import java.util.*;

import Brain.CentralCorpus;
import Objects.Syntax;
import Objects.Sentence;

public class SyntaxParser {

	public ArrayList<String> depFromColl = new ArrayList<String>();
	public ArrayList<String> collapsed = new ArrayList<String>();
	
	public void syntaxAnalysis(String graph,Sentence s) {
		
		ArrayList<String> syntax = new ArrayList<String>(Arrays.asList(graph.split("\n")));
		for (int k = 0; k < syntax.size(); k++) {
			parseSyntax(syntax.get(k).toString(), s);
		}
	}

	public void parseSyntax(String dependency, Sentence s) {

		//CentralCorpus.CorpusPrint("[LAUNCH] Syntax.Analysis()");
		
		String collapsed = "";
		String[] typeVsContent = dependency.split("\\(");
		String extractedDependency = typeVsContent[0];
		String[] governorVsDependent = typeVsContent[1].split(",");
		String[] governorVsIndex = governorVsDependent[0].split("-");
		String governor = governorVsIndex[0];
		String[] dependentVsIndex = governorVsDependent[1].split("-");
		String dependent = dependentVsIndex[0];
		int index = Integer.parseInt(dependentVsIndex[1].split("\\)")[0]);
		
		Syntax d = new Syntax(extractedDependency.trim(),governor.trim(),dependent.trim(),index);
		
		if (extractedDependency.contains(":")) {
			String[] actualDependency = extractedDependency.split(":");
			@SuppressWarnings("unused")
			String cleanDependency = actualDependency[0];
			collapsed = actualDependency[1];
			CentralCorpus.CorpusPrint("[INFO] I found a collapsed dependency.");
			d = new Syntax(collapsed.trim(),governor.trim(),dependent.trim(),index);
		}
		
		s.syntax.add(d);
		
		//CentralCorpus.CorpusPrint("[END] Syntax.Analysis()");
	}
}