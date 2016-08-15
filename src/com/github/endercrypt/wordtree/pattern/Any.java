package com.github.endercrypt.wordtree.pattern;

public class Any implements BranchPattern
{
	@Override
	public boolean doesAllow(char c)
	{
		return true;
	}
}
