package Prototyping;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import rita.RiMarkov;

public class MarkovPrototyping {

	public static void main(String[] args) throws IOException {
		
		RiMarkov rm = new RiMarkov(3);
		String theText = readFile("FP.txt",Charset.defaultCharset());
		rm.loadText(theText);
		String[] sentences = rm.generateSentences(10);

		for (int i = 0; i < sentences.length; i++) {
			System.out.println(sentences[i]);
		}

	}
	
	static String readFile(String path, Charset encoding) throws IOException {
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, encoding);
	}

}
