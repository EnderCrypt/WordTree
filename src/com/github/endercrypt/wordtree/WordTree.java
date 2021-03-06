package com.github.endercrypt.wordtree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;

import com.github.endercrypt.wordtree.branch.Branch;
import com.github.endercrypt.wordtree.exception.WordTreeException;

public class WordTree extends Branch implements Serializable
{
	private static final long serialVersionUID = 504967785518953354L;

	/**
	 * 
	 */

	public WordTree()
	{
		super();
	}

	public void addWord(String word)
	{
		addWord(word, 0);
	}

	public void loadFile(String string) throws FileNotFoundException, IOException
	{
		loadFile(new File(string));
	}

	public void loadFile(File file) throws FileNotFoundException, IOException
	{
		try (BufferedReader br = new BufferedReader(new FileReader(file)))
		{
			String line;
			while ((line = br.readLine()) != null)
			{
				try
				{
					addWord(line.toLowerCase());
				}
				catch (WordTreeException e)
				{
					// ignore
				}
			}
		}
	}
}
