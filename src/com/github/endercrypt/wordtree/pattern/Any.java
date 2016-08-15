package com.github.endercrypt.wordtree.pattern;

import com.github.endercrypt.wordtree.BranchPattern;

public class Any implements BranchPattern
{
	@Override
	public boolean doesAllow(char c)
	{
		return true;
	}
}
