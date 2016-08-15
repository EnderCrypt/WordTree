package com.github.endercrypt.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

import com.github.endercrypt.wordtree.WordTree;
import com.github.endercrypt.wordtree.exception.WordTreeException;
import com.github.endercrypt.wordtree.interpret.CompiledPattern;
import com.github.endercrypt.wordtree.interpret.Interpreter;

public class Main
{
	private static WordTree master = new WordTree();

	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		loadWords();
		CompiledPattern pattern = Interpreter.compile("m(b-f)[pl]h");
		Set<String> words = pattern.run(master).getWords(100);
		words.forEach(System.out::println);
	}

	private static void loadWords() throws FileNotFoundException, IOException
	{
		File file = new File("words.txt");
		System.out.print("Loading words from " + file + "... ");
		long start = System.currentTimeMillis();
		try (BufferedReader br = new BufferedReader(new FileReader(file)))
		{
			String line;
			while ((line = br.readLine()) != null)
			{
				try
				{
					master.addWord(line.toLowerCase());
				}
				catch (WordTreeException e)
				{
					// ignore
				}
			}
		}
		long miliTaken = System.currentTimeMillis() - start;
		System.out.println("Done in " + miliTaken + " Miliseconds!");
	}
}
