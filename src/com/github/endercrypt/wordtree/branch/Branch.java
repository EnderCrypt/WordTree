package com.github.endercrypt.wordtree.branch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.github.endercrypt.wordtree.exception.WordTreeException;

public class Branch extends BranchSource implements Serializable
{
	private static final long serialVersionUID = 9059589071905139827L;

	/**
	 * 
	 */

	protected static final int ASCII_START = 97;
	protected static final int LETTERS = 26;

	private Branch[] branches = new Branch[LETTERS];
	private String word = null;
	private char branchChar = '\000';
	private boolean isWord = false;
	private int wordCount = 0;

	/**
	 * creates a new empty branch
	 * @param word
	 */
	protected Branch()
	{
		this.word = "";
	}

	/**
	 * Creates a new empty node branching from a word
	 * @param previous
	 * @param c
	 */

	protected Branch(String word, char c)
	{
		this.word = word + c;
		branchChar = c;
	}

	/**
	 * Creates a new empty node branching out from the previous branch, and for this letter
	 * @param previous
	 * @param c
	 */
	protected Branch(Branch previous, char c)
	{
		this(previous.word, c);
	}

	protected Branch getOrCreate(char c)
	{
		int index = index(c);
		Branch current = branches[index];
		if (current != null)
		{
			return current;
		}
		current = new Branch(this, c);
		branches[index] = current;
		return current;
	}

	protected boolean addWord(String word, int index)
	{
		if (index == word.length())
		{
			return setAsWord();
		}
		else
		{
			Branch node = getOrCreate(word.charAt(index));
			boolean isAdded = node.addWord(word, index + 1);
			if (isAdded)
			{
				wordCount++;
			}
			return isAdded;
		}
	}

	protected boolean setAsWord()
	{
		if (isWord == false)
		{
			isWord = true;
			wordCount++;
			return true;
		}
		return false;
	}

	private int index(char c)
	{
		int index = c - ASCII_START;
		if ((index < 0) || (index > LETTERS))
			throw new WordTreeException("Not a valid letter");
		return index;
	}

	public int countWords()
	{
		return wordCount;
	}

	public boolean isWord()
	{
		return isWord;
	}

	public char getChar()
	{
		return branchChar;
	}

	@Override
	public void getWords(Set<String> set, int limit)
	{
		if (limit == set.size())
			return;
		if (isWord())
		{
			set.add(word);
		}
		for (Branch subBranch : branches)
		{
			if (subBranch != null)
				subBranch.getWords(set, limit);
		}
	}

	@Override
	public String toString()
	{
		return word;
	}

	@Override
	public Branch[] getBranches()
	{
		return new Branch[] { this };
	}

	@Override
	public Branch[] getSubBranches()
	{
		List<Branch> subBranches = new ArrayList<>();
		for (Branch branch : branches)
		{
			if (branch != null)
				subBranches.add(branch);
		}
		return subBranches.toArray(new Branch[subBranches.size()]);
	}
}
