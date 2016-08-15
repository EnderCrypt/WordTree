package com.github.endercrypt.wordtree.pattern;

public class Not implements BranchPattern
{
	private BranchPattern pattern;

	public Not(BranchPattern pattern)
	{
		this.pattern = pattern;
	}

	@Override
	public boolean doesAllow(char c)
	{
		return pattern.doesAllow(c) == false;
	}
}
