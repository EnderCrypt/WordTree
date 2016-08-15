package com.github.endercrypt.wordtree.pattern;

import com.github.endercrypt.wordtree.BranchPattern;

public class Range implements BranchPattern
{
	private char start;
	private char stop;

	public Range(char start, char stop)
	{
		this.start = start;
		this.stop = stop;
	}

	@Override
	public boolean doesAllow(char c)
	{
		return ((c >= start) && (c <= stop));
	}

}
