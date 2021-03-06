package com.github.endercrypt.wordtree.pattern;

public class Letter implements BranchPattern
{
	private char letter;

	public Letter(char letter)
	{
		this.letter = letter;
	}

	@Override
	public boolean doesAllow(char c)
	{
		return (c == letter);
	}
}
