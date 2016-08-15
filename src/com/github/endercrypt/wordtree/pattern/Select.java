package com.github.endercrypt.wordtree.pattern;

public class Select implements BranchPattern
{
	private char[] chars;

	public Select(String string)
	{
		chars = string.toCharArray();
	}

	public Select(char... chars)
	{
		this.chars = chars;
	}

	@Override
	public boolean doesAllow(char c)
	{
		for (char ca : chars)
		{
			if (ca == c)
				return true;
		}
		return false;
	}

}
