package com.github.endercrypt.wordtree;

import java.io.Serializable;

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

	public static boolean isLetter(String string)
	{
		if (string == null)
			return false;
		return isLetters(string.toCharArray());
	}

	public static boolean isLetters(char... chars)
	{
		if (chars == null)
			return false;
		if (chars.length == 0)
			return false;
		for (char c : chars)
		{
			if (isLetter(c) == false)
				return false;
		}
		return true;
	}

	public static boolean isLetter(char c)
	{
		int index = c - ASCII_START;
		return ((index >= 0) && (index <= LETTERS));
	}
}
