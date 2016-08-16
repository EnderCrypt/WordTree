package com.github.endercrypt.wordtree.interpret;

import java.util.Iterator;

import com.github.endercrypt.wordtree.branch.Branch;
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
		return next("Pattern incomplete (reason unknown)");
	}

	public Character next(String errorString)
	{
		if (hasNext() == false)
			throw new InterpreterException(errorString);
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
		char got = next("missing " + expect + " at position " + (index + 1));
		if (got != expect)
			throw new InterpreterException("Invalid pattern at " + index + " expected " + expect + " got " + got);
	}

	public BranchPattern nextPattern()
	{
		char c = next();
		if (isLetter(c)) // LETTER
		{
			return new Letter(c);
		}
		if (isAny(c)) // ANY
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
			if (to == -1)
				throw new InterpreterException("Missing a closing ] character, to close [ at position " + from);
			index = to + 1;
			String inString = string.substring(from, to);
			for (char cc : inString.toCharArray())
			{
				if (isLetter(cc) == false)
					throw new InterpreterException("contents of [ ] (position " + from + " to " + to + ") contains illegal character '" + cc + "'");
			}
			return new Select(inString);
		}
		if (c == '(') // RANGE
		{
			char start = next("missing starting character in ( starting at position " + index);
			if (isAny(start))
				start = 'a';
			if (isLetter(start) == false)
				throw new InterpreterException("Invalid character '" + start + "' at position " + index + ", must be a letter");
			expect('-');
			char stop = next("missing ending character in (" + start + "- at position " + (index + 1));
			if (isAny(start))
				start = 'z';
			if (isLetter(stop) == false)
				throw new InterpreterException("Invalid character '" + stop + "' at position " + index + ", must be a letter");
			expect(')');
			return new Range(start, stop);
		}
		throw new InterpreterException("Unknown pattern character '" + c + "' (at position " + (index) + ")");
	}

	private boolean isLetter(char c)
	{
		int index = c - Branch.ASCII_START;
		return ((index >= 0) && (index <= Branch.LETTERS));
	}

	private boolean isAny(char c)
	{
		return ((c == '?') || (c == '*'));
	}
}