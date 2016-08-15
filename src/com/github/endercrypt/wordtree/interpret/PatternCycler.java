package com.github.endercrypt.wordtree.interpret;

import java.util.Iterator;

import com.github.endercrypt.wordtree.WordTree;
import com.github.endercrypt.wordtree.exception.InterpreterException;
import com.github.endercrypt.wordtree.pattern.Any;
import com.github.endercrypt.wordtree.pattern.BranchPattern;
import com.github.endercrypt.wordtree.pattern.Letter;
import com.github.endercrypt.wordtree.pattern.Not;
import com.github.endercrypt.wordtree.pattern.Range;
import com.github.endercrypt.wordtree.pattern.Select;

public class PatternCycler implements Iterator<Character>
{
	private int index = 0;
	private String string;

	public PatternCycler(String string)
	{
		this.string = string;
	}

	@Override
	public Character next()
	{
		if (hasNext() == false)
			throw new InterpreterException("Pattern incomplete");
		char c = string.charAt(index);
		index++;
		return c;
	}

	@Override
	public boolean hasNext()
	{
		return (index < string.length());
	}

	public void expect(char expect)
	{
		char got = next();
		if (got != expect)
			throw new InterpreterException("Invalid pattern at " + index + " expected " + expect + " got " + got);
	}

	public BranchPattern nextPattern()
	{
		char c = next();
		if (WordTree.isLetter(c)) // LETTER
		{
			return new Letter(c);
		}
		if (c == '?') // ANY
		{
			return new Any();
		}
		if (c == '!') // NOT
		{
			return new Not(nextPattern());
		}
		if (c == '[') // SELECT
		{
			int from = index;
			int to = string.indexOf(']', from);
			index = to + 1;
			return new Select(string.substring(from, to));
		}
		if (c == '(') // RANGE
		{
			char start = next();
			expect('-');
			char stop = next();
			expect(')');
			return new Range(start, stop);
		}
		throw new InterpreterException("Unknown pattern character " + c + " (at " + (index) + ")");
	}
}