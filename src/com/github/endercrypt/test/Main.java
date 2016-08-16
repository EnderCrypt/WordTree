package com.github.endercrypt.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

import com.github.endercrypt.wordtree.WordTree;
import com.github.endercrypt.wordtree.interpret.Interpreter;
import com.github.endercrypt.wordtree.interpret.Pattern;

public class Main
{
	private static WordTree master = new WordTree();

	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		loadWords();
		Pattern pattern = Interpreter.compile("m(b-f)[pl]h");
		Set<String> words = pattern.process(master).getWords(100);
		words.forEach(System.out::println);
	}

	private static void loadWords() throws FileNotFoundException, IOException
	{
		File file = new File("words.txt");
		System.out.print("Loading words from " + file + "... ");
		long start = System.currentTimeMillis();
		master.loadFile("words.txt");
		long miliTaken = System.currentTimeMillis() - start;
		System.out.println("Done in " + miliTaken + " Miliseconds!");
	}
}
