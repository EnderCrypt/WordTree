package com.github.endercrypt.wordtree.interpret;

import java.util.ArrayList;
import java.util.List;

import com.github.endercrypt.wordtree.WordTree;
import com.github.endercrypt.wordtree.exception.WordTreeException;
import com.github.endercrypt.wordtree.pattern.Any;
import com.github.endercrypt.wordtree.pattern.BranchPattern;
import com.github.endercrypt.wordtree.pattern.Letter;
import com.github.endercrypt.wordtree.pattern.Not;
import com.github.endercrypt.wordtree.pattern.Range;
import com.github.endercrypt.wordtree.pattern.Select;

public class Interpreter
{
	private Interpreter()
	{

	}

	public static Pattern compile(String patternString)
	{
		List<BranchPattern> patterns = new ArrayList<>();
		int i = 0;
		while (i < patternString.length())
		{
			char c = patternString.charAt(i);
			i++;
			if (WordTree.isLetter(c)) // LETTER
			{
				patterns.add(new Letter(c));
				continue;
			}
			if (c == '?') // ANY
			{
				patterns.add(new Any());
				continue;
			}
			if (c == '!') // NOT
			{
				char letter = patternString.charAt(i);
				patterns.add(new Not(letter));
				i++;
				continue;
			}
			if (c == '[') // SELECT
			{
				int to = patternString.indexOf(']', i);
				patterns.add(new Select(patternString.substring(i, to)));
				i = to;
				continue;
			}
			if (c == '(') // RANGE
			{
				char start = patternString.charAt(i);
				if (patternString.charAt(i + 1) != '-')
					throw new WordTreeException("getByPattern ( RANGE ) requires - between, eg (a-c)");
				char stop = patternString.charAt(i + 2);
				patterns.add(new Range(start, stop));
				i += 3;
				continue;
			}
		}
		return new Pattern(patterns);
	}
}
