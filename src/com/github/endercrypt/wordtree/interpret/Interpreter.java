package com.github.endercrypt.wordtree.interpret;

import java.util.ArrayList;
import java.util.List;

import com.github.endercrypt.wordtree.pattern.BranchPattern;

public class Interpreter
{
	private Interpreter()
	{

	}

	public static Pattern compile(String patternString)
	{
		List<BranchPattern> patterns = new ArrayList<>();
		PatternCycler iterator = new PatternCycler(patternString);
		while (iterator.hasNext())
		{
			patterns.add(iterator.nextPattern());
		}
		return new Pattern(patterns);
	}
}
