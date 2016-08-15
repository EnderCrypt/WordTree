package com.github.endercrypt.wordtree.pattern;

public class Not implements BranchPattern
{
	private char letter;

	public Not(char letter)
	{
		this.letter = letter;
	}

	@Override
	public boolean doesAllow(char c)
	{
		return (c != letter);
	}
}
